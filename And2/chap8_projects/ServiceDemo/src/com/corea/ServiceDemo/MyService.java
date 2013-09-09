package com.corea.ServiceDemo;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

//Service의 실행 클래스
public class MyService extends Service implements Runnable {

    //시작ID
    private int mStartId;
    //타이머 주기 3초
    private static final int TIMER_PERIOD = 3 * 1000;
    //처리회수
    private static final int COUNT = 10;

    //처리카운터. 일정 회수 처리를 실행하면 종료한다
    private int mCounter;
    //Service용의 쓰레드에 관련된 Handler
    private Handler mHandler;
    //동작중 Frag
    private boolean mRunning;

    //Service 생성 때 호출됨
    public void onCreate() {
        Log.i("MyService", "MyService created.");
        super.onCreate();
        mHandler = new Handler();
        mRunning = false;
    }

    //Service의 시작요구 시에 호출된다
    //전달인자의 startID는 stopSelf에서 종료할 때에 사용한다
    public void onStart(Intent intent, int startId) {
        Log.i("MyService", "onStart id = " + startId);
        super.onStart(intent, startId);
        mStartId = startId;
        mCounter = COUNT;
        //이미 동작중이 아니면 run메소드를 일정시간 후에 기동한다
        if (!mRunning) {
            mHandler.postDelayed(this, TIMER_PERIOD);
            mRunning = true;
        }
    }

    //Service의 종료 시에 호출된다
    public void onDestroy() {
        Log.i("MyService", "MyService stop.");
        mRunning = false;
        super.onDestroy();
    }

    //Service의 처리
    public void run() {
        Log.i("MyService", "run " + mCounter);

        if (!mRunning) {
            //onDestory에서 Service가 정지하고 있으면 아무것도 하지  않고 종료
            Log.i("MyService", "run after destory");
            return;
        } else if (--mCounter <= 0) {
            //일정 회수 실행하면 스스로 종료한다
            Log.i("MyService", "stop Service id = " + mStartId);
            stopSelf(mStartId);
        } else {
            //다음 처리를 재차 요구한다
            mHandler.postDelayed(this, TIMER_PERIOD);
        }
    }

    //Remote에서의 메소드호출을 위해 사용한다
    //메소드호출을 제공하지 않을 경우에는 null을 반환한다
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
