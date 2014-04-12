package cucumber;

import java.util.ArrayList;
import java.util.Random;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import potato.Potatos2;
import cucumber.Cucumbers;
import element.Face;
import element.GL_OBJ;
import element.Shape;
import element.Vertex;
import AppUtils.Caculator;
import Resource.MyColors;
import Resource.Static;
import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.os.Handler;

public class CucumberRender implements GLSurfaceView.Renderer{
	private boolean positive=true;//旋转方向
	private int width;
	private int height;
	private float near=1;
	private float far=20;
	private float unit=0.1f;
	private float rx,ry,rz;
	private float cx,cy,cz;
	private Cucumbers cucumbers;
	private GL_OBJ obj_others;
	private GL_OBJ obj_layer;
	private int faceIndex;//left=0,right=1,front=2,back=3,top=4,bottom=5;
	protected int layerDegree;

	@Override
	public void onDrawFrame(GL10 gl) {
		try{
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	        gl.glLoadIdentity();
	        gl.glTranslatef(cx, cy, cz);
	        gl.glRotatef(rx, 1, 0, 0);
	        gl.glRotatef(ry, 0, 1, 0);
	        gl.glRotatef(rz, 0, 0, 1);
	        obj_others.drawC(gl);
	        obj_layer.drawC(gl);
	        gl.glFinish();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		float ratio = (float) width / height;
		this.width = width;
		this.height = height;
	    gl.glViewport(0, 0, width, height);
	    gl.glMatrixMode(GL10.GL_PROJECTION);
	    gl.glLoadIdentity();
	    gl.glOrthof(-ratio, ratio, -1, 1, near, far);
	    gl.glMatrixMode(GL10.GL_MODELVIEW);    
	    gl.glLoadIdentity();    
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepthf(1.0f);                            
        gl.glEnable(GL10.GL_DEPTH_TEST);                        
        gl.glDepthFunc(GL10.GL_LEQUAL);                            
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
	   	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	   	gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        objCreated();
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
	public void rotateTo(float rx,float ry,float rz){
		this.rx += rx;
		this.ry += ry;
		this.rz += rz;
		while(rx>180)	this.rx -=360;
		while(ry>180)	this.ry -=360;
		while(rz>180)	this.rz -=360;
	}
	public void rotateTo(float rx,float ry){
		this.rx += rx;
		if(this.rx>180)	this.rx-=360;
		else if(this.rx<-180)	this.rx+=360;
		
		if(this.rx>90||this.rx<-90)		this.ry -= ry;
		else	this.ry += ry;
		if(this.ry>180)	this.ry-=360;
		else if(this.ry<-180)	this.ry+=360;
	}
	public void objCreated(){
        obj_layer=new GL_OBJ();
        obj_others=new GL_OBJ();
        cucumbers=new Cucumbers(unit);
        update();
	}
	public void update(){
		obj_layer.clear();
		obj_others.clear();
		for(int i=0;i<cucumbers.layer.size();i++)	obj_layer.addShape(cucumbers.layer.get(i));
		for(int i=0;i<cucumbers.layerOther.size();i++)	obj_others.addShape(cucumbers.layerOther.get(i));
		obj_layer.generateC();
		obj_others.generateC();
}
	public void animation(){
		if(Static.animation){
			new Handler().postDelayed(new Runnable(){
				public void run() {
					if(cucumbers!=null){
						rotateTo(0.1f, 0.2f, 0.3f);
						layerDegree+=2;
						rotateLayerBy(layerDegree);
						if(layerDegree==120){
							fresh();
							Random r=new Random();
							setLayer(r.nextInt(11));
							layerDegree=0;
						}
					}
					animation();
				}
			}, 10);
		}
	}
	
	public boolean setLayer(float x,float y,float dx,float dy){
		Cucumbers c=new Cucumbers(unit);
		c.rotateY(-ry);
		c.rotateX(-rx);
		c.move(cx,cy,cz);
		ArrayList <Shape> sl=new ArrayList<Shape>();
		ArrayList <Face>	 fl=new ArrayList<Face>();
		for(int i=0;i<c.potatoList.size();i++){{//获取所有看被射线拾取的Shape
			if(c.potatoList.get(i).isShot(x, y, width, height) )
				sl.add(c.potatoList.get(i));
			}
		}
		if(sl.size()==0) return false;//射线没有拾取到正三棱锥
		for(int i=0;i<sl.size();i++){//获取所有被射线拾取的面
			for(int j=0;j<sl.get(i).faceList.size();j++){
				if( Caculator.isFaceShot(x, y, sl.get(i).faceList.get(j), width, height) )	fl.add(sl.get(i).faceList.get(j));
			}
		}
		while(fl.size()>1){//获取最近的面
			if(Caculator.getCrossPointZ(x, y, fl.get(0), width, height)>Caculator.getCrossPointZ(x, y, fl.get(1), near, width, height))	fl.remove(1);
			else	fl.remove(0);
		}
		
		int ic=-1;
		for(int i=0;i<c.potatoList.size();i++){//获取面所在的Shape
			for(int j=0;j<c.potatoList.get(i).faceList.size();j++){
				if(c.potatoList.get(i).faceList.get(j).equals(fl.get(0))){
					ic=i;
//					p.potatoList.get(i).faceList.get(j).setColor(Caculator.black);
					break;
				}
				if(ic>-1)	break;
			}
		}
		System.out.println("shapeIndex="+ic);
		faceIndex=fl.get(0).faceIndex;
		System.out.println("faceIndex="+faceIndex);
		int rotateDirection=getRotateDirection(faceIndex,dx,dy);
		System.out.println("rotateDierction="+rotateDirection);
		if( faceIndex == -1 ) {
			return false;
		}
		if(getRotateDirection(faceIndex, dx, dy)==-1){//获取旋转轴
			return false;//没有获取到匹配的方向则退出
		}else{
			cucumbers.setLayer(faceIndex,ic, rotateDirection);//设置魔方要旋转的层
//			modify();
			update();//更新绘图数据
			return true;
		}
	}
	public void setLayer(int layerIndex){
		cucumbers.setLayer(layerIndex);
		update();
	}
	public int getRotateDirection(int faceIndex,float dx,float dy){
//		System.out.println("faceIndex="+faceIndex+"---PyramidRender getRotateDirection()");
		float a,a1,a2,a3,aa1,aa2,aa3;
		float xx[]=new float[]{dx,-dy};//屏幕坐标越往下Y越大，NDC坐标越往下Y越小，所以取反
		Vertex v1,v2,v3;
		v1=new Vertex();
		v2=new Vertex();
		v3=new Vertex();
		switch(faceIndex){
		case 0:
			v1=new Vertex(new float[]{0,1,0});
			v2=new Vertex(new float[]{0,0,-1});
			v3=new Vertex(new float[]{0,1,1});
			break;
		case 1:
			v1=new Vertex(new float[]{0,-1,0});
			v2=new Vertex(new float[]{0,0,1});
			v3=new Vertex(new float[]{0,1,-1});
			break;
		case 2:
			v1=new Vertex(new float[]{0,1,0});
			v2=new Vertex(new float[]{-1,0,0});
			v3=new Vertex(new float[]{1,1,0});
			break;
		case 3:
			v1=new Vertex(new float[]{0,-1,0});
			v2=new Vertex(new float[]{1,0,0});
			v3=new Vertex(new float[]{-1,1,0});
			break;
		case 4:
			v1=new Vertex(new float[]{0,0,-1});
			v2=new Vertex(new float[]{1,0,0});
			v3=new Vertex(new float[]{1,0,-1});
			break;
		case 5:
			v1=new Vertex(new float[]{0,0,1});
			v2=new Vertex(new float[]{-1,0,0});
			v3=new Vertex(new float[]{1,0,1});
			break;
		case 6:
			v1=new Vertex(new float[]{0,0,1});
			v2=new Vertex(new float[]{-1,0,0});
			v3=new Vertex(new float[]{1,0,1});
			break;
		case 7:
			v1=new Vertex(new float[]{0,0,1});
			v2=new Vertex(new float[]{-1,0,0});
			v3=new Vertex(new float[]{1,0,1});
			break;
		}
		v1.rotateY(-ry);
		v1.rotateX(-rx);
		v2.rotateY(-ry);
		v2.rotateX(-rx);
		v3.rotateY(-ry);
		v3.rotateX(-rx);
		a1=Caculator.angle(xx, new float[]{v1.xyz[0],v1.xyz[1]});
		a2=Caculator.angle(xx, new float[]{v2.xyz[0],v2.xyz[1]});
		a3=Caculator.angle(xx, new float[]{v3.xyz[0],v3.xyz[1]});
//		System.out.println("a1="+a1);
//		System.out.println("a2="+a2);
//		System.out.println("a3="+a3);
//		System.out.println("a4="+a4);
		aa1=a1;
		aa2=a2;
		aa3=a3;
		if(a1>90)	a1=180-a1;
		if(a2>90)	a2=180-a2;
		if(a3>90)	a3=180-a3;
		a=(a1<a2?a1:a2);
		a=(a<a3?a:a3);
		if(a==a1){
			if(aa1>90)	positive=true;
			else positive=false;
			return 0;
		}else if(a==a2){
			if(aa2>90)	positive=true;
			else positive=false;
			return 1;
		}else{
			if(aa3>90)	positive=true;
			else positive=false;
			return 2;
		}
	}
//	private void modify(){
//		int i=cucumbers.layerIndex;
//		switch(faceIndex){
//		case 0:
//		case 1:
//		case 2:
//		case 3:if(i>9)	positive=!positive;break;
//		case 4:if(i==6|i==7) positive=!positive;break;
//		case 5:if(i==12|i==13) positive=!positive;break;
//		}
//	}
	public void fresh(){
		//把旋转的角度划归到-180度到+180度之间的120度整数倍位置
		while(obj_layer.angle<-180)		obj_layer.angle	+= 360;
		while(obj_layer.angle>180)		obj_layer.angle	-= 360;
		if(obj_layer.angle<-135)	obj_layer.angle = -180;
		else if(obj_layer.angle<-45)	obj_layer.angle = -90;
		else if(obj_layer.angle<45)	obj_layer.angle = 0;
		else if(obj_layer.angle<135)	obj_layer.angle = 90;
		else obj_layer.angle = 180;
		cucumbers.layerRotate(obj_layer.angle);
		cucumbers.fresh();//更新pyramids中的数据（做相应的旋转）
		obj_layer.angle=0;
		update();
	}
	public void rotateLayerBy(float degree){
		if(positive)	 degree=-degree;
		obj_layer.angle = degree;
		switch(cucumbers.layerIndex){//需要判断顺时针还是逆时针
		case 0:
		case 1:
			obj_layer.xyz = new float[]{0,-1,0};
			break;
		case 2:
		case 3:
			obj_layer.xyz = new float[]{-1,0,0};
			break;
		case 4:
		case 5:
			obj_layer.xyz = new float[]{0,0,-1};
			break;
		case 6:
			obj_layer.xyz = new float[]{-1,1,1};
			break;
		case 7:
			obj_layer.xyz = new float[]{1,1,1};
			break;
		case 8:
			obj_layer.xyz = new float[]{1,1,-1};
			break;
		case 9:
			obj_layer.xyz = new float[]{-1,1,-1};
			break;
		case 10:
			obj_layer.xyz = new float[]{-1,-1,1};
			break;
		case 11:
			obj_layer.xyz = new float[]{1,-1,1};
			break;
		case 12:
			obj_layer.xyz = new float[]{1,-1,-1};
			break;
		case 13:
			obj_layer.xyz = new float[]{-1,-1,-1};
			break;
		}
		update();
	}
	public boolean shapeTouched(float x,float y){
		Potatos2 p=new Potatos2(unit);
		p.rotateY(-ry);
		p.rotateX(-rx);
		p.move(cx, cy, cz);
		for(int i=0;i<p.potatoList.size();i++){
			if( p.potatoList.get(i).isShot(x, y, near, width, height) )
				return true;
		}
		return false;
	}
	public void saveData(Context context){
		SharedPreferences userData = context.getSharedPreferences("pyramidData", Context.MODE_PRIVATE);
		for(int i=0;i<cucumbers.potatoList.size();i++){
			for(int j=0;j<cucumbers.potatoList.get(i).faceList.size();j++){
				String rgb="";
				for(int k=0;k<3;k++){
					rgb+=cucumbers.potatoList.get(i).faceList.get(j).rgba[k];
				}
				userData.edit().putString(i+"-"+j, rgb).commit();
			}
		}
		userData.edit().putFloat("rx",rx).commit();
		userData.edit().putFloat("ry",ry).commit();
	}
	public void loadData(Context context){
		SharedPreferences userData = context.getSharedPreferences("pyramidData", Context.MODE_PRIVATE);
		for(int i=0;i<cucumbers.potatoList.size();i++){
			for(int j=0;j<cucumbers.potatoList.get(i).faceList.size();j++){
				if(userData.getString(i+"-"+j, "").equals("0.01.00.0"))
					cucumbers.potatoList.get(i).faceList.get(j).setColor(MyColors.green);
				else if(userData.getString(i+"-"+j, "").equals("1.01.00.0"))
					cucumbers.potatoList.get(i).faceList.get(j).setColor(MyColors.yellow);
				else if(userData.getString(i+"-"+j, "").equals("0.01.01.0"))
					cucumbers.potatoList.get(i).faceList.get(j).setColor(MyColors.blue);
				else if(userData.getString(i+"-"+j, "").equals("1.00.00.0"))
					cucumbers.potatoList.get(i).faceList.get(j).setColor(MyColors.red);
				else if(userData.getString(i+"-"+j, "").equals("1.01.01.0"))
					cucumbers.potatoList.get(i).faceList.get(j).setColor(MyColors.white);
				else if(userData.getString(i+"-"+j, "").equals("0.50.00.5"))
					cucumbers.potatoList.get(i).faceList.get(j).setColor(MyColors.purple);
			}
		}
		this.rx=userData.getFloat("rx", rx);
		this.ry=userData.getFloat("ry", ry);
	}
	public void setScale(float unit){
		this.unit=unit;
		objCreated();
	}
}
