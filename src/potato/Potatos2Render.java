package potato;

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

public class Potatos2Render implements GLSurfaceView.Renderer{
	private boolean positive=true;//旋转方向
	private int width;
	private int height;
	private float near=1;
	private float far=20;
	private float unit=1f;
	private float rx,ry,rz;
	private float cx,cy,cz;
	public Potatos2 potatos;
	private GL_OBJ obj_others;
	private GL_OBJ obj_layer;
	private int faceIndex;//left=0,right=1,front=2,back=3,top=4,bottom=5;
	private int layerDegree;

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
	     gl.glFrustumf(-ratio, ratio, -1, 1, near, far);
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
        potatos=new Potatos2(unit);
        update();
	}
	public void update(){
		obj_layer.clear();
		obj_others.clear();
		for(int i=0;i<potatos.layer.size();i++)	obj_layer.addShape(potatos.layer.get(i));
		for(int i=0;i<potatos.layerOther.size();i++)	obj_others.addShape(potatos.layerOther.get(i));
		obj_layer.generateC();
		obj_others.generateC();
}
	public void animation(){
		if(Static.animation){
			new Handler().postDelayed(new Runnable(){
				public void run() {
					if(potatos!=null){
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
		Potatos2 p=new Potatos2(unit);
		p.rotateY(-ry);
		p.rotateX(-rx);
		p.move(cx,cy,cz);
		ArrayList <Shape> sl=new ArrayList<Shape>();
		ArrayList <Face>	 fl=new ArrayList<Face>();
		for(int i=0;i<p.potatoList.size();i++){{//获取所有看被射线拾取的Shape
			if(p.potatoList.get(i).isShot(x, y, near, width, height) )
				sl.add(p.potatoList.get(i));
			}
		}
		if(sl.size()==0) return false;//射线没有拾取到正三棱锥
		for(int i=0;i<sl.size();i++){//获取所有被射线拾取的面
			for(int j=0;j<sl.get(i).faceList.size();j++){
				if( Caculator.isInTrangleArea(x, y, sl.get(i).faceList.get(j), near, width, height) )	fl.add(sl.get(i).faceList.get(j));
			}
		}
		while(fl.size()>1){//获取最近的面
			if(Caculator.getCrossPointZ(x, y, fl.get(0), near, width, height)>Caculator.getCrossPointZ(x, y, fl.get(1), near, width, height))	fl.remove(1);
			else	fl.remove(0);
		}
		
		int ic=-1;
		for(int i=0;i<p.potatoList.size();i++){//获取面所在的Shape
			for(int j=0;j<p.potatoList.get(i).faceList.size();j++){
				if(p.potatoList.get(i).faceList.get(j).equals(fl.get(0))){
					ic=i;
//					p.potatoList.get(i).faceList.get(j).setColor(Caculator.black);
					break;
				}
				if(ic>-1)	break;
			}
		}
		System.out.println("shapeIndex="+ic);
//		faceIndex=p.potatoList.get(ic).getFace(x, y, near, width, height);//获取面在Shape中的索引
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
			potatos.setLayer(faceIndex,ic, rotateDirection);//设置魔方要旋转的层
			modify();
			update();//更新绘图数据
			return true;
		}
	}
	public void setLayer(int layerIndex){
		potatos.setLayer(layerIndex);
		update();
	}
	public int getRotateDirection(int faceIndex,float dx,float dy){
//		System.out.println("faceIndex="+faceIndex+"---PyramidRender getRotateDirection()");
		float a,a1,a2,a3,a4,aa1,aa2,aa3,aa4;
		float xx[]=new float[]{dx,-dy};//屏幕坐标越往下Y越大，NDC坐标越往下Y越小，所以取反
		Vertex v1,v2,v3,v4;
		v1=new Vertex();
		v2=new Vertex();
		v3=new Vertex();
		v4=new Vertex();
		switch(faceIndex){
		case 0:
			v1=new Vertex(new float[]{0,1,0});
			v2=new Vertex(new float[]{0,0,-1});
			v3=new Vertex(new float[]{0,1,1});
			v4=new Vertex(new float[]{0,-1,1});
			break;
		case 1:
			v1=new Vertex(new float[]{0,-1,0});
			v2=new Vertex(new float[]{0,0,1});
			v3=new Vertex(new float[]{0,1,-1});
			v4=new Vertex(new float[]{0,-1,-1});
			break;
		case 2:
			v1=new Vertex(new float[]{0,1,0});
			v2=new Vertex(new float[]{-1,0,0});
			v3=new Vertex(new float[]{1,1,0});
			v4=new Vertex(new float[]{1,-1,0});
			break;
		case 3:
			v1=new Vertex(new float[]{0,-1,0});
			v2=new Vertex(new float[]{1,0,0});
			v3=new Vertex(new float[]{-1,1,0});
			v4=new Vertex(new float[]{-1,-1,0});
			break;
		case 4:
			v1=new Vertex(new float[]{0,0,-1});
			v2=new Vertex(new float[]{1,0,0});
			v3=new Vertex(new float[]{1,0,-1});
			v4=new Vertex(new float[]{1,0,1});
			break;
		case 5:
			v1=new Vertex(new float[]{0,0,1});
			v2=new Vertex(new float[]{-1,0,0});
			v3=new Vertex(new float[]{1,0,1});
			v4=new Vertex(new float[]{1,0,-1});
			break;
		}
		v1.rotateY(-ry);
		v1.rotateX(-rx);
		v2.rotateY(-ry);
		v2.rotateX(-rx);
		v3.rotateY(-ry);
		v3.rotateX(-rx);
		v4.rotateY(-ry);
		v4.rotateX(-rx);
		a1=Caculator.angle(xx, new float[]{v1.xyz[0],v1.xyz[1]});
		a2=Caculator.angle(xx, new float[]{v2.xyz[0],v2.xyz[1]});
		a3=Caculator.angle(xx, new float[]{v3.xyz[0],v3.xyz[1]});
		a4=Caculator.angle(xx, new float[]{v4.xyz[0],v4.xyz[1]});
//		System.out.println("a1="+a1);
//		System.out.println("a2="+a2);
//		System.out.println("a3="+a3);
//		System.out.println("a4="+a4);
		aa1=a1;
		aa2=a2;
		aa3=a3;
		aa4=a4;
		if(a1>90)	a1=180-a1;
		if(a2>90)	a2=180-a2;
		if(a3>90)	a3=180-a3;
		if(a4>90)	a4=180-a4;
		a=(a1<a2?a1:a2);
		a=(a<a3?a:a3);
		a=(a<a4?a:a4);
		if(a==a1){
			if(aa1>90)	positive=true;
			else positive=false;
			return 0;
		}else if(a==a2){
			if(aa2>90)	positive=true;
			else positive=false;
			return 1;
		}else if(a==a3){
			if(aa3>90)	positive=true;
			else positive=false;
			return 2;
		}else{
			if(aa4>90)	positive=true;
			else positive=false;
			return 3;
		}
	}
	private void modify(){
		int i=potatos.layerIndex;
		switch(faceIndex){
		case 0:
		case 1:
		case 2:
		case 3:if(i>9)	positive=!positive;break;
		case 4:if(i==6|i==7) positive=!positive;break;
		case 5:if(i==12|i==13) positive=!positive;break;
		}
	}
	public void fresh(){
		//把旋转的角度划归到-180度到+180度之间的120度整数倍位置
		while(obj_layer.angle<-180)		obj_layer.angle	+= 360;
		while(obj_layer.angle>180)		obj_layer.angle	-= 360;
		if(potatos.layerIndex<6){
			if(obj_layer.angle<-135)			obj_layer.angle = -180;
			else if(obj_layer.angle<-45)		obj_layer.angle =-90;
			else if(obj_layer.angle<45)		obj_layer.angle =0;
			else if(obj_layer.angle<135)		obj_layer.angle =90;
			else											obj_layer.angle =180;
		}else{
			if(obj_layer.angle<-60)	obj_layer.angle = -120;
			else if(obj_layer.angle<60)	obj_layer.angle = 0;
			else obj_layer.angle = 120;
		}
		potatos.layerRotate(obj_layer.angle);
		potatos.fresh();//更新pyramids中的数据（做相应的旋转）
		obj_layer.angle=0;
		update();
	}
	public void rotateLayerBy(float degree){
		if(positive)	 degree=-degree;
		obj_layer.angle = degree;
		switch(potatos.layerIndex){//需要判断顺时针还是逆时针
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
		for(int i=0;i<potatos.potatoList.size();i++){
			for(int j=0;j<potatos.potatoList.get(i).faceList.size();j++){
				String rgb="";
				for(int k=0;k<3;k++){
					rgb+=potatos.potatoList.get(i).faceList.get(j).rgba[k];
				}
				userData.edit().putString(i+"-"+j, rgb).commit();
			}
		}
		userData.edit().putFloat("rx",rx).commit();
		userData.edit().putFloat("ry",ry).commit();
	}
	public void loadData(Context context){
		SharedPreferences userData = context.getSharedPreferences("pyramidData", Context.MODE_PRIVATE);
		for(int i=0;i<potatos.potatoList.size();i++){
			for(int j=0;j<potatos.potatoList.get(i).faceList.size();j++){
				if(userData.getString(i+"-"+j, "").equals("0.01.00.0"))
					potatos.potatoList.get(i).faceList.get(j).setColor(MyColors.green);
				else if(userData.getString(i+"-"+j, "").equals("1.01.00.0"))
					potatos.potatoList.get(i).faceList.get(j).setColor(MyColors.yellow);
				else if(userData.getString(i+"-"+j, "").equals("0.01.01.0"))
					potatos.potatoList.get(i).faceList.get(j).setColor(MyColors.blue);
				else if(userData.getString(i+"-"+j, "").equals("1.00.00.0"))
					potatos.potatoList.get(i).faceList.get(j).setColor(MyColors.red);
				else if(userData.getString(i+"-"+j, "").equals("1.01.01.0"))
					potatos.potatoList.get(i).faceList.get(j).setColor(MyColors.white);
				else if(userData.getString(i+"-"+j, "").equals("0.50.00.5"))
					potatos.potatoList.get(i).faceList.get(j).setColor(MyColors.purple);
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
