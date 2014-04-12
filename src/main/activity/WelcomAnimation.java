package main.activity;

import cubes.Cubes2Game;
import cubes.CubesRender;
import element.Config;
import main.activity.R;

import Resource.Static;
import Resource.Textures;
import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;

public class WelcomAnimation extends Activity{
	GLSurfaceView view;
	public float z=-20;
	public float rx,ry,rz;
	private CubesRender render;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Static.init(getApplicationContext());
        view=new GLSurfaceView(this);
        render=new CubesRender();
        render.setLevel(3);
        render.setDrawMode(Config.DRAW_WITH_TEXTURE);
        render.setTextures(Textures.cubesTextures0);
        render.translateTo(0, 0, -30);
        view.setRenderer(render);
        setContentView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        animation1();
//        startActivity(new Intent(this,Cubes2Game.class));
//        startActivity(new Intent(WelcomAnimation.this,Main.class));
//        finish();
    }

    public void animation1(){
    	new Handler().postDelayed(new Runnable(){
			public void run() {
				z+=0.01;
				rx+=0.001;
				render.translateTo(0, 0, z);
				render.rotateTo(rx, ry, rz);
				view.requestRender();
				if(z<-10){
					animation1();
				}else{
					animation2();
				}
			}
        }, 2);
    }
    public void animation2(){
    	new Handler().postDelayed(new Runnable(){
			public void run() {
				z-=0.01;
				rx-=0.001;
				render.translateTo(0, 0, z);
				render.rotateTo(rx, ry, rz);
				view.requestRender();
				if(z>-5){
					animation2();
				}else{
					animation3();
				}
			}
        }, 2);
    }
    public void animation3(){
    	new Handler().postDelayed(new Runnable(){
			public void run() {
				z-=0.05;
				rz+=0.001;
				ry+=0.002;
				render.rotateTo(rx, ry, rz);
				view.requestRender();
				if(z>-20f){
					animation3();
				}else{
					finish();
					startActivity(new Intent(WelcomAnimation.this,Main.class));
				}
			}
        }, 3);
    }
    public void welcome(){
    	
    }
}
