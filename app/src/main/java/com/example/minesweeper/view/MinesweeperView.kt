package com.example.minesweeper.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.minesweeper.MainActivity
import com.example.minesweeper.R
import com.example.minesweeper.model.MinesweeperModel
import com.google.android.material.snackbar.Snackbar


class MinesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var paintBackground: Paint
    var paintLine: Paint
    var paintText: Paint

    var bitmapBomb = BitmapFactory.decodeResource(resources, R.drawable.bomb)
    var bitmapTile = BitmapFactory.decodeResource(resources, R.drawable.tile)
    var bitmapFlag = BitmapFactory.decodeResource(resources, R.drawable.flag)

    private var flagMode = false

    private var gameStatus = true

    init {
        paintBackground = Paint()
        paintBackground.setColor(Color.GRAY)
        paintBackground.style = Paint.Style.FILL

        paintLine = Paint()
        paintLine.setColor(Color.BLACK)
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 10f

        paintText = Paint()
        paintText.textSize = 100f
        paintText.setColor(Color.GREEN)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        paintText.textSize = height / 3f

        bitmapBomb = Bitmap.createScaledBitmap(bitmapBomb, width / 5, height / 5, false)
        bitmapTile = Bitmap.createScaledBitmap(bitmapTile, width / 5, height / 5, false)
        bitmapFlag = Bitmap.createScaledBitmap(bitmapFlag, width / 5, height / 5, false)

        MinesweeperModel.setMines()
        MinesweeperModel.setNumbers()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        drawGameArea(canvas)

        drawPlayers(canvas)
    }

    private fun drawGameArea(canvas: Canvas) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)

        for (xy in 1..4) {
            canvas.drawLine(
                0f,
                ((height / 5) * xy).toFloat(),
                width.toFloat(),
                ((height / 5) * xy).toFloat(),
                paintLine
            )
            canvas.drawLine(
                ((width / 5) * xy).toFloat(),
                0f,
                ((width / 5) * xy).toFloat(),
                height.toFloat(),
                paintLine
            )
        }
    }

    private fun drawPlayers(canvas: Canvas) {
        for (i in 0..4) {
            for (j in 0..4) {
                val covered = MinesweeperModel.getSeenContent(i, j)
                val currentTile = MinesweeperModel.getFieldContent(i, j)

                if (covered == MinesweeperModel.UNSEEN) {
                    canvas.drawBitmap(
                        bitmapTile,
                        (i * (width / 5)).toFloat(),
                        (j * (height / 5)).toFloat(),
                        null
                    )
                } else if (covered == MinesweeperModel.FLAG) {
                    canvas.drawBitmap(
                        bitmapFlag,
                        (i * (width / 5)).toFloat(),
                        (j * (height / 5)).toFloat(),
                        null
                    )
                } else {
                    when (currentTile) {
                        MinesweeperModel.BOMB -> {
                            canvas.drawBitmap(
                                bitmapBomb,
                                (i * (width / 5)).toFloat(),
                                (j * (height / 5)).toFloat(),
                                null
                            )
                        }

                        MinesweeperModel.ONE -> {
                            paintText.setColor(Color.GREEN)
                            canvas.drawText(
                                "1",
                                (i * (width / 5)).toFloat(),
                                ((j + 1) * (height / 5)).toFloat(),
                                paintText
                            )
                        }

                        MinesweeperModel.TWO -> {
                            paintText.setColor(Color.rgb(255, 165, 0))
                            canvas.drawText(
                                "2",
                                (i * (width / 5)).toFloat(),
                                ((j + 1) * (height / 5)).toFloat(),
                                paintText
                            )
                        }

                        MinesweeperModel.THREE -> {
                            paintText.setColor(Color.RED)
                            canvas.drawText(
                                "3",
                                (i * (width / 5)).toFloat(),
                                ((j + 1) * (height / 5)).toFloat(),
                                paintText
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val tX = event.x.toInt() / (width / 5)
        val tY = event.y.toInt() / (height / 5)

        if (event.action == MotionEvent.ACTION_UP && tX < 5 && tY < 5 && gameStatus) {
            if(flagMode){
                if(MinesweeperModel.getSeenContent(tX, tY) == MinesweeperModel.UNSEEN){
                    MinesweeperModel.setFlagContent(tX, tY)
                    winCondition(tX, tY)
                } else if (MinesweeperModel.getSeenContent(tX, tY) == MinesweeperModel.FLAG){
                    MinesweeperModel.setUnflagContent(tX, tY)
                }
            } else {
                if (MinesweeperModel.getSeenContent(tX, tY) == MinesweeperModel.UNSEEN) {
                    MinesweeperModel.setToSeen(tX, tY)
                    loseCondition(tX, tY)
                    if(MinesweeperModel.getSeenContent(tX, tY) == MinesweeperModel.ZERO){
                        MinesweeperModel.clearZeros(tX, tY)
                        invalidate()
                    }
                }
            }
            invalidate()
        }
        return true
    }

    fun resetGame() {
        MinesweeperModel.reset()
        MinesweeperModel.setMines()
        MinesweeperModel.setNumbers()
        gameStatus = true
        (context as MainActivity).showMessage("")
        invalidate()
    }

    fun setFlagMode(mode: Boolean) {
        flagMode = mode
    }

    fun loseCondition(x: Int, y: Int){
        if(MinesweeperModel.getFieldContent(x, y) == MinesweeperModel.BOMB){
            (context as MainActivity).showMessage("YOU LOST!")
            gameStatus = false
        }
    }

    fun winCondition(x: Int, y: Int){
        if(MinesweeperModel.checkWin()){
            (context as MainActivity).showMessage("YOU WON!")
            MinesweeperModel.setAllSeen()
            gameStatus = false
        }
    }
}