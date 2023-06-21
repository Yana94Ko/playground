package yana.playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication//초기설정시에 임시로 설정한 값(DB연동 후 바로 삭제 필요)
public class PlaygroundApiApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-api,application-domain,application-SECRET");
        SpringApplication.run(PlaygroundApiApplication.class, args);
    }
}