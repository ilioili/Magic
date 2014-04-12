package cubes;

import element.Config;
import AppUtils.TextureUtil;
import Resource.Static;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;

@SuppressLint("HandlerLeak")
public class Cubes2Game extends CubesActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	

	@Override
	public void initData() {
		this.N=2;
		this.position=Static.sp_config.getFloat(Static.SP_KEY_CUBES_2_POSITION, -10);
		this.layerIndices=new int[]{0,1,3,2,0,5,0,1,1,5};
		this.gameLevel=new int[][]{
				{0},
				{0,0},
				{0,1},
				{0,2},
				{0,2,0},
				{0,0,2},
				{0,2,4},
				{0,2,0,2},
				{0,2,4,0},
				{0,2,4,1},
				{0,4,1,2}
		};
		int edge=12;
		TextureUtil util=new TextureUtil();
		final Bitmap []bitmaps=new Bitmap[7];
		bitmaps[0]=util.getBitmap(255, 255, 	0, 		255, 	128, 	edge);//yellow
		bitmaps[1]=util.getBitmap(255, 0, 		0, 		255, 	128, 	edge);//red
		bitmaps[2]=util.getBitmap(0, 	255, 	0, 		255, 	128, 	edge);//green
		bitmaps[3]=util.getBitmap(0, 	0, 		255, 	255, 	128, 	edge);//blue
		bitmaps[4]=util.getBitmap(255, 255, 	255, 	255, 	128, 	edge);//white
		bitmaps[5]=util.getBitmap(0, 	255, 	255, 	255, 	128, 	edge);//skyblue
		bitmaps[6]=util.getBitmap(50,	50,	50, 	255, 	128, 	edge);//black
//        cv.setTextures(bitmaps);
//        cv.setDrawMode(Config.DRAW_WITH_TEXTURE);
        cv.translateTo(0, 0, position);
        cv.setDrawMode(Config.DRAW_WITH_COLOR);
        new Handler().postDelayed(new Runnable(){
        	public void run(){
              for(int i=0;i<7;i++){
    			bitmaps[i].recycle();
    			bitmaps[i]=null;
    		}
        	}
        }, 1000);
	}

	public void onPause(){
		super.onPause();
		Static.sp_config.edit().putFloat(Static.SP_KEY_CUBES_2_POSITION, position).commit();
	}



	
}

