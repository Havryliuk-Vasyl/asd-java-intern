package team.asd.entity;

import java.sql.Timestamp;

public class Party {
	private int id;
	private String name;
	private String state;
	private String postalAddress;
	private String emailAddress;
	private String mobilePhone;
	private String password;
	private String currency;
	private String userType;
	private Timestamp version;

	public Party() {
		this.state = "Initial";
	}

	public Party(int id, String name, String state, String postalAddress, String emailAddress, String mobilePhone, String password, String currency,
			String userType, Timestamp version) {
		this.id = id;
		this.name = name;
		this.state = state;
		this.postalAddress = postalAddress;
		this.emailAddress = emailAddress;
		this.mobilePhone = mobilePhone;
		this.password = password;
		this.currency = currency;
		this.userType = userType;
		this.version = version;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Timestamp getVersion() {
		return version;
	}

	public void setVersion(Timestamp version) {
		this.version = version;
	}
}
