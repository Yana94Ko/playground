package yana.playground.member.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import yana.playground.member.entity.MemberStatus;
import yana.playground.member.entity.MemberRoles;

@Getter
@Setter
public class MemberResponse {
    @NotNull
    private Long id;
    @NotNull
    @Email
    private String email;
    private String nickname;
    @NotBlank
    @Size(min = 8)
    private String password;
    private String realName;
    private String phone;
    @Valid
    private AddressDto address;
    private MemberStatus status;
    private List<MemberRoles> roles;

    @Getter
    @Setter
    public static class idOnly {
        @NotNull
        private Long id;
    }
}
