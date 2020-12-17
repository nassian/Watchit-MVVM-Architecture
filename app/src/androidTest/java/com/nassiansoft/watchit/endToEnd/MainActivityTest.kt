package com.nassiansoft.watchit.endToEnd

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nassiansoft.watchit.MainActivity
import com.nassiansoft.watchit.R
import com.nassiansoft.watchit.data.FakeRemoteDataSource
import com.nassiansoft.watchit.data.model.Movie
import com.nassiansoft.watchit.ui.home.HomeAdapter
import com.nassiansoft.watchit.ui.search.SearchAdapter
import kotlinx.coroutines.delay
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun testMovie(){

        val scenario=ActivityScenario.launch(MainActivity::class.java)

        //go to search fragment
        onView(withId(R.id.floatingActionButton)).check(matches(isDisplayed()))
        onView(withId(R.id.floatingActionButton)).perform(click())

        //perform search
        onView(withId(R.id.searchEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.searchEditText)).perform(replaceText("term"))
        onView(withId(R.id.searchButton)).perform(click())


        //add a movie
        onView(withText(FakeRemoteDataSource.movie1.trackName)).check(matches(isDisplayed()))
        onView(withId(R.id.searchRecyclerView)).perform(
            RecyclerViewActions.
            actionOnItemAtPosition<SearchAdapter.Vh>(0,ClickOnAddBtn())
        )
        pressBack()


        // if the movie is saved
        onView(withText(FakeRemoteDataSource.movie1.trackName))
            .check(matches(isDisplayed()))

        //check as watched
        onView(withId(R.id.homeRecyclerView)).perform(
            RecyclerViewActions.
            actionOnItemAtPosition<HomeAdapter.HomeVh>(0,ClickWatchedCheckBox())
        )

        //delete movie
        onView(withId(R.id.homeRecyclerView)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<HomeAdapter.HomeVh>(0, click())
        )
        onView(withId(R.id.detail_delete_button)).perform(click())
        onView(withId(R.id.homeRecyclerView)).check( matches(hasChildCount(0)))


        scenario.close()

    }


    class ClickOnAddBtn :ViewAction{
        override fun getConstraints(): Matcher<View> =
           click().constraints


        override fun getDescription(): String =
            "Click on me"

        override fun perform(uiController: UiController?, view: View?) {
            click().perform(uiController,view?.findViewById(R.id.addImageView))
        }

    }

    class ClickWatchedCheckBox:ViewAction{
        override fun getConstraints(): Matcher<View> =
            click().constraints

        override fun getDescription(): String ="Check me"

        override fun perform(uiController: UiController?, view: View?) {
            click().perform(uiController,view?.findViewById(R.id.watchedCheckBox))
        }

    }
}