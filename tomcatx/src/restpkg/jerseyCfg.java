package restpkg;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;


//    restpkg.jerseyCfg
@ApplicationPath("/")
public class jerseyCfg  extends ResourceConfig{
	

	 

	  public jerseyCfg() {
        packages("restpkg");
	    register(MultiPartFeature.class);
	    register(jerserMeth.class);
	  }
 

}
