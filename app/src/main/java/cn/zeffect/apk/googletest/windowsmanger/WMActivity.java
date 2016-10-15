package cn.zeffect.apk.googletest.windowsmanger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by zeffect on 2016/10/11.
 */
public class WMActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, WMService.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("点击返回键盘");
    }
}
