package com.example.testapplication.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentRepositoryListBinding
import com.example.testapplication.presentation.core.base.BaseFragment
import com.example.testapplication.presentation.core.base.createViewModel
import com.example.testapplication.presentation.ui.base.HorizontalDividerItemDecoration
import com.example.testapplication.presentation.ui.base.VerticalItemDecoration
import com.example.testapplication.presentation.util.convertToDp
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest

class RepoListFragment : BaseFragment() {

    private lateinit var binding: FragmentRepositoryListBinding

    private lateinit var repositoryAdapter: RepoAdapter

    private lateinit var viewModel: RepoListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRepositoryListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        repositoryAdapter = RepoAdapter { repoId ->
            showRepoDetailsFragment(repoId)
        }
        repositoryAdapter.addLoadStateListener { loadState ->
            val loadStateError: LoadState.Error? = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            loadStateError?.error?.message?.apply {
                Snackbar.make(requireView(), this, Snackbar.LENGTH_LONG).show()
            }
        }
        val loadingStateAdapter = LoadingStateAdapter()
        val concatAdapter = repositoryAdapter.withLoadStateFooter(loadingStateAdapter)

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            val indent = requireContext().convertToDp(R.dimen.middle_indent)
            addItemDecoration(VerticalItemDecoration(indent, indent))
            addItemDecoration(HorizontalDividerItemDecoration(indent))
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
            adapter = concatAdapter
        }
    }

    private fun showRepoDetailsFragment(repoId: Int) {
        val action = RepoListFragmentDirections.showRepoDetails(repoId)
        binding.recyclerView.findNavController().navigate(action)
    }

    private fun initViewModel() {
        viewModel = createViewModel(viewModelFactory) {
            lifecycleScope.launchWhenCreated {
                repos.collectLatest {
                    repositoryAdapter.submitData(it)
                }
            }
        }
    }
}