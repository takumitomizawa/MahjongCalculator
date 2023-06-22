package jp.techacademy.mahjongcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.techacademy.mahjongcalculator.databinding.ActivityCalculateBinding
import jp.techacademy.mahjongcalculator.databinding.ActivityTutorialBinding

class Tutorial : AppCompatActivity() {

    private lateinit var binding: ActivityTutorialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val backToHomeFab = findViewById<FloatingActionButton>(R.id.backToHomeFab)

        backToHomeFab.setOnClickListener{
            finish()
        }*/

        val tutorialItems = listOf(
            TutorialItem(R.drawable.tutorial, "こちらはチュートリアル画面です。　進む場合はスワイプし、右下の家マークのボタンでタイトルへ戻ります。"),
            TutorialItem(R.drawable.tutorial_first, "麻雀牌を選択すると画面上部に表示されます。"),
            TutorialItem(R.drawable.tutorial_support_button, "補助ボタンはこちらです。　　　　　　　　　　　　　　　　補助ボタンを押さないと牌を登録できません。"),
            TutorialItem(R.drawable.tutorial_support_input, "4の筒子を押すだけで、自動的に登録します。　　　　　　　　補助ボタンが有効の時は連続して入力可能です。"),
            TutorialItem(R.drawable.tutorial_next, "全ての牌を入力したら次へを押しましょう。"),
            TutorialItem(R.drawable.tutorial_selected, "牌を選択してあがり牌を設定してください。"),
            TutorialItem(R.drawable.tutorial_calculate, "偶然役等を設定したら、計算開始を押してください。"),
            TutorialItem(R.drawable.tutorial_calculate_result, "点数の詳細と、役に対応した翻数が表示されます。　　　　　　　　　これで一連の動作が終了です。")
        )

        val adapter = TutorialAdapter(tutorialItems)
        binding.viewPager.adapter = adapter
    }
}