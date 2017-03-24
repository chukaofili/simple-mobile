package com.chuka.simplemobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chuka.simplemobileapp.data.ApiInterface;
import com.chuka.simplemobileapp.data.ItemResponse;
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
    @BindView(R.id.progress) ProgressBar progressBar;

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
                        } else if (!Patterns.WEB_URL.matcher(url).matches()) {
                            Toast.makeText(MainActivity.this, "Invalid url", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Doesnt match");
                            return;
                        } else {
                            Log.e(TAG, "Matches");
                        }

                        if (!url.startsWith("http://")) {
                            url = "http://" + url;
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
                        progressBar.setVisibility(View.VISIBLE);
                    }
                })
                .subscribe(new Subscriber<ItemResponse>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "Unable to retrieve data", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ItemResponse items) {
                        // Launch list activity
                        Intent intent = new Intent(MainActivity.this, ItemsActivity.class);
                        intent.putExtra(ItemsActivity.EXTRA_ITEMS, items);
                        startActivity(intent);
                    }
                });
    }
}
