package element;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

import AppUtils.Matrix;
import AppUtils.Caculator;


public abstract class Shape implements Cloneable{
	public float rgba[];//设置整个shape的颜色
	public Vertex origin;
	public ArrayList <Face> faceList;
	
	public float x,y,z;//用于坐标系的平移变换
	public  float rx,ry,rz;//用于坐标系的旋转变换
	
	private ArrayList <Vertex>verticesList;
	private float vertices[];
	private FloatBuffer   vertexBuffer;
	private float colors[];
	private FloatBuffer   colorBuffer;
	private short indices[];
	private ShortBuffer indiceBuffer;
	
	public Shape(){
		faceList=new ArrayList<Face>();
		origin=new Vertex(new float[]{0,0,0});
	}
	public Shape(Shape s){
		this();
		for(int i=0;i<s.faceList.size();i++){
			faceList.add(new Face(s.faceList.get(i)));
		}
		this.origin=s.origin.clone();
	}
	
	public void setTexture(int id){
		for(int i=0;i<faceList.size();i++){
			faceList.get(i).setTextureId(id);
		}
	}
	
	final public void setColor(float []rgba){
		for(int i=0;i<faceList.size();i++){
			faceList.get(i).setColor(rgba);
		}
	}
	public void move(float []xyz){
		for(int i=0;i<faceList.size();i++){
			faceList.get(i).move(xyz);
		}
		origin.move(xyz);
	}
	final public boolean overlap(Shape s){
		double d=	Math.abs(origin.xyz[0]-s.origin.xyz[0])+
							Math.abs(origin.xyz[1]-s.origin.xyz[1])+
							Math.abs(origin.xyz[2]-s.origin.xyz[2]);
		if(d<0.001)	return true;
		return false;
	}
	final public void extractColor(Shape s){
		for(int i=0;i<faceList.size();i++){
			for(int j=0;j<faceList.size();j++){
				if(faceList.get(i).overlap(s.faceList.get(j))){
					faceList.get(i).setColor(s.faceList.get(j).getColor());
					break;
				}
			}
		}
	}
	final public void extractTexture(Shape s){
		for(int i=0;i<faceList.size();i++){
			for(int j=0;j<faceList.size();j++){
				if(faceList.get(i).overlap(s.faceList.get(j))){
					faceList.get(i).setTextureId(s.faceList.get(j).textureId);
					break;
				}
			}
		}
	}
	
	public void rotate(float x,float y,float z,float angle){
		Matrix.rotate(this, new double[]{x,y,z}, angle);
	}
	final public boolean isGlobeShot(float x,float y,float near,int width,int height,float r){
		return Caculator.isGlobeShot(x, y, near, width, height, origin, r);
	}
	final public boolean isShot(float x,float y,int width,int height){
		for(int i=0;i<faceList.size();i++){
			if(Caculator.isFaceShot(x, y, faceList.get(i), width, height)) 
				return true;
		}
		return false;
	}
	final public boolean isShot(float x,float y,float near,int width,int height){
		for(int i=0;i<faceList.size();i++){
			if(Caculator.isInTrangleArea(x, y, faceList.get(i), near, width, height))	
				return true;
		}
		return false;
	}
	final public int getFace(float x,float y,int width,int height){
		ArrayList <Face> temp=new ArrayList<Face>();
		for(int i=0;i<faceList.size();i++){
			if(Caculator.isFaceShot(x, y, faceList.get(i), width, height)){
				temp.add(faceList.get(i));
			}
		}
		while(temp.size()>1){
			float z0=Caculator.getCrossPointZ(x, y, temp.get(0), width, height);
			float z1=Caculator.getCrossPointZ(x, y, temp.get(1), width, height);
			if(	z0>z1 )	temp.remove(1);
			else	temp.remove(0);
		}
		if(temp.size()==1){
			for(int i=0;i<faceList.size();i++)
				if(faceList.get(i).equals(temp.get(0)))	{
					return i;
				}
		}
		return -1;
	}
	
