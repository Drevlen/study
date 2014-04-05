<?xml version="1.0" encoding="UTF-8"?>
<!-- Edited by XMLSpy® -->
<html xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>ChosenVille</title>
        <meta charset="UTF-8"/>
        <meta name="keywords" content="ukraine, cities,  rating, top,  live"/>
        <meta name="description" content="Міста України для проживання."/>
        <link rel="icon" href="res/favicon.ico" />
        <link rel="stylesheet" href="style/style.css" type="text/css"/>
    </head>
        
  <body style="height: 100%; width: 100%; background-color: #ffffcc;">
        <div id="header"></div>
        <div id ="nav"><ul>
            <li class="linav"><a href="index.php" class="nav">Головна</a></li>
            <li class="linav"><a href="turist.php" class="nav">Варто відвідати</a></li>
            <li class="linav"><a href="chose.php" class="nav">Вибір міста проживання</a></li>
            <li class="linav"><a href="form.php" class="nav">Опитування та відгуки</a></li>
            <li class="linav"><a href="registration.php" class="nav">Реєстрація</a></li>
        </ul></div>
        <br></br>
        <div id="content">
            <xsl:for-each select="breakfast_menu/food">
              <div style="background-color:teal;color:white;padding:4px">
                <span style="font-weight:bold"><xsl:value-of select="name"/></span>
                - <xsl:value-of select="price"/>
              </div>
              <div style="margin-left:20px;margin-bottom:1em;font-size:10pt">
                <p><xsl:value-of select="description"/>.
                <span style="font-style:italic">
                  <xsl:value-of select="calories"/> (calories per serving)
                </span>.</p>
              </div>
            </xsl:for-each>
        </div>
  </body>
</html>


<!--

    Document   : newstylesheet.xsl
    Created on : January 13, 2014, 10:24 AM
    Author     : drevlen
    Description:
        Purpose of transformation follows.


<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

     TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    
    <xsl:template match="/">
        <html>
            <head>
                <title>newstylesheet.xsl</title>
            </head>
            <body>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>-->
