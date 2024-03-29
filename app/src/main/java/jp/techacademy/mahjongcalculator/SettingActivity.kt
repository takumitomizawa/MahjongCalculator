package jp.techacademy.mahjongcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import jp.techacademy.mahjongcalculator.databinding.ActivitySettingBinding
import kotlin.math.round

class SettingActivity : AppCompatActivity() {

    private lateinit var recyclerViewSettingHand: RecyclerView
    private lateinit var settingAdapter: SettingTileAdapter
    private lateinit var binding: ActivitySettingBinding
    private var selectedTile: MahjongTile? = null
    private var doraCount: Int = 0
    private var roundCount: Int = 0
    private var selectedTiles: ArrayList<MahjongTile>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewSettingHand = binding.recyclerViewSettingHand

        val selectedTiles = intent.getParcelableArrayListExtra<MahjongTile>("selectedTiles")
        if (selectedTiles != null) {
            recyclerViewSettingHand.layoutManager = GridLayoutManager(this, selectedTiles.size)
        }

        settingAdapter = selectedTiles?.let { SettingTileAdapter(it) }!!
        binding.recyclerViewSettingHand.adapter = settingAdapter

        settingAdapter.setOnTileClickListener { tile ->
            // クリックイベントの処理
            // 赤枠の表示処理を行う
            selectedTiles.let {
                val position = it.indexOf(tile)
                if (position != -1) {
                    settingAdapter.setSelectedPosition(position)
                }
            }
        }

        // 初期状態でRadioButtonを東でセットしておく
        binding.eastRadioButton.isChecked = true
        binding.eastOwnRadioButton.isChecked = true

        binding.checkBoxOne.isEnabled = false

