package com.dchdemo.isb.hub.dynamics;

import net.minidev.json.JSONObject;

public class AccountsBean {

	private String accountid;
	private String name;
	private String emailaddress1;
	private String new_emailpreference;
	
	public int save() throws Exception {
		
		JSONObject account = new JSONObject();
        account.put("accountid", accountid);
        account.put("name", name);
        account.put("emailaddress1", emailaddress1);
        account.put("new_emailpreference", new_emailpreference);
        
        String uri = "/api/data/v8.0/accounts(" + accountid + ")";
        
		return CRMUtil.saveData( account, uri );
	}
	
	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailaddress1() {
		return emailaddress1;
	}
	public void setEmailaddress1(String emailaddress1) {
		this.emailaddress1 = emailaddress1;
	}
	public String getNew_emailpreference() {
		return new_emailpreference;
	}
	public void setNew_emailpreference(String new_emailpreference) {
		this.new_emailpreference = new_emailpreference;
	}
	
}
