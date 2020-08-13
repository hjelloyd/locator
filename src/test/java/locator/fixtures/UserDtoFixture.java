package locator.fixtures;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import generated.rest.user.model.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class UserDtoFixture {

  public static UserDto aDefaultUserDto() {
    return new UserDto()
        .id(1L)
        .firstName("Sally")
        .lastName("Long")
        .email("sally.long@london.com")
        .ipAddress("141.49.93.0")
        .latitude(BigDecimal.valueOf(51.5077922))
        .longitude(BigDecimal.valueOf(-0.1273621));
  }

  public static List<UserDto> aDefaultCityUserDtoResponse() {
    return List.of(aDefaultUserDto(),
        new UserDto()
            .id(2L)
            .firstName("Jacob")
            .lastName("Strong")
            .email("jacob.strong@LondonOntario.com")
            .ipAddress("145.56.96.0")
            .latitude(BigDecimal.valueOf(42.9825139))
            .longitude(BigDecimal.valueOf(-81.2304319)));
  }

  public static List<UserDto> aDefaultAllUsersDtoResponse() {
    var users = new ArrayList<>(aDefaultCityUserDtoResponse());
    users.add(
        new UserDto()
            .id(3L)
            .firstName("Kate")
            .lastName("Lite")
            .email("kate.lite@blackpool.com")
            .ipAddress("145.56.96.0")
            .latitude(BigDecimal.valueOf(52.6716613))
            .longitude(BigDecimal.valueOf(-2.7122216)));
    users.add(
        new UserDto()
            .id(4L)
            .firstName("Alex")
            .lastName("Kelly")
            .email("alex.kelly@london.com")
            .ipAddress("141.49.93.0")
            .latitude(BigDecimal.valueOf(51.6710832))
            .longitude(BigDecimal.valueOf(0.8078532)));
    return users;
  }
}
