package jp.techacademy.mahjongcalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TutorialAdapter(private val tutorialItems: List<TutorialItem>) :
    RecyclerView.Adapter<TutorialAdapter.TutorialViewHolder>() {

    inner class TutorialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val textView: TextView = itemView.findViewById(R.id.textView)

        fun bind(item: TutorialItem) {
            imageView.setImageResource(item.imageResId)
            textView.text = item.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tutorial, parent, false)
        return TutorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: TutorialViewHolder, position: Int) {
        val item = tutorialItems[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return tutorialItems.size
    }
}