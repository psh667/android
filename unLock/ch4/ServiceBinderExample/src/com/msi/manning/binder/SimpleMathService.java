package com.msi.manning.binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SimpleMathService extends Service {

    private final ISimpleMathService.Stub binder = new ISimpleMathService.Stub() {

        public int add(int a, int b) {
            return a + b;
        }

        public int subtract(int a, int b) {
            return a - b;
        }

        public String echo(String input) {
            return "echo " + input;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

}
