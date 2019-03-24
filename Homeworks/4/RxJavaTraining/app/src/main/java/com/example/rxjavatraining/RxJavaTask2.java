package com.example.rxjavatraining;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class RxJavaTask2 {

    /**
     * TODO:
     * <p>
     * Method takes observable of strings as a parameter
     * <p>
     * Supply all elements until you meet word END in the stream (END word should be excluded)
     * After that remove all repeated values from the stream
     */
    @NonNull
    public static Observable<String> task2(@NonNull Observable<String> observable) {
        String stopWord = "end";
        return observable
                .takeWhile(s -> !s.equalsIgnoreCase(stopWord))
                .distinct();
    }

}