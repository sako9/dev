package com.bignerdranch.android.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {
	private MediaPlayer mPlayer;
	private int pos = 0;
	
	public void stop(){
		// Releases the mediaplayer, destroys the instance
		//simple rule: keep exactly one MediaPlayer around and keep it around only as long as you need it
		if(mPlayer != null){
			mPlayer.release();
			mPlayer = null;
		}
	}
	
	public void pause(){
		if(mPlayer != null){
			if(mPlayer.isPlaying()){
				pos = mPlayer.getCurrentPosition();
				mPlayer.pause();
			}else{
				mPlayer.seekTo(pos);
				mPlayer.start();
			}
			
		}
	}
	
	public void play(Context c){
		//calling stop() at the beginning of play(context) prevents multiple instances of MediaPlayer
		stop();
		//play media by creating an instance of MediaPlayer by giving it a context and an the media file id
		
		mPlayer = MediaPlayer.create(c, R.raw.one_small_step);
		
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				stop();
			}
		});
		mPlayer.start();
	}

}
