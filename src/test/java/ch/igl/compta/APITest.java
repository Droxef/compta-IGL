package ch.igl.compta;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.igl.compta.controller.ComptaController;
import ch.igl.compta.service.api.ComptaService;

@WebMvcTest(controllers = ComptaController.class)
public class APITest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ComptaService comptaService;

    @Test
    public void testGetCompta() throws Exception {
        mockMvc.perform(get("/compta"))
            .andExpect(status().isOk());
//            .andExpect(jsonPath("$", Matchers.empty())));
    }
}
