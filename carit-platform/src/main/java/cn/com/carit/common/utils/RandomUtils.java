package cn.com.carit.common.utils;

import java.util.Random;

public class RandomUtils {

	
	private static Random random = new Random();;
	
	private static String [] candidatee=new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
		, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m"
		, "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
		, "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"
		, "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
		, "!", "@", "#", "$", "%", "^", "&", "*", "(", ")"};
	
	private RandomUtils(){
	}
	
	/**
	 * 随机指定长度密码
	 * @param length
	 * @return
	 */
	public static String randomPassword(int length){
		StringBuilder sb=new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(candidatee[random.nextInt(candidatee.length)]);
		}
		return sb.toString();
	}
	
	/**
	 * 随机8位密码
	 * @return
	 */
	public static String randomPassword(){
		return randomPassword(8);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String pwd=randomPassword(8);
		System.out.println(pwd.matches("[\\w!@#$%^&**()]{6,30}"));
		System.out.println("12345!".matches("[\\w!@#$%^&**()]{6,30}"));
	}

}
