package com.br.vitrineescritores.api;

import com.br.vitrineescritores.bean.Book;
import com.br.vitrineescritores.service.ServiceFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rlima on 25/04/18.
 */

public class BookAPI {
    public static final String TAG = BookAPI.class.getCanonicalName().toUpperCase();
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static ListenerBook listenerBook;

    public static void listBooksOfAuthor(ListenerBook listener) {
        BookAPI.listenerBook = listener;
//        factory.getRetrofit().create()
    }

    public interface ListenerBook extends Serializable {
        void callOnSuccess(List<Book> list);

        void callOnFailure(int returnCod, String msg);
    }
}
