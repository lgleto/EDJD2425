package ipca.examples.dailynews.ui.favorites

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ipca.examples.dailynews.database.AppDatabase
import ipca.examples.dailynews.models.Article
import ipca.examples.dailynews.repositrories.ArticleRepository
import ipca.examples.dailynews.repositrories.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

data class ArticlesState (
    val articles: List<Article> = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class  FavotritesViewModel @Inject constructor(
    val articleRepository: ArticleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArticlesState())
    val uiState : StateFlow<ArticlesState> = _uiState.asStateFlow()

    fun fetchArticles() {
        articleRepository
            .fetchArticlesFromDb()
            .onEach { result ->
                when(result) {
                    is ResultWrapper.Success ->{
                        _uiState.value = ArticlesState(
                            articles = result.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }
                    is ResultWrapper.Error ->{
                        _uiState.value = ArticlesState(
                            isLoading = false,
                            error = result.message
                        )
                    }
                    is ResultWrapper.Loading ->{
                        _uiState.value = ArticlesState(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

}