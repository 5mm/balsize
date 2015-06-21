package com.devroo.donttouchme.Fragment;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.devroo.donttouchme.Adapter.AdapterApp;
import com.devroo.donttouchme.Adapter.AdapterSetting;
import com.devroo.donttouchme.Class.CApp;
import com.devroo.donttouchme.Class.CSetting;
import com.devroo.donttouchme.DBHandler.DBApplicationHandler;
import com.devroo.donttouchme.DBHandler.DBSettingHandler;
import com.devroo.donttouchme.FourFragment;
import com.devroo.donttouchme.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentApplication extends Fragment implements View.OnClickListener {

    static DBSettingHandler db = null;
    static DBApplicationHandler appDB = null;
    static ArrayList<PInfo> pg;
    static ListView lv1;
    static View initView;
    static List<CApp> apps;
    static boolean isAll = false;

    //Fragment의 View 작업을 하는 function
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView = inflater.inflate(R.layout.application_fragment, container, false);

        ImageButton bButton = (ImageButton) initView.findViewById(R.id.bt_back);
        Button sButton = (Button) initView.findViewById(R.id.bt_save);

        bButton.setOnClickListener(this);
        sButton.setOnClickListener(this);

        TextView topTitle = (TextView) initView.findViewById(R.id.topTitle);
        topTitle.setText("차단앱 관리");

        db = new DBSettingHandler(getActivity());
        appDB = new DBApplicationHandler(getActivity());

        Log.d("Reading: ", "Reading all Rows..");
        List<CSetting> dates = db.getAllRows();

        CSetting cs = null;
        for (CSetting cn : dates) {
            cs = cn;
            String log = "Sn: " + cn.getSN() + " ,appYn: " + cn.getAppOutYn();
            Log.d("rows: ", log);
        }

        Switch appSwitch = (Switch) initView.findViewById(R.id.appSwitch);
        appSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("rows", "onCheckedChanged : " + (isChecked));
                if (isChecked) {
                    initListview();
                } else {
                    lv1.setAdapter(null);
                }

                CSetting cs = db.getRow(1);
                cs.setAppOutYn((isChecked)? "Y" : "N");
                db.updateRow(cs);
            }
        });

        Log.d("Reading: ", "Reading all Rows..");

        apps = appDB.getAllRows();
        CApp ap = null;
        for (CApp cn : apps) {
            ap = cn;
            String log = "Sn: " + cn.getSN() + " ,appId: " + cn.getAppId() + " ,appNm: " + cn.getAppNm();
            Log.d("rows: ", log);
        }

        if (db.getRowCount() < 1) {
            db.addEmptyRow(new CSetting());
        } else {
            appSwitch.setChecked((cs.getAppOutYn() != null && cs.getAppOutYn().equals("Y")? true : false));
            if (cs.getAppOutYn() != null && cs.getAppOutYn().equals("Y")) {
                initListview();
            }
        }

//        lv1.setCacheColorHint(Color.TRANSPARENT);
//        lv1.requestFocus(0);

        return initView;
    }

    private void initListview() {
        apps = appDB.getAllRows();
        ArrayList image_details = getListData();
        lv1 = (ListView) initView.findViewById(R.id.listView);
        lv1.setAdapter(new AdapterApp(getActivity(), image_details, apps));
        lv1.setOnItemClickListener(new ListViewItemClickListener());
    }

    private ArrayList getListData() {
        ArrayList<String> results = new ArrayList<String>();

        pg = getPackages();
        for (PInfo item : pg) {
            results.add(item.appname);
        }

        return results;
    }

    class PInfo {
        private String appname = "";
        private String pname = "";
        private String versionName = "";
        private int versionCode = 0;
        private Drawable icon;
        private void prettyPrint() {
            Log.d("rows", appname + "\t" + pname + "\t" + versionName + "\t" + versionCode);
        }
    }

    private ArrayList<PInfo> getPackages() {
        ArrayList<PInfo> apps = getInstalledApps(false); /* false = no system packages */
//        final int max = apps.size();
//        for (int i=0; i<max; i++) {
//            apps.get(i).prettyPrint();
//        }
        return apps;
    }

    private ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
        ArrayList<PInfo> res = new ArrayList<PInfo>();
        List<PackageInfo> packs = getActivity().getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);

        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue ;
            }
            if (isSystemPackage(p)) {
                continue;
            }
            if (p.packageName.equals("com.devroo.donttouchme")) {
                continue;
            }
            PInfo newInfo = new PInfo();
            newInfo.appname = p.applicationInfo.loadLabel(getActivity().getPackageManager()).toString();
            newInfo.pname = p.packageName;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            newInfo.icon = p.applicationInfo.loadIcon(getActivity().getPackageManager());
            res.add(newInfo);
        }
        return res;
    }
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }
    //ImageButton 클릭 시 fragmentReplace 호출
    @Override
    public void onClick(View v) {
        Fragment newFragment = null;
        FragmentManager fragmentManager = null;
        FragmentTransaction transaction = null;

        switch (v.getId()) {
            case R.id.bt_back:
                newFragment = new FourFragment();
                fragmentManager = getFragmentManager();

                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.view_fragment, newFragment);
                transaction.commit();
                break;
            case R.id.bt_save:
                if (isAll) {
                    List<CApp> apps = appDB.getAllRows();
                    for (CApp cn : apps) {
                        appDB.deleteRow(cn);
                    }
//                    for (int i = 0; i < pg.size(); i++) {
//                        getViewByPosition(i, lv1).setBackgroundColor(Color.TRANSPARENT);
//                    }
                    initListview();
                    Toast.makeText(getActivity(), "모든 어플을 차단목록에서 해제하였습니다.", Toast.LENGTH_LONG).show();
                } else {
                    List<CApp> apps = appDB.getAllRows();
                    for (CApp cn : apps) {
                        appDB.deleteRow(cn);
                    }
                    for (int i = 0; i < pg.size(); i++) {
                        CApp app = new CApp();
                        app.setAppId(pg.get(i).pname);
                        app.setAppNm(pg.get(i).appname);
                        appDB.addRow(app);
//                        getViewByPosition(i, lv1).setBackgroundColor(Color.rgb(255, 59, 59));
                    }

                    initListview();
                    Toast.makeText(getActivity(), "모든 어플을 차단목록에 추가하였습니다.", Toast.LENGTH_LONG).show();
                }
                isAll = !isAll;
                break;
        }
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    // InnerClass Listener방식으로 구현
    private class ListViewItemClickListener implements android.widget.AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            boolean isChecked = false;
            CApp delApp = null;
            List<CApp> apps = appDB.getAllRows();
            for (CApp cn : apps) {
                for (int i = 0; i < pg.size(); i++) {
                    if (cn.getAppId() != null && cn.getAppId().equals(pg.get(position).pname)) {
                        isChecked = true;
                        delApp = cn;
                        break;
                    }
                }
            }

            if (isChecked) {
                if (delApp != null) {
                    appDB.deleteRow(delApp);
                }
//                view.setBackgroundColor(Color.TRANSPARENT);
                initListview();

                Toast.makeText(getActivity(), delApp.getAppNm() + "을 차단목록에서 해제하였습니다.", Toast.LENGTH_LONG).show();
            } else {
                CApp app = new CApp();
                app.setAppId(pg.get(position).pname);
                app.setAppNm(pg.get(position).appname);
                appDB.addRow(app);

//                view.setBackgroundColor(Color.rgb(255, 59, 59));
                initListview();

                Toast.makeText(getActivity(), pg.get(position).appname + "을 차단목록에 추가하였습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}

