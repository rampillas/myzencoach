package com.unir.grupo2.myzeancoach.data.MCustomize;

import com.unir.grupo2.myzeancoach.domain.model.StressCoachResponsePojo;
import com.unir.grupo2.myzeancoach.domain.model.StressQuestionsListPojo;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by andres on 28/03/2017.
 */

public interface StressRepository {
    Observable<Void> addQuestions(RequestBody body,String token);
    Observable<StressQuestionsListPojo> getStressQuestions(String url,String token);
    Observable<Void> setAnswers(String username,RequestBody body,String token);
    Observable<StressCoachResponsePojo> getResponseCoach(String token);
}
