package com.dchdemo.isb.hub.converters;

import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;

import com.dchdemo.isb.hub.dynamics.AccountsBean;

@Converter
public class AccountsConverter implements TypeConverters {

	@Converter
	public static AccountsBean fromAcmeCustomer( com.dchdemo.osp.acmeinc.dbutil.Customer acmeCust ){
		
		AccountsBean accounts = new AccountsBean();
		accounts.setName( acmeCust.getFirstName() + " " + acmeCust.getLastName() );
		accounts.setEmailaddress1( acmeCust.getEmail() );
		accounts.setNew_emailpreference( acmeCust.getStatementInEmail() );
		accounts.setAccountid( acmeCust.getCrmid() );
		
		return accounts;
	}
	
	@Converter
	public static AccountsBean fromWayneCustomer( com.dchdemo.osp.wayneent.dbutil.Customer wayneCust ){
		
		AccountsBean accounts = new AccountsBean();
		accounts.setName( wayneCust.getFirstName() + " " + wayneCust.getLastName() );
		accounts.setEmailaddress1( wayneCust.getEmail() );
		accounts.setNew_emailpreference( wayneCust.getStatementInEmail() );
		accounts.setAccountid( wayneCust.getCrmid() );
		
		return accounts;
	}
}
