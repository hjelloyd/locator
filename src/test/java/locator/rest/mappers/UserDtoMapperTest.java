package locator.rest.mappers;

import generated.rest.user.model.UserDto;
import locator.model.Person;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static locator.fixtures.PersonFixture.aDefaultPerson;
import static locator.fixtures.UserDtoFixture.aDefaultUserDto;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UserDtoMapperTest {

  @Test
  void toPerson() {
    Person mappedPerson = UserDtoMapper.toPerson(aDefaultUserDto());
    assertThat(mappedPerson).isEqualTo(aDefaultPerson());
  }

  @Test
  void toPerson_when_null_coordinates() {
    UserDto userDto = aDefaultUserDto();
    userDto.setLatitude(null);
    Person mappedPerson = UserDtoMapper.toPerson(userDto);
    assertThat(mappedPerson.getLocation()).isNull();
  }
}
