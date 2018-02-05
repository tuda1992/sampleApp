package findlocation.bateam.com.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.adapter.FindLocationAdapter;
import findlocation.bateam.com.adapter.FindLocationFooterAdapter;
import findlocation.bateam.com.base.BaseCustomLayout;
import findlocation.bateam.com.model.PlaceModel;

/**
 * Created by acv on 12/22/17.
 */

public class LayoutPlace extends BaseCustomLayout {

    private LinearLayoutManager mLinearLayoutManager;
    private FindLocationFooterAdapter mAdapter;
    private Context mContext;
    private List<PlaceModel> mListData = new ArrayList<>();
    private EndlessRecyclerViewScrollListener mLazyLoadListener;

    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipe;

    @OnClick(R.id.iv_down)
    public void onClickHideLayout() {
        if (mListener != null) {
            mListener.onHideLayout();
        }
    }

    private LayoutPlaceListener mListener;

    public interface LayoutPlaceListener {
        void onHideLayout();

        void onItemLayoutClick(int position, PlaceModel item);

        void onItemInfoClick(int position, PlaceModel item);

        void onLoadMore();

        void onPullToRefresh();
    }

    public void setLayoutListener(LayoutPlaceListener listener) {
        this.mListener = listener;
    }

    public void setDataForLayoutPlace(List<PlaceModel> listData, boolean isCreated) {
        mListData = listData;
        if (isCreated) {
            mAdapter.notifyDataSetChanged();
            return;
        }

        mAdapter = new FindLocationFooterAdapter(mContext, mListData, new FindLocationFooterAdapter.ICallBackItemClick() {
            @Override
            public void onItemClick(int position, PlaceModel item) {
                if (mListener != null) {
                    mListener.onItemLayoutClick(position, item);
                }
            }

            @Override
            public void onItemInfo(int position, PlaceModel item) {
                if (mListener != null) {
                    mListener.onItemInfoClick(position, item);
                }
            }

            @Override
            public void onLoadMore() {
                if (mListener != null) {
                    mListener.onLoadMore();
                }
            }
        });

        mRvData.setAdapter(mAdapter);
    }

    public LayoutPlace(Context context) {
        super(context);
        this.mContext = context;
    }

    public LayoutPlace(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_place;
    }

    @Override
    protected void initCompoundView() {
    }

    @Override
    protected void initData() {
        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvData.setLayoutManager(mLinearLayoutManager);
        mRvData.setHasFixedSize(true);
    }

    @Override
    protected void initListener() {

        mLazyLoadListener = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page) {
                if (mListener != null) {
                    mListener.onLoadMore();
                }
            }
        };

//        mRvData.addOnScrollListener(mLazyLoadListener);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mLazyLoadListener.resetState();
                if (mListener != null) {
                    mListener.onPullToRefresh();
                }
            }
        });
    }

    public void setRefreshing() {
        mSwipe.setRefreshing(false);
    }

    public void setNotifyAdapter() {
        mAdapter.notifyDataSetChanged();
    }

}
