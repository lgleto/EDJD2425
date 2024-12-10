package ipca.examples.dailynews.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ipca.examples.dailynews.R
import ipca.examples.dailynews.models.Article
import ipca.examples.dailynews.toYYYYMMDD
import ipca.examples.dailynews.ui.theme.DailyNewsTheme
import java.util.Date

@Composable
fun RowArticle(modifier: Modifier = Modifier, article: Article) {
    Row(modifier = modifier) {
        article.urlToImage?.let {
            AsyncImage(
                model = it,
                contentDescription = "image article",
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(6.dp),
                contentScale = ContentScale.Crop
            )
        } ?: run {
            Image(
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(6.dp),
                painter = painterResource(id = R.mipmap.img_place_holder),
                contentDescription = "image article",
                contentScale = ContentScale.Crop,
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = article.title?: "",
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,)
            Text(text = article.description?: "",
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,)
            Text(text = article.publishedAt?.toYYYYMMDD()?:"")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RowArticlePreview() {
    DailyNewsTheme {
        RowArticle(article = Article(
            "Title",
            "description",
            null,
            "url",
            Date()
        ))
    }
}