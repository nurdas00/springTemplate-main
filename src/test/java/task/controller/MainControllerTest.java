package task.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import task.annotation.WithMockCustomUser;
import task.entity.Transaction;
import task.entity.User;
import task.service.TransactionService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MainController.class)
@WithMockCustomUser
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void shouldReturnIndexView() throws Exception {
        when(transactionService.getUserAsReceiverTransactions(any())).thenReturn(Collections.emptyList());

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/index"))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getModelAndView().getViewName();

        assertEquals(result, "/index");
    }

    @Test
    public void shouldReturnReceiveView() throws Exception {

        User receiver = new User();
        receiver.setId(1L);
        receiver.setName("tom");
        receiver.setLastname("reddle");

        User sender = new User();
        sender.setId(2L);
        sender.setName("harry");
        sender.setLastname("potter");

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setReceiver(receiver);
        transaction.setSender(sender);
        transaction.setAmount(100L);

        when(transactionService.getTransactionById(any())).thenReturn(transaction);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/receive/1"))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getModelAndView().getViewName();

        assertEquals(result, "/receiveForm");
    }

    @Test
    public void shouldReturnSendView() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/send"))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getModelAndView().getViewName();

        assertEquals(result, "/sendForm");
    }
}
