package de.makerhub.application;

import de.makerhub.persistence.Account;
import de.makerhub.persistence.Model;
import de.makerhub.persistence.ModelRepository;
import de.makerhub.persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;
    private final LoginService loginService;
    private final UserRepository userRepository;

    public byte[] printModel(UUID uuid) {
        Model model = getModel(uuid);
        countPrint(model);

        Account currentAccount = loginService.getCurrentUser();
        if (currentAccount.getPrintedModels() == null) {
            currentAccount.setPrintedModels(new ArrayList<>());
        }
        currentAccount.getPrintedModels().add(model);
        userRepository.save(currentAccount);

        return model.getStlData();
    }

    private void countPrint(Model model) {
        model.setPrintCount(model.getPrintCount() + 1);
        modelRepository.save(model);
    }

    public Model getModel(UUID uuid) {
        return modelRepository.findById(uuid).orElseThrow(ResouceNotFoundException::new);
    }
}
