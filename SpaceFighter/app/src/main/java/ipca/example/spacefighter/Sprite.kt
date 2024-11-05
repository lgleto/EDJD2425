package ipca.example.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

open class Sprite  {

    var x = 0
    var y = 0
    var maxX = 0
    var maxY = 0
    var minX = 0
    var minY = 0

    var bitmap : Bitmap? = null
    var detectCollision : Rect

    constructor(width: Int, height: Int){
        maxX = width
        maxY = height
        minX = 0
        minY = 0

        detectCollision = Rect(0, 0, 0, 0)
    }



    constructor(context: Context, width: Int, height: Int, resource: Int){
        bitmap = BitmapFactory.decodeResource(context.resources, resource)

        minX = 0
        maxX = width

        maxY = height - bitmap!!.height
        minY = 0

        detectCollision = Rect(x, y, bitmap!!.width, bitmap!!.height)
    }

    open fun update() {
        detectCollision?.left = x
        detectCollision?.top = y
        detectCollision?.right = x + (bitmap?.width?:0)
        detectCollision?.bottom = y + (bitmap?.height?:0)
    }




}