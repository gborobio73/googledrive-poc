package servlet;

import java.io.IOException;
import java.util.Collections;

import javax.jdo.JDOHelper;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.jdo.auth.oauth2.JdoCredentialStore;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class DriveServletSample extends AbstractAuthorizationCodeServlet {
	private static String CLIENT_ID = "782217327038-f4j1lbb7gb9kpq1l7uaa3k844ub5hg12.apps.googleusercontent.com";
	  private static String CLIENT_SECRET = "DI54P-kGJO_PWZtYv073daKn";
	  @Override
	  protected void doGet(HttpServletRequest request, HttpServletResponse response)
	      throws IOException {
	    // do stuff
	  }

	  @Override
	  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
	    GenericUrl url = new GenericUrl(req.getRequestURL().toString());
	    url.setRawPath("/oauth2callback");
	    return url.build();
	  }

	  @Override
	  protected AuthorizationCodeFlow initializeFlow() throws IOException {
	    return new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), new JacksonFactory(),
	        "CLIENT_ID", "CLIENT_SECRET",
	        Collections.singleton(DriveScopes.DRIVE)).setCredentialStore(
	        new JdoCredentialStore(JDOHelper.getPersistenceManagerFactory("transactions-optional")))
	        .build();
	  }

	  @Override
	  protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
		  UserService userService = UserServiceFactory.getUserService();
		  return userService.getCurrentUser().getUserId();  
	  }
	}