package cn.zeffect.apk.googletest.windowsmanger;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

    private boolean isLock = false;

    private void creatFullFloatWindow() {
        if (mManager == null) {
            mParams = new WindowManager.LayoutParams();
            mManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
//            mParams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
            mParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
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
//                    updateLayoutView(isLock = !isLock);
                    mManager.removeView(layoutWM);
                }
            });
        }
//        String temp = Build.MANUFACTURER + " " + Build.DEVICE;
        mManager.addView(layoutWM, mParams);
    }

    /**
     * 根据是否锁屏，更新布局信息
     *
     * @param isLockScreen
     */
    private void updateLayoutView(boolean isLockScreen) {
        int typte = mParams.type;
        int setType = 0;
        if (isLockScreen) {
            setType = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        } else {
            setType = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        if (typte != setType) {
            mParams.type = setType;
            mManager.removeView(layoutWM);
            mManager.addView(layoutWM, mParams);
        }
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
