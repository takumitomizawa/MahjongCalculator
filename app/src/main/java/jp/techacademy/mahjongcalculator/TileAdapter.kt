package jp.techacademy.mahjongcalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class TileAdapter(private val tileList: List<MahjongTile>) :
    RecyclerView.Adapter<TileAdapter.TileViewHolder>() {

    override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
        val tileImage = holder.itemView.findViewById<ImageView>(R.id.imageViewTile)
        tileImage.setImageResource(tileList[position].imageResourceId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tile, parent, false)
        return TileViewHolder(itemView)
    }

    override fun getItemCount() = tileList.size

    inner class TileViewHolder(itemView: View) : ViewHolder(itemView)
}