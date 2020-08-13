package locator.services;

import java.util.List;

import generated.rest.user.UserServiceApi;
import locator.model.Person;
import locator.model.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static locator.fixtures.UserDtoFixture.aDefaultAllUsersDtoResponse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PopulateDatabaseWithPeopleTest {

  @Mock
  private PersonRepository personRepository;

  @Mock
  private UserServiceApi userServiceApi;

  private final ArgumentCaptor<List<Person>> personCaptor = ArgumentCaptor.forClass(List.class);


  private PopulateDatabaseWithPeople populateDatabaseWithPeople;

  @BeforeEach
  void setup() {
    populateDatabaseWithPeople = new PopulateDatabaseWithPeople(personRepository, userServiceApi);
  }

  @Test
  void ensure_that_files_are_saved() {
    when(userServiceApi.getUsers()).thenReturn(aDefaultAllUsersDtoResponse());
    populateDatabaseWithPeople.execute();
    verify(personRepository, times(1)).deleteAll();
    verify(userServiceApi, times(1)).getUsers();
    verify(personRepository, times(1)).saveAll(personCaptor.capture());
    verify(personRepository, times(1)).count();

    List<Person> people = personCaptor.getValue();
    Assertions.assertThat(people.size()).isEqualTo(4);
  }

}
