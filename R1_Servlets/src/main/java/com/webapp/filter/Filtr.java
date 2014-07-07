package com.webapp.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Author: Daniel
 */
public class Filtr implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        String imie = servletRequest.getParameter("imie");
        if (imie != null && imie.equals("jan")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {}
}
