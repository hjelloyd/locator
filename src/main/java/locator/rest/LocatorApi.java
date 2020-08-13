package locator.rest;

import java.util.List;

import generated.rest.locator.LocatorInterfaceApi;
import generated.rest.locator.model.PersonDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class LocatorApi implements LocatorInterfaceApi {

  private final LocatorManager locatorManager;

  @Override
  public ResponseEntity<List<PersonDto>> getPeopleInOrAroundACityMongo(String city,
      Integer distance) {
    distance = distance == null ? 50 : distance;
    log.info("Received a request to get people from {}, and {} miles arround it", city, distance);
    return ResponseEntity.ok(locatorManager.getPeopleUsingMongoPoint(city, distance));
  }

}
