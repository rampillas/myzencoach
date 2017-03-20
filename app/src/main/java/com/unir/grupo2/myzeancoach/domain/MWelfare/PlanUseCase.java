package com.unir.grupo2.myzeancoach.domain.MWelfare;

import com.unir.grupo2.myzeancoach.data.Profil.ProfilDataRepository;
import com.unir.grupo2.myzeancoach.data.Profil.ProfilRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 18/03/2017.
 */

public class PlanUseCase extends UseCase {

    private RequestBody body;

    public PlanUseCase(RequestBody body) {
        this.body = body;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        ProfilRepository repo = ProfilDataRepository.getInstance();
        return repo.updateEmoticon(body).first();
    }
}
