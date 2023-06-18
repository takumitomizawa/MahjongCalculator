package jp.techacademy.mahjongcalculator

class ScoreTable(private var tiles: List<MahjongTile>) {
    private var fu: Int = 0
    private var han: Int = 0
    private var isKid: Boolean = false

    /*val basePoint = when (fu == 20) {
        (han == 2) -> {
            if (isKid) 2400 else 1600
        }
        (han == 3) -> {
            if (isKid) 4800 else 3200
        }
        (han == 4) -> {
            if (isKid) 9600 else 6400
        }
        else -> {
            if (isKid) 12000 else 8000
        }
    }*/

    val basePoint = when (fu) {
        20 -> a()
        30 -> ab()
        else -> Unit
    }

    fun a():Int {
    return 10
    }

    fun ab() {

    }
}

fun ab(fu: Int = 0) {

}