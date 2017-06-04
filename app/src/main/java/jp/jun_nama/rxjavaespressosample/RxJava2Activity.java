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

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import jp.jun_nama.rxjavaespressosample.databinding.ActivityRxJava2Binding;

public class RxJava2Activity extends AppCompatActivity {

    public CompositeDisposable compositeDisposable;
    public ActivityRxJava2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("RxJava 2");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rx_java2);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onStart() {
        super.onStart();
        compositeDisposable.add(RxView.clicks(binding.buttonDebounce)
                .observeOn(Schedulers.computation())
                .debounce(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> binding.textDebounceResult.setText("Debounce Completed")));

        compositeDisposable.add(RxView.clicks(binding.buttonSleep)
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
        compositeDisposable.clear();
        super.onStop();
    }
}
