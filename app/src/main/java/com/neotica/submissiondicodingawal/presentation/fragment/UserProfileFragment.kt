package com.neotica.submissiondicodingawal.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.neotica.submissiondicodingawal.data.local.database.Entity
import com.neotica.submissiondicodingawal.databinding.LayoutProfileBinding
import com.neotica.submissiondicodingawal.presentation.fragment.adapter.TabAdapter
import com.neotica.submissiondicodingawal.presentation.viewmodel.GithubViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class UserProfileFragment : Fragment() {
    private var _binding: LayoutProfileBinding? = null
    private val binding get() = _binding!!
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
        _binding = LayoutProfileBinding.inflate(layoutInflater, container, false)
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
        val args = UserProfileFragmentArgs.fromBundle(arguments as Bundle)
        val avatar = args.avatar.toString()
        val profile = args.profile
        viewModel.getUserDetail(profile)

        binding.apply {
            Glide.with(root)
                .load(avatar)
                .circleCrop()
                .into(ivProfile)
            tvUsername.text = profile
                .replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                }
            ivBookmark.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.setFavorite(Entity(profile, avatar, true))
                }
                Toast.makeText(context, "$profile added to favorite", Toast.LENGTH_SHORT)
                    .show()
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.isLoadingDetail.collect{
                    binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
                }
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.detailResponse.collect { github ->
                    if (github != null) {
                        val followers = github.followers.toString()
                        val following = github.following.toString()

                        tvFollowers.text = "Followers: $followers"
                        tvFollowing.text = "Following: $following"
                        tvName.text = github.name
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}