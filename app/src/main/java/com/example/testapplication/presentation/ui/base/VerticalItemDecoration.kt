package com.example.testapplication.presentation.ui.base

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalItemDecoration(
    private val innerDivider: Int,
    private val outerDivider: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapter = parent.adapter ?: return
        val currentPosition =
            parent.getChildAdapterPosition(view).takeIf { it != RecyclerView.NO_POSITION } ?: return
        val lastIndex = adapter.itemCount - 1
        val firstIndex = 0
        val oneSideInnerDivider = innerDivider / 2
        when (currentPosition) {
            firstIndex -> {
                outRect.top = outerDivider
                outRect.bottom = innerDivider
            }
            lastIndex -> {
                outRect.bottom = outerDivider
                outRect.top = innerDivider
            }
            else -> {
                with(outRect) {
                    top = oneSideInnerDivider
                    bottom = oneSideInnerDivider
                }
            }
        }
    }
}