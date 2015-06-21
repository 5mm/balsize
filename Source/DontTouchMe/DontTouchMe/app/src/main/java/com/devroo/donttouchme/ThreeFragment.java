package com.devroo.donttouchme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.devroo.donttouchme.Calendar.CaldroidFragment;
import com.devroo.donttouchme.Calendar.CaldroidListener;
import com.devroo.donttouchme.Class.CCalendar;
import com.devroo.donttouchme.DBHandler.DBScheduleHandler;
import com.devroo.donttouchme.Fragment.FragmentCalendar;
import com.devroo.donttouchme.Popup.PopupSchedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ThreeFragment extends Fragment implements View.OnClickListener {

    private boolean undo = false;
    private CaldroidFragment caldroidFragment;

    static DBScheduleHandler db = null;

    //Fragment의 View 작업을 하는 function
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.three_fragment, container, false);

        ImageButton button = (ImageButton) view.findViewById(R.id.bt_add);
        button.setOnClickListener(this);

        TextView topTitle = (TextView) view.findViewById(R.id.topTitle);
        topTitle.setText("캘린더");

        db = new DBScheduleHandler(getActivity());
        Log.d("Reading: ", "Reading all Rows..");
        List<CCalendar> dates = db.getAllRows();

        for (CCalendar cn : dates) {
            String log = "Sn: " + cn.getSN() + " ,typeCd: " + cn.getTypeCd() + " ,typeNm: " + cn.getTypeNm() + " ,vDtm: " + cn.getVDtm()
                    + " ,title: " + cn.getTitle() + " ,subject: " + cn.getSubject() + " ,compYn: " + cn.getCompYn()
                    + " ,outYn: " + cn.getOutYn() + " ,sTime: " + cn.getSTime() + " ,eTime: " + cn.getETime() + " ,contents: " + cn.getContents();
            // Writing Contacts to log
            Log.d("rows: ", log);
        }

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        caldroidFragment = new CaldroidFragment();

        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        } else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            args.putInt(CaldroidFragment.THEME_RESOURCE, R.style.CaldroidDefault);

            caldroidFragment.setArguments(args);
        }

        setCustomResourceForDates();

        // Attach to the activity
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Fragment newFragment = new FragmentCalendar();

                Bundle bundle = new Bundle();
                bundle.putString("date", format.format(date));
                newFragment.setArguments(bundle);

                final FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.view_fragment, newFragment);
                transaction.commit();
            }

            @Override
            public void onChangeMonth(int month, int year) {
            }

            @Override
            public void onLongClickDate(Date date, View view) {
            }

            @Override
            public void onCaldroidViewCreated() {
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

        return view;
    }

    private void setCustomResourceForDates() {
        List<CCalendar> calList = db.getAllRows();

        for (CCalendar cn : calList) {
            if (caldroidFragment != null) {
                Calendar cal = Calendar.getInstance();
                String[] day = cn.getVDtm().split("-");

                cal.set(Calendar.YEAR, Integer.valueOf( day[0] ));
                cal.set(Calendar.MONTH, Integer.valueOf( day[1] ) - 1);
                cal.set(Calendar.DATE, Integer.valueOf( day[2] ));

                switch ( db.getTypeList(cn.getVDtm()) ) {
                    case "Both":
                        caldroidFragment.setBackgroundResourceForDate(R.color.event_color_01, cal.getTime());
                        caldroidFragment.setTextColorForDate(R.color.white, cal.getTime());
                        break;
                    case "Subj":
                        caldroidFragment.setBackgroundResourceForDate(R.color.event_color_03, cal.getTime());
                        caldroidFragment.setTextColorForDate(R.color.white, cal.getTime());
                        break;
                    case "Sch":
                        caldroidFragment.setBackgroundResourceForDate(R.color.event_color_04, cal.getTime());
                        caldroidFragment.setTextColorForDate(R.color.white, cal.getTime());
                        break;
                    case "None":
                        break;
                }
            }

        }
    }

    //ImageButton 클릭 시 fragmentReplace 호출
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                Intent intent = new Intent(getActivity(), PopupSchedule.class);
                intent.setFlags(intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
        }
    }
}

