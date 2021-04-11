package com.example.citiboxchallenge.presentation.features.characterslist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.citiboxchallenge.databinding.CharacterItemBinding
import com.example.domain.entities.Character

class CharacterListViewHolder(private val binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(character: Character, onCharacterSelected: (Character) -> Unit) {
        with(binding) {
            characterCard.setOnClickListener { onCharacterSelected(character) }
            name.text = character.name
            speciesAndType.text = "${character.species} ${character.type}"
            Glide.with(itemView.context)
                .load(character.imageUrl)
                .into(avatar)
        }
    }
}