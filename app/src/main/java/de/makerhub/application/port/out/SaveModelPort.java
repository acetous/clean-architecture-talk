package de.makerhub.application.port.out;

import de.makerhub.domain.Model;

public interface SaveModelPort {
    void create(Model model);
    void update(Model model);
}
