package ir.drax.samples.lifecycle.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.drax.samples.lifecycle.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}