package cn.zeffect.apk.googletest.ImageCanvas2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.forward.androids.utils.ImageUtils;
import cn.zeffect.apk.googletest.ImageCanvas2.view.GraffitiView;
import cn.zeffect.apk.googletest.R;

/**
 * Created by zeffect on 2016/11/8.
 *
 * @auther zzx
 */
public class ImageCanvas2 extends Activity {
    GraffitiView mGraffitiView;
    RadioButton penBtn, eraseBtn, scaBtn;
    private Button saveBtn, randomBtn, changeBtn;

    private boolean mIsMovingPic = false;
    private float mScale = 1;
    private int mTouchMode;
    private float mTouchLastX, mTouchLastY;
    // 手势操作相关
    private float mOldScale, mOldDist, mNewDist, mToucheCentreXOnGraffiti,
            mToucheCentreYOnGraffiti, mTouchCentreX, mTouchCentreY;// 双指距离
    private final float mMaxScale = 3.5f; // 最大缩放倍数
    private final float mMinScale = 0.25f; // 最小缩放倍数
    private int mTouchSlop;
    FrameLayout tFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_canvas2);
        tFrameLayout = (FrameLayout) findViewById(R.id.aic2_fl);

        mTouchSlop = ViewConfiguration.get(getApplicationContext()).getScaledTouchSlop();
        penBtn = (RadioButton) findViewById(R.id.aic2_pen);
        eraseBtn = (RadioButton) findViewById(R.id.aic2_erase);
        scaBtn = (RadioButton) findViewById(R.id.aic2_scaly);
        penBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton pButton, boolean pB) {
                if (pB) {
                    mGraffitiView.setPen(GraffitiView.Pen.HAND);
                }
            }
        });
        eraseBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton pButton, boolean pB) {
                if (pB) {
//                    mGraffitiView.setPen(GraffitiView.Pen.ERASER);
                    mGraffitiView.setPen(GraffitiView.Pen.HAND);
                    mGraffitiView.setColor(0xffffffff);
                }
            }
        });
        scaBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton pButton, boolean pB) {
                mIsMovingPic = pB;
                if (mIsMovingPic) {
                    Toast.makeText(getApplicationContext(), "双指缩放", Toast.LENGTH_SHORT).show();
                }
            }
        });
        saveBtn = (Button) findViewById(R.id.aic2_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mGraffitiView.save();
            }
        });
        randomBtn = (Button) findViewById(R.id.aic2_random_text);
        randomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mGraffitiView.drawTextInPic("随机写字");
            }
        });
        changeBtn = (Button) findViewById(R.id.aic2_change_photo);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                closeView();
                creatView(BitmapFactory.decodeResource(getResources(), R.drawable.teacher_home_work));
                tFrameLayout.addView(mGraffitiView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        });
        creatView(BitmapFactory.decodeResource(getResources(), R.drawable.test));
        tFrameLayout.addView(mGraffitiView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    private void creatView(Bitmap pBitmap) {
        mGraffitiView = new GraffitiView(this, pBitmap, new GraffitiView.GraffitiListener() {
            @Override
            public void onSaved(Bitmap bitmap) {
                File file = new File(getExternalFilesDir("photo"), System.currentTimeMillis() + ".jpg");
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(GraffitiView.ERROR_SAVE, e.getMessage());
                } finally {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                        }
                    }
                }
            }

            @Override
            public void onError(int i, String msg) {

            }
        });
        // 添加涂鸦的触摸监听器，移动图片位置
        mGraffitiView.setOnTouchListener(new View.OnTouchListener() {

            boolean mIsBusy = false; // 避免双指滑动，手指抬起时处理单指事件。

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!mIsMovingPic) {
                    return false;
                }
                mScale = mGraffitiView.getScale();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchMode = 1;
                        mTouchLastX = event.getX();
                        mTouchLastY = event.getY();
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mTouchMode = 0;
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (mTouchMode < 2) { // 单点滑动
                            if (mIsBusy) {
                                mIsBusy = false;
                                mTouchLastX = event.getX();
                                mTouchLastY = event.getY();
                                return true;
                            }
                            float tranX = event.getX() - mTouchLastX;
                            float tranY = event.getY() - mTouchLastY;
                            mGraffitiView.setTrans(mGraffitiView.getTransX() + tranX, mGraffitiView.getTransY() + tranY);
                            mTouchLastX = event.getX();
                            mTouchLastY = event.getY();
                        } else { // 多点
                            mNewDist = spacing(event);// 两点滑动时的距离
                            if (Math.abs(mNewDist - mOldDist) >= mTouchSlop) {
                                float scale = mNewDist / mOldDist;
                                mScale = mOldScale * scale;

                                if (mScale > mMaxScale) {
                                    mScale = mMaxScale;
                                }
                                if (mScale < mMinScale) { // 最小倍数
                                    mScale = mMinScale;
                                }
                                // 围绕坐标(0,0)缩放图片
                                mGraffitiView.setScale(mScale);
                                // 缩放后，偏移图片，以产生围绕某个点缩放的效果
                                float transX = mGraffitiView.toTransX(mTouchCentreX, mToucheCentreXOnGraffiti);
                                float transY = mGraffitiView.toTransY(mTouchCentreY, mToucheCentreYOnGraffiti);
                                mGraffitiView.setTrans(transX, transY);
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_POINTER_UP:
                        mTouchMode -= 1;
                        return true;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        mTouchMode += 1;
                        mOldScale = mGraffitiView.getScale();
                        mOldDist = spacing(event);// 两点按下时的距离
                        mTouchCentreX = (event.getX(0) + event.getX(1)) / 2;// 不用减trans
                        mTouchCentreY = (event.getY(0) + event.getY(1)) / 2;
                        mToucheCentreXOnGraffiti = mGraffitiView.toX(mTouchCentreX);
                        mToucheCentreYOnGraffiti = mGraffitiView.toY(mTouchCentreY);
                        mIsBusy = true;
                        return true;
                }
                return false;
            }
        });
        penBtn.setChecked(true);
        mGraffitiView.setPen(GraffitiView.Pen.HAND);
    }

    private void closeView() {
        if (mGraffitiView != null) {
            tFrameLayout.removeView(mGraffitiView);
            mGraffitiView = null;
        }
    }


    /**
     * 计算两指间的距离
     *
     * @param event
     * @return
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
}
