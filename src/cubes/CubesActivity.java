package cubes;

import main.activity.R;
import Resource.DB;
import Resource.Static;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public abstract class CubesActivity extends Activity {
	public static boolean stopAnimation;
	public CubesView cv;
	public TextView tv_time,tv_step;
	public float position=-10;
	public long startTime;
	public int time;
	public int steps;
	public int N;
	public Handler handler;
	public Vibrator vibrator;
	public int [] layerIndices;
	public int [][] gameLevel;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
        setContentView(R.layout.cubes);
        cv=(CubesView)findViewById(R.id.cubeView);
        tv_time=(TextView)findViewById(R.id.time);
        tv_step=(TextView)findViewById(R.id.step);
        tv_time.setText("Time: 0");
        tv_step.setText("Steps: 0");
        handler=new Handler(){
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch(msg.what){
				case Static.HANDLER_STEP_PLUS:
					steps++;
					break;
				case Static.HANDLER_STEP_MINIUS:
					steps--;
					break;
				case Static.HANDLER_STEP_CLEAR:
					steps=0;
					break;
				}
        		tv_step.setText("Step: "+steps);
			}
        };
        cv.getSteps(handler);
        timeTicker();
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        initData();
        cv.setLevel(N);
        cv.rotateTo(30, 30, 0);
//		int edge=4;
//		TextureUtil util=new TextureUtil();
//		Bitmap []bitmaps=new Bitmap[7];
//		bitmaps[0]=util.getBitmap(255, 255, 	0, 		255, 	64, 	edge);//yellow
//		bitmaps[1]=util.getBitmap(255, 0, 		0, 		255, 	64, 	edge);//red
//		bitmaps[2]=util.getBitmap(0, 	255, 	0, 		255, 	64, 	edge);//green
//		bitmaps[3]=util.getBitmap(0, 	0, 		255, 	255, 	64, 	edge);//blue
//		bitmaps[4]=util.getBitmap(255, 255, 	255, 	255, 	64, 	edge);//white
//		bitmaps[5]=util.getBitmap(0, 	255, 	255, 	255, 	64, 	edge);//skyblue
//		bitmaps[6]=util.getBitmap(50,	50,	50, 	255, 	64, 	edge);//black
//        cv.setTextures(bitmaps);
//        cv.setLevel(2);
//        cv.setDrawMode(Config.DRAW_WITH_TEXTURE);
//        cv.translateTo(0, 0, position);
//        cv.setDrawMode(Config.DRAW_WITH_COLOR);
//        for(int i=0;i<7;i++){
//			bitmaps[i].recycle();
//			bitmaps[i]=null;
//		}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		cv.requestRender();
	}
	public void loadData(View v){
		loadData();
	}
	public void saveData(View v){
		saveData();
	}
	public abstract void initData();
	public void timeTicker(){
		if(Static.timer){
			new Handler().postDelayed(new Runnable(){
				public void run(){
					if(Static.reTick){
						time=-1;
						Static.reTick=false;
					}
					time++;
					tv_time.setText("Time: "+time);
					timeTicker();
				}
	        }, 1000);
		}
	}
	public void fresh(View v){
		cv.messUp(layerIndices);
	}
	public void onClick_zoom_out(View v){
		vibrator.vibrate(50);
		position-=0.2;
		cv.translateTo(0, 0, position);
		cv.requestRender();
	}
	public void onClick_zoom_in(View v){
		vibrator.vibrate(50);
		position+=0.2;
		cv.translateTo(0, 0, position);
		cv.requestRender();
	}
	public void onPause(){
		super.onPause();
		stopAnimation=true;
	}
	public void loadData(){
		Cursor cursor=DB.db.query(DB.DATABASE_DATA, null, DB.DB_GAME+"=?", new String[]{Static.GAME_CUBES_+N	}, null, null, null);
		cursor.moveToFirst();
		cv.loadTextures(cursor.getString(DB.DB_INDEX_TEXTURES));
		cv.requestRender();
		steps=cursor.getInt(DB.DB_INDEX_STEPS);
		time=cursor.getInt(DB.DB_INDEX_TIME_USED);
		new AlertDialog.Builder(this)
        .setTitle("数据已载入").show();
	}
	public void saveData(){
		DB.saveData(Static.GAME_CUBES_+N, steps,time,"default",cv.getTextures(),"");
		new AlertDialog.Builder(this)
        .setTitle("数据已保存").show();
	}
}

