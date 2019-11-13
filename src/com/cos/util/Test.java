package com.cos.util;

public class Test {
	public static void main(String[] args) {
		byte b = 117; // 2의 8승 = 256
		
		//byte를 String으로
		String s = Integer.toString(b);
		System.out.println(s);
		
		//Test 비트 연산
		// 00000000 00000000 00000000 00000001 = binary(2진수)
		int i = 1;
		System.out.println(Integer.toBinaryString(i));
		
		// 00000000 00000000 00000000 10010110 = int
		// 첫째자리가 1이면(128이상이면) binary 화 될때 1의 앞자리가 전부 1로 바뀜
		// 11111111 11111111 11111111 10010110 = binary(2진수)
		// 이렇게 변조되는 것을 막기 위해서 마스킹을 해줘야한다.
		// 16진수 0xff = 00000000 00000000 00000000 11111111
		// and 연산은 둘다 1이여야 1로 출력되는 연산이다 
		int j = 150;
		System.out.println(Integer.toBinaryString(j));
		
		System.out.println(Integer.toString((byte)j & 0xff, 16));
		
	}
}
