package yana.playground.member.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yana.playground.global.mapper.MemberMapper;
import yana.playground.member.dto.MemberRequest;
import yana.playground.member.dto.MemberResponse;
import yana.playground.member.entity.Member;
import yana.playground.member.exceptions.MemberNotFoundException;
import yana.playground.member.repository.MemberRepo;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepo memberRepo;
    private final MemberMapper mapper;

    public List<MemberResponse> getMembers() {
        return mapper.memberListEtoD(memberRepo.findAll());
    }

    public MemberResponse getMember(Long id) {
        return mapper.memberEtoD(memberRepo.findById(id).orElseThrow(() -> new MemberNotFoundException(id)));
    }

    //temp
    public Member getMemberByEmail(String email) {
        return memberRepo.findByEmail(email);
    }

    @Transactional
    public MemberResponse signUpMember(MemberRequest.Create memberDto) {
        Member newMember = mapper.memberCreateDtoE(memberDto);
        return mapper.memberEtoD(memberRepo.save(newMember));
    }

    @Transactional
    public MemberResponse updateMember(MemberRequest.Update memberDto) {
        Member newMember = mapper.memberUpdateDtoE(memberDto);
        // TODO : spring security 통한 auth 구현해서 temp코드 정리하기
        Member existingMember = getMemberByEmail(newMember.getEmail());
        existingMember.updateMember(newMember);
        return mapper.memberEtoD(existingMember);
    }

    @Transactional
    public void deleteMember(MemberRequest.Delete memberDto) {
        Member loginMember = getMemberByEmail(memberDto.getEmail());
        loginMember.deleteMember(loginMember);
    }
}
