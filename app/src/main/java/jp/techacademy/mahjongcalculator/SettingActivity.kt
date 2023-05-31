package jp.techacademy.mahjongcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import jp.techacademy.mahjongcalculator.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var recyclerViewHand: RecyclerView
    //private lateinit var handAdapter: MainActivity.TileAdapter

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }
}