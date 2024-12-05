package com.lion.demo.security;

import com.lion.demo.entity.User;
import com.lion.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
@Slf4j
public class MyOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired private UserService userService;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    // 사용자 정보 받은걸 내DB에 넣을꺼
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String uid, email, uname;
        String hashedPwd = bCryptPasswordEncoder.encode("Social Login");
        User user = null;

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info(" === getAttribute() === " + oAuth2User.getAttributes());
        String provider = userRequest.getClientRegistration().getRegistrationId();
        switch (provider) {
            case "github":
                int id = oAuth2User.getAttribute("id");
                uid = provider + "_" + id;
                user = userService.findByUid(uid);
                if (user == null) { // 내 DB에 없으면 가입 시켜줌
                    uname = oAuth2User.getAttribute("name");
                    email = oAuth2User.getAttribute("email");
                    user = User.builder()
                            .uid(uid).pwd(hashedPwd).uname(uname).email(email)
                            .regDate(LocalDate.now()).role("ROLE_USER").provider(provider)
                            .build();
                    userService.registerUser(user);
                    log.info("깃허브 계정을 통해 회원가입이 되었습니다. " + user.getUname());
                }
                break;
            case  "google":
                String sub = oAuth2User.getAttribute("sub"); // 구글ID
                uid = provider + "_" + sub;
                user = userService.findByUid(uid);
                if (user == null) { // 내 DB에 없으면 가입 시켜줌
                    uname = oAuth2User.getAttribute("name");
                    uname = (uname == null) ? "google_user" : uname;
                    email = oAuth2User.getAttribute("email");
                    // profileUrl 해줘야함
                    user = User.builder()
                            .uid(uid).pwd(hashedPwd).uname(uname).email(email)
                            .regDate(LocalDate.now()).role("ROLE_USER").provider(provider)
                            .build();
                    userService.registerUser(user);
                    log.info("구글 계정을 통해 회원가입이 되었습니다. " + user.getUname());
                }

                break;

            case "naver":
                Map<String, Object> response = (Map) oAuth2User.getAttribute("response");
                String nid = (String) response.get("id");
                uid = provider + "_" + nid;
                user = userService.findByUid(uid);
                if (user == null) {         // 내 DB에 없으면 가입을 시켜줌
                    uname = (String) response.get("nickname");
                    uname = (uname == null) ? "naver_user" : uname;
                    email = (String) response.get("email");
//                    profileUrl = (String) response.get("profile_image");
                    user = User.builder()
                            .uid(uid).pwd(hashedPwd).uname(uname).email(email)
                            .regDate(LocalDate.now()).role("ROLE_USER").provider(provider)
                            .build();
                    userService.registerUser(user);
                    log.info("네이버 계정을 통해 회원가입이 되었습니다. " + user.getUname());
                }
                break;
        }
        return new MyUserDetails(user, oAuth2User.getAttributes());
    }
}
