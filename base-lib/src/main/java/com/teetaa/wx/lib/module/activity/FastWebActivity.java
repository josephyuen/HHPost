package com.teetaa.wx.lib.module.activity;


import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import com.aries.ui.view.title.TitleBarView;
import com.aries.ui.widget.BasisDialog;
import com.aries.ui.widget.action.sheet.UIActionSheetDialog;
import com.just.agentweb.AgentWeb;
import com.teetaa.wx.lib.FastManager;
import com.teetaa.wx.lib.R;
import com.teetaa.wx.lib.i.TitleBarViewControl;
import com.teetaa.wx.lib.util.FastUtil;
import com.teetaa.wx.lib.util.ToastUtil;

/**
 * WangXu ---- 梯台网络
 * Function: App内快速实现WebView功能
 * Description:
 * 1、调整WebView自适应屏幕代码属性{@link #initAgentWeb()}
 */
public abstract class FastWebActivity extends FastTitleActivity {

    protected ViewGroup mContainer;
    protected String url = "";
    protected String mCurrentUrl;
    protected AlertDialog mAlertDialog;
    protected AgentWeb mAgentWeb;
    protected AgentWeb.CommonBuilder mAgentBuilder;
    protected UIActionSheetDialog mActionSheetView;
    private TitleBarViewControl mTitleBarViewControl;

    protected static void start(Activity mActivity, Class<? extends FastWebActivity> activity, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        FastUtil.startActivity(mActivity, activity, bundle);
    }


    protected void setAgentWeb(AgentWeb mAgentWeb) {

    }

    protected void setAgentWeb(AgentWeb.CommonBuilder mAgentBuilder) {

    }

    /**
     * 设置进度条颜色
     *
     * @return
     */
    @ColorInt
    protected int getProgressColor() {
        return -1;
    }

    /**
     * 设置进度条高度 注意此处最终AgentWeb会将其作为float 转dp2px
     *
     * @return
     */
    protected int getProgressHeight() {
        return 2;
    }

    @Override
    public void beforeSetContentView() {
        super.beforeSetContentView();
        mTitleBarViewControl = FastManager.getInstance().getTitleBarViewControl();
    }

    @Override
    public void beforeInitView() {
        mContainer = findViewById(R.id.lLayout_containerFastWeb);
        url = getIntent().getStringExtra("url");
        mCurrentUrl = url;
        initAgentWeb();
        super.beforeInitView();

    }

    @Override
    public void beforeSetTitleBar(TitleBarView titleBar) {
        super.beforeSetTitleBar(titleBar);
        if (mTitleBarViewControl != null) {
            mTitleBarViewControl.createTitleBarViewControl(titleBar, this.getClass());
        }
        titleBar.setOnLeftTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        })
                .setRightTextDrawable(FastUtil.getTintDrawable(
                        getResources().getDrawable(R.drawable.fast_ic_more),
                        getResources().getColor(R.color.white)))
                .setOnRightTextClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showActionSheet();
                    }
                })
                .addLeftAction(titleBar.new ImageAction(
                        FastUtil.getTintDrawable(getResources().getDrawable(R.drawable.fast_ic_close),
                                getResources().getColor(R.color.white)), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog();
                    }
                }));
    }

    @Override
    public int getContentLayout() {
        return R.layout.fast_activity_fast_web;
    }

    protected void initAgentWeb() {
        mAgentBuilder = AgentWeb.with(this)
                .setAgentWebParent(mContainer, new ViewGroup.LayoutParams(-1, -1))
                .useDefaultIndicator(getProgressColor() != -1 ? getProgressColor() : getResources().getColor(R.color.colorTitleText),
                        getProgressHeight())
                .setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        mCurrentUrl = view.getUrl();
                        mTitleBar.setTitleMainText(title);
                    }
                })
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK);
        setAgentWeb(mAgentBuilder);
        mAgentWeb = mAgentBuilder
                .createAgentWeb()//
                .ready()
                .go(url);
        WebSettings webSettings = mAgentWeb.getAgentWebSettings().getWebSettings();
        //设置webView自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        setAgentWeb(mAgentWeb);
    }

    protected void showDialog() {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.fast_web_alert_title)
                    .setMessage(R.string.fast_web_alert_msg)
                    .setNegativeButton(R.string.fast_web_alert_left, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                        }
                    })
                    .setPositiveButton(R.string.fast_web_alert_right, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                            mContext.finish();
                        }
                    }).create();
        }
        mAlertDialog.show();
        //show之后可获取对应Button对象设置文本颜色--show之前获取对象为null
        mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.RED);
    }

    protected void showActionSheet() {
        if (mActionSheetView == null) {
            mActionSheetView = new UIActionSheetDialog.ListSheetBuilder(mContext)
                    .addItems(R.array.fast_arrays_web_more)
                    .setOnItemClickListener(new UIActionSheetDialog.OnItemClickListener() {
                        @Override
                        public void onClick(BasisDialog dialog, View itemView, int i) {
                            switch (i) {
                                case 0:
                                    mAgentWeb.getUrlLoader().reload();
                                    break;
                                case 1:
                                    FastUtil.copyToClipboard(mContext, mCurrentUrl);
                                    ToastUtil.show(R.string.fast_copy_success);
                                    break;
                                case 2:
                                    FastUtil.startShareText(mContext, mCurrentUrl);
                                    break;
                            }
                        }
                    })
                    .setCancel(R.string.fast_cancel)
                    .setTextSizeUnit(TypedValue.COMPLEX_UNIT_DIP)
                    .create();
        }
        mActionSheetView.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mAgentWeb.back()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
