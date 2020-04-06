package restpkg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.attilax.mvc.MvcUtil;

public class jerserMeth {
	
	
	   //   http://localhost/services/t?aaa  
	 
	@Path("/t")
	public String get(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		MvcUtil.outputHtml(httpServletResponse, "hhh");
	    return "thing";
	}

}
