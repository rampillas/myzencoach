package com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders;

import com.unir.grupo2.myzeancoach.data.MCustomize.RemaindersDataRepository;
import com.unir.grupo2.myzeancoach.data.MCustomize.RemaindersRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.model.RemainderItem;
import com.unir.grupo2.myzeancoach.domain.model.RemaindersListPojo;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by andres on 23/03/2017.
 */

public class UpdateListUseCase extends UseCase {
    private String token;

    public UpdateListUseCase(String token) {
        this.token = token;
    }

    public UpdateListUseCase() {

    }

    @Override
    protected Observable buildUseCaseObservable() {
        RemaindersRepository repo = RemaindersDataRepository.getInstance();
        return repo.allRemainders(token).map(new Func1<RemaindersListPojo, List<RemainderItem>>() {
            @Override
            public List<RemainderItem> call(RemaindersListPojo remaindersListPojo) {

                List<RemainderItem> remainderItemList = new ArrayList<RemainderItem>();

                for(int i = 0; i < remaindersListPojo.getCount(); i++){
                    remainderItemList.add(remaindersListPojo.getRemainderItems().get(i));
                }
                return remainderItemList;
            }
        });
    }
}
