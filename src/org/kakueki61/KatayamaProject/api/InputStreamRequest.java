package org.kakueki61.KatayamaProject.api;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 13/12/09 kodama-t
 */
public class InputStreamRequest extends Request<InputStream> {

    private final Response.Listener<InputStream> mListener;

    /**
     * Creates a new request with the given method (one of the values from {@link com.android.volley.Request.Method}),
     * URL, and error listener.  Note that the normal response listener is not provided here as
     * delivery of responses is provided by subclasses, who have a better idea of how to deliver
     * an already-parsed response.
     *
     * @param method
     * @param url
     * @param listener
     */
    public InputStreamRequest(int method, String url, Response.Listener<InputStream> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
    }

    /**
     * Subclasses must implement this to parse the raw network response
     * and return an appropriate response type. This method will be
     * called from a worker thread.  The response will not be delivered
     * if you return null.
     *
     * @param response Response from the network
     *
     * @return The parsed response, or null in the case of an error
     */
    @Override
    protected Response<InputStream> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    /**
     * Subclasses must implement this to perform delivery of the parsed
     * response to their listeners.  The given response is guaranteed to
     * be non-null; responses that fail to parse are not delivered.
     *
     * @param response The parsed response returned by
     *                 {@link #parseNetworkResponse(com.android.volley.NetworkResponse)}
     */
    @Override
    protected void deliverResponse(InputStream response) {

    }
}
