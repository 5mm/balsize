package com.devroo.donttouchme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devroo.donttouchme.BackgroundService.TaskService;
import com.devroo.donttouchme.Class.CDate;
import com.devroo.donttouchme.DBHandler.DBDateHandler;
import com.devroo.donttouchme.Fragment.FragmentRoom;

import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    int FRAGMENT_ONE = 1;
    int FRAGMENT_TWO = 2;
    int FRAGMENT_THREE = 3;
    int FRAGMENT_FOUR = 4;
    int FRAGMENT_MAP = 5;

    int mCurrentFragmentIndex = FRAGMENT_ONE;

    public static Context mContext;
    Intent it;
    public LocationManager manager;
    public boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DBDateHandler db = new DBDateHandler(this);
        List<CDate> cd = db.getAllRows();
        Intent intent = getIntent();
        String page = intent.getStringExtra("page");

        if (page != null) {
            if (page.equals("map")) {
                fragmentReplace(FRAGMENT_FOUR);
                fragmentReplace(FRAGMENT_MAP);
            } else if (page.equals("time")) {
                fragmentReplace(FRAGMENT_TWO);
            } else if (page.equals("sch")) {
                fragmentReplace(FRAGMENT_THREE);
            } else {
                fragmentReplace(FRAGMENT_ONE);
            }
        } else {
            startActivity(new Intent(this, SplashActivity.class));
        }
        ImageButton bt_oneFragment = (ImageButton) findViewById(R.id.bt_oneFragment);
        ImageButton bt_twoFragment = (ImageButton) findViewById(R.id.bt_twoFragment);
        ImageButton bt_threeFragment = (ImageButton) findViewById(R.id.bt_threeFragment);
        ImageButton bt_fourFragment = (ImageButton) findViewById(R.id.bt_fourFragment);

        bt_oneFragment.setOnClickListener(this);
        bt_twoFragment.setOnClickListener(this);
        bt_threeFragment.setOnClickListener(this);
        bt_fourFragment.setOnClickListener(this);

        if (page == null) {
            fragmentReplace(mCurrentFragmentIndex);
        }

        mContext = this;
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        startService(getIntentService());
    }

    public Intent getIntentService() {
        it = new Intent(mContext, TaskService.class);
        return it;
    } // method ends

    // fragment 영역에 fragment를 set 해주는 function
    public void fragmentReplace(int reqNewFragmentIndex) {
        Fragment newFragment = null;
        newFragment = getFragment(reqNewFragmentIndex);  // replace fragment

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.view_fragment, newFragment);
        transaction.commit();
    }

    // fragment의 index를 입력받아 해당 fragment 를 반환하는 function
    private Fragment getFragment(int idx) {
        Fragment newFragment = null;
        switch (idx) {
            case 1:
                newFragment = new OneFragment();
                break;
            case 2:
                newFragment = new TwoFragment();
                break;
            case 3:
                newFragment = new ThreeFragment();
                break;
            case 4:
                newFragment = new FourFragment();
                break;
            case 5:
                newFragment = new FragmentRoom();
                break;
        }
        setButtonBackgroundColor(idx);
        return newFragment;
    }

    public void setButtonBackgroundColor(int idx) {
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.lay1);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.lay2);
        LinearLayout layout3 = (LinearLayout) findViewById(R.id.lay3);
        LinearLayout layout4 = (LinearLayout) findViewById(R.id.lay4);

        ImageButton bt_oneFragment = (ImageButton) findViewById(R.id.bt_oneFragment);
        ImageButton bt_twoFragment = (ImageButton) findViewById(R.id.bt_twoFragment);
        ImageButton bt_threeFragment = (ImageButton) findViewById(R.id.bt_threeFragment);
        ImageButton bt_fourFragment = (ImageButton) findViewById(R.id.bt_fourFragment);

        TextView txt1 = (TextView) findViewById(R.id.txt1);
        TextView txt2 = (TextView) findViewById(R.id.txt2);
        TextView txt3 = (TextView) findViewById(R.id.txt3);
        TextView txt4 = (TextView) findViewById(R.id.txt4);

        if (idx == 5) idx = 4;

        layout1.setBackgroundColor(Color.rgb(62, 69, 77));
        layout2.setBackgroundColor(Color.rgb(62, 69, 77));
        layout3.setBackgroundColor(Color.rgb(62, 69, 77));
        layout4.setBackgroundColor(Color.rgb(62, 69, 77));

        bt_oneFragment.setBackgroundColor(Color.rgb(62, 69, 77));
        bt_twoFragment.setBackgroundColor(Color.rgb(62, 69, 77));
        bt_threeFragment.setBackgroundColor(Color.rgb(62, 69, 77));
        bt_fourFragment.setBackgroundColor(Color.rgb(62, 69, 77));

        txt1.setBackgroundColor(Color.rgb(62, 69, 77));
        txt2.setBackgroundColor(Color.rgb(62, 69, 77));
        txt3.setBackgroundColor(Color.rgb(62, 69, 77));
        txt4.setBackgroundColor(Color.rgb(62, 69, 77));
        switch (idx) {
            case 1:
                layout1.setBackgroundColor(Color.rgb(37, 41, 46));
                bt_oneFragment.setBackgroundColor(Color.rgb(37, 41, 46));
                txt1.setBackgroundColor(Color.rgb(37, 41, 46));
                break;
            case 2:
                layout2.setBackgroundColor(Color.rgb(37, 41, 46));
                bt_twoFragment.setBackgroundColor(Color.rgb(37, 41, 46));
                txt2.setBackgroundColor(Color.rgb(37, 41, 46));
                break;
            case 3:
                layout3.setBackgroundColor(Color.rgb(37, 41, 46));
                bt_threeFragment.setBackgroundColor(Color.rgb(37, 41, 46));
                txt3.setBackgroundColor(Color.rgb(37, 41, 46));
                break;
            case 4:
                layout4.setBackgroundColor(Color.rgb(37, 41, 46));
                bt_fourFragment.setBackgroundColor(Color.rgb(37, 41, 46));
                txt4.setBackgroundColor(Color.rgb(37, 41, 46));
                break;
        }
    }

    // ImageButton 클릭 시 index를 부여하여 fragmentReplace 호출
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_oneFragment:
                mCurrentFragmentIndex = FRAGMENT_ONE;
                fragmentReplace(mCurrentFragmentIndex);
                break;
            case R.id.bt_twoFragment:
                mCurrentFragmentIndex = FRAGMENT_TWO;
                fragmentReplace(mCurrentFragmentIndex);
                break;
            case R.id.bt_threeFragment:
                mCurrentFragmentIndex = FRAGMENT_THREE;
                fragmentReplace(mCurrentFragmentIndex);
                break;
            case R.id.bt_fourFragment:
                mCurrentFragmentIndex = FRAGMENT_FOUR;
                fragmentReplace(mCurrentFragmentIndex);
                break;
        }
    }

    // 앱이 다시 시작되었을 떄 실행되는 리스너
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    // 앱이 중지되었을 때 실행되는 리스너
    @Override
    protected void onStop() {
        super.onStop();
    }
}
