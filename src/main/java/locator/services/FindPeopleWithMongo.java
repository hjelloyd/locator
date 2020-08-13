package locator.services;

import java.util.Collections;
import java.util.List;

import locator.configuration.CityCoordinateProperties;
import locator.model.Person;
import locator.model.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindPeopleWithMongo {

  private final PersonRepository personRepository;

  private final CityCoordinateProperties cityCoordinates;

  private static final Double EARTH_RADIUS = 3963.2;

  public List<Person> execute(String city, Integer distance) {
    if (cityCoordinates.getLongitude().get(city.toLowerCase()) == null ||
        cityCoordinates.getLatitude().get(city.toLowerCase()) == null) {
      log.warn("{} has not been configured with coordinates", city);
      return Collections.emptyList();
    }
    log.info("Creating radius of {} miles arround {}", distance, city);
    log.info("{} longitude {}", city, cityCoordinates.getLongitude().get(city.toLowerCase()));
    log.info("{} latitude {}", city, cityCoordinates.getLatitude().get(city.toLowerCase()));
    Circle circle = new Circle(
        new Point(cityCoordinates.getLongitude().get(city.toLowerCase())
            , cityCoordinates.getLatitude().get(city.toLowerCase())),
        distance.doubleValue() / EARTH_RADIUS);
    return personRepository.findPeopleWithinACircle(circle);
  }
}
