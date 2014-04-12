package element;

import javax.microedition.khronos.opengles.GL10;
import Resource.MyColors;


public class NewCube{
	public Mesh[] squareArray=new Mesh[6];
	public int[] textures=new int[6];
	final public static int left=0,right=1,front=2,back=3,top=4,bottom=5;
	
	public NewCube(float unit){
		Vertex v1=new Vertex(new float[]{unit,unit,unit});
		Vertex v2=new Vertex(new float[]{unit,unit,-unit});
		Vertex v3=new Vertex(new float[]{unit,-unit,-unit});
		Vertex v4=new Vertex(new float[]{unit,-unit,unit});
		Vertex v5=new Vertex(new float[]{-unit,unit,unit});
		Vertex v6=new Vertex(new float[]{-unit,unit,-unit});
		Vertex v7=new Vertex(new float[]{-unit,-unit,-unit});
		Vertex v8=new Vertex(new float[]{-unit,-unit,unit});
		//左面1
		float [] temp0=new float[]{-unit,0,0};
		Face f1=new Face(new Vertex[]{v7,v6,v5},MyColors.blue);
		Face f2=new Face(new Vertex[]{v7,v5,v8},MyColors.blue);
		//右面0
		float [] temp1=new float[]{unit,0,0};
		Face f3=new Face(new Vertex[]{v4,v1,v2},MyColors.red);
		Face f4=new Face(new Vertex[]{v4,v2,v3},MyColors.red);
		//正面2
		float [] temp2=new float[]{0,0,unit};
		Face f5=new Face(new Vertex[]{v8,v5,v1},MyColors.yellow);
		Face f6=new Face(new Vertex[]{v8,v1,v4},MyColors.yellow);
		//背面3
		float [] temp3=new float[]{0,0,-unit};
		Face f7=new Face(new Vertex[]{v3,v2,v6},MyColors.white);
		Face f8=new Face(new Vertex[]{v3,v6,v7},MyColors.white);
		//上面4
		float [] temp4=new float[]{0,unit,0};
		Face f9=new Face(new Vertex[]{v5,v6,v2},MyColors.purple);
		Face f10=new Face(new Vertex[]{v5,v2,v1},MyColors.purple);
		//下面5
		float [] temp5=new float[]{0,-unit,0};
		Face f11=new Face(new Vertex[]{v7,v8,v4},MyColors.green);
		Face f12=new Face(new Vertex[]{v7,v4,v3},MyColors.green);
		squareArray[0]=new Mesh(f1,f2);
		squareArray[1]=new Mesh(f3,f4);
		squareArray[2]=new Mesh(f5,f6);
		squareArray[3]=new Mesh(f7,f8);
		squareArray[4]=new Mesh(f9,f10);
		squareArray[5]=new Mesh(f11,f12);
		squareArray[0].origin.move(temp0);
		squareArray[1].origin.move(temp1);
		squareArray[2].origin.move(temp2);
		squareArray[3].origin.move(temp3);
		squareArray[4].origin.move(temp4);
		squareArray[5].origin.move(temp5);
	}
	public void generate() {
		for(int i=squareArray.length-1;i>-1;i--){
			squareArray[i].generate();
		}
	}
	public void setFaceTexture(GL10 gl, int faceIndex,int textureIndex){
		squareArray[faceIndex].loadTexture(gl, textures[textureIndex]);
	}
	public void draw(GL10 gl, int config) {
		for(int i=squareArray.length-1;i>-1;i--){
			squareArray[i].draw(gl,config);
		}
	}
}
