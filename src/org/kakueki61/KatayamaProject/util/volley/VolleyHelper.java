package org.kakueki61.KatayamaProject.util.volley;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.kakueki61.KatayamaProject.api.InputStreamRequest;

import java.io.InputStream;

/**
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 14/02/06 kodama-t
 */
public class VolleyHelper {

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
}
