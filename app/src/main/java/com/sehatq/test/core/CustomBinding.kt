package com.sehatq.test.core

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.ScrollView
import androidx.cardview.widget.CardView
import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.sehatq.test.R
import com.sehatq.test.utils.GlideApp
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import me.everything.android.ui.overscroll.IOverScrollDecor
import me.everything.android.ui.overscroll.IOverScrollUpdateListener
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator

object CustomBinding {

    @JvmStatic
    @BindingAdapter("rvOverScroll")
    fun setOverScroll(recyclerView: RecyclerView, isHorizontal: Boolean) {
        if (recyclerView.layoutManager != null)
            OverScrollDecoratorHelper.setUpOverScroll(
                recyclerView,
                if (isHorizontal) (OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)
                else (OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
            )
    }

    @JvmStatic
    @BindingAdapter("nestedOverScroll")
    fun setOverScrollNested(nestedScrollView: NestedScrollView, isSet: Boolean) {
        if (isSet)
            VerticalOverScrollBounceEffectDecorator(
                NestedScrollViewOverScrollDecorAdapter(
                    nestedScrollView
                )
            )
    }

    @JvmStatic
    @BindingAdapter("rvOverScrollSwipe")
    fun setOverScrollWithSwipe(recyclerView: RecyclerView, swipeRefreshLayout: SwipeRefreshLayout) {
        var decor: IOverScrollDecor?
        if (recyclerView.layoutManager != null) {
            decor = OverScrollDecoratorHelper.setUpOverScroll(
                recyclerView,
                OverScrollDecoratorHelper.ORIENTATION_VERTICAL
            )
            decor.setOverScrollUpdateListener(IOverScrollUpdateListener { _, _, offset ->
                if (offset > 150) {
                    swipeRefreshLayout.isRefreshing = true
                }
            })
        }
    }

    @JvmStatic
    @BindingAdapter("vpOverScroll")
    fun setOverScrollViewPager(viewPager: ViewPager, set: Boolean) {
        if (set)
            OverScrollDecoratorHelper.setUpOverScroll(viewPager)
    }

    @JvmStatic
    @BindingAdapter("hvOverScroll")
    fun setOverScrollHorizontalScrollView(
        horizontalScrollView: HorizontalScrollView,
        isSet: Boolean
    ) {
        if (isSet)
            OverScrollDecoratorHelper.setUpOverScroll(horizontalScrollView)
    }

    @JvmStatic
    @BindingAdapter("svOverScroll")
    fun setOverScrollScrollView(scrollView: ScrollView, isSet: Boolean) {
        if (isSet)
            OverScrollDecoratorHelper.setUpOverScroll(scrollView)
    }

    @JvmStatic
    @BindingAdapter("rvAnimation")
    fun setAnimationRecyclerView(recyclerView: RecyclerView, isAnimate: Boolean) {
        if (isAnimate) {
            recyclerView.itemAnimator = SlideInRightAnimator()
        }
    }

    @JvmStatic
    @BindingAdapter("imageSource")
    fun imageSource(view: ImageView, imageSource: Int) {
        view.setImageResource(imageSource)
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun setIsVisible(view: View, isVisible: Boolean?) {
        if (isVisible == null) {
            return
        }
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("circleImageUrl")
    fun imageCircleSourceUrl(view: ImageView, url: String) {
        if (url.isNullOrEmpty()) {
            GlideApp.with(view.context).load(R.drawable.error_image).circleCrop().into(view)
        } else {
            GlideApp.with(view.context)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.loading_image)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun imageSourceUrl(view: ImageView, url: String) {
        if (url.isNullOrEmpty()) {
            GlideApp.with(view.context).load(R.drawable.error_image).into(view)
        } else {
            GlideApp.with(view.context)
                .load(url)
                .fitCenter()
                .placeholder(R.drawable.loading_image)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("appBarVisible")
    fun bindAppBarVisible(view: CardView, isShow: Boolean) {
        if (isShow) {
            view.visibility = View.VISIBLE
            view.animate()
                .translationY(0f)
                .alpha(1.0f)
                .setListener(object : AnimatorListenerAdapter() {
                })
        } else {
            view.animate()
                .translationY(100f)
                .alpha(0.0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        view.visibility = View.GONE
                    }
                })
        }
    }

}