package jp.techacademy.mahjongcalculator

import kotlin.math.pow

class TitoitsuScoreCalculator(private val tiles: List<MahjongTile>) {

    fun calculateScore(isParent: Boolean, isTsumo: Boolean, doraCount: Int): CalculateActivity.ScoreResult {
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
            var han = 2

            // ドラの数に応じて翻数を調整する
            han += doraCount

            val basePoint = when(han){
                2 -> {
                    if (!isParent) 2400 else 1600
                }
                3 -> {
                    if (!isParent) 4800 else 3200
                }
                4 -> {
                    if (!isParent) 9600 else 6400
                }
                else -> {
                    if (!isParent) 12000 else 8000
                }
            }

            val finalPoints = if (isParent){
                basePoint * 1.5.toInt()
            } else{
                basePoint
            }

            CalculateActivity.ScoreResult(fu, han, finalPoints)
        } else{
            CalculateActivity.ScoreResult(0, 0, 0)
        }
    }
}