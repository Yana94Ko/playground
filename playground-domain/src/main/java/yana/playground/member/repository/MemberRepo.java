package yana.playground.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import yana.playground.member.entity.Member;

public interface MemberRepo extends JpaRepository<Member, Long> {
    Optional <Member> findByEmail(String email);
}
