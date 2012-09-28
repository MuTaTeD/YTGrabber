/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.uni.sushilkumar.ytgrabber;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author sushil
 */
public class YTGrabber extends HttpServlet {
    private String title=null;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String url=request.getParameter("url");
        try {
            if(url!=null)
            {
                if(url.indexOf("watch")<0)
                {
                    String json=generateMalformedUrlException();
                    out.println(json);
                }
                
                else
                {
                url=filterUrl(url);
                String markup=getMarkup(url);
                
                String json=extractLinks(markup);
                out.println(json);
                }
            }
            else
            {
                String json=generateNoUrlException();
                out.println(json);
            }
        } finally {            
            out.close();
        }
    }
    
    public String filterUrl(String url)
    {
        int index=url.indexOf("&");
        if(index>=0)
        url=url.substring(0,index);
        return url;
    }
    
    public String getMarkup(String url) throws IOException
    {
        int index1=0,index2=0;
        Document doc=Jsoup.connect(url).get(); 
        title=doc.title();
        title= title.replaceAll("[^a-zA-Z0-9]+","");
        String data=doc.outerHtml();
        index1=data.indexOf("var swf =");
        index2=data.indexOf("document.getElementById('watch-player').innerHTML = swf");
        data=data.substring(index1+8,index2);
        index1=data.indexOf("url_encoded_fmt_stream_map=");
        data=data.substring(index1+27);
        index2=data.indexOf("u0026amp;");
	data=data.substring(0,index2-1);
        data=URLDecoder.decode(data,"UTF-8");
	
        return data;
    }
    
    public String filterLink(String link)
    {
        int index=0;
		if(link.indexOf("codecs")>=0)
		{
			index=link.indexOf("codecs");
			String temp1=link.substring(0,index-2);
			index=link.indexOf("fallback");
			String temp2=link.substring(index-1);
			link=temp1+temp2;
		}
		

		return link;
    }
    
    public String extractQuality(String link)
    {
        if(link.indexOf("small")>=0)
            return "small(240p)";
        else if(link.indexOf("medium")>=0)
            return "medium(360p)";
        else if(link.indexOf("large")>=0)
            return "large(480p)";
        else if(link.indexOf("hd720")>=0)
            return "HD(720p)";
        else if(link.indexOf("hd1080")>=0)
            return "HD(1080p)";
        return "Average Quality";
    }
    
    public String extractFormat(String link)
    {
        if(link.indexOf("mp4")>0)
            return "mp4";
        else if(link.indexOf("flv")>0)
            return "flv";
        else if(link.indexOf("3gpp")>0)
            return "3gpp";
        return "Format";
    }
    
    public String extractLinks(String data) throws UnsupportedEncodingException
    {
        JSONObject obj=new JSONObject();
        JSONArray array=new JSONArray();
        String current="",left=data;
        while(left.indexOf("url")>=0)
        {
            JSONObject tempObj=new JSONObject();
            int index1=left.indexOf("url");
            int index2=left.indexOf(",");
            if(index2>0)
            {
                current=left.substring(index1+4,index2);
                current=URLDecoder.decode(current,"UTF-8");
                left=left.substring(index2+1);
            }
            else
            {
                current=left.substring(index1+4);
                current=URLDecoder.decode(current,"UTF-8");
                left="";
            }
            
            if(current.indexOf("webm")<0)
            {
                current=filterLink(current);
                current=current.replace('&','!');
                current=replace(current,"sig", "signature");
                tempObj.put("type",extractFormat(current)+" "+extractQuality(current));
                tempObj.put("link","http://projects-sushilkumar.rhcloud.com/YTDownload?url="+current+"&title="+title+"."+extractFormat(current));
                array.add(tempObj);
            }
        }
        obj.put("links",array);
        return obj.toString();
        
        
    }
    
    public String generateNoUrlException()
    {
        JSONObject obj=new JSONObject();
        obj.put("error_no","100");
        obj.put("error","No URL Specified");
        return obj.toJSONString();
        
    }
    
    public String generateMalformedUrlException()
    {
         JSONObject obj=new JSONObject();
        obj.put("error_no","200");
        obj.put("error","Not a valid URL");
        return obj.toJSONString();
    }
    
    static String replace(String str, String pattern, String replace) {
    int s = 0;
    int e = 0;
    StringBuilder result = new StringBuilder();

    while ((e = str.indexOf(pattern, s)) >= 0) {
        result.append(str.substring(s, e));
        result.append(replace);
        s = e+pattern.length();
    }
    result.append(str.substring(s));
    return result.toString();
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
