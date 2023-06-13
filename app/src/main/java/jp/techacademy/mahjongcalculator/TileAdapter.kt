package jp.techacademy.mahjongcalculator

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import jp.techacademy.mahjongcalculator.databinding.ItemTileBinding
import java.util.*


class TileAdapter(
    private val tileList: List<MahjongTile>
) : RecyclerView.Adapter<TileAdapter.TileViewHolder>() {

    private var onTileClickListener: ((MahjongTile) -> Unit)? = null
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    fun setOnTileClickListener(listener: (MahjongTile) -> Unit){
        onTileClickListener = listener
    }

    override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
        holder.tileImageView.setImageResource(tileList[position].imageResourceId)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tile, parent, false)
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

    /*inner class TileViewHolder(private val binding: ItemTileBinding) : RecyclerView.ViewHolder(binding.root){
        val tileImageView: ImageView = itemView.findViewById(R.id.imageViewTile)
        fun bind(tile: MahjongTile, position: Int){
            binding.imageViewTile.setImageResource(tile.imageResourceId)

            // 選択された牌の位置によって背景を変更
            if (position == selectedPosition) {
                binding.imageViewTile.setBackgroundResource(R.drawable.tile_border)
            } else {
                binding.imageViewTile.background = null
            }

            // クリックイベントのリスナーを設定
            binding.imageViewTile.setOnClickListener{
                // 選択された牌の位置を更新
                selectedPosition = adapterPosition

                // RecyclerViewを更新
                notifyDataSetChanged()
            }
        }
    }*/
}