package cucumber;

import java.util.ArrayList;

import element.Potato;

import AppUtils.Matrix;


public class CucumberUnit {
	public ArrayList<Potato>	unitList;
	public CucumberUnit(float unit,float interval){
		unitList=new ArrayList<Potato>();
		float s2=(float)Math.sqrt(2);
		Potato p1=new Potato(unit*s2);
		Potato p2=new Potato(unit*s2);
		Potato p3=new Potato(unit*s2);
		Potato p4=new Potato(unit*s2);
		Potato p5=new Potato(unit*s2);
		Potato p6=new Potato(unit*s2);
		Potato p7=new Potato(unit*s2);
		Potato p8=new Potato(unit*s2);
		Matrix.rotateY(p2, 90);
		Matrix.rotateY(p3,180);
		Matrix.rotateY(p4, -90);
		Matrix.rotateX(p5,90);
		Matrix.rotateX(p6,90);
		Matrix.rotateY(p6, 90);
		Matrix.rotateX(p7, 90);
		Matrix.rotateY(p7,180);
		Matrix.rotateX(p8, 90);
		Matrix.rotateY(p8, -90);
		float d=unit*(interval-1)/2;
		p1.move(new float[]{d,d,-d});
		p2.move(new float[]{d,d,d});
		p3.move(new float[]{-d,d,d});
		p4.move(new float[]{-d,d,-d});
		p5.move(new float[]{d,-d,-d});
		p6.move(new float[]{d,-d,d});
		p7.move(new float[]{-d,-d,d});
		p8.move(new float[]{-d,-d,-d});
		unitList.add(p1);
		unitList.add(p2);
		unitList.add(p3);
		unitList.add(p4);
		unitList.add(p5);
		unitList.add(p6);
		unitList.add(p7);
		unitList.add(p8);
	}
	public void move(float[]xyz){
		for(int i=0;i<unitList.size();i++){
			unitList.get(i).move(xyz);
		}
	}
	public void rotateX(float angle){
		for(int i=0;i<unitList.size();i++){
			Matrix.rotateX(unitList.get(i),angle);
		}
	}
	public void rotateY(float angle){
		for(int i=0;i<unitList.size();i++){
			Matrix.rotateY(unitList.get(i),angle);
		}
	}
	public void rotateZ(float angle){
		for(int i=0;i<unitList.size();i++){
			Matrix.rotateZ(unitList.get(i),angle);
		}
	}
}
