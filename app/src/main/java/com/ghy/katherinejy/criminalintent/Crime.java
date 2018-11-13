package com.ghy.katherinejy.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mSuspect;

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE    = "date";
    private static final String JSON_SUSPECT = "suspect";

    public Crime(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Crime(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        if( json.has(JSON_TITLE) ){
            mTitle = json.getString(JSON_TITLE);
        }
        if( json.has(JSON_SUSPECT) ) {
            mSuspect = json.getString(JSON_SUSPECT);
        }
        mSolved = json.getBoolean(JSON_SOLVED);
        mDate = new Date(json.getString(JSON_DATE));
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID,mId.toString());
        json.put(JSON_TITLE,mTitle);
        json.put(JSON_SOLVED,mSolved);
        json.put(JSON_DATE,mDate.getTime());
        json.put(JSON_SUSPECT,mSuspect);
        return json;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Date getDate() {
        return mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public String getmSuspect() {
        return mSuspect;
    }

    public void setmSuspect(String mSuspect) {
        this.mSuspect = mSuspect;
    }

    @Override
    public String toString(){
        return mTitle;
    }
}
