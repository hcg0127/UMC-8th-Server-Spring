package umc.spring.config.security.oauth.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.TempHandler;

import java.util.Arrays;

@Component
@Slf4j
public class KakaoUtil {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUrl;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    public KakaoDTO.OAuthToken requestToken(String code) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", clientId);
        map.add("redirect_uri", redirectUrl);
        map.add("code", code);
//        map.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate
                .exchange("https://kauth.kakao.com/oauth/token",
                        HttpMethod.POST,
                        request,
                        String.class);

        ObjectMapper mapper = new ObjectMapper();
        KakaoDTO.OAuthToken oAuthToken = null;

        try {
            oAuthToken = mapper.readValue(response.getBody(),
                    KakaoDTO.OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new TempHandler(ErrorStatus.PARSE_ERROR);
        }
        return oAuthToken;
    }

    public KakaoDTO.KakaoProfile requestProfile(KakaoDTO.OAuthToken token) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer " + token.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate
                .exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET,
                        request, String.class);

        ObjectMapper mapper = new ObjectMapper();

        KakaoDTO.KakaoProfile profile = null;

        try {
            profile = mapper.readValue(response.getBody(),
                    KakaoDTO.KakaoProfile.class);
        } catch (JsonProcessingException e) {
            log.info(Arrays.toString(e.getStackTrace()));
            throw new TempHandler(ErrorStatus.PARSE_ERROR);
        }

        return profile;
    }
}
