package findlocation.bateam.com.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import findlocation.bateam.com.R;
import findlocation.bateam.com.constant.Constants;

/**
 * Created by doanhtu on 3/14/18.
 */

public class UltraPagerAdapter extends PagerAdapter {

    private boolean isMultiScr;
    private ItemClickCallBackListener mListener;
    private List<String> mListUrl;
    private Context mContext;

    public interface ItemClickCallBackListener {
        void onItemClick(String url);
    }

    public UltraPagerAdapter(Context context, boolean isMultiScr, List<String> listUrl, ItemClickCallBackListener listener) {
        this.isMultiScr = isMultiScr;
        this.mListener = listener;
        this.mListUrl = listUrl;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mListUrl != null ? mListUrl.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
//        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_view_pager, null);
//        ImageView ivData = (ImageView) linearLayout.findViewById(R.id.iv_data_location);
//
//
//        Glide.with(mContext).load(Constants.BASE_IMAGE + mListUrl.get(position)).into(ivData);
//
//        linearLayout.setId(R.id.item_id);
//
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mListener != null) {
//                    mListener.onItemClick("" + mListUrl.get(position));
//                }
//            }
//        });
//
//        container.addView(linearLayout);
//
//        return linearLayout;

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view_pager, container, false);
        ImageView ivData = (ImageView) view.findViewById(R.id.iv_data_location);

//        switch (position) {
//            case 0:
//                linearLayout.setBackgroundColor(Color.parseColor("#2196F3"));
//                break;
//            case 1:
//                linearLayout.setBackgroundColor(Color.parseColor("#673AB7"));
//                break;
//            case 2:
//                linearLayout.setBackgroundColor(Color.parseColor("#009688"));
//                break;
//            case 3:
//                linearLayout.setBackgroundColor(Color.parseColor("#607D8B"));
//                break;
//            case 4:
//                linearLayout.setBackgroundColor(Color.parseColor("#F44336"));
//                break;
//        }

        Glide.with(mContext).load(Constants.BASE_IMAGE + mListUrl.get(position)).apply(new RequestOptions().override(400, 400)).into(ivData);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick("" + mListUrl.get(position));
                }
            }
        });
        container.addView(view);
        return view;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
