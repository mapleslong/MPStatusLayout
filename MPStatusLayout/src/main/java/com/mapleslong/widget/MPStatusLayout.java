package com.mapleslong.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mapleslong.widget.statuslayout.R;

/**
 * 创建时间: 2017/1/4
 * 描述: 状态布局
 *
 * @author 枫叶
 * @version 1.0
 */
public class MPStatusLayout extends FrameLayout {
  /**
   * 状态集合
   */
  public static final int STATUS_SUCCESS = 0;
  public static final int STATUS_EMPTY = 1;
  public static final int STATUS_ERROR = 2;
  public static final int STATUS_NONETWORK = 3;
  public static final int STATUS_LOADING = 4;

  //当前状态
  private int mStatus;
  private Context mContext;
  //子布局--内容布局
  private View mContentView;
  private View mLoadingView;
  private View mErrorView;
  private View mEmptyView;
  private View mNoNetworkView;

  private static String strEmpty;
  private static String strError;
  private static String strNoNetwork;
  private static String strReload;
  private static String strLoading;

  /**
   * Empty空布局控件
   */
  private TextView tvEmpty;
  private ImageView imgEmpty;
  /**
   * Error错误布局控件
   */
  private ImageView imgError;
  private TextView tvError;
  private TextView tvBtnErrorReload;
  /**
   * Loading加载布局控件
   */
  private ImageView imgLoading;
  private TextView tvLoading;
  private ProgressBar progressBar;

  /**
   * NoNetwork无网络布局控件
   */
  private ImageView imgNoNetwork;
  private TextView tvNoNetwork;
  private TextView tvBtnNoNetwork;

  /**
   * 配置参数集合
   */
  private static int textTipSize = 12;
  private static int textTipColor = R.color.black;
  private static int textReloadColor = R.color.black;
  private static int textReloadSize = 12;
  private static int textReloadWidth = 150;
  private static int textReloadHeight = 40;

  private static int emptyImgResId = R.mipmap.status_empty;
  private static int errorImgResId = R.mipmap.status_error;
  private static int nonetworkImgResId = R.mipmap.status_nonetwork;
  private static int loadingImgResId = 0;
  private static int reloadBackgroundResId;

  private onReloadListener onReloadListener;

  public MPStatusLayout(Context context) {
    this(context, null);
  }

