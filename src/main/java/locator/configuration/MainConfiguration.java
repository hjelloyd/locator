package locator.configuration;

import generated.rest.user.UserServiceApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration {

  @Bean
  public UserServiceApi userServiceApi(){
    return new UserServiceApi();
  }

}
