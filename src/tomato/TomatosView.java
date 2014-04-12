package tomato;

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

public class TomatosView extends GLSurfaceView{
	private boolean shapeTouched;
	private boolean layerChoosed;
	private float preX,preY,firstX,firstY;
	public TomatosRender render;
	private float dpx;
	private float dpy;
	private int music;
	private SoundPool sp;//声明一个SoundPool
	protected int layerDegree;
	protected int degree;
	protected int times=5;
	private Vibrator vibrator;
	
	public TomatosView(Context context) {
		super(context);
		render=new TomatosRender();
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
	public TomatosView(Context context,AttributeSet paramAttributeSet) {//不加入第二个参数会导致XML解析异常
		super(context,paramAttributeSet);
		render=new TomatosRender();
		render.translateTo(0, 0, -4f);
		this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		this.setRenderer(render);
		this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
//		this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
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
        	System.out.println(shapeTouched);
        	layerChoosed=false;
        	break;
        case MotionEvent.ACTION_UP:
        	render.fresh();
        	if(layerChoosed)	vibrator.vibrate(50);
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
        			render.rotateLayerBy((dx+dy-20)/2);
        		}else if(Math.abs(dpx)+Math.abs(dpy)>20){
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
	public void saveData(Context context){
		render.saveData(context);
	}
	public void loadData(Context context){
		render.loadData(context);
		render.update();
		this.requestRender();
	}
	public void fresh(){
		final TomatosView t=this;
		new Handler().postDelayed(new Runnable(){
			public void run(){
				render.rotateTo(0.3f,0.1f);
				render.rotateLayerBy(degree++);
				if(degree==60)	sp.play(music, 1, 1, 0, 0, 1);
				if(degree==120){
					render.fresh();
					times--;
					Random r=new Random();
					render.setLayer(r.nextInt(7));
					degree=0;
				}
				if(times==0){
					render.fresh();
					times=5;
					Static.reTick=true;
				}else{
					fresh();
				}
				t.requestRender();
			}
		}, 10);
	}
	
}

