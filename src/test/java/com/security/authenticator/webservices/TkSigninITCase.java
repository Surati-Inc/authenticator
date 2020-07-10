package com.security.authenticator.webservices;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.takes.Take;
import org.takes.http.FtRemote;
import org.takes.http.FtRemote.Script;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.jcabi.http.Request;
import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.RestResponse;
import com.security.authenticator.web.TkApp;

/**
 * Test case for {@link TkSignin}
 * @author Olivier B. OURA (baudoliver7@gmail.com)
 *
 */
public class TkSigninITCase {
	
	/**
	 * TkSignin can generate a token for User user with true password pwd
	 * @throws Exception If some problem inside
	 */
	@Test
    public void generateTokenTest() throws Exception {
		
		final Take app = new TkApp();
		
		new FtRemote(app).exec(
			new Script() {			
				@Override
				public void exec(URI home) throws Exception {
					
					final Request request = new JdkRequest(home);
			        final String body = "{\"login\": \"user\", \"password\": \"pwd\"}";
			        try(
			        		final InputStream bodyStream = new ByteArrayInputStream(body.getBytes());
			        ){			        	
			        	request.uri().path("token").back()
				               .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
				               .method(Request.POST)
				               .fetch(bodyStream)
				               .as(RestResponse.class)
				               .assertStatus(HttpURLConnection.HTTP_CREATED)
				               .assertBody(Matchers.equalTo("{\"token\":\"eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1OTQzMzkyMDAsImlzcyI6IkF1dGhlbnRpY2F0b3IiLCJzdWIiOiJ1c2VyIn0.00DCCosD4yOpjSGwxcIU5RSBTCd1oZ5adWlzcZhjtrdIQgA7YbsZepOl_v94IfKD1exDC_98Re4aC1UsWYFogQ\"}"));
			        }			        	        			      
				}
		});        
    }
	
	/**
	 * TkSignin can fail for User user with wrong password pwd1
	 * @throws Exception If some problem inside
	 */
	@Test
    public void generateTokenFailsTest() throws Exception {
		
		final Take app = new TkApp();
		
		new FtRemote(app).exec(
			new Script() {			
				@Override
				public void exec(URI home) throws Exception {
					
					final Request request = new JdkRequest(home);
			        final String body = "{\"login\": \"user\", \"password\": \"pwd1\"}";
			        try(
			        	final InputStream bodyStream = new ByteArrayInputStream(body.getBytes());
			        ){
			        	request.uri().path("token").back()
				               .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
					           .method(Request.POST)
					           .fetch(bodyStream)
					           .as(RestResponse.class)
					           .assertStatus(HttpURLConnection.HTTP_BAD_REQUEST)
					           .assertBody(Matchers.equalTo("{\"message\":\"Login or password is invalid !\"}"));
			        }			        			        			        		        			    
				}
		});        
    }
}
