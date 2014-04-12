package element;

import AppUtils.Caculator;
import Resource.MyColors;


public class Potato extends Shape{
	public Potato(float unit){
		Vertex v0=new Vertex(new float[]{0,0,0});
		Vertex v1=new Vertex(new float[]{unit,0,0});
		Vertex v2=new Vertex(new float[]{0,unit,0});
		Vertex v3=new Vertex(new float[]{0,0,-unit});
		Face f1=new Face(new Vertex[]{v0,v2,v1},MyColors.blue);
		Face f2=new Face(new Vertex[]{v3,v2,v0},MyColors.white);
		Face f3=new Face(new Vertex[]{v0,v1,v3},MyColors.darkred);
		Face f4=new Face(new Vertex[]{v1,v2,v3},MyColors.purple);
		f1.origin=Caculator.getCenterPoint(f1);
		f2.origin=Caculator.getCenterPoint(f2);
		f3.origin=Caculator.getCenterPoint(f3);
		f4.origin=Caculator.getCenterPoint(f4);
		
		faceList.add(f1);
		faceList.add(f2);
		faceList.add(f3);
		faceList.add(f4);
	}

	@Override
	public void initTextureBuffer() {
		// TODO Auto-generated method stub
		
	}

}
