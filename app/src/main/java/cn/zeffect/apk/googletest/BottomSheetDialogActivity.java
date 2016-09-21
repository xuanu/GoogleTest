package cn.zeffect.apk.googletest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;

/**
 * Created by xuan on 2016/9/21.
 */
public class BottomSheetDialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomsheetdialog);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBottom(R.layout.layout_sheet_1);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBottom(R.layout.layout_sheet_2);
            }
        });
    }


    private void openBottom(@LayoutRes int resID) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(resID);
        dialog.show();
    }
}
