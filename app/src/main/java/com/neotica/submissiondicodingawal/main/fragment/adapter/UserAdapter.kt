package com.neotica.submissiondicodingawal.main.fragment.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neotica.submissiondicodingawal.main.fragment.UserFragmentDirections
import com.neotica.submissiondicodingawal.databinding.IvUserListBinding
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import java.util.*

enum class FragmentType {
    USERS_FRAGMENT,
    DETAILS_FRAGMENT
}

class UserAdapter(private val users: List<GithubResponseItem>, usersFragment: FragmentType) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    class ListViewHolder(private val binding: IvUserListBinding, private val fragmentType: FragmentType) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(listUser: GithubResponseItem) {
            binding.apply {
                tvUsername.text = listUser.login
                    .replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }
                Glide.with(root)
                    .load(listUser.avatar_url)
                    .into(ivProfile)
                /*itemView.setOnClickListener {
                    if (fragmentType == FragmentType.DETAILS_FRAGMENT){
                        val action = UserFragmentDirections.actionUserFragmentSelf()
                        action.search
                        it.findNavController().navigate(action)
                    }
                }*/
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = IvUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding, fragmentType = FragmentType.DETAILS_FRAGMENT)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        Log.d(TAG, "Binding item at position $position")
        holder.bindData(users[position])
        val avatar = users[position].avatar_url
        val username = users[position].login
        val followers = users[position].followers_url
        val following = users[position].following_url
        holder.itemView.setOnClickListener { view ->
            val action =
                UserFragmentDirections.actionUserFragmentToUserProfileFragment(
                    avatar, username, followers, following
                )
            view.findNavController().navigate(action)
        }
    }
}