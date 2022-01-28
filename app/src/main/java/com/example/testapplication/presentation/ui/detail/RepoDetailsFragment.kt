package com.example.testapplication.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.testapplication.databinding.FragmentRepoDetailsBinding
import com.example.testapplication.domain.entity.Repo
import com.example.testapplication.presentation.core.base.BaseFragment
import com.example.testapplication.presentation.core.base.createViewModel
import com.example.testapplication.presentation.core.base.observeData
import com.example.testapplication.presentation.core.base.observeDataEvent
import com.example.testapplication.presentation.util.getDateCreation
import com.example.testapplication.presentation.util.load


class RepoDetailsFragment : BaseFragment() {

    private val params by navArgs<RepoDetailsFragmentArgs>()

    private lateinit var binding: FragmentRepoDetailsBinding

    private lateinit var viewModel: RepoDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRepoDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = createViewModel(viewModelFactory) {
            getRepoById(params.repoId)
            observeData(repo, ::showRepoDetails)
            observeData(progressBarShown, ::setLoading)
            observeDataEvent(snackBar, ::showSnackbarEvent)
        }
    }

    private fun showRepoDetails(repo: Repo?) {
        repo?.apply {
            binding.repoStarsTextView.text = stars.toString()
            binding.repositoryNameTextView.text = name
            binding.repoCreatedTimeTextView.text = getDateCreation(createdAt)
            binding.repositoryFullNameTextView.text = fullName
            binding.repositoryDescriptionTextView.text = description
            binding.repoOwnerNameTextView.text = owner.login
            binding.repoOwnerAvatarImageView.load(owner.avatarUrl)
        }
    }

    private fun setLoading(shownLoading: Boolean?) {
        shownLoading?.apply {
            binding.progressBar.visibility = if (this) View.VISIBLE else View.GONE
        }
    }
}