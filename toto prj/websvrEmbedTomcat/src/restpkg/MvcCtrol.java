package springboothtml;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;


@MyComponent
public class MvcCtrol {
    //  http://localhost:8080/mvctest
	@MyMapping("/mvctest")
	public String mvctestMeth(HttpServletRequest req) {

		return "data";
	}

}
