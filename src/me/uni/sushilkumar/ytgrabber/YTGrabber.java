/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.uni.sushilkumar.ytgrabber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author sushil
 */
public class YTGrabber extends HttpServlet {

    private String title = null;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String url = request.getParameter("url");
        try {
            int index = url.indexOf("&");
            if (index >= 0) {
                url = url.substring(0, index);
            }
            index = url.indexOf("=");
            String vid = url.substring(index + 1);
            String json = extractLinks(vid);
            out.println(json);
        } finally {
            out.close();
        }
    }

    public Map<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            String[] values = param.split("=");

            if (values.length > 1) {
                map.put(values[0], values[1]);
            } else {
                map.put(values[0], "NA");
            }


        }
        return map;
    }

    public String getExtension(String type) {
        if (type.toLowerCase().equals("video/webm")) {
            return "webm";
        }
        if (type.toLowerCase().equals("video/x-flv")) {
            return "flv";
        }
        if (type.toLowerCase().equals("video/mp4")) {
            return "mp4";
        }
        if (type.toLowerCase().equals("video/3gpp")) {
            return "3gpp";
        }
        return "format";
    }

    public String getQuality(String quality) {
        if (quality.toLowerCase().equals("small")) {
            return "240p";
        }
        if (quality.toLowerCase().equals("medium")) {
            return "360p";
        }
        if (quality.toLowerCase().equals("large")) {
            return "480p";
        }
        if (quality.toLowerCase().equals("hd720")) {
            return "720p";
        }
        if (quality.toLowerCase().equals("hd1080")) {
            return "1080p";
        }
        return "Normal";
    }

    public String extractLinks(String vid) {
        InputStream inStream = null;
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            URL url = new URL("http://www.youtube.com/get_video_info?video_id=" + vid);
            inStream = url.openStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
            String response = "", temp;
            while ((temp = in.readLine()) != null) {
                response += temp;
            }
            in.close();
            Map<String, String> parameters = getQueryMap(response);
            title = parameters.get("title");
            title = URLDecoder.decode(title, "UTF-8");
            title = title.replaceAll("[^a-zA-Z0-9\\s]+", "");
            System.out.println(title);
            String url_encoded_fmt_stream_map = parameters.get("url_encoded_fmt_stream_map");
            url_encoded_fmt_stream_map = URLDecoder.decode(url_encoded_fmt_stream_map, "UTF-8");
            String[] urls = url_encoded_fmt_stream_map.split(",");
            for (String u : urls) {
                Map<String, String> urlParameters = getQueryMap(u);


                String type = urlParameters.get("type");
                String quality = urlParameters.get("quality");
                type = URLDecoder.decode(type, "UTF-8");
                quality = URLDecoder.decode(quality, "UTF-8");
                int index = type.indexOf(";");
                if (index >= 0) {
                    type = type.substring(0, index);
                }
                //System.out.println(type+"---->"+quality);
                String fileName = title + "." + getExtension(type);
                String ul = urlParameters.get("url") + "&signature=" + urlParameters.get("sig");
                //System.out.println(title + "." + getExtension(type));
                ul = URLDecoder.decode(ul, "UTF-8");
                //ul=ul+ "&title=" +URLEncoder.encode(fileName,"UTF-8");
                JSONObject tempObj = new JSONObject();
                ul = ul.replace("&", "!");
                ul = "http://projects-sushilkumar.rhcloud.com/YTDownload?url=" + ul + "&title=" + URLEncoder.encode(fileName, "UTF-8");
                tempObj.put("link", ul);
                tempObj.put("type", getExtension(type) + "/" + getQuality(quality));
                array.add(tempObj);

            }
            obj.put("links", array);
        } catch (IOException ex) {
            Logger.getLogger(YTGrabber.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inStream.close();
            } catch (IOException ex) {
                Logger.getLogger(YTGrabber.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obj.toJSONString();
    }

    public String generateNoUrlException() {
        JSONObject obj = new JSONObject();
        obj.put("error_no", "100");
        obj.put("error", "No URL Specified");
        return obj.toJSONString();

    }

    public String generateMalformedUrlException() {
        JSONObject obj = new JSONObject();
        obj.put("error_no", "200");
        obj.put("error", "Not a valid URL");
        return obj.toJSONString();
    }

    static String replace(String str, String pattern, String replace) {
        int s = 0;
        int e = 0;
        StringBuilder result = new StringBuilder();

        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e + pattern.length();
        }
        result.append(str.substring(s));
        return result.toString();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
