package com.br.vitrineescritores.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.vitrineescritores.R;

/**
 * Created by Ronan.lima on 24/04/2018.
 */

public class AuthorViewHolder extends RecyclerView.ViewHolder {
    private TextView fullName;
    private ImageView imgAuthor;
    private TextView quantBooks;

    public AuthorViewHolder(View itemView) {
        super(itemView);
        setFullName((TextView) itemView.findViewById(R.id.full_name));
        setImgAuthor((ImageView) itemView.findViewById(R.id.img_author));
        setQuantBooks((TextView) itemView.findViewById(R.id.label_qtd_books));
    }

    public TextView getFullName() {
        return fullName;
    }

    public void setFullName(TextView fullName) {
        this.fullName = fullName;
    }

    public ImageView getImgAuthor() {
        return imgAuthor;
    }

    public void setImgAuthor(ImageView imgAuthor) {
        this.imgAuthor = imgAuthor;
    }

    public TextView getQuantBooks() {
        return quantBooks;
    }

    public void setQuantBooks(TextView quantBooks) {
        this.quantBooks = quantBooks;
    }
}
