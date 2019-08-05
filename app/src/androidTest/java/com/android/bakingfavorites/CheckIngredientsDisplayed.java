package com.android.bakingfavorites;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CheckIngredientsDisplayed {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkIngredientsDisplayed() throws InterruptedException{
        //wait for data to be loaded...
        Thread.sleep(1000);
        ViewInteraction textView = onView(
                allOf(withId(R.id.recycler_recipe_text), withText("Nutella Pie"),
                        childAtPosition(
                                allOf(withId(R.id.recipe_list_item_layout),
                                        childAtPosition(
                                                withId(R.id.main_recycler_view),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Nutella Pie")));

        ViewInteraction relativeLayout = onView(
                allOf(withId(R.id.recipe_list_item_layout),
                        childAtPosition(
                                allOf(withId(R.id.main_recycler_view),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        relativeLayout.perform(click());

        //wait a bit for info to load...

        Thread.sleep(1000);

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.ingredients_ingredient), withText("Graham Cracker crumbs"),
                        childAtPosition(
                                allOf(withId(R.id.list_item_ingredients_layout),
                                        childAtPosition(
                                                withId(R.id.ingredients_recycler_view),
                                                0)),
                                1),
                        isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
