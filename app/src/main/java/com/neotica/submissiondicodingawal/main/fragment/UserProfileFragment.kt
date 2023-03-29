package com.neotica.submissiondicodingawal.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.neotica.submissiondicodingawal.databinding.LayoutProfileBinding
import com.neotica.submissiondicodingawal.main.fragment.adapter.TabAdapter
import com.neotica.submissiondicodingawal.mvvm.GithubViewModel
import com.neotica.submissiondicodingawal.room.Entity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class UserProfileFragment : Fragment() {
    private lateinit var binding: LayoutProfileBinding
    private lateinit var tabAdapter: TabAdapter
    private val tabTitle = listOf(
        "Followers",
        "Following"
    )
    private val viewModel: GithubViewModel by viewModel()

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

    private fun viewPager() {
        val title = tabTitle
        binding.apply {
            viewPager.apply {
                tabAdapter = TabAdapter(this@UserProfileFragment)
                adapter = tabAdapter
                currentItem = 0
            }
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = title[position]
            }.attach()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindHEHE() {
        val avatar = UserProfileFragmentArgs.fromBundle(arguments as Bundle).avatar.toString()
        val profile = UserProfileFragmentArgs.fromBundle(arguments as Bundle).profile
        binding.apply {
            viewModel.getUserDetail(profile)
            viewModel.detailResponse.observe(viewLifecycleOwner) { github ->
                if (github != null) {
                    val followers = github.followers.toString()
                    val following = github.following.toString()
                    ivBookmark.setOnClickListener {
                        viewModel.setFavorite(Entity(profile, avatar, true), true)
                        Toast.makeText(context, "$profile added to favorite", Toast.LENGTH_SHORT)
                            .show()
                    }
                    tvFollowers.text = "Followers: $followers"
                    tvFollowing.text = "Following: $following"
                    tvName.text = github.name
                }
            }
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