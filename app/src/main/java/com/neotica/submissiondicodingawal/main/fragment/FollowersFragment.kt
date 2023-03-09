package com.neotica.submissiondicodingawal.main.fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.navigation.NavController
import com.neotica.submissiondicodingawal.main.MainAdapter
import com.neotica.submissiondicodingawal.retrofit.ApiConfig

class FollowersFragment : Fragment() {
    private lateinit var binding: RvUserListBinding
    private lateinit var navController: NavController

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
        getUser()
    }

    private fun showLoading(isLoading: Boolean) {
        //Step 24: Declare the condition
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getUser() {
        showLoading(true)
        val followers = UserProfileFragmentArgs.fromBundle(arguments as Bundle).followers
        // TODO: Make repository and viewmodel for getfollowers 
        val client = ApiConfig.getApiService().getFollowers(followers)
        client.enqueue(object : Callback<List<GithubResponseItem>> {
            override fun onResponse(
                call: Call<List<GithubResponseItem>>,
                response: Response<List<GithubResponseItem>>,
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    navController = NavController(requireContext())
                    setRecView(responseBody, navController)
                } else {
                    Log.e(ContentValues.TAG,"On failure: ${response.message()}")
                    Toast.makeText(context, "else ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                showLoading(false)
                Log.e(ContentValues.TAG,"On failure: ${t.message}")
                Toast.makeText(context, "onfailure ${t.message}", Toast.LENGTH_SHORT).show()
            }


        })
    }

    private fun setRecView(listData: List<GithubResponseItem>?, navController: NavController) {
        val adapter = listData?.let { MainAdapter(it) }
        val layoutManager = LinearLayoutManager(context)
        binding.apply {
            rvHomeList.layoutManager = layoutManager
            val itemDivider = DividerItemDecoration(context, layoutManager.orientation)
            rvHomeList.addItemDecoration(itemDivider)
            rvHomeList.adapter = adapter
        }
        listData?.get(0)
    }
}