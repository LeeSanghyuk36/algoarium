package com.d204.algo.ui.ranking

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.viewModels
import com.d204.algo.base.BaseFragment
import com.d204.algo.base.BaseViewModel
import com.d204.algo.databinding.FragmentRankingBinding
import com.d204.algo.presentation.viewmodel.RankingFragmentViewModel
import com.d204.algo.ui.adapter.RankingAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RankingFragment : BaseFragment<FragmentRankingBinding, BaseViewModel>() {
    companion object {
        @JvmStatic
        fun newInstance() =
            RankingFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun getViewBinding(): FragmentRankingBinding = FragmentRankingBinding.inflate(layoutInflater)
    override val viewModel: RankingFragmentViewModel by viewModels()

    @Inject
    lateinit var rankingAdapter: RankingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewToAnimate = binding.fragmentRankingWantedOuter // 애니메이션을 적용할 뷰
        val targetHeight = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._200sdp) // 200dp를 픽셀로 변환

        val animation = ValueAnimator.ofInt(0, targetHeight)
        animation.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams = viewToAnimate.layoutParams
            layoutParams.height = value
            viewToAnimate.layoutParams = layoutParams
        }
        animation.interpolator = AccelerateInterpolator()
        animation.duration = 4000 // 10초 동안 애니메이션 진행
        animation.start()
    }
}
