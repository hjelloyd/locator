package locator.model;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PersonRepository {

  private final MongoTemplate mongoTemplate;

  public List<Person> findPeopleWithinACircle(Circle circle) {
    return mongoTemplate
        .find(Query.query(Criteria.where("location").withinSphere(circle)), Person.class);
  }

  public void saveAll(List<Person> people){
    for(Person person : people) {
      mongoTemplate.save(person);
    }
  }

  public void deleteAll(){
    mongoTemplate.remove(new Query(), Person.class);
  }

  public Long count(){
    return mongoTemplate.count(new Query(), Person.class);
  }


}
