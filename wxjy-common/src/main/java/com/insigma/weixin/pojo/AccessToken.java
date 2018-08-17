package com.insigma.weixin.pojo;

/**
 * 凭证
 *
 * @author liufeng
 * @date 2013-10-17
 */
public class AccessToken {

    // 接口访问凭证
    private String accessToken;
    // 凭证有效期，单位：秒
    private int expiresIn;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String token) {
		this.accessToken = token;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

    
}
