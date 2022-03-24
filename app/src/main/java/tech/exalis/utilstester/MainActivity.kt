package tech.exalis.utilstester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import tech.exalis.utils.message.Message

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<MaterialButton>(R.id.button).setOnClickListener {
            Message.Builder()
                .viewGroup(findViewById(R.id.root))
                .title("C'est un titre")
                .message("C'est un contenu")
                .build()
        }
    }
}
