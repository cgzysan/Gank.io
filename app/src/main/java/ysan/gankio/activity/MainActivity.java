package ysan.gankio.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import ysan.gankio.R;
import ysan.gankio.fragment.FindFragment;

/**
 * Created by YSAN on 2017/1/6 8:55
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Fragment fragment = new FindFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_root, fragment)
                .commit();
    }
}
