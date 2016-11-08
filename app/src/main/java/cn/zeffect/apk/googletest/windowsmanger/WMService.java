package cn.zeffect.apk.googletest.windowsmanger;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.zeffect.apk.googletest.R;

/**
 * Created by zeffect on 2016/10/11.
 */
public class WMService extends Service {
    WindowManager mManager;
    WindowManager.LayoutParams mParams;
    private View layoutWM;
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        creatFullFloatWindow();
//        new Thread(new IRunnable()).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void creatFullFloatWindow() {
        if (mManager == null) {
            mParams = new WindowManager.LayoutParams();
            mManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//            mParams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
            mParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN;
            mParams.gravity = Gravity.LEFT | Gravity.TOP;
            mParams.x = 0;
            mParams.y = 0;
            mParams.alpha = 1f;// 透明度
            mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        }
        //添加布局
        if (layoutWM == null) {
            layoutWM = LayoutInflater.from(this).inflate(R.layout.layout_wm, null);
            layoutWM.findViewById(R.id.lw_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mManager.removeView(layoutWM);
//                    mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
//                    mManager.addView(layoutWM, mParams);
                }
            });
        }
        mManager.addView(layoutWM, mParams);
    }

    private void showPop() {
        TextView textView = new TextView(mContext);
        textView.setText("测试POPWINDOWS");
        PopupWindow popupWindow = new PopupWindow(textView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(layoutWM, Gravity.CENTER, 0, 0);
    }

    private void showPop2() {
        TextView textView = new TextView(mContext);
        textView.setText("测试POPWINDOWS");
        PopupWindow popupWindow = new PopupWindow(textView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(layoutWM, Gravity.BOTTOM, 0, 0);
    }

    private int dp2px(Context pContext, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, pContext.getResources().getDisplayMetrics());
    }
}
