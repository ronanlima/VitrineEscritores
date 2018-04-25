package com.br.vitrineescritores;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.br.vitrineescritores.api.AuthorAPI;
import com.br.vitrineescritores.bean.Author;
import com.br.vitrineescritores.view.AuthorView;

import java.util.List;

/**
 * Created by Ronan.lima on 24/04/2018.
 */

public class MainActivity extends Activity implements AuthorAPI.ListenerAuthor {
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayShowTitleEnabled(false);
        initProgressDialog();
        recyclerView = (RecyclerView) findViewById(R.id.recyler);
        AuthorAPI.listAuthors(this);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Carregando");
        progressDialog.show();
    }

    @Override
    public void callOnSuccess(List<Author> list) {
        recyclerView.setAdapter(new AuthorView(this, list));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        stopLoading();
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
