package ipca.example.shoppinglist.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme

data class Items(
    val value : Float,
    val weekDay : Int
)

@Composable
fun LineGraphicsView (
    data: List<Items> = arrayListOf()
) {

    Canvas(
        modifier = Modifier.fillMaxSize()
    ){
        val width = size.width
        val height = size.height

        val minWidth = width/10f
        val minHeight = height/10f

        for (i in 0..10) {
            drawLine(
                start = Offset(x = minWidth * i, y = 0f),
                end = Offset(minWidth * i, size.height),
                color = Color.Black
            )
            drawLine(
                start = Offset(x = 0f, y = minHeight * i),
                end = Offset(size.width * i, minHeight * i ),
                color = Color.Black
            )
        }

        val dayHeight = height/7f
        for (index in 0..data.size-1) {
            drawCircle(
                color = Color.Blue,
                center = Offset(  (width/100f*data[index].value)   ,
                   height - (data[index].weekDay.toFloat()*dayHeight) ),
                radius = 30f
            )
            if (index < data.size-1) {

            drawLine(
                color = Color.Blue,
                start = Offset(  (width/100f*data[index].value)   ,
                    height - (data[index].weekDay.toFloat()*dayHeight) ),
                end = Offset(  (width/100f*data[index+1].value)   ,
                    height - (data[index+1].weekDay.toFloat()*dayHeight) ),
                strokeWidth = 60f
            )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LineGraphicsViewPreview(){
    ShoppingListTheme {
        LineGraphicsView(
            data = arrayListOf(
                Items(10f, 1),
                Items(20f, 2),
                Items(50f, 3),
                Items(30f, 4),
                Items(40f, 5),
                Items(  0f, 6),
                Items(100f, 7),
            )
        )
    }
}