package com.dchdemo.osp.acmeinc.dbutil;

public class Policy {

	private String policyNumber;
	private String role;
	private String lastPremiumPaid;
	private String status;
	
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getLastPremiumPaid() {
		return lastPremiumPaid;
	}
	public void setLastPremiumPaid(String lastPremiumPaid) {
		this.lastPremiumPaid = lastPremiumPaid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
