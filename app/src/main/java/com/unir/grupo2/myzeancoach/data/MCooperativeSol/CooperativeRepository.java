package com.unir.grupo2.myzeancoach.data.MCooperativeSol;

import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.domain.model.DilemmaListPojo;

import okhttp3.RequestBody;
import rx.Observable;

public interface CooperativeRepository {

    Observable<DilemmaListPojo> allDilemmas(String userName, String token, RequestBody body);
    Observable<Dilemma> addDilemma(String token, RequestBody body);
    Observable<Void> amendDilemma(String userName, String token, RequestBody body);
    Observable<Void> addComment(String userName, String token, RequestBody body);
    Observable<Void> addPros(String userName, String token, RequestBody body);
    Observable<Void> addCons(String userName, String token, RequestBody body);
    Observable<Void> addLikeDilemma(String userName, String token, RequestBody body);
}
