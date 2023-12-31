package com.d204.algo.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.d204.algo.base.BaseAdapter
import com.d204.algo.data.model.Problem
import com.d204.algo.databinding.ItemStatusListBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StatusAdapter @Inject constructor(
    @ApplicationContext private val context: Context,
) : BaseAdapter<Problem>() {
    // this(context) 때문에 override로 구현
    override val differ = AsyncListDiffer(this, diffCallback)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemStatusListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val isCheck = true
        return StatusViewHolder(binding, isCheck)
    }

    inner class StatusViewHolder(private val binding: ItemStatusListBinding, private var isCheck: Boolean) :
        RecyclerView.ViewHolder(binding.root), Binder<Problem> {
        override fun bind(item: Problem) = with(binding) {
            Log.d("포지션", "${item.title}  ${item.problemLike} $layoutPosition")
            itemStatusNameTextView.text = item.title
            itemStatusNumberTextView.text = item.problemNumber.toString()
            itemStatusBookmarkButton.isChecked = isCheck

            // 북마크 버튼
            itemStatusBookmarkButton.setOnCheckedChangeListener { compoundButton, isChecked ->
                isCheck = isChecked
                statusClickListener.bookmarkClick(binding, item, isChecked, layoutPosition)
            }

            // 메모 버튼
            itemStatusMemoButton.setOnClickListener {
                statusClickListener.memoClick(binding, item, layoutPosition)
            }

            itemStatusListLayout.setOnClickListener {
                statusClickListener.layoutClick(item)
            }
        }
    }

    // 이벤트 처리 listener
    interface StatusClickListener {
        fun bookmarkClick(binding: ItemStatusListBinding, problem: Problem, isChecked: Boolean, position: Int)
        fun memoClick(binding: ItemStatusListBinding, problem: Problem, position: Int)
        fun layoutClick(problem: Problem)
    }
    private lateinit var statusClickListener: StatusClickListener
    fun setStatusClickListener(statusClickListener: StatusClickListener) {
        this.statusClickListener = statusClickListener
    }
}
