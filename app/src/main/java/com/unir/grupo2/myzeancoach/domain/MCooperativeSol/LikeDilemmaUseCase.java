package com.unir.grupo2.myzeancoach.domain.MCooperativeSol;

import com.unir.grupo2.myzeancoach.data.MCooperativeSol.CooperativeDataRepository;
import com.unir.grupo2.myzeancoach.data.MCooperativeSol.CooperativeRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 21/03/2017.
 */

public class LikeDilemmaUseCase extends UseCase {

    private String userName;
    private String token;
    private RequestBody commentBody;

    public LikeDilemmaUseCase(String userName, String token, RequestBody commentBody) {
        this.userName = userName;
        this.token = token;
        this.commentBody = commentBody;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        CooperativeRepository repo = CooperativeDataRepository.getInstance();
        return repo.addLikeDilemma(userName,token,commentBody).first();
    }
}
