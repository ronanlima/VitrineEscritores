package com.br.vitrineescritores.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.br.vitrineescritores.MainActivity;
import com.br.vitrineescritores.R;
import com.br.vitrineescritores.api.AuthorAPI;
import com.br.vitrineescritores.bean.Author;
import com.br.vitrineescritores.view.AuthorView;

import java.util.List;

/**
 * Created by rlima on 25/04/18.
 */

public class ListAuthorFragment extends Fragment implements AuthorAPI.ListenerAuthor, AuthorAPI.ListenerBookOfAuthor {
    public static final String TAG = ListAuthorFragment.class.getCanonicalName().toUpperCase();
    public static final int PAGE_SIZE = 10;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private boolean isLastPage = false;
    private int page = 1;
    private List<Author> authorList;

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

            if (!((MainActivity) getActivity()).getProgressDialog().isShowing() && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                    AuthorAPI.listAuthors(ListAuthorFragment.this, ListAuthorFragment.this, ++page, PAGE_SIZE);
                    ((MainActivity) getActivity()).getProgressDialog().show();
                    isLastPage = true;
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_author, container, false);
        AuthorAPI.listAuthors(this, this, page, PAGE_SIZE);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyler);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), View.SCROLL_AXIS_HORIZONTAL));
    }

    @Override
    public void callOnSuccess(List<Author> list) {
        initListAuthor(list);
        ((MainActivity) getActivity()).stopLoading();
        isLastPage = false;
    }

    private void initListAuthor(List<Author> listAPI) {
        if (getAuthorList() == null || getAuthorList().isEmpty()) {
            setAuthorList(listAPI);
            recyclerView.setAdapter(new AuthorView(getActivity(), getAuthorList()));
            recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        } else {
            getAuthorList().addAll(listAPI);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void callOnSucces(Author author) {
        int position = getPositionOnListFromAuthor(author);
        if (position != -1) {
            recyclerView.getAdapter().notifyItemChanged(position);
        }
    }

    private int getPositionOnListFromAuthor(Author aut) {
        int aux = 0;
        for (Author author : getAuthorList()) {
            if (author.getId() == aut.getId()) {
                author.setBooks(aut.getBooks());
                return aux;
            }
            aux++;
        }
        return -1;
    }

    @Override
    public void callOnFailure(int returnCod, String msg) {
        ((MainActivity) getActivity()).stopLoading();
        Toast.makeText(getActivity(), "Não carregou nada", Toast.LENGTH_SHORT).show();
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }
}
