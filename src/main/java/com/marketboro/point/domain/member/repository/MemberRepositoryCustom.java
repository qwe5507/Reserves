package com.marketboro.point.domain.member.repository;

import com.marketboro.point.domain.member.Member;
import com.marketboro.point.domain.member.MemberProvider;

public interface MemberRepositoryCustom {

    Member findMemberById(Long memberId);

    Member findMemberByUidAndProvider(String uid, MemberProvider provider);

    Member findMemberByName(String name);

}
