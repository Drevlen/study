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
                <p><a href="L1.php" class="content">Лабораторна робота №1. 
                        <b>Структурне Планування</b></a></p>
                <form action="L1.php?project=true" method="post">
                    <p>Назва Проекту: <input type="text" name="Pname" /></p>
                   <a href="L1.php?project=true>"> <button id="button" type="submit">
                            Завантажити</button>  </a>
                    <a href="L1.php?project=true>?"> <button id="button" type="reset">
                            Видалити</button> </a>
                </form>
                        <?php
                            include_once 'functions.php';
                            echo '<div id="ProjectName">';
                            echo project();
                            echo '</div>';
                        ?>
                        <br>
                <form action="L1.php?addLine=true" method="post">
                    <p>Назва Роботи: <input size="30" type="text" name="name" /></p>
                    <p>Тривалість(дні): <input type="text" name="time" /></p>
                    <p>Попередники: <input  type="text" name="depend" /></p>
                    <input id="button" type="submit" value="Додати рядок"/>
                    <a href="L1.php?reset=true>"> <button id="button" type="reset">
                            Очистити таблицю</button>  </a>
                    <a href="L1.php?compute=true>"> <button id="button" type="button">
                            Знайти критичний шлях</button> </a>
                </form>
                    <br>
                    <?php
                    include_once 'functions.php';
                    if ( ! file_exists('res/CurrentProject.txt')) {
                        file_put_contents('CurrentProject.txt','Project');
                    }
                    if (isset($_GET['project'])) {
                        if (isset($_POST['Pname'])) {
                            if ($_POST['Pname'] == "") {
                                setProject("Project");
                            } else {
                                setProject($_POST['Pname']);
                            }
                        } else {
                            counter1('reset');
                            setProject("Project");
                        }
                        header('Location: L1.php');
                    }
                    
                    if ( ! file_exists('res/'.project().'count1.txt')) {
                        file_put_contents('res/'.project().'count1.txt','0');
                    }
                    if (isset($_GET['addLine'])) {
                        counter1('addLine');
                        if (isset($_POST['name']))
                            addToTable1();
                        header('Location: L1.php');
                    }
                    if (isset($_GET['reset'])) {
                        counter1('reset');
                        header('Location: L1.php');
                    }
                    if (isset($_GET['compute']) || isset($_GET['sort'])) {
                        $times = drawGraph1();
                        $isTime = true;
                    }
                    if (isset($_GET['removeLine'])) {
                        removeLine1($_POST['line']);
                        header('Location: L1.php');
                    }
                    if (isset($_GET['editLine'])) {
                        removeLine1($_POST['line']);
                        header('Location: L1.php');
                    }
                    
                        echo '<br>';
                        
                        
                                                $table = readTable1();
                        for ($i = 0; $i < 4 * counter1('getCount'); $i ++) {
                            $newtable[$i % 4][$i / 4] = $table[$i];
                        }

                        if (isset($_GET['sort'])) {
                            echo $_GET['sort'];
                            if ($_GET['sort'] == 'num') {
                                array_multisort($newtable[0], $newtable[1], 
                                        $newtable[2], $newtable[3], $times[0], 
                                        $times[1], $times[2]);
                            } elseif ($_GET['sort'] == 'name') {
                                array_multisort($newtable[1], $newtable[0], 
                                        $newtable[2], $newtable[3], $times[0], 
                                        $times[1], $times[2]);
                            } elseif ($_GET['sort'] == 'time') {
                                echo 'here';
                                array_multisort($newtable[2], $newtable[1], 
                                        $newtable[0], $newtable[3], $times[0], 
                                        $times[1], $times[2]);
                            } elseif ($_GET['sort'] == 'pred') {
                                array_multisort($newtable[3], $newtable[1], 
                                        $newtable[0], $newtable[2], $times[0], 
                                        $times[1], $times[2]);
                            } elseif ($_GET['sort'] == 'early') {
                                array_multisort( $times[0], $newtable[2], $newtable[1], 
                                        $newtable[0], $newtable[3],
                                        $times[1], $times[2]);
                            } elseif ($_GET['sort'] == 'late') {
                                array_multisort($times[1], $newtable[2], $newtable[1], 
                                        $newtable[0], $newtable[3], $times[0], 
                                        $times[2]);
                             } elseif ($_GET['sort'] == 'reserv') {
                                array_multisort($times[2], $newtable[2], $newtable[1], 
                                        $newtable[0], $newtable[3], $times[0], 
                                        $times[1]);
                             }
                        } else {
                                array_multisort($newtable[0], $newtable[1], 
                                        $newtable[2], $newtable[3], $times[0], 
                                        $times[1], $times[2]);
                        }
                        
                        echo '<table border="1"><tr>';
                        echo '<td><a href="L1.php?sort=num">№</a></td>';
                        echo '<td><a href="L1.php?sort=name">Назва Роботи</a></td>';
                        echo '<td><a href="L1.php?sort=time">Тривалість</a></td>';
                        echo '<td><a href="L1.php?sort=pred">Попередники</a></td>';
                        if ($isTime)
                        {
                            echo '<td><a href="L1.php?sort=early">Ранній час початку</a></td>';
                            echo '<td><a href="L1.php?sort=late">Пізній час початку</a></td>';
                            echo '<td><a href="L1.php?sort=reserv">Резерв</a></td>';                            
                        }
                        echo '</tr>';
                    
                    for ($i = 0; $i < counter1('getCount'); $i ++) {
                        echo '<form action="L1.php?editLine=true" method="post">';
                        echo '<tr><td><input size="3" type="text" name="id" value="';
                        echo $newtable[0][$i];
                        echo '" /></td><td><input size="30" type="text" name="name" value="';
                        echo $newtable[1][$i];
                        echo '" /></td><td><input size="10" type="text" name="time" value="';
                        echo $newtable[2][$i];
                        echo '" /></td><td><input size="10" type="text" name="depend" value="';
                        echo $newtable[3][$i];
                        echo '" />';
                        if ($isTime) {
                            echo '</td><td>';
                            echo $times[0][$i];
                            echo '</td><td>';
                            echo $times[1][$i];
                            echo '</td><td>';
                            echo $times[2][$i];
                        }
                        echo '</td><td>';
                        echo '<input id="button" type="submit" name="line" '
                        . 'value="Редагувати рядок '.$newtable[0][$i].'"/>';
                        echo '</form></td><td>';
                        echo '<form action="L1.php?removeLine=true" method="post">';
                        echo '<input id="button" type="submit" name="line" '
                        . 'value="Видалити рядок '.$newtable[0][$i].'"/>';
                        echo '</form>';
                        echo '</td></tr>';
                    }
                    echo '</table><br>';
                    if ($times) {
                    echo '<img src="res/'.project().'myStructGraph.png" alt="Структура Проекту"/>';
                    }
?> 
            </div>
            <div id="footer">
                <p>Копірайт © 2013-2014 Вербовий Андрій</p>
            </div>
        </div>
    </body>
</html>