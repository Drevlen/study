<!DOCTYPE html>
<html>
    <head>
        <title>ChosenVille</title>
        <meta charset="UTF-8"/>
        <meta name="keywords" content="ukraine, cities,  rating, top,  live"/>
        <meta name="description" content="Міста України для проживання."/>
        <link rel="icon" href="res/favicon.ico" />
        <link rel="stylesheet" href="style/style.css" type="text/css"/>
    </head>
    <body>
        <div id="wrapper"> 
            <div id="header"></div>
            
            <div id ="nav"><ul>
                <li class="linav"><a href="index.php" class="nav">Головна</a></li>
                <li class="linav"><a href="turist.php" class="nav">Варто відвідати</a></li>
                <li class="linav"><a href="topToVisit.xml" class="nav">Вибір міста проживання</a></li>
                <li class="linav"><a href="users.xml" class="nav">Користувачі</a></li>
                <li class="linav"><a href="registration.php" class="nav">Реєстрація</a></li>
            </ul></div>
            
            <br>
            <div id="content">
<?php
                echo "<table>";
                $cities = simplexml_load_file('topToVisit.xml');
                for ($i=0; $i<  count($cities); $i++)
                  { 
                  echo "<tr><td>";
                  echo $cities->City[$i]->Name;
                  echo " - ";
                  echo $cities->City[$i]->Region;
                  echo "</td><td>";
                  echo $cities->City[$i]->Population;
                  echo "</td><td>";
                  echo $cities->City[$i]->Description;
                  echo "</td><td><img alt=\"Фото недоступне\" src=\"";
                  echo $cities->City[$i]->Photo;
                  echo "\"></td></tr>";
                  }
                echo "</table>";
?>

<!--                <script>
                if (window.XMLHttpRequest)
                  {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp=new XMLHttpRequest();
                  }
                else
                  {// code for IE6, IE5
                  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                  }
                xmlhttp.open("GET","topToVisit.xml",false);
                xmlhttp.send();
                xmlDoc=xmlhttp.responseXML; 

                echo "<table>");
                var x=xmlDoc.getElementsByTagName("City");
                for (i=0;i<x.length;i++)
                  { 
                  echo "<tr><td>");
                  echo x[i].getElementsByTagName("Name")[0].childNodes[0].nodeValue);
                  echo " - ");
                  echo x[i].getElementsByTagName("Region")[0].childNodes[0].nodeValue);
                  echo "</td><td>");
                  echo x[i].getElementsByTagName("Population")[0].childNodes[0].nodeValue);
                  echo "</td><td>");
                  echo x[i].getElementsByTagName("Description")[0].childNodes[0].nodeValue);
                  echo "</td><td><img alt=\"Фото недоступне\" src=\"");
                  echo x[i].getElementsByTagName("Photo")[0].childNodes[0].nodeValue);
                  echo "\"></td></tr>");
                  }
                echo "</table>");
                </script>-->
                
            </div>
            <div id="footer">
                <p>Копірайт © 2013-2014 Вербовий Андрій</p>
            </div>
        </div>
    </body>
</html>