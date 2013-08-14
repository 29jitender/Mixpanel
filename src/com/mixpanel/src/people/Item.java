package com.mixpanel.src.people;

public class Item {
	private String headline;
	private String reporterName;
	private String date;
	private String url;
	private String location;
	private String userid;


	public String getuserid(){
		return userid;
	}
	public void setuserid(String userid){
		this.userid=userid;
	}
	
	public String getlocation(){
		return location;
	}
	public void setlocation(String location){
		this.location=location;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getReporterName() {
		return reporterName;
	}

	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return headline+","+userid;
	}
}
