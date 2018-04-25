package com.br.vitrineescritores.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.vitrineescritores.R;
import com.br.vitrineescritores.bean.Author;

import java.util.List;

/**
 * Created by Ronan.lima on 24/04/2018.
 */

public class AuthorView extends RecyclerView.Adapter<AuthorViewHolder> {
    private Context mContext;
    private List<Author> list;

    public AuthorView(Context mContext, List<Author> list) {
        setmContext(mContext);
        setList(list);
    }

    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(getmContext()).inflate(R.layout.card_view_author, parent, false);
        return new AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AuthorViewHolder holder, int position) {
        Author author = getList().get(position);
        holder.getFullName().setText(String.format("%s %s", author.getFirstName(), author.getLastName()));
        holder.getQuantBooks().setText(String.format(mContext.getResources().getString(R.string.obras_publicadas), 10));
    }

    @Override
    public int getItemCount() {
        return getList() != null ? getList().size() : 0;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List<Author> getList() {
        return list;
    }

    public void setList(List<Author> list) {
        this.list = list;
    }
}
