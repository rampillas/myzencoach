package com.unir.grupo2.myzeancoach.domain.MEssentialInfo;

import com.unir.grupo2.myzeancoach.data.MEssentialInfo.EssentialDataRepository;
import com.unir.grupo2.myzeancoach.data.MEssentialInfo.EssentialRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;


/**
 * Created by Cesar on 14/03/2017.
 */

public class UpdateTestUseCase extends UseCase {

    private String token;
    private RequestBody body;

    public UpdateTestUseCase(String token, RequestBody body) {
        this.token = token;
        this.body = body;
    }

    @Override
    protected Observable<Void> buildUseCaseObservable() {
        EssentialRepository repo = EssentialDataRepository.getInstance();
        return repo.updateTest(token, body).first();
    }
}

