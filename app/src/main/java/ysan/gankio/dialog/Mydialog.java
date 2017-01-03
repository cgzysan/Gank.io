package ysan.gankio.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import ysan.gankio.R;

/**
 * Created by YSAN on 2017/1/1 10:59
 */
public class Mydialog extends Dialog implements View.OnClickListener {

    private TextView mTvSave;
    private TextView mTvShare;
    private TextView mTvCancel;
    private Context mContext;

    public Mydialog(Context context) {
        this(context,R.style.MyDialogStyle);
        this.mContext = context;
    }

    public Mydialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取窗体
        Window window = getWindow();
        //获取属性
        WindowManager.LayoutParams attributes = window.getAttributes();
        //设置初始位置
        attributes.gravity = Gravity.CENTER_HORIZONTAL;
        attributes.width = 60;
        attributes.height = 100;
        //在把属性设置给window
        window.setAttributes(attributes);

        initView();
        initEvent();
    }

    private void initEvent() {
        mTvSave.setOnClickListener(this);
        mTvShare.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
    }

    private void initView() {
        setContentView(R.layout.dialog_bitmap);

        mTvSave = (TextView) findViewById(R.id.tv_bitmap_save);
        mTvShare = (TextView) findViewById(R.id.tv_bitmap_share);
        mTvCancel = (TextView) findViewById(R.id.tv_bitmap_cancel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_bitmap_save:
                Toast.makeText(mContext, "保存了", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.tv_bitmap_share:
                Toast.makeText(mContext, "分享了", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.tv_bitmap_cancel:
                dismiss();
                break;
        }
    }
}
