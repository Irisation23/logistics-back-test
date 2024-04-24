package com.douzon.smartlogistics.domain.member.application;

import com.douzon.smartlogistics.domain.entity.Member;
import com.douzon.smartlogistics.domain.member.dao.MemberDao;
import com.douzon.smartlogistics.domain.member.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberDao;
    public Member memberLogin(LoginDto loginDto) {
        return memberDao.memberLogin(loginDto);
    }


    public boolean saveIpAddress(HashMap<String, Object> paramsMap) {
        return memberDao.saveIpAddress(paramsMap);
    }

    public List<Member> searchMemberList(Integer memberNo, String memberId, String createDate) {
        return memberDao.searchMemberList(memberNo,memberId, createDate);
    }

    //멤버넘버를 통해서 멤버 단일값 정보를 들고옴
    public Member searchMember(Long memberNo) {
        return memberDao.searchMember(memberNo);
    }

    public void insert(Member member) {
        memberDao.insert(member);
    }

    public void modify(Integer memberNo, Member member) {
        memberDao.modify(memberNo, member);
    }

    public void delete(List<Integer> memberNos) {
            memberDao.delete(memberNos);
    }

    public boolean checkId(String memberId) {
        return memberDao.checkId(memberId);
    }
}
