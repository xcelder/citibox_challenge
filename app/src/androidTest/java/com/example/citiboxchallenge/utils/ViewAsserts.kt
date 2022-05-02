package com.example.citiboxchallenge.utils

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matcher
import org.hamcrest.Matchers

fun Int.isVisible() = onView(withId(this)).isDisplayed()

fun onItemAtPosition(position: Int, matcher: Matcher<View>) =
    onView(itemAtPosition(position, matcher))

fun Int.tapOnItemAtPosition(position: Int) = onView(withId(this))
    .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))

fun checkAll(@IdRes vararg resIds: Int) = resIds.map { withId(it) }
    .let { Matchers.allOf(it) }
    .let { onView(it) }

fun onViewWithText(text: String) = onView(withText(text))

fun ViewInteraction.isDisplayed() = check(matches(ViewMatchers.isDisplayed()))