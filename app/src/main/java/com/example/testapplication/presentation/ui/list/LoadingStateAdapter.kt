package com.example.testapplication.presentation.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.databinding.ItemLoadStateBinding
import com.google.android.material.snackbar.Snackbar

class LoadingStateAdapter : LoadStateAdapter<LoadingStateAdapter.LoadStateViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ) = LoadStateViewHolder(parent)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState,
    ) = holder.bind(loadState)

    class LoadStateViewHolder(
        parent: ViewGroup,
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_load_state, parent, false)
    ) {
        private val binding = ItemLoadStateBinding.bind(itemView)
        private val progressBar: ProgressBar = binding.progressBar
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                Snackbar.make(
                    progressBar,
                    loadState.error.localizedMessage ?: "",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            progressBar.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
        }
    }
}
