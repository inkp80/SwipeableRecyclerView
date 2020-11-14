package inkyu.naver.com.swipablerecyclerview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RvHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {


    val colorSet = listOf<String>(
        "#1d976c",
        "#93f9b9",
        "#FFC837",
        "#3A6073"
    )

    companion object {
        const val LAYOUT_ID = R.layout.layout_holder
        fun create(parent: ViewGroup): RvHolder {
            val view = LayoutInflater.from(parent.context).inflate(LAYOUT_ID, parent, false)
            return RvHolder(view)
        }
    }

    fun bind(position: Int) {
        val color = colorSet[position % 4]
        itemView.setBackgroundColor(Color.parseColor(color))
    }

}