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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class LocatorManager {

  private final UserServiceApi userServiceApi;

  private final FindPeopleWithMongo findPeopleWithMongo;

  public List<PersonDto> getPeopleUsingMongoPoint(String city, Integer distance) {
    List<PersonDto> peopleFromCity = userServiceApi.getUsersByCity(capitalizeCity(city)).stream()
        .map(PersonDtoMapper::fromUserDto).collect(Collectors.toList());
    log.info("Received {} people from the city {}", peopleFromCity.size(), city);

    List<PersonDto> peopleFromSurroundingArea = findPeopleWithMongo.execute(city, distance).stream()
        .map(PersonDtoMapper::fromPerson).collect(Collectors.toList());

    log.info("Received {} people from surrounding the city {}", peopleFromSurroundingArea.size(),
        city);
    peopleFromCity.addAll(peopleFromSurroundingArea);
    return getDistinctPeople(peopleFromCity);
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
