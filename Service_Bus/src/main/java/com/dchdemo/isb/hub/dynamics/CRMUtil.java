package com.dchdemo.isb.hub.dynamics;

import java.net.URI;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.naming.ServiceUnavailableException;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientAssertion;

import net.minidev.json.JSONObject;

public class CRMUtil {

	private static String DYNAMICS_CONFIG = "dynamics_config.properties";
	private static Properties crmConfig = new Properties();
	
	//Azure Application Client ID
    private static String CLIENT_ID;
    
    //CRM URL
    private static String RESOURCE;
    
    //O365 credentials for authentication w/o login prompt
    private static String USERNAME;
    private static String PASSWORD;
  
    //Azure Directory OAUTH 2.0 AUTHORIZATION ENDPOINT
    private static String AUTHORITY;
    
    private static String accessToken;
    private static String refreshToken;
    private static Date expiresOn;
    
    static {
    	try {
    		
    		crmConfig.load( CRMUtil.class.getClassLoader().getResourceAsStream( DYNAMICS_CONFIG ) );
    		CLIENT_ID = crmConfig.getProperty("CLIENT_ID");
    		RESOURCE = crmConfig.getProperty("RESOURCE");
    		USERNAME = crmConfig.getProperty("USERNAME");
    		PASSWORD = crmConfig.getProperty("PASSWORD");
    		AUTHORITY = crmConfig.getProperty("AUTHORITY");
    		 
    		synchronized (DYNAMICS_CONFIG) 
    		{
	    		AuthenticationResult authResult = getAccessTokenFromUserCredentials();
				accessToken = authResult.getAccessToken();
				refreshToken = authResult.getRefreshToken();
				expiresOn = authResult.getExpiresOnDate();
    		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private static AuthenticationResult getAccessTokenFromUserCredentials() throws Exception {
    	
        AuthenticationContext context = null;
        AuthenticationResult result = null;
        ExecutorService service = null;
        
        try 
        {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(AUTHORITY, false, service);
            Future<AuthenticationResult> future = context.acquireToken( RESOURCE, CLIENT_ID, USERNAME, PASSWORD, null);
            
            result = future.get();
            
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException("authentication result was null");
        }
        
        return result;
    }
    
    private static AuthenticationResult getAccessTokenFromRefreshToken() throws Exception {
    	
        AuthenticationContext context = null;
        AuthenticationResult result = null;
        ExecutorService service = null;
        
        try 
        {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(AUTHORITY, false, service);
            
            //TODO - fix the below code
            Future<AuthenticationResult> future = context.acquireTokenByRefreshToken( refreshToken, CLIENT_ID, null, RESOURCE, null  );
            result = future.get();
            
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException("authentication result was null");
        }
        
        return result;
    }
    
    public static String getAccessToken() throws Exception{
    	
    	Date now = new Date( GregorianCalendar.getInstance().getTimeInMillis() );
    	if( now.after( expiresOn ) ){
    		
    		synchronized (DYNAMICS_CONFIG) {
			
	    		AuthenticationResult authResult = getAccessTokenFromRefreshToken();
				accessToken = authResult.getAccessToken();
				refreshToken = authResult.getRefreshToken();
				expiresOn = authResult.getExpiresOnDate();	
			}
    	}
    	
    	return accessToken;
    }
    
    public static int saveData ( JSONObject jsonObj, String uri ) throws Exception {
    	
    	CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPatch httpPatch = new HttpPatch( new URI( RESOURCE + uri) );
        httpPatch.setHeader("OData-MaxVersion", "4.0");
        httpPatch.setHeader("OData-Version", "4.0");
        httpPatch.setHeader("Accept", MediaType.APPLICATION_JSON);
        httpPatch.setHeader("Authorization", "Bearer " + getAccessToken() );
        httpPatch.setHeader("Content-Type", MediaType.APPLICATION_JSON );
        
        HttpEntity entity = new StringEntity( jsonObj.toJSONString() );
        httpPatch.setEntity(entity);
        
        CloseableHttpResponse response = httpClient.execute(httpPatch);
        
        return response.getStatusLine().getStatusCode();
    	
    }
	
}
