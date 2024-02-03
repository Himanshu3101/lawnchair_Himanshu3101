package com.android.launcher3.pw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class PhysicsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {


    private var currentTime: String = ""
    private var runnable: Runnable
    private val paint: Paint = Paint()

    //    private var paint2: Paint = Paint()
    private var validation: Boolean = false


    init {
        paint.color = resources.getColor(android.R.color.white)
        paint.style = Paint.Style.FILL
        runnable = object : Runnable {
            override fun run() {
                currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                var sec = 60 - currentTime.substring(6, 8).toInt()
                currentTime = (currentTime.split(":")[0] + ":" + currentTime.split(":")[1])
                invalidate()
                handler.postDelayed(this, sec.toLong())
            }
        }
    }

    private fun startRunnable() {
        runnable.run()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //For Corners
        val corners = floatArrayOf(
            80f, 80f,   // Top left radius in px
            80f, 80f,   // Top right radius in px
            80f, 80f,     // Bottom right radius in px
            80f, 80f,      // Bottom left radius in px
        )

        //For background set
        val path = Path()
        val rect = RectF(100f, 10f, 950f, 200f)
        path.addRoundRect(rect, corners, Path.Direction.CW)
        canvas.drawPath(path, paint)

        //For Data set
        forDataSet(canvas)
        if (!validation) {
            validation = true
            startRunnable()
        }


//        canvas.drawText("s", rect.centerX(), rect.centerY(), paint2);


        val path1 = Path()
        path1.addRoundRect(RectF(100f, 250f, 950f, 700f), corners, Path.Direction.CW)
        canvas.drawPath(path1, paint)


//        (100F, 10F, 100F, 600F, paint);

//        val centerX = width / 2f
//        val centerY = height / 2f
//        val radius = Math.min(centerX, centerY)
//
//        canvas.drawCircle(centerX, centerY, radius, paint)
    }

    private fun forDataSet(canvas: Canvas) {
        val paint2 = Paint()
        paint2.textSize = 55f
        paint2.color = Color.BLUE
        canvas.drawText(currentTime, 520 - paint2.textSize, 90f, paint2)

        val paint3 = Paint()
        paint3.textSize = 50f
        paint3.color = Color.BLUE
        val calendar = Calendar.getInstance()
        val date = calendar.get(Calendar.DATE).toString()
        val month_name = SimpleDateFormat("MMM").format(calendar.time)
        val day = SimpleDateFormat("EEEE").format(Date())
        canvas.drawText("$day, $date $month_name", 430 - paint2.textSize, 150f, paint3)
    }
}

