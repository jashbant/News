package grab.com.application.src.di.module;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.grab.app.R;

import javax.inject.Inject;

import butterknife.BindView;
import grab.com.application.src.base.BaseFragment;



public class DetailsFragment extends BaseFragment {

    @BindView(R.id.webview_article)
    WebView webViewArticle;


    @BindView(R.id.loading_view_webview)
    ProgressBar progressBar;
  public static DetailsFragment newInstance(String url){
      DetailsFragment fragment = new DetailsFragment();
      Bundle bundle = new Bundle();
      bundle.putString("url",url);
      fragment.setArguments(bundle);
      return  fragment;

  }

    @Inject
    ViewModelFactory viewModelFactory;


    @Override
    protected int layoutRes() {
        return R.layout.screen_details;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

      String url= (String) getArguments().get("url");
        webViewArticle.loadUrl(url);

        WebViewClient webViewClient = new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(progressBar!=null)
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        };
        webViewArticle.setWebViewClient(webViewClient);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }


}