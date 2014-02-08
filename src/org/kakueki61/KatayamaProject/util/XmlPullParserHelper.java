package org.kakueki61.KatayamaProject.util;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class to manipulate xml.
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 14/02/06 kodama-t
 */
public class XmlPullParserHelper {

    public static List<Map<String, String>> inputStreamToHashList(InputStream inputStream) {
        List<Map<String, String>> responseHolder = new ArrayList<Map<String, String>>();
        Map<String, String> itemMap = new HashMap<String, String>();
        XmlPullParser parser;
        try {
            parser = new KXmlParser();
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
