package jp.techacademy.mahjongcalculator

import kotlin.math.pow

class TitoitsuScoreCalculator(private val tiles: List<MahjongTile>) {

    fun calculateScore(isParent: Boolean, isTsumo: Boolean): CalculateActivity.ScoreResult {
        val tileCountMap = mutableMapOf<MahjongTile, Int>()

        // 手牌を数える
        for (tile in tiles){
            if (tileCountMap.containsKey(tile)){
                tileCountMap[tile] = tileCountMap[tile]!! + 1
            } else {
                tileCountMap[tile] = 1
            }
        }

        // 対子が7セット揃っているかチェック
        var pairCount = 0
        for (count in tileCountMap.values){
            if (count == 2){
                pairCount++
            }
        }

        return if (pairCount == 7){
            // 七対子の場合の点数を計算
            val fu = 25
            val han = 2
            val isParent = false

            val basePoint = when(han){
                2 -> if (isParent) 1600 else 800
                3 -> if (isParent) 3200 else 1600
                4 -> if (isParent) 6400 else 3200
                else -> if (isParent) 8000 else 8000
            }

            val finalPoints = basePoint * (2.0.pow(han.toDouble())).toInt()

            CalculateActivity.ScoreResult(fu, han, finalPoints)
        } else{
            CalculateActivity.ScoreResult(0, 0, 0)
        }
    }
}