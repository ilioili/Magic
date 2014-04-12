package element;

import AppUtils.Matrix;

public class Vertex implements Cloneable{
	public float []xyz;
	public float []rgba;
	
	public Vertex(){
		this.xyz=new float[3];
		this.rgba=new float[]{0,1,1,1};
	}
	public Vertex(Vertex v){
		this();
		System.arraycopy(v.xyz, 0, this.xyz, 0, 3);
		System.arraycopy(v.rgba, 0, this.rgba, 0, 4);
	}
	public Vertex(float xyz[]){
		this();
		setPosition(xyz);
	}
	public Vertex(float xyz[],float rgba[]){
		this(xyz);
		this.setColor(rgba);
	}
	public void copy(Vertex v){
		System.arraycopy(v.xyz, 0, this.xyz, 0, 3);
		System.arraycopy(v.rgba, 0, this.rgba, 0, 4);
	}
	public void setPosition(float xyz[]){
		System.arraycopy(xyz, 0, this.xyz, 0, 3);
	}
	public void setColor(float rgba[]){
		System.arraycopy(rgba, 0, this.rgba, 0, 4);
	}
	public void move(float xyz[]){
		this.xyz[0]+=xyz[0];
		this.xyz[1]+=xyz[1];
		this.xyz[2]+=xyz[2];
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
	public void rotate(float angle,float x,float y,float z){
		Matrix.rotate(this, new double[]{x,y,z}, angle);
	}
	public Vertex quantity(Vertex vv){//求当前点到目标点的向量
		Vertex v=new Vertex();
		v.xyz[0]=vv.xyz[0]-this.xyz[0];
		v.xyz[1]=vv.xyz[1]-this.xyz[1];
		v.xyz[2]=vv.xyz[2]-this.xyz[2];
		return v;
	}
	/**
	 * 
	 * @param v
	 * @return
	 */
	public boolean equals(Vertex v){//判断是否相等
		return ((Math.abs(xyz[0]-v.xyz[0])+Math.abs(xyz[1]-v.xyz[1])+Math.abs(xyz[2]-v.xyz[2]))<0.0001);
	}
	
	@Override
	public String toString() {
		return 	"Vertex: " +
					"position<xyz>("+xyz[0]+","+xyz[1]+","+xyz[2]+")-" +
					"color<rgba>("+rgba[0]+","+rgba[1]+","+rgba[2]+","+rgba[3]+") ;";
	}
	@Override
	public boolean equals(Object o) {
		if(null==o)
			return false;
		if(this==o) 
			return true;
		if(o instanceof Vertex){
			Vertex v=(Vertex)o;
			return ((Math.abs(xyz[0]-v.xyz[0])+Math.abs(xyz[1]-v.xyz[1])+Math.abs(xyz[2]-v.xyz[2]))<0.0001);
		}
		return false;
	}
	@Override
	public Vertex clone(){
		return new Vertex(this);
	}
	
}
