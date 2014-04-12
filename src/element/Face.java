package element;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;


public class Face implements Cloneable{
	public Vertex 	[]xyz;
	public Vertex origin;
	public float 	[]rgba;
	public int faceIndex=-1;
	
	private static float[] vertices;
	private FloatBuffer   vertexBuffer;
	
	private float[] colors;
	private FloatBuffer   colorBuffer;
	
	private static short[] indices;
	private ShortBuffer indiceBuffer;
	
	int textureId;
	private FloatBuffer textureBuffer;
	public float[] textureCoordinates;
	
	public Face(){
		this.xyz=new Vertex[3];
		this.origin=new Vertex();
		this.rgba=new float[4];//默认靛蓝
		this.textureCoordinates=new float[6];
	}
	public Face(Vertex []xyz){
		this();
		setVertex(xyz);
	}
	public Face(Vertex xyz[],float rgba[]){
		this(xyz);
		this.setColor(rgba);
	}
	public Face(Face f){
		this(f.xyz,f.rgba);
		origin=f.origin.clone();
	}
	public boolean overlap(Face f){
		double d=	Math.abs(origin.xyz[0]-f.origin.xyz[0])+
							Math.abs(origin.xyz[1]-f.origin.xyz[1])+
							Math.abs(origin.xyz[2]-f.origin.xyz[2]);
		if(d<0.001)	return true;
		return false;
	}
	public void setVertex(Vertex xyz[]){
		this.xyz[0]=xyz[0].clone();
		this.xyz[1]=xyz[1].clone();
		this.xyz[2]=xyz[2].clone();
	}
	public void setColor(float rgba[]){
		System.arraycopy(rgba, 0, this.rgba, 0, 4);
		this.xyz[0].rgba=this.rgba;
		this.xyz[1].rgba=this.rgba;
		this.xyz[2].rgba=this.rgba;
	}
	public void setTextureCoords(float []coordinates){
		System.arraycopy(coordinates, 0, this.textureCoordinates, 0, 6);
	}
	public void setTextureId(int textureId){
		this.textureId=textureId;
	}
	public float[] getColor(){
		return new float[]{rgba[0],rgba[1],rgba[2],rgba[3]};
	}
	public int getTexture(){
		return textureId;
	}
	
	public void move(float xyz[]){
		this.xyz[0].move(xyz);
		this.xyz[1].move(xyz);
		this.xyz[2].move(xyz);
		this.origin.move(xyz);
	}
	public void setFaceIndex(int faceIndex){
		this.faceIndex=faceIndex;
	}

	@Override
	public String toString(){
		return "Face: " +
					"origin<xyz>("+origin.xyz[0]+","+origin.xyz[1]+","+origin.xyz[2]+")-" +
					"color<rgba>("+rgba[0]+","+rgba[1]+","+rgba[2]+","+rgba[3]+","+rgba[4]+")-"+
					"faceIndex="+faceIndex+" ;";
	}
	
	@Override
	public Face clone(){
		return new Face(this);
	}
	
	public void draw(GL10 gl, int config){
		try{
			gl.glEnable(GL10.GL_CULL_FACE);
			gl.glFrontFace(GL10.GL_CW);
			gl.glCullFace(GL10.GL_BACK);
			switch(config){
			case Config.DRAW_WITH_COLOR:
				gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		    	gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		   	 	gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
				gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,GL10.GL_UNSIGNED_SHORT, indiceBuffer);
				break;
			case Config.DRAW_WITH_TEXTURE:
				gl.glEnable(GL10.GL_TEXTURE_2D);
				gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
				gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_NEAREST);
			 	gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
				gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		   	 	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		   	 	gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,GL10.GL_UNSIGNED_SHORT, indiceBuffer);
		   	 	break;
			}
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
		
    }

	/*
	 * 加载顶点Buffer
	 */
	public void initVertexBuffer(){
		vertices=new float[9];
		for(int i=0;i<3;i++){
			System.arraycopy(xyz[i].xyz, 0, vertices, i*3, 3);
		}
		ByteBuffer bbv = ByteBuffer.allocateDirect(36);
	    bbv.order(ByteOrder.nativeOrder());
	    vertexBuffer = bbv.asFloatBuffer();
	    vertexBuffer.put(vertices);
	    vertexBuffer.position(0);
	    indices=new short[]{0,1,2};
	    ByteBuffer bbi = ByteBuffer.allocateDirect(6);
	    bbi.order(ByteOrder.nativeOrder());
	    indiceBuffer = bbi.asShortBuffer();
	    indiceBuffer.put(indices);
		indiceBuffer.position(0);
	}
	/*
	 * 加载颜色Buffer
	 */
	public void initColorBuffer(){
		colors=new float[12];
		for(int i=0;i<3;i++){
			System.arraycopy(xyz[i].rgba, 0, colors, i*4, 4);
		}
		ByteBuffer bbc= ByteBuffer.allocateDirect(48);
	    bbc.order(ByteOrder.nativeOrder());
	    colorBuffer = bbc.asFloatBuffer();
	    colorBuffer.put(colors);
	    colorBuffer.position(0);
	}
	/*
	 * 加载材质坐标
	 */
	public void initTextureBuffer(){
		ByteBuffer bbt = ByteBuffer.allocateDirect(24);
		bbt.order(ByteOrder.nativeOrder());
		textureBuffer = bbt.asFloatBuffer();
		textureBuffer.put(textureCoordinates);
		textureBuffer.position(0);
	}
	
	public void copy(Face f){
		this.origin.copy(f.origin);
		this.faceIndex=f.faceIndex;
		this.textureId=f.textureId;
		this.xyz[0].copy(f.xyz[0]);
		this.xyz[1].copy(f.xyz[1]);
		this.xyz[2].copy(f.xyz[2]);
		System.arraycopy(f.rgba, 0, this.rgba, 0, 4);
	}

}
