package jp.techacademy.mahjongcalculator

import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.techacademy.mahjongcalculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewHand: RecyclerView
    private lateinit var recyclerViewManzu: RecyclerView
    private lateinit var recyclerViewPinzu: RecyclerView
    private lateinit var recyclerViewSouzu: RecyclerView
    private lateinit var recyclerViewJi: RecyclerView

    private lateinit var handAdapter: TileAdapter
    private lateinit var manzuAdapter: TileAdapter
    private lateinit var pinzuAdapter: TileAdapter
    private lateinit var souzuAdapter: TileAdapter
    private lateinit var jiAdapter: TileAdapter

    private lateinit var binding: ActivityMainBinding

    private val selectedTiles = mutableListOf<MahjongTile>()

    private var isSyuntsuButtonPressed = false
    private var isKoutsuButtonPressed = false
    private var isTitoitsuButtonPressed = false
    private var isPonButtonPressed = false
    private var isTiButtonPressed = false
    private var isAnkanButtonPressed = false
    private var isMinkanButtonPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ダイアログの表示
        showCustomDialog()

        recyclerViewHand = binding.recyclerViewHand
        recyclerViewManzu = binding.recyclerViewManzu
        recyclerViewPinzu = binding.recyclerViewPinzu
        recyclerViewSouzu = binding.recyclerViewSouzu
        recyclerViewJi = binding.recyclerViewJi

        recyclerViewHand.layoutManager = GridLayoutManager(this, 14)
        recyclerViewManzu.layoutManager = GridLayoutManager(this, 9)
        recyclerViewPinzu.layoutManager = GridLayoutManager(this, 9)
        recyclerViewSouzu.layoutManager = GridLayoutManager(this, 9)
        recyclerViewJi.layoutManager = GridLayoutManager(this, 9)

        handAdapter = TileAdapter(selectedTiles)
        manzuAdapter = TileAdapter(getTiles(getString(R.string.main_key_manzu)))
        pinzuAdapter = TileAdapter(getTiles(getString(R.string.main_key_pinzu)))
        souzuAdapter = TileAdapter(getTiles(getString(R.string.main_key_souzu)))
        jiAdapter = TileAdapter(getTiles(getString(R.string.main_key_ji)))

        recyclerViewHand.adapter = handAdapter
        recyclerViewManzu.adapter = manzuAdapter
        recyclerViewPinzu.adapter = pinzuAdapter
        recyclerViewSouzu.adapter = souzuAdapter
        recyclerViewJi.adapter = jiAdapter

        val initialSpanCount = 14
        val itemWidth = resources.getDimensionPixelSize(R.dimen.item_width)

        val layoutManager = GridLayoutManager(this, initialSpanCount)
        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position < handAdapter.itemCount) 1 else layoutManager.spanCount
            }
        }
        layoutManager.spanSizeLookup = spanSizeLookup

        recyclerViewHand.layoutManager = layoutManager
        recyclerViewHand.layoutParams.width = itemWidth * initialSpanCount

        manzuAdapter.setOnTileClickListener { tile ->
            if (isSyuntsuButtonPressed) {
                SyuntsuButtonState(tile)
            } else if (isKoutsuButtonPressed) {
                KoutsuButtonState(tile)
            } else if (isTitoitsuButtonPressed) {
                TitoitsuButtonState(tile)
            } else if (isPonButtonPressed) {
                PonButtonState(tile)
            } else if (isTiButtonPressed) {
                TiButtonState(tile)
            } else if (isAnkanButtonPressed || isMinkanButtonPressed) {
                kanButtonState(tile)
            } else if (selectedTiles.isEmpty()) {
                headTileState(tile)
            }
        }

        pinzuAdapter.setOnTileClickListener { tile ->
            if (isSyuntsuButtonPressed) {
                SyuntsuButtonState(tile)
            } else if (isKoutsuButtonPressed) {
                KoutsuButtonState(tile)
            } else if (isTitoitsuButtonPressed) {
                TitoitsuButtonState(tile)
            } else if (isPonButtonPressed) {
                PonButtonState(tile)
            } else if (isTiButtonPressed) {
                TiButtonState(tile)
            } else if (isAnkanButtonPressed) {
                kanButtonState(tile)
            } else if (isMinkanButtonPressed) {
                kanButtonState(tile)
            }
        }

        souzuAdapter.setOnTileClickListener { tile ->
            if (isSyuntsuButtonPressed) {
                SyuntsuButtonState(tile)
            } else if (isKoutsuButtonPressed) {
                KoutsuButtonState(tile)
            } else if (isTitoitsuButtonPressed) {
                TitoitsuButtonState(tile)
            } else if (isPonButtonPressed) {
                PonButtonState(tile)
            } else if (isTiButtonPressed) {
                TiButtonState(tile)
            } else if (isAnkanButtonPressed) {
                kanButtonState(tile)
            } else if (isMinkanButtonPressed) {
                kanButtonState(tile)
            }
        }

        jiAdapter.setOnTileClickListener { tile ->
            if (isKoutsuButtonPressed) {
                KoutsuButtonState(tile)
            } else if (isTitoitsuButtonPressed) {
                TitoitsuButtonState(tile)
            } else if (isAnkanButtonPressed) {
                kanButtonState(tile)
            } else if (isMinkanButtonPressed) {
                kanButtonState(tile)
            }
        }

        binding.resetButton.setOnClickListener {
            binding.syuntsuButton.isChecked = false
            binding.koutsuButton.isChecked = false
            binding.titoitsuButton.isChecked = false
            binding.ponButton.isChecked = false
            binding.tiButton.isChecked = false
            binding.minkanButton.isChecked = false
            binding.ankanButton.isChecked = false
            isSyuntsuButtonPressed = false
            isKoutsuButtonPressed = false
            isTitoitsuButtonPressed = false
            isPonButtonPressed = false
            isTiButtonPressed = false
            isMinkanButtonPressed = false
            isAnkanButtonPressed = false
            selectedTiles.clear()
            recyclerViewHand.layoutManager = GridLayoutManager(this, 14)
            recyclerViewHand.adapter = handAdapter
            handAdapter.notifyDataSetChanged()
        }

        binding.nextButton.setOnClickListener {
            if (selectedTiles.size in 14..18) {
                val intent = Intent(this, SettingActivity::class.java)
                intent.putParcelableArrayListExtra("selectedTiles", ArrayList(selectedTiles))
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "麻雀牌が正しく登録されていません", Toast.LENGTH_SHORT).show()
            }
        }

        binding.syuntsuButton.setOnClickListener {
            if (selectedTiles.isNotEmpty()) {
                // 順子ボタンが押された場合、モードを変更(例.trueならばfalseへ変更)
                isSyuntsuButtonPressed = !isSyuntsuButtonPressed
                binding.koutsuButton.isChecked = false
                binding.titoitsuButton.isChecked = false
                binding.ponButton.isChecked = false
                binding.tiButton.isChecked = false
                binding.minkanButton.isChecked = false
                binding.ankanButton.isChecked = false
                isKoutsuButtonPressed = false
                isTitoitsuButtonPressed = false
                isPonButtonPressed = false
                isTiButtonPressed = false
                isMinkanButtonPressed = false
                isAnkanButtonPressed = false
            } else {
                binding.syuntsuButton.isChecked = false
                isSyuntsuButtonPressed = false
                Toast.makeText(applicationContext, "頭の牌が未選択です。", Toast.LENGTH_SHORT).show()
            }
        }

        binding.koutsuButton.setOnClickListener {
            if (selectedTiles.isNotEmpty()) {
                // 刻子ボタンが押された場合、モードを変更(例.trueならばfalseへ変更)
                isKoutsuButtonPressed = !isKoutsuButtonPressed
                binding.syuntsuButton.isChecked = false
                binding.titoitsuButton.isChecked = false
                binding.ponButton.isChecked = false
                binding.tiButton.isChecked = false
                binding.minkanButton.isChecked = false
                binding.ankanButton.isChecked = false
                isSyuntsuButtonPressed = false
                isTitoitsuButtonPressed = false
                isPonButtonPressed = false
                isTiButtonPressed = false
                isMinkanButtonPressed = false
                isAnkanButtonPressed = false
            } else {
                binding.koutsuButton.isChecked = false
                isKoutsuButtonPressed = false
                Toast.makeText(applicationContext, "頭の牌が未選択です。", Toast.LENGTH_SHORT).show()
            }

        }

        binding.titoitsuButton.setOnClickListener {
            if (selectedTiles.isNotEmpty()) {
                isTitoitsuButtonPressed = !isTitoitsuButtonPressed
                binding.syuntsuButton.isChecked = false
                binding.koutsuButton.isChecked = false
                binding.ponButton.isChecked = false
                binding.tiButton.isChecked = false
                binding.minkanButton.isChecked = false
                binding.ankanButton.isChecked = false
                isSyuntsuButtonPressed = false
                isKoutsuButtonPressed = false
                isPonButtonPressed = false
                isTiButtonPressed = false
                isMinkanButtonPressed = false
                isAnkanButtonPressed = false
            } else {
                binding.titoitsuButton.isChecked = false
                isTitoitsuButtonPressed = false
                Toast.makeText(applicationContext, "頭の牌が未選択です。", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ponButton.setOnClickListener {
            if (selectedTiles.isNotEmpty()) {
                isPonButtonPressed = !isPonButtonPressed
                binding.syuntsuButton.isChecked = false
                binding.koutsuButton.isChecked = false
                binding.titoitsuButton.isChecked = false
                binding.tiButton.isChecked = false
                binding.minkanButton.isChecked = false
                binding.ankanButton.isChecked = false
                isSyuntsuButtonPressed = false
                isKoutsuButtonPressed = false
                isTitoitsuButtonPressed = false
                isTiButtonPressed = false
                isMinkanButtonPressed = false
                isAnkanButtonPressed = false
            } else {
                binding.ponButton.isChecked = false
                isPonButtonPressed = false
                Toast.makeText(applicationContext, "頭の牌が未選択です。", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tiButton.setOnClickListener {
            if (selectedTiles.isNotEmpty()) {
                isTiButtonPressed = !isTiButtonPressed
                binding.syuntsuButton.isChecked = false
                binding.koutsuButton.isChecked = false
                binding.titoitsuButton.isChecked = false
                binding.ponButton.isChecked = false
                binding.minkanButton.isChecked = false
                binding.ankanButton.isChecked = false
                isSyuntsuButtonPressed = false
                isKoutsuButtonPressed = false
                isTitoitsuButtonPressed = false
                isPonButtonPressed = false
                isMinkanButtonPressed = false
                isAnkanButtonPressed = false
            } else {
                binding.tiButton.isChecked = false
                isTiButtonPressed = false
                Toast.makeText(applicationContext, "頭の牌が未選択です。", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ankanButton.setOnClickListener {
            if (selectedTiles.isNotEmpty()) {
                isAnkanButtonPressed = !isAnkanButtonPressed
                binding.syuntsuButton.isChecked = false
                binding.koutsuButton.isChecked = false
                binding.titoitsuButton.isChecked = false
                binding.ponButton.isChecked = false
                binding.tiButton.isChecked = false
                binding.minkanButton.isChecked = false
                isSyuntsuButtonPressed = false
                isKoutsuButtonPressed = false
                isTitoitsuButtonPressed = false
                isPonButtonPressed = false
                isTiButtonPressed = false
                isMinkanButtonPressed = false
            } else {
                binding.ankanButton.isChecked = false
                isAnkanButtonPressed = false
                Toast.makeText(applicationContext, "頭の牌が未選択です。", Toast.LENGTH_SHORT).show()
            }
        }

        binding.minkanButton.setOnClickListener {
            if (selectedTiles.isNotEmpty()) {
                isMinkanButtonPressed = !isMinkanButtonPressed
                binding.syuntsuButton.isChecked = false
                binding.koutsuButton.isChecked = false
                binding.titoitsuButton.isChecked = false
                binding.ponButton.isChecked = false
                binding.tiButton.isChecked = false
                binding.ankanButton.isChecked = false
                isSyuntsuButtonPressed = false
                isKoutsuButtonPressed = false
                isTitoitsuButtonPressed = false
                isPonButtonPressed = false
                isTiButtonPressed = false
                isAnkanButtonPressed = false
            } else {
                binding.minkanButton.isChecked = false
                isMinkanButtonPressed = false
                Toast.makeText(applicationContext, "頭の牌が未選択です。", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showCustomDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater

        // レイアウトファイルをインフレートしてビューを取得
        val dialogView = inflater.inflate(R.layout.dialog_layout, null)
        dialogBuilder.setView(dialogView)

        // メッセージを表示するTextViewを取得
        val messageTextView = dialogView.findViewById<TextView>(R.id.messageTextView)

        // メッセージを設定
        messageTextView.text = "頭の牌を選択してください"

        // OKボタンを設定
        dialogBuilder.setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
            // OKボタンが押された時の処理
            dialog.dismiss()
        }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun SyuntsuButtonState(tile: MahjongTile) {
        if (isSyuntsuButtonPressed) {
            val tilesToAddForTappedTile = getSequentialTiles(tile.tileType, tile.number)
            selectedTiles.addAll(tilesToAddForTappedTile)
            handAdapter.notifyDataSetChanged()

        } else {
            selectedTiles.add(tile)
            handAdapter.notifyDataSetChanged()
        }
    }

    private fun KoutsuButtonState(tile: MahjongTile) {
        if (isKoutsuButtonPressed) {
            val tilesToAddForTappedTile = getSequentialTiles(tile.tileType, tile.number)
            selectedTiles.addAll(tilesToAddForTappedTile)
            handAdapter.notifyDataSetChanged()

        } else {
            selectedTiles.add(tile)
            handAdapter.notifyDataSetChanged()
        }
    }

    private fun TitoitsuButtonState(tile: MahjongTile) {
        if (isTitoitsuButtonPressed) {
            val tilesToAddForTappedTile = getSequentialTiles(tile.tileType, tile.number)
            selectedTiles.addAll(tilesToAddForTappedTile)
            handAdapter.notifyDataSetChanged()

        } else {
            selectedTiles.add(tile)
            handAdapter.notifyDataSetChanged()
        }
    }

    private fun PonButtonState(tile: MahjongTile) {
        if (isPonButtonPressed) {
            val tilesToAddForTappedTile = getSequentialTiles(tile.tileType, tile.number)
            selectedTiles.addAll(tilesToAddForTappedTile)
            handAdapter.notifyDataSetChanged()

        } else {
            selectedTiles.add(tile)
            handAdapter.notifyDataSetChanged()
        }
    }

    private fun TiButtonState(tile: MahjongTile) {
        if (isTiButtonPressed) {
            val tilesToAddForTappedTile = getSequentialTiles(tile.tileType, tile.number)
            selectedTiles.addAll(tilesToAddForTappedTile)
            handAdapter.notifyDataSetChanged()

        } else {
            selectedTiles.add(tile)
            handAdapter.notifyDataSetChanged()
        }
    }

    private fun kanButtonState(tile: MahjongTile) {
        if (isAnkanButtonPressed) {
            selectedTiles.add(tile)

            val backTile =
                MahjongTile(tile.number, tile.tileType, true, true, R.drawable.tiles_back)
            selectedTiles.add(backTile)

            val backTile2 =
                MahjongTile(tile.number, tile.tileType, true, true, R.drawable.tiles_back)
            selectedTiles.add(backTile2)

            val revealedTile =
                MahjongTile(tile.number, tile.tileType, false, false, tile.imageResourceId)
            selectedTiles.add(revealedTile)

            val layoutManager = recyclerViewHand.layoutManager as GridLayoutManager
            layoutManager.spanCount += 1
            handAdapter.notifyDataSetChanged()

        } else if (isMinkanButtonPressed) {
            val tilesToAddForTappedTile = getSequentialTiles(tile.tileType, tile.number)
            selectedTiles.addAll(tilesToAddForTappedTile)

            val layoutManager = recyclerViewHand.layoutManager as GridLayoutManager
            layoutManager.spanCount += 1
            handAdapter.notifyDataSetChanged()
        }
    }

    private fun headTileState(tile: MahjongTile) {
        if (selectedTiles.isEmpty()) {
            val tilesToAddForTappedTile = getSequentialTiles(tile.tileType, tile.number)
            selectedTiles.addAll(tilesToAddForTappedTile)
            handAdapter.notifyDataSetChanged()

        } else {
            selectedTiles.add(tile)
            handAdapter.notifyDataSetChanged()
        }
    }


    /**
     * 麻雀牌の画像を取得する処理
     * @param tileType 麻雀牌の種類
     * @return List<MahjongTile> 麻雀牌の一覧を返す
     */
    private fun getTiles(tileType: String): List<MahjongTile> {
        val tileList = mutableListOf<MahjongTile>()
        val resourcePrefix = "tiles_"

        // 1-9までの牌を取得する
        for (i in 1..9) {
            val resourceName = resourcePrefix + tileType + "_" + i.toString()
            val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
            val tile = MahjongTile(i, tileType, false, false, resourceId)
            tileList.add(tile)
        }
        return tileList
    }

    private fun getSequentialTiles(tileType: String, tileNumber: Int): List<MahjongTile> {
        val tileList = mutableListOf<MahjongTile>()
        val resourcePrefix = "tiles_"

        if (isSyuntsuButtonPressed && tileNumber < 8) {
            val endNumber = tileNumber + 2

            for (i in tileNumber..endNumber) {
                val resourceName = resourcePrefix + tileType + "_" + i.toString()
                val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
                val tile = MahjongTile(i, tileType, false, false, resourceId)
                tileList.add(tile)
            }

        } else if (isKoutsuButtonPressed) {

            for (i in 0..2) {
                val resourceName = resourcePrefix + tileType + "_" + tileNumber.toString()
                val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
                val tile = MahjongTile(tileNumber, tileType, false, false, resourceId)
                tileList.add(tile)
            }
        } else if (isTitoitsuButtonPressed) {

            for (i in 0..1) {
                val resourceName = resourcePrefix + tileType + "_" + tileNumber.toString()
                val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
                val tile = MahjongTile(tileNumber, tileType, false, false, resourceId)
                tileList.add(tile)
            }
        } else if (isPonButtonPressed) {

            for (i in 0..2) {
                val resourceName = resourcePrefix + tileType + "_" + tileNumber.toString()
                val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
                val tile = MahjongTile(tileNumber, tileType, false, false, resourceId)
                tileList.add(tile)
            }
        } else if (isTiButtonPressed && tileNumber < 8) {
            val endNumber = tileNumber + 2

            for (i in tileNumber..endNumber) {
                val resourceName = resourcePrefix + tileType + "_" + i.toString()
                val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
                val tile = MahjongTile(tileNumber, tileType, false, false, resourceId)
                tileList.add(tile)
            }
        } else if (isAnkanButtonPressed) {
            for (i in 0..3) {
                val resourceName = resourcePrefix + tileType + "_" + tileNumber.toString()
                val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
                val tile = MahjongTile(tileNumber, tileType, false, false, resourceId)
                tileList.add(tile)
            }
        } else if (isMinkanButtonPressed) {
            for (i in 0..3) {
                val resourceName = resourcePrefix + tileType + "_" + tileNumber.toString()
                val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
                val tile = MahjongTile(tileNumber, tileType, false, false, resourceId)
                tileList.add(tile)
            }
        } else if (selectedTiles.isEmpty()) {
            for (i in 0..1) {
                val resourceName = resourcePrefix + tileType + "_" + tileNumber.toString()
                val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
                val tile = MahjongTile(tileNumber, tileType, false, false, resourceId)
                tileList.add(tile)
            }
        }
        return tileList

    }
}

