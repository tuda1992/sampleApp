package findlocation.bateam.com.findlocation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import findlocation.bateam.com.R;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.widget.TouchImageView;

/**
 * Created by doanhtu on 3/15/18.
 */

public class FragmentDialogImage extends DialogFragment {

    private TouchImageView mIvData;
    private String mUrl;

    public FragmentDialogImage() {
    }

    public static FragmentDialogImage newInstance(String url) {
        FragmentDialogImage f = new FragmentDialogImage();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("url", url);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme);
        mUrl = getArguments().getString("url", "");
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() == null)
            return;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels * 4 / 5;
        getDialog().getWindow().setLayout(width, height);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_image, container);
        mIvData = (TouchImageView) view.findViewById(R.id.iv_data);

        if (!TextUtils.isEmpty(mUrl)) {
            Glide.with(getActivity()).load(Constants.BASE_IMAGE + mUrl.replace(" ", "")).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<? super Drawable> transition) {
                    mIvData.setImageDrawable(drawable);
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                }
            });
        }

        return view;
    }
}