        // ここから各ボタンが押された時の処理
        binding.checkBoxReach.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkBoxDoubleReach.isChecked = false
                binding.checkBoxOne.isEnabled = true
            } else if (!binding.checkBoxDoubleReach.isChecked) {
                binding.checkBoxOne.isEnabled = false
                binding.checkBoxOne.isChecked = false
            }
        }
        binding.checkBoxDoubleReach.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkBoxReach.isChecked = false
                binding.checkBoxOne.isEnabled = true
                binding.checkBoxHaitei.isEnabled = false
            } else if (!binding.checkBoxReach.isChecked) {
                binding.checkBoxOne.isEnabled = false
                binding.checkBoxOne.isChecked = false
                binding.checkBoxHaitei.isEnabled = true
            }
        }

        binding.checkBoxHaitei.setOnClickListener {
            binding.checkBoxHoutei.isChecked = false
            binding.checkBoxTihou.isChecked = false
            binding.checkBoxTenhou.isChecked = false
            binding.checkBoxRinshan.isChecked = false
            binding.checkBoxChankan.isChecked = false
            binding.goalChangeSwitch.isChecked = false
        }
        binding.checkBoxHoutei.setOnClickListener {
            binding.checkBoxHaitei.isChecked = false
            binding.checkBoxTihou.isChecked = false
            binding.checkBoxTenhou.isChecked = false
            binding.checkBoxRinshan.isChecked = false
            binding.checkBoxChankan.isChecked = false
            binding.goalChangeSwitch.isChecked = true
        }

        binding.checkBoxTenhou.setOnClickListener {
            binding.checkBoxTihou.isChecked = false
            binding.checkBoxHoutei.isChecked = false
            binding.checkBoxHaitei.isChecked = false
            binding.checkBoxRinshan.isChecked = false
            binding.checkBoxChankan.isChecked = false
            binding.goalChangeSwitch.isChecked = false
        }
        binding.checkBoxTihou.setOnClickListener {
            binding.checkBoxTenhou.isChecked = false
            binding.checkBoxHoutei.isChecked = false
            binding.checkBoxHaitei.isChecked = false
            binding.checkBoxRinshan.isChecked = false
            binding.checkBoxChankan.isChecked = false
            binding.goalChangeSwitch.isChecked = false
        }

        binding.checkBoxRinshan.setOnClickListener {
            binding.checkBoxTihou.isChecked = false
            binding.checkBoxTenhou.isChecked = false
            binding.checkBoxHoutei.isChecked = false
            binding.checkBoxHaitei.isChecked = false
            binding.checkBoxChankan.isChecked = false
            binding.goalChangeSwitch.isChecked = false
        }

        binding.checkBoxChankan.setOnClickListener {
            binding.checkBoxHaitei.isEnabled = !binding.checkBoxChankan.isChecked
            binding.checkBoxTihou.isChecked = false
            binding.checkBoxTenhou.isChecked = false
            binding.checkBoxHoutei.isChecked = false
            binding.checkBoxHaitei.isChecked = false
            binding.checkBoxRinshan.isChecked = false
            binding.goalChangeSwitch.isChecked = true
        }

        binding.nextToResultButton.setOnClickListener {
            if (selectedTile != null){
                navigateToCalculateActivity()
            } else {
                Toast.makeText(applicationContext, "あがり牌を選択してください。", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backToFirstButton.setOnClickListener {
            finish()
        }

        binding.doraUpImageView.setOnClickListener {
            doraCount++
            updateDoraTextView()
        }
        binding.doraDownImageView.setOnClickListener {
            if (doraCount > 0) {
                doraCount--
                updateDoraTextView()
            }
        }

        binding.upImageView.setOnClickListener {
            roundCount++
            updateRoundTextView()
        }
        binding.downImageView.setOnClickListener {
            if (roundCount > 0) {
                roundCount--
                updateRoundTextView()
            }
        }

        binding.eastRadioButton.setOnClickListener {
            binding.southRadioButton.isChecked = false
            binding.westRadioButton.isChecked = false
            binding.northRadioButton.isChecked = false
        }
        binding.southRadioButton.setOnClickListener {
            binding.eastRadioButton.isChecked = false
            binding.westRadioButton.isChecked = false
            binding.northRadioButton.isChecked = false
        }
        binding.westRadioButton.setOnClickListener {
            binding.southRadioButton.isChecked = false
            binding.eastRadioButton.isChecked = false
            binding.northRadioButton.isChecked = false
        }
        binding.northRadioButton.setOnClickListener {
            binding.southRadioButton.isChecked = false
            binding.westRadioButton.isChecked = false
            binding.eastRadioButton.isChecked = false
        }

        binding.eastOwnRadioButton.setOnClickListener {
            binding.southOwnRadioButton.isChecked = false
            binding.westOwnRadioButton.isChecked = false
            binding.northOwnRadioButton.isChecked = false
        }
        binding.southOwnRadioButton.setOnClickListener {
            binding.eastOwnRadioButton.isChecked = false
            binding.westOwnRadioButton.isChecked = false
            binding.northOwnRadioButton.isChecked = false
        }
        binding.westOwnRadioButton.setOnClickListener {
            binding.southOwnRadioButton.isChecked = false
            binding.eastOwnRadioButton.isChecked = false
            binding.northOwnRadioButton.isChecked = false
        }
        binding.northOwnRadioButton.setOnClickListener {
            binding.southOwnRadioButton.isChecked = false
            binding.westOwnRadioButton.isChecked = false
            binding.eastOwnRadioButton.isChecked = false
        }

        settingAdapter.setOnTileClickListener { tile ->
            selectedTile = tile
            val position = selectedTiles.indexOf(tile)
            if (position != -1) {
                settingAdapter.setSelectedPosition(position)
            }
        }


        // ここまで各ボタンが押された時の処理
    }

    private fun navigateToCalculateActivity(){
        selectedTiles = intent.getParcelableArrayListExtra<MahjongTile>("selectedTiles")

        val intent = Intent(this, CalculateActivity::class.java)

        if (binding.checkBoxReach.isChecked){
            intent.putExtra("isReach", binding.checkBoxReach.isChecked)
        }
        if (binding.checkBoxDoubleReach.isChecked){
            intent.putExtra("isDoubleReach", binding.checkBoxDoubleReach.isChecked)
        }
        if (binding.checkBoxOne.isChecked){
            intent.putExtra("isOne", binding.checkBoxOne.isChecked)
        }
        if (binding.checkBoxChankan.isChecked){
            intent.putExtra("isChankan", binding.checkBoxChankan.isChecked)
        }
        if (binding.checkBoxRinshan.isChecked){
            intent.putExtra("isRinshan", binding.checkBoxRinshan.isChecked)
        }
        if (binding.checkBoxHoutei.isChecked){
            intent.putExtra("isReach", binding.checkBoxHoutei.isChecked)
        }
        if (binding.checkBoxHaitei.isChecked){
            intent.putExtra("isHaitei",binding.checkBoxHaitei.isChecked)
        }
        if (binding.checkBoxTenhou.isChecked){
            intent.putExtra("isTenhou",binding.checkBoxTenhou.isChecked)
        }
        if (binding.checkBoxTihou.isChecked){
            intent.putExtra("isTihou",binding.checkBoxTihou.isChecked)
        }

        // 親か子か判断するために使う
        intent.putExtra("parentCheck", binding.parentChangeSwitch.isChecked)

        // ツモかロンか判断するために使う
        intent.putExtra("goalCheck", binding.goalChangeSwitch.isChecked)

        // ドラの数を判断するために使う
        intent.putExtra("doraCount", doraCount)

        // 本場を判断するために使う
        intent.putExtra("roundCount", roundCount)

        // あがり牌が何か判断するために使う
        val selectedUpTiles = ArrayList<MahjongTile>()
        // 牌のデータが配列なので、ArrayListに格納してから送る
        selectedTile?.let { selectedUpTiles.add(it) }

        intent.putParcelableArrayListExtra("selectedUpTile", ArrayList(selectedUpTiles))

        intent.putParcelableArrayListExtra("selectedTiles", selectedTiles?.let { ArrayList(it) })
        startActivity(intent)
    }

    private fun updateDoraTextView(){
        binding.doraTextView.text = "ドラ$doraCount"
    }

    private fun updateRoundTextView(){
        binding.roundTextView.text = "${roundCount}本場"
    }

}