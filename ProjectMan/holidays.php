<!DOCTYPE html>
<html>
    <head>
        <title>ProjectMan</title>
        <meta charset="UTF-8"/>
        <meta name="keywords" content="project management, kpi, labs, student work"/>
        <meta name="description" content="Веб сайт з практичною частиною лабораторних робіт з курсу Проектного менеджменту."/>
        <link rel="icon" href="res/favicon.ico" />
        <link rel="stylesheet" href="styles/style.css" type="text/css"/>
    </head>
    <body>
        <div id="wrapper"> 
            <div id="header">
                
            </div>
        <ul id="nav">
            <li class="linav"><a href="index.php" class="nav">Головна</a></li>
            <li class="linav"><a href="L1.php" class="nav">Лабораторна робота 1.</a></li>
            <li class="linav"><a href="L2.php" class="nav">Лабораторна робота 2.</a></li>
            <li class="linav"><a href="holidays.php" class="nav">Свята.</a></li>
        </ul>
            <br>
            <div id="content">
                <p><a href="holidays.php" class="content"><b>Свята.</b></a></p>
                
                <form action="holidays.php?add=true" method="post">
                    <p>Назва: <input size="30" type="text" name="name" /></p>
                    <p>Місяць(1-12): <input type="text" name="month" /></p>
                    <p>День(1-31): <input  type="text" name="day" /></p>
                    <input id="button" type="submit" value="Додати вихідний"/>
                </form>

            <?php
                include_once 'functions.php';
            
                if (isset($_GET['add'])) {
                    addHoliday();                 
                    header('Location: holidays.php');
                }
                if (isset($_GET['removeLine'])) {
                    removeLine3($_POST['line']);
                    header('Location: holidays.php');
                }
                
                echo '<table border="1"><tr>';
                echo '<td>Назва</td>';
                echo '<td>Місяць</td>';
                echo '<td>День</td></tr>';
                $table = loadHolidays();
                foreach ($table as $m => $month) {
                    foreach ($month as $d => $dates) {
                        if ($d == 'info') {
                            continue;
                        }
                        echo '<tr><td>';
                        echo $month['info'][$d];
                        echo '</td><td>';
                        echo $m;
                        echo '</td><td>';
                        echo $d;
                        echo '</td><td>';
                        echo '<form action="holidays.php?removeLine=true" method="post">';
                        echo '<input id="button" type="submit" name="line" '
                        . 'value="Видалити рядок '.$d.'-'.$m.'"/>';
                        echo '</form>';
                        echo '</td></tr>';
                    }
                }
                echo '<table>';
            ?>
            </div>
            <div id="footer">
                <p>Копірайт © 2013-2014 Вербовий Андрій</p>
            </div>
        </div>
    </body>
</html>