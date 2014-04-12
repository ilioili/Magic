package pyramid;

import java.util.ArrayList;

import element.Patch;
import element.Pyramid;
import element.Shape;

import AppUtils.Matrix;
import Resource.MyColors;


public class Pyramids3 extends Shape{
	public ArrayList <Shape>	pyramidList;
	public ArrayList<Shape> layer;
	public ArrayList<Shape> layerOther;
	public int layerIndex;
	private float unit;
	public Pyramids3(float unit){
		this.unit=unit;
		pyramidList=new ArrayList<Shape>();
		layer=new ArrayList<Shape>();
		layerOther=new ArrayList<Shape>();
		float dx=(float)unit/2;
		float dy=(float)Math.sqrt(6)*unit/3;
		float dz=(float)Math.sqrt(3)*unit/2;
		float interval=1.03f;
		for(int i=0;i<3;i++){//3²ã
			for(int j=0;j<(i+1);j++){
				for(int k=0;k<(2*j+1);k++){
						if(k%2==0){
							Pyramid p=new Pyramid(unit);
							p.move(new float[]{interval*dx*(k-j),interval*dy*(-i)+1.5f*interval*dy,interval*dz*j-interval*i*dz*2/3});
							p.setColor(MyColors.grey);
							if(j==i)		p.faceList.get(0).setColor(MyColors.green);
							if(k==0)	p.faceList.get(1).setColor(MyColors.blue);
							if(k==2*j)	p.faceList.get(2).setColor(MyColors.white);
							if(i==2)		p.faceList.get(3).setColor(MyColors.red);
							pyramidList.add(p);
						}else{
							Patch p=new Patch(unit);
							p.move(new float[]{interval*dx*(k-j),interval*dy*(0.25f-i)+1.5f*interval*dy,interval*dz*(j-1)-interval*(i-1)*dz*2/3});
							p.setColor(MyColors.grey);
							if(j==i)			p.faceList.get(0).setColor(MyColors.green);
							if(k==1)			p.faceList.get(1).setColor(MyColors.blue);
							if(k==2*j-1)		p.faceList.get(2).setColor(MyColors.white);
							if(i==2) 			p.faceList.get(3).setColor(MyColors.red);
							pyramidList.add(p);
						}
				}
			}
		}
		setLayer(1);
	}
	public void setLayer(int layerIndex){
//		System.out.println("Pyramids setLayer(int layerIndex)");
		this.layerIndex=layerIndex;
		layer.clear();
		layerOther.clear();
		int []index=new int[]{};
		switch(layerIndex){
		case 0:
			index=new int[]{0};
			break;
		case 1:
			index=new int[]{1,2,3,4};
			break;
		case 2:
			index=new int[]{5,6,7,8,9,10,11,12,13};
			break;
		case 3:
			index=new int[]{9};
			break;
		case 4:
			index=new int[]{2,6,10,11};
			break;
		case 5:
			index=new int[]{0,1,3,4,5,7,8,12,13};
			break;
		case 6:
			index=new int[]{5};
			break;
		case 7:
			index=new int[]{1,6,7,8};
			break;
		case 8:
			index=new int[]{0,2,3,4,9,10,11,12,13};
			break;
		case 9:
			index=new int[]{13};
			break;
		case 10:
			index=new int[]{4,8,11,12};
			break;
		case 11:
			index=new int[]{0,1,2,3,5,6,7,9,10};
			break;
		}
		for(int i=0;i<index.length;i++){
			layer.add(pyramidList.get(index[i]));
		}
		for(int i=0;i<pyramidList.size();i++){
			if(!layer.contains(pyramidList.get(i)))	layerOther.add(pyramidList.get(i));
		}
	}
	public void setLayer(int shapeIndex,int direction){
//		System.out.println("Pyramids setLayer(int layerIndex, int direction)");
		switch(direction){
		case 0:
			if(shapeIndex==0)			
				setLayer(0);
			else if(shapeIndex<5)		
				setLayer(1);
			else						
				setLayer(2);
			break;
		case 1:
			if(shapeIndex==9)																
				setLayer(3);
			else if(shapeIndex==2|shapeIndex==6|shapeIndex==10|shapeIndex==11)	
				setLayer(4);
			else																			
				setLayer(5);
			break;
		case 2:
			if(shapeIndex==5)																
				setLayer(6);
			else if(shapeIndex==1|shapeIndex==6|shapeIndex==7|shapeIndex==8)		
				setLayer(7);
			else																			
				setLayer(8);
			break;
		case 3:
			if(shapeIndex==13)															
				setLayer(9);
			else if(shapeIndex==4|shapeIndex==8|shapeIndex==11|shapeIndex==12)	
				setLayer(10);
			else																			
				setLayer(11);
			break;
		}
	}
	public void layerRotate(float degree){
//		System.out.println("Pyramids layerRotate(float degree)");
		double s6=Math.sqrt(6);
		double s3=Math.sqrt(3);
		switch(layerIndex){
		case 0:
		case 1:
		case 2:
			for(int i=0;i<layer.size();i++){
				Matrix.rotate(layer.get(i),new double[]{0,1,0}, -degree);
			}
			break;
		case 3:
		case 4:
		case 5:
			for(int i=0;i<layer.size();i++){
				Matrix.rotate(layer.get(i),new double[]{-6,-s6,2*s3},-degree);
			}
			break;
		case 6:
		case 7:
		case 8:
			for(int i=0;i<layer.size();i++){
				Matrix.rotate(layer.get(i),new double[]{0,-s6,-4*s3},-degree);
			}
			break;
		case 9:
		case 10:
		case 11:
			for(int i=0;i<layer.size();i++){
				Matrix.rotate(layer.get(i),new double[]{6,-s6,2*s3},-degree);
			}
			break;
		}
	}
	public void move(float x,float y,float z){
		for(int i=0;i<pyramidList.size();i++){
			pyramidList.get(i).move(new float[]{x,y,z});
		}
	}
	public void rotateX(float angle){
		for(int i=0;i<pyramidList.size();i++){
			Matrix.rotateX(pyramidList.get(i),angle);
		}
	}
	public void rotateY(float angle){
		for(int i=0;i<pyramidList.size();i++){
			Matrix.rotateY(pyramidList.get(i),angle);
		}
	}
	public void fresh(){
//		System.out.println("Pyramids fresh()");
		Pyramids3 p=new Pyramids3(unit);
		for(int i=0;i<14;i++){
			for(int j=0;j<14;j++){
					if(p.pyramidList.get(i).overlap(pyramidList.get(j))){
						p.pyramidList.get(i).extractColor(pyramidList.get(j));
						break;
					}
			}
		}
		pyramidList=p.pyramidList;
		setLayer(layerIndex);
	}
	@Override
	public void initTextureBuffer() {
		// TODO Auto-generated method stub
		
	}
}
