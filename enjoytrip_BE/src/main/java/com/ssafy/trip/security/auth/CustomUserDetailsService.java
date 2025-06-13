package com.ssafy.trip.security.auth;

import com.ssafy.trip.domain.auth.model.OAuthProvider;
import com.ssafy.trip.domain.auth.repository.OauthProviderDao;
import com.ssafy.trip.domain.member.dto.MemberRecord;
import com.ssafy.trip.domain.member.model.Member;

import com.ssafy.trip.domain.member.repository.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberDao memberDao;
    private final OauthProviderDao oauthProviderDao;

    // username(email)로 사용자를 찾는 메서드
    @Override
    public UserDetails loadUserByUsername(String username){
        MemberRecord memberRecord = memberDao.selectByEmail(username);
        if (memberRecord == null)
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        Member member = memberRecord.toModel();
        return new CustomUserDetails(null, member);
    }

    // oauth provider key로 사용자를 찾는 메서드
    public UserDetails loadUserByOAuthId(String oAuthProviderId, String email) throws UsernameNotFoundException {
        OAuthProvider oAuthProvider = oauthProviderDao.selectByOAuthProviderId(oAuthProviderId);
        if (oAuthProvider != null)
            return new CustomUserDetails(oAuthProvider, oAuthProvider.getMember());

        MemberRecord memberRecord = memberDao.selectByEmail(email);

        if (memberRecord != null) {
            Member member = memberRecord.toModel();
            return new CustomUserDetails(null, member);
        }

        // OAuthProvider가 null인 경우는 처음 로그인 시도
        return new CustomUserDetails(null, null);
    }
}
