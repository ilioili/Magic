package potato;

import java.util.ArrayList;

import element.Shape;

import AppUtils.Matrix;
import AppUtils.Caculator;
import Resource.MyColors;


public class Potatos2 {
	private float unit;
	private ArrayList<PotatosUnit> unitList;
	public ArrayList <Shape> potatoList;
	public ArrayList<Shape> layer;
	public ArrayList<Shape> layerOther;
	public int layerIndex;
	public Potatos2(float unit){
		this.unit=unit;
		unitList=new ArrayList<PotatosUnit>();
		potatoList=new ArrayList<Shape>();
		layer=new ArrayList<Shape>();
		layerOther=new ArrayList<Shape>();
		float interval=1.012f;
		float d=unit*interval/2+(interval-1)/2;
		PotatosUnit p1=new PotatosUnit(unit,interval);
		PotatosUnit p2=new PotatosUnit(unit,interval);
		PotatosUnit p3=new PotatosUnit(unit,interval);
		PotatosUnit p4=new PotatosUnit(unit,interval);
		PotatosUnit p5=new PotatosUnit(unit,interval);
		PotatosUnit p6=new PotatosUnit(unit,interval);
		PotatosUnit p7=new PotatosUnit(unit,interval);
		PotatosUnit p8=new PotatosUnit(unit,interval);
		p1.move(new float[]{-d,-d,d});
		p2.rotateZ(90);
		p2.move(new float[]{d,-d,d});
		p3.rotateZ(-90);
		p3.move(new float[]{-d,d,d});
		p4.move(new float[]{d,d,d});
		p5.rotateX(90);
		p5.move(new float[]{-d,-d,-d});
		p6.move(new float[]{d,-d,-d});
		p7.move(new float[]{-d,d,-d});
		p8.rotateX(90);
		p8.move(new float[]{d,d,-d});
		unitList.add(p1);
		unitList.add(p2);
		unitList.add(p3);
		unitList.add(p4);
		unitList.add(p5);
		unitList.add(p6);
		unitList.add(p7);
		unitList.add(p8);
		for(int i=0;i<8;i++){
			for(int j=0;j<4;j++){
				potatoList.add(unitList.get(i).unitList.get(j));
			}
		}
		setColor();
		setLayer(0);
	}
 	private void setColor() {
 		for(int i=0;i<potatoList.size();i++){
 			for(int j=0;j<potatoList.get(i).faceList.size();j++){
 				potatoList.get(i).faceList.get(j).setColor(MyColors.grey);
 			}
 		}
 		for(int i=0;i<potatoList.size();i++){
 			for(int j=0;j<3;j++){
 				if(Caculator.isInTheSamePlane(potatoList.get(i).faceList.get(j), potatoList.get(1).faceList.get(1))){//left
 					potatoList.get(i).faceList.get(j).setColor(MyColors.white);
 					potatoList.get(i).faceList.get(j).faceIndex=0;
 				}else if(Caculator.isInTheSamePlane(potatoList.get(i).faceList.get(j), potatoList.get(4).faceList.get(2))){//right
 					potatoList.get(i).faceList.get(j).setColor(MyColors.red);
 					potatoList.get(i).faceList.get(j).faceIndex=1;
 				}else if(Caculator.isInTheSamePlane(potatoList.get(i).faceList.get(j), potatoList.get(0).faceList.get(0))){//front
 					potatoList.get(i).faceList.get(j).setColor(MyColors.blue);
 					potatoList.get(i).faceList.get(j).faceIndex=2;
 				}else if(Caculator.isInTheSamePlane(potatoList.get(i).faceList.get(j), potatoList.get(22).faceList.get(0))){//back
 					potatoList.get(i).faceList.get(j).setColor(MyColors.yellow);
 					potatoList.get(i).faceList.get(j).faceIndex=3;
 				}else if(Caculator.isInTheSamePlane(potatoList.get(i).faceList.get(j), potatoList.get(9).faceList.get(1))){//top
 					potatoList.get(i).faceList.get(j).setColor(MyColors.green);
 					potatoList.get(i).faceList.get(j).faceIndex=4;
 				}else if(Caculator.isInTheSamePlane(potatoList.get(i).faceList.get(j), potatoList.get(0).faceList.get(2))){//bottom
 					potatoList.get(i).faceList.get(j).setColor(MyColors.purple);
 					potatoList.get(i).faceList.get(j).faceIndex=5;
 				}
 			}
 		}
	}
 	public void setLayer(int layerIndex){//横01，竖竖23，左上4，右上5，左下6，右下7，后面依次对应
		this.layerIndex=layerIndex;
		layer.clear();
		layerOther.clear();
		int []index=new int[]{};
		switch(layerIndex){
		case 0:
			index=new int[]{8,9,10,11,12,13,14,15,24,25,26,27,28,29,30,31};
			break;
		case 1:
			index=new int[]{0,1,2,3,4,5,6,7,16,17,18,19,20,21,22,23};
			break;
		case 2:
			index=new int[]{0,1,2,3,8,9,10,11,16,17,18,19,24,25,26,27};
			break;
		case 3:
			index=new int[]{4,5,6,7,12,13,14,15,20,21,22,23,28,29,30,31};
			break;
		case 4:
			index=new int[]{16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
			break;
		case 5:
			index=new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
			break;
		case 6:
			index=new int[]{8};
			break;
		case 7:
			index=new int[]{15};
			break;
		case 8:
			index=new int[]{31};
			break;
		case 9:
			index=new int[]{25};
			break;
		case 10:
			index=new int[]{0};
			break;
		case 11:
			index=new int[]{4};
			break;
		case 12:
			index=new int[]{22};
			break;
		case 13:
			index=new int[]{17};
			break;
		}
		for(int i=0;i<index.length;i++){
			layer.add(potatoList.get(index[i]));
		}
		for(int i=0;i<potatoList.size();i++){
			if(!layer.contains(potatoList.get(i)))
				layerOther.add(potatoList.get(i));
		}
	}
 	
	public void layerRotate(float degree){
		switch(layerIndex){
		case 0:
		case 1:
			for(int i=0;i<layer.size();i++){
				Matrix.rotateY(layer.get(i), degree);
			}
			break;
		case 2:
		case 3:
			for(int i=0;i<layer.size();i++){
				Matrix.rotateX(layer.get(i), degree);
			}
			break;
		case 4:
		case 5:
			for(int i=0;i<layer.size();i++){
				Matrix.rotateZ(layer.get(i), -degree);
			}
			break;
		case 6:
			for(int i=0;i<layer.size();i++){
				layer.get(i).rotate(-1, 1, 1, -degree);
			}
			break;
		case 7:
			for(int i=0;i<layer.size();i++){
				layer.get(i).rotate(1, 1, 1, -degree);
			}
			break;
		case 8:
			for(int i=0;i<layer.size();i++){
				layer.get(i).rotate(1, 1, -1,-degree);
			}
			break;
		case 9:
			for(int i=0;i<layer.size();i++){
				layer.get(i).rotate(-1, 1, -1, -degree);
			}
			break;
		case 10:
			for(int i=0;i<layer.size();i++){
				layer.get(i).rotate(-1, -1, 1, -degree);
			}
			break;
		case 11:
			for(int i=0;i<layer.size();i++){
				layer.get(i).rotate(1, -1, 1, -degree);
			}
			break;
		case 12:
			for(int i=0;i<layer.size();i++){
				layer.get(i).rotate(1, -1, -1, -degree);
			}
			break;
		case 13:
			for(int i=0;i<layer.size();i++){
				layer.get(i).rotate(-1, -1, -1, -degree);
			}
			break;
		}
	}
	public void move(float x,float y,float z){
		for(int i=0;i<potatoList.size();i++){
			potatoList.get(i).move(new float[]{x,y,z});
		}
	}
	public void rotateX(float angle){
		for(int i=0;i<potatoList.size();i++){
			Matrix.rotateX(potatoList.get(i), angle);
		}
	}
	public void rotateY(float angle){
		for(int i=0;i<potatoList.size();i++){
			Matrix.rotateY(potatoList.get(i), angle);
		}
	}
	public void fresh(){
		Potatos2 p=new Potatos2(unit);
		for(int i=0;i<potatoList.size();i++){
			for(int j=0;j<potatoList.size();j++){
					if(p.potatoList.get(i).overlap(potatoList.get(j))){
						p.potatoList.get(i).extractColor(potatoList.get(j));
						break;
					}
			}
		}
		potatoList=p.potatoList;
		setLayer(layerIndex);
	}
	public void setLayer(int faceIndex,int shapeIndex,int directionIndex){
		int i=shapeIndex;
		switch(faceIndex){
		case 0:
			switch(directionIndex){
			case 0:
				if(i==16|i==17|i==24|i==25)
					setLayer(4);
				else setLayer(5);
				break;
			case 1:
				if(i==0|i==1|i==16|i==17)
					setLayer(1);
				else
					setLayer(0);
				break;
			case 2:
				if(i==25) setLayer(9);
				else if(i==0) setLayer(10);
				break;
			case 3:
				if(i==8) setLayer(6);
				else if(i==17) setLayer(13);
				break;
			}
			break;
		case 1:
			switch(directionIndex){
			case 0:
				if(i==4|i==6|i==14|i==15)
					setLayer(5);
				else	setLayer(4);
				break;
			case 1:
				if(i==4|i==6|i==22|i==23)
					setLayer(1);
				else
					setLayer(0);
				break;
			case 2:
				if(i==15)
					setLayer(7);
				else if(i==22) setLayer(12);
				break;
			case 3:
				if(i==4) setLayer(11);
				else if(i==31) setLayer(8);
				break;
			}
			break;
		case 2:
			switch(directionIndex){
			case 0:
				if(i==0|i==3|i==8|i==11)
					setLayer(2);
				else	setLayer(3);
				break;
			case 1:
				if(i==8|i==11|i==12|i==15)
					setLayer(0);
				else
					setLayer(1);
				break;
			case 2:
				if(i==8)
					setLayer(6);
				else if(i==4) setLayer(11);
				break;
			case 3:
				if(i==0)
					setLayer(10);
				else if(i==15) setLayer(7);
				break;
			}
			break;
		case 3:
			switch(directionIndex){
			case 0:
				if(i==17|i==19|i==25|i==26)
					setLayer(2);
				else	setLayer(3);
				break;
			case 1:
				if(i==25|i==26|i==29|i==31)
					setLayer(0);
				else
					setLayer(1);
				break;
			case 2:
				if(i==31)
					setLayer(8);
				else if(i==17) setLayer(13);
				break;
			case 3:
				if(i==22)
					setLayer(12);
				else if(i==25) setLayer(9);
				break;
			}
			break;
		case 4:
			switch(directionIndex){
			case 0:
				if(i==13|i==15|i==31|i==28)
					setLayer(3);
				else	setLayer(2);
				break;
			case 1:
				if(i==8|i==9|i==13|i==15)
					setLayer(5);
				else
					setLayer(4);
				break;
			case 2:
				if(i==25)
					setLayer(9);
				else if(i==15) setLayer(7);
				break;
			case 3:
				if(i==8)
					setLayer(6);
				else if(i==31) setLayer(8);
				break;
			}
			break;
		case 5:
			switch(directionIndex){
			case 0:
				if(i==4|i==5|i==20|i==22)
					setLayer(3);
				else	setLayer(2);
				break;
			case 1:
				if(i==0|i==2|i==4|i==5)
					setLayer(5);
				else
					setLayer(4);
				break;
			case 2:
				if(i==0)
					setLayer(10);
				else if(i==22) setLayer(12);
				break;
			case 3:
				if(i==17)
					setLayer(13);
				else if(i==22) setLayer(12);
				break;
			}
			break;
		}
	}
 	public void putOut(){//用于确定每一层都有那些Shape
		for(int i=0;i<potatoList.size();i++){
			if(potatoList.get(i).origin.xyz[1]>0) 
				System.out.println("layer(0)="+i);
		}
		for(int i=0;i<potatoList.size();i++){
			if(potatoList.get(i).origin.xyz[1]<0) System.out.println("layer(1)="+i);
		}
		for(int i=0;i<potatoList.size();i++){
			if(potatoList.get(i).origin.xyz[0]<0) System.out.println("layer(2)="+i);
		}
		for(int i=0;i<potatoList.size();i++){
			if(potatoList.get(i).origin.xyz[0]>0) System.out.println("layer(3)="+i);
		}
		for(int i=0;i<potatoList.size();i++){
			if(potatoList.get(i).origin.xyz[2]<0) System.out.println("layer(4)="+i);
		}
		for(int i=0;i<potatoList.size();i++){
			if(potatoList.get(i).origin.xyz[2]>0) System.out.println("layer(5)="+i);
		}
	}
}

