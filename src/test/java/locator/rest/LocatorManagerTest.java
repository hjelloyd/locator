package locator.rest;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import generated.rest.locator.model.PersonDto;
import generated.rest.user.UserServiceApi;
import locator.services.FindPeopleWithMongo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static locator.fixtures.PersonFixture.aDefaultListOfPeople;
import static locator.fixtures.UserDtoFixture.aDefaultAllUsersDtoResponse;
import static locator.fixtures.UserDtoFixture.aDefaultCityUserDtoResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class LocatorManagerTest {

  @Mock
  private UserServiceApi userServiceApi;

  @Mock
  private FindPeopleWithMongo findPeopleWithMongo;

  private LocatorManager locatorManager;

  private static final String LONDON = "london";

  @BeforeEach
  void setup() {
    locatorManager = new LocatorManager(userServiceApi, findPeopleWithMongo);
  }

  @Test
  void when_a_lower_case_city_is_entered_it_is_capitalized() {
    when(userServiceApi.getUsersByCity("London")).thenReturn(aDefaultCityUserDtoResponse());
    lenient().when(userServiceApi.getUsersByCity(LONDON)).thenReturn(Collections.emptyList());
    when(findPeopleWithMongo.execute(any(), any())).thenReturn(Collections.emptyList());

    List<PersonDto> people = locatorManager.getPeopleUsingMongoPoint(LONDON, 50);
    assertThat(people.size()).isEqualTo(2);
    verify(userServiceApi, never()).getUsersByCity(LONDON);
  }

  @Test
  void when_duplicate_people_are_found_then_a_distinct_list_is_returned_and_CITY_is_prioritized() {
    when(userServiceApi.getUsersByCity("London")).thenReturn(aDefaultAllUsersDtoResponse());
    when(findPeopleWithMongo.execute(any(), any())).thenReturn(aDefaultListOfPeople());

    List<PersonDto> people = locatorManager.getPeopleUsingMongoPoint(LONDON, 50);
    assertThat(people.size()).isEqualTo(4);
    assertThat(people.stream().map(PersonDto::getLocation)).allMatch(Predicate.isEqual(
        PersonDto.LocationEnum.CITY));
  }

}
