package Resource;

import android.content.Context;
import android.content.SharedPreferences;

public class Static {
	final public static String GAME_CUBES_="CBUES_";
	final public static String GAME_CUBES_2="CUBES_2";
	final public static String GAME_CUBES_3="CUBES_3";
	final public static String GAME_CUBES_4="CUBES_4";
	final public static String GAME_CUBES_5="CUBES_4";
	
	final public static String SP_NAME_CONFIG="SP_NAME_CONFIG";
	final public static String SP_KEY_CUBES_2_POSITION="SP_KEY_CUBES_2_POSITION";
	final public static String SP_KEY_CUBES_3_POSITION="SP_KEY_CUBES_3_POSITION";
	final public static String SP_KEY_CUBES_4_POSITION="SP_KEY_CUBES_4_POSITION";
	final public static String SP_KEY_CUBES_2_STEP="SP_KEY_CUBE_2_STEP";
	final public static String SP_KEY_CUBES_3_STEP="SP_KEY_CUBE_3_STEP";
	final public static String SP_KEY_CUBES_4_STEP="SP_KEY_CUBE_4_STEP";
	final public static String SP_KEY_CUBES_2_TIME="SP_KEY_CUBE_2_TIME";
	final public static String SP_KEY_CUBES_3_TIME="SP_KEY_CUBE_3_TIME";
	final public static String SP_KEY_CUBES_4_TIME="SP_KEY_CUBE_4_TIME";
	
	final public static int HANDLER_STEP_PLUS=0;
	final public static int HANDLER_STEP_MINIUS=1;
	final public static int HANDLER_STEP_CLEAR=2;
	final public static int HANDLER_TIME_PLUS=0;
	final public static int HANDLER_TIME_MINIUS=1;
	final public static int HANDLER_TIME_CLEAR=2;
	
	public static Context context;
	public static SharedPreferences sp_config;
	public static boolean reTick;
	public static int steps=10;
	public static boolean animation=true;
	public static boolean timer=true;
	public static boolean saveData=false;
	public static void init(Context context){
		Static.context=context;
		sp_config=context.getSharedPreferences(SP_NAME_CONFIG, Context.MODE_PRIVATE);
		DB.init(context);
		Textures.init();
		
	}
}
