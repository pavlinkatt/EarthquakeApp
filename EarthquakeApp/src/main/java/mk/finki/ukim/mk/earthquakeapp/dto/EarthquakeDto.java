package mk.finki.ukim.mk.earthquakeapp.dto;

import lombok.Data;

import java.util.List;


@Data
public class EarthquakeDto {
    private String type;
    private List<Feature> features;

    @Data
    public static class Feature{
        private String type;
        private Properties properties;
    }

    @Data
    public static class Properties{
        private Double mag;
        private String magType;
        private String place;
        private String title;
        private Long time;
    }
}
