package com.neotica.submissiondicodingawal

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
        client.enqueue(object : Callback<List<GithubResponseItem>> {
            override fun onResponse(
                call: Call<List<GithubResponseItem>>,
                response: Response<List<GithubResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    setRecView(responseBody)
                } else {
                    Log.e(TAG,"On failure: ${response.message()}")
                    Toast.makeText(this@MainActivity, "else ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG,"On failure: ${t.message}")
                Toast.makeText(this@MainActivity, "onfailure ${t.message}", Toast.LENGTH_SHORT).show()
            }


        })
    }

    private fun setRecView(listData: List<GithubResponseItem>?) {
        val adapter = listData?.let { MainAdapter(it) }
        val layoutManager = LinearLayoutManager(this)
        binding.apply {
            rvHomeList.layoutManager = layoutManager
            val itemDivider = DividerItemDecoration(this@MainActivity, layoutManager.orientation)
            rvHomeList.addItemDecoration(itemDivider)
            rvHomeList.adapter = adapter
        }
        listData?.get(0)
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