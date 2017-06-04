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

package jp.jun_nama.rxjavaespressosample;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import jp.jun_nama.rxjavaespressosample.databinding.ActivityRxJava1Binding;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RxJava1Activity extends AppCompatActivity {

    public CompositeSubscription compositeSubscription;
    private ActivityRxJava1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("RxJava 1");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rx_java1);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    protected void onStart() {
        super.onStart();
        compositeSubscription.add(RxView.clicks(binding.buttonDebounce)
                .observeOn(Schedulers.computation())
                .debounce(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> binding.textDebounceResult.setText("Debounce Completed")));

        compositeSubscription.add(RxView.clicks(binding.buttonSleep)
                .observeOn(Schedulers.io())
                .map(v -> {
                    SystemClock.sleep(3000L);
                    return v;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> binding.textSleepResult.setText("Sleep Completed")));
    }

    @Override
    protected void onStop() {
        compositeSubscription.clear();
        super.onStop();
    }
}
