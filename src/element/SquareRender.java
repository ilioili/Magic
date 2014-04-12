package element;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import main.activity.R;

import Resource.Static;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

public class SquareRender implements GLSurfaceView.Renderer{

	private float near=1;
	private float far=20;
	public float rx,ry,rz;
	private Mesh square;
	private Face face;
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		square.generate();
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		face.setTextureCoords(new float[]{0,1,0,0,1,1});
		face.setTextureId(textures[0]);
		face.initTextureBuffer();
        Bitmap bitmap=BitmapFactory.decodeResource(Static.context.getResources(), R.drawable.blue);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();
		bitmap=null;
		square.loadTexture(gl,textures[0]);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glClearDepthf(1.0f);                            
		gl.glEnable(GL10.GL_DEPTH_TEST);                        
		gl.glDepthFunc(GL10.GL_LEQUAL);                            
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
//		gl.glGenTextures(1, square.textures, 0);
//		gl.glBindTexture(GL10.GL_TEXTURE_2D, square.textureId);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		float ratio = (float) width / height;
	    gl.glViewport(0, 0, width, height);
	    gl.glMatrixMode(GL10.GL_PROJECTION);
	    gl.glLoadIdentity();
	    gl.glFrustumf(-ratio, ratio, -1, 1, near, far);
	    gl.glMatrixMode(GL10.GL_MODELVIEW);    
	    gl.glLoadIdentity();    
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -2f);
        gl.glRotatef(rz, 0, 0, 1);
        gl.glRotatef(ry, 0, 1, 0);
        face.draw(gl, Config.DRAW_WITH_COLOR);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -2f);
        gl.glRotatef(rz, -2, 0, 1);
        gl.glRotatef(ry, 0, 1, 0);
        face.draw(gl, Config.DRAW_WITH_TEXTURE);
	}

	public SquareRender(Mesh s){
		this.square=s;
		Vertex v0=new Vertex(new float[]{0,0,0},new float[]{0,0,1,1});
		Vertex v1=new Vertex(new float[]{0,1,0},new float[]{0,1,1,1});
		Vertex v2=new Vertex(new float[]{1,0,0},new float[]{1,0,1,1});
		this.face=new Face(new Vertex[]{v0,v1,v2});
		face.initColorBuffer();
		face.initVertexBuffer();
	}
	
}

