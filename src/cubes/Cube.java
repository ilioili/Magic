package cubes;

import element.Face;
import element.Shape;
import element.Vertex;

public class Cube extends Shape{
	final public static int left=0,right=1,front=2,back=3,top=4,bottom=5;
	final float [][]coordinates=new float[][]{{0,0,1,0,1,1},{0,0,1,1,0,1}};
	private float [][]faceColor;
	private int [] texturesId;
	private float unit;
	public Cube(){
		this.faceColor=new float[7][4];
		this.texturesId=new int[7];
	}
	public Cube(float unit){
		this();
		this.unit=unit;
		Vertex v1=new Vertex(new float[]{unit,unit,unit});
		Vertex v2=new Vertex(new float[]{unit,unit,-unit});
		Vertex v3=new Vertex(new float[]{unit,-unit,-unit});
		Vertex v4=new Vertex(new float[]{unit,-unit,unit});
		Vertex v5=new Vertex(new float[]{-unit,unit,unit});
		Vertex v6=new Vertex(new float[]{-unit,unit,-unit});
		Vertex v7=new Vertex(new float[]{-unit,-unit,-unit});
		Vertex v8=new Vertex(new float[]{-unit,-unit,unit});
		//左面1
		Face f1=new Face(new Vertex[]{v6,v5,v8});
		Face f2=new Face(new Vertex[]{v6,v8,v7});
		
		f1.origin.move(new float[]{-unit,0,0});
		f2.origin.move(new float[]{-unit,0,0});
		
		//右面0
		Face f3=new Face(new Vertex[]{v1,v2,v3});
		Face f4=new Face(new Vertex[]{v1,v3,v4});
		f3.origin.move(new float[]{unit,0,0});
		f4.origin.move(new float[]{unit,0,0});
		//正面2
		Face f5=new Face(new Vertex[]{v5,v1,v4});
		Face f6=new Face(new Vertex[]{v5,v4,v8});
		f5.origin.move(new float[]{0,0,unit});
		f6.origin.move(new float[]{0,0,unit});
		//背面3
		Face f7=new Face(new Vertex[]{v2,v6,v7});
		Face f8=new Face(new Vertex[]{v2,v7,v3});
		f7.origin.move(new float[]{0,0,-unit});
		f8.origin.move(new float[]{0,0,-unit});
		//上面4
		Face f9=new Face(new Vertex[]{v6,v2,v1});
		Face f10=new Face(new Vertex[]{v6,v1,v5});
		f9.origin.move(new float[]{0,unit,0});
		f10.origin.move(new float[]{0,unit,0});
		//下面5
		Face f11=new Face(new Vertex[]{v4,v3,v7});
		Face f12=new Face(new Vertex[]{v4,v7,v8});
		f11.origin.move(new float[]{0,-unit,0});
		f12.origin.move(new float[]{0,-unit,0});
		this.faceList.add(f1);
		this.faceList.add(f2);
		this.faceList.add(f3);
		this.faceList.add(f4);
		this.faceList.add(f5);
		this.faceList.add(f6);
		this.faceList.add(f7);
		this.faceList.add(f8);
		this.faceList.add(f9);
		this.faceList.add(f10);
		this.faceList.add(f11);
		this.faceList.add(f12);
	}
	@Override
	public void initTextureBuffer(){
		for(int i=0;i<12;i++){
			this.faceList.get(i).textureCoordinates=coordinates[i%2];
			this.faceList.get(i).initVertexBuffer();
			this.faceList.get(i).initTextureBuffer();
		}
	}
	final public void setTexture(int faceIndex,int textureId){
		this.faceList.get(faceIndex*2).setTextureId(textureId);
//		this.faceList.get(faceIndex*2).setFaceIndex(faceIndex);
		this.faceList.get(faceIndex*2+1).setTextureId(textureId);
//		this.faceList.get(faceIndex*2+1).setFaceIndex(faceIndex);
		this.texturesId[faceIndex]=textureId;
	}
	final public void setColor(int faceIndex,float[]rgba){
		this.faceList.get(faceIndex*2).setColor(rgba);
		this.faceList.get(faceIndex*2+1).setColor(rgba);
	}
	
	final public Face[] getFace(int faceIndex){
		return new Face[]{faceList.get(faceIndex*2),faceList.get(faceIndex*2+1)};
	}
	final public int getTexture(int faceIndex){
		return texturesId[faceIndex];
	}
	final public float[] getColor(int faceIndex){
		return faceColor[faceIndex];
	}
	final public int getFace(float x,float y,float near,int width,int height){
		int i=super.getFace(x, y, near, width, height);
		if(i!=-1)	return (int)i/2;
		else return -1;
	}
	@Override
	public Cube clone(){
		return new Cube(this.unit);
	}
}
