package com.unir.grupo2.myzeancoach.domain.MCooperativeSol;

import com.unir.grupo2.myzeancoach.data.MCooperativeSol.CooperativeDataRepository;
import com.unir.grupo2.myzeancoach.data.MCooperativeSol.CooperativeRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func3;

/**
 * Created by Cesar on 21/03/2017.
 */

public class AddCommentUseCase extends UseCase {

    private String userName;
    private String token;
    private RequestBody commentBody;
    private RequestBody prosBody;
    private RequestBody consBody;

    public AddCommentUseCase(String userName, String token, RequestBody commentBody,
                             RequestBody prosBody, RequestBody consBody) {
        this.userName = userName;
        this.token = token;
        this.commentBody = commentBody;
        this.prosBody = prosBody;
        this.consBody = consBody;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        CooperativeRepository repo = CooperativeDataRepository.getInstance();
        return Observable.combineLatest(repo.addComment(userName,token,commentBody),
        repo.addPros(userName,token,prosBody), repo.addCons(userName,token,commentBody),
                new Func3<Void, Void, Void, Void>() {
                    @Override
                    public Void call(Void aVoid, Void aVoid2, Void aVoid3) {
                        return null;
                    }
                });
    }
}
