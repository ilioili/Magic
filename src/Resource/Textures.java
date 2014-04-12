package Resource;

import main.activity.R;
import android.graphics.Bitmap;


public class Textures {
	public static int cubesTextures1[];
	public static int cubesTextures0[];
	public static Bitmap[] bitmaps;
	public static void init(){
		cubesTextures0=new int[]{
				R.drawable.star_purple,
				R.drawable.star_green,
				R.drawable.star_red,
				R.drawable.star_skyblue,
				R.drawable.star_yellow,
				R.drawable.star_white,
				R.drawable.star_blue
			};
		cubesTextures1=new int[]{
				R.drawable.purple,
				R.drawable.yellow,
				R.drawable.red,
				R.drawable.white,
				R.drawable.green,
				R.drawable.blue,
				R.drawable.grey
		};
	}
}
