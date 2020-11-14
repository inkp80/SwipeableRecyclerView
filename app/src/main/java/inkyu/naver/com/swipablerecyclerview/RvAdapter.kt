package inkyu.naver.com.swipablerecyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RvAdapter : RecyclerView.Adapter<RvHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvHolder {
        return RvHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: RvHolder, position: Int) {
        holder.bind(position)
    }
}