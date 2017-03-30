package com.unir.grupo2.myzeancoach.domain.MCooperativeSol;

import com.unir.grupo2.myzeancoach.data.MCooperativeSol.CooperativeDataRepository;
import com.unir.grupo2.myzeancoach.data.MCooperativeSol.CooperativeRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.domain.model.DilemmaListPojo;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Cesar on 21/03/2017.
 */

public class DilemmasUseCase extends UseCase {

    private String userName;
    private String token;
    private RequestBody body;

    public DilemmasUseCase(String userName, String token, RequestBody body) {
        this.userName = userName;
        this.token = token;
        this.body = body;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        CooperativeRepository repo = CooperativeDataRepository.getInstance();
        return repo.allDilemmas(userName,token,body).map(new Func1<DilemmaListPojo, List<Dilemma>>() {
            @Override
            public List<Dilemma> call(DilemmaListPojo dilemmaListPojo) {

                List<Dilemma> dilemmas = new ArrayList<Dilemma>();

                for (int i = 0; i < dilemmaListPojo.getDilemmas().size(); i++){
                    dilemmas.add(dilemmaListPojo.getDilemmas().get(i));
                }
                return dilemmas;
            }
        });
    }
}
