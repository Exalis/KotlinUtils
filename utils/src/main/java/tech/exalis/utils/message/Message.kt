package tech.exalis.utils.message

import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import tech.exalis.utils.databinding.BannerMessageBinding
import tech.exalis.utils.message.ViewAnimation.collapse


class Message private constructor(
    val viewGroup: ViewGroup,
    val message: String,
    val title: String,
    val icon: Drawable?,
    val backgroundColor: Int?,
    val closingDelay: Long,
    val bottomMargin: Int,
    val comesFromBottom: Boolean = false
) {
    lateinit var messageBinding: BannerMessageBinding
    private var hasAlreadyAParentView: Boolean = false

    init{
        initView()
    }

    private fun initView() {
        createView()
    }


    private fun createView() {
        createBinding()
        val params = setLayoutParams()

        setMessageGravity(params)
        setMessageTitle()
        setMessageContent()

        messageBinding.root.layoutParams = params

        if (hasAlreadyAParentView) return

        addViewToLayout()

        ViewAnimation.expand(messageBinding.root)

        Handler(viewGroup.context.mainLooper).postDelayed(
            { collapse(messageBinding.getRoot()) },
            closingDelay
        )
    }

    private fun setMessageContent() {
        messageBinding.bannerTextContent.text = message
    }

    private fun setMessageTitle() {
        messageBinding.bannerTitleContent.text = title
    }

    private fun addViewToLayout() {
        viewGroup?.addView(messageBinding.root)
        hasAlreadyAParentView = true
    }

    private fun setMessageGravity(params: CoordinatorLayout.LayoutParams) {
        if (comesFromBottom) {
            params.gravity = Gravity.BOTTOM
            params.setMargins(20, 20, 20, bottomMargin)
        } else {
            params.setMargins(20, 20, 20, 20)
        }
    }

    private fun setLayoutParams(): CoordinatorLayout.LayoutParams {
        val params = CoordinatorLayout.LayoutParams(
            CoordinatorLayout.LayoutParams.MATCH_PARENT,
            100
        )
        return params
    }

    private fun createBinding() {
        messageBinding = BannerMessageBinding.inflate(
            LayoutInflater.from(
                viewGroup?.context
            )
        )
    }

    data class Builder(
        var message: String = "Information",
        var title: String = "erreur.",
        var icon: Drawable? = null,
        var backgroundColor: Int? = null,
        var closingDelay: Long = 2500,
        var bottomMargin: Int = 20,
        var viewGroup: ViewGroup? = null,
        ){

        fun message(message: String) = apply { this.message = message }
        fun title(title: String) = apply { this.title = title }
        fun icon(icon: Drawable) = apply { this.icon = icon }
        fun backgroundColor(backgroundColor: Int) = apply { this.backgroundColor = backgroundColor }
        fun closingDelay(closingDelay: Long) = apply { this.closingDelay = closingDelay }
        fun bottomMargin(bottomMargin: Int) = apply { this.bottomMargin = bottomMargin }
        fun viewGroup(viewGroup: ViewGroup) = apply { this.viewGroup = viewGroup }
        fun build() = Message(
            viewGroup = viewGroup!!,
            message = message,
            title = title,
            icon = icon,
            backgroundColor = backgroundColor,
            closingDelay = closingDelay,
            bottomMargin = bottomMargin
        )
    }
}
