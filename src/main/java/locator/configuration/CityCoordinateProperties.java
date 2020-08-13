package locator.configuration;

import java.util.Map;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("city.coordinates")
@Data
public class CityCoordinateProperties {
  private Map<String,Double> latitude;
  private Map<String,Double> longitude;
}
