package locator.rest.mappers;

import java.math.BigDecimal;

import generated.rest.locator.model.PersonDto;
import generated.rest.user.model.UserDto;
import locator.model.Person;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class PersonDtoMapper {

  public static PersonDto fromUserDto(UserDto userDto){
    return new PersonDto()
        .id(userDto.getId())
        .ipAddress(userDto.getIpAddress())
        .firstName(userDto.getFirstName())
        .lastName(userDto.getLastName())
        .email(userDto.getEmail())
        .latitude(userDto.getLatitude())
        .longitude(userDto.getLongitude())
        .location(PersonDto.LocationEnum.CITY);
  }

  public static PersonDto fromPerson(Person person){
    return new PersonDto()
        .id(person.getExternalId())
        .ipAddress(person.getIpAddress())
        .firstName(person.getFirstName())
        .lastName(person.getLastName())
        .email(person.getEmail())
        .latitude(BigDecimal.valueOf(person.getLocation().getCoordinates().get(1)))
        .longitude(BigDecimal.valueOf(person.getLocation().getCoordinates().get(0)))
        .location(PersonDto.LocationEnum.SURROUNDING_AREA);
  }

}
