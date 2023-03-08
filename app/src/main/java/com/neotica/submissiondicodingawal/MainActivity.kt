package com.neotica.submissiondicodingawal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neotica.submissiondicodingawal.databinding.ActivityMainBinding
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: RvUserListBinding
    private lateinit var bindingMain: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RvUserListBinding.inflate(layoutInflater)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
    }
}