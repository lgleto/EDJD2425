package ipca.examples.dailynews.repositrories

import ipca.examples.dailynews.models.Article
import ipca.examples.dailynews.ui.home.ArticlesState
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.jvm.Throws

const val BASE_API = "https://newsapi.org/v2/"
const val API_KEY = "&apiKey=1765f87e4ebc40229e80fd0f75b6416c"

object ArticlesAPI {

    val client = OkHttpClient()

    //top-headlines?country=us
    @Throws(IOException::class)
    suspend fun fetchArticle(path : String) : List<Article> {

        val request = Request.Builder()
            .url("$BASE_API$path$API_KEY")
            .build()

        val resultRequest = client.newCall(request).await()

        if (!resultRequest.isSuccessful) throw IOException("Unexpected code ${resultRequest.networkResponse}")

        val articlesResult = arrayListOf<Article>()
        val result = resultRequest.body!!.string()
        val jsonResult = JSONObject(result)
        val status = jsonResult.getString("status")
        if (status == "ok") {
            val articlesJson = jsonResult.getJSONArray("articles")
            for (index in 0 until articlesJson.length()) {
                val articleJson = articlesJson.getJSONObject(index)
                val article = Article.fromJson(articleJson)
                articlesResult.add(article)
            }
        }

        return articlesResult
    }
}

suspend fun Call.await(recordStack: Boolean = false): Response {
    val callStack = if (recordStack) {
        IOException().apply {
            // Remove unnecessary lines from stacktrace
            // This doesn't remove await$default, but better than nothing
            stackTrace = stackTrace.copyOfRange(1, stackTrace.size)
        }
    } else {
        null
    }
    return suspendCancellableCoroutine { continuation ->
        enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                continuation.resume(response)
            }

            override fun onFailure(call: Call, e: IOException) {
                // Don't bother with resuming the continuation if it is already cancelled.
                if (continuation.isCancelled) return
                callStack?.initCause(e)
                continuation.resumeWithException(callStack ?: e)
            }
        })

        continuation.invokeOnCancellation {
            try {
                cancel()
            } catch (ex: Throwable) {
                //Ignore cancel exception
            }
        }
    }
}
