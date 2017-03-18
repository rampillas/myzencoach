package com.unir.grupo2.myzeancoach.data.Profil;

import com.unir.grupo2.myzeancoach.domain.model.Emoticon;
import com.unir.grupo2.myzeancoach.domain.model.EmoticonListPojo;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */


public interface ProfilRepository {
    Observable<EmoticonListPojo> emoticon();
    Observable<Emoticon> updateEmoticon(RequestBody body);
}

