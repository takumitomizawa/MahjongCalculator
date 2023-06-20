package jp.techacademy.mahjongcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import jp.techacademy.mahjongcalculator.databinding.ActivityCopyrightBinding

class Copyright : AppCompatActivity() {

    private lateinit var binding: ActivityCopyrightBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCopyrightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hyperlink.text = Html.fromHtml("<a href=\"https://majandofu.com/mahjong-images\">https://majandofu.com/mahjong-images</a>")
        binding.hyperlink.movementMethod = LinkMovementMethod.getInstance()


        binding.backToTitleButton.setOnClickListener{
            finish()
        }
    }
}