package cucumber;

import main.activity.R;

import Resource.Static;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class CucumberGame extends Activity{
	private int time;
	private TextView tv;
	public CucumberView ev;
	private Vibrator vibrator;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	        WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
	    setContentView(R.layout.cucumber);
	    ev=(CucumberView)findViewById(R.id.cucumber);
	    tv=(TextView)findViewById(R.id.cucumberTime);
	    tv.setText("Time Used: 0");
	    timeTicker();
	    vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
	}
	public void loadData(View v){
		final Context c=this;
		SharedPreferences userData=this.getSharedPreferences("pyramidData", Context.MODE_PRIVATE);
		time=userData.getInt("time", 0);
		new Thread(new Runnable(){
			public void run(){
				ev.loadData(c);
			}
		}).start();
		new AlertDialog.Builder(this, AlertDialog.THEME_TRADITIONAL)
        .setIconAttribute(android.R.attr.alertDialogIcon)
        .setTitle("数据已加载").show();
		vibrator.vibrate(50);
	}
	public void saveData(View v){
		final Context c=this;
		SharedPreferences userData=c.getSharedPreferences("pyramidData", Context.MODE_PRIVATE);
		userData.edit().putInt("time", time).commit();
		new Thread(new Runnable(){
			public void run() {
				ev.saveData(c);
			}
		}).start();
		new AlertDialog.Builder(this, AlertDialog.THEME_TRADITIONAL)
        .setIconAttribute(android.R.attr.alertDialogIcon)
        .setTitle("数据已保存").show();
		vibrator.vibrate(50);
	}
	public void timeTicker(){
		if(Static.timer){
			new Handler().postDelayed(new Runnable(){
				public void run(){
					if(Static.reTick){
						time=-1;
						Static.reTick=false;
					}
					time++;
					tv.setText("Time Used: "+time);
					timeTicker();
				}
	        }, 1000);
		}
	}
	public void fresh(View v){
		ev.fresh();
		vibrator.vibrate(50);
	}
}

