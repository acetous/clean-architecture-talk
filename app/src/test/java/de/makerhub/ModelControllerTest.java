package de.makerhub;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ModelControllerTest {

    @Autowired
    private TestRestTemplate client;
    
    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private UserRepository userRepository;

    @MockitoBean
    private LoginService loginService;

    @Test
    public void getModelData() {
        Model savedModel = createModel();
        ResponseEntity<Model> response = client.getForEntity("/model/{uuid}", Model.class, savedModel.getUuid());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(savedModel);
    }

    @Test
    public void printReturnsStl() {
        Account loggedInUser = createAccout();
        when(loginService.getCurrentUser()).thenReturn(loggedInUser);
        Model savedModel = createModel();
        ResponseEntity<byte[]> response = client.getForEntity("/model/{uuid}/print",  byte[].class, savedModel.getUuid());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(savedModel.getStlData());
    }

    @Test
    public void printIncreasesPrintCount() {
        Account loggedInUser = createAccout();
        when(loginService.getCurrentUser()).thenReturn(loggedInUser);
        Model savedModel = createModel();

        client.getForEntity("/model/{uuid}/print",  byte[].class, savedModel.getUuid());
        client.getForEntity("/model/{uuid}/print",  byte[].class, savedModel.getUuid());
        client.getForEntity("/model/{uuid}/print",  byte[].class, savedModel.getUuid());

        ResponseEntity<Model> response = client.getForEntity("/model/{uuid}", Model.class, savedModel.getUuid());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getPrintCount()).isEqualTo(3);
    }

    @Test
    public void printAddsToUsersPrintedModels() {
        Account loggedInUser = createAccout();
        when(loginService.getCurrentUser()).thenReturn(loggedInUser);
        Model savedModel = createModel();
        ResponseEntity<byte[]> response = client.getForEntity("/model/{uuid}/print",  byte[].class, savedModel.getUuid());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userRepository.findByIdFetchPrintedModels(loggedInUser.getUuid()).getPrintedModels())
                .extracting(Model::getUuid)
                .contains(savedModel.getUuid());
    }

    private Account createAccout() {
        Account account = new Account();
        account.setUuid(UUID.randomUUID());
        account.setUsername("username");
        return userRepository.save(account);
    }

    private Model createModel() {
        Model model = new Model();
        model.setUuid(UUID.randomUUID());
        model.setName("Raspberry Pi 4B Armor Case Wall Mount");
        model.setDescription("A wall mount for the Raspberry Pi 4B Armor case.");
        model.setStlData(model.getUuid().toString().getBytes(StandardCharsets.UTF_8));
        return modelRepository.save(model);
    }
}
