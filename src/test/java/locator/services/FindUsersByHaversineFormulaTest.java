package locator.services;

import java.util.HashMap;
import java.util.List;

import generated.rest.user.UserServiceApi;
import generated.rest.user.model.UserDto;
import locator.configuration.CityCoordinateProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static locator.fixtures.UserDtoFixture.aDefaultAllUsersDtoResponse;
import static locator.fixtures.UserDtoFixture.aDefaultUserDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class FindUsersByHaversineFormulaTest {

  @Mock
  private UserServiceApi userServiceApi;

  private CityCoordinateProperties cityCoordinates;

  private FindUsersByHaversineFormula findUsersByHaversineFormula;

  private static final String LONDON = "london";

  private HashMap<String, Double> latitude;

  @BeforeEach
  void setup() {
    latitude = new HashMap<>();
    latitude.put(LONDON, 51.5076261);
    HashMap<String, Double> longitude = new HashMap<>();
    longitude.put(LONDON, -0.1279106);

    cityCoordinates = new CityCoordinateProperties();
    cityCoordinates.setLatitude(latitude);
    cityCoordinates.setLongitude(longitude);
    findUsersByHaversineFormula = new FindUsersByHaversineFormula(userServiceApi, cityCoordinates);
    lenient().when(userServiceApi.getUsers()).thenReturn(aDefaultAllUsersDtoResponse());
  }

  @Test
  void when_city_coordinates_are_valid_userServiceApi_is_called() {
    List<UserDto> users = findUsersByHaversineFormula.execute(LONDON, 50);
    assertThat(users.size()).isEqualTo(2);
  }

  @Test
  void if_city_is_capitalized_it_is_converted_to_lowercase() {
    List<UserDto> users = findUsersByHaversineFormula.execute("London", 50);
    assertThat(users.size()).isEqualTo(2);
  }

  @Test
  void when_city_coordinates_are_invalid_userServiceApi_is_not_called() {
    latitude.put(LONDON, null);
    List<UserDto> users = findUsersByHaversineFormula.execute(LONDON, 50);
    assertThat(users).isEmpty();
    verify(userServiceApi, never()).getUsers();
  }

  @Test
  void when_user_coordinates_are_invalid_User_is_not_returned() {
    UserDto user = aDefaultUserDto();
    user.setLatitude(null);
    List<UserDto> userDtos = aDefaultAllUsersDtoResponse();
    userDtos.add(user);
    lenient().when(userServiceApi.getUsers()).thenReturn(userDtos);

    List<UserDto> users = findUsersByHaversineFormula.execute(LONDON, 50);
    assertThat(users.size()).isEqualTo(2);
  }
}
