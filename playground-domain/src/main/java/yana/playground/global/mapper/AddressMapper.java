package yana.playground.global.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import yana.playground.member.dto.AddressDto;
import yana.playground.member.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    default Address toEntity(AddressDto addressDto) {
        if (addressDto == null) {
            return null;
        }
        return new Address(addressDto.getZipCode(), addressDto.getAddress(), addressDto.getDetailAddress());
    }

    AddressDto toDto(Address address);
}
