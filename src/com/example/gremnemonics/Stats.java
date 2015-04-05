package com.example.gremnemonics;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class Stats extends Activity implements OnClickListener{
	TabHost thStats;
	TabSpec spec;
	ImageButton ibnReset;
	View view;
	ProgressBar pbAllWords,pb1to100Words,pb100to200Words,pb200to300Words,pb300to400Words,pb400to500Words,pb500to600Words,
	pb600to700Words,pb700to800Words,pb800to900Words,pb900to1000Words;
	TextView tvAllWordsCount,tv1to100WordsCount,tv100to200WordsCount,tv200to300WordsCount,tv300to400WordsCount,tv400to500WordsCount,tv500to600WordsCount,
	tv600to700WordsCount,tv700to800WordsCount,tv800to900WordsCount,tv900to1000WordsCount;
	ProgressBar pbNonViewedWords,pbRedWords,pbOrangeRedWords,pbYellowWords,pbLightGreenWords,pbGreenWords;
	TextView tvNonViewedWordsCount,tvRedWordsCount,tvOrangeRedWordsCount,tvYellowWordsCount,tvLightGreenWordsCount,tvGreenWordsCount;
	String ListValue;
	ImageButton ibnRandomize,ibnListIcon,ibnStatsIcon,ibnSettings,ibnHome;
	MnemonicDB db;
	ArrayList<String> nameValues;
	int countAllWordsCountr,countNonViewedWordsCountr,count1to100WordsCount,count100to200WordsCount,count200to300WordsCount,
	count300to400WordsCount,count400to500WordsCount,count500to600WordsCount,count600to700WordsCount,count700to800WordsCount,
	count800to900WordsCount,count900to1000WordsCount,countRedWordsCount,countOrangeRedWordsCount,countYellowWordsCount,
	countLightGreenWordsCount,countGreenWordsCount;
	SharedPreferences spStats;
	Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats);
		Intent intent=new Intent(this, StatLoader.class);
		Bundle bundle=new Bundle();
		bundle.putParcelable("database", db);
		bundle.putStringArrayList("NameValues",nameValues);
		intent.putExtras(bundle); 
		startActivityForResult(intent,0);
	}
	private void init() {
		view=new View(this);
		thStats=(TabHost) findViewById(R.id.thStats); 
		thStats.setup();
		spec=thStats.newTabSpec("OrderByList");
		spec.setContent(R.id.tbOrderByList);
		spec.setIndicator("Stats By List");
		thStats.addTab(spec);
		spec=thStats.newTabSpec("OrderByStatus");
		spec.setContent(R.id.tbOrderByStatus);
		spec.setIndicator("Stats By Status");
		thStats.addTab(spec);
		thStats.setOnClickListener(this);
		ibnReset=(ImageButton) findViewById(R.id.ibnReset);
		ibnHome=(ImageButton) findViewById(R.id.ibnHome);
		ibnHome.setOnClickListener(this);
		ibnReset.setOnClickListener(this);
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		db=new MnemonicDB(getApplicationContext());
		db=(MnemonicDB) bundle.getParcelable("database");
		nameValues=bundle.getStringArrayList("NameValues");
		db.setcontext(getApplicationContext());
		tvInit();
		pbInit();
		getDbValues();
	}
	private void getDbValues() {
		tvAllWordsCount.setText(countAllWordsCountr+"/1000");
		pbAllWords.setProgress(countAllWordsCountr);
		tvNonViewedWordsCount.setText(countNonViewedWordsCountr+"/1000");
		pbNonViewedWords.setProgress(countNonViewedWordsCountr);
		tv1to100WordsCount.setText(count1to100WordsCount+"/100");
		pb1to100Words.setProgress(count1to100WordsCount);
		tv100to200WordsCount.setText(count100to200WordsCount+"/100");
		pb100to200Words.setProgress(count100to200WordsCount);
		tv200to300WordsCount.setText(count200to300WordsCount+"/100");
		pb200to300Words.setProgress(count200to300WordsCount);
		tv300to400WordsCount.setText(count300to400WordsCount+"/100");
		pb300to400Words.setProgress(count300to400WordsCount);
		tv400to500WordsCount.setText(count400to500WordsCount+"/100");
		pb400to500Words.setProgress(count400to500WordsCount);
		tv500to600WordsCount.setText(count500to600WordsCount+"/100");
		pb500to600Words.setProgress(count500to600WordsCount);
		tv600to700WordsCount.setText(count600to700WordsCount+"/100");
		pb600to700Words.setProgress(count600to700WordsCount);
		tv700to800WordsCount.setText(count700to800WordsCount+"/100");
		pb700to800Words.setProgress(count700to800WordsCount);
		tv800to900WordsCount.setText(count800to900WordsCount+"/100");
		pb800to900Words.setProgress(count800to900WordsCount);
		tv900to1000WordsCount.setText(count900to1000WordsCount+"/100");
		pb900to1000Words.setProgress(count900to1000WordsCount);
		tvRedWordsCount.setText(countRedWordsCount+"/1000");
		pbRedWords.setProgress(countRedWordsCount);
		tvOrangeRedWordsCount.setText(countOrangeRedWordsCount+"/1000");
		pbOrangeRedWords.setProgress(countOrangeRedWordsCount);
		tvYellowWordsCount.setText(countYellowWordsCount+"/1000");
		pbYellowWords.setProgress(countYellowWordsCount);
		tvLightGreenWordsCount.setText(countLightGreenWordsCount+"/1000");
		pbLightGreenWords.setProgress(countLightGreenWordsCount);
		tvGreenWordsCount.setText(countGreenWordsCount+"/1000");
		pbGreenWords.setProgress(countGreenWordsCount);
	}
	private void tvInit() {
		tvAllWordsCount=(TextView) findViewById(R.id.tvAllWordsCount);
		tv1to100WordsCount=(TextView) findViewById(R.id.tv1to100WordsCount);
		tv100to200WordsCount=(TextView) findViewById(R.id.tv100to200WordsCount);
		tv200to300WordsCount=(TextView) findViewById(R.id.tv200to300WordsCount);
		tv300to400WordsCount=(TextView) findViewById(R.id.tv300to400WordsCount);
		tv400to500WordsCount=(TextView) findViewById(R.id.tv400to500WordsCount);
		tv500to600WordsCount=(TextView) findViewById(R.id.tv500to600WordsCount);
		tv600to700WordsCount=(TextView) findViewById(R.id.tv600to700WordsCount);
		tv700to800WordsCount=(TextView) findViewById(R.id.tv700to800WordsCount);
		tv800to900WordsCount=(TextView) findViewById(R.id.tv800to900WordsCount);
		tv900to1000WordsCount=(TextView) findViewById(R.id.tv900to1000WordsCount);
		tvNonViewedWordsCount=(TextView) findViewById(R.id.tvNonviewedWordsCount);
		tvRedWordsCount=(TextView) findViewById(R.id.tvRedviewedWordsCount);
		tvOrangeRedWordsCount=(TextView) findViewById(R.id.tvOrangeRedViewedWordsCount);
		tvYellowWordsCount=(TextView) findViewById(R.id.tvYellowViewedWordsCount);
		tvLightGreenWordsCount=(TextView) findViewById(R.id.tvLightGreenViewedWordsCount);
		tvGreenWordsCount=(TextView) findViewById(R.id.tvGreenViewedWordsCount);
		ibnRandomize=(ImageButton) findViewById(R.id.ibnRandomise);
		ibnListIcon=(ImageButton) findViewById(R.id.ibnListIcon);
		ibnStatsIcon=(ImageButton) findViewById(R.id.ibnStatsIcon);
		ibnSettings=(ImageButton) findViewById(R.id.ibnSettings);
		ibnRandomize.setOnClickListener(this);
		ibnListIcon.setOnClickListener(this);
		ibnStatsIcon.setOnClickListener(this);
		ibnSettings.setOnClickListener(this);
	}
	private void pbInit() {
		pbAllWords=(ProgressBar) findViewById(R.id.pbAllWords);
		pb1to100Words=(ProgressBar) findViewById(R.id.pb1to100Words);
		pb100to200Words=(ProgressBar) findViewById(R.id.pb100to200Words);
		pb200to300Words=(ProgressBar) findViewById(R.id.pb200to300Words);
		pb300to400Words=(ProgressBar) findViewById(R.id.pb300to400Words);
		pb400to500Words=(ProgressBar) findViewById(R.id.pb400to500Words);
		pb500to600Words=(ProgressBar) findViewById(R.id.pb500to600Words);
		pb600to700Words=(ProgressBar) findViewById(R.id.pb600to700Words);
		pb700to800Words=(ProgressBar) findViewById(R.id.pb700to800Words);
		pb800to900Words=(ProgressBar) findViewById(R.id.pb800to900Words);
		pb900to1000Words=(ProgressBar) findViewById(R.id.pb900to1000Words);
		pbNonViewedWords=(ProgressBar) findViewById(R.id.pbNonViewedWords);
		pbRedWords=(ProgressBar) findViewById(R.id.pbRedWords);
		pbOrangeRedWords=(ProgressBar) findViewById(R.id.pbOrangeRedWords);
		pbYellowWords=(ProgressBar) findViewById(R.id.pbYellowWords);
		pbLightGreenWords=(ProgressBar) findViewById(R.id.pbLightGreenWords);
		pbGreenWords=(ProgressBar) findViewById(R.id.pbGreenWords);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibnReset:
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(getApplicationContext(), "Reset Complete", Toast.LENGTH_SHORT).show();
					tvAllWordsCount.setText("0/1000");
					pbAllWords.setProgress(0);
					tv1to100WordsCount.setText("0/100");
					pb1to100Words.setProgress(0);
					tv100to200WordsCount.setText("0/100");
					pb100to200Words.setProgress(0);
					tv200to300WordsCount.setText("0/100");
					pb200to300Words.setProgress(0);
					tv300to400WordsCount.setText("0/100");
					pb300to400Words.setProgress(0);
					tv400to500WordsCount.setText("0/100");
					pb400to500Words.setProgress(0);
					tv500to600WordsCount.setText("0/100");
					pb500to600Words.setProgress(0);
					tv600to700WordsCount.setText("0/100");
					pb600to700Words.setProgress(0);
					tv700to800WordsCount.setText("0/100");  
					pb700to800Words.setProgress(0);
					tv800to900WordsCount.setText("0/100");
					pb800to900Words.setProgress(0);
					tv900to1000WordsCount.setText("0/100");
					pb900to1000Words.setProgress(0);
					tvNonViewedWordsCount.setText("1000/1000");
					pbNonViewedWords.setProgress(1000);
					tvRedWordsCount.setText("0/1000");
					pbRedWords.setProgress(0);
					tvOrangeRedWordsCount.setText("0/1000");
					pbOrangeRedWords.setProgress(0);
					tvYellowWordsCount.setText("0/1000");
					pbYellowWords.setProgress(0);
					tvLightGreenWordsCount.setText("0/1000");
					pbLightGreenWords.setProgress(0);
					tvGreenWordsCount.setText("0/1000");
					pbGreenWords.setProgress(0);
					db.open();
					db.resetColor();
					db.close();
					view.postInvalidate();
				}
			});
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(getApplicationContext(), "Reset Cancelled", Toast.LENGTH_SHORT).show();
				}
			});
			AlertDialog dialog=builder.create();
			dialog.setTitle("Reset");
			dialog.setMessage("Do you want to Reset?");
			dialog.show();
			break;
		case R.id.ibnRandomise:
			Random random=new Random();
			int n=random.nextInt(10);
			ArrayList<String> str=new ArrayList<String>();
			db.open();
			str=db.getRowNames((n*100)+1);
			db.close();
			Intent intent=new Intent(this, Display.class);
			Bundle bundle=new Bundle();
			bundle.putParcelable("database", db);
			bundle.putString("Source", "HomeScreen");
			bundle.putStringArrayList("NameValues", str);	
			intent.putExtras(bundle); 
			startActivity(intent);
			break;
		case R.id.ibnStatsIcon:
			break;
		case R.id.ibnListIcon:
			Intent listIcon=new Intent(this,DisplayItems.class);
			Bundle bundle1=new Bundle();
			bundle1.putParcelable("database", db); 
			bundle1.putStringArrayList("NameValues",this.nameValues);
			listIcon.putExtras(bundle1); 
			startActivity(listIcon);
			break;
		case R.id.ibnSettings:
			Intent settings=new Intent(getApplicationContext(), Settings.class);
			Bundle bundle2=new Bundle();
			bundle2.putParcelable("database", db); 
			bundle2.putStringArrayList("NameValues",this.nameValues);
			settings.putExtras(bundle2); 
			startActivity(settings);
			break;
		case R.id.ibnHome:
			Intent i=new Intent(getApplicationContext(), HomeScreen.class);
			startActivity(i);
			break; 
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Bundle bundle=data.getExtras();
		db=bundle.getParcelable("database");
		nameValues=bundle.getStringArrayList("NameValues");
		init();
		countAllWordsCountr=bundle.getInt("countAllWordsCountr");
		countNonViewedWordsCountr=bundle.getInt("countNonViewedWordsCountr");
		count1to100WordsCount=bundle.getInt("count1to100WordsCount");
		count100to200WordsCount=bundle.getInt("count100to200WordsCount");
		count200to300WordsCount=bundle.getInt("count200to300WordsCount");
		count300to400WordsCount=bundle.getInt("count300to400WordsCount");
		count400to500WordsCount=bundle.getInt("count400to500WordsCount");
		count500to600WordsCount=bundle.getInt("count500to600WordsCount");
		count600to700WordsCount=bundle.getInt("count600to700WordsCount");
		count700to800WordsCount=bundle.getInt("count700to800WordsCount");
		count800to900WordsCount=bundle.getInt("count800to900WordsCount");
		count900to1000WordsCount=bundle.getInt("count900to1000WordsCount");
		countRedWordsCount=bundle.getInt("countRedWordsCount");
		countOrangeRedWordsCount=bundle.getInt("countOrangeRedWordsCount");
		countYellowWordsCount=bundle.getInt("countYellowWordsCount");
		countLightGreenWordsCount=bundle.getInt("countLightGreenWordsCount");
		countGreenWordsCount=bundle.getInt("countGreenWordsCount");
		getDbValues();
		view.postInvalidate();
	}

	@Override
	protected void onResume() {
		super.onResume();
		getprefs();
	}
	private void getprefs() {
		spStats=getSharedPreferences("spStatScreen", 0);
		countAllWordsCountr=spStats.getInt("countAllWordsCountr",0 );
		countNonViewedWordsCountr=spStats.getInt("countNonViewedWordsCountr",0);
		count1to100WordsCount=spStats.getInt("count1to100WordsCount",0);
		count100to200WordsCount=spStats.getInt("count100to200WordsCount",0);
		count200to300WordsCount=spStats.getInt("count200to300WordsCount",0);
		count300to400WordsCount=spStats.getInt("count300to400WordsCount",0);
		count400to500WordsCount=spStats.getInt("count400to500WordsCount",0);
		count500to600WordsCount=spStats.getInt("count500to600WordsCount",0);
		count600to700WordsCount=spStats.getInt("count600to700WordsCount",0);
		count700to800WordsCount=spStats.getInt("count700to800WordsCount",0);
		count800to900WordsCount=spStats.getInt("count800to900WordsCount",0);
		count900to1000WordsCount=spStats.getInt("count900to1000WordsCount",0);
		countRedWordsCount=spStats.getInt("countRedWordsCount",0);
		countOrangeRedWordsCount=spStats.getInt("countOrangeRedWordsCount",0);
		countYellowWordsCount=	spStats.getInt("countYellowWordsCount",0);
		countLightGreenWordsCount=spStats.getInt("countLightGreenWordsCount",0);
		countGreenWordsCount=spStats.getInt("countGreenWordsCount", 0);
	}
	@Override
	protected void onPause() {
		super.onPause();
		saveprefs();
	}
	private void saveprefs() {
		spStats=getSharedPreferences("spStatScreen", 0);
		editor=spStats.edit();
		editor.putInt("countAllWordsCountr", countAllWordsCountr);
		editor.putInt("countNonViewedWordsCountr",countNonViewedWordsCountr);
		editor.putInt("count1to100WordsCount",count1to100WordsCount);
		editor.putInt("count100to200WordsCount",count100to200WordsCount);
		editor.putInt("count200to300WordsCount",count200to300WordsCount);
		editor.putInt("count300to400WordsCount",count300to400WordsCount);
		editor.putInt("count400to500WordsCount",count400to500WordsCount);
		editor.putInt("count500to600WordsCount",count500to600WordsCount);
		editor.putInt("count600to700WordsCount",count600to700WordsCount);
		editor.putInt("count700to800WordsCount",count700to800WordsCount);
		editor.putInt("count800to900WordsCount",count800to900WordsCount);
		editor.putInt("count900to1000WordsCount",count900to1000WordsCount);
		editor.putInt("countRedWordsCount",countRedWordsCount);
		editor.putInt("countOrangeRedWordsCount",countOrangeRedWordsCount);
		editor.putInt("countYellowWordsCount",countYellowWordsCount);
		editor.putInt("countLightGreenWordsCount",countLightGreenWordsCount);
		editor.putInt("countGreenWordsCount", countGreenWordsCount);
	}



}
