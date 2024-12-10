package ipca.examples.dailynews.repositrories

import android.content.Context
import ipca.examples.dailynews.database.AppDatabase
import ipca.examples.dailynews.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val context: Context,
    private val articleApi: ArticlesAPI
) {

    fun fetchArticles (path : String) : Flow<ResultWrapper<List<Article>>>  =
        flow{
            emit(ResultWrapper.Loading())
            try {
                val articles = articleApi.fetchArticle(path)
                emit(ResultWrapper.Success(articles))
            }catch (e : IOException){
                emit(ResultWrapper.Error(e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)

    fun fetchArticlesFromDb () :  Flow<ResultWrapper<List<Article>>> =
        flow{
            val articles = AppDatabase.getInstance(context)
                ?.articleDao()
                ?.getAll()
            emit(ResultWrapper.Success(articles?: emptyList()))
        }.flowOn(Dispatchers.IO)
}