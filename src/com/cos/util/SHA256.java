package com.cos.util;

import java.security.MessageDigest;

//256bit 길이의 암호 = 해시= 복호화가 안됨
public class SHA256 {
	//password를 암호화해서 return 해줄 것
	
	public static String getEncrypt(String rawPassword, String salt) {
		//ex) rawPassword = qw5678qw
		//salt = "cos";
		String result = "";
		//[q,w,5,6,7,8,q,w,c,o,s]
		byte[] b = (rawPassword+salt).getBytes();
		
		try {
			//싱글톤 객체다
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(b);//MessageDigest가 SHA-256으로 암호화된 값을 들고 있음.
			byte [] bResult = md.digest();
			
			//테스트용 for 문 여기는 10진수인데 이걸 16진수로 바꿔야한다.
//			for(byte data : bResult) {
//				System.out.print(data+" ");
//			}
			//16진수로 바꿔보자
			StringBuffer sb = new StringBuffer();
			for (byte data : bResult) {
				sb.append(Integer.toString(data & 0xff, 16));
			}
			result = sb.toString();
//			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		
		return result;
	}
	public static void main(String[] args) {
		getEncrypt("qw5678qw", "cos");
	}
}
