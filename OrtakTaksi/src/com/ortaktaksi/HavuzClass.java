package com.ortaktaksi;

public class HavuzClass {
	private String startPoint;
	private String destinationPoint;
	private String MeetingPoint;	
	private String MeetingTime;
	private String NameSurname;
	private int CreateUserID;
	private int RoutesID; 
	

	public HavuzClass(String startPoint, String destinationPoint,String MeetingPoint,String MeetingTime, String NameSurname,int CreateUserID,int RoutesID ) {
		this.startPoint = startPoint;
		this.destinationPoint = destinationPoint;
		this.MeetingPoint= MeetingPoint;
		this.MeetingTime =MeetingTime;
		this.NameSurname = NameSurname;		
		this.CreateUserID= CreateUserID;
		this.RoutesID=RoutesID;
	}
	public String getStartPoint() {
		return startPoint;
	}
	public String getDestinationPoint() {
		return destinationPoint;
	}
	public String getMeetingPoint(){
		return MeetingPoint;
	}
	public String getMeetingTime(){
		return MeetingTime;
	}
	public String getNameSurname() {
		return NameSurname;
	}
	public int getRoutesId(){
		return RoutesID;
	}
	public int getCreateUserId(){
		return CreateUserID;
	}
	
}