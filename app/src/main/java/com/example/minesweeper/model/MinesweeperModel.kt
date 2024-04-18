package com.example.minesweeper.model

object MinesweeperModel {
    val ZERO: Short = 0
    val BOMB: Short = -1
    val ONE: Short = 1
    val TWO: Short = 2
    val THREE: Short = 3

    val numBombs = 3

    private var model = arrayOf(
        shortArrayOf(ZERO, ZERO, ZERO, ZERO, ZERO),
        shortArrayOf(ZERO, ZERO, ZERO, ZERO, ZERO),
        shortArrayOf(ZERO, ZERO, ZERO, ZERO, ZERO),
        shortArrayOf(ZERO, ZERO, ZERO, ZERO, ZERO),
        shortArrayOf(ZERO, ZERO, ZERO, ZERO, ZERO))

    val FLAG: Short = -2
    val UNSEEN: Short = -3
    val SEEN: Short = -4

    private var cover = arrayOf(
        shortArrayOf(UNSEEN, UNSEEN, UNSEEN, UNSEEN, UNSEEN),
        shortArrayOf(UNSEEN, UNSEEN, UNSEEN, UNSEEN, UNSEEN),
        shortArrayOf(UNSEEN, UNSEEN, UNSEEN, UNSEEN, UNSEEN),
        shortArrayOf(UNSEEN, UNSEEN, UNSEEN, UNSEEN, UNSEEN),
        shortArrayOf(UNSEEN, UNSEEN, UNSEEN, UNSEEN, UNSEEN))

    fun setMines(){
        var bombCount = 0

        while(bombCount < numBombs){
            val x = (0..4).random()
            val y = (0..4).random()
            if(model[x][y] == ZERO){
                model[x][y] = BOMB
                bombCount += 1
            }
        }
    }

    fun setNumbers(){
        for (i in 0..4) {
            for (j in 0..4) {
                val surroundingBombs = countBombs(i, j)
                if(model[i][j] != BOMB){
                    if(surroundingBombs == 1){
                        model[i][j] = ONE
                    } else if (surroundingBombs == 2) {
                        model[i][j] = TWO
                    } else if (surroundingBombs == 3) {
                        model[i][j] = THREE
                    }
                }
            }
        }
    }

    fun countBombs(x: Int, y: Int) : Int{
        var count = 0

        // Top-Left
        if(x == 0 && y == 0){
            if(model[0][1] == BOMB)
                count += 1
            if(model[1][0] == BOMB)
                count += 1
            if(model[1][1] == BOMB)
                count += 1
        } else if(x == 0 && y == 4) {
            if(model[0][3] == BOMB)
                count += 1
            if(model[1][3] == BOMB)
                count += 1
            if(model[1][4] == BOMB)
                count += 1
        } else if(x == 0) {
            if(model[0][y - 1] == BOMB)
                count += 1
            if(model[0][y + 1] == BOMB)
                count += 1
            if(model[1][y + 1] == BOMB)
                count += 1
            if(model[1][y - 1] == BOMB)
                count += 1
            if(model[1][y] == BOMB)
                count += 1
        }  else if(x == 4 && y == 0) {
            if(model[3][0] == BOMB)
                count += 1
            if(model[3][1] == BOMB)
                count += 1
            if(model[4][1] == BOMB)
                count += 1
        } else if(y == 0){
            if(model[x - 1][0] == BOMB)
                count += 1
            if(model[x - 1][1] == BOMB)
                count += 1
            if(model[x][1] == BOMB)
                count += 1
            if(model[x + 1][y + 1] == BOMB)
                count += 1
            if(model[x + 1][0] == BOMB)
                count += 1
        } else if(x == 4 && y == 4){
            if(model[3][4] == BOMB)
                count += 1
            if(model[4][3] == BOMB)
                count += 1
            if(model[3][3] == BOMB)
                count += 1
        } else if(x == 4){
            if(model[3][y - 1] == BOMB)
                count += 1
            if(model[3][y] == BOMB)
                count += 1
            if(model[3][y + 1] == BOMB)
                count += 1
            if(model[4][y - 1] == BOMB)
                count += 1
            if(model[4][y + 1] == BOMB)
                count += 1
        } else if(y == 4){
            if(model[x - 1][4] == BOMB)
                count += 1
            if(model[x - 1][3] == BOMB)
                count += 1
            if(model[x][3] == BOMB)
                count += 1
            if(model[x + 1][3] == BOMB)
                count += 1
            if(model[x + 1][4] == BOMB)
                count += 1
        } else {
            if(model[x - 1][y - 1] == BOMB)
                count += 1
            if(model[x - 1][y] == BOMB)
                count += 1
            if(model[x - 1][y + 1] == BOMB)
                count += 1
            if(model[x][y - 1] == BOMB)
                count += 1
            if(model[x][y + 1] == BOMB)
                count += 1
            if(model[x + 1][y - 1] == BOMB)
                count += 1
            if(model[x + 1][y] == BOMB)
                count += 1
            if(model[x + 1][y + 1] == BOMB)
                count += 1
        }

        return count
    }

