package app.mobile.examwarrior.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import app.mobile.examwarrior.ui.fragments.ForgetPasswordFragment;
import app.mobile.examwarrior.ui.fragments.LoginFragment;
import app.mobile.examwarrior.ui.fragments.SignUpFragment;

/**
 * Created by sandesh on 3/5/17, 10:29 PM.
 */

public class RegistrationPagerAdapter extends FragmentStatePagerAdapter {

    private int pageCount = 0;

    public RegistrationPagerAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.pageCount = pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = ForgetPasswordFragment.newInstance("", "");
                break;
            case 1:
                fragment = LoginFragment.newInstance("SignIn", " ");
                break;
            case 2:
                fragment = SignUpFragment.newInstance("SignUp", " ");
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
