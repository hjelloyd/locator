package locator.rest;

import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Resource;

import generated.rest.locator.model.PersonDto;
import generated.rest.user.UserServiceApi;
import locator.IntegrationTestBase;
import locator.model.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static locator.fixtures.PersonFixture.aDefaultListOfPeople;
import static locator.fixtures.UserDtoFixture.aDefaultAllUsersDtoResponse;
import static locator.fixtures.UserDtoFixture.aDefaultCityUserDtoResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class LocatorApiTest extends IntegrationTestBase {

  @Resource
  private TestRestTemplate testRestTemplate;

  @Resource
  private PersonRepository personRepository;

  @MockBean
  private UserServiceApi userServiceApi;

  @BeforeEach
  void setup() {
    when(userServiceApi.getUsersByCity("London"))
        .thenReturn(aDefaultCityUserDtoResponse());
  }

  @Nested
  class Mongo_Method_Tests {

    @BeforeEach
    void setup() {
      personRepository.deleteAll();
      personRepository.saveAll(aDefaultListOfPeople());
    }

    @Test
    void when_location_is_given_people_are_returned() {
      ResponseEntity<PersonDto[]> response = testRestTemplate
          .getForEntity("/mongo/people/city/London", PersonDto[].class);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(response.getBody()).isNotEmpty();

      List<PersonDto> people = List.of(response.getBody());
      assertThat(people.size()).isEqualTo(3);

      assertThat(people.stream().map(PersonDto::getLocation)
          .filter(Predicate.isEqual(PersonDto.LocationEnum.CITY))
          .count()).isEqualTo(2);
      assertThat(people.stream().map(PersonDto::getLocation)
          .filter(Predicate.isEqual(PersonDto.LocationEnum.SURROUNDING_AREA))
          .count()).isEqualTo(1);
    }

    @Test
    void when_distance_is_specified_people_are_returned() {
      ResponseEntity<PersonDto[]> response = testRestTemplate
          .getForEntity("/mongo/people/city/London?distance=10", PersonDto[].class);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(response.getBody()).isNotEmpty();

      List<PersonDto> people = List.of(response.getBody());
      assertThat(people.size()).isEqualTo(2);

      assertThat(people.stream().map(PersonDto::getLocation)
          .filter(Predicate.isEqual(PersonDto.LocationEnum.CITY))
          .count()).isEqualTo(2);
      assertThat(people.stream().map(PersonDto::getLocation)
          .filter(Predicate.isEqual(PersonDto.LocationEnum.SURROUNDING_AREA))
          .count()).isEqualTo(0);
    }

    @Test
    void when_location_is_invalid_empty_list_is_returned() {
      ResponseEntity<PersonDto[]> response = testRestTemplate
          .getForEntity("/mongo/people/city/invalid", PersonDto[].class);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(response.getBody()).isEmpty();
    }
  }

  @Nested
  class Haversine_Method_Tests {

    @BeforeEach
    void setup() {
      when(userServiceApi.getUsers())
          .thenReturn(aDefaultAllUsersDtoResponse());
    }

    @Test
    void when_location_is_given_people_are_returned() {
      ResponseEntity<PersonDto[]> response = testRestTemplate
          .getForEntity("/haversine/people/city/London", PersonDto[].class);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(response.getBody()).isNotEmpty();

      List<PersonDto> people = List.of(response.getBody());
      assertThat(people.size()).isEqualTo(3);

      assertThat(people.stream().map(PersonDto::getLocation)
          .filter(Predicate.isEqual(PersonDto.LocationEnum.CITY))
          .count()).isEqualTo(2);
      assertThat(people.stream().map(PersonDto::getLocation)
          .filter(Predicate.isEqual(PersonDto.LocationEnum.SURROUNDING_AREA))
          .count()).isEqualTo(1);
    }

    @Test
    void when_distance_is_specified_people_are_returned() {
      ResponseEntity<PersonDto[]> response = testRestTemplate
          .getForEntity("/haversine/people/city/London?distance=10", PersonDto[].class);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(response.getBody()).isNotEmpty();

      List<PersonDto> people = List.of(response.getBody());
      assertThat(people.size()).isEqualTo(2);

      assertThat(people.stream().map(PersonDto::getLocation)
          .filter(Predicate.isEqual(PersonDto.LocationEnum.CITY))
          .count()).isEqualTo(2);
      assertThat(people.stream().map(PersonDto::getLocation)
          .filter(Predicate.isEqual(PersonDto.LocationEnum.SURROUNDING_AREA))
          .count()).isEqualTo(0);
    }

    @Test
    void when_location_is_invalid_empty_list_is_returned() {
      ResponseEntity<PersonDto[]> response = testRestTemplate
          .getForEntity("/haversine/people/city/invalid", PersonDto[].class);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(response.getBody()).isEmpty();
    }
  }
}
