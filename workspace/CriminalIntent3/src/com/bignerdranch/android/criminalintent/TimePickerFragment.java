package com.bignerdranch.android.criminalintent;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;


public class TimePickerFragment extends DialogFragment {
	public static final String Extra_Time = "com.bignerdranch.andriod.criminalIntent";
	private Date mDate;
	
	public void sendResult(int resultCode){
		if(getTargetFragment() == null)
			return;
		
		Intent i = new Intent();
		i.putExtra(Extra_Time, mDate);
		
		getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,i);
		
	}
	
	public static TimePickerFragment newInstance(Date date){
		Bundle args = new Bundle();
		args.putSerializable(Extra_Time, date);
		
		TimePickerFragment fragment = new TimePickerFragment();
		fragment.setArguments(args);
		return fragment;
		
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		mDate = (Date)getArguments().getSerializable(Extra_Time);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
		TimePicker timePicker = (TimePicker)v.findViewById(R.id.dialog_time_timepicker);
		timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener(){

			@Override
			public void onTimeChanged(TimePicker view, int hour, int minute) {
				// TODO Auto-generated method stub
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(mDate);
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				mDate = new GregorianCalendar(year,month,day,hour,minute).getTime();
				getArguments().putSerializable(Extra_Time, mDate);
			}
			
		});
		return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.time_picker_title).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				sendResult(Activity.RESULT_OK);
			}
		}).create();
	}

}


