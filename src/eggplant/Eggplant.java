package eggplant;

import element.Face;
import element.Shape;
import element.Vertex;
import AppUtils.Caculator;
import Resource.MyColors;


public class Eggplant extends Shape{
	public Eggplant(float unit){
		float s2=(float)Math.sqrt(2);
		Vertex v0=new Vertex(new float[]{0,s2*unit,0});
		Vertex v1=new Vertex(new float[]{-unit,0,unit});
		Vertex v2=new Vertex(new float[]{unit,0,unit});
		Vertex v3=new Vertex(new float[]{unit,0,-unit});
		Vertex v4=new Vertex(new float[]{-unit,0,-unit});
		Vertex v5=new Vertex(new float[]{0,-s2*unit,0});
		Face f1=new Face(new Vertex[]{v1,v0,v2},MyColors.blue);
		Face f2=new Face(new Vertex[]{v2,v0,v3},MyColors.red);
		Face f3=new Face(new Vertex[]{v3,v0,v4},MyColors.white);
		Face f4=new Face(new Vertex[]{v4,v0,v1},MyColors.darkred);
		Face f5=new Face(new Vertex[]{v5,v1,v2},MyColors.yellow);
		Face f6=new Face(new Vertex[]{v5,v2,v3},MyColors.green);
		Face f7=new Face(new Vertex[]{v5,v3,v4},MyColors.purple);
		Face f8=new Face(new Vertex[]{v5,v4,v1},MyColors.seablue);
		f1.origin=Caculator.getCenterPoint(f1);
		f2.origin=Caculator.getCenterPoint(f2);
		f3.origin=Caculator.getCenterPoint(f3);
		f4.origin=Caculator.getCenterPoint(f4);
		f5.origin=Caculator.getCenterPoint(f5);
		f6.origin=Caculator.getCenterPoint(f6);
		f7.origin=Caculator.getCenterPoint(f7);
		f8.origin=Caculator.getCenterPoint(f8);
		faceList.add(f1);
		faceList.add(f2);
		faceList.add(f3);
		faceList.add(f4);
		faceList.add(f5);
		faceList.add(f6);
		faceList.add(f7);
		faceList.add(f8);
	}

	@Override
	public void initTextureBuffer() {
		// TODO Auto-generated method stub
		
	}

	
	
}
