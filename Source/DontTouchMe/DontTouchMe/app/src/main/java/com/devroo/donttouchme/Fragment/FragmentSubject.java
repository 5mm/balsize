package com.devroo.donttouchme.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.devroo.donttouchme.Class.CSetting;
import com.devroo.donttouchme.DBHandler.DBSettingHandler;
import com.devroo.donttouchme.FourFragment;
import com.devroo.donttouchme.R;

import java.util.List;

public class FragmentSubject extends Fragment implements View.OnClickListener {

    static DBSettingHandler db = null;
    static EditText subjectDay;

    //Fragment의 View 작업을 하는 function
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subject_fragment, container, false);

        ImageButton bButton = (ImageButton) view.findViewById(R.id.bt_back);
        ImageButton sButton = (ImageButton) view.findViewById(R.id.bt_save);

        bButton.setOnClickListener(this);
        sButton.setOnClickListener(this);

        TextView topTitle = (TextView) view.findViewById(R.id.topTitle);
        topTitle.setText("D-Day 관리");

        db = new DBSettingHandler(getActivity());

        Log.d("Reading: ", "Reading all Rows..");
        List<CSetting> dates = db.getAllRows();

        CSetting cs = null;
        for (CSetting cn : dates) {
            cs = cn;
            String log = "Sn: " + cn.getSN() + " ,Subject: " + cn.getSubjectDays();
            // Writing Contacts to log
            Log.d("rows: ", log);
        }

        subjectDay = (EditText) view.findViewById(R.id.subject);

        if (db.getRowCount() < 1) {
            db.addEmptyRow(new CSetting());
        } else {
            subjectDay.setText(cs.getSubjectDays());
        }

        return view;
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
                CSetting cs = db.getRow(1);
                cs.setSubjectDays(subjectDay.getText().toString());
                db.updateRow(cs);

                Toast.makeText(getActivity(), "저장되었습니다.", Toast.LENGTH_LONG).show();
                break;
        }
        InputMethodManager mm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mm.hideSoftInputFromWindow(subjectDay.getWindowToken(), 0);
    }
}

