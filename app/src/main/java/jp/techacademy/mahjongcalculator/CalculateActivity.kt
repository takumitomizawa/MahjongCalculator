package jp.techacademy.mahjongcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.techacademy.mahjongcalculator.databinding.ActivityCalculateBinding
import jp.techacademy.mahjongcalculator.databinding.ActivitySettingBinding

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

        // テスト用の役のデータリストを作成
        val testYakuList = listOf("役1", "役2", "役3", "役4", "役5", "役6", "役7")

        val linearLayoutColumn1 = binding.yakuColumn1
        val linearLayoutColumn2 = binding.yakuColumn2

        for ((index, yaku) in testYakuList.withIndex()) {
            val textView = TextView(this)
            textView.text = yaku

            if (index % 2 == 0) {
                linearLayoutColumn1.addView(textView)
            } else {
                linearLayoutColumn2.addView(textView)
            }
        }
    }
}