package ipca.example.spacefighter

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameOverView(modifier: Modifier = Modifier,
                 onResumeClick: () -> Unit = {}){
    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text = "Game Over",
            fontSize = 100.sp)
    }

}

@Preview(showBackground = true)
@Composable
fun GameOverViewPreview() {
    GameOverView()
}