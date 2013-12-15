package org.kakueki61.KatayamaProject;

import android.app.Activity;
import android.os.Bundle;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

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

        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
    }
}
