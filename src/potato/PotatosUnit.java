package potato;

import java.util.ArrayList;

import element.Potato;
import element.Shape;

import AppUtils.Matrix;


public class PotatosUnit {
	public ArrayList<Shape> unitList;
	public PotatosUnit(float unit,float interval){
		unitList=new ArrayList<Shape>();
		float d=unit*interval/2;
		Potato p1=new Potato(unit);
		Potato p2=new Potato(unit);
		Potato p3=new Potato(unit);
		Potato p4=new Potato(unit);
		p1.move(new float[]{-d,-d,+d});
		Matrix.rotateX(p2, 180);
		p2.move(new float[]{-d,d,-d});
		Matrix.rotateY(p3,180);
		p3.move(new float[]{d,-d,-d});
		Matrix.rotateZ(p4,180);
		p4.move(new float[]{d,d,d});
		unitList.add(p1);
		unitList.add(p2);
		unitList.add(p3);
		unitList.add(p4);
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
