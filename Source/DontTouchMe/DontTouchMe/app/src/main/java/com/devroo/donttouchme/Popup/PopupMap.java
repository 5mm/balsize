package com.devroo.donttouchme.Popup;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devroo.donttouchme.Class.CRoom;
import com.devroo.donttouchme.DBHandler.DBRoomHandler;
import com.devroo.donttouchme.MainActivity;
import com.devroo.donttouchme.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class PopupMap extends FragmentActivity implements View.OnClickListener {

    static final LatLng SEOUL = new LatLng(37.56, 126.97);
    private GoogleMap mMap;
    static LatLng lastPos = null;
    static EditText mapName;
    static DBRoomHandler roomDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_map);

        Button bt_ok = (Button) findViewById(R.id.bt_ok);
        Button bt_cancel = (Button) findViewById(R.id.bt_cancel);

        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);

        roomDB = new DBRoomHandler(this);
        mapName = (EditText) findViewById(R.id.mapName);

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    private void setUpMap() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 14));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Here"));
                lastPos = latLng;
            }
        });
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
                if (mapName.getText() != null && mapName.getText().length() != 0) {
                    List<CRoom> roomList = roomDB.getAllRows();

                    boolean isChecked = false;
                    for (CRoom rm : roomList) {
                        if (rm.getLocationNm() != null && rm.getLocationNm().equals(mapName.getText().toString())) {
                            isChecked = true;
                            break;
                        }
                    }

                    if (isChecked) {
                        Toast.makeText(this, "이름이 중복되었습니다. 다시 입력해주세요.", Toast.LENGTH_LONG).show();
                    } else {
                        if (lastPos != null) {
                            String code = "Room" + roomList.size() + 1;
                            CRoom newRoom = new CRoom(code, mapName.getText().toString(), String.valueOf(lastPos.latitude), String.valueOf(lastPos.longitude));
                            roomDB.addRow(newRoom);

                            Intent intent = new Intent(this, MainActivity.class);
                            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("page", "map");
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "지도에 마커를 지정해주세요.", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.bt_cancel:
                finish();
                break;
        }
    }
}
