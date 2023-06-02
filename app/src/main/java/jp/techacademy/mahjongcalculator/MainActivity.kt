package jp.techacademy.mahjongcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        manzuAdapter.setOnTileClickListener { tile ->
            if (isSyuntsuButtonPressed) {
                SyuntsuButtonState(tile)
            } else if (isKoutsuButtonPressed) {
                KoutsuButtonState(tile)
            }
        }

        pinzuAdapter.setOnTileClickListener { tile ->
            if (isSyuntsuButtonPressed) {
                SyuntsuButtonState(tile)
            } else if (isKoutsuButtonPressed) {
                KoutsuButtonState(tile)
            }
        }

        souzuAdapter.setOnTileClickListener { tile ->
            if (isSyuntsuButtonPressed) {
                SyuntsuButtonState(tile)
            } else if (isKoutsuButtonPressed) {
                KoutsuButtonState(tile)
            }
        }

        jiAdapter.setOnTileClickListener { tile ->
            if (isKoutsuButtonPressed) {
                KoutsuButtonState(tile)
            }
            handAdapter.notifyDataSetChanged()
        }

        binding.resetButton.setOnClickListener {
            selectedTiles.clear()
            isSyuntsuButtonPressed = false
            isKoutsuButtonPressed = false
            handAdapter.notifyDataSetChanged()
        }

        binding.nextButton.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        binding.syuntsuButton.setOnClickListener {
            // 順子ボタンが押された場合、モードを変更(例.trueならばfalseへ変更)
            isSyuntsuButtonPressed = !isSyuntsuButtonPressed
        }

        binding.koutsuButton.setOnClickListener {
            // 刻子ボタンが押された場合、モードを変更(例.trueならばfalseへ変更)
            isKoutsuButtonPressed = !isKoutsuButtonPressed
        }
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

    private fun getTiles(tileType: String): List<MahjongTile> {
        val tileList = mutableListOf<MahjongTile>()
        val resourcePrefix = "tiles_"

        // 1-9までの牌を取得する
        for (i in 1..9) {
            val resourceName = resourcePrefix + tileType + "_" + i.toString()
            val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
            val tile = MahjongTile(tileType, i, resourceId)
            tileList.add(tile)
        }
        return tileList
    }

    private fun getSequentialTiles(tileType: String, tileNumber: Int): List<MahjongTile> {
        val tileList = mutableListOf<MahjongTile>()
        val resourcePrefix = "tiles_"

        if (isSyuntsuButtonPressed) {
            val startNumber = tileNumber
            val endNumber = tileNumber + 2

            for (i in startNumber..endNumber) {
                val resourceName = resourcePrefix + tileType + "_" + i.toString()
                val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
                val tile = MahjongTile(tileType, i, resourceId)
                tileList.add(tile)
            }

        } else if (isKoutsuButtonPressed) {
            val selectNumber = tileNumber

            for (i in 0..2) {
                val resourceName = resourcePrefix + tileType + "_" + selectNumber.toString()
                val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
                val tile = MahjongTile(tileType, selectNumber, resourceId)
                tileList.add(tile)
            }
        } else {
            val resourceName = resourcePrefix + tileType + "_" + tileNumber.toString()
            val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
            val tile = MahjongTile(tileType, tileNumber, resourceId)
            tileList.add(tile)

            selectedTiles.add(tile)
        }

        return tileList
    }

// 槓子の処理はこれでOK！
/*for (i in 0..3){
        val resourceName = resourcePrefix + tileType + "_" + selectNumber.toString()
        val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
        val tile = MahjongTile(tileType, selectNumber, resourceId)
        tileList.add(tile)
    }*/


}