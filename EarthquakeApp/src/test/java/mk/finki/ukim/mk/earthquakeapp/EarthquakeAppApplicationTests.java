package mk.finki.ukim.mk.earthquakeapp;

import mk.finki.ukim.mk.earthquakeapp.exceptions.EarthquakeNotFoundException;
import mk.finki.ukim.mk.earthquakeapp.model.Earthquake;
import mk.finki.ukim.mk.earthquakeapp.repository.EarthquakeRepository;
import mk.finki.ukim.mk.earthquakeapp.service.EarthquakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EarthquakeAppApplicationTests {

    @Autowired
    private EarthquakeService earthquakeService;

    @Autowired
    private EarthquakeRepository earthquakeRepository;

    @MockitoBean
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        earthquakeRepository.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testGetAllEarthquakes_success() {
        Earthquake eq1 = new Earthquake();
        eq1.setMagnitude(3.5);
        eq1.setMagType("ml");
        eq1.setPlace("Alaska");
        eq1.setTitle("M 3.5 - Alaska");
        eq1.setTimeOfEvent(Instant.now());

        Earthquake eq2 = new Earthquake();
        eq2.setMagnitude(2.5);
        eq2.setMagType("md");
        eq2.setPlace("California");
        eq2.setTitle("M 2.5 - California");
        eq2.setTimeOfEvent(Instant.now());

        earthquakeRepository.saveAll(List.of(eq1, eq2));

        List<Earthquake> result = earthquakeService.getAllEarthquakes();
        assertEquals(2, result.size());
    }

    @Test
    void testDeleteById_success() {
        Earthquake eq = new Earthquake();
        eq.setMagnitude(3.0);
        eq.setMagType("ml");
        eq.setPlace("Japan");
        eq.setTitle("M 3.0 - Japan");
        eq.setTimeOfEvent(Instant.now());

        Earthquake saved = earthquakeRepository.save(eq);
        earthquakeService.deleteById(saved.getId());

        assertFalse(earthquakeRepository.existsById(saved.getId()));
    }

    @Test
    void testDeleteById_notFound() {
        assertThrows(EarthquakeNotFoundException.class, () -> {
            earthquakeService.deleteById(999L);
        });
    }

    @Test
    void testGetEarthquakesAfter_success() {
        Instant now = Instant.now();

        Earthquake old = new Earthquake();
        old.setMagnitude(2.5);
        old.setMagType("ml");
        old.setPlace("Italy");
        old.setTitle("M 2.5 - Italy");
        old.setTimeOfEvent(now.minusSeconds(3600));

        Earthquake recent = new Earthquake();
        recent.setMagnitude(3.0);
        recent.setMagType("ml");
        recent.setPlace("Macedonia");
        recent.setTitle("M 3.0 - Macedonia");
        recent.setTimeOfEvent(now.plusSeconds(100));

        earthquakeRepository.saveAll(List.of(old, recent));

        List<Earthquake> result = earthquakeService.getEarthquakesAfter(now);
        assertEquals(1, result.size());
        assertEquals("Macedonia", result.get(0).getPlace());
    }
}