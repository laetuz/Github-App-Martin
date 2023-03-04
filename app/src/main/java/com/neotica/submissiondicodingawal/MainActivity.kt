package com.neotica.submissiondicodingawal

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
import com.neotica.submissiondicodingawal.response.GithubResponse
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: RvUserListBinding
    private lateinit var adapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RvUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        val client = ApiConfig.getApiService().getUser()
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    setRecView()
                    val responseBody = response.body()
                    if (responseBody != null) {
                        response.body()?.responseItem
                    }
                } else {
                    Log.e(TAG,"On failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
            }


        })
    }

    private fun setRecView() {
        adapter = MainAdapter()
        adapter.setData(arrayListOf())
        val layoutManager = LinearLayoutManager(this)
        binding.rvHomeList.layoutManager = layoutManager
        val itemDivider = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvHomeList.addItemDecoration(itemDivider)
        binding.rvHomeList.adapter = adapter
    }

    private fun adapter(){

    }
    private fun setUserList(userList: List<GithubResponseItem>){
        val thatList = ArrayList<String>()
        for (login in userList){
            thatList.add(
                """
                    ${login.login}
                    ${login.avatar_url}
                """.trimIndent()
            )
        }
    }
}