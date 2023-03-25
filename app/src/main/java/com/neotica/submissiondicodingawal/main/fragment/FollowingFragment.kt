package com.neotica.submissiondicodingawal.main.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import androidx.navigation.NavController
import com.neotica.submissiondicodingawal.main.fragment.adapter.FollowingAdapter
import com.neotica.submissiondicodingawal.main.fragment.adapter.FragmentType
import com.neotica.submissiondicodingawal.main.fragment.adapter.UserAdapter
import com.neotica.submissiondicodingawal.mvvm.GithubViewModel
import com.neotica.submissiondicodingawal.mvvm.GithubViewModelFactory
import kotlinx.coroutines.launch

class FollowingFragment : Fragment() {
    private lateinit var binding: RvUserListBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<GithubViewModel> { GithubViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getFollowing(){
        showLoading(true)
        val following = requireParentFragment().arguments?.let {
            FollowingFragmentArgs.fromBundle(it)
        }?.profile
        if (following != null){
            viewModel.getFollowing(following)
        }
        viewModel.githubResponse.observe(viewLifecycleOwner) {
            github ->
            if (github != null) {
                showLoading(false)
                setRecView(github)
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
        if (listData == null){
            Toast.makeText(context, "data is zero", Toast.LENGTH_SHORT).show()
        }
    }
}