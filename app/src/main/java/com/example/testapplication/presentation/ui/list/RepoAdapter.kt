package com.example.testapplication.presentation.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.databinding.ItemRepositoryBinding
import com.example.testapplication.domain.entity.Repo

class RepoAdapter(private val itemClick: (Int) -> Unit) :
    PagingDataAdapter<Repo, RepoAdapter.ViewHolder>(RepoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    class ViewHolder(private val binding: ItemRepositoryBinding, val itemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: Repo?) {
            item?.apply {
                binding.repositoryNameTextView.text = name
                binding.repositoryDescriptionTextView.text = description

                binding.card.setOnClickListener {
                    itemClick(id)
                }
            }
        }
    }
}

class RepoDiffCallback : DiffUtil.ItemCallback<Repo>() {

    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem.name == newItem.name && oldItem.description == newItem.description
    }
}