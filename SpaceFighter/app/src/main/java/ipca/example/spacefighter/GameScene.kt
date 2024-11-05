package ipca.example.spacefighter

import android.content.Context
import android.view.MotionEvent

class GameScene : GameView {

    //lateinit var player : Player
    var player : Sprite

    constructor(context: Context, width: Int, height: Int) : super(context, width, height){
        var stars = arrayListOf<Star>()
        var enemies = arrayListOf<Enemy>()

        lateinit var boom : Boom

        for (i in 0..100){
            stars.add(Star(width, height))
        }

        for (i in 0..2){
            enemies.add(Enemy(context,width, height, R.drawable.enemy))
        }

        //player = Player(context, width, height, R.drawable.player)

        player = object : Sprite(context, width, height, R.drawable.player) {

            var boosting = false
            var speed = 0

            val GRAVITY = -10
            val MAX_SPEED = 20
            val MIN_SPEED = 1

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
        }.apply {
            x = 75
            y = 50
            speed = 1
        }

        boom = Boom(context, width, height, R.drawable.boom)

        sprites.add(player)
        sprites.add(boom)
        sprites.addAll(stars)
        sprites.addAll(enemies)

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                //player.boosting = true
            }
            MotionEvent.ACTION_UP -> {
                //player.boosting = false
            }
        }
        return true
    }
}