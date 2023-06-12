package jp.techacademy.mahjongcalculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.utilities.Score
import jp.techacademy.mahjongcalculator.databinding.ActivityCalculateBinding
import jp.techacademy.mahjongcalculator.databinding.ActivitySettingBinding
import kotlin.math.pow

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
        val isParent = parentChangeSwitch.isChecked
        val isTsumo = goalChangeSwitch.isChecked

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

        val scoreResult = TitoitsuScoreCalculator(selectedTiles).calculateScore(isParent, isTsumo)
        val resultText = formatResult(scoreResult, isParent)
        textViewResult.text = resultText
    }

    private fun formatResult(scoreResult: ScoreResult, isParent: Boolean): String {
        val role = if (isParent) "親" else "子"
        return "$role${scoreResult.fu}符 ${scoreResult.han}翻 ${scoreResult.points}点"
    }

    data class ScoreResult(val fu: Int, val han: Int, val points: Int)
}