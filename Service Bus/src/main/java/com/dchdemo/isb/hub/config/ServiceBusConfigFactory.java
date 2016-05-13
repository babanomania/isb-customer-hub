package com.dchdemo.isb.hub.config;

public class ServiceBusConfigFactory {

	private ServiceBusConfigs ctnConf = new ServiceBusConfigs();

	public ServiceBusConfigs getConfig(){
		return ctnConf;
	}
	
	public void setConfig( ServiceBusConfigs pCtnConf ){
		this.ctnConf = pCtnConf;
	}
	
	
}
