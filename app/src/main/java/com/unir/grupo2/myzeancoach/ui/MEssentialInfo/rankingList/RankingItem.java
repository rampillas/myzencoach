package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.rankingList;

/**
 * Created by Cesar on 23/02/2017.
 */

public class RankingItem {

    private String nick;
    private int score;

    public RankingItem(String nick, int score){
        this.nick = nick;
        this.score = score;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
