package com.example.gremnemonics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;
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
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class DisplayItems extends Activity implements OnQueryTextListener, OnItemClickListener, OnClickListener{
	String ListValue;
	int rowValue=0;
	String QueryValue;
	ArrayAdapter<String> adapter;
	ListView lvDynamicWords;
	SearchView svWordSearch;
	MnemonicDB db;
	ArrayList<String> nameValues,str1;
	ArrayList<String> newNameValues;
	SharedPreferences spDisplayItems;
	ProgressBar pbStatusIndicator;
	ImageButton ibnHome;
	ImageButton ibnRandomize,ibnListIcon,ibnStatsIcon,ibnSettings;
	Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displayitems);
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		db=new MnemonicDB(getApplicationContext());
		db=(MnemonicDB) bundle.getParcelable("database");
		nameValues=bundle.getStringArrayList("NameValues");
		db.setcontext(getApplicationContext());
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameValues);
		lvDynamicWords=(ListView) findViewById(R.id.lvDynamicWords);
		lvDynamicWords.setAdapter(adapter);
		svWordSearch=(SearchView) findViewById(R.id.svWordSearch);
		svWordSearch.setOnQueryTextListener(this);
		ibnHome=(ImageButton) findViewById(R.id.ibnHome);
		ibnHome.setOnClickListener(this);
		pbStatusIndicator=(ProgressBar) findViewById(R.id.pbStatusIndicator);
		lvDynamicWords.setOnItemClickListener(this); ibnRandomize=(ImageButton) findViewById(R.id.ibnRandomise);
		ibnListIcon=(ImageButton) findViewById(R.id.ibnListIcon);
		ibnStatsIcon=(ImageButton) findViewById(R.id.ibnStatsIcon);
		ibnSettings=(ImageButton) findViewById(R.id.ibnSettings);
		ibnRandomize.setOnClickListener(this);
		ibnListIcon.setOnClickListener(this);
		ibnStatsIcon.setOnClickListener(this);	
		ibnSettings.setOnClickListener(this);
		newNameValues=new ArrayList<String>();
		db.open();
		str1=db.getRowNames(1001);
		int a[]=db.getCount();
		int j=a[6]+a[7]+a[8]+a[9]+a[10]+a[11]+a[12]+a[13]+a[14]+a[15];
		pbStatusIndicator.setProgress((int)((j)/10));
		if(db.getProgressStat().contentEquals("FALSE"))
			pbStatusIndicator.setVisibility(View.INVISIBLE);
		db.close();
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent=new Intent(this, Display.class);
		Bundle bundle=new Bundle();
		bundle.putParcelable("database", db);
		db.open();
		int rowID=db.getRowId((String) arg0.getItemAtPosition(arg2));
		System.out.println("onitemclick row id "+rowID);
		newNameValues=db.getRowNames(getRowValue(rowID));
		db.close();
		bundle.putStringArrayList("NameValues", newNameValues);
		bundle.putString("Source", (String) arg0.getItemAtPosition(arg2));
		intent.putExtras(bundle);
		startActivity(intent);
	}
	@Override
	public boolean onQueryTextChange(String newText) {
		newText=newText.toLowerCase();
		adapter.getFilter().filter(newText);
		return true;
	}
	@Override
	public boolean onQueryTextSubmit(String query) {
		return true;
	}
	public int getRowValue(int rowID){
		if(rowID>900){
			rowID=900;
		}else if(rowID>800){
			rowID=800;
		}else if(rowID>700){
			rowID=700;
		}else if(rowID>600){
			rowID=600;
		}else if(rowID>500){
			rowID=500;
		}else if(rowID>400){
			rowID=400;
		}else if(rowID>300){
			rowID=300;
		}else if(rowID>200){
			rowID=200;
		}else if(rowID>100){
			rowID=100;
		} 
		else rowID=1;
		return rowID;
	}
	@Override
	protected void onResume() {
		super.onResume();
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameValues);
		lvDynamicWords.setAdapter(adapter);
		getprefs();
	}
	@Override
	protected void onPause() {
		super.onPause();
		saveprefs();
	}
	private void saveprefs() {
		spDisplayItems=getSharedPreferences("spDispItemsScreen", 0);
		editor=spDisplayItems.edit(); 
		Set<String> set=new TreeSet<String>();
		if(nameValues!=null){
			for (int i = 0; i < nameValues.size(); i++) {
				set.add(nameValues.get(i));
			}
			editor.putStringSet("Stringset",set);}
		else
			editor.putStringSet("Stringset",null);
		editor.commit();
	}
	private void getprefs() {
		spDisplayItems=getSharedPreferences("spDispItemsScreen", 0);
		editor=spDisplayItems.edit();
		Set<String> set=new TreeSet<String>();
		set=spDisplayItems.getStringSet("StringSet", null);
		if(set!=null){
			nameValues=new ArrayList<String>();
			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				nameValues.add(string);	
			}
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

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
		case R.id.ibnListIcon:
			Intent listIcon=new Intent(this,DisplayItems.class);
			Bundle bundle1=new Bundle();
			bundle1.putParcelable("database", db);
			bundle1.putStringArrayList("NameValues",this.str1);
			listIcon.putExtras(bundle1); 
			startActivity(listIcon);
			break;
		case R.id.ibnStatsIcon:
			Intent stats=new Intent(getApplicationContext(), Stats.class);
			Bundle bundle2=new Bundle();
			bundle2.putParcelable("database", db);
			bundle2.putStringArrayList("NameValues", str1);
			stats.putExtras(bundle2);
			startActivity(stats);
			break;
		case R.id.ibnSettings:
			Intent settings=new Intent(getApplicationContext(), Settings.class);
			Bundle bundle3=new Bundle();
			bundle3.putParcelable("database", db);
			bundle3.putStringArrayList("NameValues", str1);
			settings.putExtras(bundle3);
			startActivity(settings);
			break;
		case R.id.ibnHome:
			Intent inten=new Intent(getApplicationContext(), HomeScreen.class);
			startActivity(inten);
			break;
		}
	}
}
