package locator.fixtures;

import java.util.List;

import locator.model.Person;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@NoArgsConstructor(access = AccessLevel.NONE)
public class PersonFixture {

  public static Person aDefaultPerson() {
    return Person.builder()
        .externalId(1L)
        .firstName("Sally")
        .lastName("Long")
        .email("sally.long@london.com")
        .ipAddress("141.49.93.0")
        .location(new GeoJsonPoint(-0.1273621, 51.5077922))
        .build();
  }

  public static List<Person> aDefaultListOfPeople() {
    return List.of(aDefaultPerson(),
        Person.builder()
            .externalId(2L)
            .firstName("Jacob")
            .lastName("Strong")
            .email("jacob.strong@LondonOntario.com")
            .ipAddress("145.56.96.0")
            .location(new GeoJsonPoint(-81.2304319, 42.9825139))
            .build(),
        Person.builder()
            .externalId(3L)
            .firstName("Kate")
            .lastName("Lite")
            .email("kate.lite@blackpool.com")
            .ipAddress("145.56.96.0")
            .location(new GeoJsonPoint(-2.7122216, 52.6716613))
            .build(),
        Person.builder()
            .externalId(4L)
            .firstName("Alex")
            .lastName("Kelly")
            .email("alex.kelly@london.com")
            .ipAddress("141.49.93.0")
            .location(new GeoJsonPoint(0.8078532, 51.6710832))
            .build());

  }

  public static List<Person> aDefaultListOfPeopleInCity() {
    return List.of(aDefaultPerson(),
        Person.builder()
            .externalId(4L)
            .firstName("Alex")
            .lastName("Kelly")
            .email("alex.kelly@london.com")
            .ipAddress("141.49.93.0")
            .location(new GeoJsonPoint(0.8078532, 51.6710832))
            .build());

  }

}
