package Resource;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DB {
	final public static String DB_COUNT="DB_COUNT";
	final public static String DB_TIME="DB_TIME";
	final public static String DB_GAME="DB_GAME";
	final public static String DB_TEXTURES="DB_TEXTURES";
	final public static String DB_COLORS="DB_COLORS";
	final public static String DB_STEPS="DB_STEPS";
	final public static String DB_TIME_USED="DB_TIME_USED";
	
	final public static int DB_INDEX_COUNT=0;
	final public static int DB_INDEX_TIME=1;
	final public static int DB_INDEX_GAME=2;
	final public static int DB_INDEX_TEXTURES=3;
	final public static int DB_INDEX_COLORS=4;
	final public static int DB_INDEX_STEPS=5;
	final public static int DB_INDEX_TIME_USED=6;
	
	final public static String DATABASE_DATA= "GAME_DB";
	
	final private static String SQL_CREATE_DAIRY_TABLE = 
											"create table  if not exists " + 
											DATABASE_DATA+" (" +			//±íÃû
											DB_COUNT +" Text,"+
											DB_TIME+" Integer,"+
											DB_GAME+" Text,"+
											DB_TEXTURES+" Text,"+
											DB_COLORS+" Text,"+
											DB_STEPS+" Integer,"+
											DB_TIME_USED+" Integer)";
	
	public static SQLiteDatabase db;
	private static ContentValues values;
	
	public static void init(Context context){
		DatabaseHelper dbHelper=new DatabaseHelper(context, DATABASE_DATA);
		db = dbHelper.getWritableDatabase();
		db.execSQL(SQL_CREATE_DAIRY_TABLE);
		values = new ContentValues();
	}
	
	public static void saveData(String game,int steps,int timeUsed,String count,String textures,String colors){
		values.clear();
		values.put(DB_COLORS, colors);
		values.put(DB_TEXTURES, textures);
		values.put(DB_COUNT, count);
		values.put(DB_GAME, game);
		values.put(DB_TIME_USED, timeUsed);
		values.put(DB_TIME,System.currentTimeMillis());
		values.put(DB_STEPS,steps);
		db.insert(DATABASE_DATA, null, values);
	}
	
	
	
}
