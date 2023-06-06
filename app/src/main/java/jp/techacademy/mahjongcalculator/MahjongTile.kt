package jp.techacademy.mahjongcalculator

data class MahjongTile(
    val number: Int,
    val tileType: String,
    val isBack: Boolean,
    val isRevealed: Boolean,
    val imageResourceId: Int
)