package tomato;

import java.util.ArrayList;
import java.util.Random;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

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

public class TomatosRender implements GLSurfaceView.Renderer{
	private boolean positive=true;//旋转方向
	private int width;
	private int height;
	private float near=1;
	private float far=20;
	private float unit=1;
	private float rx,ry,rz;
	private float cx,cy,cz;
	private Tomatos tomatos;
	private GL_OBJ obj_others;
	private GL_OBJ obj_layer;
	private int faceIndex;//left=0,right=1,front=2,back=3,top=4,bottom=5;
	protected int layerDegree;
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
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glClearDepthf(1.0f);                            
		gl.glEnable(GL10.GL_DEPTH_TEST);                        
		gl.glDepthFunc(GL10.GL_LEQUAL);                            
		gl.glEnable (GL10.GL_BLEND);
		gl.glBlendFunc (GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
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
	public void rotateTo(float rx,float ry){
		this.rx += rx;
		if(this.rx>180)	this.rx-=360;
		else if(this.rx<-180)	this.rx+=360;
		if(this.rx>90||this.rx<-90)		this.ry -= ry;
		else	this.ry += ry;
		if(this.ry>180)	this.ry-=360;
		else if(this.ry<-180)	this.ry+=360;
	}
	public void rotateTo(float rx,float ry,float rz){
		this.rx += rx;
		this.ry += ry;
		this.rz += rz;
		while(rx>180)	this.rx -=360;
		while(ry>180)	this.ry -=360;
		while(rz>180)	this.rz -=360;
	}
	public void objCreated(){
		tomatos = new Tomatos(unit);
        obj_layer=new GL_OBJ();
        obj_others=new GL_OBJ();
        update();
	}
	public void update(){
		obj_layer.clear();
		obj_others.clear();
		for(int i=0;i<3;i++)	obj_layer.addShape(tomatos.layer.get(i));
		for(int i=0;i<10;i++)	obj_others.addShape(tomatos.layerOther.get(i));
		obj_layer.generateC();
		obj_others.generateC();
}
	public void setLayer(int layerIndex){
		tomatos.setLayer(layerIndex);
		update();
	}
	public void animation(){
		if(Static.animation){
			new Handler().postDelayed(new Runnable(){
				public void run() {
					if(tomatos!=null){
						rotateTo(0.1f, 0.2f, 0.3f);
						rotateLayerBy(layerDegree++);
						if(layerDegree==120){
							fresh();
							Random r=new Random();
							setLayer(r.nextInt(7));
							layerDegree=0;
						}
					}
					animation();
				}
			}, 10);
		}
	}
	public boolean setLayer(float x,float y,float dx,float dy){
		Tomatos t = new Tomatos(unit);
		t.rotateY(-ry);
		t.rotateX(-rx);
		t.move(cx,cy,cz);
		ArrayList <Shape> tl=new ArrayList<Shape>();
		ArrayList <Face>	 fl=new ArrayList<Face>();
		for(int i=0;i<t.tomatoList.size();i++){
			if(t.tomatoList.get(i).isShot(x, y, near,width, height))
					tl.add(t.tomatoList.get(i));
		}
		for(int i=0;i<tl.size();i++){//获取所有被射线拾取的面
			for(int j=0;j<tl.get(i).faceList.size();j++){
				if( Caculator.isInTrangleArea(x, y, tl.get(i).faceList.get(j),near, width, height) )
					fl.add(tl.get(i).faceList.get(j));
			}
		}
		while(fl.size()>1){//获取最近的面
			if(Caculator.getCrossPointZ(x, y, fl.get(0), near, width, height)>Caculator.getCrossPointZ(x, y, fl.get(1), near, width, height))	fl.remove(1);
			else	fl.remove(0);
		}
		faceIndex=fl.get(0).faceIndex;
		if( faceIndex == -1 ) return false;
		int ic=-1;
		for(int i=0;i<12;i++){//获取面所在的Shape
			for(int j=0;j<2;j++){//只需扫面前两个面
				if(t.tomatoList.get(i).faceList.get(j).equals(fl.get(0))){
					ic=i;
					break;
				}
				if(ic>-1)	break;//减少扫描次数
			}
		}
		boolean b=getRotateDirection(faceIndex,dx,dy);
		tomatos.setLayer(faceIndex, ic,b);
		if(b){
			switch(faceIndex){
			case 0:
				if(ic==0|ic==9)	positive=!positive;break;
			case 1:
				if(ic==2|ic==10) positive=!positive;break;
			case 2:
				if(ic==1|ic==5) positive=!positive;break;
			case 3:
				if(ic==3|ic==6) positive=!positive;break;
			case 4:
				if(ic==4|ic==11) positive=!positive;break;
			case 5:
				if(ic==6|ic==10) positive=!positive;break;
			}
			}else{
				switch(faceIndex){
				case 0:
					if(ic==0|ic==8)	positive=!positive;break;
				case 1:
					if(ic==2|ic==11) positive=!positive;break;
				case 2:
					if(ic==1|ic==4) positive=!positive;break;
				case 3:
					if(ic==3|ic==7) positive=!positive;break;
				case 4:
					if(ic==7|ic==11) positive=!positive;break;
				case 5:
					if(ic==5|ic==10) positive=!positive;break;
			}
		}
		
		update();//更新绘图数据
		return true;//表明层拾取成功
	}
	public boolean getRotateDirection(int faceIndex,float dx,float dy){
		float xx[]=new float[]{dx,-dy};//屏幕坐标越往下Y越大，NDC坐标越往下Y越小，所以取反
		float a1,a2;
		Vertex v1=new Vertex();
		Vertex v2=new Vertex();
		switch(faceIndex){
		case 0://left
			v1=new Vertex(new float[]{0,2,2});
			v2=new Vertex(new float[]{0,-2,2});
			break;
		case 1://right
			v1=new Vertex(new float[]{0,2,-2});
			v2=new Vertex(new float[]{0,-2,-2});
			break;
		case 2://front
			v1=new Vertex(new float[]{2,2,0});
			v2=new Vertex(new float[]{2,-2,0});
			break;
		case 3://back
			v1=new Vertex(new float[]{-2,2,0});
			v2=new Vertex(new float[]{-2,-2,0});
			break;
		case 4://top
			v1=new Vertex(new float[]{2,0,-2});
			v2=new Vertex(new float[]{2,0,2});
			break;
		case 5://bottom
			v1=new Vertex(new float[]{2,0,2});
			v2=new Vertex(new float[]{2,0,-2});
			break;
		}
		v1.rotateY(-ry);
		v1.rotateX(-rx);
		v1.move(new float[]{cx,cy,cz});
		v2.rotateY(-ry);
		v2.rotateX(-rx);
		v2.move(new float[]{cx,cy,cz});
		a1=Caculator.angle(xx, new float[]{v1.xyz[0],v1.xyz[1]});
		a2=Caculator.angle(xx, new float[]{v2.xyz[0],v2.xyz[1]});
		System.out.println("a1="+a1);
		System.out.println("a2="+a2);
		float aa1=a1;
		float aa2=a2;
		if(a1>90) a1=180-a1;
		if(a2>90) a2=180-a2;
		if(a1<a2){
			if(aa1>90)	positive=true;
			else	positive=false;
			return true;
		}else{
			if(aa2<90)	positive=true;
			else	positive=false;
			return false;
		}
	}
	public void fresh(){
		while(obj_layer.angle<-180)		obj_layer.angle	+= 360;
		while(obj_layer.angle>180)		obj_layer.angle	-= 360;
		if(obj_layer.angle<-60)	obj_layer.angle = -120;
		else if(obj_layer.angle<60)	obj_layer.angle = 0;
		else obj_layer.angle = 120;
		tomatos.layerRotate(-obj_layer.angle);
		tomatos.fresh();//更新tomatos中的数据（做相应的旋转）
		obj_layer.angle=0;
		update();
	}
	public void rotateLayerBy(float degree){
		if(positive)	degree=-degree;
		switch(tomatos.layerIndex){
		case 0:
			obj_layer.xyz=new float[]{-1,1,1};
			break;
		case 1:
			obj_layer.xyz=new float[]{1,1,1};
			break;
		case 2:
			obj_layer.xyz=new float[]{1,1,-1};
			break;
		case 3:
			obj_layer.xyz=new float[]{-1,1,-1};
			break;
		case 4:
			obj_layer.xyz=new float[]{-1,-1,1};
			break;
		case 5:
			obj_layer.xyz=new float[]{1,-1,1};
			break;
		case 6:
			obj_layer.xyz=new float[]{1,-1,-1};
			break;
		case 7:
			obj_layer.xyz=new float[]{-1,-1,-1};
			break;
		}
		obj_layer.angle=degree;
	}
	public boolean shapeTouched(float x,float y){
		Tomatos t=new Tomatos(unit);
		t.rotateY(-ry);
		t.rotateX(-rx);
		t.move(cx, cy, cz);
		for(int i=0;i<t.tomatoList.size();i++){
			if( t.tomatoList.get(i).isShot(x, y,near,width, height) ){
				return true;
			}
				
		}
		return false;
	}
	public void saveData(Context context){
		final SharedPreferences userData = context.getSharedPreferences("cubes4Data", Context.MODE_PRIVATE);
		for(int i=0;i<tomatos.tomatoList.size();i++){
			for(int j=0;j<tomatos.tomatoList.get(i).faceList.size();j++){
				String rgb="";
				for(int k=0;k<3;k++){
					rgb+=tomatos.tomatoList.get(i).faceList.get(j).rgba[k];
				}
				userData.edit().putString(i+"-"+j, rgb).commit();
			}
		}
			userData.edit().putFloat("rx",rx).commit();
			userData.edit().putFloat("ry",ry).commit();
	}
	public void loadData(Context context){
		SharedPreferences userData = context.getSharedPreferences("cubes4Data", Context.MODE_PRIVATE);
		for(int i=0;i<12;i++){
			for(int j=0;j<2;j++){
				if(userData.getString(i+"-"+j, "").equals("0.01.00.0"))
					tomatos.tomatoList.get(i).faceList.get(j).setColor(MyColors.green);
				else if(userData.getString(i+"-"+j, "").equals("1.01.00.0"))
					tomatos.tomatoList.get(i).faceList.get(j).setColor(MyColors.yellow);
				else if(userData.getString(i+"-"+j, "").equals("1.00.00.0"))
					tomatos.tomatoList.get(i).faceList.get(j).setColor(MyColors.red);
				else if(userData.getString(i+"-"+j, "").equals("1.01.01.0"))
					tomatos.tomatoList.get(i).faceList.get(j).setColor(MyColors.white);
				else if(userData.getString(i+"-"+j, "").equals("0.50.00.5"))
					tomatos.tomatoList.get(i).faceList.get(j).setColor(MyColors.purple);
				else if(userData.getString(i+"-"+j,"").equals("0.01.01.0"))
					tomatos.tomatoList.get(i).faceList.get(j).setColor(MyColors.blue);
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

