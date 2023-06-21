package jp.techacademy.mahjongcalculator

import android.util.Log

class ScoreCalculator(private val tiles: List<MahjongTile>) {

    fun calculateScore(
        isKid: Boolean,
        isRon: Boolean,
        doraCount: Int,
        isReach: Boolean,
        isDoubleReach: Boolean,
        isOne: Boolean,
        isChankan: Boolean,
        isRinshan: Boolean,
        isHoutei: Boolean,
        isHaitei: Boolean,
        isTenhou: Boolean,
        isTihou: Boolean
    ): CalculateActivity.ScoreResult {
        val selectedYaku = SelectedYaku(tiles)
        val yakuList = mutableListOf<String>()
        val calculateParams = CalculationParams(
            isKid,
            isRon,
            doraCount,
            isReach,
            isDoubleReach,
            isOne,
            isChankan,
            isRinshan,
            isHoutei,
            isHaitei,
            isTenhou,
            isTihou
        )

        return if (selectedYaku.isTitoitsu()) {
            yakuList.add(YakuList.TITOITSU)
            calculateTitoitsuScore(calculateParams)
        } else if (selectedYaku.isPinfu()) {
            yakuList.add(YakuList.PINFU)
            calculatePinfuScore(calculateParams)
        } else {
            calculateOtherScore(calculateParams)
        }
    }

    private fun calculateTitoitsuScore(params: CalculationParams): CalculateActivity.ScoreResult {
        // 七対子の場合の点数を計算
        val fu = 25
        var han = 2

        // ドラの数に応じて翻数を調整する
        han += params.doraCount

        if (!params.isRon) han += 1
        if (params.isReach) han += 1
        if (params.isDoubleReach) han += 2
        if (params.isOne) han += 1
        if (params.isChankan) han += 1
        if (params.isRinshan) han += 1
        if (params.isHoutei) han += 1
        if (params.isHaitei) han += 1
        if (params.isTenhou) han += 13
        if (params.isTihou) han += 13


        val basePoint = when (han) {
            2 -> {
                if (!params.isKid) 2400 else 1600
            }
            3 -> {
                if (!params.isKid) 4800 else 3200
            }
            4 -> {
                if (!params.isKid) 9600 else 6400
            }
            else -> {
                if (!params.isKid) 12000 else 8000
            }
        }
        return CalculateActivity.ScoreResult(fu, han, basePoint)
    }

    private fun calculatePinfuScore(params: CalculationParams): CalculateActivity.ScoreResult {

        // 平和は1飜
        var han = 1

        // ドラの数に応じて翻数を調整する
        han += params.doraCount

        if (!params.isRon) han += 1
        if (params.isReach) han += 1
        if (params.isDoubleReach) han += 2
        if (params.isOne) han += 1
        if (params.isChankan) han += 1
        if (params.isRinshan) han += 1
        if (params.isHoutei) han += 1
        if (params.isHaitei) han += 1
        if (params.isTenhou) han += 13
        if (params.isTihou) han += 13

        // ロンで30符だった時
        return if (params.isRon) {
            val fu = 30
            val baseRonPoint = when (han) {
                1 -> {
                    if (!params.isKid) 1500 else 1000
                }
                2 -> {
                    if (!params.isKid) 2900 else 2000
                }
                3 -> {
                    if (!params.isKid) 5800 else 3900
                }
                4 -> {
                    if (!params.isKid) 11600 else 7700
                }
                else -> {
                    if (!params.isKid) 12000 else 8000
                }
            }
            CalculateActivity.ScoreResult(fu, han, baseRonPoint)
        } else {
            val fu = 20
            val baseTsumoPoint = when (han) {
                2 -> {
                    if (!params.isKid) 2000 else 1300
                }
                3 -> {
                    if (!params.isKid) 3900 else 2600
                }
                4 -> {
                    if (!params.isKid) 7700 else 5200
                }
                else -> {
                    if (!params.isKid) 12000 else 8000
                }
            }
            CalculateActivity.ScoreResult(fu, han, baseTsumoPoint)
        }
    }

    private fun calculateOtherScore(params: CalculationParams): CalculateActivity.ScoreResult {
        // 手牌を判定する
        var fu = 20
        if (params.isRon) {
            fu += 10
        } else {
            fu += 2
        }
        if (tiles[0].tileType == "ji") {
            fu += 2
        }

        // 手牌に槓子が存在するか判定して符数を加算する
        fu += calculateFuForKan()

        // 手牌に暗刻が存在するか判定して符数を加算する
        fu += calculateFuForAnko()

        var calculatedFu = if ((fu % 10) != 0) {
            fu - (fu % 10) + 10
        } else {
            fu
        }

        // 飜数を判断する
        var han = 0

        han += params.doraCount

        if (!params.isRon) han += 1
        if (params.isReach) han += 1
        if (params.isDoubleReach) han += 2
        if (params.isOne) han += 1
        if (params.isChankan) han += 1
        if (params.isRinshan) han += 1
        if (params.isHoutei) han += 1
        if (params.isHaitei) han += 1
        if (params.isTenhou) han += 13
        if (params.isTihou) han += 13


        // 得た飜数と符数を下にScoreTable.ktを参照する
        val baseOtherPoint = ScoreTable.getBaseOtherPoint(calculatedFu, han, params.isKid)

        // 得た対応する点数を取得して、CalculateActivity.ktのScoreResultに送る
        return CalculateActivity.ScoreResult(calculatedFu, han, baseOtherPoint)
    }

    private fun calculateFuForKan(): Int {
        var fu = 0

        var i = 2
        while (i <= tiles.size - 4) {
            val tile1 = tiles[i]
            val tile2 = tiles[i + 1]
            val tile3 = tiles[i + 2]
            val tile4 = tiles[i + 3]

            if (tile1.number == tile2.number && tile1.number == tile3.number && tile1.number == tile4.number
                && tile1.tileType == tile2.tileType && tile1.tileType == tile3.tileType && tile1.tileType == tile4.tileType
            ) {
                fu += if (tile2.isRevealed && tile3.isRevealed) {
                    if (tile1.tileType == "ji" || tile1.number == 1 || tile1.number == 9) {
                        32
                    } else {
                        16
                    }
                } else {
                    if (tile1.tileType == "ji" || tile1.number == 1 || tile1.number == 9) {
                        16
                    } else {
                        8
                    }
                }
            }
            i++
        }
        return fu
    }

    private fun calculateFuForAnko(): Int {
        var fu = 0

        var i = 2
        while (i <= tiles.size - 5) {
            val tile1 = tiles[i]
            val tile2 = tiles[i + 1]
            val tile3 = tiles[i + 2]

            if (tile1.number == tile2.number && tile1.number == tile3.number
                && tile1.tileType == tile2.tileType && tile1.tileType == tile3.tileType
            ) {
                if (!tile1.isRevealed && !tile2.isRevealed && !tile3.isRevealed) {
                    fu += if (tile1.tileType == "ji" || tile1.number == 1 || tile1.number == 9) {
                        8
                    } else {
                        4
                    }
                }
            }
            i++
        }
        return fu
    }

    data class CalculationParams(
        var isKid: Boolean = false,
        var isRon: Boolean = false,
        var doraCount: Int,
        var isReach: Boolean = false,
        var isDoubleReach: Boolean = false,
        var isOne: Boolean = false,
        var isChankan: Boolean = false,
        var isRinshan: Boolean = false,
        var isHoutei: Boolean = false,
        var isHaitei: Boolean = false,
        var isTenhou: Boolean = false,
        var isTihou: Boolean = false
    )
}