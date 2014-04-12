package element;

import AppUtils.Caculator;
import Resource.MyColors;


public class Pyramid extends Shape{
	public Pyramid(float unit){
		float s6=(float)Math.sqrt(6);
		float s3=(float)Math.sqrt(3);
		Vertex v1=new Vertex(new float[]{0,				unit*s6/4,			0				});
		Vertex v2=new Vertex(new float[]{unit/2,		-unit*s6/12,		unit*s3/6	});
		Vertex v3=new Vertex(new float[]{-unit/2,	-unit*s6/12,		unit*s3/6	});
		Vertex v4=new Vertex(new float[]{0,				-unit*s6/12,		-unit*s3/3	});
		Face f1=new Face(new Vertex[]{v1,v2,v3});
		Face f2=new Face(new Vertex[]{v1,v3,v4});
		Face f3=new Face(new Vertex[]{v1,v4,v2});
		Face f4=new Face(new Vertex[]{v2,v4,v3});
		f1.setColor(MyColors.green);
		f1.origin=Caculator.getCenterPoint(f1);
		f2.setColor(MyColors.white);
		f2.origin=Caculator.getCenterPoint(f2);
		f3.setColor(MyColors.yellow);
		f3.origin=Caculator.getCenterPoint(f3);
		f4.setColor(MyColors.red);
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
