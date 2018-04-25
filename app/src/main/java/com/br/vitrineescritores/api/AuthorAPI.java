package com.br.vitrineescritores.api;

import android.util.Log;

import com.br.vitrineescritores.bean.Author;
import com.br.vitrineescritores.bean.Book;
import com.br.vitrineescritores.service.AuthorService;
import com.br.vitrineescritores.service.ServiceFactory;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rlima on 24/04/18.
 */

public class AuthorAPI {
    public static final String TAG = AuthorAPI.class.getCanonicalName().toUpperCase();

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static ListenerAuthor listener;
    private static ListenerBookOfAuthor listenerBookOfAuthor;

    public static void listAuthors(final ListenerAuthor listener, int page, int limit) {
        AuthorAPI.listener = listener;

        if (page != 0 || limit != 0) {
            JsonObject json = new JsonObject();
            json.addProperty("limit", limit);
            json.addProperty("page", page);

            factory.getRetrofit().create(AuthorService.class).listPaginationAuthor(json).enqueue(new Callback<List<Author>>() {
                @Override
                public void onResponse(Call<List<Author>> call, Response<List<Author>> response) {
                    callReturnToView(response, AuthorAPI.listener);
                }

                @Override
                public void onFailure(Call<List<Author>> call, Throwable t) {
                    Log.e(TAG, call.toString());
                    listener.callOnFailure(500, t.getMessage());
                }
            });
        } else {
            factory.getRetrofit().create(AuthorService.class).listAuthors().enqueue(new Callback<List<Author>>() {
                @Override
                public void onResponse(Call<List<Author>> call, Response<List<Author>> response) {
                    callReturnToView(response, AuthorAPI.listener);
                }

                @Override
                public void onFailure(Call<List<Author>> call, Throwable t) {
                    Log.e(TAG, call.toString());
                    listener.callOnFailure(500, t.getMessage());
                }
            });
        }

    }

    private static void callReturnToView(Response<List<Author>> response, ListenerAuthor listener) {
        if (response.isSuccessful() && response.body() != null) {
            listener.callOnSuccess(response.body());
        } else {
            try {
                listener.callOnFailure(response.code(), response.errorBody().string());
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public static void listBookOfAuthor(final List<Author> listAuthors, ListenerBookOfAuthor listenerBook) {
        AuthorAPI.listenerBookOfAuthor = listenerBook;

        for (final Author author : listAuthors) {
            factory.getRetrofit().create(AuthorService.class).listBooksOfAuthor(author.getId()).enqueue(new Callback<List<Book>>() {
                @Override
                public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        author.setBooks(response.body());
                        listenerBookOfAuthor.callOnSucces(listAuthors);
                        // como a chamada é assincrona, não tem como executar o laço inteiro para
                        // só depois enviar o resultado disso. Nesse caso, enviar cada um separado e
                        // mandar atualizar o recyclerView?
                        // Ou criar outro método/classe, que liste todos os livros e no processamento
                        // do dispositivo, vincular livro com autor?
                    }
                }

                @Override
                public void onFailure(Call<List<Book>> call, Throwable t) {
                    Log.e(TAG, call.toString());
                    listenerBookOfAuthor.callOnFailure(500, t.getMessage());
                }
            });
        }
    }

    public interface ListenerAuthor extends Serializable {
        void callOnSuccess(List<Author> list);
        void callOnFailure(int returnCod, String msg);
    }

    public interface ListenerBookOfAuthor extends Serializable {
        void callOnSucces(List<Author> list);
        void callOnFailure(int returnCode, String msg);
    }
}
