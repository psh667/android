package pro.android;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.os.SystemClock;

public class PolygonRenderer extends AbstractRenderer
{
    // 사용할 정점 수
    private final static int VERTS = 4;

    // 정점 좌표들을 저장할 무형식 전용 버퍼
    private FloatBuffer mFVertexBuffer;

    // 정점들의 재사용에 쓰일 인덱스를
    // 저장할 무형식 전용 버퍼
    private ShortBuffer mIndexBuffer;
    private int numOfIndices = 0;

    private long prevtime = SystemClock.uptimeMillis();

    private int sides = 3;

    public PolygonRenderer(Context context)
    {
        //EvenPolygon t = new EvenPolygon(0,0,0,1,3);
        //EvenPolygon t = new EvenPolygon(0,0,0,1,4);
        prepareBuffers(sides);
    }

    private void prepareBuffers(int sides)
    {
        RegularPolygon t = new RegularPolygon(0,0,0,1,sides);
        //RegularPolygon t = new RegularPolygon(1,1,0,1,sides);
        this.mFVertexBuffer = t.getVertexBuffer();
        this.mIndexBuffer = t.getIndexBuffer();
        this.numOfIndices = t.getNumberOfIndices();
        this.mFVertexBuffer.position(0);
        this.mIndexBuffer.position(0);
    }

    // 재정의 메서드
    protected void draw(GL10 gl)
    {
        long curtime = SystemClock.uptimeMillis();
        if ((curtime - prevtime) > 2000)
        {
            prevtime = curtime;
            sides += 1;
            if (sides > 20)
            {
                sides = 3;
            }
            this.prepareBuffers(sides);
        }
        //EvenPolygon.test();
        gl.glColor4f(1.0f, 0, 0, 0.5f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES, this.numOfIndices,
                GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
    }
}

