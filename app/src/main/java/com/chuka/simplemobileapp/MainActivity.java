package com.chuka.simplemobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chuka.simplemobileapp.data.ApiInterface;
import com.chuka.simplemobileapp.data.ServiceGenerator;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.domain) EditText domain;
    @BindView(R.id.next) Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        nextBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = domain.getText().toString().trim();
                        if (url.length() == 0) {
                            Toast.makeText(MainActivity.this, "Enter a domain to continue", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Fetch the items
                        fetchItems(url);
                    }
                });
    }

    private void fetchItems(String url) {
        ApiInterface apiInterface = ServiceGenerator.getApiInterface(url);
        apiInterface.getList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "onSubscribe");
                    }
                })
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        // Launch list activity
                        Intent intent = new Intent(MainActivity.this, ItemsActivity.class);
                        startActivity(intent);
                    }
                });
    }
}
