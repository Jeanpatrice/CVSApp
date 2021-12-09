package com.pmz.cvsapp.view


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.pmz.cvsapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class FlickrActivityTestSearchFragment {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(FlickrActivity::class.java)

    @Test
    fun flickrActivityTestSearchFragment() {
        val materialAutoCompleteTextView = onView(
            allOf(
                withId(R.id.query_auto_tv),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.search_text_input),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialAutoCompleteTextView.perform(click())

        val materialAutoCompleteTextView2 = onView(
            allOf(
                withId(R.id.query_auto_tv),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.search_text_input),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialAutoCompleteTextView2.perform(replaceText("firefly"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.search_btn), withText("Search"),
                childAtPosition(
                    allOf(
                        withId(R.id.cl_search_page),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
