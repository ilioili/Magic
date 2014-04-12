package pyramid;

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

public class Pyramids3View extends GLSurfaceView{
	private boolean shapeTouched;
	private boolean layerChoosed;
	private float preX,preY,firstX,firstY;
	public Pyramids3Render render;
	private float dpx;
	private float dpy;
	private int music;
	private SoundPool sp;//����һ��SoundPool
	protected int times=5;
	protected int degree;
	private Vibrator vibrator;
	public Pyramids3View(Context context) {
		super(context);
		render=new Pyramids3Render();
		render.translateTo(0, 0, -6f);
		render.rotateTo(60, 0);
		this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		this.setRenderer(render);
		this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		this.setZOrderOnTop(true);
		sp= new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);//��һ������Ϊͬʱ���������������������ڶ����������ͣ�����Ϊ��������
        music = sp.load(Static.context, R.raw.cc, 1); //����������زķŵ�res/raw���2��������Ϊ��Դ�ļ�����3��Ϊ���ֵ����ȼ�
        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
	}
	public Pyramids3View(Context context,AttributeSet paramAttributeSet) {//������ڶ��������ᵼ��XML�����쳣
		super(context,paramAttributeSet);
		render=new Pyramids3Render();
		render.translateTo(0, 0, -5.5f);
		render.rotateTo(30, 30);
		this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		this.setRenderer(render);
		this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		this.setZOrderOnTop(true);
		sp= new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);//��һ������Ϊͬʱ���������������������ڶ����������ͣ�����Ϊ��������
        music = sp.load(Static.context, R.raw.cc, 1); //����������زķŵ�res/raw���2��������Ϊ��Դ�ļ�����3��Ϊ���ֵ����ȼ�
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
		final Pyramids3View p=this;
		new Handler().postDelayed(new Runnable(){
			public void run(){
				render.rotateTo(0.1f, 0.2f);
				render.rotateLayerBy(degree++);
				if(degree==60) 	sp.play(music, 1, 1, 0, 0, 1);
				if(degree==120){
					times--;
					render.fresh();
					Random r=new Random();
					render.setLayer(r.nextInt(8));
					degree=0;
				}
				if(times==0){
					render.fresh();
					times=5;
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
