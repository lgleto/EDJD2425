package ipca.example.spacefighter

import java.util.*

class Star : Sprite{

    var speed = 0
    val  generator = Random()

    constructor(width: Int, height: Int) : super(width,height){
        x = generator.nextInt(maxX)
        y = generator.nextInt(maxY)
        speed = generator.nextInt(15) + 1
    }

    override fun update() {
        //x -= playerSpeed
        x -= speed

        if (x < 0){
            x = maxX
            y = Random().nextInt(maxY)
            speed = generator.nextInt(15) + 1
        }
        super.update()
    }


    var starWidth : Int = 0
        get() {
            return generator.nextInt(10) + 1
        }


}