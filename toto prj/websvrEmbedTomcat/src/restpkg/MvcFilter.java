package restpkg;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.log4j.Logger;

import com.attilax.mvc.MethodObj;
import com.attilax.mvc.MvcUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

//@Component
@WebFilter(urlPatterns = "/*", filterName = "mvcFilter")
//@//WebServlet(urlPatterns = "/*",fil)
public class MvcFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	static Logger logger = Logger.getLogger(MvcFilter.class);
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) {
		try {

			// get url out mapper
			List list = Lists.newArrayList();
			list.add(jerserMeth.class);
			Map<String, MethodObj> url_method_maps = MvcUtil.get_url_out_mapper(list);
			logger.info(url_method_maps);

			HttpServletRequest httpServletRequest = (HttpServletRequest) arg0;
			HttpServletResponse httpServletResponse = (HttpServletResponse) arg1;
			String uri = httpServletRequest.getRequestURI();
			System.out.println(uri);

			if (url_method_maps.get(uri) == null) {
				arg2.doFilter(arg0, arg1);
				return;
			}
			MethodObj MethodObj1 = url_method_maps.get(uri);

			Object classObj = ConstructorUtils.invokeConstructor(MethodObj1.classProp, null);
			Method meth = MethodObj1.methodProp;
			Object ivkrzt = meth.invoke(classObj, httpServletRequest, httpServletResponse);
			// outputHtml(httpServletResponse, r);
			System.out.println("==ivkrzt:" + ivkrzt);
			return; // not to next ,beirs springmvc show err,cause cant this mappering
			// arg2.doFilter(arg0, arg1);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// private List getMycomponentClassList() {
	// List li_allclass=Lists.newArrayList();
	// li_allclass.add(MvcCtrol.class);
	//
	// List li= Lists.newArrayList();
	// classList.add();
	//
	// }

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
