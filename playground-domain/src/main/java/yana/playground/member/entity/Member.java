package yana.playground.member.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yana.playground.global.Auditable;

@Getter
@Builder
@Entity
@Table(name = "MEMBER")
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 20)
    private String nickname;

    private String password;

    private String realName;

    @Column(nullable = false, unique = true)
    private String phone;

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private MemberStatus status = MemberStatus.MEMBER_ACTIVE;

    @Embedded
    private Address address;

//    private String oauthId;
//
//    private String provider;
//
//    private String providerId;
    public void updateMember(Member updateMember){
        Optional.ofNullable(updateMember.getEmail()).ifPresent(email -> this.email = email);
        Optional.ofNullable(updateMember.getNickname()).ifPresent(nickname -> this.nickname = nickname);
        Optional.ofNullable(updateMember.getPassword()).ifPresent(password -> this.password = password);
        Optional.ofNullable(updateMember.getRealName()).ifPresent(realName -> this.realName = realName);
        Optional.ofNullable(updateMember.getPhone()).ifPresent(phone -> this.phone = phone);
        Optional.ofNullable(updateMember.getAddress()).ifPresent(address -> this.address = address);
    }
    public void deleteMember(Member deleteMember){
        this.status = MemberStatus.MEMBER_WITHDRAWAL;
    }
}