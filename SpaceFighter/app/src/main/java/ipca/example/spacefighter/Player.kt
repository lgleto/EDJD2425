package ipca.example.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class Player  : Sprite {

    var boosting = false
    var speed = 0

    private val GRAVITY = -10
    private val MAX_SPEED = 20
    private val MIN_SPEED = 1

    constructor(context: Context, width: Int, height: Int, resource: Int) : super(
        context,
        width,
        height,
        resource
    ){
        x = 75
        y = 50
        speed = 1
    }

    override fun update(){
        if (boosting) speed += 2
        else speed -= 5
        if (speed > MAX_SPEED) speed = MAX_SPEED
        if (speed < MIN_SPEED) speed = MIN_SPEED

        y -= speed + GRAVITY

        if (y < minY) y = minY
        if (y > maxY) y = maxY
        super.update()
    }


}