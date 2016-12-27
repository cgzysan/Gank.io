package ysan.gankio.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ysan.gankio.R;
import ysan.gankio.dialog.Mydialog;

/**
 * 创建者     YSAN
 * 创建时间   2016/12/20 23:11
 * 描述	      ${TODO}
 * <p/>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class BitmapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        ImageView imageView = (ImageView) findViewById(R.id.bitmap_image);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        Glide.with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.color.app_primary_dark)
                .crossFade()
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mydialog dialog = new Mydialog(BitmapActivity.this);
                dialog.show();
                /**将dialog的宽度设置成填充父窗体*/
                    /*--------------- add begin ---------------*/
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                Point size = new Point();
                display.getSize(size);
                lp.width = size.x - 128;
                dialog.getWindow().setAttributes(lp);
                    /*--------------- add end ---------------*/
            }
        });
    }
}
