package com.bignerdranch.android.criminalintent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

public class CriminalIntentJSONSerializer {
	
	private Context mContext;
	private String mFilename;
	public boolean mExternalStorageAvailiable = false;
	public boolean mExternalStorageWriteable = false;
	public boolean mExternalStorageEmulated = false;
	
	public CriminalIntentJSONSerializer(Context c, String f){
		mContext = c;
		mFilename = f;
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void checkExternalMedia(){
		String state = Environment.getExternalStorageState();
		mExternalStorageEmulated = Environment.isExternalStorageEmulated();
		if(Environment.MEDIA_MOUNTED.equals(state)){
			mExternalStorageAvailiable = mExternalStorageWriteable = true;
		}else if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
			mExternalStorageAvailiable = true;
			mExternalStorageWriteable = false;
		}else{
			mExternalStorageAvailiable = mExternalStorageWriteable = false;
		}
		Log.d("JSON","the value of mExternalStorageAvailiable is "+mExternalStorageAvailiable);
		Log.d("JSON","the value of mExternalStorageWritable is "+mExternalStorageWriteable);
	}
	
	public ArrayList<Crime> loadCrimes() throws IOException, JSONException{
		ArrayList<Crime> crimes = new ArrayList<Crime>();
		BufferedReader reader = null;
		checkExternalMedia();
		if(mExternalStorageAvailiable && mExternalStorageWriteable && !mExternalStorageEmulated){
			try{
				File root = Environment.getExternalStorageDirectory();
				File dir = new File (root.getAbsolutePath() + "/download");
				File file = new File(dir,mFilename);
				reader = new BufferedReader(new FileReader(file));
				StringBuilder jsonString = new StringBuilder();
				String line = null;
				while((line = reader.readLine()) != null){
					//Line breaks are omitted and irrelevant
					jsonString.append(line);
				}
				//Parse the JSON using JSONTokener
				JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
				//Build the array of crimes from JSONObjects
				for(int i = 0; i<array.length(); i++){
					crimes.add(new Crime(array.getJSONObject(i)));
				}
			}catch (FileNotFoundException e){
				//Ignore this one; it happens when starting fresh
			}finally{
				if(reader != null)
					reader.close();
			}
			return crimes;
		}else{
		try{
			//open and read file into StringBuilder
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null){
				//Line breaks are omitted and irrelevant
				jsonString.append(line);
			}
			//Parse the JSON using JSONTokener
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
			//Build the array of crimes from JSONObjects
			for(int i = 0; i<array.length(); i++){
				crimes.add(new Crime(array.getJSONObject(i)));
			}
		}catch (FileNotFoundException e){
			//Ignore this one; it happens when starting fresh
		}finally{
			if(reader != null)
				reader.close();
		}
		return crimes;
		}
	}
	
	public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException{
		//Build an array in json
		checkExternalMedia();
		JSONArray array = new JSONArray();
		for(Crime c: crimes)
			array.put(c.toJSON());
		Writer writer = null;
		//Write the file to disk
		if(mExternalStorageAvailiable && mExternalStorageWriteable && !mExternalStorageEmulated){
			File root = Environment.getExternalStorageDirectory();
			File dir = new File (root.getAbsolutePath() + "/download");
			dir.mkdirs();
			File file = new File(dir, mFilename);
			try{
				FileOutputStream f  = new FileOutputStream(file);
				writer = new OutputStreamWriter(f);
				writer.write(array.toString());
			}finally{
				if(writer != null)
					writer.close();
			}
		}else{
			try{
				OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
				writer = new OutputStreamWriter(out);
				writer.write(array.toString());
			}finally{
				if(writer != null)
					writer.close();
			}
		}
	}
}
