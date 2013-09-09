package pro.android;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.util.Log;

public class RegularPolygon
{
    // 중점의 (x,y,z) 좌표를 저장할 변수: cx,cy,cz
    // 반경을 저장할 변수: r
    private float cx, cy, cz, r;
    private int sides;

    // 좌표 배열: (x,y) 정점들
    private float[] xarray = null;
    private float[] yarray = null;

    // 텍스처 배열: (x,y) - (s,t)라고도 함
    // 도형이 텍스처 비트맵으로 매핑될 정점들
    private float[] sarray = null;
    private float[] tarray = null;

    //**********************************************
    // 생성자
    //**********************************************
    public RegularPolygon(float incx, float incy, float incz, // (x,y,z) 중점 
    float inr, // 반경
    int insides) // 변의 수
    {
        cx = incx;
        cy = incy;
        cz = incz;
        r = inr;
        sides = insides;

        // 배열들에 메모리 할당
        xarray = new float[sides];
        yarray = new float[sides];

        // 텍스처 점 배열에 메모리 할당
        sarray = new float[sides];
        tarray = new float[sides];

        // 정점들을 계산
        calcArrays();

        // 텍스처 점들을 계산
        calcTextureArrays();
    }

    //**********************************************
    // 정점 좌표들을 기점과 반경을 이용해 구한 후 변환
    // 각도에 대한 실제 로직은 getMultiplierArray() 함수 안에 구현됨
    //**********************************************
    private void calcArrays()
    {
        // 원의 반경이 1이고 기점 0 위치에 있다는
        // 전제로 정점들을 구함
        float[] xmarray = this.getXMultiplierArray();
        float[] ymarray = this.getYMultiplierArray();

        // xarray 계산: 기점의 x 부분을
        // 좌표와 반경의 곱에 더해서 정점을 구함
        for(int i=0;i<sides;i++)
        {
            float curm = xmarray[i];
            float xcoord = cx + r * curm;
            xarray[i] = xcoord;
        }
        this.printArray(xarray, "xarray");

        // yarray 계산: y 좌표에 대해 마찬가지로 수행
        for(int  i=0;i<sides;i++)
        {
            float curm = ymarray[i];
            float ycoord = cy + r * curm;
            yarray[i] = ycoord;
        }
        this.printArray(yarray, "yarray");

    }

    //**********************************************
    // 텍스처 배열 계산
    // 자세한 내용은 '텍스처' 하위 절을 참조하자
    // 거의 비슷한 방법이지만,
    // 여기서는 다각형이 정사각 공간에 매핑돼야 한다.
    //**********************************************
    private void  calcTextureArrays()
    {
        float[] xmarray = this.getXMultiplierArray();
        float[] ymarray = this.getYMultiplierArray();

        // xarray 계산
        for(int i=0;i<sides;i++)
        {
            float curm = xmarray[i];
            float xcoord = 0.5f + 0.5f * curm;
            sarray[i] = xcoord;
        }
        this.printArray(sarray, "sarray");

        // yarray 계산
        for(int  i=0;i<sides;i++)
        {
            float curm = ymarray[i];
            float ycoord = 0.5f + 0.5f * curm;
            tarray[i] = ycoord;
        }
        this.printArray(tarray, "tarray");
    }

    //**********************************************
    // 정점들의 자바 배열을
    // nio 부동소수점 버퍼로 변환
    //**********************************************
    public FloatBuffer getVertexBuffer()
    {
        int vertices = sides + 1;
        int coordinates = 3;
        int floatsize = 4;
        int spacePerVertex = coordinates * floatsize;

        ByteBuffer vbb = ByteBuffer.allocateDirect(spacePerVertex * vertices);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer mFVertexBuffer = vbb.asFloatBuffer();

        // 첫 번째 좌표 (x,y,z:0,0,0)를 버퍼에 추가
        mFVertexBuffer.put(cx); // x 
        mFVertexBuffer.put(cy); // y 
        mFVertexBuffer.put(0.0f); // z

        int totalPuts = 3;
        for (int  i=0;i<sides;i++)
        {
            mFVertexBuffer.put(xarray[i]); // x 
            mFVertexBuffer.put(yarray[i]); // y
            mFVertexBuffer.put(0.0f); // z
            totalPuts += 3;
        }
        Log.d("버퍼에 추가된 총 좌표 수:",Integer.toString(totalPuts));
        return mFVertexBuffer;
    }

    //**********************************************
    // 텍스처 버퍼를 nio 버퍼로 변환
    //**********************************************
    public FloatBuffer getTextureBuffer()
    {
        int vertices = sides + 1;
        int coordinates = 2;
        int floatsize = 4;
        int spacePerVertex = coordinates * floatsize;

        ByteBuffer vbb = ByteBuffer.allocateDirect(spacePerVertex * vertices);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer mFTextureBuffer = vbb.asFloatBuffer();

        // 첫 번째 좌표 (x,y (s,t):0,0)를 버퍼에 추가
        mFTextureBuffer.put(0.5f); // x 또는 s 
        mFTextureBuffer.put(0.5f); // y 또는 t

        int totalPuts = 2;
        for (int i=0;i<sides;i++)
        { 
            mFTextureBuffer.put(sarray[i]); // x
            mFTextureBuffer.put(tarray[i]); // y
            totalPuts += 2;
        }
        Log.d("버퍼에 추가된 총 텍스처 좌표 수:",Integer.toString(totalPuts));
        return mFTextureBuffer;
    }

