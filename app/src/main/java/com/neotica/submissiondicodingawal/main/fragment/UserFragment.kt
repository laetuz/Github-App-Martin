package com.neotica.submissiondicodingawal.main.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.neotica.submissiondicodingawal.R
import com.neotica.submissiondicodingawal.databinding.IvUserListBinding
import com.neotica.submissiondicodingawal.main.fragment.adapter.UserAdapter
import com.neotica.submissiondicodingawal.mvvm.GithubViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class UserFragment : Fragment() {
    private lateinit var binding: RvUserListBinding
    private lateinit var itemList: IvUserListBinding

    private val viewModel : GithubViewModel by viewModel()

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
        setTheme()
    }

    private fun getUserViewModel(){
        viewModel.getUser()
        binding.progressBar.isVisible = true
        viewModel.githubResponse.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                bindData(it[0])
                setRecView(it)
            }
            viewModel.isLoading.observe(viewLifecycleOwner){
                binding.progressBar.isVisible = it
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
                .load(listUser.avatar_url)
                .into(ivProfile)
        }
    }

    private fun setRecView(listData: List<GithubResponseItem>?) {
        val adapter = listData?.let { UserAdapter(it) }
        binding.rvHomeList.apply {
            layoutManager=LinearLayoutManager(context)
            this.adapter = adapter
        }
        val layoutManager = LinearLayoutManager(context)
        binding.apply {
            val itemDivider = DividerItemDecoration(context, layoutManager.orientation)
            rvHomeList.addItemDecoration(itemDivider)
        }
        listData?.get(0)
    }
    /*---------------SEARCH----------------*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.favorite -> {
                val action = UserFragmentDirections.actionUserFragmentToFavoriteFragment()
                findNavController().navigate(action)
                true
            }
            R.id.theme -> {
                val current = when(AppCompatDelegate.getDefaultNightMode()){
                    AppCompatDelegate.MODE_NIGHT_NO -> "LIGHT"
                    AppCompatDelegate.MODE_NIGHT_YES -> "DARK"
                    else -> "LOl"
                }
                viewModel.saveThemeSetting(current)
                true
            }
            /*{
                val currentMode = AppCompatDelegate.getDefaultNightMode()
                val newMode = if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.MODE_NIGHT_NO
                } else {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
                AppCompatDelegate.setDefaultNightMode(newMode)
                true
            }*/
            else -> true
        }
    }
    private fun setTheme(){
        viewModel.getThemeSettings().observe(viewLifecycleOwner){
            if(it=="DARK"){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(androidx.appcompat.R.string.abc_search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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
    /*Light mode*/
}