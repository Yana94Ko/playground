package yana.playground.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yana.playground.member.entity.Member;

public interface MemberRepo extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
}
