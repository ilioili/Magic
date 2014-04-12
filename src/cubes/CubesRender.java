package cubes;

import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import element.Config;
import element.GL_OBJ;
import element.Shape;
import element.Vertex;
import AppUtils.Caculator;
import AppUtils.TextureUtil;
import Resource.Static;
import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;

public class CubesRender implements GLSurfaceView.Renderer{
	private boolean positive=true;//旋转方向
	private boolean isColorMode=true;
	private int [] resources;
	private int N=0;
	private int width;
	private int height;
	private float near=1;
	private float far=20;
	private float unit=1f;
	private float rx,ry,rz;
	private float cx,cy,cz;
	private boolean isBufferCreated=false;
	private Cubes cubes;
	private GL_OBJ obj_others;
	private GL_OBJ obj_layer;
	private int faceIndex;//left=0,right=1,front=2,back=3,top=4,bottom=5;
	private Bitmap[] bitmaps;
	private int [] textures;
	private Cubes cubesTemp;
	private ArrayList<Shape> tempCubeList;
	public void setLevel(int N){
		this.N=N;
	}
	public void setTextures(Bitmap[] bitmaps){
		this.bitmaps=bitmaps;
	}
	public void setTextures(int [] resouses){
		this.resources=resouses;
	}
	public void setDrawMode(int config){
		if(config==Config.DRAW_WITH_COLOR){
			this.isColorMode=true;
		}else if(config==Config.DRAW_WITH_TEXTURE){
			this.isColorMode=false;
		}
	}
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(cx, cy, cz);
        gl.glRotatef(rx, 1, 0, 0);
        gl.glRotatef(ry, 0, 1, 0);
        gl.glRotatef(rz, 0, 0, 1);
        try{
        	if(this.isColorMode){
    			obj_others.drawC(gl);
    	        obj_layer.drawC(gl);
    		}else{
    			obj_others.drawT(gl);
    	        obj_layer.drawT(gl);
    		}
        }catch(ArrayIndexOutOfBoundsException e){
        	e.printStackTrace();
        }catch(NullPointerException e){
        	e.printStackTrace();
        }
	}
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		float ratio = (float) width / height;
		this.width = width;
	    this.height = height;
	    gl.glViewport(0, 0, width, height);
	    gl.glMatrixMode(GL10.GL_PROJECTION);
	    gl.glLoadIdentity();
	    gl.glFrustumf(-ratio, ratio, -1, 1, near, far);
	    gl.glMatrixMode(GL10.GL_MODELVIEW);    
	    gl.glLoadIdentity();    
	}
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//		System.out.println("onSurfaceCreated(GL10 gl, EGLConfig config)");
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepthf(1.0f);                            
        gl.glEnable(GL10.GL_DEPTH_TEST);                        
        gl.glDepthFunc(GL10.GL_LEQUAL);                            
        gl.glEnable (GL10.GL_BLEND);
		gl.glBlendFunc (GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		init(gl);
	}
	public void setCamera(float near,float far){
		this.near = near;
		this.far = far;
	}
	public void translateTo(float x,float y,float z){
		this.cx = x;
		this.cy = y;
		this.cz = z;
	}
	public void rotateBy(float rx,float ry){
		this.rx += rx;
		while(this.rx>180)	this.rx-=360;
		while(this.rx<-180)	this.rx+=360;
		if(this.rx>90||this.rx<-90)		this.ry -= ry;
		else	this.ry += ry;
		while(this.ry>180)	this.ry-=360;
		while(this.ry<-180)	this.ry+=360;
	}
	public void rotateTo(float rx,float ry,float rz){
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
		while(this.rx>180)	this.rx -=360;
		while(this.rx<-180) this.rx += 360;
		while(this.ry>180)	this.ry -=360;
		while(this.ry<-180) this.ry += 360;
		while(this.rz>180)	this.rz -=360;
		while(this.rz<-180) this.rz += 360;
		
		System.out.println("this.ry="+this.ry);
	}
	public void init(GL10 gl){
//		System.out.println("init(GL10 gl)");
		tempCubeList=new ArrayList<Shape>();
		this.isBufferCreated=false;
		cubes = new Cubes(unit,N,0.1f);
		cubesTemp=new Cubes(unit,N);
		obj_layer=new GL_OBJ();
        obj_others=new GL_OBJ();
		if(this.isColorMode){
			updateC();
		}else{
			if(null==bitmaps){
				textures=TextureUtil.loadTextures(Static.context.getResources(), gl, resources);
			}else{
				textures=TextureUtil.loadTextures(gl, bitmaps);
			}
			cubes.initFaceTexture(textures);
			updateT();
		}
	}
	public void updateT(){
//		System.out.println("updateT()");
		obj_layer.clear();
		obj_others.clear();
		for(int i=0;i<cubes.layer.size();i++){
			obj_layer.addShape(cubes.layer.get(i));
		}
		for(int i=0;i<cubes.layerOther.size();i++){
			obj_others.addShape(cubes.layerOther.get(i));
		}
		if(isBufferCreated){
			obj_layer.reGenerateT();
			obj_others.reGenerateT();
		}else{
			isBufferCreated=true;
			obj_layer.generateT();
			obj_others.generateT();
		}
	}
	public void updateC(){
//		System.out.println("update()");
		obj_layer.clear();
		obj_others.clear();
		for(int i=0;i<cubes.layer.size();i++){
			obj_layer.addShape(cubes.layer.get(i));
		}
		for(int i=0;i<cubes.layerOther.size();i++){
			obj_others.addShape(cubes.layerOther.get(i));
		}
		obj_layer.generateC();
		obj_others.generateC();
}
	/**
	 * 设置当前要旋转的层
	 * @param layerIndex
	 */
	public void setLayer(int layerIndex){
//		System.out.println("setLayer(int layerIndex)");
		if(cubes.layerIndex==layerIndex){
			return;
		}
		cubes.setLayer(layerIndex);
		if(this.isColorMode){
			updateC();
		}else{
			updateT();
		}
		
	}

	/**
	 * 根据触点坐标及滑动位移来确定那一层要旋转
	 * @return true表示某一层被选中，false表示没有层被选中
	 */
	public boolean setLayer(float x,float y,float dx,float dy){
//		System.out.print("setLayer(float x,float y,float dx,float dy)");
		cubesTemp.copy(cubes);
		cubesTemp.rotateY(-ry);
		cubesTemp.rotateX(-rx);
		cubesTemp.move(new float[]{cx,cy,cz});
		tempCubeList.clear();
		for(int i=0;i<cubesTemp.shapeList.size();i++){
			if(cubesTemp.shapeList.get(i).isGlobeShot(x, y, near, width, height,1.7f*unit)){
				tempCubeList.add(cubesTemp.shapeList.get(i));
			}
		}
		if(tempCubeList.size()==0) return false;//射线没有拾取到正方形的球形包裹体，则退出
		while(tempCubeList.size()>1){//获取距离屏幕最近的正方形
			if(tempCubeList.get(0).origin.xyz[2]>tempCubeList.get(1).origin.xyz[2])
				tempCubeList.remove(1);
			else
				tempCubeList.remove(0);
		}
		int ic=0,jc=0,kc=0;//用于记录用来获取的正方形的位置
		boolean breakNow=false;
		for(int i=0;i<N;i++){
			if(breakNow) break;
			for(int j=0;j<N;j++){
				if(breakNow) break;
				for(int k=0;k<N;k++){
					if( i==0 |  i==N-1 | j==0 | j==N-1 | k==0 | k==N-1){
						if( cubesTemp.cubes[i][j][k].equals(tempCubeList.get(0)) ){
							ic=i;jc=j;kc=k;
							breakNow=true;
							break;
						}
					}
				}
			}
		}
		faceIndex=cubesTemp.cubes[ic][jc][kc].getFace(x, y, near, width, height);
		if( faceIndex == -1 ) return false;
		if(getRotateDirection(faceIndex, dx, dy)==-1){
			return false;//没有获取到匹配的方向则退出
		}else{
			cubes.setLayer(ic, jc, kc, getRotateDirection(faceIndex,dx,dy));//设置魔方要旋转的层
			if(this.isColorMode){
				updateC();//更新绘图数据
			}else{
				updateT();
			}
			return true;
		}
		
	}
	
	/**
	 * 计算旋转的方向（0表示绕x轴，1表示绕y轴，2表示绕z轴, -1表示未匹配到）
	 * 对 positive进行赋值，正表示顺时针，负表示相反方向
	 * @param faceIndex 射线拾取到的面的索引
	 * @param dx 屏幕滑动的dx
	 * @param dy 屏幕滑动的dy
	 * @return
	 */
	private int getRotateDirection(int faceIndex,float dx,float dy){
//		System.out.println("getRotateDirection(int faceIndex,float dx,float dy)\n");
		float xx[]=new float[]{dx,-dy};//屏幕坐标越往下Y越大，NDC坐标越往下Y越小，所以取反
		Vertex vx=new Vertex(new float[]{1,0,0});
		Vertex vy=new Vertex(new float[]{0,1,0});
		Vertex vz=new Vertex(new float[]{0,0,1});
		vx.rotateY(-ry);
		vx.rotateX(-rx);
		vx.move(new float[]{cx,cy,cz});
		vy.rotateY(-ry);
		vy.rotateX(-rx);
		vy.move(new float[]{cx,cy,cz});
		vz.rotateY(-ry);
		vz.rotateX(-rx);
		vz.move(new float[]{cx,cy,cz});
		switch(faceIndex){
		case Cube.left://left
			if(Caculator.match(xx,Caculator.projectionVertex(vz, near))){
				positive = Caculator.angle(xx, Caculator.projectionVertex(vz, near))>150;//OK
				return 1;
			}
			else if(Caculator.match(xx,Caculator.projectionVertex(vy, near))){
				positive = Caculator.angle(xx, Caculator.projectionVertex(vy, near))<30;//OK
				return 2;
			}
		case Cube.right://right
			if(Caculator.match(xx,Caculator.projectionVertex(vz, near))){
				positive = Caculator.angle(xx, Caculator.projectionVertex(vz, near))<30;//OK
				return 1;
			}
			else if(Caculator.match(xx,Caculator.projectionVertex(vy, near))){
				positive = Caculator.angle(xx, Caculator.projectionVertex(vy, near))>150;//OK
				return 2;
			}
		case Cube.front://front
			if(Caculator.match(xx,Caculator.projectionVertex(vy, near))){
				positive = Caculator.angle(xx, Caculator.projectionVertex(vy, near))<30;//OK
				return 0;
			}
			else if(Caculator.match(xx,Caculator.projectionVertex(vx, near))){
				positive = Caculator.angle(xx, Caculator.projectionVertex(vx, near))>150;//OK
				return 1;
			}
		case Cube.back://back
			if(Caculator.match(xx,Caculator.projectionVertex(vy, near))){
				positive = Caculator.angle(xx, Caculator.projectionVertex(vy, near))>150;//OK
				return 0;
			}
			else if(Caculator.match(xx,Caculator.projectionVertex(vx, near))){
				positive = Caculator.angle(xx, Caculator.projectionVertex(vx, near))<30;//OK
				return 1;
			}
		case Cube.top://top
			if(Caculator.match(xx,Caculator.projectionVertex(vz, near))){
				positive = Caculator.angle(xx, Caculator.projectionVertex(vz, near))>150;//OK
				return 0;
			}
			else if(Caculator.match(xx,Caculator.projectionVertex(vx, near))){
				positive = Caculator.angle(xx, Caculator.projectionVertex(vx, near))<30;//OK
				return 2;
			}
		case Cube.bottom://bottom
			if(Caculator.match(xx,Caculator.projectionVertex(vz, near))){
				positive = Caculator.angle(xx, Caculator.projectionVertex(vz, near))<30;//OK
				return 0;
			}
			else if(Caculator.match(xx,Caculator.projectionVertex(vx, near))){
				positive = Caculator.angle(xx, Caculator.projectionVertex(vx, near))>150;//OK
				return 2;
			}
		}
//		System.out.println("No Axis Matched"+"<CubeRender getRotateDirection()>");
		return -1;
	}
	
	public void fresh(){
//		System.out.println("fresh()");
		cubesTemp.copy(cubes);
		cubesTemp.setLayer(this.cubes.layerIndex);
		if(cubesTemp.layerIndex<N){
			cubesTemp.rotateLayer(-obj_layer.rx);
		}else if(cubesTemp.layerIndex<2*N){
			cubesTemp.rotateLayer(-obj_layer.ry);
		}else{
			cubesTemp.rotateLayer(obj_layer.rz);
		}
		obj_layer.rx=0;
		obj_layer.ry=0;
		obj_layer.rz=0;
		if(this.isColorMode){
			this.cubes.extractColor(cubesTemp);//更新cubes中的数据（做相应的旋转）
			updateC();
		}else{
			this.cubes.extractTexture(cubesTemp);
			updateT();
		}
	}
	
	public void rotateLayerTo(float degree){
//		System.out.println("rotateLayerTo(float degree)");
		if(positive)	degree=-degree;
//		left=0,right=N-1,front=N,back=2*N-1,top=2*N,bottom=3*N-1;
		if(cubes.layerIndex<N){
			obj_layer.rx = degree;
		}else if(cubes.layerIndex<2*N){
			obj_layer.ry = degree;
		}else if(cubes.layerIndex<3*N){
			obj_layer.rz = degree;
		}
	}
	
	/**
	 * 遍历立方体判断是否被射线拾取到
	 * @param x MotionEvent.getX();
	 * @param y MotionEvent.getY();
	 * @return
	 */
	public boolean isShapeTouched(float x,float y){
//		System.out.println("shapeTouched(float x,float y)");
		cubesTemp.copy(cubes);
		cubesTemp.rotateY(-ry);
		cubesTemp.rotateX(-rx);
		cubesTemp.move(new float[]{cx,cy,cz});
		for(int i=0;i<cubesTemp.shapeList.size();i++){
			if(cubesTemp.shapeList.get(i).isGlobeShot(x, y, near, width, height,1.7f*unit)){
				return true;
			}
		}
		return false;
		
	}
	
	public void saveData(Context context){
	}
	public void loadData(Context context){
	}
	public void setScale(float unit){
		this.unit=unit;
	}
	public String getTextures(){
		StringBuilder textures=new StringBuilder();
//		StringBuilder colors=new StringBuilder();
		for(int i=0;i<cubes.faceList.size();i++){
//			for(int j=0;j<4;j++){
//				colors.append(cubes.faceList.get(i).rgba[j]);
//				colors.append("*")
//			}
//			colors.append("@");
			textures.append(cubes.faceList.get(i).getTexture()+"");
			textures.append("@");
		}
		String str=textures.toString();
		str.replaceAll(""+this.textures[0]+"", "0");
		str.replaceAll(""+this.textures[1]+"", "1");
		str.replaceAll(""+this.textures[2]+"", "2");
		str.replaceAll(""+this.textures[3]+"", "3");
		str.replaceAll(""+this.textures[4]+"", "4");
		str.replaceAll(""+this.textures[5]+"", "5");
		str.replaceAll(""+this.textures[6]+"", "6");
		return textures.toString();
	}
	public void loadTextures(String textures){
		//需要在外面调用requestRender();
		String [] texturesId = textures.split("@");
		for(int i=0;i<cubes.faceList.size();i++){
			cubes.faceList.get(i).setTextureId(Integer.valueOf(texturesId[i]));
		}
		texturesId=null;
	}
}
