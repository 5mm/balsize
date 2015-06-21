package com.devroo.donttouchme.Class;

public class CTime {

    //private variables
    String _DAYS;
    String _CLASS;
    String _LECTURE;
    String _COLOR;
    String _TITLE;
    String _PROFESSOR;
    String _ROOM;
    String _LOCATION_CD;
    String _LOCATION_NM;
    String _STATUS;
    String _REMARK;
    String _REG_ID;
    String _REG_DTM;
    String _MOD_ID;
    String _MOD_DTM;
    int _SN;

    // Empty constructor
    public CTime(){

    }
    // constructor
    public CTime(int sn, String days, String classes, String lecture, String color, String title, String professor, String room,
                 String locationCd, String locationNm){
        this._SN = sn;
        this._DAYS = days;
        this._CLASS = classes;
        this._LECTURE = lecture;
        this._COLOR = color;
        this._TITLE = title;
        this._PROFESSOR = professor;
        this._ROOM = room;
        this._LOCATION_CD = locationCd;
        this._LOCATION_NM = locationNm;
    }

    // constructor
    public CTime(String days, String classes, String lecture, String color, String title, String professor, String room,
                 String locationCd, String locationNm){
        this._DAYS = days;
        this._CLASS = classes;
        this._LECTURE = lecture;
        this._COLOR = color;
        this._TITLE = title;
        this._PROFESSOR = professor;
        this._ROOM = room;
        this._LOCATION_CD = locationCd;
        this._LOCATION_NM = locationNm;
    }

    // _SN
    public int getSN(){
        return this._SN;
    }
    public void setSN(int sn){
        this._SN = sn;
    }

    // _DAYS
    public String getDays(){
        return this._DAYS;
    }
    public void setDays(String days){
        this._DAYS = days;
    }

    // _CLASS
    public String getClasses(){
        return this._CLASS;
    }
    public void setClasses(String classes){
        this._CLASS = classes;
    }

    // _LECTURE
    public String getLecture(){
        return this._LECTURE;
    }
    public void setLectrue(String lecture){
        this._LECTURE = lecture;
    }

    // _COLOR
    public String getColor(){
        return this._COLOR;
    }
    public void setColor(String color){
        this._COLOR = color;
    }

    // _TITLE
    public String getTitle(){
        return this._TITLE;
    }
    public void setTitle(String title){
        this._TITLE = title;
    }

    // _PROFESSOR
    public String getProfessor(){
        return this._PROFESSOR;
    }
    public void setProfessor(String professor){
        this._PROFESSOR = professor;
    }

    // _ROOM
    public String getRoom(){
        return this._ROOM;
    }
    public void setRoom(String room){
        this._ROOM = room;
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
