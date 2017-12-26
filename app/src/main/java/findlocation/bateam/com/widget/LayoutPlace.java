package findlocation.bateam.com.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.adapter.FindLocationAdapter;
import findlocation.bateam.com.base.BaseCustomLayout;
import findlocation.bateam.com.model.PlaceModel;

/**
 * Created by acv on 12/22/17.
 */

public class LayoutPlace extends BaseCustomLayout {

    private LinearLayoutManager mLinearLayoutManager;
    private FindLocationAdapter mAdapter;
    private Context mContext;

    @BindView(R.id.rv_data)
    RecyclerView mRvData;

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
    }

    public void setLayoutListener(LayoutPlaceListener listener) {
        this.mListener = listener;
    }

    public void setDataForLayoutPlace(List<PlaceModel> listData) {
        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mAdapter = new FindLocationAdapter(mContext, listData, new FindLocationAdapter.ICallBackItemClick() {
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
        });
        mRvData.setLayoutManager(mLinearLayoutManager);
        mRvData.setHasFixedSize(true);
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
    }

    @Override
    protected void initListener() {

    }
}
