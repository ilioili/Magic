package cubes;

import element.Config;
import Resource.Static;
import Resource.Textures;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;

public class Cubes3Game extends CubesActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	public void initData(){
//		int edge=10;
//		TextureUtil util=new TextureUtil();
//		Bitmap []bitmaps=new Bitmap[7];
//		bitmaps[0]=util.getBitmap(255, 255, 	0, 		255, 	256, 	edge);//yellow
//		bitmaps[1]=util.getBitmap(255, 0, 		0, 		255, 	256, 	edge);//red
//		bitmaps[2]=util.getBitmap(0, 	255, 	0, 		255, 	256, 	edge);//green
//		bitmaps[3]=util.getBitmap(0, 	0, 		255, 	255, 	256, 	edge);//blue
//		bitmaps[4]=util.getBitmap(255, 255, 	255, 	255, 	256, 	edge);//white
//		bitmaps[5]=util.getBitmap(0, 	255, 	255, 	255, 	256, 	edge);//skyblue
//		bitmaps[6]=util.getBitmap(50,	50,	50, 	255, 	256, 	edge);//black
        
//        cv.setTextures(bitmaps);
		this.N=3;
		this.position=Static.sp_config.getFloat(Static.SP_KEY_CUBES_3_POSITION, -12);
//        cv.setTextures(Textures.cubesTextures0);
//        cv.setDrawMode(Config.DRAW_WITH_TEXTURE);
        cv.translateTo(0, 0, position);
        layerIndices=new int[]{2,5,1,8,3};
        cv.setDrawMode(Config.DRAW_WITH_COLOR);
//        for(int i=0;i<7;i++){
//			bitmaps[i].recycle();
//			bitmaps[i]=null;
//		}
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		cv.requestRender();
	}


	public void onPause(){
		super.onPause();
		Static.sp_config.edit().putFloat(Static.SP_KEY_CUBES_3_POSITION, position).commit();
	}
}

