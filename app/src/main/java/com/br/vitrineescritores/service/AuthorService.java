package com.br.vitrineescritores.service;

import com.br.vitrineescritores.bean.Author;
import com.br.vitrineescritores.bean.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rlima on 24/04/18.
 */

public interface AuthorService {

    @GET("authors")
    Call<List<Author>> listAuthors();

    @GET("author/{id}")
    Call<List<Book>> listBooksOfAuthor(@Path("id") Integer id);
}
