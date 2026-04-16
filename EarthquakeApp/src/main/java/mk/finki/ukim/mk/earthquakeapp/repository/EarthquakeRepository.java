package mk.finki.ukim.mk.earthquakeapp.repository;

import mk.finki.ukim.mk.earthquakeapp.model.Earthquake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface EarthquakeRepository extends JpaRepository<Earthquake, Long> {
    List<Earthquake> findByTimeOfEventAfter(Instant time);
}
