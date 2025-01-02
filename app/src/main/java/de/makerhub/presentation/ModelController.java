package de.makerhub.presentation;

import de.makerhub.application.CollectionService;
import de.makerhub.application.ModelService;
import de.makerhub.persistence.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ModelController {

    private final ModelService modelService;
    private final CollectionService collectionService;

    @GetMapping("/model/{uuid}")
    public Model getModel(@PathVariable UUID uuid) {
        return modelService.getModel(uuid);
    }

    @GetMapping("/model/{uuid}/print")
    public byte[] printModel(@PathVariable UUID uuid) {
        return modelService.printModel(uuid);
    }

    @PostMapping("/collection/{uuid}")
    public void addModelToCollection(@RequestBody UUID modelUuid, @PathVariable UUID uuid) {
        collectionService.addModelToCollection(modelUuid, uuid);
    }
}
