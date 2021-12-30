package com.demo.mapdemologinext.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.io.ByteArrayOutputStream
import kotlin.math.abs

class CaptureSignatureView(context: Context?, attr: AttributeSet?) : View(context, attr) {
    private var _bitmap: Bitmap? = null
    private var _canvas: Canvas? = null
    private val _path: Path = Path()
    private val _bitmapPaint: Paint = Paint(Paint.DITHER_FLAG)
    private val _paint: Paint = Paint()
    private var _mX = 0f
    private var _mY = 0f
    private val touchTolerance = 4f
    private val lineThickness = 4f
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        _bitmap = Bitmap.createBitmap(
            w,
            if (h > 0) h else (this.parent as View).height,
            Bitmap.Config.ARGB_8888
        ).apply {
            _canvas = Canvas(this)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(_bitmap!!, 0f, 0f, _bitmapPaint)
        canvas.drawPath(_path, _paint)
    }

    private fun touchStart(x: Float, y: Float) {
        _path.reset()
        _path.moveTo(x, y)
        _mX = x
        _mY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = abs(x - _mX)
        val dy = abs(y - _mY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            _path.quadTo(_mX, _mY, (x + _mX) / 2, (y + _mY) / 2)
            _mX = x
            _mY = y
        }
    }

    private fun touchUp() {
        if (!_path.isEmpty) {
            _path.lineTo(_mX, _mY)
            _canvas!!.drawPath(_path, _paint)
        } else {
            _canvas!!.drawPoint(_mX, _mY, _paint)
        }
        _path.reset()
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        super.onTouchEvent(e)
        val x = e.x
        val y = e.y
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }
        return true
    }

    fun clearCanvas() {
        _canvas!!.drawColor(Color.WHITE)
        invalidate()
    }

    val bytes: ByteArray
        get() {
            val b = bitmap
            val baos = ByteArrayOutputStream()
            b.compress(Bitmap.CompressFormat.PNG, 100, baos)
            return baos.toByteArray()
        }
    val bitmap: Bitmap
        get() {
            val v = this as View
            val b = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
            val c = Canvas(b)
            v.layout(v.left, v.top, v.right, v.bottom)
            v.draw(c)
            return b
        }

    init {
        _paint.isAntiAlias = true
        _paint.isDither = true
        _paint.color = Color.argb(255, 0, 0, 0)
        _paint.style = Paint.Style.STROKE
        _paint.strokeJoin = Paint.Join.ROUND
        _paint.strokeCap = Paint.Cap.ROUND
        _paint.strokeWidth = lineThickness
    }
}