package inkyu.naver.com.swipablerecyclerview

import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = RvAdapter()
        recyclerView.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)

        showBtn.setOnClickListener { showToolTip() }
        hideBtn.setOnClickListener { hideToolTip() }
    }

    private fun showToolTip() {
        val recyclerViewPosition =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                100f,
                resources.displayMetrics
            )
        recyclerView.setBaseTranslationX(recyclerViewPosition, true)
    }

    private fun hideToolTip() {
        recyclerView.setBaseTranslationX(0f, true)
    }
}