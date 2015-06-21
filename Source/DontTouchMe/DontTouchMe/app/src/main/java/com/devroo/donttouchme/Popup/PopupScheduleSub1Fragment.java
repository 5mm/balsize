package com.devroo.donttouchme.Popup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devroo.donttouchme.R;

public class PopupScheduleSub1Fragment extends Fragment {

    //Fragment의 View 작업을 하는 function
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_schedule_sub1, container, false);

        return view;
    }
}

