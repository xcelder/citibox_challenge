package com.example.citiboxchallenge.presentation.features.characterslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.citiboxchallenge.databinding.CharacterItemBinding
import com.example.domain.entities.Character

class CharacterListAdapter(
    private val onCharacterSelected: (Character) -> Unit
) : ListAdapter<Character, CharacterListViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val characterItemBinding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterListViewHolder(characterItemBinding)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        holder.bind(getItem(position), onCharacterSelected)
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Character, newItem: Character) = with(oldItem) {
        name == newItem.name &&
                episode == newItem.episode &&
                imageUrl == newItem.imageUrl &&
                species == newItem.species &&
                type == newItem.type &&
                location == newItem.location
    }
}
