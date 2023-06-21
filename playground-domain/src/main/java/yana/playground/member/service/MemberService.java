package yana.playground.member.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yana.playground.member.entity.Member;
import yana.playground.member.entity.MemberStatus;
import yana.playground.member.repository.MemberRepo;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepo memberRepo;

    public List<Member> getMembers() {
        return memberRepo.findAll();
    }

    public Member getMember(Long id) {
        return memberRepo.findById(id).orElseThrow(IllegalAccessError::new);
    }

    //temp
    public Member getMemberByEmail(String email) {
        return memberRepo.findByEmail(email);
    }

    @Transactional
    public Member signUpMember(Member member) {
        return memberRepo.save(member);
    }

    @Transactional
    public void deleteMember(Member member) {
        Member loginMember = getMemberByEmail(member.getEmail());
        loginMember.setStatus(MemberStatus.MEMBER_WITHDRAWAL);
    }

    @Transactional
    public Member updateMember(Member member) {
        //TODO : spring security 통한 auth 구현해서 temp코드 정리하기
        Member existingMember = getMemberByEmail(member.getEmail());
        existingMember.setEmail(member.getEmail());
        existingMember.setPassword(member.getPassword());
        existingMember.setNickname(member.getNickname());
        existingMember.setPostcode(member.getPostcode());
        existingMember.setAddress(member.getAddress());
        existingMember.setDetailAddress(member.getDetailAddress());
        existingMember.setPhone(member.getPhone());
        existingMember.setRealName(member.getRealName());
        return existingMember;
    }

}
