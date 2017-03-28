package com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Stress;

import com.unir.grupo2.myzeancoach.data.MCustomize.StressDataRepository;
import com.unir.grupo2.myzeancoach.data.MCustomize.StressRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.model.StressCoachResponse;
import com.unir.grupo2.myzeancoach.domain.model.StressCoachResponsePojo;
import com.unir.grupo2.myzeancoach.domain.model.StressQuestion;
import com.unir.grupo2.myzeancoach.domain.model.StressQuestionsListPojo;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by andres on 28/03/2017.
 */

public class UpdateQuestionsListUseCase extends UseCase {
    String token;

    public UpdateQuestionsListUseCase(String token) {
        this.token = token;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        StressRepository repo = StressDataRepository.getInstance();
        return repo.getStressQuestions(token).map(new Func1<StressQuestionsListPojo, List<StressQuestion>>() {
            @Override
            public List<StressQuestion> call(StressQuestionsListPojo stressCoachResponsePojo) {

                List<StressQuestion> stressQuestionList = new ArrayList<>();

                for(int i = 0; i < stressCoachResponsePojo.getCount(); i++){
                    stressQuestionList.add(stressCoachResponsePojo.getResults().get(i));
                }
                return stressQuestionList;
            }
        });
    }
}
