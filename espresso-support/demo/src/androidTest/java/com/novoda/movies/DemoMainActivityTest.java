package com.novoda.movies;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DemoMainActivityTest {

    @Rule
    public ActivityTestRule<DemoMainActivity> activityRule = new ActivityTestRule<>(DemoMainActivity.class);

    @Test
    public void displaysListWithMovie1() {
        onView(withText("Movie 1")).
                check(matches(isDisplayed()));
    }

}
