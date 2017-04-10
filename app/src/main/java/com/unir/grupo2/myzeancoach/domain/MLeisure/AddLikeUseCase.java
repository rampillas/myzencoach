package com.unir.grupo2.myzeancoach.domain.MLeisure;

import com.unir.grupo2.myzeancoach.data.MLeisure.LeisureDataRepository;
import com.unir.grupo2.myzeancoach.data.MLeisure.LeisureRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.model.Event;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;

import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Cesar on 15/03/2017.
 */

public class AddLikeUseCase extends UseCase {

    private String username;
    private String token;
    private RequestBody body;

    public AddLikeUseCase(String username, String token, RequestBody body) {
        this.username = username;
        this.token = token;
        this.body = body;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        LeisureRepository repo = LeisureDataRepository.getInstance();
        return repo.addLike(username, token, body).map(new Func1<Event, Event>() {
            @Override
            public Event call(Event event) {

                event.setDate(Utils.dateFormat(event.getDate()));
                event.setUser(Utils.covertUserNameBackend(event.getUser()));

                return event;
            }
        });
    }
}
