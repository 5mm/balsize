package com.devroo.donttouchme.Class;

public class CSetting {

    //private variables
    String _ALARM;
    String _ALARM_URI;
    String _APP_OUT_YN;
    String _SUBJECT_DAYS;
    String _TIME_START;
    String _TIME_LECTURE;
    String _TIME_REST;
    String _REG_ID;
    String _REG_DTM;
    String _MOD_ID;
    String _MOD_DTM;
    int _SN;

    // Empty constructor
    public CSetting(){

    }
    public CSetting(int sn, String alarm, String alarmUri) {
        this._SN = sn;
        this._ALARM = alarm;
        this._ALARM_URI = alarmUri;
    }

    public CSetting(String type, int sn, String value) {
        if (type.toLowerCase().equals("subject")) {
            this._SN = sn;
            this._SUBJECT_DAYS = value;
        } else {
            this._SN = sn;
            this._APP_OUT_YN = value;
        }
    }

    public CSetting(int sn, String timeStart, String timeLecture, String timeRest) {
        this._SN = sn;
        this._TIME_START = timeStart;
        this._TIME_LECTURE = timeLecture;
        this._TIME_REST = timeRest;
    }

    // constructor
    public CSetting(int sn, String alarm, String alarmUri, String appOutYn, String subjectDays, String timeStart, String timeLecture, String timeRest){
        this._SN = sn;
        this._ALARM = alarm;
        this._ALARM_URI = alarmUri;
        this._APP_OUT_YN = appOutYn;
        this._SUBJECT_DAYS = subjectDays;
        this._TIME_START = timeStart;
        this._TIME_LECTURE = timeLecture;
        this._TIME_REST = timeRest;
    }

    // constructor
    public CSetting(String alarm, String alarmUri, String appOutYn, String subjectDays, String timeStart, String timeLecture, String timeRest){
        this._ALARM = alarm;
        this._ALARM_URI = alarmUri;
        this._APP_OUT_YN = appOutYn;
        this._SUBJECT_DAYS = subjectDays;
        this._TIME_START = timeStart;
        this._TIME_LECTURE = timeLecture;
        this._TIME_REST = timeRest;
    }

    // _SN
    public int getSN(){
        return this._SN;
    }
    public void setSN(int sn){
        this._SN = sn;
    }

    // _ALARM
    public String getAlarm(){
        return this._ALARM;
    }
    public void setAlarm(String alarm){
        this._ALARM = alarm;
    }

    // _ALARM_URI
    public String getAlarmUri(){
        return this._ALARM_URI;
    }
    public void setAlarmUri(String alarmUri){
        this._ALARM_URI = alarmUri;
    }

    // _APP_OUT_YN
    public String getAppOutYn(){
        return this._APP_OUT_YN;
    }
    public void setAppOutYn(String appOutYn){
        this._APP_OUT_YN = appOutYn;
    }

    // _SUBJECT_DAYS
    public String getSubjectDays(){
        return this._SUBJECT_DAYS;
    }
    public void setSubjectDays(String subjectDays){
        this._SUBJECT_DAYS = subjectDays;
    }

    // _TIME_START
    public String getTimeStart(){
        return this._TIME_START;
    }
    public void setTimeStart(String timeStart){
        this._TIME_START = timeStart;
    }

    // _TIME_LECTURE
    public String getTimeLecture(){
        return this._TIME_LECTURE;
    }
    public void setTimeLecture(String lecture){
        this._TIME_LECTURE = lecture;
    }

    // _TIME_REST
    public String getTimeRest(){
        return this._TIME_REST;
    }
    public void setTimeRest(String timeRest){
        this._TIME_REST = timeRest;
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
