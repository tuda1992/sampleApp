package findlocation.bateam.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import findlocation.bateam.com.R;

/**
 * Created by acv on 12/13/17.
 */

public class FindLocationAdapter extends RecyclerView.Adapter<FindLocationAdapter.ViewHolder> {

    private Context mContext;
    private ICallBackItemClick mListener;

    public FindLocationAdapter(Context context, ICallBackItemClick listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    public interface ICallBackItemClick {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_location)
        ImageView mIvLocation;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void setData() {
        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData();
    }

}