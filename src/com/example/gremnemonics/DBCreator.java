package com.example.gremnemonics;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DBCreator extends Activity  {
	MnemonicDB mnemonicDB;
	LinearLayout lvLoader;
	Random clr;
	TextView tvdbLoader;
	ImageView imgdb1,imgdb2,imgdb3,imgdb4;
	int i=1;
	private void init() {
		mnemonicDB=new MnemonicDB(getApplicationContext());
		mnemonicDB.open();
		//mnemonicDB.emptyDB();
		System.out.println("emptying db");
		for(i=1;i<=1000;i++){
			Random random=new Random();
			int n=random.nextInt(6);
			runOnUiThread(new Runnable() {
				public void run() {
					if(i>20 && i<50){
						imgdb1.setAlpha(0.40f);
					}
					if(i>50 && i<100){
						imgdb1.setAlpha(0.55f);
					}
					if(i>100 && i<150){
						imgdb1.setAlpha(0.70f);
					}
					if(i>150 && i<200){
						imgdb1.setAlpha(0.85f);
					}
					if(i>200 && i<250){
						imgdb1.setAlpha(1.0f);
						imgdb2.setAlpha(0.25f);
					}
					if(i>250 && i<300){
						imgdb1.setAlpha(0.85f);
						imgdb2.setAlpha(0.40f);
					}
					if(i>300 && i<350){
						imgdb1.setAlpha(0.70f);
						imgdb2.setAlpha(0.55f);
					}
					if(i>350 && i<400){
						imgdb1.setAlpha(0.55f);
						imgdb2.setAlpha(0.70f);
					}
					if(i>400 && i<450){
						imgdb1.setAlpha(0.40f);
						imgdb2.setAlpha(0.85f);
					}
					if(i>450 && i<500){
						imgdb1.setAlpha(0.25f);
						imgdb2.setAlpha(1.0f);
					}

					if(i>500 && i<550){
						imgdb2.setAlpha(0.85f);
						imgdb3.setAlpha(0.25f);
					}

					if(i>550 && i<600){
						imgdb2.setAlpha(0.70f);
						imgdb3.setAlpha(0.40f);
					}
					if(i>600 && i<650){
						imgdb2.setAlpha(0.55f);
						imgdb3.setAlpha(0.55f);
					}

					if(i>650 && i<700){
						imgdb2.setAlpha(0.40f);
						imgdb3.setAlpha(0.70f);
					}
					if(i>700 && i<750){
						imgdb2.setAlpha(0.25f);
						imgdb3.setAlpha(0.85f);
					}


					if(i>750 && i<800){
						imgdb3.setAlpha(1.0f);
						imgdb4.setAlpha(0.25f);
					}
					if(i>800 && i<850){
						imgdb3.setAlpha(0.85f);
						imgdb4.setAlpha(0.4f);
					}
					if(i>800 && i<850){
						imgdb3.setAlpha(0.70f);
						imgdb4.setAlpha(0.55f);
					}if(i>850 && i<900){
						imgdb3.setAlpha(0.55f);
						imgdb4.setAlpha(0.70f);
					}
					if(i>900 && i<950){
						imgdb3.setAlpha(0.40f);
						imgdb4.setAlpha(0.85f);
					}
					if(i>950 ){
						imgdb3.setAlpha(0.25f);
						imgdb4.setAlpha(1.0f);
					}

				}
			});

			if(i==1)
				n=0;//progress check
			if(i==999)
				n=1;
			if(i==998){
				n=7;
				mnemonicDB.createEntry("Word-Name"+i, "Word-Meaning"+i, "Word-Mnemonic"+i, "BLUE","0");
			}
			if(n==0){
				mnemonicDB.createEntry("Word-Name"+i, "Word-Meaning"+i, "Word-Mnemonic"+i, "BLUE","TRUE");
				System.out.println("Word-Name"+i+""+n);
			}
			else if(n==1){
				mnemonicDB.createEntry("Word-Name"+i, "Word-Meaning"+i, "Word-Mnemonic"+i, "RED","FALSE");
				System.out.println("Word-Name"+i+""+n);
			}
			else if(n==2){
				mnemonicDB.createEntry("Word-Name"+i, "Word-Meaning"+i, "Word-Mnemonic"+i, "ORANGERED","TRUE");
				System.out.println("Word-Name"+i+""+n);
			}
			else if(n==3)   {
				mnemonicDB.createEntry("Word-Name"+i, "Word-Meaning"+i, "Word-Mnemonic"+i, "YELLOW","TRUE");
				System.out.println("Word-Name"+i+""+n);
			}
			else if(n==4){
				mnemonicDB.createEntry("Word-Name"+i, "Word-Meaning"+i, "Word-Mnemonic"+i, "GREENYELLOW","TRUE");
				System.out.println("Word-Name"+i+""+n);
			}
			else if(n==5){
				mnemonicDB.createEntry("Word-Name"+i, "Word-Meaning"+i, "Word-Mnemonic"+i, "GREEN","TRUE");
				System.out.println("Word-Name"+i+""+n);
			}
		}
		mnemonicDB.close();		
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.copyofhomescreen);
		tvdbLoader=(TextView) findViewById(R.id.tvdbLoader);
		ProgressBar bar=(ProgressBar) findViewById(R.id.pbdbLoader);
		Animation animation=new ScaleAnimation(Animation.RELATIVE_TO_SELF,0.05f,Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.05f,Animation.RELATIVE_TO_SELF,1.0f);
		animation.setDuration(1500);    
		animation.setRepeatCount(-1);
		clr=new Random();
		imgdb1=(ImageView) findViewById(R.id.imgdb1);
		imgdb2=(ImageView) findViewById(R.id.imgdb2);
		imgdb3=(ImageView) findViewById(R.id.imgdb3);
		imgdb4=(ImageView) findViewById(R.id.imgdb4);
		imgdb2.setAlpha(0.25f);
		imgdb3.setAlpha(0.25f);
		imgdb4.setAlpha(0.25f);

		animation.setBackgroundColor(Color.rgb(clr.nextInt(256),clr.nextInt(256),clr.nextInt(256)));
		animation.setRepeatMode(Animation.REVERSE);
		animation.setFillAfter(true);
		tvdbLoader.setAnimation(animation);
		animation=new RotateAnimation(-35.0f, 17.0f, bar.getWidth(),bar.getHeight()); 
		animation.setDuration(4500);  
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.REVERSE);
		animation.setFillAfter(false);
		bar.setAnimation(animation);
		lvLoader=(LinearLayout) findViewById(R.id.lvdbLoader);

		TimerTask task=new TimerTask() {
			@Override
			public void run() {
				init();
			}
		};
		Timer timer=new Timer();
		TimerTask task1=new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						lvLoader.setBackgroundColor(Color.rgb(clr.nextInt(256),clr.nextInt(256),clr.nextInt(256)));
						tvdbLoader.setTextColor(Color.rgb(clr.nextInt(256),clr.nextInt(256),clr.nextInt(256)));
					}
				});
			}
		};

		timer.scheduleAtFixedRate(task1, 0, 1000);
		Timer timer1=new Timer();
		timer1.schedule(task,5); 

	}


}
