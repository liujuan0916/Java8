package querywrapperdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qzz
 */
@SpringBootApplication
@MapperScan("querywrapperdemo.dao")
public class QuerywrapperDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuerywrapperDemoApplication.class, args);
    }

}
