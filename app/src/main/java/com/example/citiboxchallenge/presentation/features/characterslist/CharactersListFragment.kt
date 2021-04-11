package com.example.citiboxchallenge.presentation.features.characterslist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.citiboxchallenge.R
import com.example.citiboxchallenge.databinding.CharactersListFragmentBinding
import com.example.citiboxchallenge.presentation.features.characterslist.adapter.CharacterListAdapter
import com.example.domain.entities.Character
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CharactersListFragment : Fragment() {

    private lateinit var binding: CharactersListFragmentBinding

    private val viewModel: CharactersListViewModel by viewModel{ parametersOf(findNavController()) }

    private val adapter: CharacterListAdapter by lazy { CharacterListAdapter(viewModel::onCharacterSelected) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharactersListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        startListeningStateChanges()

        viewModel.loadNextCharactersPage()
    }

    private fun setupViews() {
        binding.charactersView.adapter = adapter
    }

    private fun startListeningStateChanges() {
        lifecycleScope.launchWhenCreated {
            viewModel.characters.collect(::updateCharactersList)
        }
        lifecycleScope.launchWhenCreated {
            viewModel.charactersListStateFlow.collect(::handleStateUpdate)
        }
    }

    private fun handleStateUpdate(state: CharactersListState) = when (state) {
        CharactersListState.Loading -> showLoading()
        CharactersListState.Ready -> hideLoading()
        CharactersListState.Error -> {
            hideLoading()
            showError()
        }
    }

    private fun updateCharactersList(characters: List<Character>) {
        adapter.submitList(characters)
    }

    private fun showLoading() {
        binding.loadingView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loadingView.visibility = View.GONE
    }

    private fun showError() {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.characters_error_message)
            .setPositiveButton(R.string.positive_button) { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
