package com.engleg.staticyayinalici;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class YayinAlicim extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Yayin Alicim","Cihaz Acildi");
    }
}
