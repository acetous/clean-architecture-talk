package de.makerhub;

import de.makerhub.application.port.out.CurrentUserPort;
import de.makerhub.application.port.out.LoadAccountPort;
import de.makerhub.application.port.out.SaveAccountPort;
import de.makerhub.application.port.out.SaveModelPort;
import de.makerhub.domain.Account;
import de.makerhub.domain.Model;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ModelControllerTest {

    @Autowired
    private TestRestTemplate client;
    
    @Autowired
    private SaveModelPort updateModelPort;

    @Autowired
    private SaveAccountPort updateAccountPort;

    @Autowired
    private LoadAccountPort loadAccountPort;

    @MockitoBean
    private CurrentUserPort currentUserPortMock;

    @Test
    public void getModelData() {
        Model savedModel = createModel();
        ResponseEntity<Model> response = client.getForEntity("/model/{uuid}", Model.class, savedModel.uuid());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(savedModel);
    }

    @Test
    public void printReturnsStl() {
        Account loggedInUser = createAccout();
        when(currentUserPortMock.getCurrentUser()).thenReturn(loggedInUser);
        Model savedModel = createModel();
        ResponseEntity<byte[]> response = client.getForEntity("/model/{uuid}/print",  byte[].class, savedModel.uuid());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(savedModel.stlData());
    }

    @Test
    public void printIncreasesPrintCount() {
        Account loggedInUser = createAccout();
        when(currentUserPortMock.getCurrentUser()).thenReturn(loggedInUser);
        Model savedModel = createModel();

        client.getForEntity("/model/{uuid}/print",  byte[].class, savedModel.uuid());
        client.getForEntity("/model/{uuid}/print",  byte[].class, savedModel.uuid());
        client.getForEntity("/model/{uuid}/print",  byte[].class, savedModel.uuid());

        ResponseEntity<Model> response = client.getForEntity("/model/{uuid}", Model.class, savedModel.uuid());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().printCount()).isEqualTo(3);
    }

    @Test
    public void printAddsToUsersPrintedModels() {
        Account loggedInUser = createAccout();
        when(currentUserPortMock.getCurrentUser()).thenReturn(loggedInUser);
        Model savedModel = createModel();
        ResponseEntity<byte[]> response = client.getForEntity("/model/{uuid}/print",  byte[].class, savedModel.uuid());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loadAccountPort.getById(loggedInUser.id()).printedModels())
                .extracting(Model::uuid)
                .contains(savedModel.uuid());
    }

    private Account createAccout() {
        Account account = new Account(
                UUID.randomUUID(),
                "username",
                new HashSet<>()
        );
        updateAccountPort.create(account);
        return account;
    }

    private Model createModel() {
        Model model = new Model(
                UUID.randomUUID(),
                "Raspberry Pi 4B Armor Case Wall Mount",
                "A wall mount for the Raspberry Pi 4B Armor case.",
                "stldata".getBytes(StandardCharsets.UTF_8),
                0L
        );
        updateModelPort.create(model);
        return model;
    }
}
