package com.unir.grupo2.myzeancoach.domain.MLeisure;

import com.unir.grupo2.myzeancoach.data.MLeisure.LeisureDataRepository;
import com.unir.grupo2.myzeancoach.data.MLeisure.LeisureRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.model.Interest;
import com.unir.grupo2.myzeancoach.domain.model.InterestListPojo;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Cesar on 15/03/2017.
 */

public class GetInterestsUseCase extends UseCase {

    private String userName;
    String token;

    public GetInterestsUseCase(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        LeisureRepository repo = LeisureDataRepository.getInstance();
        return repo.interests(userName, token).map(new Func1<InterestListPojo, ArrayList<Interest>>() {
            @Override
            public ArrayList<Interest> call(InterestListPojo interestListPojo) {
                ArrayList<Interest> interests =  new ArrayList<>();

                for (int i = 0; i < interestListPojo.getInterests().size(); i++){
                    Interest interest = interestListPojo.getInterests().get(i);
                    interest.setUser(Utils.covertUserNameBackend(interest.getUser()));
                    interests.add(interest);
                }
                return interests;
            }
        });
    }
}
