package com.unir.grupo2.myzeancoach.domain.Profil;

import com.unir.grupo2.myzeancoach.data.Profil.ProfilDataRepository;
import com.unir.grupo2.myzeancoach.data.Profil.ProfilRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 18/03/2017.
 */

public class UpdateEmoticonUseCase extends UseCase {

    private String token;
    private RequestBody body;

    public UpdateEmoticonUseCase(String token, RequestBody body) {
        this.token = token;
        this.body = body;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        ProfilRepository repo = ProfilDataRepository.getInstance();
        return repo.updateEmoticon(token, body).first();
    }
}
