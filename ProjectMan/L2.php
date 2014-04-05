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
                <p><a href="L2.php" class="content">Лабораторна робота №2. 
                        <b>Календарне Планування</b></a></p>
                        
                <form action="L2.php?project=true" method="post">
                    <p>Назва Проекту: <input type="text" name="Pname" /></p>
                    <a href="L2.php?project=true>"> <button id="button" type="submit">
                            Завантажити</button>  </a>
                    <a href="L2.php?project=true>?"> <button id="button" type="reset">
                            Видалити</button> </a>
                </form>
                        <?php
                            include_once 'functions.php';
                            echo '<div id="ProjectName">';
                            echo project();
                            echo '</div>';
                        ?>
                        <br>
                        
                <form action="L2.php?addLine=true" method="post">
                    <p>Назва Роботи: <input type="text" name="name" /></p>
                    <p>Тривалість(дні): <input type="text" name="time" /></p>
                    <p>Попередники: <input type="text" name="depend" /></p>
                    <p>Виконавці: <input type="text" name="worker" /></p>
                    <p>Зайнятість виконавця: <input type="text" name="usage" /></p>
                    <input id="button" type="submit" value="Додати рядок"/>
                    <a href="L2.php?reset=true>"> <button id="button" type="reset">
                            Очистити таблицю</button>  </a>
                </form><form action="L2.php?compute=true" method="post">
                    <p>Дата початку Проекту(DD-MM-YYYY): <input type="text" name="date" />
                        <input id="button" type="submit" value="Побудова календарного плану"/>
                    </p>
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
                        header('Location: L2.php');
                    }
                    if ( ! file_exists('res/'.project().'count2.txt')) {
                        file_put_contents('res/'.project().'count2.txt','0');
                    }
                    if (isset($_GET['addLine'])) {
                        counter2('addLine');
                        if (isset($_POST['name']))
                            addToTable2();
                        header('Location: L2.php');
                    }
                    if (isset($_GET['reset'])) {
                        counter2('reset');
                        header('Location: L2.php');
                    }
                    if (isset($_GET['removeLine'])) {
                        removeLine2($_POST['line']);
                        header('Location: L2.php');
                    }
                    if (isset($_GET['editLine'])) {
                        removeLine2($_POST['line']);
                        header('Location: L2.php');
                    }
                    if (isset($_GET['compute'])) {
                        $parsedDate = explode('-', $_POST['date']);
                        if ($parsedDate[2] == "") {
                            $startDate['year'] = date('Y');
                            $startDate['month'] = date('m');
                            $startDate['day'] = date('d');
                        } else {
                            $startDate['year'] = $parsedDate[2];
                            $startDate['month'] = $parsedDate[1];
                            $startDate['day'] = $parsedDate[0];
                        }
                        $workers = drawGraph2($startDate);
                    }
                    
                        echo '<br>';
                        echo '<table border="1"><tr>';
                        echo '<td>№</td>';
                        echo '<td>Назва Роботи</td>';
                        echo '<td>Тривалість</td>';
                        echo '<td>Попередники</td>';
                        echo '<td>Працівники</td>';
                        echo '<td>Зайнятість</td>';
                        echo '</tr>';
                    $table = readTable2();
                    for ($i = 1; $i <= counter2('getCount'); $i++) {
                        echo '<tr><td>';
                        echo '<form action="L2.php?editLine=true" method="post">';
                        echo '<tr><td><input size="3" type="text" name="id" value="';
                        echo $table[$i][0];
                        echo '" /></td><td><input size="30" type="text" name="name" value="';
                        echo $table[$i][1];
                        echo '" /></td><td><input size="10" type="text" name="time" value="';
                        echo $table[$i][2];
                        echo '" /></td><td><input size="10" type="text" name="depend" value="';
                        echo $table[$i][3];
                        echo '" /></td><td><input size="30" type="text" name="worker" value="';
                        echo $table[$i][4];
                        echo '" /></td><td><input size="10" type="text" name="usage" value="';
                        echo $table[$i][5];
                        echo '" /></td><td>';
                        
                        echo '<input id="button" type="submit" name="line" '
                        . 'value="Редагувати рядок '.$table[$i][0].'"/>';
                        echo '</form></td><td>';
                        
                        echo '<form action="L2.php?removeLine=true" method="post">';
                        echo '<input id="button" type="submit" name="line" '
                        . 'value="Видалити рядок '.$table[$i][0].'"/>';
                        echo '</form></td></tr>';
                    }
                    echo '<table><br>';
                    
                    if ($workers && FALSE) {
                        foreach ($workers as $i => $worker) {
                            print_r($worker);
                        echo "<br>$i:<br>";
                        echo '<table border="1"><tr>';
                        echo '<td>№</td>';
                        echo '<td>Назва Роботи</td>';
                        echo '<td>Початок</td>';
                        echo '<td>Закінчення</td>';
                        echo '<td>Зайнятість</td>';
                        echo '</tr>';
                            $usage = $worker[2];
                            foreach ($usage as $date => $task) {
                                $task[0] = 0;
                            }
                            $height = 0;
                            $number = 0;
                            $activeTasks = array();
                            while (1) {
                                $number++;
                                $start = findMinDate($usage);
                                $height += $usage[$start][1];
                                $height -= $usage[$start][2];
                                $usage[$start][0] = 1;

                                $end = findMinDate($usage);
                                if (!$end) {
                                    break;
                                }
                                foreach ($usage[$end][4] as $k => $task) {
                                    $activeTasks[$task] = 0;
                                }
                                foreach ($usage[$start][3] as $k => $task) {
                                    $activeTasks[$task] = 1;
                                }
                                $curentTask = "";
                                foreach ($activeTasks as $task => $state) {
                                    if ($state) {
                                        $curentTask = $curentTask.' '.$task;
                                    }
                                }
                                echo '<tr><td>';
                                echo $number;
                                echo '</td><td>';
                                echo $curentTask;
                                echo '</td><td>';
                                echo $start;
                                echo '</td><td>';
                                echo $end;
                                echo '</td><td>';
                                echo $height;
                                echo '</td></tr>';
                            }
                            echo '</table>';
                        }
                    }
                    if ($workers) {
                    echo '<br>';
                    echo '<img src="res/'.project().'myGanttGraph.png" alt="Календарна Діаграма"/>';
                    echo '<br>';
                    echo '<img src="res/'.project().'myUsageGanttGraph.png" alt="Зайнятість робітників"/>';
                    }
?> 
                    
            </div>
            <div id="footer">
                <p>Копірайт © 2013-2014 Вербовий Андрій</p>
            </div>
        </div>
    </body>
</html>