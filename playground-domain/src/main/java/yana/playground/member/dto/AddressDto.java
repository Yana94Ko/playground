package yana.playground.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    @NotBlank
    private String zipCode;
    @NotBlank
    private String address;
    @NotBlank
    private String detailAddress;
}
