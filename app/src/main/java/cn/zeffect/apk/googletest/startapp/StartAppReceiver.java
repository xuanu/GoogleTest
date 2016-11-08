package cn.zeffect.apk.googletest.startapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.zeffect.apk.googletest.MainActivity;

/**
 * Created by zeffect on 2016/11/8.
 *
 * @auther zzx
 */
public class StartAppReceiver extends BroadcastReceiver {
    public static final String STARTAPP = "cn.zeffect.apk.googletest.startapp";

    @Override
    public void onReceive(Context pContext, Intent pIntent) {
        System.out.println("收到一个广播：" + pIntent.getAction());
        if (pIntent.getAction().equals(STARTAPP)) {
            Intent intent = new Intent(pContext, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            pContext.startActivity(intent);
        }
    }
}
