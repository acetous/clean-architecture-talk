package de.makerhub.application;

import de.makerhub.persistence.Account;
import de.makerhub.persistence.Model;
import de.makerhub.persistence.ModelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;
    private final LoginService loginService;

    public byte[] getModelPrintData(UUID uuid) {
        Model model = getModel(uuid);
        countPrint(model);

        Account currentAccount = loginService.getCurrentUser();
        currentAccount.getPrintedModels().add(model);

        return model.getStlData();
    }

    private static void countPrint(Model model) {
        model.setPrintCount(model.getPrintCount() + 1);
    }

    public Model getModel(UUID uuid) {
        return modelRepository.findById(uuid).orElseThrow(ResouceNotFoundException::new);
    }
}
