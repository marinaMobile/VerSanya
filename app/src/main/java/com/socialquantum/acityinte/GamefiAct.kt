package com.socialquantum.acityinte

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.socialquantum.acityinte.databinding.ActivityGamefiBinding

class GamefiAct : AppCompatActivity() {

    private lateinit var binding: ActivityGamefiBinding
    private val viewModel: GamefiActViewModel by viewModels()
    private lateinit var imageViewsArray: Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamefiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setImageViewsArray()
        randomizeField()
        randomizeTask()

        startTimer()

        setListeners()
        setGameStatus()
        setObserver()
    }

    private fun setImageViewsArray() {
        with(binding) {
            imageViewsArray = arrayOf(
                iv1Variant, iv2Variant, iv3Variant, iv4Variant, iv5Variant,
                iv6Variant, iv7Variant, iv8Variant, iv9Variant, iv10Variant, iv11Variant,
                iv12Variant, iv13Variant, iv14Variant, iv15Variant, iv16Variant
            )
        }
    }

    private fun randomizeTask() {
        binding.ivSearchingFigure.setImageResource(randomIcon())
        viewModel.setTaskBitmap((binding.ivSearchingFigure.drawable as BitmapDrawable).bitmap)
    }

    private fun setGameStatus() {
        viewModel.setGameStatus()
    }

    private fun startTimer() {
        viewModel.gameStarted.observe(this) {
            viewModel.startTimer()
        }
    }

    private fun setListeners() {
        with(binding) {
            btnRefreshField.setOnClickListener {
                if (viewModel.scoreSubject.value!! >= SCORE_FOR_REFRESH) {
                    randomizeField()
                    viewModel.refreshField()
                }
            }
            for (imageView in imageViewsArray) {
                imageView.setOnClickListener {
                    if ((imageView.drawable as BitmapDrawable).bitmap.sameAs(viewModel.bitmapTask.value)) {
                        randomizeTask()
                        randomizeField()
                        viewModel.getCorrectVariant()
                    } else viewModel.getIncorrectVariant()
                }
            }
        }
    }

    private fun setObserver() {
        with(binding) {
            viewModel.timeSubject.observe(this@GamefiAct) {
                tvScore.text = getString(R.string.tv_time_string).format(it)
            }
            viewModel.scoreSubject.observe(this@GamefiAct) {
                tvTime.text = getString(R.string.tv_score_string).format(it)
            }
            viewModel.timeSubject.observe(this@GamefiAct) {
                if (it <= 0) {
                    changeActivityToGameOver()
                }
            }
        }
    }

    private fun changeActivityToGameOver() {
        val intent = Intent(this, GamefiActGameOver::class.java)
        intent.putExtra(SCORE_KEY, viewModel.scoreSubject.value)
        startActivity(intent)
        finish()
    }

    private fun randomizeField() {
        for (i in 1..16) {
            val imageViewId = resources.getIdentifier("iv$i" + "Variant", "id", packageName)
            val imageView = findViewById<ImageView>(imageViewId)
            imageView.setImageResource(randomIcon())
        }
    }

    private fun randomIcon(): Int {
        return when ((1..5).random()) {
            1 -> R.drawable.candy_one
            2 -> R.drawable.candy_two
            3 -> R.drawable.candy_three
            4 -> R.drawable.candy_fourh
            5 -> R.drawable.candy_five
            else -> 0
        }
    }

    companion object {
        const val SCORE_FOR_REFRESH = 2
        const val SCORE_KEY = "score"
    }
}