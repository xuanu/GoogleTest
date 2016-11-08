package cn.zeffect.apk.googletest.imagecanvas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.zeffect.apk.googletest.R;
import cn.zeffect.apk.googletest.imagecanvas.view.ImageCanvasView;

/**
 * Created by zeffect on 2016/11/7.
 *
 * @auther zzx
 */
public class ImageCanvasActivity extends Activity {
    Button penBtn, eraserBtn;
    ImageCanvasView mImageCanvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_canvas);
        mImageCanvasView = (ImageCanvasView) findViewById(R.id.canvasview);
        penBtn = (Button) findViewById(R.id.aic_pen_btn);
        eraserBtn = (Button) findViewById(R.id.aic_eraser_btn);
        penBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mImageCanvasView.setType(ImageCanvasView.TYPE_PEN_1);
            }
        });
        eraserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mImageCanvasView.setType(ImageCanvasView.TYPE_ERASER_2);
            }
        });
    }
}
