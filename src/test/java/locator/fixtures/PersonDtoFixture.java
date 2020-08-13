package locator.fixtures;

import java.math.BigDecimal;

import generated.rest.locator.model.PersonDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class PersonDtoFixture {

  public static PersonDto aDefaultPersonDto() {
    return new PersonDto()
        .id(1L)
        .firstName("Sally")
        .lastName("Long")
        .email("sally.long@london.com")
        .ipAddress("141.49.93.0")
        .latitude(BigDecimal.valueOf(51.5077922))
        .longitude(BigDecimal.valueOf(-0.1273621))
        .location(PersonDto.LocationEnum.CITY);
  }
}
