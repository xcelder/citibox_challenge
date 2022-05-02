# Android technical test

This android app has been development as a technical test. The application has the following features:

- A main screen with a characters list of Rick And Morty. Each item shows the image and name of the character.
- A Meetup screen where the app should find the perfect match for the selected character to have a meetup following this specifications:

    - Characters can only meet another characters if they are from his same location.
    - So that they have enough anecdotes to tell while they get drunk, priority will be given to the characters who have shared the filming set the most times.
    - In the event that two possible matches have shared the same number of chapters with the selected character, those who have known each other for the longest time will be preferred.
    - If they have participated in the same number of chapters, and they met on the same day, priority will be given to the candidate who has not seen the selected character for the longest time.
    - In case there is more than one candidate that meets all the criteria, it will be sorted by ID.

## Architecture
The architecture is based on Clean Architecture and each layer is implemented by a Gradle module.

1. **Domain layer**
   Here we define the domain models and errors.

2. **Data layer**
   Where the data sources and repositories are defined.

3. **Framework layer**
   Unlike the other 2 modules, this is an Android library module in which we implement de Data layer using the libraries needed for connecting with network, DDBB...
   This keeps private by exposing the Data layer objects using dependency injection (Koin).

4. **Usecase layer**
   Here we use the Data layer (through the Framework layer) to implement the usecases needed for the app.

5. **Presentation layer**
   Implemented using MVVM-MVRX taking advantage of the Airbnb/Mavericks library.

> **Unit tests and Instrumented tests included**