    fun getFieldContent(x: Int, y: Int) = model[x][y]

    fun setToSeen(x: Int, y: Int){
        cover[x][y] = SEEN
    }

    fun getSeenContent(x: Int, y: Int) = cover[x][y]

    fun setFlagContent(x: Int, y: Int){
        cover[x][y] = FLAG
    }

    fun setUnflagContent(x: Int, y: Int){
        cover[x][y] = UNSEEN
    }

    fun reset(){
        model = arrayOf(
            shortArrayOf(ZERO, ZERO, ZERO, ZERO, ZERO),
            shortArrayOf(ZERO, ZERO, ZERO, ZERO, ZERO),
            shortArrayOf(ZERO, ZERO, ZERO, ZERO, ZERO),
            shortArrayOf(ZERO, ZERO, ZERO, ZERO, ZERO),
            shortArrayOf(ZERO, ZERO, ZERO, ZERO, ZERO))

        cover = arrayOf(
            shortArrayOf(UNSEEN, UNSEEN, UNSEEN, UNSEEN, UNSEEN),
            shortArrayOf(UNSEEN, UNSEEN, UNSEEN, UNSEEN, UNSEEN),
            shortArrayOf(UNSEEN, UNSEEN, UNSEEN, UNSEEN, UNSEEN),
            shortArrayOf(UNSEEN, UNSEEN, UNSEEN, UNSEEN, UNSEEN),
            shortArrayOf(UNSEEN, UNSEEN, UNSEEN, UNSEEN, UNSEEN))
    }

    fun checkWin(): Boolean{
        var count = 0
        for(i in 0..4){
            for(j in 0..4){
                if(model[i][j] == BOMB && cover[i][j] == FLAG){
                    count += 1
                }
            }
        }
        return count == 3
    }

    fun setAllSeen(){
        for(i in 0..4){
            for(j in 0..4){
                if(cover[i][j] != FLAG && cover[i][j] == UNSEEN){
                    cover[i][j] = SEEN
                }
            }
        }
    }

    fun clearZeros(x: Int, y: Int){
        // NEXT TIME SET PADDING AROUND MODEL, SO INSTEAD OF 5X5, MAKE A 7X7 BUT ONLY USE THE 5X5 SO THERE ARE NO INDEX OUT OF BOUDNS ERRORS.
        cover[x][y] = SEEN
        if(x == 0 && y == 0){ // Top-Left
            if(model[0][1] == ZERO)
                clearZeros(0, 1)
            if(model[1][0] == ZERO)
                clearZeros(1, 0)
        } else if(x == 0 && y == 4) { // Top-right
            if(model[0][3] == ZERO)
                clearZeros(0, 3)
            if(model[1][3] == ZERO)
                clearZeros(1, 3)
        } else if(x == 0) {
            if(model[0][y - 1] == ZERO)
                clearZeros(0, y - 1)
            if(model[0][y + 1] == ZERO)
                clearZeros(0, y + 1)
            if(model[1][y] == ZERO)
                clearZeros(1, y)
        }  else if(x == 4 && y == 0) {
            if(model[3][0] == ZERO)
                clearZeros(3, 0)
            if(model[4][1] == ZERO)
                clearZeros(3, 0)
        } else if(y == 0){
            if(model[x - 1][0] == ZERO)
                clearZeros(x - 1, 0)
            if(model[x][1] == ZERO)
                clearZeros(x, 1)
            if(model[x + 1][0] == ZERO)
                clearZeros(x + 1, 0)
        } else if(x == 4 && y == 4){
            if(model[3][4] == ZERO)
                clearZeros(3, 4)
            if(model[4][3] == ZERO)
                clearZeros(4, 3)
        } else if(x == 4){
            if(model[3][y] == ZERO)
                clearZeros(3, y)
            if(model[4][y - 1] == ZERO)
                clearZeros(4, y - 1)
            if(model[4][y + 1] == ZERO)
                clearZeros(4, y + 1)
        } else if(y == 4){
            if(model[x - 1][4] == ZERO)
                clearZeros(x - 1, 4)
            if(model[x][3] == ZERO)
                clearZeros(x, 3)
            if(model[x + 1][4] == ZERO)
                clearZeros(x + 1, 4)
        } else {
            if(model[x - 1][y] == ZERO)
                clearZeros(x - 1, y)
            if(model[x][y - 1] == ZERO)
                clearZeros(x, y - 1)
            if(model[x][y + 1] == ZERO)
                clearZeros(x, y + 1)
            if(model[x + 1][y] == ZERO)
                clearZeros(x + 1, y)
        }
    }
}