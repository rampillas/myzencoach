package com.unir.grupo2.myzeancoach.domain.Tracking;

import com.unir.grupo2.myzeancoach.data.Tracking.TrackingDataRepository;
import com.unir.grupo2.myzeancoach.data.Tracking.TrackingRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 15/03/2017.
 */

public class ConnectionUseCase extends UseCase {

    private String token;
    private RequestBody body;

    public ConnectionUseCase(String token, RequestBody body) {
        this.token = token;
        this.body = body;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        TrackingRepository repo = TrackingDataRepository.getInstance();
        return repo.connectionCount(token, body).first();
    }
}
