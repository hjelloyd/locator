package locator.rest.mappers;

import generated.rest.user.model.UserDto;
import locator.model.Person;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@NoArgsConstructor(access = AccessLevel.NONE)
public class UserDtoMapper {

  public static Person toPerson(UserDto userDto) {
    return Person.builder()
        .externalId(userDto.getId())
        .ipAddress(userDto.getIpAddress())
        .firstName(userDto.getFirstName())
        .lastName(userDto.getLastName())
        .email(userDto.getEmail())
        .location(createPoint(userDto))
        .build();
  }

  private static GeoJsonPoint createPoint(UserDto userDto) {
    if (userDto.getLongitude() != null && userDto.getLatitude() != null) {
      return new GeoJsonPoint(userDto.getLongitude().doubleValue(),
          userDto.getLatitude().doubleValue());
    }
    return null;
  }

}
