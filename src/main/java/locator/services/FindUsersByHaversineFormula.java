package locator.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import generated.rest.user.UserServiceApi;
import generated.rest.user.model.UserDto;
import locator.configuration.CityCoordinateProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.lang.Math.toRadians;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindUsersByHaversineFormula {

  private final UserServiceApi userServiceApi;

  private final CityCoordinateProperties cityCoordinates;

  private static final Double AVERAGE_EARTH_RADIUS = 3958.8;

  public List<UserDto> execute(String city, Integer distance) {
    if (cityCoordinates.getLongitude().get(city.toLowerCase()) == null ||
        cityCoordinates.getLatitude().get(city.toLowerCase()) == null) {
      log.warn("{} has not been configured with coordinates", city);
      return Collections.emptyList();
    }

    log.info("Getting users from bpdts-test-app.herokuapp.com and filtering using Haversine Formula");
    return userServiceApi.getUsers().stream()
        .filter(user -> withinRadius(cityCoordinates.getLongitude().get(city.toLowerCase()),
            cityCoordinates.getLatitude().get(city.toLowerCase()), user, distance.doubleValue()))
        .collect(Collectors.toList());

  }

  private boolean withinRadius(double cityLong, double cityLat, UserDto user, double allowance) {
    if (user.getLatitude() == null || user.getLongitude() == null) {
      return false;
    }
    // Haversine formula
    double londiff = toRadians(cityLong) - toRadians(user.getLongitude().doubleValue());
    double latdiff = toRadians(cityLat) - toRadians(user.getLatitude().doubleValue());
    double distance = AVERAGE_EARTH_RADIUS * Math.asin(Math.sqrt(Math.pow(Math.sin(latdiff / 2), 2)
        + Math.cos(toRadians(user.getLatitude().doubleValue())) * Math.cos(cityLat)
        * Math.pow(Math.sin(londiff / 2), 2)));
    return distance <= allowance;
  }
}
