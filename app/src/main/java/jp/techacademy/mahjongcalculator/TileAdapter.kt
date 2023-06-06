package jp.techacademy.mahjongcalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


class TileAdapter(
    private val tileList: List<MahjongTile>
) : RecyclerView.Adapter<TileAdapter.TileViewHolder>() {

    private var onTileClickListener: ((MahjongTile) -> Unit)? = null

    fun setOnTileClickListener(listener: (MahjongTile) -> Unit){
        onTileClickListener = listener
    }

    override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
        holder.tileImageView.setImageResource(tileList[position].imageResourceId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tile, parent, false)
        return TileViewHolder(itemView)
    }

    override fun getItemCount() = tileList.size

    inner class TileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tileImageView: ImageView = itemView.findViewById(R.id.imageViewTile)

        init {
            tileImageView.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val tile = tileList[position]
                    onTileClickListener?.invoke(tile)
                }
            }
        }
    }
}