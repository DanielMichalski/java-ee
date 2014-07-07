package com.webapp.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Author: Daniel
 */
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

//        showText(req, resp);
//        showImage(resp);
//        createCounter(req, resp, resp.getWriter());
//        setServletAttribute(req, resp);
        filter(req, resp);
    }

    private void filter(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        ServletContext context = getServletContext();
        writer.println("Serwlet");
    }

    private void setServletAttribute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        ServletContext context = getServletContext();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html; charset=utf-8");
        PrintWriter writer = resp.getWriter();

        String imie = req.getParameter("imie");
        if (imie != null && !"".equals(imie)) {
            Cookie cookie = new Cookie("imie", imie);
            cookie.setMaxAge(3600);
            resp.addCookie(cookie);
            writer.println("Witaj " + imie);
        }

    }

    private void createCounter(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer)
            throws IOException {

        resp.setContentType("text/html; charset=utf-8");
        HttpSession session = req.getSession();
        Integer licznik = (Integer) session.getAttribute("licznik");

        if (licznik == null) {
            licznik = 0;
        }
        licznik++;

        session.setAttribute("licznik", licznik);
        writer.println("Licznik: " + licznik);

    }

    private void showText(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html; charset=utf-8");
        PrintWriter writer = resp.getWriter();

        if (req.getCookies() != null) {
            for (int i = 0; i < req.getCookies().length; i++) {
                Cookie cookie = req.getCookies()[i];
                if (cookie.getName().equals("imie")) {
                    writer.println("Witaj " + cookie.getValue());
                    break;
                }
            }
        }

        writer.println("<html><head><title>Web app</title></head>");
        writer.println("<body>");
        writer.println("<p>Welcome</p>");
        writer.println("<p>Drugi wiersz</p>");
        writer.println("<form method=\"post\">");
        writer.println("<p>Podaj imie:</p>");
        writer.println("<input name=\"imie\" /><input type=\"submit\" value=\"OK\" />");
        writer.println("</form>");
        writer.println("</body></html>");
    }

    private void showImage(HttpServletResponse resp) throws IOException {
        byte[] bufor = wczytajPlik("web.jpg");
        resp.setContentType("image/jpg");
        resp.setContentLength(bufor.length);
//        wyswietlanie obrazka w przegladarce lub udostępnienie pobierania
//        resp.addHeader("Content-Disposition", "attachment;filename=web.jpg");
        ServletOutputStream os = resp.getOutputStream();
        os.write(bufor);
        os.flush();
    }

    private byte[] wczytajPlik(String nazwaPliku)
            throws IOException {

        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream(nazwaPliku);
        long length = resourceAsStream.available();
        byte[] content = new byte[(int)length];
        resourceAsStream.read(content);
        resourceAsStream.close();
        return content;
    }
}
