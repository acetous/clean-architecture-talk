package de.makerhub.adapter.in.restapi;

import de.makerhub.application.port.in.AddModelToCollectionUseCase;
import de.makerhub.application.port.in.PrintModelUseCase;
import de.makerhub.application.port.in.ViewModelUseCase;
import de.makerhub.domain.Model;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Transactional
public class ModelController {

    private final PrintModelUseCase printModelUseCase;
    private final ViewModelUseCase viewModelUseCase;
    private final AddModelToCollectionUseCase addModelToCollectionUseCase;

    @GetMapping("/model/{uuid}")
    public ModelJson getModel(@PathVariable UUID uuid) {
        Model model = viewModelUseCase.loadModel(uuid);
        return new ModelJson(model.uuid(), model.name(), model.description(), model.stlData(), model.printCount());
    }

    @GetMapping("/model/{uuid}/print")
    public byte[] printModel(@PathVariable UUID uuid) {
        return printModelUseCase.printModel(uuid);
    }

    @PostMapping("/collection/{collectionUuid}")
    public void addModelToCollection(@RequestBody UUID modelUuid, @PathVariable UUID collectionUuid) {
        addModelToCollectionUseCase.addModelToCollection(modelUuid, collectionUuid);
    }
}
