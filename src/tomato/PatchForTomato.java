package tomato;

import element.Face;
import element.Shape;
import element.Vertex;
import AppUtils.Caculator;
import Resource.MyColors;


public class PatchForTomato extends Shape{
	public PatchForTomato(float unit){
		Vertex v1=new Vertex(new float[]{unit, unit, unit});
		Vertex v2=new Vertex(new float[]{unit, -unit, unit});
		Vertex v3=new Vertex(new float[]{unit, -unit, -unit});
		Vertex v4=new Vertex(new float[]{unit, unit, -unit});
		Vertex v5=new Vertex(new float[]{-unit, unit, unit});
		Vertex v6=new Vertex(new float[]{-unit, unit, -unit});
		Vertex v7=new Vertex(new float[]{-unit, -unit, -unit});
		Vertex v8=new Vertex(new float[]{-unit, -unit, unit});
		Face f1=new Face(new Vertex[]{v2,v5,v4},MyColors.white);
		Face f2=new Face(new Vertex[]{v2,v4,v7},MyColors.yellow);
		Face f3=new Face(new Vertex[]{v7,v4,v5},MyColors.blue);
		Face f4=new Face(new Vertex[]{v7,v5,v2},MyColors.purple);
		Face f5=new Face(new Vertex[]{v8,v1,v3},MyColors.yellow);
		Face f6=new Face(new Vertex[]{v3,v1,v6},MyColors.blue);
		Face f7=new Face(new Vertex[]{v3,v6,v8},MyColors.purple);
		Face f8=new Face(new Vertex[]{v6,v1,v8},MyColors.white);
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
