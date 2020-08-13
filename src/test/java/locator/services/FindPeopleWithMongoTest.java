package locator.services;

import java.util.HashMap;

import locator.configuration.CityCoordinateProperties;
import locator.model.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class FindPeopleWithMongoTest {

  @Mock
  private PersonRepository personRepository;

  private CityCoordinateProperties cityCoordinates;

  private FindPeopleWithMongo findPeopleWithMongo;

  private HashMap<String, Double> latitude;
  private HashMap<String, Double> longitude;
  private Circle circle;

  private static final Double EARTH_RADIUS = 3963.2;

  private static final String LONDON = "london";

  @BeforeEach
  void setup() {
    circle = new Circle(new Point(-0.1279106, 51.5076261), 50 / EARTH_RADIUS);
    latitude = new HashMap<>();
    latitude.put(LONDON, 51.5076261);
    longitude = new HashMap<>();
    longitude.put(LONDON, -0.1279106);

    cityCoordinates = new CityCoordinateProperties();
    cityCoordinates.setLatitude(latitude);
    cityCoordinates.setLongitude(longitude);
    findPeopleWithMongo = new FindPeopleWithMongo(personRepository, cityCoordinates);
  }

  @Test
  void when_coordinates_are_valid_repository_is_queried() {
    findPeopleWithMongo.execute(LONDON, 50);
    verify(personRepository, times(1)).findPeopleWithinACircle(circle);
  }

  @Test
  void if_city_is_capitalized_it_is_converted_to_lowercase() {
    findPeopleWithMongo.execute("London", 50);
    verify(personRepository, times(1)).findPeopleWithinACircle(circle);
  }

  @Test
  void when_coordinates_are_Invalid_repository_is_not_queried() {
    latitude.put(LONDON, null);
    findPeopleWithMongo.execute(LONDON, 50);
    verify(personRepository, never()).findPeopleWithinACircle(circle);
  }


}
