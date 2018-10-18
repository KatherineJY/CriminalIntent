package com.ghy.katherinejy.criminalintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CriminalIntentJSONSerializer {
    private Context mContext;
    private String mFilename;

    public CriminalIntentJSONSerializer(Context c,String file){
        mContext = c;
        mFilename = file;
    }

    public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;
        try{
            //open and read the file into a StringBuffer
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while( (line=reader.readLine())!=null ){
                jsonString.append(line);
            }
            //parse the json using jsontokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            //build the array of crimes from JSONObjects
            for(int i=0;i<array.length();i++){
                crimes.add(new Crime(array.getJSONObject(i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if( reader!=null ){
                reader.close();
            }
        }
        return crimes;
    }

    public void save(ArrayList<Crime> crimes) throws IOException, JSONException {
        JSONArray array = new JSONArray();
        for( Crime c : crimes ){
            array.put(c.toJSON());
        }

        Writer writer = null;
        try{
            OutputStream out = mContext.openFileOutput(mFilename,Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }
        finally {
            if(writer!=null){
                writer.close();
            }
        }
    }
}
