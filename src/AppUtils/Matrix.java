package AppUtils;

import element.Face;
import element.Shape;
import element.Vertex;


public class Matrix {
	private static Vertex temp=new Vertex();
	
	final public static void rotate(int[][]src, int[][]dsc, int n){
		int i, j;
		for(i=0;i<n;i++){
			for(j=0;j<n;j++){
				dsc[j][n-i]=src[i][j];
			}
		}
	}
	
	final public static int[][] getCubeLayerTextureId(int layerIndex, int n){
		//cube[x][y][z];
		return null;
	}
	
	final public static void multiply(Vertex v,double m[][]){
		temp.xyz[0]=0;
		temp.xyz[1]=0;
		temp.xyz[2]=0;
		for(int i = 0; i<3; i++){
			for(int j = 0; j<3; j++){
				temp.xyz[i]+=(float)v.xyz[j]*m[j][i];
			}
		}
		v.xyz[0]=temp.xyz[0];
		v.xyz[1]=temp.xyz[1];
		v.xyz[2]=temp.xyz[2];
	}
	final public static void multiply(Face f,double m[][]){
		for(int i=0;i<3;i++){
			multiply(f.xyz[i],m);
		}
		multiply(f.origin,m);
	}
	final public static void multiply(Shape s,double m[][]){
		for(int i=0;i<s.faceList.size();i++){
			multiply(s.faceList.get(i),m);
		}
		multiply(s.origin,m);
	}
	final public static void rotateX(Vertex v,double angle){
		if(angle==90|angle==-270){
			float y=v.xyz[1],z=v.xyz[2];
			v.xyz[1]=z;
			v.xyz[2]=-y;
		}else if(angle==-90|angle==270){
			float y=v.xyz[1],z=v.xyz[2];
			v.xyz[1]=-z;
			v.xyz[2]=y;
		}else if(angle==180|angle==-180){
			float y=v.xyz[1],z=v.xyz[2];
			v.xyz[1]=-y;
			v.xyz[2]=-z;
		}else if(angle==0|angle==360|angle==-360){
		}else{
			angle=Math.PI *(angle/180);
			double m[][]=new double [][]
				{	{1,						0,								0	},
					{0,Math.cos(angle),	-Math.sin(angle)	},
					{0,Math.sin(angle),	Math.cos(angle)	}
				};
			multiply(v,m);
		}
		
	}
	final public static void rotateY(Vertex v,double angle){
		if(angle==90|angle==-270){
			float x=v.xyz[0],z=v.xyz[2];
			v.xyz[0]=-z;
			v.xyz[2]=x;
		}else if(angle==-90|angle==270){
			float x=v.xyz[0],z=v.xyz[2];
			v.xyz[0]=z;
			v.xyz[2]=-x;
		}else if(angle==180|angle==-180){
			float x=v.xyz[0],z=v.xyz[2];
			v.xyz[0]=-x;
			v.xyz[2]=-z;
		}else if(angle==0|angle==360|angle==-360){
		}else{
			angle=Math.PI *(angle/180);
			double m[][]=new double [][]
				{	{Math.cos(angle),	0,Math.sin(angle)		},
					{0,							1,							0	},
					{-Math.sin(angle),	0,Math.cos(angle)		}
					
				};
			multiply(v,m);
		}
	}
	final public static void rotateZ(Vertex v,double angle){
		if(angle==90|angle==-270){
			float x=v.xyz[0],y=v.xyz[1];
			v.xyz[0]=-y;
			v.xyz[1]=x;
		}else if(angle==-90|angle==270){
			float x=v.xyz[0],y=v.xyz[1];
			v.xyz[0]=y;
			v.xyz[1]=-x;
		}else if(angle==180|angle==-180){
			float x=v.xyz[0],y=v.xyz[1];
			v.xyz[0]=-x;
			v.xyz[1]=-y;
		}else if(angle==0|angle==360|angle==-360){
		}else{
			angle=Math.PI *(angle/180);
			double m[][]=new double [][]
				{	{Math.cos(angle),	Math.sin(angle),	0	},
					{-Math.sin(angle),	Math.cos(angle),	0	},
					{0,							0,							1	}
				};
			multiply(v,m);
		}
		
	}
	final public static void rotateX(Face f,double angle){
		for(int i=0;i<3;i++){
			rotateX(f.xyz[i],angle);
		}
		rotateX(f.origin,angle);
	}
	final public static void rotateY(Face f,double angle){
		for(int i=0;i<3;i++){
			rotateY(f.xyz[i],angle);
		}
		rotateY(f.origin,angle);
	}
	final public static void rotateZ(Face f,double angle){
		for(int i=0;i<3;i++){
			rotateZ(f.xyz[i],angle);
		}
		rotateZ(f.origin,angle);
	}
	final public static void rotateX(Shape s,double angle){
		for(int i=0;i<s.faceList.size();i++){
			rotateX(s.faceList.get(i),angle);
		}
		rotateX(s.origin,angle);
	}
	final public static void rotateY(Shape s,double angle){
		for(int i=0;i<s.faceList.size();i++){
			rotateY(s.faceList.get(i),angle);
		}
		rotateY(s.origin,angle);
	}
	final public static void rotateZ(Shape s,double angle){
		for(int i=0;i<s.faceList.size();i++){
			rotateZ(s.faceList.get(i),angle);
		}
		rotateZ(s.origin,angle);
	}
	final public static void rotate(Vertex v,double []xyz,double angle){
		double t=(float)Math.sqrt(xyz[0]*xyz[0]+xyz[1]*xyz[1]+xyz[2]*xyz[2]);
		xyz[0]/=t;
		xyz[1]/=t;
		xyz[2]/=t;
		double C=Math.cos(angle*Math.PI/180);
		double S=Math.sin(angle*Math.PI/180);
		double X=xyz[0];
		double Y=xyz[1];
		double Z=xyz[2];
		double m[][]=new double[][]{	{	C+X*X*(1-C),		X*Y*(1-C)-Z*S,	X*Z*(1-C)+Y*S	},
														{	X*Y*(1-C)+Z*S,	C+Y*Y*(1-C),		Y*Z*(1-C)-X*S	},
														{	X*Z*(1-C)-Y*S,	Y*Z*(1-C)+X*S,	C+Z*Z*(1-C)		}	};
//		double m[][]=new double[][]{	{	C+X*X*(1-C),		X*Y*(1-C)+Z*S,	X*Z*(1-C)-Y*S	},
//														{	X*Y*(1-C)-Z*S,	C+Y*Y*(1-C),		Y*Z*(1-C)+X*S	},
//														{	X*Z*(1-C)+Y*S,	Y*Z*(1-C)-X*S,	C+Z*Z*(1-C)		}	};
		multiply(v, m);
	}
	final public static void rotate(Face f,double []xyz,double angle){
		double t=(float)Math.sqrt(xyz[0]*xyz[0]+xyz[1]*xyz[1]+xyz[2]*xyz[2]);
		xyz[0]/=t;
		xyz[1]/=t;
		xyz[2]/=t;
		double C=Math.cos(angle*Math.PI/180);
		double S=Math.sin(angle*Math.PI/180);
		double X=xyz[0];
		double Y=xyz[1];
		double Z=xyz[2];
		double m[][]=new double[][]{	{	C+X*X*(1-C),		X*Y*(1-C)-Z*S,	X*Z*(1-C)+Y*S	},
														{	X*Y*(1-C)+Z*S,	C+Y*Y*(1-C),		Y*Z*(1-C)-X*S	},
														{	X*Z*(1-C)-Y*S,	Y*Z*(1-C)+X*S,	C+Z*Z*(1-C)		}	};
		multiply(f, m);
	}
	final public static void rotate(Shape s,double []xyz,double angle){
		double t=(float)Math.sqrt(xyz[0]*xyz[0]+xyz[1]*xyz[1]+xyz[2]*xyz[2]);
		xyz[0]/=t;
		xyz[1]/=t;
		xyz[2]/=t;
		double C=Math.cos(angle*Math.PI/180);
		double S=Math.sin(angle*Math.PI/180);
		double X=xyz[0];
		double Y=xyz[1];
		double Z=xyz[2];
		double m[][]=new double[][]{	{	C+X*X*(1-C),		X*Y*(1-C)-Z*S,	X*Z*(1-C)+Y*S	},
														{	X*Y*(1-C)+Z*S,	C+Y*Y*(1-C),		Y*Z*(1-C)-X*S	},
														{	X*Z*(1-C)-Y*S,	Y*Z*(1-C)+X*S,	C+Z*Z*(1-C)		}	};
		multiply(s, m);
	}
}
