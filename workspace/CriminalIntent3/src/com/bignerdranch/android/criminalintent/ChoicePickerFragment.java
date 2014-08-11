package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ChoicePickerFragment extends DialogFragment {
	public static final String Choice = "com.bignerdranch.andriod.criminalIntent";
	private int pick = 0;
	
	public void sendResult(int resultCode){
		if(getTargetFragment() == null)
			return;
		
		Intent i = new Intent();
		i.putExtra(Choice, pick);
		getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,i);
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.Choice_Title).setPositiveButton(R.string.Date_Choice, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				pick = 0;
				sendResult(Activity.RESULT_OK);
			}
		}).setNegativeButton(R.string.Time_Choice, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				pick = 1;
				sendResult(Activity.RESULT_OK);
			}
		});
		return builder.create();
	}

}
