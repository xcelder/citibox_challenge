package com.example.citiboxchallenge.presentation.features.charactermeetup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.citiboxchallenge.databinding.CharacterMeetupFragmentBinding
import java.text.SimpleDateFormat
import java.util.*

class CharacterMeetUpFragment : Fragment() {

    private lateinit var binding: CharacterMeetupFragmentBinding

    private val args: CharacterMeetUpFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharacterMeetupFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupViews()
    }

    private fun CharacterMeetupFragmentBinding.setupViews() {
        with(args.charactersMeetUp) {
            firstCharacterAvatar.loadGlideImageFromUrl(characters.first.imageUrl)
            firstCharacterName.text = characters.first.name

            secondCharacterAvatar.loadGlideImageFromUrl(characters.second.imageUrl)
            secondCharacterName.text = characters.second.name

            this@setupViews.location.text = location.name

            this@setupViews.episodesTogether.text = episodesTogether.toString()

            val dateFormat = "MMMM dd, yyyy"

            this@setupViews.firstMeet.text = SimpleDateFormat(dateFormat, Locale.getDefault()).format(firstMeet)
            this@setupViews.lastMeet.text = SimpleDateFormat(dateFormat, Locale.getDefault()).format(lastMeet)
        }
    }

    private fun ImageView.loadGlideImageFromUrl(url: String) =
        Glide.with(this@CharacterMeetUpFragment)
            .load(url)
            .into(this)
}