  public MPStatusLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MPStatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.mContext = context;
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    if (getChildCount() > 1) {
      throw new IllegalStateException("StatusLayout can host only one direct child");
    }
    mContentView = this.getChildAt(0);
    initViews();
  }

  private void initViews() {
    strEmpty = TextUtils.isEmpty(strEmpty) ? mContext.getString(R.string.status_empty) : strEmpty;
    strError = TextUtils.isEmpty(strError) ? mContext.getString(R.string.status_loadfail) : strError;
    strNoNetwork = TextUtils.isEmpty(strNoNetwork) ? mContext.getString(R.string.status_nonetwork) : strNoNetwork;
    strLoading = TextUtils.isEmpty(strLoading) ? mContext.getString(R.string.status_loading) : strLoading;
    strReload = TextUtils.isEmpty(strReload) ? mContext.getString(R.string.status_reload) : strReload;
    mEmptyView = LayoutInflater.from(mContext).inflate(R.layout.view_statuslayout_empty, null);
    mErrorView = LayoutInflater.from(mContext).inflate(R.layout.view_statuslayout_error, null);
    mLoadingView = LayoutInflater.from(mContext).inflate(R.layout.view_statuslayout_loading, null);
    mNoNetworkView = LayoutInflater.from(mContext).inflate(R.layout.view_statuslayout_nonetwork, null);
    ///空布局
    tvEmpty = (TextView) mEmptyView.findViewById(R.id.tv_empty);
    imgEmpty = (ImageView) mEmptyView.findViewById(R.id.img_empty);
    //错误布局
    tvError = (TextView) mErrorView.findViewById(R.id.tv_error);
    imgError = (ImageView) mErrorView.findViewById(R.id.img_error);
    tvBtnErrorReload = (TextView) mErrorView.findViewById(R.id.btn_error_reload);
    tvBtnErrorReload.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (onReloadListener != null) {
          onReloadListener.onReLoad(v);
        }
      }
    });
    //加载布局
    imgLoading = (ImageView) mLoadingView.findViewById(R.id.img_loading);
    tvLoading = (TextView) mLoadingView.findViewById(R.id.tv_loading);
    progressBar = (ProgressBar) mLoadingView.findViewById(R.id.pb_loading);
    //无网络布局
    imgNoNetwork = (ImageView) mNoNetworkView.findViewById(R.id.img_no_network);
    tvNoNetwork = (TextView) mNoNetworkView.findViewById(R.id.tv_no_network);
    tvBtnNoNetwork = (TextView) mNoNetworkView.findViewById(R.id.btn_no_network);
    tvBtnNoNetwork.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (onReloadListener != null) {
          onReloadListener.onReLoad(v);
        }
      }
    });

    /**
     * 初始化参数
     */
    //空
    imgEmpty.setImageResource(emptyImgResId);
    tvEmpty.setText(strEmpty);
    tvEmpty.setTextColor(ContextCompat.getColor(getContext(), textTipColor));
    tvEmpty.setTextSize(textTipSize);
    //错误
    tvError.setText(strError);
    tvError.setTextColor(ContextCompat.getColor(getContext(), textTipColor));
    tvError.setTextSize(textTipSize);
    imgError.setImageResource(errorImgResId);
    tvBtnErrorReload.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textReloadWidth,
        mContext.getResources().getDisplayMetrics()));
    tvBtnErrorReload.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textReloadHeight,
        mContext.getResources().getDisplayMetrics()));
    tvBtnErrorReload.setText(strReload);
    tvBtnErrorReload.setTextSize(textReloadSize);
    tvBtnErrorReload.setTextColor(ContextCompat.getColor(getContext(), textTipColor));
    tvBtnErrorReload.setBackgroundResource(reloadBackgroundResId);
    //加载
    progressBar.setVisibility(loadingImgResId == 0 ? VISIBLE : GONE);
    imgLoading.setVisibility(loadingImgResId == 0 ? GONE : VISIBLE);
    imgLoading.setImageResource(loadingImgResId);
    AnimationDrawable animationDrawable = (AnimationDrawable) imgLoading.getDrawable();
    if (animationDrawable != null) {
      animationDrawable.start();
    }
    tvLoading.setText(strLoading);
    tvLoading.setTextColor(ContextCompat.getColor(getContext(), textTipColor));
    tvLoading.setTextSize(textTipSize);
    //无网络
    tvNoNetwork.setText(strNoNetwork);
    tvNoNetwork.setTextSize(textTipSize);
    tvNoNetwork.setTextColor(ContextCompat.getColor(getContext(), textTipColor));
    imgNoNetwork.setImageResource(nonetworkImgResId);
    tvBtnNoNetwork.setText(strReload);
    tvBtnNoNetwork.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textReloadWidth,
        mContext.getResources().getDisplayMetrics()));
    tvBtnNoNetwork.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textReloadHeight,
        mContext.getResources().getDisplayMetrics()));
    tvBtnNoNetwork.setText(strReload);
    tvBtnNoNetwork.setTextSize(textReloadSize);
    tvBtnNoNetwork.setTextColor(ContextCompat.getColor(getContext(), textReloadColor));
    tvBtnNoNetwork.setBackgroundResource(reloadBackgroundResId);
    this.addView(mEmptyView);
    this.addView(mErrorView);
    this.addView(mNoNetworkView);
    this.addView(mLoadingView);
  }

  public void setStatus(int status) {
    setStatus(status, null);
  }

  public void setStatus(int status, @Nullable final onReloadListener onReloadListener) {
    mContentView.setVisibility(GONE);
    mEmptyView.setVisibility(GONE);
    mErrorView.setVisibility(GONE);
    mNoNetworkView.setVisibility(GONE);
    mLoadingView.setVisibility(GONE);
    this.mStatus = status;
    switch (mStatus) {
      case STATUS_SUCCESS:
        mContentView.setVisibility(VISIBLE);
        break;
      case STATUS_EMPTY:
        mEmptyView.setVisibility(VISIBLE);
        break;
      case STATUS_ERROR:
        mErrorView.setVisibility(VISIBLE);
        tvBtnErrorReload.setOnClickListener(new OnClickListener() {
          @Override public void onClick(View v) {
            if (onReloadListener != null) {
              onReloadListener.onReLoad(v);
            }
          }
        });
        break;
      case STATUS_NONETWORK:
        mNoNetworkView.setVisibility(VISIBLE);
        tvBtnNoNetwork.setOnClickListener(new OnClickListener() {
          @Override public void onClick(View v) {
            if (onReloadListener != null) {
              onReloadListener.onReLoad(v);
            }
          }
        });
        break;
      case STATUS_LOADING:
        mLoadingView.setVisibility(VISIBLE);
        break;
      default:
        mContentView.setVisibility(VISIBLE);
        break;
    }
  }

  public void setErrorText(String str) {
    tvError.setText(str);
  }

  public void setEmptyText(String str) {
    tvEmpty.setText(str);
  }

  public void setStatusNonetwork(String str) {
    tvBtnNoNetwork.setText(str);
  }

  public void setLoadingText(String str) {
    tvLoading.setText(str);
  }

  public interface onReloadListener {
    void onReLoad(View v);
  }

  public static class MPStatusLayoutConfig {
    public MPStatusLayoutConfig setErrorTitle(@NonNull String title) {
      strError = title;
      return this;
    }

    public MPStatusLayoutConfig setEmptyTitle(@NonNull String title) {
      strEmpty = title;
      return this;
    }

    public MPStatusLayoutConfig setNoNetworkTitle(@NonNull String title) {
      strNoNetwork = title;
      return this;
    }

    public MPStatusLayoutConfig setLoadingTitle(@NonNull String title) {
      strLoading = title;
      return this;
    }

    public MPStatusLayoutConfig setReloadButtonText(@NonNull String text) {
      strReload = text;
      return this;
    }

    public MPStatusLayoutConfig setTextTipSize(int sp) {
      textTipSize = sp;
      return this;
    }

    public MPStatusLayoutConfig setTextTipColor(@ColorRes int colorRes) {
      textTipColor = colorRes;
      return this;
    }

    public MPStatusLayoutConfig setReloadTextSize(int sp) {
      textReloadSize = sp;
      return this;
    }

    public MPStatusLayoutConfig setReloadTextColor(@ColorRes int colorRes) {
      textReloadColor = colorRes;
      return this;
    }

    public MPStatusLayoutConfig setReloadWidthAndHeight(int width, int height) {
      textReloadWidth = width;
      textReloadHeight = height;
      return this;
    }

    public MPStatusLayoutConfig setEmptyImage(@DrawableRes int resid) {
      emptyImgResId = resid;
      return this;
    }

    public MPStatusLayoutConfig setErrorImage(@DrawableRes int resid) {
      errorImgResId = resid;
      return this;
    }

    public MPStatusLayoutConfig setNoNetworkImage(@DrawableRes int resid) {
      nonetworkImgResId = resid;
      return this;
    }

    public MPStatusLayoutConfig setLoadingImage(@DrawableRes int resid) {
      loadingImgResId = resid;
      return this;
    }

    public MPStatusLayoutConfig setReloadBackground(@DrawableRes int resid) {
      reloadBackgroundResId = resid;
      return this;
    }
  }

  public void setOnReloadListener(MPStatusLayout.onReloadListener onReloadListener) {
    this.onReloadListener = onReloadListener;
  }
}
