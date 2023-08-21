package com.neotica.submissiondicodingawal.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.neotica.submissiondicodingawal.data.remote.model.GithubResponseItem
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
import com.neotica.submissiondicodingawal.presentation.fragment.adapter.FollowingAdapter
import com.neotica.submissiondicodingawal.presentation.viewmodel.GithubViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingFragment : Fragment() {
    private lateinit var binding: RvUserListBinding
    private val viewModel: GithubViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = RvUserListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch { getFollowing() }
    }

    private suspend fun getFollowing() {
        val following = requireParentFragment().arguments?.let {
            FollowingFragmentArgs.fromBundle(it)
        }?.profile
        if (following != null) {
            viewModel.getFollowing(following)
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isLoadingFollowing.collect{
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.followingResponse.collect {
                if (it != null) {
                    setRecView(it)
                }
            }
        }
    }

    private fun setRecView(listData: List<GithubResponseItem>?) {
        val adapter = listData?.let { FollowingAdapter(it) }
        val layoutManager = LinearLayoutManager(context)
        binding.apply {
            rvHomeList.layoutManager = layoutManager
            val itemDivider = DividerItemDecoration(context, layoutManager.orientation)
            rvHomeList.addItemDecoration(itemDivider)
            rvHomeList.adapter = adapter
        }
        if (listData == null) {
            Toast.makeText(context, "data is zero", Toast.LENGTH_SHORT).show()
        }
    }
}