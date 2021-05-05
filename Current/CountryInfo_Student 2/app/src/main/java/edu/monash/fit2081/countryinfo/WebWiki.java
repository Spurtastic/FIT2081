package edu.monash.fit2081.countryinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebWiki extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_wiki);
        Bundle extras = getIntent().getExtras();
        String WikiUrl = extras.getString("key1");
        WebView wikiView = findViewById(R.id.wiki_webView);
        wikiView.setWebViewClient(new WebViewClient());
        wikiView.loadUrl(WikiUrl);
        extras.clear();
    }
}
