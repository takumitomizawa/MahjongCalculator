package jp.techacademy.mahjongcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.quicksettings.Tile
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
    private var doraCount: Int = 0
    private var roundCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewSettingHand = binding.recyclerViewSettingHand
        recyclerViewSettingHand.layoutManager = GridLayoutManager(this, 14)

        val selectedTiles = intent.getParcelableArrayListExtra<MahjongTile>("selectedTiles")
        settingAdapter = selectedTiles?.let { TileAdapter(it) }!!
        binding.recyclerViewSettingHand.adapter = settingAdapter

        // 初期状態でRadioButtonを東でセットしておく
        binding.eastRadioButton.isChecked = true
        binding.eastOwnRadioButton.isChecked = true

        binding.checkBoxOne.isEnabled = false

        // ここから各ボタンが押された時の処理
        binding.nextToResultButton.setOnClickListener{
            val intent = Intent(this, CalculateActivity::class.java)
            intent.putParcelableArrayListExtra("selectedTiles", ArrayList(selectedTiles))
            startActivity(intent)
        }

        binding.checkBoxReach.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                binding.checkBoxDoubleReach.isChecked = false
                binding.checkBoxOne.isEnabled = true
            } else if (!binding.checkBoxDoubleReach.isChecked){
                    binding.checkBoxOne.isEnabled = false
            }
        }
        binding.checkBoxDoubleReach.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                binding.checkBoxReach.isChecked = false
                binding.checkBoxOne.isEnabled = true
            } else if (!binding.checkBoxReach.isChecked){
                binding.checkBoxOne.isEnabled = false
            }
        }

        binding.checkBoxHaitei.setOnClickListener{
            binding.checkBoxHoutei.isChecked = false
            binding.checkBoxTihou.isChecked = false
            binding.checkBoxTenhou.isChecked = false
            binding.checkBoxRinshan.isChecked = false
            binding.checkBoxChankan.isChecked = false
            binding.goalChangeSwitch.isChecked = false
        }
        binding.checkBoxHoutei.setOnClickListener{
            binding.checkBoxHaitei.isChecked = false
            binding.checkBoxTihou.isChecked = false
            binding.checkBoxTenhou.isChecked = false
            binding.checkBoxRinshan.isChecked = false
            binding.checkBoxChankan.isChecked = false
            binding.goalChangeSwitch.isChecked = true
        }

        binding.checkBoxTenhou.setOnClickListener{
            binding.checkBoxTihou.isChecked = false
            binding.checkBoxHoutei.isChecked = false
            binding.checkBoxHaitei.isChecked = false
            binding.checkBoxRinshan.isChecked = false
            binding.checkBoxChankan.isChecked = false
            binding.goalChangeSwitch.isChecked = false
        }
        binding.checkBoxTihou.setOnClickListener{
            binding.checkBoxTenhou.isChecked = false
            binding.checkBoxHoutei.isChecked = false
            binding.checkBoxHaitei.isChecked = false
            binding.checkBoxRinshan.isChecked = false
            binding.checkBoxChankan.isChecked = false
            binding.goalChangeSwitch.isChecked = false
        }

        binding.checkBoxRinshan.setOnClickListener{
            binding.checkBoxTihou.isChecked = false
            binding.checkBoxTenhou.isChecked = false
            binding.checkBoxHoutei.isChecked = false
            binding.checkBoxHaitei.isChecked = false
            binding.checkBoxChankan.isChecked = false
            binding.goalChangeSwitch.isChecked = false
        }

        binding.checkBoxChankan.setOnClickListener{
            binding.checkBoxTihou.isChecked = false
            binding.checkBoxTenhou.isChecked = false
            binding.checkBoxHoutei.isChecked = false
            binding.checkBoxHaitei.isChecked = false
            binding.checkBoxRinshan.isChecked = false
            binding.goalChangeSwitch.isChecked = true
        }

        binding.backToFirstButton.setOnClickListener{
            finish()
        }

        binding.doraUpImageView.setOnClickListener{
            doraCount++
            updateDoraTextView()
        }
        binding.doraDownImageView.setOnClickListener{
            if (doraCount > 0){
                doraCount--
                updateDoraTextView()
            }
        }

        binding.upImageView.setOnClickListener{
            roundCount++
            updateRoundTextView()
        }
        binding.downImageView.setOnClickListener{
            if (roundCount > 0){
                roundCount--
                updateRoundTextView()
            }
        }

        binding.eastRadioButton.setOnClickListener{
            binding.southRadioButton.isChecked = false
            binding.westRadioButton.isChecked = false
            binding.northRadioButton.isChecked = false
        }
        binding.southRadioButton.setOnClickListener{
            binding.eastRadioButton.isChecked = false
            binding.westRadioButton.isChecked = false
            binding.northRadioButton.isChecked = false
        }
        binding.westRadioButton.setOnClickListener{
            binding.southRadioButton.isChecked = false
            binding.eastRadioButton.isChecked = false
            binding.northRadioButton.isChecked = false
        }
        binding.northRadioButton.setOnClickListener{
            binding.southRadioButton.isChecked = false
            binding.westRadioButton.isChecked = false
            binding.eastRadioButton.isChecked = false
        }

        binding.eastOwnRadioButton.setOnClickListener{
            binding.southOwnRadioButton.isChecked = false
            binding.westOwnRadioButton.isChecked = false
            binding.northOwnRadioButton.isChecked = false
        }
        binding.southOwnRadioButton.setOnClickListener{
            binding.eastOwnRadioButton.isChecked = false
            binding.westOwnRadioButton.isChecked = false
            binding.northOwnRadioButton.isChecked = false
        }
        binding.westOwnRadioButton.setOnClickListener{
            binding.southOwnRadioButton.isChecked = false
            binding.eastOwnRadioButton.isChecked = false
            binding.northOwnRadioButton.isChecked = false
        }
        binding.northOwnRadioButton.setOnClickListener{
            binding.southOwnRadioButton.isChecked = false
            binding.westOwnRadioButton.isChecked = false
            binding.eastOwnRadioButton.isChecked = false
        }

        // ここまで各ボタンが押された時の処理
    }

    private fun updateDoraTextView(){
        binding.doraTextView.text = "ドラ$doraCount"
    }

    private fun updateRoundTextView(){
        binding.roundTextView.text = "${roundCount}本場"
    }
}