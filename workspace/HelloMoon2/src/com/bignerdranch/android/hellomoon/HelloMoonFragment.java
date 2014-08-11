package com.bignerdranch.android.hellomoon;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

public class HelloMoonFragment extends Fragment {
	
	private Button mPlayButton;
	private Button mStopButton;
	private Button mPauseButton;
	private VideoView videoView;
	private Uri resourceUri = Uri.parse("android.resource://"+"com.bignerdranch.android.hellomoon/raw/apollo_17_strollll");
	private AudioPlayer mPlayer = new AudioPlayer();
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstaceState){
		//inflates the hello moon fragment layout 
		View v = inflater.inflate(R.layout.fragment_hello_moon, parent,false);
		videoView = (VideoView)v.findViewById(R.id.hellomoon_video);
		videoView.setVideoURI(resourceUri);
		
		mPlayButton = (Button)v.findViewById(R.id.hellomoon_playButton);
		mPlayButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub					
					videoView.start();
					if(!videoView.isPlaying())
					videoView.resume();
			}
		});
		mStopButton = (Button)v.findViewById(R.id.hellomoon_stopButton);
		mStopButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				videoView.suspend();
			}
		});
		mPauseButton =(Button)v.findViewById(R.id.hellomoon_pauseButton);
		mPauseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				videoView.pause();
			}
		});
		return v;
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		videoView.stopPlayback();
	}

}
