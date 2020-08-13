package locator.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import generated.rest.user.UserServiceApi;
import locator.model.Person;
import locator.model.PersonRepository;
import locator.rest.mappers.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class PopulateDatabaseWithPeople{

  private final PersonRepository personRepository;

  private final UserServiceApi userServiceApi;

  @PostConstruct
  public void execute(){
    log.info("clearing repository");
    personRepository.deleteAll();
    log.info("attempting to get users from bpdts-test-app.herokuapp.com/");
    List<Person> people = userServiceApi.getUsers().stream().map(UserDtoMapper::toPerson).collect(Collectors.toList());
    log.info("received {} users from bpdts-test-app.herokuapp.com/", people.size());
    personRepository.saveAll(people);
    log.info("{} people saved in repository", personRepository.count());
  }
}
