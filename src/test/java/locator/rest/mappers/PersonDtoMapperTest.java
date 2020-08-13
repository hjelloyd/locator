package locator.rest.mappers;


import generated.rest.locator.model.PersonDto;
import org.junit.jupiter.api.Test;

import static locator.fixtures.PersonDtoFixture.aDefaultPersonDto;
import static locator.fixtures.PersonFixture.aDefaultPerson;
import static locator.fixtures.UserDtoFixture.aDefaultUserDto;
import static org.assertj.core.api.Assertions.assertThat;

public class PersonDtoMapperTest {

  @Test
  void fromPerson() {
    PersonDto  expectedPersonDto = aDefaultPersonDto();
    expectedPersonDto.setLocation(PersonDto.LocationEnum.SURROUNDING_AREA);
    PersonDto mappedPersonDto = PersonDtoMapper.fromPerson(aDefaultPerson());
    assertThat(mappedPersonDto).isEqualTo(expectedPersonDto);
  }

  @Test
  void fromUserDto() {
    PersonDto mappedPersonDto = PersonDtoMapper.fromUserDto(aDefaultUserDto());
    assertThat(mappedPersonDto).isEqualTo(aDefaultPersonDto());
  }


}
