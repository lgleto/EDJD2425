package ipca.example.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import java.util.Random

class Enemy : Sprite {
    var speed = 0

    val generator = Random()


    constructor(context: Context, width: Int, height: Int, resource: Int) : super(
        context,
        width,
        height,
        resource
    ) {

        x = maxX
        y = generator.nextInt(maxY)

        speed = generator.nextInt(6) + 10

    }

    override fun update() {
        //x -= playerSpeed
        x -= speed

        if (x < minX - (bitmap?.width?:0)){
            x = maxX
            y = generator.nextInt(maxY)
            speed = generator.nextInt(6) + 10
        }

        super.update()
    }
}