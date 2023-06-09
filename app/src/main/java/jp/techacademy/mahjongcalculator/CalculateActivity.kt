package jp.techacademy.mahjongcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    }
}