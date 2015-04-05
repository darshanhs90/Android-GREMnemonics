package com.example.gremnemonics;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class Settings extends Activity implements android.view.View.OnClickListener{
	int Request_Code=10;
	ImageButton ibnRandomize,ibnListIcon,ibnStatsIcon,ibnSettings,ibnSurvey,ibnHome;
	String ListValue;
	Switch swPrepIndicator;
	SharedPreferences preferences;
	Editor edit;
	CheckBox cbSurveyStatus;
	Button bnRandomiser,bnResetDB;
	TextView tvLikeUsAt,tvClickForInfo,tvRandom1,tvRandom2,tvRandom3,tvRandom4,tvRandom5,tvRandom6,tvRandom7;
	View view;
	MnemonicDB db;
	ArrayList<String> nameValues;
	Timer timer;
	Random random;
	Boolean uid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		view=new View(this);
		bnRandomiser=(Button) findViewById(R.id.bnRandomiser);
		ibnRandomize=(ImageButton) findViewById(R.id.ibnRandomise);
		ibnListIcon=(ImageButton) findViewById(R.id.ibnListIcon);
		ibnStatsIcon=(ImageButton) findViewById(R.id.ibnStatsIcon);
		ibnSettings=(ImageButton) findViewById(R.id.ibnSettings);
		ibnSurvey=(ImageButton) findViewById(R.id.ibnSurvey);
		bnResetDB=(Button) findViewById(R.id.bnResetDB);
		bnResetDB.setOnClickListener(this);
		swPrepIndicator=(Switch) findViewById(R.id.swPrepIndicator);
		tvLikeUsAt=(TextView) findViewById(R.id.tvLikeUsAt);
		tvClickForInfo=(TextView) findViewById(R.id.tvClickForInfo);
		tvRandom1=(TextView) findViewById(R.id.tvRandom1);
		tvRandom2=(TextView) findViewById(R.id.tvRandom2);
		tvRandom3=(TextView) findViewById(R.id.tvRandom3);
		tvRandom4=(TextView) findViewById(R.id.tvRandom4);
		tvRandom5=(TextView) findViewById(R.id.tvRandom5);
		tvRandom6=(TextView) findViewById(R.id.tvRandom6);
		tvRandom7=(TextView) findViewById(R.id.tvRandom7);
		ibnHome=(ImageButton) findViewById(R.id.ibnHome);
		ibnHome.setOnClickListener(this);
		tvLikeUsAt.setOnClickListener(this);
		tvClickForInfo.setOnClickListener(this);
		ibnRandomize.setOnClickListener(this);
		ibnListIcon.setOnClickListener(this);
		ibnStatsIcon.setOnClickListener(this);
		ibnSettings.setOnClickListener(this);
		ibnSurvey.setOnClickListener(this);
		swPrepIndicator.setOnClickListener(this);
		bnRandomiser.setOnClickListener(this);
		cbSurveyStatus=(CheckBox) findViewById(R.id.cbSurveyStatus);
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		db=new MnemonicDB(getApplicationContext());
		db=(MnemonicDB) bundle.getParcelable("database");
		nameValues=bundle.getStringArrayList("NameValues");
		db.setcontext(getApplicationContext());
		db.open();
		if(db.getSurveyStat().contentEquals("TRUE"))
			cbSurveyStatus.setChecked(true);
		if(db.getProgressStat().contentEquals("FALSE"))
			swPrepIndicator.setChecked(false);
		else
			swPrepIndicator.setChecked(true);
		random=new Random();
		if(db.getUID().contentEquals("0"))
			uid=false;
		else
			uid=true;
		//System.out.println(db.getData());
		db.close();

		timer=new Timer();
		TimerTask task1=new TimerTask() {
			@Override
			public void run() {runOnUiThread(new Runnable() {
				public void run() {
					tvRandom1.setText(Integer.toString(random.nextInt(10)));
					tvRandom2.setText(Integer.toString(random.nextInt(10)));
					tvRandom3.setText(Integer.toString(random.nextInt(10)));
					tvRandom4.setText(Integer.toString(random.nextInt(10)));
					tvRandom5.setText(Integer.toString(random.nextInt(10)));
					tvRandom6.setText(Integer.toString(random.nextInt(10)));
					tvRandom7.setText(Integer.toString(random.nextInt(10)));
					view.invalidate();
				}
			});

			}
		};
		if(uid==false)
			timer.scheduleAtFixedRate(task1, 0, 100);
		else
		{	db.open();
		String str=db.getUID();
		String a[]=str.split(" ");
		tvRandom1.setText(a[0]);
		tvRandom2.setText(a[1]);
		tvRandom3.setText(a[2]);
		tvRandom4.setText(a[3]);
		tvRandom5.setText(a[4]);
		tvRandom6.setText(a[5]);
		tvRandom7.setText(a[6]);
		db.close();
		}

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){	
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
			Intent stats=new Intent(getApplicationContext(), Stats.class);
			Bundle bundle2=new Bundle();
			bundle2.putParcelable("database", db);
			bundle2.putStringArrayList("NameValues", nameValues);
			stats.putExtras(bundle2);
			startActivity(stats);
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
			this.recreate();
			break;
		case R.id.ibnSurvey:
			Intent id=new Intent(this, Survey.class);
			Bundle bundle4=new Bundle();
			bundle4.putParcelable("database", db);
			id.putExtras(bundle4);
			startActivity(id);
			break;
		case R.id.swPrepIndicator:
			db.open();
			System.out.println(swPrepIndicator.isChecked());
			if(swPrepIndicator.isChecked()==false){
				db.resetStat("FALSE");
			}
			else
				db.resetStat("TRUE");
			db.close();
			break;
		case R.id.tvLikeUsAt:
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com"));
			startActivity(browserIntent);
			break;
		case R.id.tvClickForInfo:
			Intent browserIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://gre-download.blogspot.in/"));
			startActivity(browserIntent1);
			break;
		case R.id.ibnHome:
			Intent i=new Intent(getApplicationContext(), HomeScreen.class);
			startActivity(i);
			break;
		case R.id.bnRandomiser:
			timer.cancel();
			db.open();
			db.resetUID(tvRandom1.getText()+" "+tvRandom2.getText()+" "+tvRandom3.getText()+" "+tvRandom4.getText()+" "+tvRandom5.getText()+" "+tvRandom6.getText()+" "+tvRandom7.getText());
			System.out.println(db.getUID());
			db.close();
			break;
		case R.id.bnResetDB:
			db.open();
			db.emptyDB();
			db.close();
			Intent intentHome=new Intent(getApplicationContext(), HomeScreen.class);
			startActivity(intentHome);
			break;
		}
	}


	/*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			if(data.getBooleanExtra("Result", false)==true){
				cbSurveyStatus.setChecked(true);
			}
		}
	}*/
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		saveprefs();
	}
	private void saveprefs() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getprefs();
	}

	private void getprefs() {
		// TODO Auto-generated method stub

	}

}