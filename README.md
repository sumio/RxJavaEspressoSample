# RxJavaEspressoSample

Samples of Espresso synchronizing with RxJava

[Rx Ja Night Vol.2](https://connpass.com/event/57150/)のトーク「RxJavaの非同期処理に負けないEspressoテストコードを書く」のサンプルコードです。

# Contents

## Activity Under Tests

### RxJava1Activity

画面上に`Debounce`ボタンと`Sleep in Map Op.`ボタンが表示されています。RxJava1で実装しています。

- `Debounce`ボタンを**最後に**押してから3秒経過すると、ボタンの右側に`Debounce Completed`というテキストが表示されます。
  クリックイベントのストリームに対して、`debounce(3, TimeUnit.SECONDS）`を適用することで実現しています。
- `Sleep in Map Op.`ボタンを押してから3秒経過すると、ボタンの右側に`Sleep Completed`というテキストが表示されます。
  クリックイベントのストリームに対して、`map()`オペレーター内で3秒スリープすることで実現しています。

### RxJava2Activity

RxJava2で実装していることを除いては、`RxJava1Activity`と同じです。

## Instrumented Tests

### Package `jp.jun_nama.rxjavaespressosample.rxjava1`

#### ActivityCountingIdlingResourceTest

RxJava1Activityに対するテストを、Espressoを使って書いています。
具体的なテスト内容は以下の通りです。

- `Debounce`ボタンを押してしばらくしたら`Debounce Completed`というテキストが表示されること
- `Sleep in Map Op.`ボタンを押してしばらくしたら`Sleep Completed`というテキストが表示されること

RxJavaの非同期処理の完了を待ち合わせる方法として、[Rx Ja Night Vol.2](https://connpass.com/event/57150/)で紹介した内容を採用しています。

#### ActivityAsyncTaskExecutorTest

以下の点を除いて、ActivityCountingIdlingResourceTestと同じです。

- RxJavaのスケジューラを`AsyncTask.THREAD_POOL_EXECUTOR`に置き換えることで、RxJavaの非同期処理の完了を待ち合わせています。

### Package `jp.jun_nama.rxjavaespressosample.rxjava2`

RxJava2Activityに対するテストをしている点を除いて、
`jp.jun_nama.rxjavaespressosample.rxjava1`パッケージ配下のテストと同じです。

# License

Copyright 2017 TOYAMA Sumio &lt;jun.nama@gmail.com&gt;  
Licensed under the
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
