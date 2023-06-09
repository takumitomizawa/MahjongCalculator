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

class CalculateActivity : AppCompatActivity() {

    private lateinit var recyclerViewResultHand: RecyclerView
    private lateinit var resultAdapter: TileAdapter
    private lateinit var binding: ActivityCalculateBinding
    private var isShowingYaku = false

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
        val testYakuList = YakuList.yakuList.map { it.name }

        val linearLayoutColumn1 = binding.yakuColumn1
        val linearLayoutColumn2 = binding.yakuColumn2
        val switch = binding.yakuSwitch

        val textSizeInDp = 25

        switch.setOnCheckedChangeListener{ _, isChecked ->
            isShowingYaku = isChecked

            linearLayoutColumn1.visibility = if (isShowingYaku) View.VISIBLE else View.GONE
            linearLayoutColumn2.visibility = if (isShowingYaku) View.VISIBLE else View.GONE
        }

        linearLayoutColumn1.visibility = View.GONE
        linearLayoutColumn2.visibility = View.GONE

        for ((index, yaku) in testYakuList.withIndex()) {
            val textView = TextView(this)
            textView.text = yaku
            textView.textSize = textSizeInDp.toFloat()
            textView.setTextColor(Color.WHITE)

            if (index < 5) {
                linearLayoutColumn1.addView(textView)
                if (isShowingYaku){
                    textView.visibility = View.VISIBLE
                } else {
                    textView.visibility = View.GONE
                }
            } else {
                linearLayoutColumn2.addView(textView)
                if (isShowingYaku){
                    textView.visibility = View.VISIBLE
                } else {
                    textView.visibility = View.GONE
                }
            }
        }
    }
}