package com.douzon.smartlogistics.domain.entity;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class Member {
    private Long memberNo;
    private String memberName;
    private String memberId;
    private String password;
    private String memberRole;
    private String createDate;
    private String ipaddress;
}
