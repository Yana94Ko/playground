package yana.playground.member.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {
    private String zipCode;
    private String address;
    private String detailAddress;
    public Address () {
    };
    public Address(String zipCode, String address, String detailAddress){
        super();
        this.zipCode = zipCode;
        this.address = address;
        this.detailAddress = detailAddress;
    }
}
