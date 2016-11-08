package cn.zeffect.apk.googletest.imagecanvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * 图片上面写写画画
 * Created by zeffect on 2016/11/7.
 *
 * @auther zzx
 */
public class ImageCanvasView extends ImageView {
    public static final String TAG = ImageCanvasView.class.getSimpleName();
    /**
     * 代表当前为画笔模式
     */
    public static final int TYPE_PEN_1 = 1;
    /**
     * 代表橡皮擦
     */
    public static final int TYPE_ERASER_2 = 2;
    /**
     * 模式
     */
    private int type;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 代表一条线
     */
    private Line current = new Line();
    /**
     * 所有线的集合
     */
    private ArrayList<Line> lines = new ArrayList<Line>();
    /**
     * 当前点击X
     */
    private float clickX;
    /**
     * 当前点击Y
     */
    private float clickY;
    /**
     * 用贝塞乐曲线画线
     */
    private final Path mPath = new Path();

    public ImageCanvasView(Context context) {
        super(context);
        init();
    }

    public ImageCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageCanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(0xff123456);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(12);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("测试", 100, 100, mPaint);
        for (int i = 0; i < lines.size(); i++) {
            drawLine(canvas, lines.get(i));
        }
//        //画出当前的线
        drawLine(canvas, current);
    }


    /**
     * 点
     */
    public class ViewPoint {
        float x;
        float y;
    }

    /**
     * 点集合
     */
    public class Line {
        /**
         * 点集合
         */
        ArrayList<ViewPoint> points = new ArrayList<ViewPoint>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取坐标
        clickX = event.getX();
        clickY = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            invalidate();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            ViewPoint point = new ViewPoint();
            point.x = clickX;
            point.y = clickY;
            //在移动时添加所经过的点
            if (type == TYPE_PEN_1) {
                current.points.add(point);
            } else if (type == TYPE_ERASER_2) {
            }
            invalidate();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //添加画过的线
            lines.add(current);
            current = new Line();
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    private void drawLine(Canvas canvas, Line line) {
        mPath.reset();
        for (int i = 0; i < line.points.size() - 1; i++) {
            float x = line.points.get(i).x;
            float y = line.points.get(i).y;
            float nextX = line.points.get(i + 1).x;
            float nextY = line.points.get(i + 1).y;
            //设置贝塞尔曲线的操作点为起点和终点的一半
            float cX = (x + nextX) / 2;
            float cY = (y + nextY) / 2;
            //二次贝塞尔，实现平滑曲线；previousX, previousY为操作点，cX, cY为终点
            mPath.moveTo(x, y);
            mPath.quadTo(cX, cY, nextX, nextY);
            canvas.drawPath(mPath, mPaint);
//            canvas.drawLine(x, y, nextX, nextY, mPaint);
        }
    }

    /**
     * 设置模式
     *
     * @param pType 画笔或橡皮
     */
    public void setType(int pType) {
        this.type = pType;
    }

}
