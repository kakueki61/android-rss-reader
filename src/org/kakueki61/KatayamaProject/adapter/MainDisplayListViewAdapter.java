package org.kakueki61.KatayamaProject.adapter;

import android.app.Activity;
import android.content.Intent;
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
import org.kakueki61.KatayamaProject.NewsDetailActivity;
import org.kakueki61.KatayamaProject.R;
import org.kakueki61.KatayamaProject.util.volley.LruImageCache;

import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 14/01/20 kodama-t
 */
public class MainDisplayListViewAdapter extends ArrayAdapter<Map<String, String>> {

    private Activity mActivity;
    private List<Map<String, String>> mResponseMapList;
    private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private LayoutInflater mInflater;

    public MainDisplayListViewAdapter(Activity activity, List<Map<String, String>> responseMapList, RequestQueue queue) {
        super(activity, 0, responseMapList);

        this.mActivity = activity;
        this.mResponseMapList = responseMapList;
        this.mQueue = queue;

        mImageLoader = new ImageLoader(queue, new LruImageCache());
        mInflater = LayoutInflater.from(activity);
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

        final String text = mResponseMapList.get(position).get(MainDisplayActivity.TITLE_TAG);
        final String content = mResponseMapList.get(position).get(MainDisplayActivity.CONTENT_TAG);
        final String link = mResponseMapList.get(position).get(MainDisplayActivity.LINK_TAG);
        final String imgUrl = mResponseMapList.get(position).get(MainDisplayActivity.IMG_TAG);
        Log.d("getView: " + position, "text: " + text);
        Log.d("getView: " + position, "content: " + content);
        Log.d("getView: " + position, "link: " + link);
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

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.INTENT_TITLE, text);
                intent.putExtra(NewsDetailActivity.INTENT_CONTENT, content);
                intent.putExtra(NewsDetailActivity.INTENT_LINK, link);
                intent.putExtra(NewsDetailActivity.INTENT_IMG, imgUrl);
                mActivity.startActivity(intent);
            }
        });

        return convertView;
    }
}
