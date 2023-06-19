package jp.techacademy.mahjongcalculator

class ScoreTable(private var tiles: List<MahjongTile>) {
    private var fu: Int = 0
    private var han: Int = 0
    private var isKid: Boolean = false

    companion object{
        fun getBaseOtherPoint(fu: Int, han: Int, isKid: Boolean): Int {
            val basePoint = when (fu == 30) {
                (han == 1) -> {
                    if (!isKid) 1500 else 1000
                }
                (han == 2) -> {
                    if (!isKid) 2900 else 2000
                }
                (han == 3) -> {
                    if (!isKid) 5800 else 3900
                }
                (han == 4) -> {
                    if (!isKid) 11600 else 7700
                }
                else -> {
                    if (!isKid) 12000 else 8000
                }
            }
            return basePoint
        }
    }
}