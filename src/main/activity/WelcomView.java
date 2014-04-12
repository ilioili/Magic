package main.activity;

import cubes.CubesRender;
import element.Config;
import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class WelcomView extends GLSurfaceView{

	private CubesRender render;

	public WelcomView(Context context) {
		super(context);
		this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		render=new CubesRender();
		this.setRenderer(render);
		this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}
	
	public WelcomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		render=new CubesRender();
		this.setRenderer(render);
		this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		render.setDrawMode(Config.DRAW_WITH_TEXTURE);
	}
	public void translateTo(float x,float y,float z){
		render.translateTo(x, y, z);
	}
	public void rotateTo(float rx,float ry,float rz){
		render.rotateTo(rx,ry,rz);
	}

}
