package org.kakueki61.KatayamaProject.view;

import android.app.Activity;
import android.widget.ListView;
import org.kakueki61.KatayamaProject.R;
import org.kakueki61.KatayamaProject.adapter.MainDisplayListViewAdapter;

import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 14/02/06 kodama-t
 */
public class MainDisplayView {

    private Activity mActivity;
    private ListView mListView;

    public MainDisplayView(Activity activity) {
        this.mActivity = activity;
    }

    public void render(List<Map<String, String>> responseMapList) {
        mListView = (ListView) mActivity.findViewById(R.id.listview);

        MainDisplayListViewAdapter adapter = new MainDisplayListViewAdapter(mActivity, responseMapList);

        mListView.setAdapter(adapter);
    }
}
