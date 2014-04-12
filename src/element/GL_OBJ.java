package element;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;


public class GL_OBJ {
	private ArrayList<Shape> shapeList;
	public ArrayList<Face> faceList;
	private ArrayList <Vertex>verticesList;
	private float vertices[];
	private FloatBuffer   vertexBuffer;
	private float colors[];
	private FloatBuffer   colorBuffer;
	private short indices[];
	private ShortBuffer indiceBuffer;
	
	float x,y,z;//平移参数
	public float rx,ry,rz;//旋转参数
	public float angle;//旋转角度
	public float xyz[];//旋转轴向量
    public void clear(){
    	shapeList.clear();
    	verticesList.clear();
    	faceList.clear();
    }
    public GL_OBJ(){
    	shapeList = new ArrayList<Shape>();
    	verticesList = new ArrayList<Vertex>();
    	faceList = new ArrayList<Face>();
    	xyz=new float[3];
    }
    public void addShape(Shape shape){
    	shapeList.add(shape);
    }
    public void generateT(){
    	faceList.clear();
    	for(int i=0;i<shapeList.size();i++){
    		shapeList.get(i).initTextureBuffer();
    		Iterator<Face>it =shapeList.get(i).faceList.iterator();
    		while(it.hasNext()){
    			Face face=it.next();
    			faceList.add(face);
    		}
    	}
    }
    public void reGenerateT(){
    	faceList.clear();
    	for(int i=0;i<shapeList.size();i++){
    		Iterator<Face>it =shapeList.get(i).faceList.iterator();
    		while(it.hasNext()){
    			Face face=it.next();
    			faceList.add(face);
    		}
    	}
    }
    public void generateC(){
    	for(int i=0;i<shapeList.size();i++){
    		Iterator<Face>it =shapeList.get(i).faceList.iterator();
    		while(it.hasNext()){
    			Face face=it.next();
    			faceList.add(face);
    			verticesList.add(face.xyz[0]);
    			verticesList.add(face.xyz[1]);
    			verticesList.add(face.xyz[2]);
    		}
    	}
    	vertices = new float[verticesList.size()*3];
    	for(int i=0;i<vertices.length;){
    		for(int j=0;j<3;j++){
    			vertices[i] = verticesList.get(i/3).xyz[j];
    			i++;
    		}
    	}
    	colors = new float[verticesList.size()*4];
    	for(int i=0;i<colors.length;){
    		for(int j=0;j<4;j++){
    			colors[i] = verticesList.get(i/4).rgba[j];
    			i++;
    		}
    	}
    	indices = new short[verticesList.size()];
    	for(short i=0;i<indices.length;i++){
    		indices[i]=i;
    	}
    	
    	ByteBuffer bbc = ByteBuffer.allocateDirect(colors.length*4);
	    bbc.order(ByteOrder.nativeOrder());
		colorBuffer = bbc.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
		
	    ByteBuffer bbv = ByteBuffer.allocateDirect(vertices.length*4);
	    bbv.order(ByteOrder.nativeOrder());
	    vertexBuffer = bbv.asFloatBuffer();
	    vertexBuffer.put(vertices);
	    vertexBuffer.position(0);
	    
	    ByteBuffer bbi = ByteBuffer.allocateDirect(indices.length*2);
	    bbi.order(ByteOrder.nativeOrder());
	    indiceBuffer = bbi.asShortBuffer();
	    indiceBuffer.put(indices);
		indiceBuffer.position(0);
    }
    public void drawT(GL10 gl){
    	try{
    		gl.glTranslatef(x, y, z);
       	 	gl.glRotatef(rx, 1, 0, 0);
       	 	gl.glRotatef(ry, 0, 1, 0);
       	 	gl.glRotatef(rz, 0, 0, 1);
       	 	gl.glRotatef(angle, xyz[0], xyz[1], xyz[2]);
        	for(int i=0;i<faceList.size();i++){
        		faceList.get(i).draw(gl, Config.DRAW_WITH_TEXTURE);
        	}
    	}catch(IndexOutOfBoundsException e){
    		e.printStackTrace();
    	}
    	
    }
    public void drawC(GL10 gl){
//    	gl.glFrontFace(GL10.GL_CW);
//    	gl.glFrontFace(GL10.GL_CCW);
//    	gl.glEnable(GL10.GL_CULL_FACE);
//    	gl.glCullFace(GL10.GL_BACK);
//    	gl.glCullFace(GL10.GL_FRONT);
//    	gl.glCullFace(GL10.GL_FRONT_AND_BACK);
    	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    	gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
   	 	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
   	 	gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
   	 	gl.glTranslatef(x, y, z);
   	 	gl.glRotatef(rx, 1, 0, 0);
   	 	gl.glRotatef(ry, 0, 1, 0);
   	 	gl.glRotatef(rz, 0, 0, 1);
   	 	gl.glRotatef(angle, xyz[0], xyz[1], xyz[2]);
   	 	gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,GL10.GL_UNSIGNED_SHORT, indiceBuffer);
//   	 	gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
//        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
     	
    }

}
