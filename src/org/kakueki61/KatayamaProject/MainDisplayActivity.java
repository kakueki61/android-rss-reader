package org.kakueki61.KatayamaProject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.ListView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import org.kakueki61.KatayamaProject.adapter.MainDisplayListViewAdapter;
import org.kakueki61.KatayamaProject.api.InputStreamRequest;
import org.kakueki61.KatayamaProject.util.Constants;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the screen showing first ListView
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 13/12/09 kodama-t
 */
public class MainDisplayActivity extends Activity {

    private RequestQueue mQueue;
    private ListView listView;

    public static String TITLE_TAG = "title";
    public static String DESC_TAG = "description";
    public static String IMG_TAG = "img";
    public static String CONTENT_TAG = "content";
    public static String LINK_TAG = "link";
    public static String GUID_TAG = "guid";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list);

        listView = (ListView) findViewById(R.id.listview);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        requestFeed(Constants.URL_LIFEHUCKER);
    }

    private void requestFeed(String url) {
        InputStreamRequest request = new InputStreamRequest(url,
                new Response.Listener<InputStream>() {
                    @Override
                    public void onResponse(InputStream is) {
                        List<Map<String, String>> response = parseXmlContent(is);
                        //logHashList(response);

                        //parse <description> tag(CDATA)
                        List<Map<String, String>> cdataMapList = extractContentsFromCDATA(response);
                        logHashList(cdataMapList);

                        listView.setAdapter(new MainDisplayListViewAdapter(getApplicationContext(),
                                cdataMapList,
                                Volley.newRequestQueue(getApplicationContext())));
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

    private List<Map<String, String>> parseXmlContent(InputStream inputStream) {
        List<Map<String, String>> responseHolder = new ArrayList<Map<String, String>>();
        Map<String, String> itemMap = new HashMap<String, String>();
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

    public List<Map<String, String>> extractContentsFromCDATA(List<Map<String, String>> hashMapList) {
        String description;
        List<Map<String, String>> cdataMapList = new ArrayList<Map<String, String>>();

        String imgUrlPatternStr = "<img.+src\\s*=\\s*\"(.*?\\.(jpg|png|jpeg))\"";     //Large or Small character
        String passagePatternStr = ">\\s*([^<>]*?)\\s*<\\s*a";
        Pattern imgUrlPattern = Pattern.compile(imgUrlPatternStr);
        Pattern passagePattern = Pattern.compile(passagePatternStr);
        Matcher matcher;

        for(int i = 0; i < hashMapList.size(); i++) {
            Map<String, String> cdataMap = new HashMap<String, String>();
            description = hashMapList.get(i).get(DESC_TAG);
            matcher = imgUrlPattern.matcher(description);
            if(matcher.find()) {
                cdataMap.put(IMG_TAG, matcher.group(1));
            }else {
                continue;
            }

            matcher = passagePattern.matcher(description);
            if(matcher.find()) {
                cdataMap.put(CONTENT_TAG, matcher.group(1));
            }

            cdataMap.put(TITLE_TAG, hashMapList.get(i).get(TITLE_TAG));
            cdataMap.put(LINK_TAG, hashMapList.get(i).get(LINK_TAG));

            cdataMapList.add(cdataMap);
        }
        return cdataMapList;
    }

    private void logHashList(List<Map<String, String>> hashMapList) {
        for(int i = 0; i < hashMapList.size(); i++) {
            Log.i("map", i + ": ");
            Iterator<String> iterator = hashMapList.get(i).keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                Log.i(key, hashMapList.get(i).get(key));
            }
        }
    }
}
