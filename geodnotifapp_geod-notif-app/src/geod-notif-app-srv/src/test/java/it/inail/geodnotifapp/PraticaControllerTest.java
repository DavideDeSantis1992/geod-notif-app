package it.inail.geodnotifapp;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//import com.fasterxml.jackson.databind.ObjectMapper;

import it.inail.geodnotifapp.dto.PraticaDto;
import it.inail.geodnotifapp.exceptions.DataNotFoundException;
import it.inail.geodnotifapp.services.PraticaService;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration
@WebAppConfiguration
public class PraticaControllerTest extends AbstractControllerTest {

    @MockBean
    private PraticaService praticaService;

    protected MockMvc mockMvc;

//    @Autowired
//    private ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    private static final String USER = ""; //inserire subject del token JWT

    private static final String HEAD_OFFICE = ""; //inserire sede del subject

    private static final String ROLE = ""; //inserire ruolo del subject

    @BeforeAll
    public void setUp() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

    }

    @AfterAll
    public void tearDown() {
        reset(praticaService);
    }

    @Test
    @WithMockAuthentication(subject = USER, headOffice = HEAD_OFFICE, role = ROLE)
    public void createPraticaTest() throws Exception {

        PraticaDto praticaDto = new PraticaDto();
        praticaDto.setName("Test");
        praticaDto.setDescription("Test Description");
        praticaDto.setAuthor("John Doe");

        RequestBuilder request = buildPostRequestBuilder("/pratiche/", praticaDto,
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertNotNull(mvcResult);
    }

    @Test
    @WithMockAuthentication(subject = USER, headOffice = HEAD_OFFICE, role = ROLE)
    public void findPraticaTest() throws Exception {

        final long praticaId = 10L;
        final String username = "John Doe";

        PraticaDto praticaDto = new PraticaDto();
        praticaDto.setId(praticaId);
        praticaDto.setName("Test");
        praticaDto.setDescription("Test Description");
        praticaDto.setAuthor("John Doe");

        when(praticaService.find(praticaId, username)).thenReturn(praticaDto);

        RequestBuilder request = buildGetRequestBuilder("/pratiche/"+ praticaId,
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test()
    @WithMockAuthentication(subject = USER, headOffice = HEAD_OFFICE, role = ROLE)
    public void findPraticaNotFoundExceptionTest() throws Exception {

        final long praticaId = 10L;
//        final String username = "John Doe";

        when(praticaService.find(anyLong(), anyString())).thenThrow(new DataNotFoundException("Pratica not found."));

        RequestBuilder request = buildGetRequestBuilder("/pratiche/"+ praticaId,
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    @WithMockAuthentication(subject = USER, headOffice = HEAD_OFFICE, role = ROLE)
    public void updatePraticaTest() throws Exception {

        final long praticaId = 10L;
        final String username = "John Doe";

        PraticaDto praticaDto = new PraticaDto();
        praticaDto.setId(praticaId);
        praticaDto.setName("Test");
        praticaDto.setDescription("Test Description");
        praticaDto.setAuthor("John Doe");

        when(praticaService.update(praticaId, praticaDto, username)).thenReturn(true);

        RequestBuilder request = buildPutRequestBuilder("/pratiche/"+ praticaId, praticaDto,
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }


    @Test
    @WithMockAuthentication(subject = USER, headOffice = HEAD_OFFICE, role = ROLE)
    public void deletePraticaTest() throws Exception {

        final long praticaId = 10L;
        final String username = "John Doe";


        doNothing().when(praticaService).delete(praticaId, username);

        RequestBuilder request = buildDeleteRequestBuilder("/pratiche/"+ praticaId);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }


}
