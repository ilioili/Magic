package element;

import AppUtils.Caculator;


public class Patch extends Shape{
	public Patch(float unit){
		float s6=(float)Math.sqrt(6);
		float s3=(float)Math.sqrt(3);
		Vertex v1=new Vertex(new float[]{0,				unit*s6/6,		-unit*s3/3	});
		Vertex v2=new Vertex(new float[]{unit/2,		unit*s6/6,		unit*s3/6	});
		Vertex v3=new Vertex(new float[]{-unit/2,	unit*s6/6,		unit*s3/6	});
		Vertex v4=new Vertex(new float[]{-unit/2,	-unit*s6/6,	-unit*s3/6	});
		Vertex v5=new Vertex(new float[]{unit/2,		-unit*s6/6,	-unit*s3/6	});
		Vertex v6=new Vertex(new float[]{0,				-unit*s6/6,	unit*s3/3	});
		Face f1=new Face(new Vertex[]{v6,v3,v2});//front visible
		f1.origin=Caculator.getCenterPoint(f1);
		Face f2=new Face(new Vertex[]{v4,v1,v3});//left visible
		f2.origin=Caculator.getCenterPoint(f2);
		Face f3=new Face(new Vertex[]{v5,v2,v1});//right visible
		f3.origin=Caculator.getCenterPoint(f3);
		Face f4=new Face(new Vertex[]{v6,v5,v4});//bottom visible
		f4.origin=Caculator.getCenterPoint(f4);
		Face f5=new Face(new Vertex[]{v1,v2,v3});//top invisible
		f5.origin=Caculator.getCenterPoint(f5);
		Face f6=new Face(new Vertex[]{v1,v4,v5});//invisible
		f6.origin=Caculator.getCenterPoint(f6);
		Face f7=new Face(new Vertex[]{v2,v5,v6});//invisible
		f7.origin=Caculator.getCenterPoint(f7);
		Face f8=new Face(new Vertex[]{v3,v6,v4});//invisible
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
