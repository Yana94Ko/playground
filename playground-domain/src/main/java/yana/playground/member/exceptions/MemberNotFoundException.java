package yana.playground.member.exceptions;

public class MemberNotFoundException extends RuntimeException {
    private final Long memberId;

    public MemberNotFoundException(Long memberId) {
        super("Member not found with ID: " + memberId);
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId ;
    }
}
