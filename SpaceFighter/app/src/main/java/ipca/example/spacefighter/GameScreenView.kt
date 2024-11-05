package ipca.example.spacefighter

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun GameScreenView () {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    val density = configuration.densityDpi / 160f
    val screenWidthPx = screenWidth * density
    val screenHeightPx = screenHeight * density

    AndroidView(factory = { context ->
            GameScene(context = context,
                width = screenWidthPx.toInt(),
                height = screenHeightPx.toInt() )
        },
        update = {
            it.resume()
        }
    )
}