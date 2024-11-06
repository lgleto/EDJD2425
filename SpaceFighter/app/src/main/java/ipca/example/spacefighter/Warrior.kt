package ipca.example.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class Warrior {

    var x = 0
    var y = 0
    var maxX = 0
    var maxY = 0
    var minX = 0
    var minY = 0

    var counter = 0

    var bitmap : Bitmap
        get() {
            counter++
            if (counter > 40) counter = 0
            return getBitMapFrame(counter/10)
        }

    var boosting = false



    var spriteBitmap : Bitmap


    var detectCollision : Rect

    constructor(context: Context, width: Int, height: Int){

        spriteBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.image_360)
        spriteBitmap = Bitmap.createScaledBitmap(spriteBitmap, 800, 200, true);
        bitmap = getBitMapFrame(0)

        minX = 0
        maxX = width

        maxY = height - bitmap.height
        minY = 0

        x = 100
        y = 100

        detectCollision = Rect(x, y, bitmap.width, bitmap.height)
    }

    fun getBitMapFrame( frame : Int): Bitmap {

        val frameWidth = spriteBitmap.width/4
        val frameHeight = spriteBitmap.height

        var f = frame
        if (f > 3)
            f = 3

        return Bitmap.createBitmap(spriteBitmap, f * frameWidth , 0, frameWidth, frameHeight)
    }


    fun update(){


        if (y < minY) y = minY
        if (y > maxY) y = maxY

        detectCollision.left = x
        detectCollision.top = y
        detectCollision.right = x + bitmap.width
        detectCollision.bottom = y + bitmap.height


    }


}