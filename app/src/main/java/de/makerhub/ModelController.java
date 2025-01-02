package de.makerhub;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ModelController {

    private final ModelRepository modelRepository;
    private final CollectionRepository collectionRepository;
    private final LoginService loginService;
    private final UserRepository userRepository;

    @GetMapping("/model/{uuid}")
    public Model getModel(@PathVariable UUID uuid) {
        return modelRepository.findById(uuid).orElseThrow(ResouceNotFoundException::new);
    }

    @GetMapping("/model/{uuid}/print")
    public byte[] printModel(@PathVariable UUID uuid) {
        Model model = modelRepository.findById(uuid).orElseThrow(ResouceNotFoundException::new);
        model.setPrintCount(model.getPrintCount() + 1);

        Account currentAccount = loginService.getCurrentUser();
        if (null == currentAccount.getPrintedModels()) {
            currentAccount.setPrintedModels(new ArrayList<>());
        }
        currentAccount.getPrintedModels().add(model);
        userRepository.save(currentAccount);

        return model.getStlData();
    }

    @PostMapping("/collection/{uuid}")
    public void addModelToCollection(@RequestBody UUID modelUuid, @PathVariable UUID uuid) {
        Model model = modelRepository.findById(modelUuid).orElseThrow(ResouceNotFoundException::new);
        Collection collection = collectionRepository.findById(uuid).orElseThrow(ResouceNotFoundException::new);

        collection.getModels().add(model);
    }
}
