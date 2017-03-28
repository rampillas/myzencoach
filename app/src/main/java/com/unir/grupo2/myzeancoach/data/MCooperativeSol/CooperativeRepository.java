package com.unir.grupo2.myzeancoach.data.MCooperativeSol;

import com.unir.grupo2.myzeancoach.domain.model.DilemmaListPojo;

import okhttp3.RequestBody;
import rx.Observable;

public interface CooperativeRepository {

    Observable<DilemmaListPojo> allDilemmas(String userName, String token, RequestBody body);


}
