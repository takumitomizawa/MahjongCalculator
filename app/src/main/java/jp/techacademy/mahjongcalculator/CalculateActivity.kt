package jp.techacademy.mahjongcalculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.techacademy.mahjongcalculator.databinding.ActivityCalculateBinding
import jp.techacademy.mahjongcalculator.databinding.ActivitySettingBinding
import kotlin.math.pow

class CalculateActivity : AppCompatActivity() {

    private lateinit var recyclerViewResultHand: RecyclerView
    private lateinit var resultAdapter: TileAdapter
    private lateinit var binding: ActivityCalculateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewResultHand = binding.recyclerViewResultHand
        recyclerViewResultHand.layoutManager = GridLayoutManager(this, 14)

        val selectedTiles = intent.getParcelableArrayListExtra<MahjongTile>("selectedTiles")
        resultAdapter = selectedTiles?.let { TileAdapter(it) }!!
        binding.recyclerViewResultHand.adapter = resultAdapter

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

        // テスト用の役のデータリストを作成
        //val yakuList = YakuList.yakuList.map { it.name }
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

        val score = calculateTitoitsuScore(selectedTiles)
    }

    // 七対子の点数計算
    private fun calculateTitoitsuScore(tiles: List<MahjongTile>): Int {
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

        return finalPoints
    }
}