package com.unir.grupo2.myzeancoach.data.MEssentialInfo;

import com.unir.grupo2.myzeancoach.domain.model.TestListPojo;

import rx.Observable;

/**
 * Created by Cesar on 10/03/2017.
 */

public interface TestsRepository {
    Observable<TestListPojo> tests(String token);
}
