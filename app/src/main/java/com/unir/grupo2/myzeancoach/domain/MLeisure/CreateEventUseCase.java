package com.unir.grupo2.myzeancoach.domain.MLeisure;

import com.unir.grupo2.myzeancoach.data.MLeisure.LeisureDataRepository;
import com.unir.grupo2.myzeancoach.data.MLeisure.LeisureRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.model.Event;
import com.unir.grupo2.myzeancoach.domain.model.UserLike;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;

import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Cesar on 15/03/2017.
 */

public class CreateEventUseCase extends UseCase {

    private String userName;
    private String token;
    private RequestBody requestBody;

    public CreateEventUseCase(String userName, String token, RequestBody requestBody) {
        this.userName = userName;
        this.token = token;
        this.requestBody = requestBody;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        LeisureRepository repo = LeisureDataRepository.getInstance();
        return repo.createEvent(token, requestBody).map(new Func1<Event, Event>() {
            @Override
            public Event call(Event event) {

                event.setDate(Utils.dateFormat(event.getDate()));
                event.setUser(Utils.covertUserNameBackend(event.getUser()));

                UserLike userLike = new UserLike();
                userLike.setUser(userName);
                userLike.setEvent(event.getTitle());
                userLike.setIsLiked(false);

                event.setUserLike(userLike);
                return event;
            }
        });
    }
}
