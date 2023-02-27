package task.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import task.annotation.WithMockCustomUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MainController.class)
@WithMockCustomUser
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnIndexView() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/index"))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getModelAndView().getViewName();

        assertEquals(result, "/index");
    }

    @Test
    public void shouldReturnReceiveView() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/receive"))
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
