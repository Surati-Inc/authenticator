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
import com.security.authenticator.webservices.TkSignin;

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
		
		final Take app = new TkApp("https://www.surati.io");
		
		new FtRemote(app).exec(
			new Script() {			
				@Override
				public void exec(URI home) throws Exception {
					
					final Request request = new JdkRequest(home);
					final String body = "login=user&password=pwd";
			        try(
			        		final InputStream bodyStream = new ByteArrayInputStream(body.getBytes());
			        ){			        	
			        	request.uri().path("token").back()
				               .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
				               .method(Request.POST)
				               .fetch(bodyStream)
				               .as(RestResponse.class)
				               .assertStatus(HttpURLConnection.HTTP_CREATED)
				               .assertBody(Matchers.equalTo("{\"token\":\"eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1OTQ1MTIwMDAsImlzcyI6IkF1dGhlbnRpY2F0b3IiLCJzdWIiOiJ1c2VyIn0.XlMqNk3VMzOdsangfn7Pxgo0EQpP4Oag0dMXEy_2JI9LyzFTKNLysONoPj4t99dJwrXtlP9IbicyD3CPkphsjg\"}"));
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
		
		final Take app = new TkApp("https://www.surati.io");
		
		new FtRemote(app).exec(
			new Script() {			
				@Override
				public void exec(URI home) throws Exception {
					
					final Request request = new JdkRequest(home);
			        final String body = "login=user&password=pwd1";
			        try(
			        	final InputStream bodyStream = new ByteArrayInputStream(body.getBytes());
			        ){
			        	request.uri().path("token").back()
				               .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
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
