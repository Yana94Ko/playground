package yana.playground.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
//TODO : setter의 경우 전체 적용하는게 맞는지 고민해보기
@Setter
@Entity
@Table(name = "MEMBER")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 20)
    private String nickname;

    private String password;

    private String postcode;

    private String address;

    private String detailAddress;

    private String realName;

    @Column(nullable = false, unique = true)
    private String phone;

//    private String oauthId;
//
//    private String provider;
//
//    private String providerId;

    @Enumerated(value = EnumType.STRING)
    private MemberStatus status = MemberStatus.MEMBER_ACTIVE;
}