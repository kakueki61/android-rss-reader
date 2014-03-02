package org.kakueki61.KatayamaProject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import org.kakueki61.KatayamaProject.adapter.MainViewPagerAdapter;

/**
 * This is the screen showing first ListView
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 13/12/09 kodama-t
 */
public class MainDisplayActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list);

        MainViewPagerAdapter pagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
    }

}
