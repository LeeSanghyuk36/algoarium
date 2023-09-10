package com.d204.algo

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.d204.algo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bubbleTransition: GifDrawable
    private lateinit var rankUpEffect: GifDrawable

    @Inject
    lateinit var glide: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        setAnimation() // 새 시즌 시작이 아니면 바로 호출
        setRankAnimation() // 새 시즌으로 랭크업 해야하면 호출 후 클릭 시 setAnimation 호출
        setContentView(binding.root)
        setupNavHost()
//        카카오 앱 개발자 페이지에 등록하기
//        var keyHash = Utility.getKeyHash(this)
//        Log.d(TAG, "onCreate: $keyHash")
//        showSnackBar(binding.root, "ㅎㅇ")
    }

    private fun setupNavHost() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
    }

    private fun removeAnimation() {
        val animation = AlphaAnimation(1.0f, 0.0f) // 투명도 1.0에서 0.0으로 애니메이션
        animation.duration = 2000 // 2초 동안
        binding.activityMainUpperBg.startAnimation(animation)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                binding.activityMainUpperBg.visibility = View.GONE // 뷰를 숨기기
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setAnimation() {
        glide.asGif()
            .load(R.drawable.bubble_transition)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    bubbleTransition = resource!!
                    bubbleTransition.setLoopCount(1)

                    bubbleTransition.registerAnimationCallback(object :
                        Animatable2Compat.AnimationCallback() {
                        override fun onAnimationEnd(drawable: Drawable?) {
                            binding.transitionAnim.visibility = View.GONE
                        }
                    })

                    removeAnimation()
                    return false
                }
            })
            .into(binding.transitionAnim)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setRankAnimation() {
        val animator = ObjectAnimator.ofFloat(binding.activityMainTierBefore, "rotationY", 0f, 10800f)
        animator.duration = 5000
        animator.start()

        glide.asGif()
            .load(R.drawable.tier_up_light_transparent)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    rankUpEffect = resource!!
                    rankUpEffect.setLoopCount(1)

                    rankUpEffect.registerAnimationCallback(object :
                        Animatable2Compat.AnimationCallback() {
                        override fun onAnimationEnd(drawable: Drawable?) {
                            binding.apply{
                                activityMainRankUpEffect.visibility = View.GONE
                                activityMainTierBefore.visibility = View.GONE
                                activityMainRankUp.setOnClickListener {
                                    it.visibility = View.GONE
                                    setAnimation()
                                }
                            }
                        }
                    })

                    return false
                }
            })
            .into(binding.activityMainRankUpEffect)
    }

    fun callTransition() {
        binding.transitionAnim.visibility = View.VISIBLE
        bubbleTransition.start()
    }
}
