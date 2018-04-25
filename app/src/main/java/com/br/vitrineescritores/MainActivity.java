package com.br.vitrineescritores;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.br.vitrineescritores.api.AuthorAPI;
import com.br.vitrineescritores.bean.Author;
import com.br.vitrineescritores.view.AuthorView;

import java.util.List;

/**
 * Created by Ronan.lima on 24/04/2018.
 */

public class MainActivity extends Activity implements AuthorAPI.ListenerAuthor {
    public static final int PAGE_SIZE = 8;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ProgressDialog progressDialog;
    private boolean isLastPage = false;
    private int page = 1;

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!progressDialog.isShowing() && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                    AuthorAPI.listAuthors(MainActivity.this, ++page, PAGE_SIZE);
                    progressDialog.show();
                    isLastPage = true;
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayShowTitleEnabled(false);
        initProgressDialog();
        recyclerView = (RecyclerView) findViewById(R.id.recyler);
        AuthorAPI.listAuthors(this, page, PAGE_SIZE);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getResources().getString(R.string.text_carregando));
        progressDialog.show();
    }

    @Override
    public void callOnSuccess(List<Author> list) {
        recyclerView.setAdapter(new AuthorView(this, list));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, View.SCROLL_AXIS_HORIZONTAL));
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        stopLoading();
        isLastPage = false;
    }

    private void stopLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void callOnFailure(int returnCod, String msg) {
        stopLoading();
        Toast.makeText(this, "Não carregou nada", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sort) {
            Toast.makeText(this, "Clicou no sort", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
