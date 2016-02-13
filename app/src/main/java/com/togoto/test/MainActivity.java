package com.togoto.test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends FragmentActivity {
    TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx = (TextView) findViewById(R.id.textView);

        final Button btn = (Button) findViewById(R.id.button);
        Subscription subscribe = RxView.clicks(btn)
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void aVoid) {
                        btn.setVisibility(View.GONE);
                    }
                });

       EditText editText = (EditText) findViewById(R.id.editText);
        RxTextView.textChanges(editText)
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        tx.setText(Integer.toString(charSequence.length()));
                    }
                });

    }

    private  void helloWorld(){

        Observable<String> observable = Observable.create(
                         new Observable.OnSubscribe<String>(){
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("1 hi\n");
                        subscriber.onNext("2 Rx is cool\n");
                        subscriber.onCompleted();
                    }
        });

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            String ss = "";
            @Override
            public void onNext(String s) {
                ss +=s;
                tx.setText(ss);
//                Log.i(TAG,s);
            }
        };
        observable.subscribe(subscriber);

    }

    private  void helloWordSimple1(){

        Observable<String> observable = Observable.just("hi simpe1", "hi simple2")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        Action1<String> action = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i("SampleTest",s);
                tx.setText(s);
            }
        };
        observable.subscribe(action);

        Action1<String> action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i("subscribe 2",s);
                tx.setText(s);
            }
        };
        observable.subscribe(action1);


//        Action1<String> action1 = s -> tx.setText(s);

    }



}
