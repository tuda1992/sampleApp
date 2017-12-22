package findlocation.bateam.com.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;

/**
 * Created by acv on 11/29/17.
 */

public abstract class BaseCustomLayout extends RelativeLayout {

    public BaseCustomLayout(Context context) {
        super(context);
        setLayout();
        ButterKnife.bind(this);
        initCompoundView();
        initData();
        initListener();
    }

    public BaseCustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        setLayout();
        ButterKnife.bind(this);
        initCompoundView();
        initData();
        initListener();
    }

    protected int[] getStyleableId() {
        return null;
    }

    protected void initDataFromStyleable(TypedArray a) {
    }

    protected abstract int getLayoutId();

    protected abstract void initCompoundView();

    protected abstract void initData();

    protected abstract void initListener();

    private void init(AttributeSet attrs) {
        if (getStyleableId() != null && getStyleableId().length > 0) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, getStyleableId(), 0, 0);
            initDataFromStyleable(a);
            a.recycle();
        }
    }

    private void setLayout() {
        if (getLayoutId() != 0) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            inflater.inflate(getLayoutId(), this, true);
        }
    }

}
