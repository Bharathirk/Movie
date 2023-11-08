# Movie
Description:
This application is users are able to search for movies, see details for a given movie, favorite that movie, see all favorited movies. 

Api references:
https://developers.themoviedb.org/3/getting-started/introduction

Following things used in this project:

## Architecture

This project uses the Model-View-ViewModel (MVVM) architecture pattern. The app is divided into layers: the presentation layer (Activity/Fragment), the ViewModel layer, and the data layer (repository and data source). The ViewModel communicates with the data layer through a repository and provides data to the presentation layer through LiveData objects.

## Network

This project uses Retrofit to handle network requests. The API service is defined in the ApiService interface, and the API responses are mapped to Kotlin data classes using Gson.

## Local Database

This project uses Realm to store data locally. Realm is a mobile database that provides object-oriented data persistence, and data model is defined by the RealmObject classes, and the data access is managed by the Realm database instance.

## Event-driven Tasks

This project uses Kotlin Coroutines for asynchronous and event-driven programming. Coroutines are used for network requests, database operations, and other long-running tasks.
