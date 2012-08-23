/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.uni.sushilkumar.ytgrabber;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sushil
 */
public class YTDownload extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title=request.getParameter("title");
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition","attachment; filename=\""+title+"\"");
        
        String url=request.getParameter("url");
        url=url.replace('!','&');
        /*url=url.substring(7);
        url=URLEncoder.encode(url,"UTF-8");
        url="http://"+url;*/
        System.out.println(title);
        URLConnection  con = null;
        
      
        BufferedOutputStream out=new BufferedOutputStream(response.getOutputStream());
        InputStream in=null;
        byte[] buffer;
        int ByteRead,ByteWritten=0;
        try {

            URL dUrl=new URL(url);
            //URL dUrl=new URL("http://o-o---preferred---bharti-del2---v17---lscache7.c.youtube.com/videoplayback?upn=0SERusIEqXQ&sparams=cp%2Cgcr%2Cid%2Cip%2Cipbits%2Citag%2Cratebypass%2Csource%2Cupn%2Cexpire&fexp=907217%2C922401%2C919804%2C920704%2C912806%2C906831%2C911406%2C913550%2C912706&key=yt1&itag=37&ipbits=8&signature=C28F021E5E54DC2B50E84BFB2154F4FD817C6574.85903B32FBA5199D297CF91377B32640836D50A6&mv=m&sver=3&mt=1345687871&ratebypass=yes&source=youtube&ms=au&gcr=in&expire=1345711767&ip=116.203.237.173&cp=U0hTSldMT19LUUNOM19PRlNIOkJKQ1pDWjNzUGE5&id=9d8c9310d90eae67&quality=hd1080&fallback_host=tc.v17.cache7.c.youtube.com&type=video/mp4");
            //URL dUrl=new URL("http://docs.oracle.com/cd/B19306_01/server.102/b14220.pdf");
            con=dUrl.openConnection();
            response.setContentLength(con.getContentLength());
            in=con.getInputStream();
            buffer = new byte[1024];
            while ((ByteRead = in.read(buffer)) != -1)
            {
                
                out.write(buffer, 0, ByteRead);
                ByteWritten += ByteRead;
                
            }

         
            
            
        } finally {            
          //  out.close();
            //in.close();
        }
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
