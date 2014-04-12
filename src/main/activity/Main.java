package main.activity;

import main.activity.R;

import potato.Potatos1Game;
import potato.Potatos2Game;
import pyramid.Pyramids3Game;
import cubes.Cubes2Game;
import cubes.Cubes3Game;
import cubes.Cubes4Game;
import eggplant.EggplantsGame;
import tomato.TomatosGame;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Main extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
        setContentView(R.layout.main);
	}
	public void cubes2(View v){
		android.opengl.Matrix m;
		startActivity(new Intent(this,Cubes2Game.class));
	}
	public void cubes3(View v){
		startActivity(new Intent(this,Cubes3Game.class));
	}
	public void cubes4(View v){
		startActivity(new Intent(this,Cubes4Game.class));
	}
	public void pyramids3(View v){
		startActivity(new Intent(this,Pyramids3Game.class));
	}
	public void tomatos(View v){
		startActivity(new Intent(this,TomatosGame.class));
	}
	public void eggplants(View v){
		startActivity(new Intent(this,EggplantsGame.class));
	}
	public void potatos1(View v){
		startActivity(new Intent(this,Potatos1Game.class));
	}
	public void potatos2(View v){
		startActivity(new Intent(this,Potatos2Game.class));
	}
	public void shapeTest(View v){
	}
}
