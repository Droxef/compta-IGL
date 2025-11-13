package ch.igl.compta.web;

import static org.hamcrest.CoreMatchers.containsString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class webPersonneTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    private void testGetPersonnes() throws Exception {
        mockMvc.perform(get("/personne"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("personForm"))
            .andExpect(content().string(containsString("Liste des personnes")));
    }
}
