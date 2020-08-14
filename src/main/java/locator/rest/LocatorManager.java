package locator.rest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import generated.rest.locator.model.PersonDto;
import generated.rest.user.UserServiceApi;
import locator.rest.mappers.PersonDtoMapper;
import locator.services.FindPeopleWithMongo;
import locator.services.FindUsersByHaversineFormula;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static generated.rest.locator.model.PersonDto.LocationEnum.SURROUNDING_AREA;

@RequiredArgsConstructor
@Component
@Slf4j
public class LocatorManager {

  private final UserServiceApi userServiceApi;

  private final FindPeopleWithMongo findPeopleWithMongo;

  private final FindUsersByHaversineFormula findUsersByHaversineFormula;

  public List<PersonDto> getPeopleUsingMongo(String city, Integer distance) {
    log.info("Using Mongo method to find people");
    List<PersonDto> peopleFromCity = getPeopleFromCity(city);

    List<PersonDto> peopleFromSurroundingArea = findPeopleWithMongo.execute(city, distance).stream()
        .map(PersonDtoMapper::fromPerson).collect(Collectors.toList());

    log.info("Received {} people from surrounding the city {}", peopleFromSurroundingArea.size(),
        city);
    peopleFromCity.addAll(peopleFromSurroundingArea);
    return getDistinctPeople(peopleFromCity);
  }

  public List<PersonDto> getPeopleUsingHaversine(String city, Integer distance) {
    log.info("Using Haversine method to find people");
    List<PersonDto> peopleFromCity = getPeopleFromCity(city);

    List<PersonDto> peopleFromSurroundingArea = findUsersByHaversineFormula.execute(city, distance)
        .stream().map(user -> PersonDtoMapper.fromUserDto(user, SURROUNDING_AREA))
        .collect(Collectors.toList());

    log.info("Received {} people from surrounding the city {}", peopleFromSurroundingArea.size(),
        city);
    peopleFromCity.addAll(peopleFromSurroundingArea);
    return getDistinctPeople(peopleFromCity);
  }

  private List<PersonDto> getPeopleFromCity(String city) {
    List<PersonDto> peopleFromCity = userServiceApi.getUsersByCity(capitalizeCity(city)).stream()
        .map(PersonDtoMapper::fromUserDto).collect(Collectors.toList());
    log.info("Received {} people from the city {}", peopleFromCity.size(), city);
    return peopleFromCity;
  }

  private String capitalizeCity(String city) {
    return city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();
  }

  private List<PersonDto> getDistinctPeople(List<PersonDto> people) {
    return people.stream()
        .filter(distinctByKey(PersonDto::getId))
        .collect(Collectors.toList());
  }

  public static <T> Predicate<T> distinctByKey(
      Function<? super T, ?> keyExtractor) {
    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }
}
