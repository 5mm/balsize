package com.devroo.donttouchme.BackgroundService;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.*;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.devroo.donttouchme.Class.CApp;
import com.devroo.donttouchme.Class.CCalendar;
import com.devroo.donttouchme.Class.CDate;
import com.devroo.donttouchme.Class.CRoom;
import com.devroo.donttouchme.Class.CSetting;
import com.devroo.donttouchme.Class.CTime;
import com.devroo.donttouchme.DBHandler.DBApplicationHandler;
import com.devroo.donttouchme.DBHandler.DBDateHandler;
import com.devroo.donttouchme.DBHandler.DBRoomHandler;
import com.devroo.donttouchme.DBHandler.DBScheduleHandler;
import com.devroo.donttouchme.DBHandler.DBSettingHandler;
import com.devroo.donttouchme.DBHandler.DBTimeHandler;
import com.devroo.donttouchme.MainActivity;
import com.devroo.donttouchme.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TaskService extends Service {
    public static LocationManager manager;
    public static boolean isGPSEnabled = false;
    public static double lattitude = 0;
    public static double longitude = 0;
    int count = 0;

    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat("EEE");
    SimpleDateFormat sdf3 = new SimpleDateFormat("HH-mm-ss");

    public DBDateHandler dateDB = null;
    public DBTimeHandler timeDB = null;
    public DBScheduleHandler schDB = null;
    public DBApplicationHandler appDB = null;
    public DBRoomHandler roomDB = null;
    public DBSettingHandler setDB = null;

    public CSetting cs = new CSetting();

    boolean isDateStatus = false;
    boolean isTimeStatus = false;
    boolean isSchStatus = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    } // method ends

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        System.out.println("On Start Command is called ");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service is created//////////// ");
        new GetLocations().execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service is  Destroyed  //////////// ");
        if (manager != null && isGPSEnabled == true) {
            manager.removeUpdates(mylistener);
            System.out.println("Service is  Destroyed under if //////////// ");
        }
    }

    public class GetLocations extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dateDB = new DBDateHandler(getApplicationContext());
            timeDB = new DBTimeHandler(getApplicationContext());
            schDB = new DBScheduleHandler(getApplicationContext());
            appDB = new DBApplicationHandler(getApplicationContext());
            roomDB = new DBRoomHandler(getApplicationContext());
            setDB = new DBSettingHandler(getApplicationContext());

            List<CSetting> csList = setDB.getAllRows();
            for (CSetting set : csList) {
                cs = set;
            }

            manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isGPSEnabled) {
                manager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, 3000, 0, mylistener);
            } else {
                Toast.makeText(TaskService.this, "GPS를 켜주세요.",
                        Toast.LENGTH_LONG).show();
            }

            Timer timer = new Timer();
            timer.schedule(noti, 0, 10000);
            timer.schedule(statusCheck, 0, 3000);
            timer.schedule(alertSubject, 0, 1000 * 60 * 60 * 12);
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }// method ends
    }


    public void isAppRunning() {
        Log.d("rows", isDateStatus + " :: " + isTimeStatus + " :: " + isSchStatus);

        if (isDateStatus || isTimeStatus || isSchStatus) {

            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> al = am.getRunningAppProcesses();
            List<CApp> appList = appDB.getAllRows();

            CSetting cs = null;
            if (setDB.getRowCount() > 0) {
                cs = setDB.getRow(1);
            }

            for (int i = 0; i < al.size(); i++) {

                if (cs != null && cs.getAppOutYn() != null && cs.getAppOutYn().equals("Y")) {
                    for (CApp ap : appList) {
                        if (ap.getAppId() != null && (ap.getAppId().equals(al.get(i).processName))) {
                            Log.d("rows", ap.getAppId() + " :: " + al.get(i).processName);

                            if (al.get(i).importance <= 100) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_MAIN);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                startActivity(intent);

                                sendNotification("차단중...", ap.getAppNm() + "을(를) 종료합니다.");
                            }
                        }
                    }

                    if ("com.devroo.donttouchme".equals(al.get(i).processName)) {
                        if (al.get(i).importance <= 100) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_MAIN);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            startActivity(intent);

                            sendNotification("차단중...", "Don't Touch Me 를 종료합니다.");
                        }
                    }
                }
            }
            System.gc();
        }
    }

    TimerTask statusCheck = new TimerTask() {
        public void run() {
            isAppRunning();
        }
    };

    TimerTask alertSubject = new TimerTask() {
        public void run() {
            String today = sdf.format(new Date());
            String nowTime = sdf3.format(new Date());
            String[] days = today.split("-");
            String[] timeArr = nowTime.split("-");

            List<CCalendar> calList = schDB.getAllRows();
            if (calList.size() > 0) {
                for (CCalendar cl : calList) {
                    if (cl.getTypeCd().equals("1")) {
                        if (cl.getVDtm() == null) continue;

                        String[] da = cl.getVDtm().split("-");

                        Calendar cal1 = Calendar.getInstance();
                        cal1.set(Calendar.YEAR, Integer.valueOf(days[0]));
                        cal1.set(Calendar.MONTH, Integer.valueOf(days[1]) - 1);
                        cal1.set(Calendar.DATE, Integer.valueOf(days[2]));
                        cal1.set(Calendar.HOUR, 0);
                        cal1.set(Calendar.MINUTE, 0);
                        cal1.set(Calendar.SECOND, 0);

                        Calendar cal2 = Calendar.getInstance();
                        cal2.set(Calendar.YEAR, Integer.valueOf(da[0]));
                        cal2.set(Calendar.MONTH, Integer.valueOf(da[1]) - 1);
                        cal2.set(Calendar.DATE, Integer.valueOf(da[2]));
                        cal2.set(Calendar.HOUR, 0);
                        cal2.set(Calendar.MINUTE, 0);
                        cal2.set(Calendar.SECOND, 0);

                        if (cs.getSubjectDays() != null) {
                            cal2.add(Calendar.DATE, -Integer.valueOf(cs.getSubjectDays()));

                            Log.d("rows", String.valueOf(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                                            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                                            cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE)
                            ));

                            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                                    cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                                    cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE)) {
                                sendNotification("과제알림", cl.getTitle() + "[" + cl.getSubject() + "]" + "가 "
                                        + cs.getSubjectDays() + "일 남았습니다.");
                            }
                        } else {
                            Log.d("rows", String.valueOf(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                                            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                                            cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE)
                            ));

                            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                                    cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                                    cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE)) {
                                sendNotification("과제알림", cl.getTitle() + "[" + cl.getSubject() + "]" + "가 "
                                        + "제출일 입니다.");
                            }
                        }
                    }
                }
            }
        }
    };

    TimerTask noti = new TimerTask() {
        public void run() {
            CSetting cs = null;
            if (setDB.getRowCount() > 0) {
                cs = setDB.getRow(1);


                String today = sdf.format(new Date());
                String nowTime = sdf3.format(new Date());
                String[] timeArr = nowTime.split("-");

                List<CDate> dateList = dateDB.getAllRows(new Date());
                boolean isChecked = false;
                if (dateList.size() > 0) {
                    for (CDate dt : dateList) {
                        int sH, sM, eH, eM;
                        sH = Integer.valueOf(dt.getSTime().split(":")[0]);
                        sM = Integer.valueOf(dt.getSTime().split(":")[1]);
                        eH = Integer.valueOf(dt.getETime().split(":")[0]);
                        eM = Integer.valueOf(dt.getETime().split(":")[1]);

                        boolean l_status = false, r_status = false;
                        if (sH <= Integer.valueOf(timeArr[0])) {
                            if (sH == Integer.valueOf(timeArr[0])) {
                                if (sM > Integer.valueOf(timeArr[1])) {
                                    continue;
                                } else {
                                    l_status = true;
                                }
                            } else {
                                l_status = true;
                            }
                        }
                        if (eH >= Integer.valueOf(timeArr[0])) {
                            if (eH == Integer.valueOf(timeArr[0])) {
                                if (eM < Integer.valueOf(timeArr[1])) {
                                    continue;
                                } else {
                                    r_status = true;
                                }
                            } else {
                                r_status = true;
                            }
                        }

                        if (l_status && r_status) {
                            isChecked = true;
                        } else {
                            isChecked = false;
                        }
                    }
                }

                if (isChecked) {
                    isDateStatus = true;
                } else {
                    isDateStatus = false;
                }

                List<CCalendar> calList = schDB.getAllRows(today);
                isChecked = false;
                if (calList.size() > 0) {
                    for (CCalendar cl : calList) {
                        if (cl.getTypeCd().equals("2")) {
                            if (cl.getOutYn() != null && cl.getOutYn().equals("true")) {
                                int sH, sM, eH, eM;
                                sH = Integer.valueOf(cl.getSTime().split(":")[0]);
                                sM = Integer.valueOf(cl.getSTime().split(":")[1]);
                                eH = Integer.valueOf(cl.getETime().split(":")[0]);
                                eM = Integer.valueOf(cl.getETime().split(":")[1]);

                                boolean l_status = false, r_status = false;
                                if (sH <= Integer.valueOf(timeArr[0])) {
                                    if (sH == Integer.valueOf(timeArr[0])) {
                                        if (sM > Integer.valueOf(timeArr[1])) {
                                            continue;
                                        } else {
                                            l_status = true;
                                        }
                                    } else {
                                        l_status = true;
                                    }
                                }
                                if (eH >= Integer.valueOf(timeArr[0])) {
                                    if (eH == Integer.valueOf(timeArr[0])) {
                                        if (eM < Integer.valueOf(timeArr[1])) {
                                            continue;
                                        } else {
                                            r_status = true;
                                        }
                                    } else {
                                        r_status = true;
                                    }
                                }
                                if (l_status && r_status) {
                                    isChecked = true;
                                } else {
                                    isChecked = false;
                                }
                            }
                        }
                    }
                }

                if (isChecked) {
                    isSchStatus = true;
                } else {
                    isSchStatus = false;
                }
            }
        }
    };

    void handleLocationChanged(Location loc) {
        lattitude = loc.getLatitude();
        longitude = loc.getLongitude();
        System.out.println("getting location continous ////// Lattti " + lattitude);
        System.out.println("getting location continous ////// LONGITU " + longitude);

        String days = sdf2.format(new Date());

        List<CTime> timeList = timeDB.getAllRows();
        boolean isChecked = false;
        if (timeList.size() > 0) {
            for (CTime ct : timeList) {
                if (days.equals(ct.getDays())) {
                    List<CRoom> rmList = roomDB.getAllRows();
                    CRoom target = null;
                    for (CRoom rm : rmList) {
                        if (rm.getLocationCd().equals(ct.getLocationCd())) {
                            target = rm;
                            break;
                        }
                    }
                    Log.d("rows", "lat : " + Double.valueOf(target.getLatitude()) + ", lon : " + Double.valueOf(target.getLongitude()));
                    Log.d("rows", calcDistance(lattitude, longitude, Double.valueOf(target.getLatitude()), Double.valueOf(target.getLongitude())));

                    if (Integer.valueOf(calcDistance(lattitude, longitude,
                            Double.valueOf(target.getLatitude()), Double.valueOf(target.getLongitude()))) < 1000) {
                        Log.d("rows", "in 1 Km");

                        String[] val = new String[2];
                        if (setDB.getRowCount() > 0) {
                            CSetting cs = setDB.getRow(1);
                            if (cs.getTimeStart() != null) {
                                val = cs.getTimeStart().split(":");
                            } else {
                                val[0] = "09";
                                val[1] = "00";
                            }
                        }

                        int sH, sM, eH, eM;
                        sH = Integer.valueOf(val[0]) + Integer.valueOf(ct.getClasses()) - 1;
                        sM = Integer.valueOf(val[1]);
                        eH = Integer.valueOf(val[0]) + Integer.valueOf(ct.getClasses()) - 1 + Integer.valueOf(ct.getLecture());
                        eM = Integer.valueOf(val[1]);

                        String today = sdf.format(new Date());
                        String nowTime = sdf3.format(new Date());
                        String[] timeArr = nowTime.split("-");

                        Log.d("rows", sH + ":" + sM + "~" + eH + ":" + eM + "//" + timeArr[0] + ":" + timeArr[1]);
                        boolean l_status = false, r_status = false;
                        if (sH <= Integer.valueOf(timeArr[0])) {
                            if (sH == Integer.valueOf(timeArr[0])) {
                                if (sM > Integer.valueOf(timeArr[1])) {
                                    continue;
                                } else {
                                    l_status = true;
                                }
                            } else {
                                l_status = true;
                            }
                        }

                        if (eH >= Integer.valueOf(timeArr[0])) {
                            if (eH == Integer.valueOf(timeArr[0])) {
                                if (eM < Integer.valueOf(timeArr[1])) {
                                    continue;
                                } else {
                                    r_status = true;
                                }
                            } else {
                                r_status = true;
                            }
                        }

                        if (l_status && r_status) {
                            isChecked = true;
                            break;
                        } else {
                            isChecked = false;
                        }
                    }
                }
            }
        }

        if (isChecked) {
            isTimeStatus = true;
        } else {
            isTimeStatus = false;
        }

    }

    public static String calcDistance(double lat1, double lon1, double lat2, double lon2) {
        double EARTH_R, Rad, radLat1, radLat2, radDist;
        double distance, ret;

        EARTH_R = 6371000.0;
        Rad = Math.PI / 180;
        radLat1 = Rad * lat1;
        radLat2 = Rad * lat2;
        radDist = Rad * (lon1 - lon2);

        distance = Math.sin(radLat1) * Math.sin(radLat2);
        distance = distance + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radDist);
        ret = EARTH_R * Math.acos(distance);

        String result = String.valueOf(Math.round(ret));

        return result;
    }

    public LocationListener mylistener = new LocationListener() {

        @Override
        public void onLocationChanged(Location loc) {
            handleLocationChanged(loc);
        }

        public void onProviderDisabled(String arg0) {}

        public void onProviderEnabled(String arg0) {}

        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
    };

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
        alertDialog.setTitle("Alert");

        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getApplicationContext().startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    public void sendNotification(String title, String text) {
        int MY_NOTIFICATION_ID = 1;
        PendingIntent contentIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, new Intent(TaskService.this, MainActivity.class), 0);
        Notification myNotification = new NotificationCompat.Builder(
                getApplicationContext())
                .setContentTitle(title)
                .setContentText(text)
                .setTicker("Don't Touch Me")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(contentIntent)
                .setDefaults(Notification.DEFAULT_SOUND).setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher).build();
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}