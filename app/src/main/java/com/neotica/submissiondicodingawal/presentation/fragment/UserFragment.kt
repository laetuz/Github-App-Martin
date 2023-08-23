package com.neotica.submissiondicodingawal.presentation.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neotica.submissiondicodingawal.R
import com.neotica.submissiondicodingawal.core.data.remote.model.GithubResponseItem
import com.neotica.submissiondicodingawal.databinding.IvUserListBinding
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
import com.neotica.submissiondicodingawal.presentation.fragment.adapter.UserAdapter
import com.neotica.submissiondicodingawal.presentation.viewmodel.GithubViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class UserFragment : Fragment() {
    private var _binding: RvUserListBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemList: IvUserListBinding

    private val viewModel: GithubViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RvUserListBinding.inflate(layoutInflater, container, false)
        itemList = IvUserListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        getUserViewModel()
        setTheme()
    }

    private fun getUserViewModel() {
        viewModel.getUser()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isLoadingHome.collect{
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.homeResponse.collect {
                if (it?.isNotEmpty() == true) {
                    bindData(it[0])
                    setRecView(it)
                }
            }
        }
    }

    private fun bindData(listUser: GithubResponseItem) {
        itemList.apply {
            tvUsername.text = listUser.login
                .replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
            Glide.with(root)
                .load(listUser.avatarUrl)
                .circleCrop()
                .into(ivProfile)
        }
    }

    private fun setRecView(listData: List<GithubResponseItem>?) {
        val adapter = listData?.let { UserAdapter(it) }
        binding.rvHomeList.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
        val layoutManager = LinearLayoutManager(context)
        binding.apply {
            val itemDivider = DividerItemDecoration(context, layoutManager.orientation)
            rvHomeList.addItemDecoration(itemDivider)
        }
        listData?.get(0)
    }

    /*---------------ACTION BAR----------------*/
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                val action = UserFragmentDirections.actionUserFragmentToFavoriteFragment()
                findNavController().navigate(action)
                true
            }
            R.id.themePage -> {
                val action = UserFragmentDirections.actionUserFragmentToThemeFragment()
                findNavController().navigate(action)
                true
            }
            else -> false
        }
    }

    private fun setTheme() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getThemeSettings().collect{
                if (it == "DARK") {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager =
            requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(androidx.appcompat.R.string.abc_search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val profile = query.toString()
                val navController = Navigation.findNavController(binding.root)
                val sendBundle =
                    UserFragmentDirections.actionUserFragmentToSearchFragment(profile)
                navController.navigate(sendBundle)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}