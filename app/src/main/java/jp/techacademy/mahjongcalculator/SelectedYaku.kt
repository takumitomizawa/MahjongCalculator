package jp.techacademy.mahjongcalculator

class SelectedYaku(private val tiles: List<MahjongTile>) {

    fun isTitoitsu(): Boolean {
        val tileCountMap = mutableMapOf<MahjongTile, Int>()

        // 手牌を数える
        for (tile in tiles) {
            if (tileCountMap.containsKey(tile)) {
                tileCountMap[tile] = tileCountMap[tile]!! + 1
            } else {
                tileCountMap[tile] = 1
            }
        }

        // 対子が7セット揃っているかチェック
        var pairCount = 0
        for (count in tileCountMap.values) {
            if (count == 2) {
                pairCount++
            }
        }
        return pairCount == 7
    }

    fun isPinfu(): Boolean{
        // 字牌が含まれる場合は平和ではない
        for (tile in tiles){
            if (tile.tileType == "ji"){
                return false
            }
        }

        // 両面待ち以外の形である場合は平和ではない
        for (i in 0 until tiles.size - 2) {
            val currentTile = tiles[i]
            val nextTile = tiles[i + 1]
            val followingTile = tiles[i + 2]

            if (currentTile.number +1 == nextTile.number && nextTile.number == followingTile.number -1){
                return true
            }
        }

        return false
    }
}
