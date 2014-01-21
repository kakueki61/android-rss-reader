package org.kakueki61.KatayamaProject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import org.kakueki61.KatayamaProject.MainDisplayActivity;
import org.kakueki61.KatayamaProject.R;
import org.kakueki61.KatayamaProject.util.volley.LruImageCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 14/01/20 kodama-t
 */
public class MainDisplayListViewAdapter extends ArrayAdapter<Map<String, String>> {

    private List<Map<String, String>> mResponseMapList;
    private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private LayoutInflater mInflater;

    public MainDisplayListViewAdapter(Context context, List<Map<String, String>> responseMapList, RequestQueue queue) {
        super(context, 0, responseMapList);

        this.mResponseMapList = responseMapList;
        this.mQueue = queue;

        mImageLoader = new ImageLoader(queue, new LruImageCache());
        mInflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.main_listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String text = mResponseMapList.get(position).get(MainDisplayActivity.TITLE_TAG);
        String imgUrl = mResponseMapList.get(position).get(MainDisplayActivity.IMG_TAG);
        Log.d("getView: " + position, "text: " + text);
        Log.d("getView: " + position, "imgUrl: " + imgUrl);

        if(imgUrl == null) {
            return null;
        }

        ImageLoader.ImageListener imageListener
                = ImageLoader.getImageListener(viewHolder.imageView,
                android.R.drawable.spinner_background,
                android.R.drawable.ic_dialog_alert);
        mImageLoader.get(imgUrl, imageListener);

        viewHolder.textView.setText(text);
        return convertView;
    }
}
