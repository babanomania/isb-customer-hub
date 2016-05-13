package com.dchdemo.isb.hub.config;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.dchdemo.isb.hub.dynamics.CRMUtil;

public class ServiceBusConfigs {

	private static String ISB_CONFIG = "isb_config.properties";
	private static Properties isbConfig = new Properties();
	
	private String acmeUrl;
	private String wayneEntUrl;
	private String policyNumThreshold;
	
	public ServiceBusConfigs() {
		
		try
		{
			isbConfig.load( CRMUtil.class.getClassLoader().getResourceAsStream( ISB_CONFIG ) );
			this.acmeUrl = getProperty("acmeUrl");
			this.wayneEntUrl  = getProperty("wayneEntUrl");
			this.policyNumThreshold = isbConfig.getProperty("policyNumThreshold");
		
		}catch( Exception ex ){
			throw new RuntimeException(ex);
		}
	}

	public String getProperty( String key ){
		
		if(! StringUtils.isEmpty( System.getenv( key) ) ){
			return  System.getenv( key) ;
		}else{
			return isbConfig.getProperty( key );
		}
		
	}
	
	public String getAcmeUrl() {
		return this.acmeUrl;
	}

	public void setAcmeUrl(String acmeUrl) {
		this.acmeUrl = acmeUrl;
	}

	public String getWayneEntUrl() {
		return wayneEntUrl;
	}

	public void setWayneEntUrl(String wayneEntUrl) {
		this.wayneEntUrl = wayneEntUrl;
	}

	public String getPolicyNumThreshold() {
		return policyNumThreshold;
	}

	public void setPolicyNumThreshold(String policyNumThreshold) {
		this.policyNumThreshold = policyNumThreshold;
	}
	
}
