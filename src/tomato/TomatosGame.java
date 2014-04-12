package tomato;

import main.activity.R;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class TomatosGame extends Activity {
	TomatosView tmv;
	TextView tv;
	long startTime;
	int time;
	private Vibrator vibrator;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
        setContentView(R.layout.tomatos);
        tmv=(TomatosView)findViewById(R.id.tomatos);
        tv=(TextView)findViewById(R.id.tomatoTime);
        tv.setText("Time Used: 0");
        timeTicker();
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
	}
	public void loadData(View v){
		final Context c=this;
		SharedPreferences userData=this.getSharedPreferences("cubeData", Context.MODE_PRIVATE);
		time=userData.getInt("time", 0);
		new Thread(new Runnable(){
			public void run(){
				tmv.loadData(c);
			}
		}).start();
		new AlertDialog.Builder(this)
        .setTitle("数据已加载").show();
		vibrator.vibrate(50);
	}
	public void saveData(View v){
		final Context c=this;
		SharedPreferences userData=c.getSharedPreferences("cubeData", Context.MODE_PRIVATE);
		userData.edit().putInt("time", time).commit();
		new Thread(new Runnable(){
			public void run() {
				tmv.saveData(c);
			}
		}).start();
		new AlertDialog.Builder(this)
        .setTitle("数据已保存").show();
		vibrator.vibrate(50);
	}
	public void timeTicker(){
//		if(Caculator.timer){
//			new Handler().postDelayed(new Runnable(){
//				public void run(){
//					if(Caculator.reTick){
//						time=-1;
//						Caculator.reTick=false;
//					}
//					time++;
//					tv.setText("Time Used: "+time);
//					timeTicker();
//				}
//	        }, 1000);
//		}
	}
	public void fresh(View v){
		tmv.fresh();
		vibrator.vibrate(50);
	}
	
	
}

