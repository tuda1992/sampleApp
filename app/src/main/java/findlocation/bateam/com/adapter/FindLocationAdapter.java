package findlocation.bateam.com.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.model.PlaceModel;

/**
 * Created by acv on 12/13/17.
 */

public class FindLocationAdapter extends RecyclerView.Adapter<FindLocationAdapter.ViewHolder> {

    private Context mContext;
    private ICallBackItemClick mListener;
    private List<PlaceModel> mListData;

    public FindLocationAdapter(Context context, List<PlaceModel> listData, ICallBackItemClick listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mListData = listData;
    }

    public interface ICallBackItemClick {
        void onItemClick(int position, PlaceModel item);
        void onItemInfo(int position, PlaceModel item);
    }

    @Override
    public int getItemCount() {
        return mListData != null ? mListData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_data)
        ImageView mIvLocation;
        @BindView(R.id.tv_data)
        TextView mTvData;
        @BindView(R.id.cv_data)
        CardView mCvData;

        @OnClick(R.id.cv_data)
        public void onClickItem() {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition(), mListData.get(getAdapterPosition()));
            }
        }

        @OnClick(R.id.iv_info)
        public void onClicKInfo(){
            if (mListener != null) {
                mListener.onItemInfo(getAdapterPosition(), mListData.get(getAdapterPosition()));
            }
        }

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void setData(PlaceModel model) {
            mTvData.setText(model.addressDetail);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mListData.get(position));
    }

}