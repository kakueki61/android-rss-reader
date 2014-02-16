package org.kakueki61.KatayamaProject.api;

import android.content.Context;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.kakueki61.KatayamaProject.util.DataHandleHelper;
import org.kakueki61.KatayamaProject.util.LogHelper;
import org.kakueki61.KatayamaProject.util.XmlPullParserHelper;
import org.kakueki61.KatayamaProject.util.volley.VolleyHelper;
import org.kakueki61.KatayamaProject.view.MainDisplayView;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 13/12/23 kodama-t
 */
public class ApiRequestFunctions {

    public static void requestFeed(Context context, String url, final MainDisplayView view) {
        VolleyHelper.sendInputStreamRequest(context, url,
                new Response.Listener<InputStream>() {
                    @Override
                    public void onResponse(InputStream is) {
                        List<Map<String, String>> response = XmlPullParserHelper.inputStreamToHashList(is);
                        //logHashList(response);

                        //parse <description> tag(CDATA)
                        List<Map<String, String>> cdataMapList = DataHandleHelper.extractContentsFromCDATA(response);
                        LogHelper.logHashList(cdataMapList);

                        view.render(cdataMapList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
    }

    public static void requestFeeds(Context context, String[] urls, final MainDisplayView view) {
        VolleyHelper.sendInputStreamRequests(context, urls,
                new Response.Listener<InputStream>() {
                    @Override
                    public void onResponse(InputStream is) {
                        List<Map<String, String>> response = XmlPullParserHelper.inputStreamToHashList(is);
                        //logHashList(response);

                        //parse <description> tag(CDATA)
                        List<Map<String, String>> cdataMapList = DataHandleHelper.extractContentsFromCDATA(response);
                        LogHelper.logHashList(cdataMapList);

                        view.render(cdataMapList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
    }
}
