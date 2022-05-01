package com.example.citiboxchallenge.presentation.features.characterslist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.example.citiboxchallenge.R
import com.example.citiboxchallenge.databinding.CharactersListFragmentBinding
import com.example.citiboxchallenge.presentation.features.characterslist.adapter.CharacterListAdapter
import com.example.domain.entities.Character

class CharactersListFragment : Fragment(), MavericksView {

    private lateinit var binding: CharactersListFragmentBinding

    private val viewModel: CharactersListViewModel by fragmentViewModel()

    private val errorAlert: AlertDialog by lazy {
        AlertDialog.Builder(requireContext())
        .setMessage(R.string.characters_error_message)
        .setPositiveButton(R.string.positive_button) { dialog, _ -> dialog.dismiss() }
        .show()
    }

    private val adapter: CharacterListAdapter by lazy {
        CharacterListAdapter(onCharacterSelected = {
            withState(viewModel) { state ->
                if (!state.isLoading) {
                    viewModel.onCharacterSelected(it)
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadNextCharactersPage()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharactersListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        startListeningStateChanges()
    }

    private fun setupViews() {
        with(binding.charactersView) {
            adapter = this@CharactersListFragment.adapter
            addOnScrollListener(onScrollChangeListener)
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        if (state.isError) showError()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.charactersView.clearOnScrollListeners()
    }

    private fun startListeningStateChanges() {
        viewModel.onEach(CharactersListState::characters) { updateCharactersList(it) }
        viewModel.onEach(CharactersListState::isLoading) { showLoading(it) }
    }

    private fun showLoading(show: Boolean) {
        if (show) showLoading() else hideLoading()
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
        if (!errorAlert.isShowing) errorAlert.show()
    }

    private val onScrollChangeListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            withState(viewModel) { state ->
                if (!state.isLoading) {
                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition()
                    val lastPosition = recyclerView.adapter?.itemCount?.minus(1) ?: -1

                    if (lastVisibleItemPosition == lastPosition) {
                        viewModel.loadNextCharactersPage()
                    }
                }
            }
        }
    }
}
