package com.example.kmtestsuitmedia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kmtestsuitmedia.R
import com.example.kmtestsuitmedia.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySecondBinding
    private var name: String? = null
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        name = intent.getStringExtra("name")
        username = intent.getStringExtra("username")

        binding.textName.text = name
        binding.textUsername.text = username  ?: "Selected User Name"
        binding.backButton.setOnClickListener { toFirstPage() }
        binding.nextThird.setOnClickListener { toThirdPage() }


    }

    private fun toFirstPage(){
        val intent = Intent(this, FirstActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }

    private fun toThirdPage(){
        val intent = Intent(this, ThirdActivity::class.java)
        intent.putExtra("name", name)
        startActivity(intent)
    }

}