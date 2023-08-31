package yana.playground.member.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yana.playground.member.entity.MemberStatus;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequest {
    @Getter
    public static class Create {
        @NotNull
        @Email
        private String email;
        private String nickname;
        @NotBlank
        @Size(min = 8)
        @Setter
        private String password;
        private String realName;
        private String phone;
        @Valid
        private AddressDto address;
        private MemberStatus status;
        private List<MemberRoles> roles;

    }
    @Getter
    public static class Update {
        @NotNull
        private Long id;
        @NotNull
        @Email
        private String email;
        private String nickname;
        @Size(min = 8)
        private String password;
        private String realName;
        private String phone;
        @Valid
        private AddressDto address;
    }
    @Getter
    public static class Delete {
        @NotNull
        private Long id;
        @NotNull
        @Email
        private String email;
    }
}
