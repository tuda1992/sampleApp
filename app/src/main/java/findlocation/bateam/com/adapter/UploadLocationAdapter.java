package findlocation.bateam.com.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import findlocation.bateam.com.R;

/**
 * Created by acv on 12/14/17.
 */

public class UploadLocationAdapter extends RecyclerView.Adapter<UploadLocationAdapter.ViewHolder> {

    private Context mContext;
    private ICallBackItemClick mListener;
    private List<Bitmap> mListData;

    public UploadLocationAdapter(Context context, List<Bitmap> listData, ICallBackItemClick listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mListData = listData;
    }

    public interface ICallBackItemClick {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_location)
        ImageView mIvLocation;
        @BindView(R.id.card_view)
        CardView mCardView;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(display.getWidth() / 5, ViewGroup.LayoutParams.WRAP_CONTENT);
            mCardView.setLayoutParams(layoutParams);

        }

        public void setData(Bitmap bitmap) {
            mIvLocation.setImageBitmap(bitmap);
        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_upload_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mListData.size() == 0) {
            holder.setData(null);
        } else if (mListData.size() == 5) {
            holder.setData(mListData.get(position));
        } else {
            if (position < mListData.size()) {
                holder.setData(mListData.get(position));
            } else {
                holder.setData(null);
            }
        }
    }

}
