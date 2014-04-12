package tomato;

import java.util.ArrayList;

import element.Shape;

import AppUtils.Matrix;
import Resource.MyColors;


public class Tomatos {
	private float unit;
	ArrayList <Shape> tomatoList;
	int layerIndex;
	public ArrayList<Shape> layer;
	public ArrayList<Shape> layerOther;
	Tomatos(float unit){
		this.unit=unit;
		tomatoList=new ArrayList<Shape>();
		layer=new ArrayList<Shape>();
		layerOther=new ArrayList<Shape>();
		Tomato t1=new Tomato(unit);
		Tomato t2=new Tomato(unit);
		Tomato t3=new Tomato(unit);
		Tomato t4=new Tomato(unit);
		Tomato t5=new Tomato(unit);
		Tomato t6=new Tomato(unit);
		Tomato t7=new Tomato(unit);
		Tomato t8=new Tomato(unit);
		Tomato t9=new Tomato(unit);
		Tomato t10=new Tomato(unit);
		Tomato t11=new Tomato(unit);
		Tomato t12=new Tomato(unit);
		float inteveral=1.01f;
		t1.move(new float[]{0,0,0});//前左
		t2.rotate(0, 1, 0, -90);//前右
		t2.move(new float[]{inteveral*unit*2,0,0});
		t3.rotate(0, 1, 0, 180);//后右
		t3.move(new float[]{inteveral*unit*2,0,-inteveral*unit*2});
		t4.rotate(0,1,0,90);//后左
		t4.move(new float[]{0,0,-inteveral*unit*2});
		t5.rotate(0, 0, 1, 90);//前上
		t5.move(new float[]{inteveral*unit,inteveral*unit,0});
		t6.rotate(0, 0, 1, -90);//前下
		t6.move(new float[]{inteveral*unit,-inteveral*unit,0});
		t7.rotate(0, 0, 1, 90);//后下
		t7.rotate(1, 0, 0, 180);
		t7.move(new float[]{inteveral*unit,-inteveral*unit,-2*inteveral*unit});
		t8.rotate(0, 0, 1, 90);//后上
		t8.rotate(1, 0, 0, 90);
		t8.move(new float[]{inteveral*unit,inteveral*unit,-2*inteveral*unit});
		t9.rotate(1, 0, 0, 90);//上左
		t9.move(new float[]{0,inteveral*unit,-inteveral*unit});
		t10.rotate(1, 0, 0, -90);//下左
		t10.move(new float[]{0,-inteveral*unit,-inteveral*unit});
		t11.rotate(1, 0, 0, 90);//下右
		t11.rotate(0, 0, 1, 180);
		t11.move(new float[]{inteveral*unit*2,-inteveral*unit,-inteveral*unit});
		t12.rotate(1, 0, 0, 90);//上右
		t12.rotate(0, 0, 1, 90);
		t12.move(new float[]{inteveral*unit*2,inteveral*unit,-inteveral*unit});
		
		tomatoList.add(t1);
		tomatoList.add(t2);
		tomatoList.add(t3);
		tomatoList.add(t4);
		tomatoList.add(t5);
		tomatoList.add(t6);
		tomatoList.add(t7);
		tomatoList.add(t8);
		tomatoList.add(t9);
		tomatoList.add(t10);
		tomatoList.add(t11);
		tomatoList.add(t12);
		PatchForTomato p=new PatchForTomato(unit);
		tomatoList.add(p);
		for(int i=0;i<12;i++){
			tomatoList.get(i).move(new float[]{-unit*inteveral,0,unit*inteveral});
		}
		setColor();
		setLayer(0);
	}
 	private void setColor() {
		// TODO Auto-generated method stub
		for(int i=0;i<13;i++){
			tomatoList.get(i).setColor(MyColors.grey);
		}
		for(int i=0;i<12;i++){
			for(int j=0;j<4;j++){
				if(unit+tomatoList.get(i).faceList.get(j).origin.xyz[0]<0.000001){
					tomatoList.get(i).faceList.get(j).setColor(MyColors.white);
					tomatoList.get(i).faceList.get(j).setFaceIndex(0);
				}else if(unit-tomatoList.get(i).faceList.get(j).origin.xyz[0]<0.000001){
					tomatoList.get(i).faceList.get(j).setColor(MyColors.blue);
					tomatoList.get(i).faceList.get(j).setFaceIndex(1);
				}else if(unit-tomatoList.get(i).faceList.get(j).origin.xyz[2]<0.000001){
					tomatoList.get(i).faceList.get(j).setColor(MyColors.purple);
					tomatoList.get(i).faceList.get(j).setFaceIndex(2);
				}else if(unit+tomatoList.get(i).faceList.get(j).origin.xyz[2]<0.000001){
					tomatoList.get(i).faceList.get(j).setColor(MyColors.green);
					tomatoList.get(i).faceList.get(j).setFaceIndex(3);
				}else if(unit-tomatoList.get(i).faceList.get(j).origin.xyz[1]<0.000001){
					tomatoList.get(i).faceList.get(j).setColor(MyColors.red);
					tomatoList.get(i).faceList.get(j).setFaceIndex(4);
				}else if(unit+tomatoList.get(i).faceList.get(j).origin.xyz[1]<0.000001){
					tomatoList.get(i).faceList.get(j).setColor(MyColors.yellow);
					tomatoList.get(i).faceList.get(j).setFaceIndex(5);
				}
			}
		}
	}
	public void setLayer(int layerIndex){
		this.layerIndex=layerIndex;
		layer.clear();
		layerOther.clear();
		int []index=new int[]{};
		switch(layerIndex){
		case 0:
			index=new int[]{4,0,8};
			break;
		case 1:
			index=new int[]{4,1,11};
			break;
		case 2:
			index=new int[]{11,7,2};
			break;
		case 3:
			index=new int[]{8,7,3};
			break;
		case 4:
			index=new int[]{0,5,9};
			break;
		case 5:
			index=new int[]{1,5,10};
			break;
		case 6:
			index=new int[]{10,2,6};
			break;
		case 7:
			index=new int[]{9,3,6};
			break;
		}
		for(int i=0;i<3;i++){
			layer.add(tomatoList.get(index[i]));
		}
		for(int i=0;i<13;i++){
			if(!layer.contains(tomatoList.get(i)))
				layerOther.add(tomatoList.get(i));
		}
	}
	public void layerRotate(float degree){
		switch(layerIndex){
		case 0:
			for(int i=0;i<3;i++){
				Matrix.rotate(layer.get(i), new double[]{-1,1,1}, degree);
			}
			break;
		case 1:
			for(int i=0;i<3;i++){
				Matrix.rotate(layer.get(i), new double[]{1,1,1}, degree);
			}
			break;
		case 2:
			for(int i=0;i<3;i++){
				Matrix.rotate(layer.get(i), new double[]{1,1,-1}, degree);
			}
			break;
		case 3:
			for(int i=0;i<3;i++){
				Matrix.rotate(layer.get(i), new double[]{-1,1,-1}, degree);
			}
			break;
		case 4:
			for(int i=0;i<3;i++){
				Matrix.rotate(layer.get(i), new double[]{-1,-1,1}, degree);
			}
			break;
		case 5:
			for(int i=0;i<3;i++){
				Matrix.rotate(layer.get(i), new double[]{1,-1,1}, degree);
			}
			break;
		case 6:
			for(int i=0;i<3;i++){
				Matrix.rotate(layer.get(i), new double[]{1,-1,-1}, degree);
			}
			break;
		case 7:
			for(int i=0;i<3;i++){
				Matrix.rotate(layer.get(i), new double[]{-1,-1,-1}, degree);
			}
			break;
		}
	}
	public void move(float x,float y,float z){
		for(int i=0;i<tomatoList.size();i++){
			tomatoList.get(i).move(new float[]{x,y,z});
		}
	}
	public void rotateX(float angle){
		for(int i=0;i<tomatoList.size();i++){
			Matrix.rotateX(tomatoList.get(i),angle);
		}
	}
	public void rotateY(float angle){
		for(int i=0;i<tomatoList.size();i++){
			Matrix.rotateY(tomatoList.get(i),angle);
		}
	}
	public void fresh(){
		Tomatos t=new Tomatos(unit);
		for(int i=0;i<12;i++){
			for(int j=0;j<12;j++){
					if(t.tomatoList.get(i).overlap(tomatoList.get(j))){
						t.tomatoList.get(i).extractColor(tomatoList.get(j));
						break;
					}
			}
		}
		tomatoList=t.tomatoList;
		setLayer(layerIndex);
	}
	public void setLayer(int faceIndex,int shapeIndex,boolean r){
		switch(faceIndex){
		case 0://左面
			switch(shapeIndex){
			case 0:
				if(r) setLayer(4);
				else setLayer(0);
				break;
			case 3:
				if(r) setLayer(3);
				else setLayer(7);
				break;
			case 8:
				if(r) setLayer(3);
				else setLayer(0);
				break;
			case 9:
				if(r) setLayer(4);
				else setLayer(7);
				break;
			}
			break;
		case 1://右面
			switch(shapeIndex){
			case 1:
				if(r) setLayer(1);
				else setLayer(5);
				break;
			case 2:
				if(r) setLayer(6);
				else setLayer(2);
				break;
			case 10:
				if(r) setLayer(6);
				else setLayer(5);
				break;
			case 11:
				if(r) setLayer(1);
				else setLayer(2);
				break;
			}
			break;
		case 2://前面
			switch(shapeIndex){
			case 0:
				if(r) setLayer(0);
				else setLayer(4);
				break;
			case 1:
				if(r) setLayer(5);
				else setLayer(1);
				break;
			case 4:
				if(r) setLayer(0);
				else setLayer(1);
				break;
			case 5:
				if(r) setLayer(5);
				else setLayer(4);
				break;
			}
			break;
		case 3://后面
			switch(shapeIndex){
			case 2:
				if(r) setLayer(2);
				else setLayer(6);
				break;
			case 3:
				if(r) setLayer(7);
				else setLayer(3);
				break;
			case 6:
				if(r) setLayer(7);
				else setLayer(6);
				break;
			case 7:
				if(r) setLayer(2);
				else setLayer(3);
				break;
			}
			break;
		case 4://上面
			switch(shapeIndex){
			case 4:
				if(r) setLayer(1);
				else setLayer(0);
				break;
			case 7:
				if(r) setLayer(3);
				else setLayer(2);
				break;
			case 8:
				if(r) setLayer(3);
				else setLayer(0);
				break;
			case 11:
				if(r) setLayer(1);
				else setLayer(2);
				break;
			}
			break;
		case 5://下面
			switch(shapeIndex){
			case 5:
				if(r) setLayer(4);
				else setLayer(5);
				break;
			case 6:
				if(r) setLayer(6);
				else setLayer(7);
				break;
			case 9:
				if(r) setLayer(4);
				else setLayer(7);
				break;
			case 10:
				if(r) setLayer(6);
				else setLayer(5);
				break;
			}
			break;
		}
	}
	
}
