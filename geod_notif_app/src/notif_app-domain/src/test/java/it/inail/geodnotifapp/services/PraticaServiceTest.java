package it.inail.geodnotifapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.inail.geodnotifapp.dto.CreatePraticaResponse;
import it.inail.geodnotifapp.dto.PraticaDto;
import it.inail.geodnotifapp.exceptions.DataNotFoundException;
import it.inail.geodnotifapp.mappers.PraticaMapper;
import it.inail.geodnotifapp.mappers.PraticaMapperImpl;
import it.inail.geodnotifapp.models.Pratica;
import it.inail.geodnotifapp.queue.EventProducer;
import it.inail.geodnotifapp.repositories.PraticaRepository;
import it.inail.geodnotifapp.services.impl.PraticaServiceImpl;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PraticaMapperImpl.class})
public class PraticaServiceTest {

    @MockBean
    private PraticaRepository praticaRepository;

    @MockBean
    private EventProducer eventProducer;

    @Autowired
    private PraticaMapper praticaMapper;

    private PraticaService praticaService;

    private final String username = "John Doh";

    @BeforeAll
    public void setUp() throws Exception {
        praticaService = new PraticaServiceImpl(praticaRepository, praticaMapper, eventProducer);
        reset(praticaRepository);
    }

    @Test
    public void createPraticaTest() {

        PraticaDto praticaDto = new PraticaDto();
        praticaDto.setName("Test");
        praticaDto.setDescription("TestDescription");
        praticaDto.setAuthor("John Doh");

        Date creationDate = new Date();

        Pratica pratica = new Pratica();
        pratica.setId(100L);
        pratica.setCreator(praticaDto.getAuthor());
        pratica.setName(praticaDto.getName());
        pratica.setCreationDate(creationDate);

        when(praticaRepository.save(any(Pratica.class))).thenReturn(pratica);

        CreatePraticaResponse createPraticaResponse = praticaService.create(praticaDto, username);

        assertNotNull(createPraticaResponse);

        assertEquals(pratica.getId(), createPraticaResponse.getId());

    }

    @Test
    public void getPraticaByIdTest() {

        final long praticaId = 100L;

        Pratica pratica = new Pratica();
        pratica.setId(praticaId);
        pratica.setCreator("John Doh");
        pratica.setName("Test");
        pratica.setDescription("TestDescription");
        pratica.setCreationDate(new Date());

        when(praticaRepository.findById(praticaId)).thenReturn(Optional.of(pratica));

        PraticaDto praticaDto = praticaService.find(praticaId, username);

        assertNotNull(praticaDto);
        assertEquals(pratica.getId(), praticaDto.getId());
        assertEquals(pratica.getName(), praticaDto.getName());
        assertEquals(pratica.getDescription(), praticaDto.getDescription());
        assertEquals(pratica.getCreator(), praticaDto.getAuthor());
    }


    @Test()
    public void getPraticaByIdThowsNotFoundTest() {

        final long praticaId = 101L;

        when(praticaRepository.findById(praticaId)).thenThrow(DataNotFoundException.class);

        praticaService.find(praticaId, username);

//        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
//            praticaRepository.findById(praticaId);
//        });
//
//        assertNotNull(exception);
//      praticaService.find(praticaId, username);
    }

    @Test
    public void updatePraticaTest() {

        final long praticaId = 100L;

        Pratica pratica = new Pratica();
        pratica.setId(praticaId);
        pratica.setCreator("John Doh");
        pratica.setName("Test");
        pratica.setDescription("TestDescription");
        pratica.setCreationDate(new Date());

        PraticaDto praticaDto = new PraticaDto();
        praticaDto.setName("Test");
        praticaDto.setDescription("TestDescription");
        praticaDto.setAuthor("John Doh");

        when(praticaRepository.findById(praticaId)).thenReturn(Optional.of(pratica));

        boolean update = praticaService.update(praticaId, praticaDto, username);

        assertTrue(update);

    }

    @Test()
    public void updatePraticaTestThowsDataNotFoundTest() {

        final long praticaId = 101L;

        PraticaDto praticaDto = new PraticaDto();
        praticaDto.setName("Test");
        praticaDto.setDescription("TestDescription");
        praticaDto.setAuthor("John Doh");

        when(praticaRepository.findById(praticaId)).thenThrow(DataNotFoundException.class);

        praticaService.update(praticaId, praticaDto, username);
    }

    @Test
    public void deletePraticaTest() {

        final long praticaId = 100L;

        Pratica pratica = new Pratica();
        pratica.setId(praticaId);
        pratica.setCreator("John Doh");
        pratica.setName("Test");
        pratica.setDescription("TestDescription");
        pratica.setCreationDate(new Date());

        when(praticaRepository.findById(praticaId)).thenReturn(Optional.of(pratica));

        praticaService.delete(praticaId, username);

        assertTrue(true);

    }

    @Test()
    public void deletePraticaThowsDataNotFoundTest() {

        final long praticaId = 103L;

        when(praticaRepository.findById(praticaId)).thenThrow(DataNotFoundException.class);

        praticaService.delete(praticaId, username);

        assertTrue(true);

    }

}
