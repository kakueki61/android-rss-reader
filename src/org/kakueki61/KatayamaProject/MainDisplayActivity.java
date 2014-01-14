package org.kakueki61.KatayamaProject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import org.kakueki61.KatayamaProject.api.InputStreamRequest;
import org.kakueki61.KatayamaProject.util.Constants;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * This is the screen showing first ListView
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 13/12/09 kodama-t
 */
public class MainDisplayActivity extends Activity {

    private RequestQueue mQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        requestFeed(Constants.URL_LIFEHUCKER);
    }

    private void requestFeed(String url) {
        InputStreamRequest request = new InputStreamRequest(url,
                new Response.Listener<InputStream>() {
                    @Override
                    public void onResponse(InputStream is) {
                        List<HashMap<String, String>> response = parseXmlContent(is);
                        for(int i = 0; i < response.size(); i++) {
                            Log.i("map", i + ": ");
                            Iterator<String> iterator = response.get(i).keySet().iterator();
                            String key;
                            while (iterator.hasNext()) {
                                key = iterator.next();
                                Log.i(key, response.get(i).get(key));
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );

        mQueue.add(request);
    }

    private ArrayList<HashMap<String, String>> parseXmlContent(InputStream inputStream) {
        ArrayList<HashMap<String, String>> responseHolder = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> itemMap = new HashMap<String, String>();
        XmlPullParser parser;
        try {
            parser = Xml.newPullParser();
            parser.setInput(inputStream, "UTF-8");

            int eventType = parser.getEventType();
            String tagName = null;
            String content;
            while(eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();

                        if("item".equals(tagName)) {
                            itemMap = new HashMap<String, String>();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if(parser.isWhitespace()) {
                            break;
                        }
                    case XmlPullParser.CDSECT:
                        content = parser.getText();

                        if(content != null) {
                            itemMap.put(tagName, content);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();

                        if("item".equals(tagName)) {
                            responseHolder.add(itemMap);
                        }
                    default:
                        break;
                }

                eventType = parser.nextToken();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseHolder;
    }
}
