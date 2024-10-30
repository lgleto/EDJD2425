package ipca.example.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class Player {

    var x = 0
    var y = 0
    var speed = 0
    var maxX = 0
    var maxY = 0
    var minX = 0
    var minY = 0

    var bitmap : Bitmap
    var boosting = false

    private val GRAVITY = -10
    private val MAX_SPEED = 20
    private val MIN_SPEED = 1

    var detectCollision : Rect

    constructor(context: Context, width: Int, height: Int){
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.player)

        minX = 0
        maxX = width

        maxY = height - bitmap.height
        minY = 0

        x = 75
        y = 50

        speed = 1

        detectCollision = Rect(x, y, bitmap.width, bitmap.height)
    }

    fun update(){
        if (boosting) speed += 2
        else speed -= 5
        if (speed > MAX_SPEED) speed = MAX_SPEED
        if (speed < MIN_SPEED) speed = MIN_SPEED

        y -= speed + GRAVITY

        if (y < minY) y = minY
        if (y > maxY) y = maxY

        detectCollision.left = x
        detectCollision.top = y
        detectCollision.right = x + bitmap.width
        detectCollision.bottom = y + bitmap.height


    }


}