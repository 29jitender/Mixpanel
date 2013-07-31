package com.mixpanel.src;


public class Color_cal {

	public static String colorcall(String event) {
		int length=event.length();
		  length=length-1;
		String hex = null;
		String[] colors={"#6666CC","#CC66CC","#8A5C2E","#FF3333","#5CB800","#FFFF33","#CC3380","#CC3333","#8A00B8","#FF6633"
				,"#990033","#295F99","#FF7B00","#7F007F","#1B7777","#FF5500","#773A00","#AAE211","#CC33FF","#FF9494"};
		
		
		if(length>19){
			int i =length%20;
			hex=colors[i];
			
		}
		else{
			hex=colors[length];
			
		}
		//int n=255%length;
//		
//		int r=(int) Math.sqrt(length*200);
//		int g=(int) Math.sqrt(length-(length*134));
//		int b=(int) Math.sqrt(length+(length*34));
//		//int brightness = sqrt(r^2+g^2+b^2);
//		String hex = String.format("#%02x%02x%02x", r, g, b);
		
		return hex;
	 
		   
	}
}
