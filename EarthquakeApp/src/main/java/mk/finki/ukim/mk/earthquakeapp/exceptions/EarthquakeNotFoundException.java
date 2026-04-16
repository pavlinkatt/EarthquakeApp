package mk.finki.ukim.mk.earthquakeapp.exceptions;

public class EarthquakeNotFoundException extends RuntimeException {
    public EarthquakeNotFoundException(Long id) {
        super("Earthquake with id " + id + " not found");
    }
}
