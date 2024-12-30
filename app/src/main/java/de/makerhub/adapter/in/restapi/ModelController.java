package de.makerhub.adapter.in.restapi;

import de.makerhub.domain.Model;
import de.makerhub.application.port.in.AddModelToCollectionUseCase;
import de.makerhub.application.port.in.PrintModelUseCase;
import de.makerhub.application.port.in.ViewModelUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
@Transactional
public class ModelController {

    private final PrintModelUseCase printModelUseCase;
    private final ViewModelUseCase viewModelUseCase;
    private final AddModelToCollectionUseCase addModelToCollectionUseCase;

    @GetMapping("/model/{uuid}")
    public ModelJson getModel(@PathVariable UUID uuid) {
        Model model = viewModelUseCase.loadModel(uuid);
        return new ModelJson(model.uuid(), model.name(), model.description(), model.printCount());
    }

    @PostMapping("/model/{uuid}/print")
    public byte[] printModel(@PathVariable UUID uuid) {
        return printModelUseCase.printModel(uuid);
    }

    @PostMapping("/collection/{collectionUuid}")
    public void addModelToCollection(@RequestBody UUID modelUuid, @PathVariable UUID collectionUuid) {
        addModelToCollectionUseCase.addModelToCollection(modelUuid, collectionUuid);
    }
}
