package com.unir.grupo2.myzeancoach.domain.MLeisure;

import com.unir.grupo2.myzeancoach.data.MLeisure.LeisureDataRepository;
import com.unir.grupo2.myzeancoach.data.MLeisure.LeisureRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 15/03/2017.
 */

public class CreateCommentUseCase extends UseCase {

    private String userName;
    private String token;
    private RequestBody requestBody;

    public CreateCommentUseCase(String userName, String token, RequestBody requestBody) {
        this.userName = userName;
        this.token = token;
        this.requestBody = requestBody;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        LeisureRepository repo = LeisureDataRepository.getInstance();
        return repo.createComment(userName, token, requestBody).first();
    }
}
