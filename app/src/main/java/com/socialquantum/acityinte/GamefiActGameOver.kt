package com.socialquantum.acityinte

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.socialquantum.acityinte.databinding.ActivityGamefiGameOverBinding

class GamefiActGameOver : AppCompatActivity() {

    private lateinit var binding: ActivityGamefiGameOverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamefiGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showScore()
        setListeners()
    }

    private fun setListeners() {
        binding.btnRestart.setOnClickListener {
            val intent = Intent(this, GamefiAct::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showScore() {
        binding.tvGameOver.text =
            getString(R.string.game_over).format(intent.getStringExtra(SCORE_KEY).toString())
    }

    companion object {
        const val SCORE_KEY = "SCORE"
    }
}