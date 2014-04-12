package eggplant;

import java.util.ArrayList;

import element.Pyramid;
import element.Shape;
import element.Vertex;


import AppUtils.Matrix;
import AppUtils.Caculator;
import Resource.MyColors;


public class Eggplants {
	private float unit;
	ArrayList <Shape> eggplantList;
	int layerIndex;
	public ArrayList<Shape> layer;
	public ArrayList<Shape> layerOther;
	Eggplants(float unit){
		this.unit=unit;
		eggplantList=new ArrayList<Shape>();
		layer=new ArrayList<Shape>();
		layerOther=new ArrayList<Shape>();
		Eggplant t1=new Eggplant(unit);
		Eggplant t2=new Eggplant(unit);
		Eggplant t3=new Eggplant(unit);
		Eggplant t4=new Eggplant(unit);
		Eggplant t5=new Eggplant(unit);
		Eggplant t6=new Eggplant(unit);
		Pyramid p7=new Pyramid(2*unit);
		Pyramid p8=new Pyramid(2*unit);
		Pyramid p9=new Pyramid(2*unit);
		Pyramid p10=new Pyramid(2*unit);
		Pyramid p11=new Pyramid(2*unit);
		Pyramid p12=new Pyramid(2*unit);
		Pyramid p13=new Pyramid(2*unit);
		Pyramid p14=new Pyramid(2*unit);
		float f=1.02f;//产生间隙
		float s3=(float)Math.sqrt(3);
		float s2=(float)Math.sqrt(2);
		float angle=(float)(180-Math.acos(s3/3f)*180/Math.PI);
		float dy=(f-1)*unit/2;
		float dxz=dy*s2/2;
		t1.move(new float[]{0,s2*unit*f,0});//上顶
		t2.move(new float[]{-unit*f,0,unit*f});//左前
		t3.move(new float[]{unit*f,0,unit*f});//右前
		t4.move(new float[]{unit*f,0,-unit*f});//右后
		t5.move(new float[]{-unit*f,0,-unit*f});//左后
		t6.move(new float[]{0,-s2*unit*f,0});//下顶
		p7.rotate(1, 0, 0, angle);//上前
		p7.move(new float[]{0,s2*unit/2+dy,f*unit-dxz});
		p8.rotate(1, 0, 0, angle);//上后
		p8.move(new float[]{0,s2*unit/2+dy,-f*unit+dxz});
		p9.rotate(1, 0, 0, angle);//下左
		p9.rotate(0, 1, 0, 90);
		p9.move(new float[]{-f*unit+dxz,s2*unit/2+dy,0});
		p10.rotate(1, 0, 0, angle);//下右
		p10.rotate(0, 1, 0, 90);
		p10.move(new float[]{f*unit-dxz,s2*unit/2+dy,0});
		p11.rotate(1, 0, 0, -180+angle);//下前
		p11.move(new float[]{0,-s2*unit/2-dy,f*unit-dxz});
		p12.rotate(1, 0, 0, -180+angle);//下后
		p12.move(new float[]{0,-s2*unit/2-dy,-f*unit+dxz});
		p13.rotate(1, 0, 0, -180+angle);//下左
		p13.rotate(0, 1, 0, 90);
		p13.move(new float[]{-f*unit+dxz,-s2*unit/2-dy,0});
		p14.rotate(1, 0, 0, -180+angle);//下右
		p14.rotate(0, 1, 0, 90);
		p14.move(new float[]{f*unit-dxz,-s2*unit/2-dy,0});
		eggplantList.add(t1);
		eggplantList.add(t2);
		eggplantList.add(t3);
		eggplantList.add(t4);
		eggplantList.add(t5);
		eggplantList.add(t6);
		eggplantList.add(p7);
		eggplantList.add(p8);
		eggplantList.add(p9);
		eggplantList.add(p10);
		eggplantList.add(p11);
		eggplantList.add(p12);
		eggplantList.add(p13);
		eggplantList.add(p14);
		setColor();
		setLayer(0);
	}
 	private void setColor() {
		for(int i=0;i<eggplantList.size();i++){
			eggplantList.get(i).setColor(MyColors.black);
		}
		for(int i=0;i<eggplantList.size();i++){
 			for(int j=0;j<eggplantList.get(i).faceList.size();j++){
 				if(Caculator.isInTheSamePlane(eggplantList.get(i).faceList.get(j), eggplantList.get(0).faceList.get(0))){
 					eggplantList.get(i).faceList.get(j).setColor(MyColors.white);
 					eggplantList.get(i).faceList.get(j).faceIndex=0;
 				}else if(Caculator.isInTheSamePlane(eggplantList.get(i).faceList.get(j), eggplantList.get(0).faceList.get(1))){
 					eggplantList.get(i).faceList.get(j).setColor(MyColors.red);
 					eggplantList.get(i).faceList.get(j).faceIndex=1;
 				}else if(Caculator.isInTheSamePlane(eggplantList.get(i).faceList.get(j), eggplantList.get(0).faceList.get(2))){
 					eggplantList.get(i).faceList.get(j).setColor(MyColors.blue);
 					eggplantList.get(i).faceList.get(j).faceIndex=2;
 				}else if(Caculator.isInTheSamePlane(eggplantList.get(i).faceList.get(j), eggplantList.get(0).faceList.get(3))){
 					eggplantList.get(i).faceList.get(j).setColor(MyColors.yellow);
 					eggplantList.get(i).faceList.get(j).faceIndex=3;
 				}else if(Caculator.isInTheSamePlane(eggplantList.get(i).faceList.get(j), eggplantList.get(5).faceList.get(4))){
 					eggplantList.get(i).faceList.get(j).setColor(MyColors.green);
 					eggplantList.get(i).faceList.get(j).faceIndex=4;
 				}else if(Caculator.isInTheSamePlane(eggplantList.get(i).faceList.get(j), eggplantList.get(5).faceList.get(5))){
 					eggplantList.get(i).faceList.get(j).setColor(MyColors.purple);
 					eggplantList.get(i).faceList.get(j).faceIndex=5;
 				}else if(Caculator.isInTheSamePlane(eggplantList.get(i).faceList.get(j), eggplantList.get(5).faceList.get(6))){
 					eggplantList.get(i).faceList.get(j).setColor(MyColors.seablue);
 					eggplantList.get(i).faceList.get(j).faceIndex=6;
 				}else if(Caculator.isInTheSamePlane(eggplantList.get(i).faceList.get(j), eggplantList.get(5).faceList.get(7))){
 					eggplantList.get(i).faceList.get(j).setColor(MyColors.darkred);
 					eggplantList.get(i).faceList.get(j).faceIndex=7;
 				}
 			}
		}
	}
	public void setLayer(int layerIndex){
		System.out.println("layerIndex="+layerIndex);
		this.layerIndex=layerIndex;
		layer.clear();
		layerOther.clear();
		int []index=new int[]{};
		switch(layerIndex){
		case 0:
			index=new int[]{0,1,4,6,7,8,12};
			break;
		case 1:
			index=new int[]{2,3,5,9,10,11,13};
			break;
		case 2:
			index=new int[]{1,4,5,8,10,11,12};
			break;
		case 3:
			index=new int[]{0,2,3,6,7,9,13};
			break;
		case 4:
			index=new int[]{0,1,2,6,8,9,10};
			break;
		case 5:
			index=new int[]{3,4,5,7,11,12,13};
			break;
		case 6:
			index=new int[]{1,2,5,6,10,12,13};
			break;
		case 7:
			index=new int[]{0,3,4,7,8,9,11};
			break;
		}
		for(int i=0;i<7;i++){
			layer.add(eggplantList.get(index[i]));
		}
		for(int i=0;i<14;i++){
			if(!layer.contains(eggplantList.get(i)))
				layerOther.add(eggplantList.get(i));
		}
	}
	public void layerRotate(float degree){
		Vertex v=new Vertex();
		switch(layerIndex){
		case 0:
		case 1:
			v=Caculator.getFaceN(eggplantList.get(0).faceList.get(3));
			for(int i=0;i<7;i++){
				Matrix.rotate(layer.get(i), new double[]{v.xyz[0],v.xyz[1],v.xyz[2]}, degree);
			}
			break;
		case 2:
		case 3:
			v=Caculator.getFaceN(eggplantList.get(0).faceList.get(1));
			for(int i=0;i<7;i++){
				Matrix.rotate(layer.get(i), new double[]{v.xyz[0],v.xyz[1],v.xyz[2]}, degree);
			}
			break;
		case 4:
		case 5:
			v=Caculator.getFaceN(eggplantList.get(0).faceList.get(0));
			for(int i=0;i<7;i++){
				Matrix.rotate(layer.get(i), new double[]{v.xyz[0],v.xyz[1],v.xyz[2]}, degree);
			}
			break;
		case 6:
		case 7:
			v=Caculator.getFaceN(eggplantList.get(0).faceList.get(2));
			for(int i=0;i<7;i++){
				Matrix.rotate(layer.get(i), new double[]{v.xyz[0],v.xyz[1],v.xyz[2]}, degree);
			}
			break;
		}
	}
	public void move(float x,float y,float z){
		for(int i=0;i<eggplantList.size();i++){
			eggplantList.get(i).move(new float[]{x,y,z});
		}
	}
	public void rotateX(float angle){
		for(int i=0;i<eggplantList.size();i++){
			Matrix.rotateX(eggplantList.get(i),angle);
		}
	}
	public void rotateY(float angle){
		for(int i=0;i<eggplantList.size();i++){
			Matrix.rotateY(eggplantList.get(i),angle);
		}
	}
	public void fresh(){
		Eggplants t=new Eggplants(unit);
		for(int i=0;i<eggplantList.size();i++){
			for(int j=0;j<eggplantList.size();j++){
					if(t.eggplantList.get(i).overlap(eggplantList.get(j))){
						t.eggplantList.get(i).extractColor(eggplantList.get(j));
						break;
					}
			}
		}
		eggplantList=t.eggplantList;
		setLayer(layerIndex);
	}
	public void setLayer(int faceIndex,int shapeIndex,int direction){
		System.out.println("faceIndex="+faceIndex+"---shapeIndex="+shapeIndex+"--direction="+direction);
		switch(faceIndex){
		case 0:
			switch(shapeIndex){
			case 0:
				if(direction==0) setLayer(0);
				else if(direction==1) setLayer(3);
				else setLayer(7);
				break;
			case 6:
				if(direction==0) setLayer(0);
				else if(direction==1) setLayer(3);
				else setLayer(6);
				break;
			case 1:
				if(direction==0) setLayer(0);
				else if(direction==1) setLayer(2);
				else setLayer(6);
				break;
			case 2:
				if(direction==0) setLayer(1);
				else if(direction==1) setLayer(3);
				else setLayer(6);
				break;
			}
			break;
		case 1:
			switch(shapeIndex){
			case 0:
				if(direction==0) setLayer(4);
				else if(direction==1) setLayer(7);
				else setLayer(0);
				break;
			case 9:
				if(direction==0) setLayer(4);
				else if(direction==1) setLayer(7);
				else setLayer(1);
				break;
			case 2:
				if(direction==0) setLayer(4);
				else if(direction==1) setLayer(6);
				else setLayer(1);
				break;
			case 3:
				if(direction==0) setLayer(5);
				else if(direction==1) setLayer(7);
				else setLayer(1);
				break;
			}
			break;
		case 2:
			switch(shapeIndex){
			case 0:
				if(direction==0) setLayer(3);
				else if(direction==1) setLayer(0);
				else setLayer(4);
				break;
			case 7:
				if(direction==0) setLayer(3);
				else if(direction==1) setLayer(0);
				else setLayer(5);
				break;
			case 3:
				if(direction==0) setLayer(3);
				else if(direction==1) setLayer(1);
				else setLayer(5);
				break;
			case 4:
				if(direction==0) setLayer(2);
				else if(direction==1) setLayer(0);
				else setLayer(5);
				break;
			}
			break;
		case 3:
			switch(shapeIndex){
			case 0:
				if(direction==0) setLayer(7);
				else if(direction==1) setLayer(4);
				else setLayer(3);
				break;
			case 8:
				if(direction==0) setLayer(7);
				else if(direction==1) setLayer(4);
				else setLayer(2);
				break;
			case 4:
				if(direction==0) setLayer(7);
				else if(direction==1) setLayer(5);
				else setLayer(2);
				break;
			case 1:
				if(direction==0) setLayer(6);
				else if(direction==1) setLayer(4);
				else setLayer(2);
				break;
			}
			break;
		case 4:
			switch(shapeIndex){
			case 5:
				if(direction==0) setLayer(1);
				else if(direction==1) setLayer(2);
				else setLayer(5);
				break;
			case 10:
				if(direction==0) setLayer(1);
				else if(direction==1) setLayer(2);
				else setLayer(4);
				break;
			case 1:
				if(direction==0) setLayer(0);
				else if(direction==1) setLayer(2);
				else setLayer(4);
				break;
			case 2:
				if(direction==0) setLayer(1);
				else if(direction==1) setLayer(3);
				else setLayer(4);
				break;
			}
			break;
		case 5:
			switch(shapeIndex){
			case 5:
				if(direction==0) setLayer(5);
				else if(direction==1) setLayer(6);
				else setLayer(2);
				break;
			case 13:
				if(direction==0) setLayer(5);
				else if(direction==1) setLayer(6);
				else setLayer(3);
				break;
			case 2:
				if(direction==0) setLayer(4);
				else if(direction==1) setLayer(6);
				else setLayer(3);
				break;
			case 3:
				if(direction==0) setLayer(5);
				else if(direction==1) setLayer(7);
				else setLayer(3);
				break;
			}
			break;
		case 6:
			switch(shapeIndex){
			case 5:
				if(direction==0) setLayer(2);
				else if(direction==1) setLayer(1);
				else setLayer(6);
				break;
			case 11:
				if(direction==0) setLayer(2);
				else if(direction==1) setLayer(1);
				else setLayer(7);
				break;
			case 3:
				if(direction==0) setLayer(3);
				else if(direction==1) setLayer(1);
				else setLayer(7);
				break;
			case 4:
				if(direction==0) setLayer(2);
				else if(direction==1) setLayer(0);
				else setLayer(7);
				break;
			}
			break;
		case 7:
			switch(shapeIndex){
			case 5:
				if(direction==0) setLayer(6);
				else if(direction==1) setLayer(5);
				else setLayer(1);
				break;
			case 12:
				if(direction==0) setLayer(6);
				else if(direction==1) setLayer(5);
				else setLayer(0);
				break;
			case 4:
				if(direction==0) setLayer(7);
				else if(direction==1) setLayer(5);
				else setLayer(0);
				break;
			case 1:
				if(direction==0) setLayer(6);
				else if(direction==1) setLayer(4);
				else setLayer(0);
				break;
			}
			break;
		}
	}
}
