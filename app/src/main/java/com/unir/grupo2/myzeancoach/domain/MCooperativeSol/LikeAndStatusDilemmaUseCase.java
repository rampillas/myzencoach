package com.unir.grupo2.myzeancoach.domain.MCooperativeSol;

import com.unir.grupo2.myzeancoach.data.MCooperativeSol.CooperativeDataRepository;
import com.unir.grupo2.myzeancoach.data.MCooperativeSol.CooperativeRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func2;

/**
 * Created by Cesar on 21/03/2017.
 */

public class LikeAndStatusDilemmaUseCase extends UseCase {

    private String userName;
    private String token;
    private RequestBody likeBody;
    private RequestBody statusBody;

    public LikeAndStatusDilemmaUseCase(String userName, String token, RequestBody likeBody,
                                       RequestBody statusBody) {
        this.userName = userName;
        this.token = token;
        this.likeBody = likeBody;
        this.statusBody = statusBody;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        CooperativeRepository repo = CooperativeDataRepository.getInstance();
        return Observable.combineLatest(repo.addLikeDilemma(userName,token,likeBody), repo.amendStatusDilemma(userName,token,statusBody),
                new Func2<Void, Void, Void>() {
                    @Override
                    public Void call(Void aVoid2, Void aVoid3) {
                        return null;
                    }
                });
    }
}
