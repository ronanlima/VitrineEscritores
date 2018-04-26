package com.br.vitrineescritores.service;

import com.br.vitrineescritores.bean.Author;
import com.br.vitrineescritores.bean.Book;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rlima on 24/04/18.
 */

public interface AuthorService {

    @GET("authors")
    Call<List<Author>> listAuthors();

    @GET("authors")
    Call<List<Author>> listPaginationAuthor(@Query("filter") JsonObject params);

    @GET("authors/{id}/books")
    Call<List<Book>> listBooksOfAuthor(@Path("id") Integer id);
}
