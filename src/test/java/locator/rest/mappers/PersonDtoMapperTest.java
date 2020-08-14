package locator.rest.mappers;


import generated.rest.locator.model.PersonDto;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static generated.rest.locator.model.PersonDto.LocationEnum.SURROUNDING_AREA;
import static locator.fixtures.PersonDtoFixture.aDefaultPersonDto;
import static locator.fixtures.PersonFixture.aDefaultPerson;
import static locator.fixtures.UserDtoFixture.aDefaultUserDto;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PersonDtoMapperTest {

  @Test
  void fromPerson() {
    PersonDto  expectedPersonDto = aDefaultPersonDto();
    expectedPersonDto.setLocation(SURROUNDING_AREA);
    PersonDto mappedPersonDto = PersonDtoMapper.fromPerson(aDefaultPerson());
    assertThat(mappedPersonDto).isEqualTo(expectedPersonDto);
  }

  @Test
  void fromUserDto() {
    PersonDto mappedPersonDto = PersonDtoMapper.fromUserDto(aDefaultUserDto());
    assertThat(mappedPersonDto).isEqualTo(aDefaultPersonDto());
  }

  @Test
  void fromUserDto_wit_location() {
    PersonDto mappedPersonDto = PersonDtoMapper.fromUserDto(aDefaultUserDto(), SURROUNDING_AREA);
    assertThat(mappedPersonDto).isEqualToIgnoringGivenFields(aDefaultPersonDto(), "location");
    assertThat(mappedPersonDto.getLocation()).isEqualTo(SURROUNDING_AREA);
  }


}
