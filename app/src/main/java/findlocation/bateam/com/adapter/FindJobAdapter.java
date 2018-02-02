package findlocation.bateam.com.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.model.JobModel;

/**
 * Created by doanhtu on 1/12/18.
 */

public class FindJobAdapter extends RecyclerView.Adapter<FindJobAdapter.ViewHolder> {

    private Context mContext;
    private ICallBackItemClick mListener;
    private List<JobModel> mListData;

    public FindJobAdapter(Context context, List<JobModel> listData, ICallBackItemClick listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mListData = listData;
    }

    public interface ICallBackItemClick {
        void onItemClick(int position, JobModel item);
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
        @BindView(R.id.tv_salary)
        TextView mTvSalary;
        @BindView(R.id.tv_company_name)
        TextView mTvCompany;
        @BindView(R.id.tv_location)
        TextView mTvLocation;
        @BindView(R.id.cv_data)
        CardView mCvData;
        @BindView(R.id.rl_salary)
        RelativeLayout mRlSalary;

        @OnClick(R.id.cv_data)
        public void onClickItem() {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition(), mListData.get(getAdapterPosition()));
            }
        }

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void setData(JobModel model) {
            mTvData.setText(model.jobTitle);
            mTvCompany.setText(model.companyName);
            if (TextUtils.isEmpty(model.salary) || model.salary.contains("NULL")) {
                mRlSalary.setVisibility(View.INVISIBLE);
            } else {
                mRlSalary.setVisibility(View.VISIBLE);
                mTvSalary.setText(model.salary);
            }
            mTvLocation.setText(model.workingArea);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_job, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mListData.get(position));
    }

}
