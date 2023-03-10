package com.socialquantum.acityinte

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.socialquantum.acityinte.databinding.ActivityGamefiGameOverBinding

class GamefiActGameOver : AppCompatActivity() {

    private lateinit var binding: ActivityGamefiGameOverBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityGamefiGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showScore()
        setListeners()
    }

    private fun setListeners() {
        binding.btnRestart.setOnClickListener {
            startActivity(Intent(this, GamefiAct::class.java))
            finish()
        }
    }

    private fun showScore() {
        binding.tvGameOver.text =
            getString(R.string.game_over).format(intent.getStringExtra(SCORE_KEY))
    }

    companion object {
        const val SCORE_KEY = "score"
    }
}