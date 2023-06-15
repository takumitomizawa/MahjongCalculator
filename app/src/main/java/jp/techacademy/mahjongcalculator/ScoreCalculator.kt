package jp.techacademy.mahjongcalculator

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

        if (selectedYaku.isTitoitsu()) {
            yakuList.add(YakuList.TITOITSU)
            return calculateTitoitsuScore(
                CalculationParams(
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
            )
        } else if (selectedYaku.isPinfu()) {
            yakuList.add(YakuList.PINFU)
            return calculatePinfuScore(isKid, isRon, doraCount)
        }
        return CalculateActivity.ScoreResult(0, 0, 0)
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

    private fun calculatePinfuScore(
        isKid: Boolean,
        isRon: Boolean,
        doraCount: Int
    ): CalculateActivity.ScoreResult {

        // 平和は1飜
        var han = 1

        // ドラの数に応じて翻数を調整する
        han += doraCount

        // ロンで30符だった時
        return if (isRon) {
            val fu = 30
            val baseRonPoint = when (han) {
                1 -> {
                    if (!isKid) 1500 else 1000
                }
                2 -> {
                    if (!isKid) 2900 else 2000
                }
                3 -> {
                    if (!isKid) 5800 else 3900
                }
                4 -> {
                    if (!isKid) 11600 else 7700
                }
                else -> {
                    if (!isKid) 12000 else 8000
                }
            }
            CalculateActivity.ScoreResult(fu, han, baseRonPoint)
        } else {
            val fu = 20
            // 平和のツモの時は面前が確定しているので1飜プラス
            han++
            val baseTsumoPoint = when (han) {
                2 -> {
                    if (!isKid) 2000 else 1300
                }
                3 -> {
                    if (!isKid) 3900 else 2600
                }
                4 -> {
                    if (!isKid) 7700 else 5200
                }
                else -> {
                    if (!isKid) 12000 else 8000
                }
            }
            CalculateActivity.ScoreResult(fu, han, baseTsumoPoint)
        }
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