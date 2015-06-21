package com.devroo.donttouchme.Popup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devroo.donttouchme.Class.CDate;
import com.devroo.donttouchme.Component.DateTime;
import com.devroo.donttouchme.Component.DateTimePicker;
import com.devroo.donttouchme.Component.SimpleDateTimePicker;
import com.devroo.donttouchme.DBHandler.DBDateHandler;
import com.devroo.donttouchme.MainActivity;
import com.devroo.donttouchme.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PopupDate extends FragmentActivity implements View.OnClickListener, DateTimePicker.OnDateTimeSetListener {

    SimpleDateTimePicker simpleDateTimePicker;
    static String stype = null;
    static EditText sDate;
    static EditText eDate;

    static Button bt_ok;
    static Button bt_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_date);

        bt_ok = (Button) findViewById(R.id.bt_ok);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);

        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);

        sDate = (EditText) findViewById(R.id.sDate);
        eDate = (EditText) findViewById(R.id.eDate);

        sDate.setInputType(0);
        eDate.setInputType(0);

        sDate.setOnClickListener(this);
        eDate.setOnClickListener(this);
    }

    // Theme 적용함수
    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);

        theme.applyStyle(android.R.style.Theme_Panel, true);
    }

    // Button Click Event
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ok:
                final String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                final EditText sDate = (EditText) findViewById(R.id.sDate);
                final EditText eDate = (EditText) findViewById(R.id.eDate);

                if ( (sDate.getText() != null && sDate.getText().length() != 0)
                      && (eDate.getText() != null && eDate.getText().length() != 0) )
                {
                    int sH, sM, eH, eM;
                    sH = Integer.valueOf(sDate.getText().toString().split(":")[0]);
                    sM = Integer.valueOf(sDate.getText().toString().split(":")[1]);
                    eH = Integer.valueOf(eDate.getText().toString().split(":")[0]);
                    eM = Integer.valueOf(eDate.getText().toString().split(":")[1]);

                    int total = (eH-sH) * 60 + (eM-sM);
                    if (total < 0) {
                        Toast.makeText(this, "종료시간이 시작시간보다 빠를 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);

                        builder.setTitle("시간확인")        // 제목 설정
                                .setMessage(sDate.getText() + " ~ " + eDate.getText() + " 까지 설정하시겠습니까?")        // 메세지 설정
                                .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int whichButton){

                                        DBDateHandler db = new DBDateHandler(getApplicationContext());
                                        db.addRow(new CDate(sDate.getText().toString(), eDate.getText().toString(), today));

                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.putExtra("page", "date");
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                } else {
                    Toast.makeText(this, "날짜를 입력해주세요.", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.bt_cancel:
                finish();
                break;
            case R.id.sDate:
                simpleDateTimePicker = SimpleDateTimePicker.make(
                        "Set Time Title",
                        new Date(),
                        this,
                        getSupportFragmentManager()
                );
                simpleDateTimePicker.show();
                stype = "sDate";
                break;
            case R.id.eDate:
                simpleDateTimePicker = SimpleDateTimePicker.make(
                        "Set Time Title",
                        new Date(),
                        this,
                        getSupportFragmentManager()
                );
                simpleDateTimePicker.show();
                stype = "eDate";
                break;
        }
    }

    @Override
    public void DateTimeSet(Date date) {
        DateTime mDateTime = new DateTime(date);
        if (stype != null) {
            switch (stype) {
                case "sDate":
                    sDate.setText(mDateTime.getDateString("HH:mm"));
                    break;
                case "eDate":
                    eDate.setText(mDateTime.getDateString("HH:mm"));
                    break;
            }
        }
    }
}
