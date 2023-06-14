package jp.techacademy.mahjongcalculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.techacademy.mahjongcalculator.databinding.ActivityCalculateBinding

class CalculateActivity : AppCompatActivity() {

    private lateinit var recyclerViewResultHand: RecyclerView
    private lateinit var resultAdapter: TileAdapter
    private lateinit var binding: ActivityCalculateBinding
    private lateinit var parentChangeSwitch: Switch
    private lateinit var goalChangeSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewResultHand = binding.recyclerViewResultHand
        recyclerViewResultHand.layoutManager = GridLayoutManager(this, 14)

        val selectedTiles = intent.getParcelableArrayListExtra<MahjongTile>("selectedTiles")
        resultAdapter = selectedTiles?.let { TileAdapter(it) }!!
        binding.recyclerViewResultHand.adapter = resultAdapter

        var textViewResult = binding.textViewResult
        val isKid = intent.getBooleanExtra("parentCheck", false)
        val isTsumo = intent.getBooleanExtra("goalCheck", false)
        val isDoraCount = intent.getIntExtra("doraCount", 0)

        // SettingActivity.ktからCheckBoxの情報を取得
        val isReach = intent.getBooleanExtra("isReach", false)
        val isDoubleReach = intent.getBooleanExtra("isDoubleReach", false)
        val isOne = intent.getBooleanExtra("isOne", false)
        val isChankan = intent.getBooleanExtra("isChankan", false)
        val isRinshan = intent.getBooleanExtra("isRinshan", false)
        val isHoutei = intent.getBooleanExtra("isHoutei", false)
        val isHaitei = intent.getBooleanExtra("isHaitei", false)
        val isTenhou = intent.getBooleanExtra("isTenhou", false)
        val isTihou = intent.getBooleanExtra("isTihou", false)
        val isSelectedUpTile = intent.getParcelableArrayListExtra<MahjongTile>("selectedUpTile")

        val yakuList = mutableListOf<String>()

        // 受け取った各CheckBoxの状態に応じて処理を行う
        if (isReach){
            yakuList.add("リーチ")
        }
        if (isDoubleReach){
            yakuList.add("ダブルリーチ")
        }

        val linearLayoutColumn1 = binding.yakuColumn1
        val linearLayoutColumn2 = binding.yakuColumn2

        val textSizeInDp = 25

        for ((index, yaku) in yakuList.withIndex()) {
            val textView = TextView(this)
            textView.text = yaku
            textView.textSize = textSizeInDp.toFloat()
            textView.setTextColor(Color.WHITE)
            if (index < 5) {
                linearLayoutColumn1.addView(textView)
            } else {
                linearLayoutColumn2.addView(textView)
            }
        }

        val scoreResult = ScoreCalculator(selectedTiles).calculateScore(isKid, isTsumo, isDoraCount)
        val resultText = formatResult(scoreResult, isKid)
        textViewResult.text = resultText
    }

    private fun isTitoitsu(tiles: List<MahjongTile>): ScoreResult {
        val tileCountMap = mutableMapOf<MahjongTile, Int>()
        val isKid = intent.getBooleanExtra("parentCheck", false)
        val isDoraCount = intent.getIntExtra("doraCount", 0)

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
            han += isDoraCount

            val basePoint = when(han){
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

            CalculateActivity.ScoreResult(fu, han, basePoint)
        } else{
            CalculateActivity.ScoreResult(0, 0, 0)
        }
    }

    private fun formatResult(scoreResult: ScoreResult, isKid: Boolean): String {
        val role = if (!isKid) "親" else "子"
        return "$role${scoreResult.fu}符 ${scoreResult.han}翻 ${scoreResult.points}点"
    }

    data class ScoreResult(val fu: Int, val han: Int, val points: Int)
}