    //**********************************************
    // 여러 개의 삼각형을 구성하는 인덱스들을 계산
    // 중점 0에서 시작해서
    // 0,1,2, 0,2,3, 0,3,4.. 처럼 시계 방향으로 셈
    //**********************************************
    public ShortBuffer getIndexBuffer()
    {
        short[] iarray = new short[sides * 3];
        ByteBuffer ibb = ByteBuffer.allocateDirect(sides * 3 * 2);
        ibb.order(ByteOrder.nativeOrder());
        ShortBuffer mIndexBuffer = ibb.asShortBuffer();
        for (int i=0;i<sides;i++)
        {
            short index1 = 0;
            short index2 = (short)(i+1);
            short index3 = (short)(i+2);
            if (index3 == sides+1)
            {
                index3 = 1;
            }
            mIndexBuffer.put(index1);
            mIndexBuffer.put(index2);
            mIndexBuffer.put(index3);

            iarray[i*3 + 0]=index1; 
            iarray[i*3 + 1]=index2; 
            iarray[i*3 + 2]=index3;
        }
        this.printShortArray(iarray, "index array");
        return mIndexBuffer;
    }

    //**********************************************
    // 각 정점별 각도 배열을 받아서
    // x축의 투영 승수를 계산
    //**********************************************
    private float[] getXMultiplierArray()
    {
        float[] angleArray = getAngleArrays();
        float[] xmultiplierArray = new float[sides];
        for(int i=0;i<angleArray.length;i++)
        {
            float curAngle = angleArray[i];
            float  sinvalue = (float)Math.cos(Math.toRadians(curAngle));
            float absSinValue = Math.abs(sinvalue);
            if (isXPositiveQuadrant(curAngle))
            {
                sinvalue = absSinValue;
            }
            else
            {
                sinvalue = -absSinValue;
            }
            xmultiplierArray[i] = this.getApproxValue(sinvalue);
        }
        this.printArray(xmultiplierArray, "xmultiplierArray");
        return xmultiplierArray;
    }

    //**********************************************
    // 각 정점별 각도 배열을 받아서
    // y축의 투영 승수를 계산
    //**********************************************
    private float[] getYMultiplierArray() {
        float[] angleArray = getAngleArrays();
        float[] ymultiplierArray = new float[sides];
        for(int i=0;i<angleArray.length;i++) {
            float curAngle = angleArray[i];
            float sinvalue = (float)Math.sin(Math.toRadians(curAngle));
            float absSinValue = Math.abs(sinvalue);
            if (isYPositiveQuadrant(curAngle)) {
                sinvalue = absSinValue;
            }
            else {
                sinvalue = -absSinValue;
            }
            ymultiplierArray[i] = this.getApproxValue(sinvalue);
        }
        this.printArray(ymultiplierArray, "ymultiplierArray");
        return ymultiplierArray;
    }

    //**********************************************
    // 이 함수는 필요 없을 수도 있으므로,
    // 각자 테스트해서 필요 없으면 삭제할 것
    //**********************************************
    private boolean isXPositiveQuadrant(float angle) {
        if ((0 <= angle) && (angle <= 90)) { return true; }
        if ((angle < 0) && (angle >= -90)) { return true; }
        return false;
    }
    //**********************************************
    // 이 함수는 필요 없을 수도 있으므로,
    // 각자 테스트해서 필요 없으면 삭제할 것
    //**********************************************
    private boolean isYPositiveQuadrant(float angle) {
        if ((0 <= angle) && (angle <= 90)) { return true; }
        if ((angle < 180) && (angle >= 90)) {return true;}
        return false;
    }
    //**********************************************
    // 중점과 각 정점을 잇는
    // 각 직선별 각도 계산
    //**********************************************
    private float[] getAngleArrays() {
        float[] angleArray = new float[sides];
        float commonAngle = 360.0f/sides;
        float halfAngle = commonAngle/2.0f;
        float firstAngle = 360.0f - (90+halfAngle);
        angleArray[0] = firstAngle;

        float curAngle = firstAngle;
        for(int i=1;i<sides;i++)
        {
            float newAngle = curAngle - commonAngle;
            angleArray[i] = newAngle;
            curAngle = newAngle;
        }
        printArray(angleArray, "angleArray");
        return angleArray;
    }

    //**********************************************
    // 필요할 경우 약간의 곡선 처리
    //**********************************************
    private float getApproxValue(float f) {
        return (Math.abs(f) < 0.001) ? 0 : f;
    }
    //**********************************************
    // 주어진 변의 수에 대해, 필요한 인덱스 수 반환
    // 이것은 다각형 그리기에 필요한
    // 삼각형 수에 3을 곱하면 된다.
    // 삼각형 수가 변의 수와 같은 것은
    // 그저 우연일 뿐이다.
    //**********************************************
    public int getNumberOfIndices() {
        return sides * 3;
    }
    public static void test() {
        RegularPolygon triangle = new RegularPolygon(0,0,0,1,3);
    }
    private void printArray(float array[], String tag) {
        StringBuilder sb = new StringBuilder(tag);
        for(int i=0;i<array.length;i++) {
            sb.append(";").append(array[i]);
        }
        Log.d("hh",sb.toString());
    }
    private void printShortArray(short array[], String tag) {
        StringBuilder sb = new StringBuilder(tag);
        for(int i=0;i<array.length;i++) {
            sb.append(";").append(array[i]);
        }
        Log.d(tag,sb.toString());
    }
}
