package findlocation.bateam.com.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import findlocation.bateam.com.R;
import findlocation.bateam.com.base.BaseCustomLayout;

/**
 * Created by doanhtu on 3/13/18.
 */

public class ImageLoading extends BaseCustomLayout {

    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.pb_avatar)
    ProgressBar mPbAvatar;

    public ImageLoading(Context context) {
        super(context);
    }

    public ImageLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_image_loading;
    }

    @Override
    protected void initCompoundView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    public void loadUrl(String url) {
        Glide.with(this).load(url).apply(new RequestOptions().error(R.drawable.ic_logo)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                mPbAvatar.setVisibility(GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                mPbAvatar.setVisibility(GONE);
                return false;
            }
        }).into(mIvAvatar);
    }

    public void loadBitmap(final Bitmap bm){
        mPbAvatar.setVisibility(GONE);
        mIvAvatar.post(new Runnable() {
            @Override
            public void run() {
                mIvAvatar.setImageBitmap(bm);
            }
        });
    }

}
