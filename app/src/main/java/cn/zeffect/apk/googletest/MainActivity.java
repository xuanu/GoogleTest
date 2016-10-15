package cn.zeffect.apk.googletest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.LinkedList;
import java.util.List;

import cn.zeffect.apk.googletest.windowsmanger.WMActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> list = new LinkedList<>();
        list.add("BottomSheetDialog");
        list.add("WindowManager测试");
        Adapter adapter = new Adapter(list);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (i == 0) {
                    gotoTargit(BottomSheetDialogActivity.class);
                } else if (i == 1) {
                    gotoTargit(WMActivity.class);
                }
            }
        });
    }


    private void gotoTargit(Class<?> pClass) {
        Intent intent = new Intent(this, pClass);
        startActivity(intent);
    }

    class Adapter extends BaseQuickAdapter<String> {

        public Adapter(List<String> data) {
            super(android.R.layout.simple_list_item_1, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, String s) {
            baseViewHolder.setText(android.R.id.text1, s);
        }
    }
}
