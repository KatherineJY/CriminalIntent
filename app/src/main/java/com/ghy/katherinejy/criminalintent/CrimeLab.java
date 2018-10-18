package com.ghy.katherinejy.criminalintent;

import android.content.Context;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    private ArrayList<Crime> mCrimes;
    private CriminalIntentJSONSerializer mSerializer;
    private static final String filename = "aaa.txt";

    private CrimeLab(Context appContext){
        mAppContext = appContext;
        mSerializer = new CriminalIntentJSONSerializer(mAppContext,filename);

        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (Exception e) {
            e.printStackTrace();
            mCrimes = new ArrayList<Crime>();
            /*
            for(int i=0;i<100;i++) {
                Crime c = new Crime();
                c.setTitle("Crime #" + i);
                c.setSolved(i % 2 == 0);
                mCrimes.add(c);
            }
            */
        }
    }

    public static CrimeLab get(Context c){
        if (sCrimeLab==null){
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getmCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for(Crime c:mCrimes){
            if(c.getId().equals(id))
                return c;
        }
        return null;
    }

    public void addCrime(Crime crime){
        mCrimes.add(crime);
    }

    public boolean saveCrimes() throws IOException {
        try {
            mSerializer.save(mCrimes);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
