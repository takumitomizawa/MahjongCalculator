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
        if (tiles[0].tileType == "ji"){
            fu += 2
        }
        if (tiles[3].number == tiles[4].number){
            if ((tiles[3].number == 1 || tiles[3].number == 9) || tiles[3].tileType == "ji"){
                fu += 8
            } else fu += 4
        }
        if (tiles[6].number == tiles[7].number){
            if ((tiles[6].number == 1 || tiles[6].number == 9) || tiles[6].tileType == "ji"){
                fu += 8
            } else fu += 4
        }
        if (tiles[9].number == tiles[10].number){
            if ((tiles[9].number == 1 || tiles[9].number == 9) || tiles[9].tileType == "ji"){
                fu += 8
            } else fu += 4
        }
        if (tiles[12].number == tiles[13].number){
            if ((tiles[12].number == 1 || tiles[12].number == 9) || tiles[12].tileType == "ji"){
                fu += 8
            } else fu += 4
        }
        /*if (tiles[3].number == tiles[6].number){
            if ((tiles[3].isBack || tiles[6].isBack) || (tiles[3].number == 1 || tiles[3].number == 9) || tiles[3].tileType == "ji"){
                fu += 32
            } else {
                fu += 16
            }
        }
        if (tiles[7].number == tiles[10].number){
            if ((tiles[7].isBack || tiles[10].isBack) || (tiles[7].number == 1 || tiles[7].number == 9) || tiles[7].tileType == "ji"){
                fu += 32
            } else {
                fu += 16
            }
        }
        if (tiles[11].number == tiles[14].number){
            if ((tiles[11].isBack || tiles[14].isBack) || (tiles[11].number == 1 || tiles[11].number == 9) || tiles[11].tileType == "ji"){
                fu += 32
            } else {
                fu += 16
            }
        }*/

        var calculatedFu = if((fu % 10) != 0){
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