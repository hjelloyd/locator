package locator.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "person")
@Data
@Setter(AccessLevel.NONE)
@Builder(toBuilder = true)
public class Person {

  @Id
  private String id;

  @Field("external_id")
  private Long externalId;

  @Field("first_name")
  private String firstName;

  @Field("last_name")
  private String lastName;

  @Field("email")
  private String email;

  @Field("ip_address")
  private String ipAddress;

  @Field("location")
  private GeoJsonPoint location;
}
