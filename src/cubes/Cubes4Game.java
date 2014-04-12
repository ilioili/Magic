package cubes;

import element.Config;
import AppUtils.TextureUtil;
import Resource.Static;
import Resource.Textures;
import android.os.Bundle;
import android.os.Handler;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

public class Cubes4Game extends CubesActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void initData(){
//		int edge=20;
//		TextureUtil util=new TextureUtil();
//		Bitmap []bitmaps=new Bitmap[7];
//		bitmaps[0]=util.getBitmap(255, 255, 	0, 		255, 	256, 	edge);//yellow
//		bitmaps[1]=util.getBitmap(255, 0, 		0, 		255, 	256, 	edge);//red
//		bitmaps[2]=util.getBitmap(0, 	255, 	0, 		255, 	256, 	edge);//green
//		bitmaps[3]=util.getBitmap(0, 	0, 		255, 	255, 	256, 	edge);//blue
//		bitmaps[4]=util.getBitmap(255, 255, 	255, 	255, 	256, 	edge);//white
//		bitmaps[5]=util.getBitmap(0, 	255, 	255, 	255, 	256, 	edge);//skyblue
//		bitmaps[6]=util.getBitmap(50,	50,	50, 	255, 	256, 	edge);//black
		this.position=Static.sp_config.getFloat(Static.SP_KEY_CUBES_4_POSITION, -14);
		this.layerIndices=new int[]{2,5,1,3,9,11,10,4,8,3};
		this.N=4;
//        cv.setTextures(bitmaps);
//		cv.setTextures(Textures.cubesTextures1);
//        cv.setDrawMode(Config.DRAW_WITH_TEXTURE);
        cv.translateTo(0f, 0, position);
        
        cv.setDrawMode(Config.DRAW_WITH_COLOR);
//        for(int i=0;i<7;i++){
//		   bitmaps[i].recycle();
//		   bitmaps[i]=null;
//		}
	}

	
	public void onPause(){
		super.onPause();
		Static.sp_config.edit().putFloat(Static.SP_KEY_CUBES_4_POSITION, position).commit();
	}
}

