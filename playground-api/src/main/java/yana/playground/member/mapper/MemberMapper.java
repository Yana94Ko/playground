package yana.playground.member.mapper;

import org.mapstruct.Mapper;
import yana.playground.member.dto.MemberDTO;
import yana.playground.member.entity.Member;
import yana.playground.member.entity.MemberStatus;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    default Member dtoToMember( MemberDTO.Post memberDto ){
        return Member.builder()
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .postcode(memberDto.getPostcode())
                .address(memberDto.getAddress())
                .detailAddress(memberDto.getDetailAddress())
                .realName(memberDto.getRealName())
                .phone(memberDto.getPhone())
                .status(MemberStatus.MEMBER_ACTIVE)
                .build();
    }
}
