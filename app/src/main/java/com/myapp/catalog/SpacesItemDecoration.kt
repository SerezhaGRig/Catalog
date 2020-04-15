package com.myapp.catalog


import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class GridSpacingItemDecoration(
    private val space:Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        outRect.left = space
        outRect.right = space
        outRect.bottom = space/2
        outRect.top = space/2

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view)%2 == 0) {
            outRect.right = 0;
        } else {
            outRect.left = space;
        }

    }
}