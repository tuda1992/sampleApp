package findlocation.bateam.com.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.model.PlaceModel;

/**
 * Created by doanhtu on 2/5/18.
 */

public class FindLocationFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private Context mContext;
    private ICallBackItemClick mListener;
    private List<PlaceModel> mListData;

    public interface ICallBackItemClick {
        void onItemClick(int position, PlaceModel item);

        void onItemInfo(int position, PlaceModel item);

        void onLoadMore();
    }

    public FindLocationFooterAdapter(Context context, List<PlaceModel> listData, ICallBackItemClick listener) {
        this.mContext = context;
        this.mListData = listData;
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false);
            return new FooterViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
            return new GenericViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
        } else if (holder instanceof GenericViewHolder) {
            GenericViewHolder genericViewHolder = (GenericViewHolder) holder;
            genericViewHolder.setData(mListData.get(position));
        }
    }


    //    need to override this method
    @Override
    public int getItemViewType(int position) {
//        if (isPositionFooter(position)) {
//            return TYPE_FOOTER;
//        }
        return TYPE_ITEM;
    }

    private boolean isPositionFooter(int position) {
        return position == mListData.size();
    }


    @Override
    public int getItemCount() {
        return mListData.size();
    }


    class FooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_load_more)
        CardView mCvLoadMore;

        @OnClick(R.id.cv_load_more)
        public void onClickLoadMore() {
            if (mListener != null) {
                mCvLoadMore.setVisibility(View.GONE);
                mListener.onLoadMore();
            }
        }

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class GenericViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_data)
        ImageView mIvLocation;
        @BindView(R.id.tv_data)
        TextView mTvData;

        @OnClick(R.id.cv_data)
        public void onClickItem() {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition(), mListData.get(getAdapterPosition()));
            }
        }

        @OnClick(R.id.iv_info)
        public void onClicKInfo() {
            if (mListener != null) {
                mListener.onItemInfo(getAdapterPosition(), mListData.get(getAdapterPosition()));
            }
        }

        public GenericViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setData(PlaceModel model) {
            mTvData.setText(model.street);
        }

    }
}
