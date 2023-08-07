package com.neotica.submissiondicodingawal.view.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
import com.neotica.submissiondicodingawal.databinding.IvUserListBinding
import com.neotica.submissiondicodingawal.view.fragment.adapter.FavoriteAdapter
import com.neotica.submissiondicodingawal.viewmodel.GithubViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {
    private lateinit var binding: RvUserListBinding
    private lateinit var itemList: IvUserListBinding

    private val viewModel: GithubViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = RvUserListBinding.inflate(layoutInflater, container, false)
        itemList = IvUserListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        getUserViewModel()
    }

    private fun getUserViewModel() {
        val gitAdapter = FavoriteAdapter(viewModel)
        binding.progressBar.isVisible = true
        lifecycleScope.launchWhenStarted {
            viewModel.getFavorite().collect{
                binding.progressBar.isVisible = false
                gitAdapter.submitList(it)
            }
        }
        binding.apply {
            rvHomeList.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = gitAdapter
            }
        }
    }
}