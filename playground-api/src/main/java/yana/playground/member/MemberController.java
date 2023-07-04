package yana.playground.member;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yana.playground.error.ErrorCode;
import yana.playground.error.GeneralException;
import yana.playground.member.dto.MemberDTO;
import yana.playground.member.entity.Member;
import yana.playground.member.mapper.MemberMapper;
import yana.playground.member.service.MemberService;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    @GetMapping
    public List<Member> getMembers() {
        return memberService.getMembers();
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        return memberService.getMember(id);
    }

    @PostMapping
    public ResponseEntity<Member> signUpMember(@Valid @RequestBody Create memberDto) {
        if(memberService.getMemberByEmail(memberDto.getEmail())!= null){
            throw new GeneralException(ErrorCode.DUPLICATE_EMAIL);
        }
        Member signUpMember = memberService.signUpMember(mapper.dtoToMember(memberDto));
        return new ResponseEntity<>(signUpMember, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Member> updateMember(@Validated @RequestBody MemberDTO.Post memberDto) {
        Member updatedMember = memberService.updateMember(mapper.dtoToMember(memberDto));
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMember(@RequestBody MemberDTO.Post memberDto) {
        try {
            memberService.deleteMember(mapper.dtoToMember(memberDto));
            return new ResponseEntity<>("회원 삭제 성공", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("회원 삭제 실패", HttpStatus.BAD_REQUEST);
        }
    }
}
