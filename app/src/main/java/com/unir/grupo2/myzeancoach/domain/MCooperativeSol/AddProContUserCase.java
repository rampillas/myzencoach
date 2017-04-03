package com.unir.grupo2.myzeancoach.domain.MCooperativeSol;

import com.unir.grupo2.myzeancoach.data.MCooperativeSol.CooperativeDataRepository;
import com.unir.grupo2.myzeancoach.data.MCooperativeSol.CooperativeRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func2;

/**
 * Created by Cesar on 03/04/2017.
 */

public class AddProContUserCase extends UseCase{

    private String userName;
    private String token;
    private RequestBody prosBody;
    private RequestBody consBody;

    public AddProContUserCase(String userName, String token, RequestBody prosBody, RequestBody consBody) {
        this.userName = userName;
        this.token = token;
        this.prosBody = prosBody;
        this.consBody = consBody;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        CooperativeRepository repo = CooperativeDataRepository.getInstance();
        return Observable.combineLatest(repo.addPros(userName,token,prosBody), repo.addCons(userName,token,consBody),
                new Func2<Void, Void, Void>() {
                    @Override
                    public Void call(Void aVoid2, Void aVoid3) {
                        return null;
                    }
                });
    }
}
