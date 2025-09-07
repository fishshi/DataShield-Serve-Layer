package com.datashield.util;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.datashield.exception.BusinessException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AvatarUtil {
    private static final String TOKEN = "1576|cayI5dNnUsG3K8WIyYN5uCjCj6AJqRs6inJ0LQws";

    private static final String BASE_URL = "https://picui.cn/api/v1";

    public static String uploadAvatar(String filePath) {
        WebClient webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Authorization", "Bearer " + TOKEN)
                .build();
        File file = new File(filePath);
        FileSystemResource resource = new FileSystemResource(file);
        String json = webClient.post()
                .uri("/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("file", resource))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        JsonNode root;
        try {
            root = new ObjectMapper().readTree(json);
            String url = root.path("data").path("links").path("url").asText();
            return url;
        } catch (Exception e) {
            throw new BusinessException("上传头像失败");
        }
    }
}