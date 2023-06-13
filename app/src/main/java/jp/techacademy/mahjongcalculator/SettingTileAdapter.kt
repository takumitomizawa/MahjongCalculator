package jp.techacademy.mahjongcalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class SettingTileAdapter(
    private val tileList: List<MahjongTile>
) : RecyclerView.Adapter<SettingTileAdapter.SettingTileViewHolder>(){

    private var selectedPosition: Int = RecyclerView.NO_POSITION
    private var onTileClickListener: ((MahjongTile) -> Unit)? = null

    fun setOnTileClickListener(listener: (MahjongTile) -> Unit) {
        onTileClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingTileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tile, parent, false)
        return SettingTileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SettingTileViewHolder, position: Int) {
        holder.bind(tileList[position], position)
    }

    override fun getItemCount() = tileList.size

    inner class SettingTileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tileImageView: ImageView = itemView.findViewById(R.id.imageViewTile)

        init {
            tileImageView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val tile = tileList[position]
                    onTileClickListener?.invoke(tile)
                }
            }
        }

        fun bind(tile: MahjongTile, position: Int) {
            tileImageView.setImageResource(tile.imageResourceId)

            // 選択された牌の位置によって背景を変更
            if (position == selectedPosition) {
                tileImageView.setBackgroundResource(R.drawable.tile_border)
            } else {
                tileImageView.background = null
            }
        }
    }
}