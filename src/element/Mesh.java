package element;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/*
 	(-1,1)v2   ****************	(1,1)v3
 				  *	  f1		  *	   *
 				  *		    *		   *
 				  *	   *	    f2    *
 	(-1,-1)v1 ****************	(1,-1)v4
 */
public class Mesh{
	public Face face1,face2;
	private static float[] vertices;
	private FloatBuffer   vertexBuffer;
	
	private float[] colors;
	private FloatBuffer   colorBuffer;
	
	private static short[] indices;
	private ShortBuffer indiceBuffer;
	
	private FloatBuffer textureBuffer;
	private float[] textureCoordinates;
	
	int textureId;
	int[] textures;
	
	public Vertex origin;//正方形的中心（默认(0,0,0)）；
	public float 	[]rgba;//正方形的颜色（默认黑色）；
	public int faceIndex=-1;
	
	public Mesh(float unit){
		Vertex v1=new Vertex(new float[]{-unit,-unit,0});
		Vertex v2=new Vertex(new float[]{-unit,unit,0});
		Vertex v3=new Vertex(new float[]{unit,unit,0});
		Vertex v4=new Vertex(new float[]{unit,-unit,0});
		this.face1=new Face(new Vertex[]{v1,v2,v3});
		this.face2=new Face(new Vertex[]{v1,v3,v4});
		this.rgba=new float[4];
		this.origin=new Vertex();
	}
	public Mesh(Face face1,Face face2){
		this.face1=face1;
		this.face2=face2;
		this.rgba=new float[4];
		this.origin=new Vertex();
	}
	public void draw(GL10 gl, int config){
		switch(config){
		case Config.DRAW_WITH_COLOR:
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
	   	 	gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
			gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,GL10.GL_UNSIGNED_SHORT, indiceBuffer);
			break;
		case Config.DRAW_WITH_TEXTURE:
			gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
		 	gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
	   	 	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
	   	 	gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,GL10.GL_UNSIGNED_SHORT, indiceBuffer);
	   	 	break;
		}
		
    }
	public void loadVertex(){
		vertices=new float[18];
		for(int i=0;i<9;){
    		for(int j=0;j<3;j++){
    			vertices[i] = face1.xyz[i/3].xyz[j];
    			vertices[i+9] = face2.xyz[i/3].xyz[j];
    			i++;
    		}
    	}
		ByteBuffer bbv = ByteBuffer.allocateDirect(72);
	    bbv.order(ByteOrder.nativeOrder());
	    vertexBuffer = bbv.asFloatBuffer();
	    vertexBuffer.put(vertices);
	    vertexBuffer.position(0);
	    
	    indices=new short[]{0,1,2,3,4,5};
	    ByteBuffer bbi = ByteBuffer.allocateDirect(12);
	    bbi.order(ByteOrder.nativeOrder());
	    indiceBuffer=bbi.asShortBuffer();
	    indiceBuffer.put(indices);
		indiceBuffer.position(0);
	}
	public void loadTexture(){
		textureCoordinates =new float[]{ 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f };
		ByteBuffer bbt = ByteBuffer.allocateDirect(48);
		bbt.order(ByteOrder.nativeOrder());
		textureBuffer= bbt.asFloatBuffer();
		textureBuffer.put(textureCoordinates);
		textureBuffer.position(0);
	}
	public void loadColor(){
		colors=new float[12];
		for(int i=0; i<3; i++){
			System.arraycopy(rgba, 0, colors, i*4, 4);
		}
		ByteBuffer bbc = ByteBuffer.allocateDirect(48);
	    bbc.order(ByteOrder.nativeOrder());
	    colorBuffer = bbc.asFloatBuffer();
	    colorBuffer.put(vertices[0]);
	    colorBuffer.position(0);
	}
	public void setColor(float[] rgba){
		System.arraycopy(rgba, 0, this.rgba, 0, 4);
		face1.setColor(rgba);
		face2.setColor(rgba);
	}
	public void move(float[] xyz){
		origin.move(xyz);
		face1.move(xyz);
		face2.move(xyz);
	}
	public void generate() {
		loadVertex();
		loadTexture();
		loadColor();
	}
	
	public void loadTexture(GL10 gl, int textureId){
		this.textureId=textureId;
	}
	
	
}
