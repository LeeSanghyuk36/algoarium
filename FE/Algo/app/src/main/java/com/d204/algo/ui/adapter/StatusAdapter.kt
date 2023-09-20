package com.d204.algo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.d204.algo.base.BaseAdapter
import com.d204.algo.data.model.ProblemLike
import com.d204.algo.data.model.Status
import com.d204.algo.databinding.ItemStatusListBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StatusAdapter @Inject constructor(
    private val glide: RequestManager,
    @ApplicationContext private val context: Context,
) : BaseAdapter<Status>() {
    // this(context) 때문에 override로 구현
    override val differ = AsyncListDiffer(this, diffCallback)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemStatusListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatusViewHolder(binding)
    }

    inner class StatusViewHolder(private val binding: ItemStatusListBinding) :
        RecyclerView.ViewHolder(binding.root), Binder<Status> {
        override fun bind(item: Status) = with(binding) {
            // 북마크 버튼
            itemStatusCancelButton.setOnClickListener {
//                statusClickListener.bookmarkClick(binding, problemLike, layoutPosition)
            }

            // 메모 버튼
            itemStatusMemoButton.setOnClickListener {
//                statusClickListener.memoClick(binding, problemLike, layoutPosition)
            }
        }
    }

    // 이벤트 처리 listener
    interface StatusClickListener {
        fun bookmarkClick(binding: ItemStatusListBinding, problemLike: ProblemLike, position: Int)
        fun memoClick(binding: ItemStatusListBinding, problemLike: ProblemLike, position: Int)
    }
    private lateinit var statusClickListener: StatusClickListener
    fun setStatusClickListener(statusClickListener: StatusClickListener) {
        this.statusClickListener = statusClickListener
    }
}
