package com.movies.watchme.data.realm.module;

import com.movies.watchme.data.realm.models.MovieModel;
import io.realm.annotations.RealmModule;

@RealmModule(classes = {
    MovieModel.class
}) public class AppRealmModule {
}
