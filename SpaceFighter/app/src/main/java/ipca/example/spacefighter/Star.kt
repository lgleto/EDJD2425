package ipca.example.spacefighter

import java.util.*

class Star {

    var x = 0
    var y = 0
    var speed = 0
    var maxX = 0
    var maxY = 0
    var minX = 0
    var minY = 0

    val  generator = Random()


    constructor(width: Int, height: Int){
        maxX = width
        maxY = height
        minX = 0
        minY = 0



        x = generator.nextInt(maxX)
        y = generator.nextInt(maxY)

        speed = generator.nextInt(15) + 1

    }

    fun update() {
        x -= speed

        if (x < 0){
            x = maxX
            y = Random().nextInt(maxY)
            speed = generator.nextInt(15) + 1
        }
    }


    var starWidth : Int = 0
        get() {
            return generator.nextInt(10) + 1
        }


}