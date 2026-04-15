package mk.finki.ukim.mk.earthquakeapp.service;

import jakarta.transaction.Transactional;
import mk.finki.ukim.mk.earthquakeapp.dto.EarthquakeDto;
import mk.finki.ukim.mk.earthquakeapp.model.Earthquake;
import mk.finki.ukim.mk.earthquakeapp.repository.EarthquakeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EarthquakeService {
    private static final String USGS_API =
            "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";

    private final EarthquakeRepository earthquakeRepository;
    private final RestTemplate restTemplate;

    public EarthquakeService(EarthquakeRepository earthquakeRepository, RestTemplate restTemplate) {
        this.earthquakeRepository = earthquakeRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public List<Earthquake> fetchAndStore(){
        EarthquakeDto response = restTemplate.getForObject(USGS_API, EarthquakeDto.class);

        if(response == null || response.getFeatures() == null){
            throw new RuntimeException("Failed to fetch data from USGS API");
        }

        List<Earthquake> earthquakes = response.getFeatures().stream()
                .filter(f -> f.getProperties() != null)
                .filter(f -> f.getProperties().getMag() != null
                        && f.getProperties().getMag() > 2.0)
                .map(f -> {
                    EarthquakeDto.Properties p = f.getProperties();
                    Earthquake earthquake = new Earthquake();
                    earthquake.setMagnitude(p.getMag());
                    earthquake.setMagType(p.getMagType());
                    earthquake.setPlace(p.getPlace());
                    earthquake.setTitle(p.getTitle());
                    earthquake.setTimeOfEvent(Instant.ofEpochMilli(p.getTime()));
                    return earthquake;
                })
                .collect(Collectors.toList());

        earthquakeRepository.deleteAll();
        return earthquakeRepository.saveAll(earthquakes);
    }

    public List<Earthquake> getAllEarthquakes() {
        return earthquakeRepository.findAll();
    }
}
