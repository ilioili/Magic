package cubes;

import java.util.ArrayList;

import element.Shapes;
import AppUtils.Matrix;
import Resource.MyColors;


public class Cubes extends Shapes{
	//NxN魔方，层的划分，从左到右，从下到上，从后到前
	public int N;
	public Cube [][][]cubes;
	public int layerIndex;
	private float unit;
	public ArrayList<Cube> layer;
	public ArrayList<Cube> layerOther;
	public float interval=0f;
	
	public Cubes(float unit,int N){
		this(unit,N,0);
	}
	public Cubes(float unit, int N, float interval){
		this.unit = unit;
		this.N=N;
		this.interval=interval;
		this.layer=new ArrayList<Cube>();
		this.layerOther=new ArrayList<Cube>();
		this.cubes=new Cube[N][N][N];
		float temp=unit*interval;//间隙
		float temp1=2*unit+temp;//Cube单词位移距离
		float temp2=(N-1)*unit+(N-1)*unit*temp/2;//Cubes移动到中心位置的偏移量     //***********************
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				for(int k=0;k<N;k++){
					Cube c = new Cube(unit);
					c.move(new float[]{	i*temp1-temp2,	j*temp1-temp2,	k*temp1-temp2	});
					this.cubes[i][j][k] = c;
					this.faceList.addAll(this.cubes[i][j][k].faceList);
					this.shapeList.add(c);
				}
			}
		}
		setLayer(0);
		initFaceColor();
	}
	private void initFaceColor(){
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				for(int k=0;k<N;k++){
					cubes[i][j][k].setColor(MyColors.black);
				}
			}
		}
		for(int j=0;j<N;j++){//left
			for(int k=0;k<N;k++){
				cubes[0][j][k].setColor(Cube.left,MyColors.blue);
			}
		}
		for(int j=0;j<N;j++){//right
			for(int k=0;k<N;k++){
				cubes[N-1][j][k].setColor(Cube.right,MyColors.red);
			}
		}
		for(int i=0;i<N;i++){//bottom
			for(int k=0;k<N;k++){
				cubes[i][0][k].setColor(Cube.bottom,MyColors.green);
			}
		}
		for(int i=0;i<N;i++){//top
			for(int k=0;k<N;k++){
				cubes[i][N-1][k].setColor(Cube.top,MyColors.purple);
			}
		}
		for(int i=0;i<N;i++){//back
			for(int j=0;j<N;j++){
				cubes[i][j][0].setColor(Cube.back,MyColors.white);
			}
		}
		for(int i=0;i<N;i++){//front
			for(int j=0;j<N;j++){
				cubes[i][j][N-1].setColor(Cube.front,MyColors.yellow);
			}
		}
		
		
	}
	public void initFaceTexture(int [] texturesId){
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				for(int k=0;k<N;k++){
					cubes[i][j][k].setTexture(texturesId[6]);
				}
			}
		}
		for(int j=0;j<N;j++){//left
			for(int k=0;k<N;k++){
				cubes[0][j][k].setTexture(Cube.left, texturesId[Cube.left]);
			}
		}
		for(int j=0;j<N;j++){//right
			for(int k=0;k<N;k++){
				cubes[N-1][j][k].setTexture(Cube.right, texturesId[Cube.right]);
			}
		}
		for(int i=0;i<N;i++){//bottom
			for(int k=0;k<N;k++){
				cubes[i][0][k].setTexture(Cube.bottom, texturesId[Cube.bottom]);
			}
		}
		for(int i=0;i<N;i++){//top
			for(int k=0;k<N;k++){
				cubes[i][N-1][k].setTexture(Cube.top, texturesId[Cube.top]);
			}
		}
		for(int i=0;i<N;i++){//back
			for(int j=0;j<N;j++){
				cubes[i][j][0].setTexture(Cube.back, texturesId[Cube.back]);
			}
		}
		for(int i=0;i<N;i++){//front
			for(int j=0;j<N;j++){
				cubes[i][j][N-1].setTexture(Cube.front, texturesId[Cube.front]);
			}
		}
		
		
	}
	public void setInterval(float f){
		interval=f;
	}
	public void setLayer(int index){
		layerIndex = index;
		layer.clear();
		layerOther.clear();
		if(layerIndex<N){//从左到右
			for(int j=0;j<N;j++){
				for(int k=0;k<N;k++){
					layer.add(cubes[index][j][k]);
				}
			}
		}else if(layerIndex<N*2){//从下到上
			for(int i=0;i<N;i++){
				for(int k=0;k<N;k++){
					layer.add(cubes[i][index-N][k]);
				}
			}
		}else{
			for(int i=0;i<N;i++){//从后到前
				for(int j=0;j<N;j++){
					layer.add(cubes[i][j][index-2*N]);
				}
			}
		}
		for(int i=0;i<shapeList.size();i++){
			if(!layer.contains(shapeList.get(i)))	layerOther.add((Cube)shapeList.get(i));
		}
	}
	
	public void rotateLayer(float degree){
		if(layerIndex<N){
			for(int i=0;i<layer.size();i++)	{
				Matrix.rotateX(layer.get(i), degree);
			}
		}else if(layerIndex<2*N){
			for(int i=0;i<layer.size();i++)	{
				Matrix.rotateY(layer.get(i), degree);
			}
		}else{
			for(int i=0;i<layer.size();i++)	{
				Matrix.rotateZ(layer.get(i), degree);
			}
		}
	}
	public void setLayer(int i,int j,int k,int rotateDirection){
		int axisX=0;int axisY=1;int axisZ=2;
		if(rotateDirection==axisX){
			setLayer(i);
		}else if(rotateDirection==axisY){
			setLayer(j+N);
		}else if(rotateDirection==axisZ){
			setLayer(k+2*N);
		}
	}
	@Override
	public void initTextureBuffer() {
		for(int i=0;i<shapeList.size();i++){
			shapeList.get(i).initTextureBuffer();
		}
	}
	
}//end of the class

