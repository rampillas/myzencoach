package com.unir.grupo2.myzeancoach.domain.MLeisure;

import com.unir.grupo2.myzeancoach.data.MLeisure.LeisureDataRepository;
import com.unir.grupo2.myzeancoach.data.MLeisure.LeisureRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.model.Event;
import com.unir.grupo2.myzeancoach.domain.model.EventListPojo;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Cesar on 15/03/2017.
 */

public class GetEventsUseCase extends UseCase {

    private String username;
    private String token;

    public GetEventsUseCase(String username, String token) {
        this.username = username;
        this.token = token;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        LeisureRepository repo = LeisureDataRepository.getInstance();
        return repo.events(username, token).map(new Func1<EventListPojo, ArrayList<Event>>() {
            @Override
            public ArrayList<Event> call(EventListPojo eventListPojo) {

                ArrayList<Event> events = new ArrayList<>();

                for(int i = 0; i < eventListPojo.getEvents().size(); i++){
                    Event event = eventListPojo.getEvents().get(i);
                    event.setDate(Utils.dateFormat(eventListPojo.getEvents().get(i).getDate()));
                    event.setUser(Utils.covertUserNameBackend(eventListPojo.getEvents().get(i).getUser()));
                    if (event.getComments() != null && !event.getComments().isEmpty()){
                        for (int j = 0; j < event.getComments().size(); j++){
                            event.getComments().get(j).setDate(Utils.dateFormat(event.getComments().get(j).getDate()));
                        }
                    }
                    events.add(event);
                }
                return events;
            }
        });
    }
}
