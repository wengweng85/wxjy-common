package com.insigma.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  敏感关键字过滤
 * */
public class SensitiveWordFilter extends HttpServlet implements Filter {
	/**
	   *
	 */
	private static final long serialVersionUID = 5286703103846683570L;
	

	@Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterchain) throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest) servletRequest;
	     HttpServletResponse response = (HttpServletResponse) servletResponse;
        /**	
         * 关键字过滤
         **/
	     SensitiveWordFilterHttpServletRequestWrapper safeRequest = new SensitiveWordFilterHttpServletRequestWrapper(request);
         filterchain.doFilter(safeRequest, servletResponse);
	}
	



	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
