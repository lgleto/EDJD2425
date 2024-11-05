package ipca.example.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import java.util.Random

class Boom : Sprite{

    constructor(context: Context, width: Int, height: Int, resource: Int) : super(
        context,
        width,
        height,
        resource
    ){
        x = -300
        y = -300
    }

}