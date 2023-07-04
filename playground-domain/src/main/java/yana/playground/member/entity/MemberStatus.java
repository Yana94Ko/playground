package yana.playground.member.entity;

import lombok.Getter;

public enum MemberStatus {
    MEMBER_ACTIVE("활동 중"),
    MEMBER_WITHDRAWAL("회원 탈퇴"),
    MEMBER_BLOCK("회원 차단");

    @Getter
    private final String status;

    MemberStatus( String status ){
        this.status = status;
    }

}
