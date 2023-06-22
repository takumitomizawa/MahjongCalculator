package jp.techacademy.mahjongcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jp.techacademy.mahjongcalculator.databinding.ActivityTitleBinding

class TitleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTitleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTitleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.moveToCalculatorButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.showUrlButton.setOnClickListener{
            val intent = Intent(this, Copyright::class.java)
            startActivity(intent)
        }

        binding.moveToTutorialButton.setOnClickListener{
            val intent = Intent(this, Tutorial::class.java)
            startActivity(intent)
        }

    }
}