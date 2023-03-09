package com.neotica.submissiondicodingawal

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
import com.bumptech.glide.Glide
import com.neotica.submissiondicodingawal.databinding.LayoutProfileBinding
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileFragment : Fragment() {
    private lateinit var binding: LayoutProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = LayoutProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindHEHE()
    }

    private fun bindHEHE(){
        val avatar = UserProfileFragmentArgs.fromBundle(arguments as Bundle).profile.toString()
        binding.apply {
            Glide.with(root)
                .load(avatar)
                .into(ivProfile)
            tvUsername
        }
    }
}