package org.kakueki61.KatayamaProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import org.kakueki61.KatayamaProject.util.volley.LruImageCache;

/**
 *
 * @author <a href="mailto:">TakuyaKodama</a> (kodama-t)
 * @version 1.00 14/01/24 kodama-t
 */
public class NewsDetailActivity extends Activity {

    private final String TAG = this.getClass().getName();

    public final static String INTENT_TITLE = "title";
    public final static String INTENT_DESC = "desc";
    public final static String INTENT_CONTENT = "content";
    public final static String INTENT_LINK = "link";
    public final static String INTENT_IMG = "img";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra(INTENT_TITLE);
        String content = intent.getStringExtra(INTENT_CONTENT);
        String link = intent.getStringExtra(INTENT_LINK);
        String imgUrl = intent.getStringExtra(INTENT_IMG);
        Log.d(TAG, "title: " + title);
        Log.d(TAG, "desc: " + content);
        Log.d(TAG, "link: " + link);
        Log.d(TAG, "img: " + imgUrl);

        ImageView imageView = (ImageView) findViewById(R.id.imageview);
        ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(getApplicationContext()), new LruImageCache());
        TextView titleView = (TextView) findViewById(R.id.title_text);
        TextView descView = (TextView) findViewById(R.id.desc_text);

        ImageLoader.ImageListener imageListener
                = ImageLoader.getImageListener(imageView,
                android.R.drawable.spinner_background,
                android.R.drawable.ic_dialog_alert);
        imageLoader.get(imgUrl, imageListener);

        titleView.setText(title);
        descView.setText(content);
    }

}
