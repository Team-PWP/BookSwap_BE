package team_pwp.swap_be.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import team_pwp.swap_be.domain.user.UserCreate;


@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class OAuthService {

    @Value("${kakao.client-id}")
    private String CLIENT_ID;
    @Value("${kakao.redirect-uri}")
    private String REDIRECTION_URI;
    @Value("${kakao.token-uri}")
    private String TOKEN_URI;

    @Value("${kakao.user-info-uri}")
    private String USER_INFO_URI;

    private final RestTemplate restTemplate = new RestTemplate();


    public UserCreate getUserInfo(String code) {
        //리소스 서버의 access token 받아오기
        String accessToken = getAccessToken(code);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        log.info("entity: {}", entity);
        //restTemplate.exchange(USER_INFO_URI,HttpMethod.GET, entity, JsonNode.class).getBody();
        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(USER_INFO_URI, HttpMethod.GET,
            entity, JsonNode.class);
        JsonNode userInfoNode = responseNode.getBody();
        log.info("userInfoNode: {}", userInfoNode);
        return UserCreate.from(userInfoNode);
    }

    public String getAccessToken(String code) {
        log.info("code: {}", code);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECTION_URI);
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        log.info("entity: {}", entity);
        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(TOKEN_URI,
            HttpMethod.POST,
            entity, JsonNode.class);
        //responseNode의 exception 처리
        if (responseNode.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("카카오 인증 정보가 올바르지 않습니다.");
        }
        log.info("responseNode: {}", responseNode);
        JsonNode accessTokenNode = responseNode.getBody();
        log.info("accessTokenNode: {}", accessTokenNode);
        return accessTokenNode.get("access_token").asText();
    }


}
