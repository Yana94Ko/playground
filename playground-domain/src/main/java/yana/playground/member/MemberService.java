package yana.playground.member;

import org.springframework.stereotype.Service;

@Service
public class MemberService {

    public String getMembers() {
        return "All Members";
    }

}
