package element;

import java.util.ArrayList;

import AppUtils.Matrix;

/*
 * public float rgba[];//��������shape����ɫ
	public Vertex origin;
	public ArrayList <Face> faceList;
	public float x,y,z;//��������ϵ��ƽ�Ʊ任
	public  float rx,ry,rz;//��������ϵ����ת�任
	private ArrayList <Vertex>verticesList;
	private static float vertices[];
	private FloatBuffer   vertexBuffer;
	private float colors[];
	private FloatBuffer   colorBuffer;
	private static short indices[];
	private ShortBuffer indiceBuffer;
 */

public abstract class Shapes extends Shape{
	public ArrayList <Shape> shapeList;
	public Shapes(){
		shapeList=new ArrayList<Shape>();
	}
	@Override
	public abstract void initTextureBuffer();
	public void rotateX(float angle){
		for(int i=0;i<shapeList.size();i++){
			Matrix.rotateX(shapeList.get(i), angle);
		}
		
	}
	public void rotateY(float angle){
		for(int i=0;i<shapeList.size();i++){
			Matrix.rotateY(shapeList.get(i), angle);
		}
	}
	public void rotateZ(float angle){
		for(int i=0;i<shapeList.size();i++){
			Matrix.rotateZ(shapeList.get(i), angle);
		}
	}
	@Override
	public void move(float []xyz){
		for(int i=0;i<shapeList.size();i++){
			shapeList.get(i).move(xyz);
		}
	}
	@Override
	public void rotate(float x,float y,float z,float angle){
		for(int i=0;i<shapeList.size();i++){
			Matrix.rotate(shapeList.get(i), new double[]{x,y,z}, angle);
		}
	}
	public void copy(Shapes s){
		for(int i=0;i<s.shapeList.size();i++){
			this.shapeList.get(i).copy(s.shapeList.get(i));
		}
	}
}
