package jp.techacademy.mahjongcalculator

data class Yaku(val name: String)

object YakuList{
    val yakuList: List<Yaku> = listOf(
        Yaku("リーチ"),
        Yaku("一発"),
        Yaku("メンゼンツモ"),
        Yaku("一盃口"),
        Yaku("チャンタ"),
        Yaku("三色同順")
    )
}