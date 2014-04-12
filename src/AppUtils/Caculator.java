package AppUtils;

import element.Face;
import element.Vertex;

public class Caculator {
	/**
	 * �ж����������Ƿ����ƥ��
	 * @param a ����a
	 * @param b ����b
	 * @return true��ʾ�����нǴ���150����С��30��
	 */
	public static boolean match(float[]a,float[]b){
		if(angle(a,b)>150|angle(a,b)<30){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * ��������ά�����ļн�
	 * @param a ����a
	 * @param b ����b
	 * @return �нǵĽǶ���
	 */
	public static float angle(float[]a,float[]b){
		double d = ( a[0]*b[0]+a[1]*b[1] ) / ( Math.sqrt(a[0]*a[0]+a[1]*a[1])*Math.sqrt(b[0]*b[0]+b[1]*b[1]) );
		return (float) (180*Math.acos(d)/Math.PI);
	}
	/**
	 * �ѿռ��ͶӰ���ֻ���Ļ��
	 * @param v�ռ����ά��
	 * @param near �ӵ����Ļ�ľ���
	 * @return
	 */
	public static float[] projectionVertex(Vertex v,float near){
		float xy[] = new float[2];
		xy[0] = near*v.xyz[0]/Math.abs(v.xyz[2]);
		xy[1] = near*v.xyz[1]/Math.abs(v.xyz[2]);
		return xy;
	}
	/**
	 * ��ƽ���������ε����
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @return
	 */
	public static float area(float x1,float y1,float x2,float y2,float x3,float y3){
		float a=(float)Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
		float b=(float)Math.sqrt((x1-x3)*(x1-x3)+(y1-y3)*(y1-y3));
		float c=(float)Math.sqrt((x2-x3)*(x2-x3)+(y2-y3)*(y2-y3));
		return (float) (0.25*Math.sqrt( (float)(a+b+c)*(a+b-c)*(a-b+c)*(-a+b+c) ) );
	}
	/**
	 * �ж�����ͶӰʱ���Ƿ񱻴�����ʰȡ��
	 * @param x ��Ļ�ϵĴ������x����
	 * @param y ��Ļ�ϵĴ������y����
	 * @param f ��ά�ռ������
	 * @param width ��Ļ��View���Ŀ�ȣ����أ�
	 * @param height ��Ļ��View���ĸ߶ȣ����أ�
	 * @return
	 */
	public static boolean isFaceShot(float x,float y,Face f,int width,int height){
		x=x-width/2;
		x=2*x/height;
		y=height/2-y;
		y=2*y/height;
		float s1=area(x,y,    f.xyz[0].xyz[0],f.xyz[0].xyz[1],    f.xyz[1].xyz[0],f.xyz[1].xyz[1]);
		float s2=area(x,y,    f.xyz[0].xyz[0],f.xyz[0].xyz[1],    f.xyz[2].xyz[0],f.xyz[2].xyz[1]);
		float s3=area(x,y,    f.xyz[2].xyz[0],f.xyz[2].xyz[1],    f.xyz[1].xyz[0],f.xyz[1].xyz[1]);
		float s4=area(f.xyz[2].xyz[0],f.xyz[2].xyz[1],    f.xyz[1].xyz[0],f.xyz[1].xyz[1],    f.xyz[0].xyz[0],f.xyz[0].xyz[1]);
		return s1+s2+s3-s4<0.000001;
	}
	/**
	 * ��ȡFace�ķ�����������Vertex��
	 * @param f ��ά�ռ����������
	 * @return Face�ķ�����
	 */
	public static  Vertex getFaceN(Face f){
		float NX=(f.xyz[1].xyz[1]-f.xyz[0].xyz[1])*(f.xyz[2].xyz[2]-f.xyz[0].xyz[2]) - (f.xyz[1].xyz[2]-f.xyz[0].xyz[2])*(f.xyz[2].xyz[1]-f.xyz[0].xyz[1]);
		float NY=(f.xyz[1].xyz[2]-f.xyz[0].xyz[2])*(f.xyz[2].xyz[0]-f.xyz[0].xyz[0]) - (f.xyz[1].xyz[0]-f.xyz[0].xyz[0])*(f.xyz[2].xyz[2]-f.xyz[0].xyz[2]);
		float NZ=(f.xyz[1].xyz[0]-f.xyz[0].xyz[0])*(f.xyz[2].xyz[1]-f.xyz[0].xyz[1]) - (f.xyz[1].xyz[1]-f.xyz[0].xyz[1])*(f.xyz[2].xyz[0]-f.xyz[0].xyz[0]);
		return new Vertex(new float[]{NX,NY,NZ});
	}
	/**
	 * �ж�Face1�Ƿ���ƽ��Face2��
	 * @param f1
	 * @param f2
	 * @return
	 */
	public static boolean isInTheSamePlane(Face f1,Face f2){
		Vertex nf1=getFaceN(f1);
		Vertex nf2=getFaceN(f2);
		boolean b1=nf1.equals(nf2);
		nf1.xyz[0]=-nf1.xyz[0];
		nf1.xyz[1]=-nf1.xyz[1];
		nf1.xyz[2]=-nf1.xyz[2];
		boolean b2=nf1.equals(nf2);
		if(b1|b2){//ƽ�淨������ͬ���෴
			float x=nf2.xyz[0]*(f1.xyz[0].xyz[0]-f2.xyz[0].xyz[0])+
						nf2.xyz[1]*(f1.xyz[0].xyz[1]-f2.xyz[0].xyz[1])+
						nf2.xyz[2]*(f1.xyz[0].xyz[2]-f2.xyz[0].xyz[2]);
			if(x<0.0001&x>-0.0001)	return 	true;
		}
		return false;
	}
	/**
	 * �жϿռ��������Ƿ�ʰȡ������ģʽ��
	 * @param x
	 * @param y
	 * @param f
	 * @param near
	 * @param width
	 * @param height
	 * @return
	 */
	public static boolean isInTrangleArea(float x,float y,Face f,float near,int width,int height){
		x=x-width/2;
		x=2*x/height;
		y=(height-y)-height/2;
		y=2*y/height;
		float x1 = projectionVertex(f.xyz[0],near)[0];
		float y1 = projectionVertex(f.xyz[0],near)[1];
		float x2 = projectionVertex(f.xyz[1],near)[0];
		float y2 = projectionVertex(f.xyz[1],near)[1];
		float x3 = projectionVertex(f.xyz[2],near)[0];
		float y3 = projectionVertex(f.xyz[2],near)[1];
		return (area(x,y,x1,y1,x2,y2)+area(x,y,x1,y1,x3,y3)+area(x,y,x2,y2,x3,y3) -area(x1,y1,x2,y2,x3,y3)<0.000001);
	}
	
	/**
	 * ����ģ���Ƿ�����ʰȡ��
	 * @param x ��Ļ�ϵĴ������x����
	 * @param y ��Ļ�ϵĴ������y����
	 * @param f ��ά�ռ������
	 * @param width ��Ļ��View���Ŀ�ȣ����أ�
	 * @param height ��Ļ��View���ĸ߶ȣ����أ�
	 * @param v ��������
	 * @param r ����뾶
	 * @return
	 */
	public static boolean isGlobeShot(float x,float y,float near,int width,int height,Vertex v,float r){
		x=x-width/2;
		x=2*x/height;
		y=height/2-y;
		y=2*y/height;
		float z=-near;
		float t=( x*v.xyz[0]+y*v.xyz[1]+z*v.xyz[2] ) / ( x*x+y*y+z*z );
		x=x*t;
		y=y*t;
		z=z*t;
		return ( (x-v.xyz[0])*(x-v.xyz[0])+(y-v.xyz[1])*(y-v.xyz[1])+(z-v.xyz[2])*(z-v.xyz[2]) < r*r );
	}
	/**
	 * �ж���Ļ�ϵ�һ���Ƿ��ڿռ����ͶӰ��
	 * @param x ���x����
	 * @param y ���y����
	 * @param rx ��Ļ��Բ��x����
	 * @param ry ��Ļ��Բ��y����
	 * @param r ԰�뾶
	 * @param width ��Ļ��View���Ŀ�ȣ����أ�
	 * @param height ��Ļ��View���ĸ߶ȣ����أ�
	 * @return
	 */
	public static boolean isInCircleArea(float x,float y,float rx,float ry,float r,int width,int height){
		x=x-width/2;
		x=2*x/height;
		y=(height-y)-height/2;
		y=2*y/height;
		return  ( (x-rx)*(x-rx) + (y-ry)*(y-ry) < r*r );
	}
	/**
	 * �жϿռ����������Ƿ���ƽ�������
	 * @param v1
	 * @param v2
	 * @param f
	 * @return
	 */
	public static boolean isInTheSameSide(Vertex v1,Vertex v2,Face f){
		if(getCrossPoint(v1,v2,f)==null) return true;
		Vertex v=getCrossPoint(v1,v2,f);
		return vertexDistence(v1, v2)<vertexDistence(v1,v)+vertexDistence(v2,v)-0.001;
	}
	/**
	 * �ռ��л�ȡ����ֱ�ߺ�ƽ��Ľ���
	 * @param v1 ֱ���ϵ�һ��
	 * @param v2 ֱ���ϵ�һ��
	 * @param f
	 * @return
	 */
	public static Vertex getCrossPoint(Vertex v1,Vertex v2,Face f){
		float l,m,n;//�ռ���ķ�����
		float a,b,c;//���㹹��ֱ�ߵķ�����
		Vertex v=new Vertex();
		l=(f.xyz[1].xyz[1]-f.xyz[0].xyz[1])*(f.xyz[2].xyz[2]-f.xyz[0].xyz[2]) - (f.xyz[1].xyz[2]-f.xyz[0].xyz[2])*(f.xyz[2].xyz[1]-f.xyz[0].xyz[1]);
		m=(f.xyz[1].xyz[2]-f.xyz[0].xyz[2])*(f.xyz[2].xyz[0]-f.xyz[0].xyz[0]) - (f.xyz[1].xyz[0]-f.xyz[0].xyz[0])*(f.xyz[2].xyz[2]-f.xyz[0].xyz[2]);
		n=(f.xyz[1].xyz[0]-f.xyz[0].xyz[0])*(f.xyz[2].xyz[1]-f.xyz[0].xyz[1]) - (f.xyz[1].xyz[1]-f.xyz[0].xyz[1])*(f.xyz[2].xyz[0]-f.xyz[0].xyz[0]);
		a=v1.xyz[0]-v2.xyz[0];
		b=v1.xyz[1]-v2.xyz[1];
		c=v1.xyz[2]-v2.xyz[2];
//		if(Math.abs(l*a+m*b+c*n)<0.000001) return null;//ƽ�У��޽���
		float t;
		t=l*f.xyz[0].xyz[0]-l*v1.xyz[0]+m*f.xyz[0].xyz[1]-m*v1.xyz[1]+n*f.xyz[0].xyz[2]-n*v1.xyz[2];
		v.xyz[0]=a*t+v1.xyz[0];
		v.xyz[1]=b*t+v1.xyz[1];
		v.xyz[2]=c*t+v1.xyz[2];
		return v;
	}
	/**
	 * �ռ�����֮��ľ���
	 * @param v1
	 * @param v2
	 * @return �ռ�����֮��ľ���
	 */
	public static float vertexDistence(Vertex v1,Vertex v2){
		float d=(v1.xyz[0]-v2.xyz[0])*(v1.xyz[0]-v2.xyz[0])+(v1.xyz[1]-v2.xyz[1])*(v1.xyz[1]-v2.xyz[1])+(v1.xyz[2]-v2.xyz[2])*(v1.xyz[2]-v2.xyz[2]);
		return (float)Math.sqrt(d);
	}
	/**
	 * ����ͶӰ��ȡ�����Zֵ
	 * @param x ���x����
	 * @param y ���y����
	 * @param f �������ڵ�ƽ��
	 * @param width ��Ļ��View���Ŀ�ȣ����أ�
	 * @param height ��Ļ��View���ĸ߶ȣ����أ�
	 * @return
	 */
	public static float getCrossPointZ(float x,float y,Face f,int width,int height){
		x=x-width/2;
		x=2*x/height;
		y=(height-y)-height/2;
		y=2*y/height;
		float NX=(f.xyz[1].xyz[1]-f.xyz[0].xyz[1])*(f.xyz[2].xyz[2]-f.xyz[0].xyz[2]) - (f.xyz[1].xyz[2]-f.xyz[0].xyz[2])*(f.xyz[2].xyz[1]-f.xyz[0].xyz[1]);
		float NY=(f.xyz[1].xyz[2]-f.xyz[0].xyz[2])*(f.xyz[2].xyz[0]-f.xyz[0].xyz[0]) - (f.xyz[1].xyz[0]-f.xyz[0].xyz[0])*(f.xyz[2].xyz[2]-f.xyz[0].xyz[2]);
		float NZ=(f.xyz[1].xyz[0]-f.xyz[0].xyz[0])*(f.xyz[2].xyz[1]-f.xyz[0].xyz[1]) - (f.xyz[1].xyz[1]-f.xyz[0].xyz[1])*(f.xyz[2].xyz[0]-f.xyz[0].xyz[0]);
		float CZ=(NZ*f.xyz[0].xyz[2]-NX*(x-f.xyz[0].xyz[0])-NY*(y-f.xyz[0].xyz[1]))/NZ;
		return CZ;
	}
	/**
	 * ͸��ͶӰ��ȡ�����Zֵ
	 * @param x ���x����
	 * @param y ���y����
	 * @param f �������ڵ�ƽ��
	 * @param width ��Ļ��View���Ŀ�ȣ����أ�
	 * @param height ��Ļ��View���ĸ߶ȣ����أ�
	 * @param near �ӽǺ���Ļ�ľ���
	 * @return
	 */
	public static float getCrossPointZ(float x,float y,Face f,float near,int width,int height){
		x=x-width/2;
		x=2*x/height;
		y=(height-y)-height/2;
		y=2*y/height;
		float z=-near;
		//ƽ�淨����(NX��NY��NZ)
		float NX=(f.xyz[1].xyz[1]-f.xyz[0].xyz[1])*(f.xyz[2].xyz[2]-f.xyz[0].xyz[2]) - (f.xyz[1].xyz[2]-f.xyz[0].xyz[2])*(f.xyz[2].xyz[1]-f.xyz[0].xyz[1]);
		float NY=(f.xyz[1].xyz[2]-f.xyz[0].xyz[2])*(f.xyz[2].xyz[0]-f.xyz[0].xyz[0]) - (f.xyz[1].xyz[0]-f.xyz[0].xyz[0])*(f.xyz[2].xyz[2]-f.xyz[0].xyz[2]);
		float NZ=(f.xyz[1].xyz[0]-f.xyz[0].xyz[0])*(f.xyz[2].xyz[1]-f.xyz[0].xyz[1]) - (f.xyz[1].xyz[1]-f.xyz[0].xyz[1])*(f.xyz[2].xyz[0]-f.xyz[0].xyz[0]);
		float CZ= z * ( (f.xyz[0].xyz[0]*NX)+(f.xyz[0].xyz[1]*NY+(f.xyz[0].xyz[2]*NZ)) ) / (NX*x+NY*y+NZ*z);
		return CZ;
	}
	/**
	 * @param f
	 * @return ��ȡ�ռ������ε�����
	 */
	public static Vertex getCenterPoint(Face f){
		Vertex v=new Vertex(new float[3]);
		v.xyz[0]=( f.xyz[0].xyz[0]+f.xyz[1].xyz[0]+f.xyz[2].xyz[0] )/3;
		v.xyz[1]=( f.xyz[0].xyz[1]+f.xyz[1].xyz[1]+f.xyz[2].xyz[1] )/3;
		v.xyz[2]=( f.xyz[0].xyz[2]+f.xyz[1].xyz[2]+f.xyz[2].xyz[2] )/3;
		return v;
	}
}
