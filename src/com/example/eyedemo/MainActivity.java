package com.example.eyedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

public class MainActivity extends Activity {
	private EyeView eyeView;
	private int screenHeight;
	private float originY;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		eyeView = (EyeView)findViewById(R.id.eye_view);
		
		DisplayMetrics dm = getResources().getDisplayMetrics();
		screenHeight = dm.heightPixels;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				originY = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				float currentY = event.getY();
				if(currentY <= originY){
					eyeView.setPhase(0);
				}else{
					eyeView.setPhase((currentY - originY) * 3 / screenHeight);
				}
				break;
			case MotionEvent.ACTION_UP:
				eyeView.setPhase(0);
				break;
		}
		return super.onTouchEvent(event);
	}
}
