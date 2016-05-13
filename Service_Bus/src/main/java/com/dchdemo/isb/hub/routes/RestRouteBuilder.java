/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dchdemo.isb.hub.routes;


import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.commons.lang3.StringUtils;

import com.dchdemo.isb.hub.beans.Policy;
import com.dchdemo.isb.hub.config.ServiceBusConfigFactory;
import com.dchdemo.isb.hub.config.ServiceBusConfigs;
import com.dchdemo.isb.hub.dynamics.AccountsBean;
import com.dchdemo.osp.acmeinc.dbutil.Customer;


public class RestRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
    	
    	@SuppressWarnings("deprecation")
		ServiceBusConfigs ctzConf = (
    								( ServiceBusConfigFactory ) getContext()
    									.getRegistry()
    									.lookup( "configFactory", ServiceBusConfigFactory.class )
    								)
    									.getConfig();
    	
        initSparkRest();
        
        staticContentRoute();
        
        getPolicyRoute(ctzConf);
        
        getMsDynamicsRoute();
        
        configRoutes();
    }
    
    private void initSparkRest(){
    	
    	String portNo = System.getenv("PORT");
    	if( StringUtils.isEmpty(portNo) ){
    		portNo = "8080";
    	}
    	
        restConfiguration().component("spark-rest").apiContextPath("api-doc").port( Integer.parseInt(portNo) )
            .bindingMode(RestBindingMode.off)
            .dataFormatProperty("prettyPrint", "true");
    }

    private void staticContentRoute(){
    	
    	//----- static ----
        
        rest("/static/")
        	.get("{pathToFile}")
	        .to("direct:getFile");
        
        from("direct:getFile").process(new Processor() {
        	  public void process(Exchange exchange) throws Exception {
        		  
        	    String uri = exchange.getIn().getHeader("pathToFile").toString();
        	    byte[] fileContent = Files.readAllBytes( Paths.get( "src/main/resources/static/" + uri ) );
        	    
        	    
        	    exchange.getOut().setBody( new String(fileContent) );
        	    
        	    if( uri.endsWith("html") ){
        	    	exchange.getOut().setHeader("Content-Type", "text/html");
        	    	
        	    }else if( uri.endsWith("js") ){
        	    	exchange.getOut().setHeader("Content-Type", "text/javascript");
        	    	
        	    }else if( uri.endsWith("css") ){
        	    	exchange.getOut().setHeader("Content-Type", "text/css");
        	    }
        	    
        	  }
        	});
    }
    
    private void getMsDynamicsRoute(){
    	
	   	 rest("/crm/customer/")
	    		.put()
	    		.to("direct:dynamics-crm-customer-save");
	   	 
	   	 from("direct:dynamics-crm-customer-save")
	    	.unmarshal().json(JsonLibrary.Jackson, Customer.class)
	       	.convertBodyTo(AccountsBean.class)
	       	.to("bean:accountsBeanFactory?method=save");
    }
    
    private void getPolicyRoute( ServiceBusConfigs ctzConf ){
    	
        //----- policy ----
        
        rest("/api/policy/")
        	.get("{polNum}")
        	.to("direct:osp-router-policy");
        	
        from("direct:osp-router-policy")
        	.choice()
        		.when(simple("${header.polNum} > " + ctzConf.getPolicyNumThreshold() ))
        			.to("direct:wayne-ent-api-policy")
        		.otherwise()
        			.to("direct:acme-api-policy");
        
        from("direct:acme-api-policy")
        	.setHeader(Exchange.HTTP_PATH, simple("/api/policy/${header.polNum}"))
        	.setHeader(Exchange.CONTENT_TYPE, simple( MediaType.APPLICATION_JSON ))
        	.to( ctzConf.getAcmeUrl() + "?bridgeEndpoint=true")
        	.unmarshal().json(JsonLibrary.Jackson, com.dchdemo.osp.acmeinc.dbutil.Policy.class)
        	.convertBodyTo(Policy.class)
        	.marshal().json(JsonLibrary.Jackson);
        
        try 
        {
			JAXBContext jcontext = JAXBContext.newInstance(com.dchdemo.osp.wayneent.dbutil.Policy.class);
			JaxbDataFormat jdf = new JaxbDataFormat(jcontext);
			

	        from("direct:wayne-ent-api-policy")
	    		.setHeader(Exchange.HTTP_PATH, simple("/api/policy/${header.polNum}"))
	    		.setHeader("Accept", constant(MediaType.APPLICATION_XML)) 
	    		.to( ctzConf.getWayneEntUrl() + "?bridgeEndpoint=true")
	    		.unmarshal(jdf)
	    		.convertBodyTo(Policy.class)
	    		.marshal().json(JsonLibrary.Jackson)
	    		.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON) );
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
        
    }
    
    private void configRoutes(){
    	
        //----- configurations ----
        
        rest("/config").produces("application/json")
        	.get()
        	.to("direct:get-config");
        	
        from("direct:get-config")	
        	.to("bean:configFactory?method=getConfig")
        	.marshal().json(JsonLibrary.Jackson);
    }
}