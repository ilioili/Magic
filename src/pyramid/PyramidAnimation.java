package pyramid;


import Resource.Static;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class PyramidAnimation extends GLSurfaceView{
	private Pyramids3Render pr;
	public PyramidAnimation(Context context, AttributeSet attrs) {
		super(context, attrs);
		pr=new Pyramids3Render();
		pr.translateTo(0, 0, -6);
		pr.setScale(0.5f);
		this.setRenderer(pr);
	}
	@Override
	public void onPause() {
		Static.animation=false;
		super.onPause();
	}
	@Override
	public void onResume(){
		Static.animation=true;
		pr.animation();
		super.onResume();
	}
}
