package org.kakueki61.KatayamaProject.util.volley;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import org.kakueki61.KatayamaProject.api.InputStreamRequest;
import org.kakueki61.KatayamaProject.util.DataHandleHelper;
import org.kakueki61.KatayamaProject.util.LogHelper;
import org.kakueki61.KatayamaProject.util.XmlPullParserHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 14/02/06 kodama-t
 */
public class VolleyHelper {

    private final static String TAG = VolleyHelper.class.getSimpleName();
    private static RequestQueue mRequestQueue;

    public static RequestQueue getRequestQueue(Context context) {
        if(mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public static void sendInputStreamRequest(Context context, String url) {
        sendInputStreamRequest(context, url, null, null);
    }

    public static void sendInputStreamRequest(Context context, String url,
                                              Response.Listener<InputStream> responseListener, Response.ErrorListener errorListener) {
        InputStreamRequest inputStreamRequest = new InputStreamRequest(url, responseListener, errorListener);

        getRequestQueue(context).add(inputStreamRequest);
    }

    public static void sendInputStreamRequests(Context context, String[] urls,
                                              Response.Listener<List<Map<String, String>>> responseListener, Response.ErrorListener errorListener) {

        if(VolleyLog.DEBUG) {
            Log.i(TAG, "Volley debug log!");
        }

        final CountDownLatch countDownLatch = new CountDownLatch(urls.length);
        final List<Map<String, String>> compiledResponseList = new ArrayList<Map<String, String>>();

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Loading");
        dialog.setMessage("Now Loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        Log.d(TAG, "dialog.show()");

        for(int i = 0; i < urls.length; i++) {
            InputStreamRequest inputStreamRequest = new InputStreamRequest(urls[i],
                    new Response.Listener<InputStream>() {
                        @Override
                        public void onResponse(InputStream is) {
                            Log.d(TAG, "onResponse!!!!!!!");
                            List<Map<String, String>> response = XmlPullParserHelper.inputStreamToHashList(is);
                            //logHashList(response);

                            //parse <description> tag(CDATA)
                            List<Map<String, String>> cdataMapList = DataHandleHelper.extractContentsFromCDATA(response);
                            LogHelper.logHashList(cdataMapList);

                            compiledResponseList.addAll(cdataMapList);

                            countDownLatch.countDown();
                            Log.d(TAG, "" + countDownLatch.getCount());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            );
            getRequestQueue(context).add(inputStreamRequest);
            Log.d(TAG, "added queue");
        }

        try {
            Log.d(TAG, "waiting");
            countDownLatch.await();
            Log.d(TAG, "---------------- Finish ! -----------------");
            dialog.dismiss();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        responseListener.onResponse(compiledResponseList);
    }
}
