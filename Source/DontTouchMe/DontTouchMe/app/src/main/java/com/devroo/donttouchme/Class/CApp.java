package com.devroo.donttouchme.Class;

public class CApp {

    //private variables
    String _APP_ID;
    String _APP_NM;
    String _REG_ID;
    String _REG_DTM;
    String _MOD_ID;
    String _MOD_DTM;
    int _SN;

    // Empty constructor
    public CApp(){

    }
    // constructor
    public CApp(int sn, String appId, String appNm){
        this._SN = sn;
        this._APP_ID = appId;
        this._APP_NM = appNm;
    }

    // constructor
    public CApp(String appId, String appNm){
        this._APP_ID = appId;
        this._APP_NM = appNm;
    }

    // _SN
    public int getSN(){
        return this._SN;
    }
    public void setSN(int sn){
        this._SN = sn;
    }

    // _APP_ID
    public String getAppId(){
        return this._APP_ID;
    }
    public void setAppId(String appId){
        this._APP_ID = appId;
    }

    // _APP_NM
    public String getAppNm(){
        return this._APP_NM;
    }
    public void setAppNm(String appNm){
        this._APP_NM = appNm;
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
