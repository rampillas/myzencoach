package com.unir.grupo2.myzeancoach.domain.MEssentialInfo;

import com.unir.grupo2.myzeancoach.data.MEssentialInfo.VideosDataRepository;
import com.unir.grupo2.myzeancoach.data.MEssentialInfo.VideosRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.model.Video;
import com.unir.grupo2.myzeancoach.domain.model.VideoListPojo;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class VideosUseCase extends UseCase {

    private String token;

    public VideosUseCase(String token) {
        this.token = token;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        VideosRepository repo = VideosDataRepository.getInstance();
        return repo.videos(token).map(new Func1<VideoListPojo, List<Video>>() {
            @Override
            public List<Video> call(VideoListPojo videoListPojo) {

                List<Video> videoList = new ArrayList<Video>();

                for (int i = 0; i < videoListPojo.getCount(); i++) {
                    videoList.add(videoListPojo.getResults().get(i));
                }
                return videoList;
            }
        });
    }

}