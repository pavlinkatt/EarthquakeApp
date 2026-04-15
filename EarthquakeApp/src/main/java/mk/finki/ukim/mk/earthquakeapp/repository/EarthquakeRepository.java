package mk.finki.ukim.mk.earthquakeapp.repository;

import mk.finki.ukim.mk.earthquakeapp.model.Earthquake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarthquakeRepository extends JpaRepository<Earthquake, Long> {
}
