package jp.techacademy.mahjongcalculator

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

    private lateinit var recyclerView: RecyclerView
    private lateinit var tileAdapter: TileAdapter

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recyclerView = binding.recyclerViewTiles
        recyclerView.layoutManager = GridLayoutManager(this, 7)
        tileAdapter = TileAdapter(getTileList())
        recyclerView.adapter = tileAdapter
    }

    private fun getTileList(): List<Int>{
        return listOf(
            R.drawable.aka1_66_90_l_emb,
            R.drawable.ji1_66_90_l_emb
        )
    }

    private inner class TileAdapter(private val tileList: List<Int>): RecyclerView.Adapter<TileAdapter.TileViewHolder>(){

        override fun onBindViewHolder(holder: TileViewHolder, position: Int){
            val tileImage = holder.itemView.findViewById<ImageView>(R.id.imageViewTile)
            tileImage.setImageResource(tileList[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tile, parent, false)
            return TileViewHolder(itemView)
        }

        override fun getItemCount() = tileList.size

        inner class TileViewHolder(itemView: View) : ViewHolder(itemView)
        }
}