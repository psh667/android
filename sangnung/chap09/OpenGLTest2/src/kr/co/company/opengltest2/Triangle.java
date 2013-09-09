package kr.co.company.opengltest2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Triangle {
	private FloatBuffer vertexBuffer;
	private FloatBuffer colorBuffer;
	
	private float vertices[] = { 
								0.0f, 1.0f, 0.0f, 	
								-1.0f, -1.0f, 0.0f, 
								1.0f, -1.0f, 0.0f 	
	};
	
	private float colors[] = {
		    					1.0f, 0.0f, 0.0f, 1.0f, 
		    					0.0f, 1.0f, 0.0f, 1.0f, 
		    					0.0f, 0.0f, 1.0f, 1.0f 	
	};
	
	public Triangle() {
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		colorBuffer = byteBuf.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
	}

	public void draw(GL10 gl) {		
		gl.glFrontFace(GL10.GL_CW);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertices.length / 3);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}
