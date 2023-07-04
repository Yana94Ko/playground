package yana.playground.member.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yana.playground.global.mapper.MemberMapper;
import yana.playground.member.dto.MemberRequest;
import yana.playground.member.entity.Member;
import yana.playground.member.exceptions.MemberNotFoundException;
import yana.playground.member.repository.MemberRepo;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepo memberRepo;
    private final MemberMapper mapper;

    public List<Member> getMembers() {
        return memberRepo.findAll();
    }

    public Member getMember(Long id) {
        return memberRepo.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
    }

    //temp
    public Member getMemberByEmail(String email) {
        return memberRepo.findByEmail(email);
    }

    @Transactional
    public Member signUpMember(MemberRequest.Create memberDto) {
        Member newMember = mapper.memberCreateDtoE(memberDto);
        return memberRepo.save(newMember);
    }

    @Transactional
    public Member updateMember(MemberRequest.Update memberDto) {
        Member newMember = mapper.memberUpdateDtoE(memberDto);
        //TODO : spring security 통한 auth 구현해서 temp코드 정리하기
        Member existingMember = getMemberByEmail(newMember.getEmail());
        existingMember.updateMember(newMember);
        return existingMember;
    }

    @Transactional
    public void deleteMember(MemberRequest.Delete memberDto) {
        Member loginMember = getMemberByEmail(memberDto.getEmail());
        loginMember.deleteMember(loginMember);
    }
}
