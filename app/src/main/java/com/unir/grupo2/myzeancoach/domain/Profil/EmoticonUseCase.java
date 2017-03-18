package com.unir.grupo2.myzeancoach.domain.Profil;

import com.unir.grupo2.myzeancoach.data.Profil.ProfilDataRepository;
import com.unir.grupo2.myzeancoach.data.Profil.ProfilRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.model.Emoticon;
import com.unir.grupo2.myzeancoach.domain.model.EmoticonListPojo;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Cesar on 15/03/2017.
 */

public class EmoticonUseCase extends UseCase {

    public EmoticonUseCase() {
    }

    @Override
    protected Observable buildUseCaseObservable() {
        ProfilRepository repo = ProfilDataRepository.getInstance();
        return repo.emoticon().map(new Func1<EmoticonListPojo, Emoticon>() {
            @Override
            public Emoticon call(EmoticonListPojo emoticonListPojo) {

                if (emoticonListPojo.getCount() > 0){
                    return emoticonListPojo.getEmoticons().get(0);
                }else{
                    return null;
                }
            }
        });
    }
}
