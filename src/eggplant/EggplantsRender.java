package eggplant;

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

public class EggplantsRender implements GLSurfaceView.Renderer{
	private boolean positive=true;//旋转方向
	private int width;
	private int height;
	private float near=1;
	private float far=20;
	private float unit=0.18f;
	private float rx,ry,rz;
	private float cx,cy,cz=-4;
	private Eggplants eggplants;
	private GL_OBJ obj_others;
	private GL_OBJ obj_layer;
	private int faceIndex;//left=0,right=1,front=2,back=3,top=4,bottom=5;
	protected int layerDegree;

	public EggplantsRender(){
		objCreated();
	}
	@Override
	public void onDrawFrame(GL10 gl) {
		try{
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	        gl.glLoadIdentity();
	        gl.glTranslatef(cx, cy, cz);
	        gl.glRotatef(rx+0.2f, 1, 0, 0);
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
	        //设置OpenGL场景的大小
	     gl.glViewport(0, 0, width, height);
	        //设置投影矩阵
	     gl.glMatrixMode(GL10.GL_PROJECTION);
	        //重置投影矩阵
	     gl.glLoadIdentity();
	        // 设置视口的大小
	     gl.glOrthof(-ratio, ratio, -1, 1, near, far);
	        // 选择模型观察矩阵
	     gl.glMatrixMode(GL10.GL_MODELVIEW);    
	        // 重置模型观察矩阵
	     gl.glLoadIdentity();    
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			gl.glShadeModel(GL10.GL_SMOOTH);
	       // 黑色背景
			gl.glClearColor(0f, 0f, 0f, 0f);
	       // 设置深度缓存
			gl.glClearDepthf(1.0f);                            
	       // 启用深度测试
			gl.glEnable(GL10.GL_DEPTH_TEST);                        
	       // 所作深度测试的类型
			gl.glDepthFunc(GL10.GL_LEQUAL);                            
	       // 告诉系统对透视进行修正
			gl.glEnable (GL10.GL_BLEND);
			gl.glBlendFunc (GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
//			gl.glEnable(gl.GL_MULTISAMPLE);
//			gl.glDisable(GL_DITHER);
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
		this.ry += ry;
		if(this.rx>60)	 this.rx=60;
		if(this.rx<-60) this.rx=-60;
		if(this.ry>180)	this.ry-=360;
		else if(this.ry<-180)	this.ry+=360;
	}
	public void objCreated(){
        obj_layer=new GL_OBJ();
        obj_others=new GL_OBJ();
        eggplants=new Eggplants(unit);
        update();
	}
	public void update(){
		obj_layer.clear();
		obj_others.clear();
		for(int i=0;i<eggplants.layer.size();i++)	obj_layer.addShape(eggplants.layer.get(i));
		for(int i=0;i<eggplants.layerOther.size();i++)	obj_others.addShape(eggplants.layerOther.get(i));
		obj_layer.generateC();
		obj_others.generateC();
}
	public void animation(){
		if(Static.animation){
			new Handler().postDelayed(new Runnable(){
				public void run() {
					if(eggplants!=null){
						rotateTo(0.1f, 0.2f, 0.3f);
						layerDegree+=2;
						rotateLayerBy(layerDegree);
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
		Eggplants e=new Eggplants(unit);
		e.rotateY(-ry);
		e.rotateX(-rx);
		e.move(cx,cy,cz);
		ArrayList <Shape> sl=new ArrayList<Shape>();
		ArrayList <Face>	 fl=new ArrayList<Face>();
		for(int i=0;i<e.eggplantList.size();i++){{//获取所有看被射线拾取的Shape
			if(e.eggplantList.get(i).isShot(x, y, width, height) )
				sl.add(e.eggplantList.get(i));
			}
		}
		if(sl.size()==0) return false;//射线没有拾取到正三棱锥
		for(int i=0;i<sl.size();i++){//获取所有被射线拾取的面
			for(int j=0;j<sl.get(i).faceList.size();j++){
				if( Caculator.isFaceShot(x, y, sl.get(i).faceList.get(j), width, height) )	fl.add(sl.get(i).faceList.get(j));
			}
		}
		while(fl.size()>1){//获取最近的面
			if(Caculator.getCrossPointZ(x, y, fl.get(0), width, height)>Caculator.getCrossPointZ(x, y, fl.get(1), width, height))	fl.remove(1);
			else	fl.remove(0);
		}
		
		int ic=-1;
		for(int i=0;i<e.eggplantList.size();i++){//获取面所在的Shape
			for(int j=0;j<e.eggplantList.get(i).faceList.size();j++){
				if(e.eggplantList.get(i).faceList.get(j).equals(fl.get(0))){
					ic=i;
					break;
				}
				if(ic>-1)	break;
			}
		}
		faceIndex=fl.get(0).faceIndex;//获取面在Shape中的索引
		eggplants.setLayer(faceIndex, ic, getRotateDirection(faceIndex,dx,dy));//设置魔方要旋转的层
		update();//更新绘图数据
		return true;
	}
	public void setLayer(int layerIndex){
		eggplants.setLayer(layerIndex);
		update();
	}
	public int getRotateDirection(int faceIndex,float dx,float dy){
//		System.out.println("faceIndex="+faceIndex+"---PyramidRender getRotateDirection()");
		float xx[]=new float[]{dx,-dy};//屏幕坐标越往下Y越大，NDC坐标越往下Y越小，所以取反
		int a1,a2,a3;
		float s2=(float)Math.sqrt(2);
		Vertex v1=new Vertex();
		Vertex v2=new Vertex();
		Vertex v3=new Vertex();
		switch(faceIndex){
		case 0:
			v1=new Vertex(new float[]{-1,-s2,1});
			v2=new Vertex(new float[]{1,-s2,1});
			v3=new Vertex(new float[]{2,0,0});
			break;
		case 1:
			v1=new Vertex(new float[]{1,-s2,1});
			v2=new Vertex(new float[]{1,-s2,-1});
			v3=new Vertex(new float[]{0,0,-2});
			break;
		case 2:
			v1=new Vertex(new float[]{1,-s2,-1});
			v2=new Vertex(new float[]{-1,-s2,-1});
			v3=new Vertex(new float[]{-2,0,0});
			break;
		case 3:
			v1=new Vertex(new float[]{-1,-s2,-1});
			v2=new Vertex(new float[]{-1,-s2,1});
			v3=new Vertex(new float[]{0,0,2});
			break;
		case 4:
			v1=new Vertex(new float[]{1,s2,1});
			v2=new Vertex(new float[]{-1,s2,1});
			v3=new Vertex(new float[]{2,0,0});
			break;
		case 5:
			v1=new Vertex(new float[]{1,s2,-1});
			v2=new Vertex(new float[]{1,s2,1});
			v3=new Vertex(new float[]{0,0,-2});
			break;
		case 6:
			v1=new Vertex(new float[]{1,s2,-1});
			v2=new Vertex(new float[]{1,s2,1});
			v3=new Vertex(new float[]{-2,0,0});
			break;
		case 7:
			v1=new Vertex(new float[]{-1,s2,1});
			v2=new Vertex(new float[]{-1,s2,-1});
			v3=new Vertex(new float[]{0,0,2});
			break;
		}
		v1.rotateY(-ry);
		v1.rotateX(-rx);
		v1.move(new float[]{cx,cy,cz});
		v2.rotateY(-ry);
		v2.rotateX(-rx);
		v2.move(new float[]{cx,cy,cz});
		v3.rotateY(-ry);
		v3.rotateX(-rx);
		v3.move(new float[]{cx,cy,cz});
		a1=(int)Caculator.angle(xx, new float[]{v1.xyz[0],v1.xyz[1]});
		a2=(int)Caculator.angle(xx, new float[]{v2.xyz[0],v2.xyz[1]});
		a3=(int)Caculator.angle(xx, new float[]{v3.xyz[0],v3.xyz[1]});
		int aa1=a1;
		int aa2=a2;
		if(a1>90) a1=180-a1;
		if(a2>90) a2=180-a2;
		int min;
		min=(a1<a2?a1:a2);
		min=(min<a3?min:a3);
		if(min==a1){
			if(aa1>90)	positive=true;
			else	positive=false;
			if(faceIndex>3) positive=!positive;
			return 0;
		}else if(min==a2){
			if(aa2<90)	positive=true;
			else	positive=false;
			if(faceIndex>3) positive=!positive;
			return 1;
		}else{
			if(aa2<90)	positive=true;
			else	positive=false;
			if(faceIndex>3)	positive=!positive;
			return 2;
		}
	}
	public void fresh(){
		//把旋转的角度划归到-180度到+180度之间的120度整数倍位置
		while(obj_layer.angle<-180)		obj_layer.angle	+= 360;
		while(obj_layer.angle>180)		obj_layer.angle	-= 360;
		if(obj_layer.angle<-60)	obj_layer.angle = -120;
		else if(obj_layer.angle<60)	obj_layer.angle = 0;
		else obj_layer.angle = 120;
		eggplants.layerRotate(-obj_layer.angle);
		eggplants.fresh();//更新pyramids中的数据（做相应的旋转）
		obj_layer.angle=0;
		update();
	}
	public void rotateLayerBy(float degree){
		if(positive)	degree=-degree;
		obj_layer.angle = degree;
		Vertex v=new Vertex();
		switch(eggplants.layerIndex){//需要判断顺时针还是逆时针
		case 0:
		case 1:
			v=Caculator.getFaceN(eggplants.eggplantList.get(0).faceList.get(3));
			obj_layer.xyz = new float[]{v.xyz[0],v.xyz[1],v.xyz[2]};
			break;
		case 2:
		case 3:
			v=Caculator.getFaceN(eggplants.eggplantList.get(0).faceList.get(1));
			obj_layer.xyz = new float[]{v.xyz[0],v.xyz[1],v.xyz[2]};
			break;
		case 4:
		case 5:
			v=Caculator.getFaceN(eggplants.eggplantList.get(0).faceList.get(0));
			obj_layer.xyz = new float[]{v.xyz[0],v.xyz[1],v.xyz[2]};
			break;
		case 6:
		case 7:
			v=Caculator.getFaceN(eggplants.eggplantList.get(0).faceList.get(2));
			obj_layer.xyz = new float[]{v.xyz[0],v.xyz[1],v.xyz[2]};
			break;
		}
	}
	public boolean shapeTouched(float x,float y){
		Eggplants p=new Eggplants(unit);
		p.rotateY(-ry);
		p.rotateX(-rx);
		p.move(cx, cy, cz);
		for(int i=0;i<p.eggplantList.size();i++){
			if( p.eggplantList.get(i).isShot(x, y, width, height) )
				return true;
		}
		return false;
	}
	public void saveData(Context context){
		SharedPreferences userData = context.getSharedPreferences("pyramidData", Context.MODE_PRIVATE);
		for(int i=0;i<14;i++){
			for(int j=0;j<4;j++){
				String rgb="";
				for(int k=0;k<3;k++){
					rgb+=eggplants.eggplantList.get(i).faceList.get(j).rgba[k];
				}
				userData.edit().putString(i+"-"+j, rgb).commit();
			}
		}
		userData.edit().putFloat("rx",rx).commit();
		userData.edit().putFloat("ry",ry).commit();
	}
	public void loadData(Context context){
		SharedPreferences userData = context.getSharedPreferences("pyramidData", Context.MODE_PRIVATE);
		for(int i=0;i<14;i++){
			for(int j=0;j<4;j++){
				if(userData.getString(i+"-"+j, "").equals("0.01.00.0"))
					eggplants.eggplantList.get(i).faceList.get(j).setColor(MyColors.green);
				else if(userData.getString(i+"-"+j, "").equals("1.01.00.0"))
					eggplants.eggplantList.get(i).faceList.get(j).setColor(MyColors.yellow);
				else if(userData.getString(i+"-"+j, "").equals("1.00.00.0"))
					eggplants.eggplantList.get(i).faceList.get(j).setColor(MyColors.red);
				else if(userData.getString(i+"-"+j, "").equals("1.01.01.0"))
					eggplants.eggplantList.get(i).faceList.get(j).setColor(MyColors.white);
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
