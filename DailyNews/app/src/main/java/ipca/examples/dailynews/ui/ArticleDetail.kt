package ipca.examples.dailynews.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import ipca.examples.dailynews.models.Article


@Composable
fun ArticleDetail(modifier: Modifier, article: Article) {
    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
            }
        },
            update = { webView ->
                webView.loadUrl(article.url!!)
            })
    }
}

@Preview
@Composable
fun ArticleDetailPreview() {
    ArticleDetail(modifier = Modifier,
        article = Article(
            url = "http://google.com"
        ))
}