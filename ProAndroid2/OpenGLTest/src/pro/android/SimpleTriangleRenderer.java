package pro.android;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public  class  SimpleTriangleRenderer extends AbstractRenderer
{
	// 사용할 정점 수
	private final static int VERTS  = 3;
	
	// 정점 좌표들이 저장될 무형식 전용 버퍼
	private  FloatBuffer mFVertexBuffer;
	
	// 정점들의 재사용에 사용되는 인덱스가 저장될 무형식 전용 버퍼
	private  ShortBuffer mIndexBuffer;
	
	public  SimpleTriangleRenderer(Context context)
	{
		ByteBuffer vbb = ByteBuffer.allocateDirect(VERTS *  3  *  4);
		vbb.order(ByteOrder.nativeOrder());
		mFVertexBuffer = vbb.asFloatBuffer();
		
		ByteBuffer ibb  = ByteBuffer.allocateDirect(VERTS *  2);
		ibb.order(ByteOrder.nativeOrder());
		mIndexBuffer   = ibb.asShortBuffer();
		
		float[] coords = {
			-0.5f, -0.5f, 0, // (x1,y1,z1)
			0.5f, -0.5f, 0,
			0.0f,  0.5f, 0
			};
		for (int i = 0; i < VERTS;  i++)   {
			for(int j = 0; j < 3; j++)   {
				mFVertexBuffer.put(coords[i*3+j]);
			}
		}
		short[] myIndecesArray = {0,1,2};
		for (int  i=0;i<3;i++)
		{
			mIndexBuffer.put(myIndecesArray[i]);
		}
		mFVertexBuffer.position(0);
		mIndexBuffer.position(0);
	}
	
	// 재정의 메서드
	protected void  draw(GL10 gl)
	{
		gl.glColor4f(1.0f, 0, 0, 0.5f);
		gl.glVertexPointer(3, GL10.GL_FLOAT,  0, mFVertexBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLES,  VERTS,
		GL10.GL_UNSIGNED_SHORT,  mIndexBuffer);
	}
}
