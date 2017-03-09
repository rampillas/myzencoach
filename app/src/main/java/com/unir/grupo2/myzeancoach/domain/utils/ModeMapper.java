package com.unir.grupo2.myzeancoach.domain.utils;

import com.unir.grupo2.myzeancoach.data.pojo.VideoPojo;
import com.unir.grupo2.myzeancoach.ui.model.VideoUI;

/**
 * Created by Cesar on 19/02/2017.
 */

public class ModeMapper {

    public static VideoUI videoConverter(VideoPojo apiModel){
        VideoUI result = new VideoUI();

        result.setTitle(apiModel.getName());
        result.setCoverUrl(apiModel.getPhotoUrl());
        result.setDate(apiModel.getDate());
        result.setUrlVideo(apiModel.getUrl());
        result.setSurvey(apiModel.getSurvey());
        result.setIsWatched(apiModel.getIsWatched());

        return result;
    }
}
