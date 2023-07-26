package yana.playground.global;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String testMainPage (){
        return "현재 페이지 위치 : \"/\"\n야나의 플레이그라운드에 오신걸 환영합니다 :)";
    }
}
