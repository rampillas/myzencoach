package com.unir.grupo2.myzeancoach.domain.MEssentialInfo;

import com.unir.grupo2.myzeancoach.data.MEssentialInfo.EssentialDataRepository;
import com.unir.grupo2.myzeancoach.data.MEssentialInfo.EssentialRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 14/03/2017.
 */

public class UpdateVideoUseCase extends UseCase{

    private String contentType;
    private String token;
    private RequestBody body;


    public UpdateVideoUseCase(String contentType, String token, RequestBody body) {
        this.contentType = contentType;
        this.token = token;
        this.body = body;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        EssentialRepository repo = EssentialDataRepository.getInstance();
        return repo.updateVideo(contentType, token, body).first();
    }
}