	public int getFace(float x,float y,float near,int width,int height){
		ArrayList <Face> temp=new ArrayList<Face>();
		for(int i=0;i<faceList.size();i++){
			if( Caculator.isInTrangleArea(x, y, faceList.get(i), near, width, height) ){
				temp.add(faceList.get(i));
			}
		}
		while(temp.size()>1){
			float z1=Caculator.getCrossPointZ(x, y, temp.get(0), near, width, height);
			float z2=Caculator.getCrossPointZ(x, y, temp.get(1), near, width, height);
			if(	z1>z2 )	temp.remove(1);
			else	temp.remove(0);
		}
		if(temp.size()==1){
			for(int i=0;i<faceList.size();i++)
				if(faceList.get(i).equals(temp.get(0)))	{
					return i;
				}
		}
		return -1;
	}
	
	public abstract void initTextureBuffer();
	
	final public void initVertexBuffer(){
		verticesList = new ArrayList<Vertex>();
    	for(int i=0;i<faceList.size();i++){
    		for(int j=0;j<3;j++){
    			verticesList.add(faceList.get(i).xyz[j]);
    		}
    	}
    	vertices = new float[verticesList.size()*3];
    	for(int i=0;i<vertices.length;){
    		for(int j=0;j<3;j++){
    			vertices[i] = verticesList.get(i/3).xyz[j];
    			i++;
    		}
    	}
    	ByteBuffer bbv = ByteBuffer.allocateDirect(vertices.length*4);
	    bbv.order(ByteOrder.nativeOrder());
	    vertexBuffer = bbv.asFloatBuffer();
	    vertexBuffer.put(vertices);
	    vertexBuffer.position(0);
	    indices = new short[verticesList.size()];
    	for(short i=0;i<indices.length;i++){
    		indices[i]=i;
    	}
	    ByteBuffer bbi = ByteBuffer.allocateDirect(indices.length*2);
	    bbi.order(ByteOrder.nativeOrder());
	    indiceBuffer = bbi.asShortBuffer();
	    indiceBuffer.put(indices);
		indiceBuffer.position(0);
	}
	final public void initColorBuffer(){
		colors = new float[verticesList.size()*4];
    	for(int i=0;i<colors.length;){
    		for(int j=0;j<4;j++){
    			colors[i] = verticesList.get(i/4).rgba[j];
    			i++;
    		}
    	}
    	ByteBuffer bbc = ByteBuffer.allocateDirect(colors.length*4);
	    bbc.order(ByteOrder.nativeOrder());
		colorBuffer = bbc.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
	}
	public void drawT(GL10 gl){
		for(int i=0;i<faceList.size();i++){
			faceList.get(i).draw(gl, Config.DRAW_WITH_TEXTURE);
		}
	}
	public void drawC(GL10 gl){
    	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    	gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
   	 	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
   	 	gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
   	 	gl.glTranslatef(x, y, z);
   	 	gl.glRotatef(rx, 1, 0, 0);
   	 	gl.glRotatef(ry, 0, 1, 0);
   	 	gl.glRotatef(rz, 0, 0, 1);
   	 	gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,GL10.GL_UNSIGNED_SHORT, indiceBuffer);
   	    gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
	public void rotateX(float angle){
		Matrix.rotateX(this, angle);
	}
	public void rotateY(float angle){
		Matrix.rotateY(this, angle);
	}
	public void rotateZ(float angle){
		Matrix.rotateZ(this, angle);
	}
	public void copy(Shape s){
		for(int i=0;i<faceList.size();i++){
			faceList.get(i).copy(s.faceList.get(i));
		}
		this.origin.copy(s.origin);
//		System.arraycopy(s.rgba, 0, this.rgba, 0, 4);

//		if(this.vertices==null){
//			this.vertices=new float[s.vertices.length];
//			this.indices=new short[s.indices.length];
//			this.colors=new float[s.colors.length];
//		}
//		System.arraycopy(s.colors, 0, this.colors, 0, s.colors.length);
//		System.arraycopy(s.vertices, 0, this.vertices, 0, s.vertices.length);
//		System.arraycopy(s.indices, 0, this.indices, 0, s.indices.length);
	}
}
