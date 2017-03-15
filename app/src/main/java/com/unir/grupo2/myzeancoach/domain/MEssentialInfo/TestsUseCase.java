package com.unir.grupo2.myzeancoach.domain.MEssentialInfo;

import com.unir.grupo2.myzeancoach.data.MEssentialInfo.EssentialDataRepository;
import com.unir.grupo2.myzeancoach.data.MEssentialInfo.EssentialRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.model.Test;
import com.unir.grupo2.myzeancoach.domain.model.TestListPojo;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Cesar on 10/03/2017.
 */

public class TestsUseCase extends UseCase{

    private String token;

    public TestsUseCase(String token) {
        this.token = token;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        EssentialRepository repo = EssentialDataRepository.getInstance();
        return repo.tests(token).map(new Func1<TestListPojo, List<Test>>() {
            @Override
            public List<Test> call(TestListPojo testListPojo) {

                List<Test> testUIList = new ArrayList<Test>();

                for(int i = 0; i < testListPojo.getCount(); i++){
                    testUIList.add(testListPojo.getResults().get(i));
                }
                return testUIList;
            }
        });
    }
}
