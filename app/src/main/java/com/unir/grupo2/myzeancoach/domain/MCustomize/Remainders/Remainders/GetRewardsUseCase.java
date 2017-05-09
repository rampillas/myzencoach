package com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders;

import com.unir.grupo2.myzeancoach.data.MCustomize.RemaindersDataRepository;
import com.unir.grupo2.myzeancoach.data.MCustomize.RemaindersRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.model.RewardsItem;
import com.unir.grupo2.myzeancoach.domain.model.RewardsListPojo;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by andres on 25/03/2017.
 */

public class GetRewardsUseCase extends UseCase {
    String token;

    public GetRewardsUseCase(String token) {
        this.token = token;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        RemaindersRepository repo = RemaindersDataRepository.getInstance();
        return repo.getRewards(token).map(new Func1<RewardsListPojo, List<RewardsItem>>() {
            @Override
            public List<RewardsItem> call(RewardsListPojo rewardsListPojo) {

                List<RewardsItem> rewardsItemList = new ArrayList<RewardsItem>();

                for (int i = 0; i < rewardsListPojo.getResults().size(); i++) {
                    rewardsItemList.add(rewardsListPojo.getResults().get(i));
                }
                return rewardsItemList;
            }
        });
    }
}
