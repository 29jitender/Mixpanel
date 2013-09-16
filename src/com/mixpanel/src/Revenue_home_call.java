package com.mixpanel.src;

public class Revenue_home_call implements Callback {

	
	public void thecall(){
		ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("revenu_home");//to get event top value
		ParseJson_object.setListener(this);
	}

	@Override
	public void methodToCallback(String response) {
		// TODO Auto-generated method stub
		
	}
}
