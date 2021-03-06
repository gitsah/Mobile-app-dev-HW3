package com.example.sahand.homework3;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Test
    public void notEighteen() {
        onView(withId(R.id.name_field)).perform(typeText("Steve L")).perform(closeSoftKeyboard());
        onView(withId(R.id.date_button)).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.date_of_birth_field)).perform(typeText("02/10/2003")).perform(closeSoftKeyboard());
        onView(withId(R.id.username_field)).perform(typeText("SteveL")).perform(closeSoftKeyboard());
        onView(withId(R.id.occupation_field)).perform(typeText("Waiter")).perform(closeSoftKeyboard());
        onView(withId(R.id.description_field)).perform(typeText("I'm a pretty cool dude")).perform(closeSoftKeyboard());
        onView(withId(R.id.email_field)).perform(typeText("jackson@gmail.com")).perform(closeSoftKeyboard());
        onView(withId(R.id.submit_button)).perform(click());
        onView(withId(R.id.validation_text)).check(matches(withText(R.string.underEighteen)));
    }

    @Test
    public void badDateOfBirth() {
        onView(withId(R.id.name_field)).perform(typeText("Steve N")).perform(closeSoftKeyboard());
        onView(withId(R.id.date_of_birth_field)).perform(typeText("1-29-1988")).perform(closeSoftKeyboard());
        onView(withId(R.id.username_field)).perform(typeText("SteveN")).perform(closeSoftKeyboard());
        onView(withId(R.id.occupation_field)).perform(typeText("Waiter")).perform(closeSoftKeyboard());
        onView(withId(R.id.description_field)).perform(typeText("I'm a pretty coolish dude")).perform(closeSoftKeyboard());
        onView(withId(R.id.email_field)).perform(typeText("jackson@gmail.com")).perform(closeSoftKeyboard());
        onView(withId(R.id.submit_button)).perform(click());
        onView(withId(R.id.validation_text)).check(matches(withText(R.string.underEighteen)));
        onView(withId(R.id.date_of_birth_field)).perform(replaceText("1/28/1782")).perform(closeSoftKeyboard());
        onView(withId(R.id.submit_button)).perform(click());
        onView(withId(R.id.validation_text)).check(matches(withText(R.string.underEighteen)));
    }

    @Test
    public void unfilledFields() {
        onView(withId(R.id.date_of_birth_field)).perform(typeText("05/10/1967")).perform(closeSoftKeyboard());
        onView(withId(R.id.email_field)).perform(typeText("jjj@gmail.com")).perform(closeSoftKeyboard());
        onView(withId(R.id.submit_button)).perform(click());
        onView(withId(R.id.validation_text)).check(matches(withText(R.string.emptyFields)));
    }

    @Test
    public void to2ndPageAndBack() throws InterruptedException {
        onView(withId(R.id.name_field)).perform(typeText("John Michaels")).perform(closeSoftKeyboard());
        onView(withId(R.id.date_of_birth_field)).perform(typeText("01/20/1989")).perform(closeSoftKeyboard());
        onView(withId(R.id.email_field)).perform(typeText("JohnM@gmail.com")).perform(closeSoftKeyboard());
        onView(withId(R.id.username_field)).perform(typeText("JohnM")).perform(closeSoftKeyboard());
        onView(withId(R.id.occupation_field)).perform(typeText("Carpenter")).perform(closeSoftKeyboard());
        onView(withId(R.id.description_field)).perform(typeText("I'm a pretty cool dude")).perform(closeSoftKeyboard());
        onView(withId(R.id.submit_button)).perform(click());
        onView(withText("CANCEL")).perform(click());
        Thread.sleep(600);
        onView(withId(R.id.name_and_age_display)).check(matches(withText("John Michaels, 30")));
        onView(withId(R.id.occupation_display)).check(matches(withText("Carpenter")));
        onView(withId(R.id.description_display)).check(matches(withText("I'm a pretty cool dude")));
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        Thread.sleep(3000);
        onView(withRecyclerView(R.id.my_recycler_view).atPositionOnView(0, R.id.like_button)).perform(click());
        onView(withRecyclerView(R.id.my_recycler_view).atPositionOnView(0, R.id.like_button)).perform(click());
        onView(withId(R.id.viewpager)).perform(swipeUp());
        onView(withId(R.id.viewpager)).perform(swipeUp());
        onView(withId(R.id.viewpager)).perform(swipeUp());
        onView(withId(R.id.viewpager)).perform(swipeUp());
        onView(withId(R.id.viewpager)).perform(swipeUp());
        Thread.sleep(600);
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        Thread.sleep(600);

        onView(withId(R.id.settings_reminder_time_field)).perform(replaceText("9:02"));
        onView(withId(R.id.settings_age_range_min_field)).perform(replaceText("20"));
        onView(withId(R.id.settings_age_range_max_field)).perform(replaceText("32"));
        onView(withId(R.id.settings_interested_gender_field)).perform(replaceText("female"));
        onView(withId(R.id.settings_gender_field)).perform(replaceText("male"));
        onView(withId(R.id.settings_maximum_distance_field)).perform(replaceText("30"));
        onView(withId(R.id.settings_save_button)).perform(click());

        Espresso.pressBack();
        onView(withId(R.id.name_field)).check(matches(withText("")));
        onView(withId(R.id.date_of_birth_field)).check(matches(withText("")));
        onView(withId(R.id.username_field)).check(matches(withText("")));
        onView(withId(R.id.occupation_field)).check(matches(withText("")));
        onView(withId(R.id.description_field)).check(matches(withText("")));
    }

    //helper
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}