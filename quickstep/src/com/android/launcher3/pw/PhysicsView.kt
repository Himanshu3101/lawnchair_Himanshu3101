package com.android.launcher3.pw

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
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


    private lateinit var corners: FloatArray
    private lateinit var day: String
    private var currentTime: String = ""
    private var runnable: Runnable
    private var paint: Paint = Paint()

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
        corners = floatArrayOf(
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

        val btnTitlePaint = Paint()
        btnTitlePaint.color = resources.getColor(android.R.color.white)
        btnTitlePaint.style = Paint.Style.FILL

        t3D_background_path.addRoundRect(RectF(120f, 270f, 930f, 650f), corners, Path.Direction.CW)
        canvas.drawPath(t3D_background_path, btnTitlePaint)

        for2ndDataSet(canvas,btnTitlePaint)


        //For Button
        corners = floatArrayOf(
            150f, 150f,   // Top left radius in px
            150f, 150f,   // Top right radius in px
            150f, 150f,     // Bottom right radius in px
            150f, 150f,      // Bottom left radius in px
        )
        val F4h_btn_path = Path()
        F4h_btn_path.addRoundRect(RectF(350f, 970f, 720f, 830f), corners, Path.Direction.CW)
        btnTitlePaint.color = resources.getColor(android.R.color.white)
        canvas.drawPath(F4h_btn_path, btnTitlePaint)
        forButtonClick(canvas, btnTitlePaint)
    }

    private fun forDateTimeSet(canvas: Canvas) {
        //Time Setup
        val time_date_paint = Paint()

        val calendar = Calendar.getInstance()
        val date = calendar.get(Calendar.DATE).toString()
        val month_name = SimpleDateFormat("MMM").format(calendar.time)
        day = SimpleDateFormat("EEEE").format(Date())

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

    private fun for2ndDataSet(canvas: Canvas, btnTitlePaint: Paint) {
        btnTitlePaint.textSize = 40f
        btnTitlePaint.color = Color.BLUE
        canvas.drawText("My Space", 205 - btnTitlePaint.textSize, 350f, btnTitlePaint)

        btnTitlePaint.textSize = 46f
        btnTitlePaint.color = Color.BLACK
        canvas.drawText("Today I will study Maths", 300 - btnTitlePaint.textSize, 442f, btnTitlePaint)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.target)
        canvas.drawBitmap(bitmap, null, RectF(165f, 390f, 235f, 460f), null)

        //For Weekdays
        val centerX = width / 21f
        val centerY = height / 21f
        val radius = Math.min(centerX, centerY)
        btnTitlePaint.textSize = 42f

        btnTitlePaint.color = ContextCompat.getColor(context, R.color.lottie_green400)
        canvas.drawCircle(210f, 540f, radius, btnTitlePaint)
        btnTitlePaint.color = Color.DKGRAY
        canvas.drawText("M", 192f, 555f, btnTitlePaint);

        btnTitlePaint.color = Color.LTGRAY
        canvas.drawCircle(318f, 540f, radius, btnTitlePaint)
        btnTitlePaint.color = Color.DKGRAY
        canvas.drawText("T", 305f, 555f, btnTitlePaint);

        btnTitlePaint.color = Color.LTGRAY
        canvas.drawCircle(426f, 540f, radius, btnTitlePaint)
        btnTitlePaint.color = Color.DKGRAY
        canvas.drawText("W", 408f, 555f, btnTitlePaint);

        btnTitlePaint.color = Color.LTGRAY
        canvas.drawCircle(534f, 540f, radius, btnTitlePaint)
        btnTitlePaint.color = Color.DKGRAY
        canvas.drawText("T", 519f, 555f, btnTitlePaint);

        btnTitlePaint.color = Color.LTGRAY
        canvas.drawCircle(642f, 540f, radius, btnTitlePaint)
        btnTitlePaint.color = Color.DKGRAY
        canvas.drawText("F", 634f, 555f, btnTitlePaint);

        btnTitlePaint.color = Color.LTGRAY
        canvas.drawCircle(750f, 540f, radius, btnTitlePaint)
        btnTitlePaint.color = Color.DKGRAY
        canvas.drawText("S", 737f, 555f, btnTitlePaint);

        btnTitlePaint.color = Color.LTGRAY
        canvas.drawCircle(858f, 540f, radius, btnTitlePaint)
        btnTitlePaint.color = Color.DKGRAY
        canvas.drawText("S", 847f, 555f, btnTitlePaint);

//      For dot(.)
        btnTitlePaint.color = Color.BLUE
        when(day) {
            "Sunday" -> canvas.drawCircle(857f, 620f, radius-40, btnTitlePaint)
            "Monday" -> canvas.drawCircle(211f, 620f, radius-40, btnTitlePaint)
            "Tuesday" -> canvas.drawCircle(317f, 620f, radius-40, btnTitlePaint)
            "Wednesday" -> canvas.drawCircle(427f, 620f, radius-40, btnTitlePaint)
            "Thursday" -> canvas.drawCircle(532f, 620f, radius-40, btnTitlePaint)
            "Friday" -> canvas.drawCircle(640f, 620f, radius-40, btnTitlePaint)
            "Saturday" -> canvas.drawCircle(749f, 620f, radius-40, btnTitlePaint)
        }
    }

    private fun forButtonClick(canvas: Canvas, btnTitlePaint: Paint) {
        btnTitlePaint.textSize = 45f
        btnTitlePaint.color = Color.BLUE
        canvas.drawText("Enter", 530 - btnTitlePaint.textSize, 920f, btnTitlePaint)
    }


}

