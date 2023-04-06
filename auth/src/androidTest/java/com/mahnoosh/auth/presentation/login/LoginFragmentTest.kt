package com.mahnoosh.auth.presentation.login



import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mahnoosh.auth.presentation.AuthActivity
import com.mahnoosh.auth.presentation.splash.SplashFragment
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
internal class LoginFragmentTest {

    @get : Rule
    var activityRule = ActivityScenarioRule(AuthActivity::class.java)

    @Before
    fun setUp() {
//        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
//        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun `test`() {

        //Getting the NavController for test
//        val navController = TestNavHostController(
//            ApplicationProvider.getApplicationContext()
//        )
//        val loginScenario = launchFragmentInContainer<LoginFragment>()
//        loginScenario.onFragment { fragment ->
//            // Set the graph on the TestNavHostController
//            navController.setGraph(com.mahnoosh.auth.R.navigation.auth_nav_graph)
//
//            // Make the NavController available via the findNavController() APIs
//            Navigation.setViewNavController(fragment.requireView(), navController)
//
            onView(ViewMatchers.withId(com.mahnoosh.auth.R.id.no_account)).perform(click())
//        }
    }
}