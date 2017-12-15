package findlocation.bateam.com.navigation;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import findlocation.bateam.com.R;

/**
 * Created by acv on 12/4/17.
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {

    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.tvTitle.setText(current.getTitle());
        if (getItemCount() == 4) {
            switch (position) {
                case 0:
                    holder.ivIcon.setImageResource(R.drawable.ic_find_location);
                    break;
                case 1:
                    holder.ivIcon.setImageResource(R.drawable.ic_find_job);
                    break;
                case 2:
                    holder.ivIcon.setImageResource(R.drawable.ic_user);
                    break;
                case 3:
                    holder.ivIcon.setImageResource(R.drawable.ic_logout);
                    break;

            }
        } else {
            switch (position) {
                case 0:
                    holder.ivIcon.setImageResource(R.drawable.ic_find_location);
                    break;
                case 1:
                    holder.ivIcon.setImageResource(R.drawable.ic_user);
                    break;
                case 2:
                    holder.ivIcon.setImageResource(R.drawable.ic_logout);
                    break;

            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }
}
