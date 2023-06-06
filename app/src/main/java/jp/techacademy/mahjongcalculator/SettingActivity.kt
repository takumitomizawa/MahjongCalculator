package jp.techacademy.mahjongcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.techacademy.mahjongcalculator.databinding.ActivityMainBinding
import jp.techacademy.mahjongcalculator.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var recyclerViewSettingHand: RecyclerView
    private lateinit var settingAdapter: TileAdapter

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewSettingHand.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val selectedTiles = intent.getParcelableArrayListExtra<MahjongTile>("selectedTiles")
        settingAdapter = selectedTiles?.let { TileAdapter(it) }!!
        //handAdapter = TileAdapter(selectedTiles)
        binding.recyclerViewSettingHand.adapter = settingAdapter
    }
}