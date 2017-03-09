package com.unir.grupo2.myzeancoach.domain.MEssentialInfo;

import com.unir.grupo2.myzeancoach.data.MEssentialInfo.VideosDataRepository;
import com.unir.grupo2.myzeancoach.data.MEssentialInfo.VideosRepository;
import com.unir.grupo2.myzeancoach.data.pojo.VideoListPojo;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.utils.ModeMapper;
import com.unir.grupo2.myzeancoach.ui.model.VideoUI;

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
        return repo.videos(token).map(new Func1<VideoListPojo, List<VideoUI>>() {
            @Override
            public List<VideoUI> call(VideoListPojo videoListPojo) {

                List<VideoUI> videoList = new ArrayList<VideoUI>();

                for(int i = 0; i < videoListPojo.getCount(); i++){
                    videoList.add(ModeMapper.videoConverter(videoListPojo.getResults().get(i)));
                }
                return videoList;
            }
        });
    }

}