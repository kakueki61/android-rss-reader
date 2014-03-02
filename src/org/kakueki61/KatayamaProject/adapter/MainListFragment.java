package org.kakueki61.KatayamaProject.adapter;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import com.android.volley.Response;
import org.kakueki61.KatayamaProject.api.ApiRequestFunctions;
import org.kakueki61.KatayamaProject.util.DataHandleHelper;
import org.kakueki61.KatayamaProject.util.LogHelper;
import org.kakueki61.KatayamaProject.util.XmlPullParserHelper;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 14/03/02 kodama-t
 */
public class MainListFragment extends ListFragment {

    private String mUrl;

    public MainListFragment(String url) {
        super();
        this.mUrl = url;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ApiRequestFunctions.requestFeed(getActivity(), mUrl, new Response.Listener<InputStream>() {
            @Override
            public void onResponse(InputStream is) {
                List<Map<String, String>> response = XmlPullParserHelper.inputStreamToHashList(is);

                //parse <description> tag(CDATA)
                List<Map<String, String>> cdataMapList = DataHandleHelper.extractContentsFromCDATA(response);
                LogHelper.logHashList(cdataMapList);

                setListAdapter(new MainDisplayListViewAdapter(getActivity(), cdataMapList));
            }
        });
    }
}
