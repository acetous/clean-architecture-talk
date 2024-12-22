package de.makerhub;

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

    private final ModelRepository modelRepository;
    private final CollectionRepository collectionRepository;
    private final LoginService loginService;

    @GetMapping("/model/{uuid}")
    public Model getModel(@PathVariable UUID uuid) {
        return modelRepository.findById(uuid).orElseThrow(ResouceNotFoundException::new);
    }

    @PostMapping("/model/{uuid}/print")
    public byte[] printModel(@PathVariable UUID uuid) {
        Model model = modelRepository.findById(uuid).orElseThrow(ResouceNotFoundException::new);
        model.setPrintCount(model.getPrintCount() + 1);

        Account currentAccount = loginService.getCurrentUser();
        currentAccount.getPrintedModels().add(model);

        return model.getStlData();
    }

    @PostMapping("/collection/{uuid}")
    public void addModelToCollection(@RequestBody UUID modelUuid, @PathVariable UUID uuid) {
        Model model = modelRepository.findById(modelUuid).orElseThrow(ResouceNotFoundException::new);
        Collection collection = collectionRepository.findById(uuid).orElseThrow(ResouceNotFoundException::new);

        collection.getModels().add(model);
    }
}
