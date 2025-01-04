# Drehbuch Übung "Clean Architecture"

# Setup

Vorbedingungen

* IntelliJ
* JDK 21 (bspw. Eclipse Temurin)

Projekt erstellen

* IntelliJ -> Neues Gradle-Projekt
* Advanced -> groupID: `de.makerhub`

Build anpassen

* `app`-Ordner anlegen
* `build.gradle` und `src` verschieben
* `settings.gradle`: `include(":app")`
* `build.gradle` ersetzen mit Inhalt `tr01`

## Projektstruktur

Klasse anlegen: `de.makerhub.App`

* Annotation `@SpringBootApplication`
* Inhalt `main`-Methode

```java
public static void main(String[] args) {
    SpringApplication.run(App.class, args);
}
```

* `application.properties` Inhalt `tr05`

Packages anlegen unter `de.makerhub`

* `domain`
* `application.port.in`
* `application.port.out`
* `adapter.in`
* `adapter.out`

# Domain

```java
public record Model( // tr02
```

```java
public record Account( // tr03
```

```java
public record Collection( // tr04
```

# Use Case "View Model"

* Neues Interface `de.makerhub.application.port.in.ViewModelUseCase`
* Methode

```java
Model loadModel(UUID modelUuid);
```

* Neue Klasse `de.makerhub.adapter.in.web.ModelController`
* Annotationen `@RequiredArgsConstructor
@RestController
@Transactional`
* Methode `tr07`

```java

@GetMapping("/model/{uuid}")
public ModelJson getModel(@PathVariable UUID uuid) {
    Model model = viewModelUseCase.loadModel(uuid);
    return new ModelJson(model.id(), model.name(), model.description(), model.stlData(), model.printCount());
}
```

* Record `ModelJson` mit `tr06`
* Interface `de.makerhub.application.port.out.LoadModelPort` mit `tr08`
* Package `de.makerhub.adapter.out.persistence`
    * Klasse `ModelEntity` mit `tr09`
    * Interface `ModelRepository` mit `tr10`
    * Klasse `ModelPersistenceService` (package-private)
        * Annotationen `@Service @RequiredArgsConstructor`
        * `implements LoadModelPort`
        * Methode `loadByUuid` von Hand
        * Klasse `ModelMapper` mit `tr11`
* Klasse `de.makerhub.application.ModelService` (package-private) von Hand
    * Annotationen `@Service @RequiredArgsConstructor` -> Kompromiss
    * `implements ViewModelUseCase`
    * Versuch `ModelRepository` importieren klappt nicht, daher `LoadModelPort`

Test anlegen

* Klasse `de.makerhub.adapter.in.web.ModelControllerTest` mit `tr12`
* Klasse `SaveModelPort` mit `tr13`
* `ModelPersistenceService` erweitern um `SaveModelPort`

## Use Case "Print Model"

Anforderungen

* return STL
* Model.printCount++
* Account.printedModels.add(model)

Lösung

* Interface `de.makerhub.application.port.in.PrintModelUseCase` mit `tr14`
* `ModelController` neue Methode `tr15`
* `ModelService` erweitern um `PrintModelUseCase`
    * Methode in `tr16`

```java

@Override
public byte[] printModel(UUID modelUuid) {
    Model model = loadModelPort.loadByUuid(modelUuid);

    Model updatedModel = new Model(
            model.id(),
            model.name(),
            model.description(),
            model.stlData(),
            model.printCount() + 1
    );

    updateModelPort.update(updatedModel);

    Account currentUser = currentUserPort.getCurrentUser();
    Set<Model> printedModels = currentUser.printedModels();
    printedModels.add(updatedModel);
    saveAccountPort.update(new Account(
            currentUser.id(),
            currentUser.username(),
            printedModels
    ));

    return model.stlData();
}
```

* Interface `de.makerhub.application.port.out.SaveAccountPort` in `tr17`
* Interface `de.makerhub.application.port.out.CurrentUserPort` in `tr18`
* Interface `de.makerhub.application.port.out.LoadAccountPort` in `tr20`
* Test `ModelControllerTest` aktualisieren mit `tr19`
* Cherry-Pick Persistence-Adapter

# Diskussion

* persistence-Package strukturierern -> Sichtbarkeiten?
* Lösung mit mehreren Gradle-Projekten