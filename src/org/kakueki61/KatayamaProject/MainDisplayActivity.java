package org.kakueki61.KatayamaProject;

import android.app.Activity;
import android.os.Bundle;
import org.kakueki61.KatayamaProject.api.ApiRequestFunctions;
import org.kakueki61.KatayamaProject.view.MainDisplayView;

/**
 * This is the screen showing first ListView
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 13/12/09 kodama-t
 */
public class MainDisplayActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list);

        ApiRequestFunctions.requestFeeds(this, getResources().getStringArray(R.array.feed_urls), new MainDisplayView(this));
    }

}
