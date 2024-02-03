package com.android.launcher3.pw

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.android.launcher3.R
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
    private var paint: Paint = Paint()

    //    private var paint2: Paint = Paint()
    private var validation: Boolean = false


    init {
        paint.color = resources.getColor(android.R.color.white)
        paint.alpha = 85
        paint.style = Paint.Style.FILL

        runnable = object : Runnable {
            override fun run() {
                currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                var sec = 60 - currentTime.substring(6, 8).toInt()
                currentTime = (currentTime.split(":")[0] + ":" + currentTime.split(":")[1])
                invalidate()
//                handler.postDelayed(this, sec.toLong())
                handler.postDelayed(this, 10000)
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
        val F1t_background_path = Path()
        val rect = RectF(100f, 10f, 950f, 200f)
        F1t_background_path.addRoundRect(rect, corners, Path.Direction.CW)
        canvas.drawPath(F1t_background_path, paint)

        //For Data set
        forDateTimeSet(canvas)
        if (!validation) {
            validation = true
            startRunnable()
        }

        //For 2nd dialog
        val S2d_background_path = Path()
        S2d_background_path.addRoundRect(RectF(100f, 250f, 950f, 900f), corners, Path.Direction.CW)
        canvas.drawPath(S2d_background_path, paint)

        //For 3rd dialog
        val t3D_background_path = Path()
        t3D_background_path.addRoundRect(RectF(120f, 270f, 930f, 700f), corners, Path.Direction.CW)
        canvas.drawPath(t3D_background_path, paint)

        for2ndDataSet(canvas)


//        canvas.drawText("s", rect.centerX(), rect.centerY(), paint2);

//        (100F, 10F, 100F, 600F, paint);

//        val centerX = width / 2f
//        val centerY = height / 2f
//        val radius = Math.min(centerX, centerY)
//
//        canvas.drawCircle(centerX, centerY, radius, paint)
    }

    private fun forDateTimeSet(canvas: Canvas) {
        //Time Setup
        val time_date_paint = Paint()

        val calendar = Calendar.getInstance()
        val date = calendar.get(Calendar.DATE).toString()
        val month_name = SimpleDateFormat("MMM").format(calendar.time)
        val day = SimpleDateFormat("EEEE").format(Date())

        time_date_paint.textSize = 67f
        time_date_paint.color = Color.BLUE
        canvas.drawText(currentTime, 520 - time_date_paint.textSize, 100f, time_date_paint)

        time_date_paint.textSize = 50f
        canvas.drawText(
            "$day, $date $month_name",
            430 - time_date_paint.textSize,
            165f,
            time_date_paint,
        )
    }

    private fun for2ndDataSet(canvas: Canvas) {
        val title_paint = Paint()
        title_paint.textSize = 40f
        title_paint.color = Color.BLUE
        canvas.drawText("My Space", 220 - title_paint.textSize, 350f, title_paint)

        title_paint.textSize = 46f
        title_paint.color = Color.BLACK
        canvas.drawText("Today I will study Maths", 350 - title_paint.textSize, 442f, title_paint)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.target)
        canvas.drawBitmap(bitmap, null, RectF(200f, 390f, 275f, 460f), null)






    }
}

