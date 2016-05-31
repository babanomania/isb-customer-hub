package com.dchdemo.osp.wayneent.dbutil;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {

	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String email;
	private String statementInEmail;
	private String crmid;
	private String cmsid;
	
	@XmlElement
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@XmlElement
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@XmlElement
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	@XmlElement
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@XmlElement
	public String getStatementInEmail() {
		return statementInEmail;
	}
	public void setStatementInEmail(String statementInEmail) {
		this.statementInEmail = statementInEmail;
	}
	
	@XmlElement
	public String getCrmid() {
		return crmid;
	}
	public void setCrmid(String crmid) {
		this.crmid = crmid;
	}
	
	@XmlElement
	public String getCmsid() {
		return cmsid;
	}
	public void setCmsid(String cmsid) {
		this.cmsid = cmsid;
	}
}
