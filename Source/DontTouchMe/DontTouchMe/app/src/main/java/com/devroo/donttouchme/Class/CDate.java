package com.devroo.donttouchme.Class;

public class CDate {

    //private variables
    String _S_TIME;
    String _E_TIME;
    String _V_Date;
    String _STATUS;
    String _REMARK;
    String _REG_ID;
    String _REG_DTM;
    String _MOD_ID;
    String _MOD_DTM;
    int _SN;

    // Empty constructor
    public CDate(){

    }
    // constructor
    public CDate(int sn, String sTime, String eTime, String vDate){
        this._SN = sn;
        this._S_TIME = sTime;
        this._E_TIME = eTime;
        this._V_Date = vDate;
    }

    // constructor
    public CDate(String sTime, String eTime, String vDate){
        this._S_TIME = sTime;
        this._E_TIME = eTime;
        this._V_Date = vDate;
    }

    // _SN
    public int getSN(){
        return this._SN;
    }
    public void setSN(int sn){
        this._SN = sn;
    }

    // _S_Time
    public String getSTime(){
        return this._S_TIME;
    }
    public void setSTime(String sTime){
        this._S_TIME = sTime;
    }

    // _E_Time
    public String getETime(){
        return this._E_TIME;
    }
    public void setETime(String eTime){
        this._E_TIME = eTime;
    }

    // _V_Date
    public String getVDate(){
        return this._V_Date;
    }
    public void setVDate(String vDate){
        this._V_Date = vDate;
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
