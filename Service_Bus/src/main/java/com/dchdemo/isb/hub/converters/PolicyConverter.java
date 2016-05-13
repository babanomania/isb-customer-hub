package com.dchdemo.isb.hub.converters;

import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;

import com.dchdemo.isb.hub.beans.Policy;

@Converter
public class PolicyConverter implements TypeConverters {

	@Converter
	public static Policy fromAcme( com.dchdemo.osp.acmeinc.dbutil.Policy acmePol ){
		
		Policy pol = new Policy();
		pol.setPolicyNumber( acmePol.getPolicyNumber() );
		pol.setRole( acmePol.getRole() );
		pol.setLastPremiumPaid( acmePol.getLastPremiumPaid() );
		pol.setStatus( acmePol.getStatus() );
		pol.setSource( "Acme Inc." );
		
		return pol;
	}
	
	@Converter
	public static Policy fromWayne( com.dchdemo.osp.wayneent.dbutil.Policy waynePol ){
		
		Policy pol = new Policy();
		pol.setPolicyNumber( waynePol.getPolicyNumber() );
		pol.setRole( waynePol.getRole() );
		pol.setLastPremiumPaid( waynePol.getLastPremiumPaid() );
		pol.setStatus( waynePol.getStatus() );
		pol.setSource( "Wayne Enterprises" );
		
		return pol;
	}
	
}
