package ipca.example.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import java.util.Random

class Boom {

    var x = 0
    var y = 0
    var speed = 0
    var maxX = 0
    var maxY = 0
    var minX = 0
    var minY = 0

    var bitmap : Bitmap


    constructor(context: Context, width: Int, height: Int){
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.boom)

        x = -300
        y = -300

    }
}