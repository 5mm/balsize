package com.devroo.donttouchme.Class;

public class CCalendar {

    //private variables
    String _TYPE_CD;
    String _TYPE_NM;
    String _V_DTM;
    String _TITLE;
    String _SUBJECT;
    String _COMP_YN;
    String _OUT_YN;
    String _S_TIME;
    String _E_TIME;
    String _CONTENTS;
    String _STATUS;
    String _REMARK;
    String _REG_ID;
    String _REG_DTM;
    String _MOD_ID;
    String _MOD_DTM;
    int _SN;

    // Empty constructor
    public CCalendar(){

    }
    // constructor
    public CCalendar(int sn, String typeCd, String typeNm, String vDtm, String title, String subject, String compYn, String outYn,
                 String sTime, String eTime, String contents){
        this._SN = sn;
        this._TYPE_CD = typeCd;
        this._TYPE_NM = typeNm;
        this._V_DTM = vDtm;
        this._TITLE = title;
        this._SUBJECT = subject;
        this._COMP_YN = compYn;
        this._OUT_YN = outYn;
        this._S_TIME = sTime;
        this._E_TIME = eTime;
        this._CONTENTS = contents;
    }

    // constructor
    public CCalendar(String typeCd, String typeNm, String vDtm, String title, String subject, String compYn, String outYn,
                     String sTime, String eTime, String contents){
        this._TYPE_CD = typeCd;
        this._TYPE_NM = typeNm;
        this._V_DTM = vDtm;
        this._TITLE = title;
        this._SUBJECT = subject;
        this._COMP_YN = compYn;
        this._OUT_YN = outYn;
        this._S_TIME = sTime;
        this._E_TIME = eTime;
        this._CONTENTS = contents;
    }

    // _SN
    public int getSN(){
        return this._SN;
    }
    public void setSN(int sn){
        this._SN = sn;
    }

    // _TYPE_CD
    public String getTypeCd(){
        return this._TYPE_CD;
    }
    public void setTypeCd(String typeCd){
        this._TYPE_CD = typeCd;
    }

    // _TYPE_NM
    public String getTypeNm(){
        return this._TYPE_NM;
    }
    public void setTypeNm(String typeNm){
        this._TYPE_NM = typeNm;
    }

    // _V_DTM
    public String getVDtm(){
        return this._V_DTM;
    }
    public void setVDtm(String vDtm){
        this._V_DTM = vDtm;
    }

    // _TITLE
    public String getTitle(){
        return this._TITLE;
    }
    public void setTitle(String title){
        this._TITLE = title;
    }

    // _SUBJECT
    public String getSubject(){
        return this._SUBJECT;
    }
    public void setSubject(String subject){
        this._SUBJECT = subject;
    }

    // _COMP_YN
    public String getCompYn(){
        return this._COMP_YN;
    }
    public void setCompYn(String compYn){
        this._COMP_YN = compYn;
    }

    // _OUT_YN
    public String getOutYn(){
        return this._OUT_YN;
    }
    public void setOutYn(String outYn){
        this._OUT_YN = outYn;
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

    // _CONTENTS
    public String getContents(){
        return this._CONTENTS;
    }
    public void setContents(String contents){
        this._CONTENTS = contents;
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
