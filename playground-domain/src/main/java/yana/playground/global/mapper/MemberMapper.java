package yana.playground.global.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import yana.playground.member.dto.MemberRequest;
import yana.playground.member.entity.Member;

@Mapper(componentModel = "spring",uses = AddressMapper.class)
public interface MemberMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "address", source = "memberDto.address")
    Member memberCreateDtoE(MemberRequest.Create memberDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", source = "memberDto.address")
    Member memberUpdateDtoE(MemberRequest.Update memberDto);
}
