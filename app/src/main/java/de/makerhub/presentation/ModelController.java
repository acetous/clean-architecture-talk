package de.makerhub.presentation;

import de.makerhub.application.CollectionService;
import de.makerhub.application.ModelService;
import de.makerhub.persistence.Model;
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

    private final ModelService modelService;
    private final CollectionService collectionService;

    @GetMapping("/model/{uuid}")
    public Model getModel(@PathVariable UUID uuid) {
        return modelService.getModel(uuid);
    }

    @PostMapping("/model/{uuid}/print")
    public byte[] printModel(@PathVariable UUID uuid) {
        return modelService.getModelPrintData(uuid);
    }

    @PostMapping("/collection/{uuid}")
    public void addModelToCollection(@RequestBody UUID modelUuid, @PathVariable UUID uuid) {
        collectionService.addModelToCollection(modelUuid, uuid);
    }
}
