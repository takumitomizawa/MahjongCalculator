package jp.techacademy.mahjongcalculator

class ScoreCalculator(private val tiles: List<MahjongTile>) {

    fun calculateScore(isKid: Boolean, isTsumo: Boolean, doraCount: Int): CalculateActivity.ScoreResult {
        val selectedYaku = SelectedYaku(tiles)

        if (selectedYaku.isTitoitsu()) {
            return calculateTitoitsuScore(isKid, isTsumo, doraCount)
        }
        return CalculateActivity.ScoreResult(0, 0, 0)
    }
    private fun calculateTitoitsuScore(isKid: Boolean, isTsumo: Boolean, doraCount: Int): CalculateActivity.ScoreResult {
        // 七対子の場合の点数を計算
        val fu = 25
        var han = 2

        // ドラの数に応じて翻数を調整する
        han += doraCount

        val basePoint = when (han) {
            2 -> {
                if (!isKid) 2400 else 1600
            }
            3 -> {
                if (!isKid) 4800 else 3200
            }
            4 -> {
                if (!isKid) 9600 else 6400
            }
            else -> {
                if (!isKid) 12000 else 8000
            }
        }
        return CalculateActivity.ScoreResult(fu, han, basePoint)
    }
}