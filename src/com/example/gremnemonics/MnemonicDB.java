package com.example.gremnemonics;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.os.Parcelable;


public class MnemonicDB  implements Parcelable{
	public static final String KEY_ROWID="_id";
	public static final String KEY_NAME="word_name";
	public static final String KEY_MEANING="word_meaning";
	public static final String KEY_MNEMONIC="word_mnemonic";
	public static final String KEY_COLOR="word_color";
	public static final String KEY_STAT="word_stat";
	private static final String DATABASE_NAME="MnemonicDb";
	private static final String DATABASE_TABLE="MnemonicTable";
	private static final int DATABASE_VERSION=1;
	private DbHelper ourdbHelper;
	private  Context ourcontext;
	private SQLiteDatabase ourdatabase;
	private int mData;

	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE "+DATABASE_TABLE+" ("+
					KEY_ROWID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
					KEY_NAME+" TEXT UNIQUE NOT NULL, "+
					KEY_MEANING+" TEXT  NOT NULL, "+
					KEY_MNEMONIC+" TEXT  NOT NULL, "+
					KEY_COLOR+" TEXT  NOT NULL, "+
					KEY_STAT+" TEXT NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
			onCreate(db); 
		}
		public void deleteDB(SQLiteDatabase db){
			db.execSQL("DROP TABLE "+DATABASE_TABLE);
			onCreate(db); 
		}

	}

	public MnemonicDB(Context context) {
		ourcontext=context;
	}
	public void setcontext(Context context){
		ourcontext=context;
	}

	@SuppressWarnings("static-access")
	public MnemonicDB open() throws SQLException{
		if(ourdbHelper==null){
			ourdbHelper=new DbHelper(ourcontext);
		}
		ourdatabase=ourdbHelper.getWritableDatabase();
		//ourdatabase.openDatabase("/data/data/com.example.gremnemonics/databases/MnemonicDb", null, 0);
		return this;
	}
	public void close(){
		//ourdatabase.close();
		ourdbHelper.close();
	}
	public void emptyDB(){
		ourdbHelper.deleteDB(ourdatabase);
	}


	public long createEntry(String name, String meaning,String mnemonic,String color,String stat) {
		ContentValues cv=new ContentValues();
		cv.put(KEY_NAME, name);
		cv.put(KEY_MEANING,meaning);
		cv.put(KEY_MNEMONIC,mnemonic);
		cv.put(KEY_COLOR,color);
		cv.put(KEY_STAT, stat);
		return(ourdatabase.insert(DATABASE_TABLE, null, cv));
	}
	public void deletedb(){
		ourdbHelper.deleteDB(ourdatabase);
	}

	public String getDBName(){
		return DATABASE_NAME;
	}
	public String getData() throws SQLException{
		String[] columns=new String[]{KEY_ROWID,KEY_NAME,KEY_MEANING,KEY_MNEMONIC,KEY_COLOR,KEY_STAT};
		Cursor cursor=ourdatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String str="";
		int iRow=cursor.getColumnIndex(KEY_ROWID);
		int iName=cursor.getColumnIndex(KEY_NAME);
		int iMeaning=cursor.getColumnIndex(KEY_MEANING);
		int iMnemonic=cursor.getColumnIndex(KEY_MNEMONIC);
		int iColor=cursor.getColumnIndex(KEY_COLOR);
		int iStat=cursor.getColumnIndex(KEY_STAT);
		for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
			str=str+cursor.getString(iRow)+" "+cursor.getString(iName)+" "+cursor.getString(iMeaning)+" "+cursor.getString(iMnemonic)+" "+cursor.getString(iColor)+cursor.getString(iStat)+"\n";
		}
		return str;
	}


	public String getName(long rowid) throws SQLException{
		String[] columns=new String[]{KEY_ROWID,KEY_NAME};
		Cursor cursor=ourdatabase.query(DATABASE_TABLE, columns, KEY_ROWID+"="+rowid, null, null, null, null);
		if(cursor!=null){
			cursor.moveToFirst();
			String str= cursor.getString(1);
			cursor.close();
			return str;
		}
		return null;
	}


	public String getMeaning(String word) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns=new String[]{KEY_ROWID,KEY_MEANING};
		Cursor cursor=ourdatabase.query(DATABASE_TABLE, columns, KEY_NAME+"="+"'"+word+"'", null, null, null, null);
		if(cursor!=null){
			cursor.moveToFirst();
			String str= cursor.getString(1);
			cursor.close();
			return str;
		}
		return null;
	}

	public String getMnemonic(String word) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns=new String[]{KEY_ROWID,KEY_MNEMONIC};
		Cursor cursor=ourdatabase.query(DATABASE_TABLE, columns,KEY_NAME+"="+"'"+word+"'", null, null, null, null);
		if(cursor!=null){
			cursor.moveToFirst();
			String str= cursor.getString(1);
			cursor.close();
			return str;
		}
		return null;
	}	

	public String getColor(String word) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns=new String[]{KEY_NAME,KEY_COLOR};
		Cursor cursor=ourdatabase.query(DATABASE_TABLE, columns, KEY_NAME+"="+"'"+word+"'", null, null, null, null);
		if(cursor!=null){
			cursor.moveToFirst();
			String str= cursor.getString(1);
			cursor.close();
			return str;
		}
		return null;
	}

	public void updateColor(String word,  String mColor) throws SQLException{
		ContentValues values=new ContentValues();
		values.put(KEY_COLOR, mColor);		
		ourdatabase.update(DATABASE_TABLE, values, KEY_NAME+"="+"'"+word+"'", null);
	}

	public void resetColor() throws SQLException{
		ContentValues values=new ContentValues();
		values.put(KEY_COLOR, "BLUE");		
		ourdatabase.update(DATABASE_TABLE, values, null, null);
	}

	public void resetStat(String stat) throws SQLException{
		ContentValues values=new ContentValues();
		values.put(KEY_STAT, stat);		
		ourdatabase.update(DATABASE_TABLE, values, KEY_ROWID+"="+"1", null);
	}
	public void resetUID(String stat) throws SQLException{
		ContentValues values=new ContentValues();
		values.put(KEY_STAT, stat);		
		ourdatabase.update(DATABASE_TABLE, values, KEY_ROWID+"="+"998", null);
	}

	public void resetSurvey(String stat) throws SQLException{
		ContentValues values=new ContentValues();
		values.put(KEY_STAT, stat);		
		ourdatabase.update(DATABASE_TABLE, values, KEY_ROWID+"="+"999", null);
	}
	public void resetDBStat() throws SQLException{
		ContentValues values=new ContentValues();
		values.put(KEY_STAT, "FALSE");		
		ourdatabase.update(DATABASE_TABLE, values, KEY_ROWID+"="+"1", null);
	}
	public int[] getCount() throws SQLException{
		// TODO Auto-generated method stub
		String[] columns=new String[]{KEY_ROWID,KEY_COLOR};
		int a[]=new int[16];
		Cursor cursor = null;
		int count1=0,count101=0,count201=0,count301=0,count401=0,count501=0,count601=0,count701=0,count801=0,count901=0;
		int blueCount=0,redCount=0,orangeRedCount=0,yellowCount=0,lightGreenCount=0,greenCount=0;
		for(int j=1;j<=1000;j++){
			cursor=ourdatabase.query(DATABASE_TABLE, columns, KEY_ROWID+"="+j, null, null, null, null);
			if(cursor!=null){
				cursor.moveToFirst();
				if(cursor.getString(1).contentEquals("BLUE"))
					blueCount++;
				else if(cursor.getString(1).contentEquals("RED"))
					redCount++;
				else if(cursor.getString(1).contentEquals("ORANGERED"))
					orangeRedCount++;
				else if(cursor.getString(1).contentEquals("YELLOW"))
					yellowCount++;
				else if(cursor.getString(1).contentEquals("GREENYELLOW"))
					lightGreenCount++;
				else if(cursor.getString(1).contentEquals("GREEN"))
					greenCount++;

				if(!cursor.getString(1).contentEquals("BLUE"))
					if(j>=1 && j<=100){
						count1++;}
					else if(j>=101 && j<=200){
						count101++;
					}else if(j>=201 && j<=300){
						count201++;
					}else if(j>=301 && j<=400){
						count301++;
					}else if(j>=401 && j<=500){
						count401++;
					}else if(j>=501 && j<=600){
						count501++;
					}else if(j>=601 && j<=700){
						count601++;
					}else if(j>=701 && j<=800){
						count701++;
					}else if(j>=801 && j<=900){
						count801++;
					}else if(j>=901 && j<=1000){
						count901++;
					}
			}
			cursor.close();
		}
		if(cursor!=null){
			cursor.close();
		}
		a[0]=blueCount;a[1]=redCount;a[2]=orangeRedCount;a[3]=yellowCount;a[4]=lightGreenCount;a[5]=greenCount;
		a[6]=count1;a[7]=count101;a[8]=count201;a[9]=count301;a[10]=count401;
		a[11]=count501;a[12]=count601;a[13]=count701;a[14]=count801;a[15]=count901;
		return a;
	}	

	public ArrayList<String> getRowNames(int i) {
		String[] columns=new String[]{KEY_ROWID,KEY_NAME};
		Cursor  cursor = null;		
		ArrayList<String> str=new ArrayList<String>();
		if(i!=1001){
			for(int j=i;j<(i+100);j++){
				cursor=ourdatabase.query(DATABASE_TABLE, columns, KEY_ROWID+"="+j, null, null, null, null);
				if(cursor!=null){
					cursor.moveToFirst();
					str.add(cursor.getString(1));
					cursor.close();
				}
			}
		}
		else  
		{
			for(int j=1;j<i;j++){
				cursor=ourdatabase.query(DATABASE_TABLE, columns, KEY_ROWID+"="+j, null, null, null, null);
				if(cursor!=null){
					cursor.moveToFirst();
					str.add(cursor.getString(1));
					cursor.close();
				}
			}
		}
		if(cursor!=null){
			cursor.close();
		}
		return str;
	}

	/*public int[] getRowCount() {
		String[] columns=new String[]{KEY_ROWID,KEY_COLOR};
		int count1=0,count101=0,count201=0,count301=0,count401=0,count501=0,count601=0,count701=0,count801=0,count901=0;
		int[] a=new int[10];
		for(int j=1;j<=1000;j++){
			Cursor cursor=ourdatabase.query(DATABASE_TABLE, columns, KEY_ROWID+"="+j, null, null, null, null);
			if(cursor!=null){
				cursor.moveToFirst();
				if(!cursor.getString(1).contentEquals("BLUE"))
					if(j>=1 && j<=100){
						count1++;}
					else if(j>=101 && j<=200){
						count101++;
					}else if(j>=201 && j<=300){
						count201++;
					}else if(j>=301 && j<=400){
						count301++;
					}else if(j>=401 && j<=500){
						count401++;
					}else if(j>=501 && j<=600){
						count501++;
					}else if(j>=601 && j<=700){
						count601++;
					}else if(j>=701 && j<=800){
						count701++;
					}else if(j>=801 && j<=900){
						count801++;
					}else if(j>=901 && j<=1000){
						count901++;
					}
			}
		}
		a[0]=count1;a[1]=count101;a[2]=count201;a[3]=count301;a[4]=count401;
		a[5]=count501;a[6]=count601;a[7]=count701;a[8]=count801;a[9]=count901;
		return a;
	}*/


	public String[] getRowStatus() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getProgressStat() {
		String[] columns=new String[]{KEY_ROWID,KEY_STAT};
		Cursor cursor=ourdatabase.query(DATABASE_TABLE, columns, KEY_ROWID+"="+"1", null, null, null, null);
		if(cursor!=null){
			cursor.moveToFirst();
			String str=cursor.getString(1);
			cursor.close();
			return str;
		}
		return null;
	}
	public String getUID() {
		String[] columns=new String[]{KEY_ROWID,KEY_STAT};
		Cursor cursor=ourdatabase.query(DATABASE_TABLE, columns, KEY_ROWID+"="+"998", null, null, null, null);
		if(cursor!=null){
			cursor.moveToFirst();
			String str=cursor.getString(1);
			cursor.close();
			return str;
		}
		return null;
	}
	public String getDBStat() throws CursorIndexOutOfBoundsException{
		String[] columns=new String[]{KEY_ROWID,KEY_STAT};
		try{
			Cursor cursor=ourdatabase.query(DATABASE_TABLE, columns, KEY_ROWID+"="+"1000", null, null, null, null);
			if(cursor!=null){
				cursor.moveToFirst();
				String str=cursor.getString(1);
				cursor.close();
				return str;
			}
		}
		catch(CursorIndexOutOfBoundsException e){
			return null;
		}
		return null;
	}
	public String getSurveyStat() throws CursorIndexOutOfBoundsException{
		String[] columns=new String[]{KEY_ROWID,KEY_STAT};
		try{
			Cursor cursor=ourdatabase.query(DATABASE_TABLE, columns, KEY_ROWID+"="+"999", null, null, null, null);
			if(cursor!=null){
				cursor.moveToFirst();
				String str=cursor.getString(1);
				cursor.close();
				return str;
			}
		}
		catch(CursorIndexOutOfBoundsException e){
			return null;
		}
		return null;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(mData);
	}
	public static final Parcelable.Creator<MnemonicDB> CREATOR = new Parcelable.Creator<MnemonicDB>() {
		public MnemonicDB createFromParcel(Parcel in) {
			return new MnemonicDB(in);
		}

		public MnemonicDB[] newArray(int size) {
			return new MnemonicDB[size];
		}
	};

	// example constructor that takes a Parcel and gives you an object populated with it's values
	private MnemonicDB(Parcel in) {
		mData = in.readInt();
	}
	public int getRowId(String word) {
		// TODO Auto-generated method stub
		String[] columns=new String[]{KEY_ROWID,KEY_NAME};
		Cursor cursor=ourdatabase.query(DATABASE_TABLE, columns,  KEY_NAME+"="+"'"+word+"'", null, null, null, null);
		if(cursor!=null){
			cursor.moveToFirst();
			int a=cursor.getInt(0);
			cursor.close();
			return(a);
		}
		return 0;
	}
	public void testColor() throws SQLException{
		ContentValues values=new ContentValues();
		values.put(KEY_COLOR, "GREEN");		
		ourdatabase.update(DATABASE_TABLE, values, null, null);
	}

}
