package com.example.gremnemonics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

public class HomeScreen extends Activity implements OnClickListener,OnItemClickListener {
	ImageButton ibnRandomize,ibnListIcon,ibnStatsIcon,ibnSettings,ibnLogo;
	String ListValue;
	MnemonicDB mnemonicDB;
	ProgressBar pbStatusIndicator;
	ArrayAdapter<String> adapter;
	ListView lvCategories;
	ArrayList<String> str1;
	SharedPreferences spHomeScreen;
	Editor editor;
	String classes[]={"1-100","101-200","201-300","301-400",
			"401-500","501-600","601-700","701-800","801-900","901-1000"};
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);
		getIntentDetails();
		mnemonicDB=new MnemonicDB(getApplicationContext());
		mnemonicDB.open();
		//mnemonicDB.emptyDB(); 
		if(mnemonicDB.getDBStat()==null){
			mnemonicDB.deletedb();
			startActivityForResult((new Intent(this, DBCreator.class)),0);
			mnemonicDB.close();
		}
		else{
			str1=new ArrayList<String>();
			init();  
			mnemonicDB.close();
		}
	}
	private void getIntentDetails() {
		mnemonicDB=new MnemonicDB(getApplicationContext());
		mnemonicDB.setcontext(getApplicationContext());
	}
	private void init() {
		ibnRandomize=(ImageButton) findViewById(R.id.ibnRandomise);
		ibnListIcon=(ImageButton) findViewById(R.id.ibnListIcon);
		ibnStatsIcon=(ImageButton) findViewById(R.id.ibnStatsIcon);
		ibnSettings=(ImageButton) findViewById(R.id.ibnSettings);
		ibnLogo=(ImageButton) findViewById(R.id.ibnLogo);
		ibnLogo.setOnClickListener(this);
		ibnRandomize.setOnClickListener(this);
		ibnListIcon.setOnClickListener(this);
		ibnStatsIcon.setOnClickListener(this);	
		ibnSettings.setOnClickListener(this);
		adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,classes);
		lvCategories=(ListView) findViewById(R.id.lvCategories);
		lvCategories.setAdapter(adapter);	
		lvCategories.setOnItemClickListener(this);
		pbStatusIndicator=(ProgressBar) findViewById(R.id.pbStatusIndicator);
		mnemonicDB.open();
		System.out.println("working?");
		if(str1.size()==0){
			str1=mnemonicDB.getRowNames(1001);
		}
		int a[]=mnemonicDB.getCount();
		System.out.println(str1.get(977));
		System.out.println("YES");
		int j=a[6]+a[7]+a[8]+a[9]+a[10]+a[11]+a[12]+a[13]+a[14]+a[15];
		pbStatusIndicator.setProgress((int)((j)/10));
		if(mnemonicDB.getProgressStat().contentEquals("FALSE"))
			pbStatusIndicator.setVisibility(View.INVISIBLE);
		mnemonicDB.close();
	}

	@Override  
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibnRandomise:
			Random random=new Random();
			int n=random.nextInt(10);
			ArrayList<String> str=new ArrayList<String>();
			if(n!=10){
				mnemonicDB.open();
				System.out.println(n);
				str=mnemonicDB.getRowNames((n*100)+1);	
				mnemonicDB.close();}
			else
				str=str1;
			Intent intent=new Intent(this, Display.class);
			Bundle bundle=new Bundle();
			bundle.putParcelable("database", mnemonicDB);
			bundle.putString("Source", "HomeScreen");
			bundle.putStringArrayList("NameValues", str);	
			intent.putExtras(bundle); 
			startActivity(intent);
			break;
		case R.id.ibnListIcon:
			Intent listIcon=new Intent(this,DisplayItems.class);
			Bundle bundle1=new Bundle();
			bundle1.putParcelable("database", mnemonicDB);
			bundle1.putStringArrayList("NameValues",this.str1);
			listIcon.putExtras(bundle1); 
			startActivity(listIcon);
			break;
		case R.id.ibnStatsIcon:
			Intent stats=new Intent(getApplicationContext(), Stats.class);
			Bundle bundle2=new Bundle();
			bundle2.putParcelable("database", mnemonicDB);
			bundle2.putStringArrayList("NameValues", str1);
			stats.putExtras(bundle2);
			startActivity(stats);
			break;
		case R.id.ibnSettings:
			Intent settings=new Intent(getApplicationContext(), Settings.class);
			Bundle bundle3=new Bundle();
			bundle3.putParcelable("database", mnemonicDB);
			bundle3.putStringArrayList("NameValues", str1);
			settings.putExtras(bundle3);
			startActivity(settings);
			break;
		case R.id.ibnLogo:
			Dialog dialog=new Dialog(this);
			dialog.setTitle("Developer Info");
			dialog.setCancelable(true);
			dialog.setContentView(R.layout.developerinfo);
			dialog.show();
			dialog.setCanceledOnTouchOutside(true);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		ArrayList<String> str2=new ArrayList<String>();	
		mnemonicDB.open();
		if(position!=0)
			str2=mnemonicDB.getRowNames((position*100)+1);
		else
			str2=mnemonicDB.getRowNames(1);
		mnemonicDB.close();
		Intent intent=new Intent(this, DisplayItems.class);
		Bundle bundle2=new Bundle();
		bundle2.putParcelable("database", mnemonicDB);
		bundle2.putString("Source", "HomeScreen");
		bundle2.putStringArrayList("NameValues", str2);
		intent.putExtras(bundle2);
		startActivity(intent);
	}
	@Override
	protected void onPause() {
		super.onPause();
		saveprefs();
	}
	private void saveprefs() {
		spHomeScreen=getSharedPreferences("spHomeScreen", 0);
		editor=spHomeScreen.edit(); 
		Set<String> set=new TreeSet<String>();
		if(str1!=null){
			for (int i = 0; i < str1.size(); i++) {
				set.add(str1.get(i));
			}
			editor.putStringSet("Stringset",set);}
		else
			editor.putStringSet("Stringset",null);
		editor.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
		getprefs();
	}

	private void getprefs() {
		spHomeScreen=getSharedPreferences("spHomeScreen", 0);
		editor=spHomeScreen.edit();
		Set<String> set=new TreeSet<String>();
		set=spHomeScreen.getStringSet("StringSet", null);
		if(set!=null){
			str1=new ArrayList<String>();
			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				str1.add(string);	
			}
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		str1=new ArrayList<String>();
		init();
	}
}