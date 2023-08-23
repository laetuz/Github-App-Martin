package com.neotica.submissiondicodingawal.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
import com.neotica.submissiondicodingawal.presentation.fragment.adapter.FollowersAdapter
import com.neotica.submissiondicodingawal.presentation.viewmodel.GithubViewModel
import com.neotica.submissiondicodingawal.core.data.remote.model.GithubResponseItem
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowersFragment : Fragment() {
    private lateinit var binding: RvUserListBinding
    private val viewModel: GithubViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RvUserListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch { getFollowers() }
    }

    private suspend fun getFollowers() {
        val test = requireParentFragment().arguments?.let {
            FollowersFragmentArgs.fromBundle(it)
        }?.profile
        if (test != null) {
            viewModel.getFollowers(test)
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isLoadingFollower.collect{
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.followerResponse.collect {
                if (it != null) {
                    setRecView(it)
                }
            }
        }
    }

    private fun setRecView(listData: List<GithubResponseItem>?) {
        val adapter = listData?.let { FollowersAdapter(it) }
        binding.rvHomeList.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
        val layoutManager = LinearLayoutManager(context)
        binding.apply {
            val itemDivider = DividerItemDecoration(context, layoutManager.orientation)
            rvHomeList.addItemDecoration(itemDivider)
        }
        if (listData == null) {
            Toast.makeText(context, "data is zero", Toast.LENGTH_SHORT).show()
        }
    }
}