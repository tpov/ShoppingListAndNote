package com.tpov.shoppinglist.utils

import android.view.MotionEvent
import android.view.View

class MyTouchListener: View.OnTouchListener {
    var xDelta = 0.0f
    var yDelta = 0.0f

    override fun onTouch(v: View, event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_MOVE -> {            //Если мы дежим
                v.x = xDelta + event.rawX
                v.y = yDelta + event.rawY
            }
            MotionEvent.ACTION_DOWN -> {            //Если мы пускаем
                xDelta = v.x - event.rawX
                yDelta = v.y - event.rawY
            }
        }
        return true
    }
}