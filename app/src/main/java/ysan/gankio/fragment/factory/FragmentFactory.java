package ysan.gankio.fragment.factory;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.SparseArrayCompat;

import ysan.gankio.R;

/**
 * Created by YSAN on 2017/1/6 11:44
 */
public class FragmentFactory {
    static Fragment mFragment;

    static SparseArrayCompat<Fragment> cacheFragment = new SparseArrayCompat<>();

    public static Fragment loadMainFragment(int id, FragmentManager supportFragmentManager) {

        mFragment = null;

        Fragment fragment = cacheFragment.get(id);

        if (fragment != null){
            mFragment = fragment;
            return mFragment;
        }

        switch (id) {
            case R.id.radio_button_find:

                break;
            case R.id.radio_button_follow:

                break;
            case R.id.radio_button_message:

                break;
            case R.id.radio_button_account:

                break;
        }

        return mFragment;
    }
}
