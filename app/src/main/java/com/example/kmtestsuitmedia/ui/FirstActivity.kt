package com.example.kmtestsuitmedia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.kmtestsuitmedia.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.checkPalindrome.setOnClickListener { checkPalindrome() }
        binding.nextSecond.setOnClickListener { toSecondPage() }
    }

    private fun toSecondPage(){
        val name = binding.inputName.text.toString()
        if (name.isEmpty()) {
            binding.inputName.error = "Input Can't Be Empty!"
        } else {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("name", name)
            startActivity(intent)
        }
    }

    private fun checkPalindrome() {
        val text = binding.inputPalindrome.text.toString()

        if(text.isEmpty()) {
            binding.inputPalindrome.error = "Input Cant Be Empty!"
        } else {
            val isPalindrome = isPalindrome(text)
            val message = if (isPalindrome) "isPalindrome" else "not palindrome"

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage(message)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    private fun isPalindrome(text: String): Boolean {
        val cleanedText = text.replace("\\W".toRegex(), "")
        val reversedText = cleanedText.reversed()

        return cleanedText == reversedText
    }

}