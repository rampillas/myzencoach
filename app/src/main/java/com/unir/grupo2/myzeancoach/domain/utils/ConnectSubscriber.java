package com.unir.grupo2.myzeancoach.domain.utils;

import rx.Subscriber;

/**
 * Created by Cesar on 23/04/2017.
 */

public final class ConnectSubscriber extends Subscriber<Void> {
    //3 callbacks

    //Show the listView
    @Override
    public void onCompleted() {

    }

    //Show the error
    @Override
    public void onError(Throwable e) {
    }

    //Update listview datas
    @Override
    public void onNext(Void aVoid) {
    }
}