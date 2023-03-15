package com.neotica.submissiondicodingawal.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.neotica.submissiondicodingawal.databinding.LayoutProfileBinding
import com.neotica.submissiondicodingawal.main.MainActivity
import com.neotica.submissiondicodingawal.main.fragment.adapter.TabAdapter
import java.util.*

class UserProfileFragment : Fragment() {
    private lateinit var binding: LayoutProfileBinding
    private lateinit var tabAdapter:TabAdapter
    private val tabTitle = listOf(
        "Followers",
        "Following"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = LayoutProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindHEHE()
        viewPager()
    }

    private fun viewPager(){
        val title = tabTitle
        binding.apply {
            viewPager.apply {
                tabAdapter = TabAdapter(this@UserProfileFragment)
                adapter = tabAdapter
                currentItem = 0
            }
            TabLayoutMediator(tabLayout,viewPager) {tab, position ->
                tab.text = title[position]
            }.attach()
        }
    }

    private fun bindHEHE(){
        val avatar = UserProfileFragmentArgs.fromBundle(arguments as Bundle).avatar.toString()
        val profile = UserProfileFragmentArgs.fromBundle(arguments as Bundle).profile
        binding.apply {
            Glide.with(root)
                .load(avatar)
                .into(ivProfile)
            tvUsername.text = profile
                .replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }
        }
    }
}