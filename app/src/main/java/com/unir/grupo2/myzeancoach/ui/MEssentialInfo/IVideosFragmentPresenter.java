package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.content.Context;

/**
 * Created by Cesar on 05/03/2017.
 */

public interface IVideosFragmentPresenter {

    void setView(VideosFragment view);
    void setContext(Context context);

    void create();

    void destroy();

    void checkChanged(boolean isChecked);
    void onErrorLayoutClick();
}
