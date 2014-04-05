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
                <li class="linav"><a href="topToVisit.xml" class="nav">Вибір міста проживання</a></li>
                <li class="linav"><a href="users.xml" class="nav">Користувачі</a></li>
                <li class="linav"><a href="registration.php" class="nav">Реєстрація</a></li>
        </ul></div>
        <br></br>
        <div id="content">
            <table>
            <xsl:for-each select="TopToVisit/City">
                <tr>
                    <td>
                        <span style="font-weight:bold">
                            <xsl:value-of select="Name"/>
                        </span>
                        - <xsl:value-of select="Region"/>
                    </td>
                    <td>
                        <xsl:value-of select="Population"/>
                    </td>
                    <td>
                        <xsl:value-of select="Description"/>
                    </td>
                    <td>
                        <img>
                            <xsl:attribute name="alt">
                                    Фото недоступне
                            </xsl:attribute>
                            <xsl:attribute name="src">
                                    <xsl:value-of select="Photo"/>
                            </xsl:attribute>
                        </img>
                    </td>
                </tr>
            </xsl:for-each>
            </table>
        </div>
        <div style="bottom: 0; width: 100%; font-size: 14px; color: darkblue;">
            <p style="text-align: center;">
                Копірайт © 2013-2014 Вербовий Андрій
            </p>
        </div>
  </body>
</html>