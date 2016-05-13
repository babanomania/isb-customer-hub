package com.dchdemo.osp.acmeinc.dbutil;

public class Customer {

	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String email;
	private String statementInEmail;
	private String crmid;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatementInEmail() {
		return statementInEmail;
	}
	public void setStatementInEmail(String statementInEmail) {
		this.statementInEmail = statementInEmail;
	}
	public String getCrmid() {
		return crmid;
	}
	public void setCrmid(String crmid) {
		this.crmid = crmid;
	}
	
}
