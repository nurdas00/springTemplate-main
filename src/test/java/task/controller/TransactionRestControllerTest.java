package task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;
import task.annotation.WithMockCustomUser;
import task.entity.Transaction;
import task.entity.User;
import task.service.TransactionService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionRestController.class)
@WithMockCustomUser
public class TransactionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private ObjectMapper mapper;
    private CsrfToken csrfToken;
    private static final String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    @BeforeEach
    public void setup() {
        mapper = new ObjectMapper();

        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
    }

    @Test
    public void shouldReturnConfirmationCode() throws Exception {
        doNothing().when(transactionService).createTransasction(any());

        Transaction transaction = new Transaction();
        transaction.setAmount(100L);
        transaction.setConfirmationCode("abc123");

        User sender = prepareUser();
        transaction.setSender(sender);

        String json = mapper.writeValueAsString(transaction);


        mockMvc.perform(post("/send")
                .content(json).contentType("application/json")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("abc123"))
                .andReturn();
    }

    @Test
    public void shouldReturnCompleteTransaction() throws Exception {
        doNothing().when(transactionService).commitTransaction(any());

        User user = prepareUser();

        String json = mapper.writeValueAsString(user);

        mockMvc.perform(post("/receive")
                        .content(json).contentType("application/json")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Transaction completed"))
                .andReturn();
    }

    private User prepareUser() {
        User user = new User();
        user.setName("timur");
        user.setLastname("batrudinov");

        return user;
    }
}
