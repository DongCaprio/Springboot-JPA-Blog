package com.cos.blog.model;

import lombok.Data;

@Data
public class OAuthToken {
	//카카오 로그인 시 주는 토큰 데이터를 담을 클래스
	private String access_token;
	private String token_type;
	private String refresh_token;
	private String id_token;
	private int expires_in;
	private String scope;
	private int refresh_token_expires_in;
}
