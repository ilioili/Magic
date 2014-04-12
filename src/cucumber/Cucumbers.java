package cucumber;

import java.util.ArrayList;

import element.Face;
import element.Pyramid;
import element.Shape;
import element.Vertex;

import AppUtils.Matrix;
import AppUtils.Caculator;
import Resource.MyColors;



public class Cucumbers {
	private float unit;
	private ArrayList<CucumberUnit> unitsList;
	public ArrayList <Shape> potatoList;
	public ArrayList<Shape> layer;
	public ArrayList<Shape> layerOther;
	public int layerIndex;
	public Cucumbers(float unit){
		this.unit=unit;
		unitsList=new ArrayList<CucumberUnit>();
		potatoList=new ArrayList<Shape>();
		layer=new ArrayList<Shape>();
		layerOther=new ArrayList<Shape>();
		float interval=1.1f;
		float s2=(float)Math.sqrt(2);
		CucumberUnit p1=new CucumberUnit(unit,interval);
		CucumberUnit p2=new CucumberUnit(unit,interval);
		CucumberUnit p3=new CucumberUnit(unit,interval);
		CucumberUnit p4=new CucumberUnit(unit,interval);
		CucumberUnit p5=new CucumberUnit(unit,interval);
		CucumberUnit p6=new CucumberUnit(unit,interval);
		Pyramid p7= new Pyramid(2*unit);
		Pyramid p8= new Pyramid(2*unit);
		Pyramid p9= new Pyramid(2*unit);
		Pyramid p10=new Pyramid(2*unit);
		Pyramid p11=new Pyramid(2*unit);
		Pyramid p12=new Pyramid(2*unit);
		Pyramid p13=new Pyramid(2*unit);
		Pyramid p14=new Pyramid(2*unit);
		float f=1f;//产生间隙
		float s3=(float)Math.sqrt(3);
		float angle=(float)(180-Math.acos(s3/3f)*180/Math.PI);
		float dy=(f*interval-1)*unit/2;
		float dxz=dy*s2/2;
		p1.rotateY(45);
		p2.rotateY(45);
		p3.rotateY(45);
		p4.rotateY(45);
		p5.rotateY(45);
		p6.rotateY(45);
		p1.move(new float[]{0,s2*unit*interval*f,0});//上顶
		p2.move(new float[]{-unit*interval*f,0,unit*interval*f});//左前
		p3.move(new float[]{unit*interval*f,0,unit*interval*f});//右前
		p4.move(new float[]{unit*interval*f,0,-unit*interval*f});//右后
		p5.move(new float[]{-unit*interval*f,0,-unit*interval*f});//左后
		p6.move(new float[]{0,-s2*unit*interval*f,0});//下顶
		
		p7.rotate(1, 0, 0, angle);//上前
		p7.move(new float[]{0,s2*unit/2+dy,f*(2*interval-1)*unit-dxz});
		p8.rotate(1, 0, 0, angle);//上后
		p8.move(new float[]{0,s2*unit/2+dy,-f*(2*interval-1)*unit+dxz});
		p9.rotate(1, 0, 0, angle);//下左
		p9.rotate(0, 1, 0, 90);
		p9.move(new float[]{-f*(2*interval-1)*unit+dxz,s2*unit/2+dy,0});
		p10.rotate(1, 0, 0, angle);//下右
		p10.rotate(0, 1, 0, 90);
		p10.move(new float[]{f*(2*interval-1)*unit-dxz,s2*unit/2+dy,0});
		p11.rotate(1, 0, 0, -180+angle);//下前
		p11.move(new float[]{0,-s2*unit/2-dy,f*(2*interval-1)*unit-dxz});
		p12.rotate(1, 0, 0, -180+angle);//下后
		p12.move(new float[]{0,-s2*unit/2-dy,-f*(2*interval-1)*unit+dxz});
		p13.rotate(1, 0, 0, -180+angle);//下左
		p13.rotate(0, 1, 0, 90);
		p13.move(new float[]{-f*(2*interval-1)*unit+dxz,-s2*unit/2-dy,0});
		p14.rotate(1, 0, 0, -180+angle);//下右
		p14.rotate(0, 1, 0, 90);
		p14.move(new float[]{f*(2*interval-1)*unit-dxz,-s2*unit/2-dy,0});
		unitsList.add(p1);
		unitsList.add(p2);
		unitsList.add(p3);
		unitsList.add(p4);
		unitsList.add(p5);
		unitsList.add(p6);
		for(int i=0;i<unitsList.size();i++){
			for(int j=0;j<unitsList.get(i).unitList.size();j++){
				potatoList.add(unitsList.get(i).unitList.get(j));
			}
		}
		potatoList.add(p7);
		potatoList.add(p8);
		potatoList.add(p9);
		potatoList.add(p10);
		potatoList.add(p11);
		potatoList.add(p12);
		potatoList.add(p13);
		potatoList.add(p14);
		setColor();
		putOut();
		setLayer(0);
	}
 	private void setColor() {
 		for(int i=0;i<potatoList.size();i++){
 			for(int j=0;j<potatoList.get(i).faceList.size();j++){
 				potatoList.get(i).faceList.get(j).setColor(MyColors.grey);
 			}
 		}
 		for(int i=0;i<potatoList.size();i++){
 			for(int j=0;j<potatoList.get(i).faceList.size();j++){
 				if(Caculator.isInTheSamePlane(potatoList.get(i).faceList.get(j), potatoList.get(9).faceList.get(3))){
 					potatoList.get(i).faceList.get(j).setColor(MyColors.white);
 					potatoList.get(i).faceList.get(j).faceIndex=0;
 				}else if(Caculator.isInTheSamePlane(potatoList.get(i).faceList.get(j), potatoList.get(16).faceList.get(3))){
 					potatoList.get(i).faceList.get(j).setColor(MyColors.red);
 					potatoList.get(i).faceList.get(j).faceIndex=1;
 				}else if(Caculator.isInTheSamePlane(potatoList.get(i).faceList.get(j), potatoList.get(3).faceList.get(3))){
 					potatoList.get(i).faceList.get(j).setColor(MyColors.blue);
 					potatoList.get(i).faceList.get(j).faceIndex=2;
 				}else if(Caculator.isInTheSamePlane(potatoList.get(i).faceList.get(j), potatoList.get(10).faceList.get(3))){
 					potatoList.get(i).faceList.get(j).setColor(MyColors.yellow);
 					potatoList.get(i).faceList.get(j).faceIndex=3;
 				}else if(Caculator.isInTheSamePlane(potatoList.get(i).faceList.get(j), potatoList.get(13).faceList.get(3))){
 					potatoList.get(i).faceList.get(j).setColor(MyColors.green);
 					potatoList.get(i).faceList.get(j).faceIndex=4;
 				}else if(Caculator.isInTheSamePlane(potatoList.get(i).faceList.get(j), potatoList.get(20).faceList.get(3))){
 					potatoList.get(i).faceList.get(j).setColor(MyColors.purple);
 					potatoList.get(i).faceList.get(j).faceIndex=5;
 				}else if(Caculator.isInTheSamePlane(potatoList.get(i).faceList.get(j), potatoList.get(31).faceList.get(3))){
 					potatoList.get(i).faceList.get(j).setColor(MyColors.darkred);
 					potatoList.get(i).faceList.get(j).faceIndex=6;
 				}else if(Caculator.isInTheSamePlane(potatoList.get(i).faceList.get(j), potatoList.get(14).faceList.get(3))){
 					potatoList.get(i).faceList.get(j).setColor(MyColors.seablue);
 					potatoList.get(i).faceList.get(j).faceIndex=7;}
 			}//front/up9/3 front/down13/3 left/down28/3 left/up24/3 back/up3/3 right/down20/3 right/up27/3  back/down31/3
 		}
	}
 	public void putOut(){//用于确定每一层都有那些Shape
 		//一共12层
 		Face f=new Face(new Vertex[]{//上尖
 				new Vertex(new float[]{ 3,0,1						}),
 				new Vertex(new float[]{ 2,0,2						}),
 				new Vertex(new float[]{ 1,0,3						})});
 		Vertex v,v1,v2;
 		v1=new Vertex(new float[]{0,1,0});
 		v2=new Vertex(new float[]{0,-1,0});
 		v=Caculator.getCrossPoint(v1, v2, f);
 		System.out.println(v.toString());
 		System.out.println(Caculator.isInTheSameSide(v1, v2, f));
 		
// 		v1=potatoList.get(0).origin;
// 		int j=0;
// 		for(int i=0;i<potatoList.size();i++){
// 			v2=potatoList.get(i).origin;
// 			if(Caculator.isInTheSameSide(v1, v2, f)) {
// 				System.out.println(i);
// 				j++;
// 			}
// 		}
// 		System.out.println("j="+j);
// 		
// 		f=new Face(new Vertex[]{//上底
// 				new Vertex(new float[]{ 0,0,0						}),
// 				new Vertex(new float[]{	1,0,0					}),
// 				new Vertex(new float[]{		0,1,0				})});
// 		for(int i=0;i<potatoList.size();i++){
// 			if(potatoList.get(i).origin.xyz[0]<0) System.out.println("layer(2)="+i);
// 		}
// 		f=new Face(new Vertex[]{
// 				new Vertex(new float[]{ 						}),
// 				new Vertex(new float[]{						}),
// 				new Vertex(new float[]{						})});
// 		for(int i=0;i<potatoList.size();i++){
// 			if(potatoList.get(i).origin.xyz[0]>0) System.out.println("layer(3)="+i);
// 		}
// 		f=new Face(new Vertex[]{
// 				new Vertex(new float[]{ 						}),
// 				new Vertex(new float[]{						}),
// 				new Vertex(new float[]{						})});
// 		for(int i=0;i<potatoList.size();i++){
// 			if(potatoList.get(i).origin.xyz[2]<0) System.out.println("layer(4)="+i);
// 		}
// 		f=new Face(new Vertex[]{
// 				new Vertex(new float[]{ 						}),
// 				new Vertex(new float[]{						}),
// 				new Vertex(new float[]{						})});
// 		for(int i=0;i<potatoList.size();i++){
// 			if(potatoList.get(i).origin.xyz[2]>0) System.out.println("layer(5)="+i);
// 		}
// 		f=new Face(new Vertex[]{
// 				new Vertex(new float[]{ 						}),
// 				new Vertex(new float[]{						}),
// 				new Vertex(new float[]{						})});
// 		for(int i=0;i<potatoList.size();i++){
// 			float s=(potatoList.get(i).origin.xyz[0]+1.018f)*(potatoList.get(i).origin.xyz[0]+1.018f)+
// 		 			(potatoList.get(i).origin.xyz[1]-1.018f)*(potatoList.get(i).origin.xyz[1]-1.018f)+
// 		 			(potatoList.get(i).origin.xyz[2]-1.018f)*(potatoList.get(i).origin.xyz[2]-1.018f);
// 	 			if(Math.sqrt(s)<1.0241f)
// 			System.out.println("layer(6)="+i);
// 		}
// 		f=new Face(new Vertex[]{
// 				new Vertex(new float[]{ 						}),
// 				new Vertex(new float[]{						}),
// 				new Vertex(new float[]{						})});
// 		for(int i=0;i<potatoList.size();i++){
// 			float s=(potatoList.get(i).origin.xyz[0]-1.018f)*(potatoList.get(i).origin.xyz[0]-1.018f)+
// 		 			(potatoList.get(i).origin.xyz[1]-1.018f)*(potatoList.get(i).origin.xyz[1]-1.018f)+
// 		 			(potatoList.get(i).origin.xyz[2]-1.018f)*(potatoList.get(i).origin.xyz[2]-1.018f);
// 	 			if(Math.sqrt(s)<1.0241f)
// 			System.out.println("layer(7)="+i);
// 		}
// 		f=new Face(new Vertex[]{
// 				new Vertex(new float[]{ 						}),
// 				new Vertex(new float[]{						}),
// 				new Vertex(new float[]{						})});
// 		for(int i=0;i<potatoList.size();i++){
// 			float s=(potatoList.get(i).origin.xyz[0]-1.018f)*(potatoList.get(i).origin.xyz[0]-1.018f)+
// 		 			(potatoList.get(i).origin.xyz[1]-1.018f)*(potatoList.get(i).origin.xyz[1]-1.018f)+
// 		 			(potatoList.get(i).origin.xyz[2]+1.018f)*(potatoList.get(i).origin.xyz[2]+1.018f);
// 	 			if(Math.sqrt(s)<1.0241f)
// 			System.out.println("layer(8)="+i);
// 		}
// 		f=new Face(new Vertex[]{
// 				new Vertex(new float[]{ 						}),
// 				new Vertex(new float[]{						}),
// 				new Vertex(new float[]{						})});
// 		for(int i=0;i<potatoList.size();i++){
// 			float s=(potatoList.get(i).origin.xyz[0]+1.018f)*(potatoList.get(i).origin.xyz[0]+1.018f)+
// 		 			(potatoList.get(i).origin.xyz[1]-1.018f)*(potatoList.get(i).origin.xyz[1]-1.018f)+
// 		 			(potatoList.get(i).origin.xyz[2]+1.018f)*(potatoList.get(i).origin.xyz[2]+1.018f);
// 	 			if(Math.sqrt(s)<1.0241f)
// 			System.out.println("layer(9)="+i);
// 		}
// 		f=new Face(new Vertex[]{
// 				new Vertex(new float[]{ 						}),
// 				new Vertex(new float[]{						}),
// 				new Vertex(new float[]{						})});
// 		for(int i=0;i<potatoList.size();i++){
// 			float s=(potatoList.get(i).origin.xyz[0]+1.018f)*(potatoList.get(i).origin.xyz[0]+1.018f)+
// 		 			(potatoList.get(i).origin.xyz[1]+1.018f)*(potatoList.get(i).origin.xyz[1]+1.018f)+
// 		 			(potatoList.get(i).origin.xyz[2]-1.018f)*(potatoList.get(i).origin.xyz[2]-1.018f);
// 	 			if(Math.sqrt(s)<1.0241f)
// 			System.out.println("layer(10)="+i);
// 		}
// 		f=new Face(new Vertex[]{
// 				new Vertex(new float[]{ 						}),
// 				new Vertex(new float[]{						}),
// 				new Vertex(new float[]{						})});
// 		for(int i=0;i<potatoList.size();i++){
// 			float s=(potatoList.get(i).origin.xyz[0]-1.018f)*(potatoList.get(i).origin.xyz[0]-1.018f)+
// 		 			(potatoList.get(i).origin.xyz[1]+1.018f)*(potatoList.get(i).origin.xyz[1]+1.018f)+
// 		 			(potatoList.get(i).origin.xyz[2]-1.018f)*(potatoList.get(i).origin.xyz[2]-1.018f);
// 	 			if(Math.sqrt(s)<1.0241f)
// 			System.out.println("layer(11)="+i);
// 		}
// 		f=new Face(new Vertex[]{
// 				new Vertex(new float[]{ 						}),
// 				new Vertex(new float[]{						}),
// 				new Vertex(new float[]{						})});
// 		for(int i=0;i<potatoList.size();i++){
// 			float s=(potatoList.get(i).origin.xyz[0]-1.018f)*(potatoList.get(i).origin.xyz[0]-1.018f)+
// 		 			(potatoList.get(i).origin.xyz[1]+1.018f)*(potatoList.get(i).origin.xyz[1]+1.018f)+
// 		 			(potatoList.get(i).origin.xyz[2]+1.018f)*(potatoList.get(i).origin.xyz[2]+1.018f);
// 	 			if(Math.sqrt(s)<1.0241f)
// 			System.out.println("layer(12)="+i);
// 		}
// 		f=new Face(new Vertex[]{
// 				new Vertex(new float[]{ 						}),
// 				new Vertex(new float[]{						}),
// 				new Vertex(new float[]{						})});
// 		for(int i=0;i<potatoList.size();i++){
// 			float s=(potatoList.get(i).origin.xyz[0]+1.018f)*(potatoList.get(i).origin.xyz[0]+1.018f)+
//	 			(potatoList.get(i).origin.xyz[1]+1.018f)*(potatoList.get(i).origin.xyz[1]+1.018f)+
//	 			(potatoList.get(i).origin.xyz[2]+1.018f)*(potatoList.get(i).origin.xyz[2]+1.018f);
// 			if(Math.sqrt(s)<1.0241f)
// 			System.out.println("layer(13)="+i);
// 		}
// 		f=new Face(new Vertex[]{
// 				new Vertex(new float[]{ 						}),
// 				new Vertex(new float[]{						}),
// 				new Vertex(new float[]{						})});
// 		time=System.currentTimeMillis()-time;
// 		System.out.println("time="+time);
 	}
	public void setLayer(int layerIndex){//横01，竖竖23，左上4，右上5，左下6，右下7，后面依次对应
		this.layerIndex=layerIndex;
		layer.clear();
		layerOther.clear();
		int []index=new int[]{};
		switch(layerIndex){
		case 0:
			index=new int[]{0};
			break;
		case 1:
			index=new int[]{0,1,2,3,4,5,6,7,16,17,18,19,20,21,22,23};
			break;
		case 2:
			index=new int[]{4,5,6,7,12,13,14,15,20,21,22,23,28,29,30,31};
			break;
		case 3:
			index=new int[]{0,1,2,3,8,9,10,11,16,17,18,19,24,25,26,27};
			break;
		case 4:
			index=new int[]{16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
			break;
		case 5:
			index=new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
			break;
		case 6:
			index=new int[]{7,8,12,13,15,28};
			break;
		case 7:
			index=new int[]{3,8,9,11,15,27};
			break;
		case 8:
			index=new int[]{9,19,25,26,27,31};
			break;
		case 9:
			index=new int[]{13,21,25,28,29,31};
			break;
		case 10:
			index=new int[]{0,4,5,7,12,20};
			break;
		case 11:
			index=new int[]{0,2,3,4,11,18};
			break;
		case 12:
			index=new int[]{2,17,18,19,22,26};
			break;
		case 13:
			index=new int[]{5,17,20,21,22,29};
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
		Cucumbers c=new Cucumbers(unit);
		for(int i=0;i<potatoList.size();i++){
			for(int j=0;j<potatoList.size();j++){
					if(c.potatoList.get(i).overlap(potatoList.get(j))){
						c.potatoList.get(i).extractColor(potatoList.get(j));
						break;
					}
			}
		}
		potatoList=c.potatoList;
		setLayer(layerIndex);
	}
	public void setLayer(int faceIndex,int shapeIndex,int directionIndex){
		int i=shapeIndex;
		switch(faceIndex){
		case 0:
			switch(directionIndex){
			case 0:
				if(i==20|i==21|i==28|i==29)
					setLayer(4);
				else setLayer(5);
				break;
			case 1:
				if(i==5|i==7|i==20|i==21)
					setLayer(1);
				else
					setLayer(0);
				break;
			case 2:
				if(i==5|i==7|i==12|i==20)
					setLayer(10);
				else	setLayer(9);
				break;
			case 3:
				if(i==7|i==12|i==13|i==28)
					setLayer(6);
				else	setLayer(13);
				break;
			}
			break;
		case 1:
			switch(directionIndex){
			case 0:
				if(i==2|i==3|i==9|i==11)
					setLayer(5);
				else	setLayer(4);
				break;
			case 1:
				if(i==2|i==3|i==18|i==19)
					setLayer(1);
				else
					setLayer(0);
				break;
			case 2:
				if(i==2|i==18|i==19|i==26)
					setLayer(12);
				else setLayer(7);
				break;
			case 3:
				if(i==2|i==3|i==11|i==18)
					setLayer(11);
				else setLayer(8);
				break;
			}
			break;
		case 2:
			switch(directionIndex){
			case 0:
				if(i==4|i==7|i==12|i==15)
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
				if(i==7|i==8|i==12|i==15)
					setLayer(6);
				else setLayer(11);
				break;
			case 3:
				if(i==0|i==4|i==7|i==12)
					setLayer(10);
				else setLayer(7);
				break;
			}
			break;
		case 3:
			switch(directionIndex){
			case 0:
				if(i==17|i==19|i==25|i==26)
					setLayer(3);
				else	setLayer(2);
				break;
			case 1:
				if(i==25|i==26|i==29|i==31)
					setLayer(0);
				else
					setLayer(1);
				break;
			case 2:
				if(i==19|i==25|i==26|i==31)
					setLayer(8);
				else setLayer(13);
				break;
			case 3:
				if(i==21|i==25|i==29|i==31)
					setLayer(9);
				else setLayer(12);
				break;
			}
			break;
		case 4:
			switch(directionIndex){
			case 0:
				if(i==13|i==15|i==31|i==28)
					setLayer(2);
				else	setLayer(3);
				break;
			case 1:
				if(i==8|i==9|i==13|i==15)
					setLayer(5);
				else
					setLayer(4);
				break;
			case 2:
				if(i==8|i==9|i==15|i==27)
					setLayer(7);
				else setLayer(9);
				break;
			case 3:
				if(i==8|i==13|i==15|i==28)
					setLayer(6);
				else setLayer(8);
				break;
			}
			break;
		case 5:
			switch(directionIndex){
			case 0:
				if(i==4|i==5|i==20|i==22)
					setLayer(2);
				else	setLayer(3);
				break;
			case 1:
				if(i==0|i==2|i==4|i==5)
					setLayer(5);
				else
					setLayer(4);
				break;
			case 2:
				if(i==0|i==4|i==5|i==20)
					setLayer(10);
				else setLayer(12);
				break;
			case 3:
				if(i==0|i==2|i==4|i==18)
					setLayer(11);
				else setLayer(13);
				break;
			}
			break;
		}
	}
}

