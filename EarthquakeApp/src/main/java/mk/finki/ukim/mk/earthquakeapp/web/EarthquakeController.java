package mk.finki.ukim.mk.earthquakeapp.web;

import mk.finki.ukim.mk.earthquakeapp.model.Earthquake;
import mk.finki.ukim.mk.earthquakeapp.service.EarthquakeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/earthquakes")
@CrossOrigin(origins = "http://localhost:3000")
public class EarthquakeController {

    private final EarthquakeService earthquakeService;

    public EarthquakeController(EarthquakeService earthquakeService) {
        this.earthquakeService = earthquakeService;
    }

    @PostMapping("/fetch")
    public ResponseEntity<List<Earthquake>> fetchAndStore() {
        List<Earthquake> earthquakes = earthquakeService.fetchAndStore();
        return ResponseEntity.ok(earthquakes);
    }

    @GetMapping
    public ResponseEntity<List<Earthquake>> getAllEarthquakes() {
        List<Earthquake> earthquakes = earthquakeService.getAllEarthquakes();
        return ResponseEntity.ok(earthquakes);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Earthquake>> getAfterTime(@RequestParam String after) {
        Instant time = Instant.parse(after);
        return ResponseEntity.ok(earthquakeService.getEarthquakesAfter(time));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        earthquakeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}