package com.devroo.donttouchme.Class;

public class CRoom {

    //private variables
    String _LOCATION_CD;
    String _LOCATION_NM;
    String _LATITUDE;
    String _LONGITUDE;
    String _STATUS;
    String _REMARK;
    String _REG_ID;
    String _REG_DTM;
    String _MOD_ID;
    String _MOD_DTM;
    int _SN;

    // Empty constructor
    public CRoom(){

    }
    // constructor
    public CRoom(int sn, String locationCd, String locationNm, String latitude, String longitude){
        this._SN = sn;
        this._LOCATION_CD = locationCd;
        this._LOCATION_NM = locationNm;
        this._LATITUDE = latitude;
        this._LONGITUDE = longitude;
    }

    // constructor
    public CRoom(String locationCd, String locationNm, String latitude, String longitude){
        this._LOCATION_CD = locationCd;
        this._LOCATION_NM = locationNm;
        this._LATITUDE = latitude;
        this._LONGITUDE = longitude;
    }

    // _SN
    public int getSN(){
        return this._SN;
    }
    public void setSN(int sn){
        this._SN = sn;
    }

    // _LOCATION_CD
    public String getLocationCd(){
        return this._LOCATION_CD;
    }
    public void setLocationCd(String locationCd){
        this._LOCATION_CD = locationCd;
    }

    // _LOCATION_NM
    public String getLocationNm(){
        return this._LOCATION_NM;
    }
    public void setLocationNm(String locationNm){
        this._LOCATION_NM = locationNm;
    }

    // _LATITUDE
    public String getLatitude(){
        return this._LATITUDE;
    }
    public void setLatitude(String latitude){
        this._LATITUDE = latitude;
    }

    // _LONGITUDE
    public String getLongitude(){
        return this._LONGITUDE;
    }
    public void setLongitude(String longitude){
        this._LONGITUDE = longitude;
    }

    // _STATUS
    public String getStatus(){
        return this._STATUS;
    }
    public void setStatus(String status){
        this._STATUS = status;
    }

    // _REMARK
    public String getRemark(){
        return this._REMARK;
    }
    public void setRemark(String remark){
        this._REMARK = remark;
    }

    // _REG_ID
    public String getRegId(){
        return this._REG_ID;
    }
    public void setRegId(String regId){
        this._REG_ID = regId;
    }

    // _REG_DTM
    public String getRegDtm(){
        return this._REG_DTM;
    }
    public void setRegDtm(String regDtm){
        this._REG_DTM = regDtm;
    }

    // _MOD_ID
    public String getModId(){
        return this._MOD_ID;
    }
    public void setModId(String modId){
        this._MOD_ID = modId;
    }

    // _MOD_DTM
    public String getModDtm(){
        return this._MOD_DTM;
    }
    public void setModDtm(String modDtm){
        this._MOD_DTM = modDtm;
    }
}
