package elegant.children.catchculture.dto.GeoCode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeoCodeDTO {
    private Result[] results;

    @Getter
    @Setter
    public static class Result {
        @JsonProperty("formatted_address")
        private String address;
        private Geometry geometry;

        @Getter
        @Setter
        public class Geometry {
            private Location location;

            @Getter
            @Setter
            public class Location {
                private double lat;
                private double lng;
            }
        }
    }
}
