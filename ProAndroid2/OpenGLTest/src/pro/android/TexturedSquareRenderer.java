package pro.android;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class TexturedSquareRenderer extends AbstractSingleTexturedRenderer
{
    // 사용할 정점 수
    private final static int VERTS = 4;

    // 정점 좌표들을 저장할 무형식 전용 버퍼
    private FloatBuffer mFVertexBuffer;

    // 정점 좌표들을 저장할 무형식 전용 버퍼
    private FloatBuffer mFTextureBuffer;

    // 정점들의 재사용에 쓰일 인덱스들을
    // 저장할 무형식 전용 버퍼
    private ShortBuffer mIndexBuffer;

    private int numOfIndices = 0;

    private int sides = 4;

    public TexturedSquareRenderer(Context context)
    {
        super(context, pro.android.R.drawable.robot);
        prepareBuffers(sides);
    }

    private void prepareBuffers(int sides)
    {
        RegularPolygon t = new RegularPolygon(0,0,0,0.5f,sides);
        this.mFVertexBuffer = t.getVertexBuffer();
        this.mFTextureBuffer = t.getTextureBuffer();
        this.mIndexBuffer = t.getIndexBuffer();
        this.numOfIndices = t.getNumberOfIndices();
        this.mFVertexBuffer.position(0);
        this.mIndexBuffer.position(0);
        this.mFTextureBuffer.position(0);

    }

    // 재정의 메서드
    protected void draw(GL10 gl)
    {
        prepareBuffers(sides);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer); 
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mFTextureBuffer); 
        gl.glDrawElements(GL10.GL_TRIANGLES, this.numOfIndices,
                GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
    }
}
