package cucumber;

import java.util.Random;
import main.activity.R;
import Resource.Static;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.SoundPool;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CucumberView extends GLSurfaceView{
	private boolean shapeTouched;
	private boolean layerChoosed;
	private float preX,preY,firstX,firstY;
	public CucumberRender render;
	private float dpx;
	private float dpy;
	private int music;
	private SoundPool sp;//声明一个SoundPool
	protected int times=600;
	protected int degree;
	private Vibrator vibrator;
	public CucumberView(Context context) {
		super(context);
		render=new CucumberRender();
		render.translateTo(0, 0, -4f);
		this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		this.setRenderer(render);
		this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		this.setZOrderOnTop(true);
		sp= new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = sp.load(Static.context, R.raw.cc, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
	}
	public CucumberView(Context context,AttributeSet paramAttributeSet) {//不加入第二个参数会导致XML解析异常
		super(context,paramAttributeSet);
		render=new CucumberRender();
		render.translateTo(0, 0, -4f);
		render.rotateTo(0, 45);
		this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		this.setRenderer(render);
		this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		this.setZOrderOnTop(true);
		sp= new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = sp.load(Static.context, R.raw.cc, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
	}
	public boolean onTouchEvent(MotionEvent e) {
		float dx=0;
		float dy=0;
		switch (e.getAction()& MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
        	this.requestRender();
        	preX=e.getX();
        	preY=e.getY();
        	firstX=preX;
        	firstY=preY;
        	shapeTouched=render.shapeTouched(firstX, firstY);
        	layerChoosed=false;
        	break;
        case MotionEvent.ACTION_UP:
        	render.fresh();
        	if(layerChoosed)	vibrator.vibrate(100);
        	this.requestRender();
        	dpx=0;
        	dpy=0;
        	break;
        case MotionEvent.ACTION_MOVE:
        	dx=e.getX()-preX;
        	dy=e.getY()-preY;
        	if(shapeTouched){
        		dpx+=dx;
        		dpy+=dy;
            	if(layerChoosed){
            		dx=(e.getX()>firstX?e.getX()-firstX:firstX-e.getX());
            		dy=(e.getY()>firstY?e.getY()-firstY:firstY-e.getY());
        			render.rotateLayerBy((dx+dy-40)/2);
//        			if(Math.abs(dpx)+Math.abs(dpy)>100)	sp.play(music, 1, 1, 0, 0, 1);
        		}else if(Math.abs(dpx)+Math.abs(dpy)>40){
        			layerChoosed=render.setLayer(firstX	,firstY, dpx, dpy);
        			sp.play(music, 1, 1, 0, 0, 1);
        		}
        	}else render.rotateTo(dy, dx);
        	preX=e.getX();
        	preY=e.getY();
        	this.requestRender();
        	break;
        }
		return true;
	}
	public void fresh(){
		final CucumberView p=this;
		new Handler().postDelayed(new Runnable(){
			public void run(){
				times--;
				render.rotateTo(0.1f, 0.2f);
				render.rotateLayerBy(degree++);
				if(degree==60) 	sp.play(music, 1, 1, 0, 0, 1);
				if(degree==120){
					render.fresh();
					Random r=new Random();
					render.setLayer(r.nextInt(8));
					degree=0;
				}
				if(times==0){
					render.fresh();
					times=600;
					Static.reTick=true;
				}else{
					fresh();
				}
				p.requestRender();
			}
		}, 10);
	}
	public void saveData(Context c){
		render.saveData(c);
	}
	public void loadData(Context c){
		render.loadData(c);
		render.update();
		this.requestRender();
	}
}


