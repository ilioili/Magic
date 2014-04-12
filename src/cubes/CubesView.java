package cubes;

import java.util.Timer;
import java.util.TimerTask;

import main.activity.R;
import Resource.Static;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.SoundPool;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CubesView extends GLSurfaceView {
	protected int degree;
	private float dx;
	private float dy;
	private float lastDx;
	private float lastDy;
//	private float rx;
//	private float ry;
	private Handler handler;
	/**
	 * �Ƿ����ڽ��ж���
	 */
	private boolean isFreshing;
	/**
	 * �����¼��Ƿ����ĳһ�㱻ѡ��
	 */
	private boolean isLayerChoosed;
	/**
	 * �����������������Ƿ�ʰȡ��
	 */
	private boolean isShapeTouched;
	
	private int layerIndex;
	private int music;
	private float firstX, firstY;
	public CubesRender render;
	private SoundPool sp;// ����һ��SoundPool
	private Vibrator vibrator;
	private boolean isVibrated;
	public CubesView(Context context) {
		super(context);
		init(context);
	}

	public CubesView(Context context, AttributeSet paramAttributeSet) {// ������ڶ��������ᵼ��XML�����쳣
		super(context, paramAttributeSet);
		init(context);
	}

	public void getSteps(Handler handler) {
		this.handler = handler;
	}

	public String getTextures() {
		return render.getTextures();
	}

	private void init(Context context) {
		render = new CubesRender();
		this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		this.setRenderer(render);
		this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		this.setZOrderOnTop(true);
		sp = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);// ��һ������Ϊͬʱ���������������������ڶ����������ͣ�����Ϊ��������
		music = sp.load(Static.context, R.raw.cc, 1); // ����������زķŵ�res/raw���2��������Ϊ��Դ�ļ�����3��Ϊ���ֵ����ȼ�
		vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public void loadData(Context context) {
		render.loadData(context);
		render.updateC();
		this.requestRender();
	}

	public void loadTextures(String textures) {
		render.loadTextures(textures);
	}

	/**
	 * ����ħ��
	 * 
	 * @param layerIndices
	 */
	public void messUp(int[] layerIndices) {
		if (isFreshing)
			return;
		isFreshing = true;
		CubesActivity.stopAnimation = false;
		messUpAnimation(layerIndices);
	}

	private void messUpAnimation(final int[] layerIndices) {
		if (CubesActivity.stopAnimation)
			return;
		System.out.println(CubesActivity.stopAnimation);
		isFreshing = true;
		if (degree == 0) {
			render.setLayer(layerIndices[layerIndex]);
		}
		new Handler().postDelayed(new Runnable() {
			public void run() {
				degree += 2;
				render.rotateBy(0.5f, 0.7f);
				render.rotateLayerTo(degree);
				requestRender();
				if (degree == 30)
					sp.play(music, 1, 1, 0, 0, 1);
				if (degree == 90) {
					vibrator.vibrate(50);
					degree = 0;
					layerIndex++;
					new Handler().postDelayed(new Runnable() {
						public void run() {
							render.fresh();
							if (layerIndex == layerIndices.length) {
								layerIndex = 0;
								isFreshing = false;
							} else {
								render.setLayer(layerIndices[layerIndex]);
								messUpAnimation(layerIndices);
							}
							requestRender();
						}
					}, 100);
				} else {
					messUpAnimation(layerIndices);
				}
			}
		}, 10);
	}

	public boolean onTouchEvent(MotionEvent e) {
		if (isFreshing)
			return true;
		switch (e.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			isVibrated = false;
			firstX = e.getX();
			firstY = e.getY();
			lastDx = lastDy = 0;
			isShapeTouched = render.isShapeTouched(firstX, firstY);
			isLayerChoosed = false;
			break;
		case MotionEvent.ACTION_UP:
			if (isLayerChoosed){
				autoRotate();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			dx = e.getX() - firstX;
			dy = e.getY() - firstY;
			int curDx = (int) (dx-lastDx)/2;
			int curDy = (int) (dy-lastDy)/2;
			lastDx = dx;
			lastDy = dy;
			if (isShapeTouched) {
				if (!isLayerChoosed && Math.abs(dx) + Math.abs(dy) > 40) {
					isLayerChoosed = render.setLayer(firstX, firstY, dx, dy);
				}
				if (isLayerChoosed) {
					if(!isVibrated){
						vibrator.vibrate(50);
						isVibrated = true;
					}
					degree = (int) (Math.abs(dx) + Math.abs(dy) - 40)/2;
					render.rotateLayerTo(degree);
				}
			} else {
				render.rotateBy(curDy, curDx);
			}
			break;
		}
		this.requestRender();
		return true;
	}

	private void autoRotate() {
		if(degree%90==0){
			render.fresh();
			requestRender();
		}else{
			final Timer timer = new Timer(true);
			final TimerTask task;
			task = new TimerTask() {
				public void run() {
					if(degree%2==1){
						degree ++;
					}else{
						degree += 2;
					}
					render.rotateLayerTo(degree);
					if(degree%90==0){
						degree = 0;
						render.fresh();
						timer.cancel();
					}
					requestRender();
				}
			};
			timer.schedule(task,0,10);
		}
		
	}

	public void rotateTo(float rx, float ry, float rz) {
		render.rotateTo(rx, ry, rz);
	}

	public void saveData(Context context) {
		render.saveData(context);
	}

	/**
	 * @param config
	 *            ������Ⱦ��ģʽ����ɫ������ͼ
	 */
	public void setDrawMode(int config) {
		render.setDrawMode(config);
	}

	/**
	 * ����ħ���Ľ���
	 * 
	 * @param N
	 */
	public void setLevel(int N) {
		render.setLevel(N);
	}

	/**
	 * ����������ͼ
	 * 
	 * @param bitmaps
	 *            �������������
	 */
	public void setTextures(Bitmap[] bitmaps) {
		render.setTextures(bitmaps);
	}

	/**
	 * ����������ͼ����Դ
	 * 
	 * @param resousesId
	 */
	public void setTextures(int[] resousesId) {
		render.setTextures(resousesId);
	}

	public void translateTo(float x, float y, float z) {
		render.translateTo(x, y, z);
	}
}
