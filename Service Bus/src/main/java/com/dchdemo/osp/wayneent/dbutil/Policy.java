package com.dchdemo.osp.wayneent.dbutil;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType( namespace="http://www.origostandards.com/schema/mth/v2", name="mtg" )
@XmlRootElement
public class Policy {

	private String policyNumber;
	private String role;
	private String lastPremiumPaid;
	private String status;
	
	@XmlElement
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	
	@XmlElement
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@XmlElement
	public String getLastPremiumPaid() {
		return lastPremiumPaid;
	}
	public void setLastPremiumPaid(String lastPremiumPaid) {
		this.lastPremiumPaid = lastPremiumPaid;
	}
	
	@XmlElement
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
