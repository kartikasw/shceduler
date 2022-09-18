import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.home.HomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun homeActivityTest() {
        Espresso.onView(withId(R.id.action_add))
            .check(matches(isDisplayed()))
            .perform(click())

        Espresso.onView(withId(R.id.add_ed_course)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.add_ed_lecturer)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.add_ed_note)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.add_spinner)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.btn_start_time)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.btn_end_time)).check(matches(isDisplayed()))
    }
}
