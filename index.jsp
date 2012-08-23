<%-- 
    Document   : index
    Created on : Aug 22, 2012, 3:46:58 PM
    Author     : sushil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Shadows Into Light Two">
        <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Didact Gothic">
        <style type="text/css">
            body{
                font-family:Didact Gothic;
                    background: #eeeeee; /* Old browsers */
background: -moz-linear-gradient(top,  #eeeeee 0%, #eeeeee 100%); /* FF3.6+ */
background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#eeeeee), color-stop(100%,#eeeeee)); /* Chrome,Safari4+ */
background: -webkit-linear-gradient(top,  #eeeeee 0%,#eeeeee 100%); /* Chrome10+,Safari5.1+ */
background: -o-linear-gradient(top,  #eeeeee 0%,#eeeeee 100%); /* Opera 11.10+ */
background: -ms-linear-gradient(top,  #eeeeee 0%,#eeeeee 100%); /* IE10+ */
background: linear-gradient(to bottom,  #eeeeee 0%,#eeeeee 100%); /* W3C */
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#eeeeee', endColorstr='#eeeeee',GradientType=0 ); /* IE6-9 */


            }
            h1{
                font-family: Shadows Into Light Two;
                 
            }
            a{
                color:black;
                text-decoration: none;
            }
            a:visited{
                color:black;
                text-decoration: none;
            }
            a:hover{
                color:black;
                text-decoration: underline;
            }
            
            
        </style>
        <title>YTGrabber-Youtube video download API</title>
        
    </head>
    <body>
        <div align="center" class="header">
        <h1>YTGrabber</h1>
        <text>Youtube video download API</text>
        </div>
        <div class="inner">
            </br>
            <p>YTGrabber lets you integrate Youtube video download feature in your application.The RESTFul nature of the
                api and JSON output format makes it easy to integrate with any programmig language.
            </p>
            
            <h2 align="center">API Call Method</h2>
            <p align="center">
                Do a HTTP POST or GET on <span class="url">http://projects-sushilkumar.rhcloud.com/YTGrabber</span> with 
                following parameters.
               
                
            </p>
            <table align="center" border="3" >
                <tr>
                    <td><b>Parameter</b></td>
                    <td><b>Description</b></td>
                </tr>
                <tr>
                    <td>url</td>
                    <td>youtube url of the form http://youtube.com/watch?v=somevideoid</td>
                </tr>
                <tr>
                    <td>format</td>
                    <td>currently it supports only JSON but in future it will support XML too</td>
                </tr>
            </table>
            </br>
            <a align="center" target="_blank" href="http://projects-sushilkumar.rhcloud.com/YTGrabber?url=http://youtube.com/watch?v=nYyTENkOrmc&feature=g-all-lik"><h3>Sample Response</h3></a>
            
                
            </p>
            
            <h3 align="center">A web application based on this API soon...;)</h3>
        </div>
        
    </body>
</html>
