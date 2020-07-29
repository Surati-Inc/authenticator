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
import com.security.authenticator.webservices.TkValidateToken;

/**
 * Test case for {@link TkValidateToken}
 * @author Olivier B. OURA (baudoliver7@gmail.com)
 *
 */
public class TkValidateTokenITCase {
	
	/**
	 * TkValidateToken can validate true token
	 * @throws Exception If some problem inside
	 */
	@Test
    public void validateTokenTest() throws Exception {
		
		final Take app = new TkApp();
		
		new FtRemote(app).exec(
			new Script() {			
				@Override
				public void exec(URI home) throws Exception {
					
					final Request request = new JdkRequest(home);
			        final String body = "token=eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1OTQzMzkyMDAsImlzcyI6IkF1dGhlbnRpY2F0b3IiLCJzdWIiOiJ1c2VyIn0.00DCCosD4yOpjSGwxcIU5RSBTCd1oZ5adWlzcZhjtrdIQgA7YbsZepOl_v94IfKD1exDC_98Re4aC1UsWYFogQ";
			        try(
			        	final InputStream bodyStream = new ByteArrayInputStream(body.getBytes());
			        ){
			        	request.uri().path("token/validate").back()
					           .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
						       .method(Request.POST)
						       .fetch(bodyStream)
						       .as(RestResponse.class)
						       .assertStatus(HttpURLConnection.HTTP_ACCEPTED)
						       .assertBody(Matchers.equalTo("{\"login\":\"user\"}"));
			        }			        			        			      
				}
		});        
    }
	
	/**
	 * TkValidateToken can fail to validate wrong token
	 * @throws Exception If some problem inside
	 */
	@Test
    public void validateTokenFailsTest() throws Exception {
		
		final Take app = new TkApp();
		
		new FtRemote(app).exec(
			new Script() {			
				@Override
				public void exec(URI home) throws Exception {
					
					final Request request = new JdkRequest(home);
					// token of subject user1 (not registered)
					
			        final String body = "token=eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTk0MjUyODAwLCJpc3MiOiJBdXRoZW50aWNhdG9yIiwic3ViIjoidXNlcjEifQ.YHAEbnrFqi2616je__XLtj0ZdfVsVNWu0jLbw_qtIuTC0-9xbnZ60mf2zG_7p5wXomWvgbr6YVXp_tY9357Fvg";
			        try(
			        	final InputStream bodyStream = new ByteArrayInputStream(body.getBytes());
			        ){
			        	request.uri().path("token/validate").back()
				               .header(HttpHeaders.CONTENT_LENGTH, MediaType.APPLICATION_FORM_URLENCODED)
					           .method(Request.POST)
					           .fetch(bodyStream)
					           .as(RestResponse.class)
					           .assertStatus(HttpURLConnection.HTTP_BAD_REQUEST)
					           .assertBody(Matchers.equalTo("{\"message\":\"Token is invalid !\"}"));
			        }			        			        			        		        			    
				}
		});        
    }
}
