package com.neotica.submissiondicodingawal.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.neotica.submissiondicodingawal.databinding.LayoutProfileBinding
import com.neotica.submissiondicodingawal.main.MainActivity
import com.neotica.submissiondicodingawal.main.fragment.adapter.TabAdapter
import com.neotica.submissiondicodingawal.mvvm.GithubViewModel
import com.neotica.submissiondicodingawal.mvvm.GithubViewModelFactory
import com.neotica.submissiondicodingawal.response.UserDetailResponse
import java.util.*

class UserProfileFragment/*(private val detail: UserDetailResponse)*/ : Fragment() {
    private lateinit var binding: LayoutProfileBinding
    private lateinit var tabAdapter:TabAdapter
    private val tabTitle = listOf(
        "Followers",
        "Following"
    )
    private val viewModel by viewModels<GithubViewModel> { GithubViewModelFactory }

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
        viewPager()
        bindHEHE()
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

    private fun showLoading(isLoading: Boolean) {
        //Step 24: Declare the condition
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun bindHEHE(){
        showLoading(true)
        val avatar = UserProfileFragmentArgs.fromBundle(arguments as Bundle).avatar.toString()
        val profile = UserProfileFragmentArgs.fromBundle(arguments as Bundle).profile
        viewModel.getUserDetail(profile)
        viewModel.detailResponse.observe(viewLifecycleOwner){
            github ->
            showLoading(false)
            if (github != null) {
                val followers = github.followers.toString()
                val following = github.following.toString()
                binding.tvFollowers.text = "Followers: $followers"
                binding.tvFollowing.text = "Following: $following"
                binding.tvName.text = github.name
            }
        }


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