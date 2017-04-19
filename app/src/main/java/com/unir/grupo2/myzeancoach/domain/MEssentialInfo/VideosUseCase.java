package com.unir.grupo2.myzeancoach.domain.MEssentialInfo;

import com.unir.grupo2.myzeancoach.data.MEssentialInfo.EssentialDataRepository;
import com.unir.grupo2.myzeancoach.data.MEssentialInfo.EssentialRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import rx.Observable;

public class VideosUseCase extends UseCase {

    private String url;
    private String token;

    public VideosUseCase(String url, String token) {
        this.url = url;
        this.token = token;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        EssentialRepository repo = EssentialDataRepository.getInstance();
        return repo.videos(url, token).first();
    }

}