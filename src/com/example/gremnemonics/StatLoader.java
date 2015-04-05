package com.example.gremnemonics;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class StatLoader extends Activity  {
	MnemonicDB mnemonicDB;
	LinearLayout lvLoader;
	Random clr;
	ArrayList<String> nameValues;
	TextView tvStatLoader;
	TabHost thStats;
	TabSpec spec;
	View view;
	
	private void init() {
		mnemonicDB=new MnemonicDB(getApplicationContext());
		mnemonicDB.open();
		Intent intent=new Intent(this,Stats.class);
		Bundle bundle=new Bundle();
		int count[]=mnemonicDB.getCount();
		bundle.putInt("countAllWordsCountr", 1000-count[0]);
		bundle.putInt("countNonViewedWordsCountr", count[0]);
		bundle.putInt("countRedWordsCount", count[1]);
		bundle.putInt("countOrangeRedWordsCount", count[2]);
		bundle.putInt("countYellowWordsCount", count[3]);
		bundle.putInt("countLightGreenWordsCount", count[4]);
		bundle.putInt("countGreenWordsCount", count[5]);
		bundle.putInt("count1to100WordsCount", count[6]);
		bundle.putInt("count100to200WordsCount", count[7]);
		bundle.putInt("count200to300WordsCount", count[8]);
		bundle.putInt("count300to400WordsCount", count[9]);
		bundle.putInt("count400to500WordsCount", count[10]);
		bundle.putInt("count500to600WordsCount", count[11]);
		bundle.putInt("count600to700WordsCount", count[12]);
		bundle.putInt("count700to800WordsCount", count[13]);
		bundle.putInt("count800to900WordsCount", count[14]);
		bundle.putInt("count900to1000WordsCount", count[15]);
		mnemonicDB.close();		
		bundle.putParcelable("database", mnemonicDB);
		bundle.putString("Source", "HomeScreen");
		bundle.putStringArrayList("NameValues", nameValues);
		intent.putExtras(bundle);
		setResult(RESULT_OK, intent);
		finish();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.copyofstats);
		view=new View(this);
		thStats=(TabHost) findViewById(R.id.thStats); 
		thStats.setup();
		spec=thStats.newTabSpec("OrderByList");
		spec.setContent(R.id.lvStatLoader);
		spec.setIndicator("Stats By List");
		thStats.addTab(spec);
		spec=thStats.newTabSpec("OrderByStatus");
		spec.setContent(R.id.tbOrderByStatus);
		spec.setIndicator("Stats By Status");
		thStats.addTab(spec);
		tvStatLoader=(TextView) findViewById(R.id.tvStatLoader);
		ProgressBar bar=(ProgressBar) findViewById(R.id.pbLoader);
		Animation animation=new ScaleAnimation(Animation.RELATIVE_TO_SELF,0.1f,Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.1f,Animation.RELATIVE_TO_SELF,1.0f);
		animation.setDuration(1000);
		animation.setRepeatCount(-1);
		clr=new Random();
		animation.setBackgroundColor(Color.rgb(clr.nextInt(256),clr.nextInt(256),clr.nextInt(256)));
		animation.setRepeatMode(Animation.REVERSE);
		animation.setFillAfter(true);
		tvStatLoader.setAnimation(animation);
		animation=new RotateAnimation(-65.0f, 30.0f, bar.getWidth(),bar.getHeight()); 
		animation.setDuration(4500);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.REVERSE);
		animation.setFillAfter(false);
		bar.setAnimation(animation);
		lvLoader=(LinearLayout) findViewById(R.id.lvStatLoader);
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
						tvStatLoader.setTextColor(Color.rgb(clr.nextInt(256),clr.nextInt(256),clr.nextInt(256)));
					}
				});
			}
		};
		timer.scheduleAtFixedRate(task1, 0, 1000);
		Timer timer1=new Timer();

		timer1.schedule(task,5); 
	}

}
