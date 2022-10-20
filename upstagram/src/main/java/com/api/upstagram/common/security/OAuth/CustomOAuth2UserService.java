package com.api.upstagram.common.security.OAuth;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.api.upstagram.entity.OauthMemberInfo.OauthMemberInfoEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OauthMemberInfoRepository userRepository;
    
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 서비스 id 구분코드(Google / Kakao)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 진행 시, 키가되는 필드값(PK)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // Oauth2UserService
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        
        // Session 정보를 저장하는 직렬화된 dto 클래스
        OauthMemberInfoEntity user = saveOrUpdate(attributes);

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), 
            attributes.getAttributes(),
            attributes.getNameAttributeKey());
    }
    
    private OauthMemberInfoEntity saveOrUpdate(OAuthAttributes attributes) {
        OauthMemberInfoEntity user = userRepository.findByEmail(attributes.getEmail())
                                    .map(entity -> ((OauthMemberInfoEntity) entity).update(attributes.getName(), attributes.getPicture()))
                                    .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
