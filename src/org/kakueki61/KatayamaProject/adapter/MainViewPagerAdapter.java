package org.kakueki61.KatayamaProject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 14/03/02 kodama-t
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        fragment = new MainListFragment();

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
