package com.douzon.smartlogistics.domain.member.dao.mapper;

import com.douzon.smartlogistics.domain.entity.Member;
import com.douzon.smartlogistics.domain.member.dto.LoginDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    Member memberLogin(LoginDto loginDto);

    boolean saveIpAddress(HashMap<String, Object> paramsMap);

    List<Member> searchMemberList(Integer memberNo, String memberId, String createDate);

    Member searchMember(Long memberNo);

    void insert(Member member);

    void modify(@Param("memberNo") Integer memberNo,
                @Param("member") Member member);

    void delete(List<Integer> memberNos);

    Optional<Member> retrieve(Integer memberNo);

    Object checkId(String memberId);
}
