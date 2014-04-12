package AppUtils;

import javax.microedition.khronos.opengles.GL10;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class TextureUtil {
	public class RGBA{
		int r=255;
		int g=255;
		int b=255;
		int a=255;
	}
	
	public static int[] loadTextures(GL10 gl, Bitmap bitmap[]){
		int[] textures=new int[bitmap.length];
		gl.glGenTextures(bitmap.length, textures,0);
		for(int i=0;i<bitmap.length;i++){
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[i]);
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap[i], 0);
		}
		return textures;
	}
	
	public static int[] loadTextures(Resources resources, GL10 gl, int[] bitmapId){
		int[] textures=new int[bitmapId.length];
		gl.glGenTextures(bitmapId.length, textures, 0);
		Bitmap bitmap=null;
		for(int i=bitmapId.length-1;i>-1;i--){
			bitmap=BitmapFactory.decodeResource(resources, bitmapId[i]);
	        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[i]);
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		}
		bitmap.recycle();
		bitmap=null;
		return textures;
	}
	
	
	public Bitmap getBitmap(int r, int g, int b, int a, int size ,int width){
		RGBA [][] colorMatrix=new RGBA[size][size];
		for(int i=size-1;i>-1;i--){
			for(int j=size-1;j>-1;j--){
				colorMatrix[i][j]=new RGBA();
				colorMatrix[i][j].r=r;
				colorMatrix[i][j].g=g;
				colorMatrix[i][j].b=b;
				colorMatrix[i][j].a=a;
			}
		}
//		int step=a/width;
//		for(int i=0;i<width;i++){
//			for(int j=i;j<size-i;j++){
//				colorMatrix[i][j].a=step*(1+i);
//				colorMatrix[size-i-1][j].a=step*(1+i);
//				colorMatrix[j][i].a=step*(1+i);
//				colorMatrix[j][size-i-1].a=step*(1+i);
//			}
//		}
		int step=50;
		for(int i=0;i<5;i++){
			for(int j=i;j<size-i;j++){
				colorMatrix[i][j].a=step*(1+i);
				colorMatrix[size-i-1][j].a=step*(1+i);
				colorMatrix[j][i].a=step*(1+i);
				colorMatrix[j][size-i-1].a=step*(1+i);
			}
		}
		int [] colors=new int[size*size];
		for(int i=0,j=0;i<size;i++){
			for(j=0;j<size;j++){
				colors[i*size+j]=(colorMatrix[i][j].b << 0) | ( colorMatrix[i][j].g << 8) | (colorMatrix[i][j].r<< 16) | (colorMatrix[i][j].a << 24);
			}
		}
		return Bitmap.createBitmap(colors, size, size, Config.ARGB_8888);
	}
	
}
