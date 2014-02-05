package org.kakueki61.KatayamaProject.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for manipulating several kinds of data.
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 14/02/06 kodama-t
 */
public class DataHandleHelper {

    public final static String TITLE_TAG = "title";
    public final static String DESC_TAG = "description";
    public final static String IMG_TAG = "img";
    public final static String CONTENT_TAG = "content";
    public final static String LINK_TAG = "link";
    public final static String GUID_TAG = "guid";

    public static List<Map<String, String>> extractContentsFromCDATA(List<Map<String, String>> hashMapList) {
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

            cdataMap.put(DESC_TAG, description);
            cdataMap.put(TITLE_TAG, hashMapList.get(i).get(TITLE_TAG));
            cdataMap.put(LINK_TAG, hashMapList.get(i).get(LINK_TAG));

            cdataMapList.add(cdataMap);
        }
        return cdataMapList;
    }

}
