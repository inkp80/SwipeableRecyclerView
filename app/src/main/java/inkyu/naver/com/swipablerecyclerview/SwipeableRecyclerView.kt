package inkyu.naver.com.swipablerecyclerview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class SwipeableRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var isSwipeable = false

    var baseTranslationX = 0f

    private var initialTouchX = -1f

    // negative : left to right
    // positive : right to left
    private var scrollDirection = 0f

    private val translationXAnimator = ValueAnimator()

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {

            MotionEvent.ACTION_DOWN -> {
                scrollDirection = 0f
                initialTouchX = e.rawX
                isSwipeable = computeHorizontalScrollOffset() == 0
            }

            MotionEvent.ACTION_MOVE -> {
                scrollDirection = initialTouchX - e.rawX

                if (isSwipeable && scrollDirection < 0) {
                    translationX = abs(initialTouchX - e.rawX) + baseTranslationX
                    (layoutManager as LinearLayoutManager).scrollToPosition(0)
                } else {
                    translationX = if (isSwipeable && baseTranslationX > 0f) {
                        val posX = -abs(initialTouchX - e.rawX) + baseTranslationX
                        if (posX >= 0f) posX else 0f
                    } else {
                        baseTranslationX
                    }
                }
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                isSwipeable = false
                val startPosX = translationX
                val endPosX = if (scrollDirection < 0) baseTranslationX else 0f

                translationXAnimator.run {
                    if (startPosX == endPosX) {
                        return@run
                    }

                    removeAllUpdateListeners()
                    removeAllListeners()
                    setDuration(300)
                    setFloatValues(startPosX, endPosX)

                    addUpdateListener {
                        translationX = animatedValue as Float
                    }

                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            baseTranslationX = translationX
                            (layoutManager as LinearLayoutManager).scrollToPosition(0)
                        }
                    })

                    start()
                }
            }
        }

        return if (isSwipeable && scrollDirection < 0) {
            true
        } else {
            super.onTouchEvent(e)
        }
    }


    // REMARK: base transX 가 설정된 상태에서 fling 동작 입력 시 스크롤이 튀는 현상 있음
    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        if (computeHorizontalScrollOffset() == 0 && scrollDirection < 0 || baseTranslationX > 0f && scrollDirection > 0) {
            return false
        }

        return super.fling(velocityX, velocityY)
    }

    fun setBaseTranslationX(posXInPx: Float, withAnimation: Boolean) {
        if (withAnimation) {
            translationXAnimator.run {
                removeAllListeners()
                removeAllUpdateListeners()
                setFloatValues(translationX, posXInPx)
                setDuration(300)
                addUpdateListener { translationX = animatedValue as Float }
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        baseTranslationX = posXInPx
                    }
                })
                start()
            }
        } else {
            translationX = posXInPx
        }
    }
}