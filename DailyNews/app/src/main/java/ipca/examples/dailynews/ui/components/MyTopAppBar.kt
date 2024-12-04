package ipca.examples.dailynews.ui.components

import android.provider.Settings.Global
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.examples.dailynews.database.AppDatabase
import ipca.examples.dailynews.models.Article
import ipca.examples.dailynews.ui.theme.DailyNewsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    navController: NavController,
    title : String,
    isBaseScreen : Boolean,
    article: Article?
){
    var context = LocalContext.current
    TopAppBar(title = {
        if (article != null){
            Text(article.title!!,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis

            )
        }else {
            Text(title)
        }
    },
        navigationIcon = {
            if (!isBaseScreen) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (!isBaseScreen){
                IconButton(
                    onClick = {
                        // TODO: share article

                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share"
                    )
                }
                IconButton(
                    onClick = {
                        // TODO: toggle favorites
                        GlobalScope.launch(Dispatchers.IO) {
                            AppDatabase.getInstance(context)
                                ?.articleDao()
                                ?.insert(article!!)
                        }

                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorites"
                    )
                }
            }
        }
        
    )
}

@Preview(showBackground = true)
@Composable
fun MyTopBarPreview(){
    DailyNewsTheme {
        MyTopAppBar(
            navController = rememberNavController(),
            "Test Title",
            isBaseScreen = false,
            article = Article(
                title = "news title",
                url = ""

            ))
    }
}