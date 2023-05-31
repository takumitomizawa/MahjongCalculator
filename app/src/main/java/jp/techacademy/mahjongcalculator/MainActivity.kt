package jp.techacademy.mahjongcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        manzuAdapter = TileAdapter(getTiles("manzu"))
        pinzuAdapter = TileAdapter(getTiles("pinzu"))
        souzuAdapter = TileAdapter(getTiles("souzu"))
        jiAdapter = TileAdapter(getTiles("ji"))

        recyclerViewHand.adapter = handAdapter
        recyclerViewManzu.adapter = manzuAdapter
        recyclerViewPinzu.adapter = pinzuAdapter
        recyclerViewSouzu.adapter = souzuAdapter
        recyclerViewJi.adapter = jiAdapter

        manzuAdapter.setOnTileClickListener { tile ->
            selectedTiles.add(tile)
            handAdapter.notifyDataSetChanged()
        }

        pinzuAdapter.setOnTileClickListener { tile ->
            selectedTiles.add(tile)
            handAdapter.notifyDataSetChanged()
        }

        souzuAdapter.setOnTileClickListener { tile ->
            selectedTiles.add(tile)
            handAdapter.notifyDataSetChanged()
        }

        jiAdapter.setOnTileClickListener { tile ->
            selectedTiles.add(tile)
            handAdapter.notifyDataSetChanged()
        }

        binding.nextButton.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
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
}