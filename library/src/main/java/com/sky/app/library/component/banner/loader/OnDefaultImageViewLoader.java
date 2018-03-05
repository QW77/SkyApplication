package com.sky.app.library.component.banner.loader;

import android.content.Context;
import android.widget.ImageView;
import com.sky.app.library.component.banner.listener.OnLoadImageViewListener;

/**
 * Default ImageViewLoader
 *
 * @author Edwin.Wu
 * @version 2016/12/6 14:42
 * @since JDK1.8
 */
public abstract class OnDefaultImageViewLoader implements OnLoadImageViewListener {

    @Override
    public ImageView createImageView(Context context) {
        return new ImageView(context);
    }
}
