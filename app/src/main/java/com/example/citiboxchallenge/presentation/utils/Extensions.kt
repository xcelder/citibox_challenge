package com.example.citiboxchallenge.presentation.utils

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.citiboxchallenge.R

fun Fragment.setActionBarTitle(@StringRes titleRes: Int) {
    (requireActivity() as? AppCompatActivity)?.supportActionBar?.title = getString(titleRes)
}