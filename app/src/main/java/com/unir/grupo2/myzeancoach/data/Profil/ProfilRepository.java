package com.unir.grupo2.myzeancoach.data.Profil;

import com.unir.grupo2.myzeancoach.domain.model.EmoticonListPojo;

import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */


public interface ProfilRepository {
    Observable<EmoticonListPojo> emoticon();
    Observable<EmoticonListPojo> updateEmoticon();
}

