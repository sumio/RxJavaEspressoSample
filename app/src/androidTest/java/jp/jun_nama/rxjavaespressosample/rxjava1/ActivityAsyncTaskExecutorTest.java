/*
 * Copyright (C) 2017 TOYAMA Sumio <jun.nama@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.jun_nama.rxjavaespressosample.rxjava1;


import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import jp.jun_nama.rxjavaespressosample.R;
import jp.jun_nama.rxjavaespressosample.RxJava1Activity;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ActivityAsyncTaskExecutorTest {
    private UiDevice uiDevice;

    @Rule
    public ActivityTestRule<RxJava1Activity> activityTestRule = new ActivityTestRule<>(RxJava1Activity.class, false, false);

    @Before
    public void setUp() throws Exception {
        // for using UiDevice.wait()
        setUpUiAutomator();

        // RxJava Synchronization in Espresso
        replaceRxSchedulerWithAsyncTask();

        // launch Activity Under Test
        activityTestRule.launchActivity(null);
    }


    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
    }

    @Test
    public void test_wait_debounce() {
        onView(ViewMatchers.withId(R.id.button_debounce)).perform(click());

        // In this case, CountingIdlingResource has no effect.
        // Use UiDevice.wait() of UIAutomator instead.
        uiDevice.wait(Until.hasObject(By.text("Debounce Completed")), 5000L);
        onView(withId(R.id.text_debounce_result))
                .check(matches(withText("Debounce Completed")));
    }

    @Test
    public void test_wait_sleep_in_map() {
        onView(withId(R.id.button_sleep)).perform(click());
        onView(withId(R.id.text_sleep_result))
                .check(matches(withText("Sleep Completed")));
    }

    private void replaceRxSchedulerWithAsyncTask() {
        RxJavaHooks.setOnComputationScheduler(old -> Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR));
        RxJavaHooks.setOnIOScheduler(old -> Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR));
        RxJavaHooks.setOnNewThreadScheduler(old -> Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR));
    }

    private void setUpUiAutomator() {
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

}
