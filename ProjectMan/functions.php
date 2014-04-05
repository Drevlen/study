<!DOCTYPE html>
 <html> <head> <title>Fuctions</title> </head> <body> </body> </html>
    
<?php
function setProject($name) {
    str_replace(" ", "_", $name);
    file_put_contents('res/CurrentProject.txt',$name);
}

function project() {
    return file_get_contents('res/CurrentProject.txt');
}

function loadHolidays(){
    if (!file_exists('res/holidays.txt'))
        return NULL;
    $fp = fopen('res/holidays.txt', 'r');
    $table = array();
    while ($line = fscanf($fp, "%s\t%s\t%s\n")) {
        list ($month, $day, $name) = $line;
        $table[$month][$day] = 1;
        $table[$month]['info'][$day] = $name;
    }
    fclose($fp);

    return $table;
}

function counter1($action) {
    if ($action == 'addLine') {
        file_put_contents('res/'.project().'count1.txt', (string) 
                (((int) file_get_contents('res/'.project().'count1.txt')) + 1)); 
    } else if ($action == 'reset') {
        file_put_contents('res/'.project().'count1.txt', (string) 0);
        file_put_contents('res/'.project().'table1.txt', "");
    } else {
        return file_get_contents('res/'.project().'count1.txt');
    }
    return "bad";
}

function counter2($action) {
    if ($action == 'addLine') {
        file_put_contents('res/'.project().'count2.txt', (string) 
                (((int) file_get_contents('res/'.project().'count2.txt')) + 1)); 
    } else if ($action == 'reset') {
        file_put_contents('res/'.project().'count2.txt', (string) 0);
        file_put_contents('res/'.project().'table2.txt', "");
    } else {
        return file_get_contents('res/'.project().'count2.txt');
    }
    return "bad";
}

function removeLine1($line) {
    $parsed = explode(" ", $line);
    $id = $parsed[2];
    $table = readTable1();
    $size = counter1();
    counter1('reset');
    file_put_contents('res/'.project().'table1.txt','');
    $edited = FALSE;
    for ($i = 0; $i < 4 * $size; $i += 4) {
        if ($table[$i] == $id) {
            if (isset($_GET['editLine']) && !$edited) {
                counter1('addLine');
                $copyline['id'] = $_POST['id'];
                $copyline['name'] = $_POST['name'];
                $copyline['time'] = $_POST['time'];
                $copyline['depend'] = $_POST['depend'];
                addToTable1($copyline);
                $edited = TRUE;
            }
            continue;
        }
        counter1('addLine');
        if ($table[$i] > $id && !$edited) {
            $copyline['id'] = $table[$i] - 1;
        } else {
            $copyline['id'] = $table[$i];
        }
        $copyline['name'] = $table[$i + 1];
        $copyline['time'] = $table[$i + 2];
        $copyline['depend'] = $table[$i + 3];
        addToTable1($copyline);
    }
}

function removeLine2($line) {
    $parsed = explode(" ", $line);
    $id = $parsed[2];
    $table = readTable2();
    $size = counter2();
    counter2('reset');
    $edited = FALSE;
    file_put_contents('res/'.project().'table2.txt','');
    for ($i = 1; $i <= $size; $i++) {
        if ($table[$i][0] == $id) {
            if (isset($_GET['editLine']) && !$edited) {
                counter2('addLine');
                $copyline['id'] = $_POST['id'];
                $copyline['name'] = $_POST['name'];
                $copyline['time'] = $_POST['time'];
                $copyline['depend'] = $_POST['depend'];
                $copyline['worker'] = $_POST['worker'];
                $copyline['usage'] = $_POST['usage'];
                addToTable2($copyline);
                $edited = TRUE;
            }
            continue;
        }
        counter2('addLine');
        if ($table[$i][0] > $id && !$edited) {
            $copyline['id'] = $table[$i][0] - 1;
        } else {
            $copyline['id'] = $table[$i][0];
        }
        $copyline['name'] = $table[$i][1];
        $copyline['time'] = $table[$i][2];
        $copyline['depend'] = $table[$i][3];
        $copyline['worker'] = $table[$i][4]; 
        $copyline['usage'] = $table[$i][5];
        addToTable2($copyline);
    }
}

function removeLine3($line){
    $parsed = explode(" ", $line);
    $id = explode("-", $parsed[2]);
    if (!file_exists('res/holidays.txt'))
        return NULL;
    $fp = fopen('res/holidays.txt', 'r');
    $table = array();
    while ($line = fscanf($fp, "%s\t%s\t%s\n")) {
        list ($month, $day, $name) = $line;
        if ($day == $id[0] && $month == $id[1]) {
            continue;
        }
        $table[$month][$day] = 1;
        $table[$month]['info'][$day] = $name;
    }
    fclose($fp);
    
    $fp = fopen('res/holidays.txt', 'w');
    foreach ($table as $m => $month) {
        foreach ($month as $d => $dates) {
            if ($d == 'info') {
                continue;
            }
            fprintf($fp, "%s\t%s\t%s\n", $m, $d, $month['info'][$d]);
        }
    }
    fclose($fp);
}

function addHoliday(){
    if ($_POST['name'] == "") {
            $name_ = "something";
        } else {
            $name_ = str_replace(" ", "_", $_POST['name']);
        }
    $fp = fopen('res/holidays.txt', 'a+'); 
    echo $name_;
    echo $_POST['month'];
    echo $_POST['day'];
    fprintf($fp, "%s\t%s\t%s\n", $_POST['month'], $_POST['day'], $name_);
    fclose($fp);
}

function addToTable1($line) {
    $fp = fopen('res/'.project().'table1.txt', 'a+');
    if ($line) {
        $name_ = str_replace(" ", "_", $line['name']);
        $depend_ = str_replace(" ", "_", $line['depend']);
        fprintf($fp, "%s\t%s\t%s\t%s\n", $line['id'], $name_, 
                $line['time'], $depend_);
    } else {
        if ($_POST['name'] == "") {
            $name_ = "something";
        } else {
            $name_ = str_replace(" ", "_", $_POST['name']);
        }

        if ($_POST['time'] == "") {
            $time_ = 0;
        } else {
            $time_ = $_POST['time'];
        }

        if ($_POST['depend'] == "") {
            $depend_ = 0;
        } else {
            $depend_ = str_replace(" ", ",", $_POST['depend']);
        }
        fprintf($fp, "%s\t%s\t%s\t%s\n", counter1(), $name_, $time_, $depend_);
    }
    fclose($fp);
}

function addToTable2($line) {
    $fp = fopen('res/'.project().'table2.txt', 'a+');
    if ($line) {
        $name_ = str_replace(" ", "_", $line['name']);
        $depend_ = str_replace(" ", ",", $line['depend']);
        $worker_ = str_replace(" ", ",", $line['worker']);
        $usage_ = str_replace(" ", ",", $line['usage']);
        fprintf($fp, "%s\t%s\t%s\t%s\t%s\t%s\n", $line['id'], $name_, 
                $line['time'], $depend_, $worker_, $usage_);
    } else {
        if ($_POST['name'] == "") {
            $name_ = "something";
        } else {
            $name_ = str_replace(" ", "_", $_POST['name']);
        }

        if ($_POST['time'] == "") {
            $time_ = 0;
        } else {
            $time_ = $_POST['time'];
        }

        if ($_POST['depend'] == "") {
            $depend_ = 0;
        } else {
            $depend_ = str_replace(" ", ",", $_POST['depend']);
        }

        if ($_POST['worker'] == "") {
            $worker_ = 0;
        } else {
            $worker_ = str_replace(" ", ",", $_POST['worker']);
        }

        if ($_POST['usage'] == "") {
            $usage_ = 100;
        } else {
            $usage_ = str_replace(" ", ",", $_POST['usage']);
        }
        fprintf($fp, "%s\t%s\t%s\t%s\t%s\t%s\n", counter2(), $name_, $time_, $depend_, $worker_, $usage_);
    }
    fclose($fp);
}

function readTable1() {
    if (!file_exists('res/'.project().'count1.txt') && !file_exists('res/'.project().'table1.txt'))
        return NULL;
    $fp = fopen('res/'.project().'table1.txt', 'r');
    $table = array();
    while ($line = fscanf($fp, "%s\t%s\t%s\t%s\n")) {
        list ($number, $name, $time, $depend) = $line;
        $table[] = $number;
        $table[] = $name;
        $table[] = $time;
        $table[] = $depend;
    }
    fclose($fp);
    return $table;
}

function readTable2() {
    if (!file_exists('res/'.project().'count2.txt') && !file_exists('res/'.project().'table2.txt'))
        return NULL;
    $fp = fopen('res/'.project().'table2.txt', 'r');
    $table = array();
    while ($line = fscanf($fp, "%s\t%s\t%s\t%s%s\t%s\n")) {
        list ($number, $name, $time, $depend, $worker, $usage) = $line;
        $table[$number][0] = $number;
        $table[$number][1] = $name;
        $table[$number][2] = $time;
        $table[$number][3] = $depend;
        $table[$number][4] = $worker;
        $table[$number][5] = $usage;
    }
    fclose($fp);
    return $table;
}

function drawGraph1() {
    if (counter1() == 0) {
        return 0;
    }
    ini_set('include_path', '.:/usr/share/php5');
    require_once ('jpgraph/src/jpgraph.php');
    require_once ('jpgraph/src/jpgraph_scatter.php');
    require_once ('jpgraph/src/jpgraph_line.php');
    // Some data
    $table = readTable1();
    $connectTo = array();
    $connectFrom = array();
    for ($i = 0; $i < counter1(); $i++) {
        $connectFrom[$i + 1] = array();
        $connectTo[$i + 1] = array();
        $connectTo[$i + 1][0] = 0;
    }
    //read number of depended states for each state on
    for ($i = 0; $i < counter1(); $i++) {
        $depend = explode(",", $table[4 * $i + 3]);
        foreach ($depend as $j => $value) {
            $connectTo[$value][$i + 1] = $i + 1;
            $connectFrom[$i + 1][$value] = $value;
        }
    }

    $layer = array();
    $layer[0] = array();
    $layer[0][1] = 1;
    $used = 0;
    for ($i = 1; $used < counter1() - 1; $i++) {
        $layer[$i] = array();
        foreach ($layer[$i - 1] as $k => $state) {
            foreach ($connectTo[$state] as $j => $value) {
                if ($connectTo[$value][0] === 0 && $j != 0) {
                    $layer[$i][$value] = $value;
                    $connectTo[$value][0] = 1; //flag used
                    $used++;
                }
            }
        }
    }    
    
    $coordinatesX = array();
    $coordinatesY = array();
    $numberLayers = $i;
    $stepX = 728 / ($numberLayers + 1);
    for ($i = 0; $i < $numberLayers; $i++) {
        $numberInRow = 0;
        foreach ($layer[$i] as $k => $row) {
            $numberInRow++;
        }
        $stepY = 500 / ($numberInRow + 1);
        $j = 0;
        foreach ($layer[$i] as $k => $row) {
            $j++;
            $coordinatesX[$row] = $stepX * ($i + 1);
            $coordinatesY[$row] = $stepY * $j;
        }
    }
    $coordinatesX[0] = $coordinatesX[1];
    $coordinatesY[0] = $coordinatesY[1];

    // Create the graph. These two calls are always required
    $graph = new Graph(728,500);
    $graph->SetScale('linlin');

    // Create the linear plot
    $plot = new ScatterPlot($coordinatesY,$coordinatesX);
    $plot->mark->SetType(MARK_FILLEDCIRCLE);
    $plot->SetColor('blue');
    // Add the plot to the graph
    $aHide = true;
    $graph->xaxis->HideLabels($aHide);
    $graph->yaxis->HideLabels($aHide);
    $graph->Add($plot);
    
    //computing critical route

    $earlyTime = array();
    for ($i = 1; $i <= counter1(); $i++) {
        $earlyTime[$i] = 0;
        foreach ($connectFrom[$i] as $k => $depend) {
            $time = $earlyTime[$depend] + $table[4 * ($depend - 1) + 2];
            if ($earlyTime[$i] < $time) {
                $earlyTime[$i] = $time;
            }
        }  
    }

    $lateTime = array();
    $size = counter1();
    $lateTime[$size] = $earlyTime[$size];

    for ($i = $size - 1; $i > 0; $i--) {
        $lateTime[$i] = 100500;
        foreach ($connectTo[$i] as $k => $depend) {
            if ($k > 0) {
                $time = $lateTime[$depend] - $table[4 * ($i - 1) + 2];
                if ($lateTime[$i] > $time) {
                    $lateTime[$i] = $time;
                }
            }
        }
    }

    $linkX = array();
    $linkY = array();
    for ($i = 1; $i < counter1(); $i++) {
        $linkX[0] = $coordinatesX[$i];
        $linkY[0] = $coordinatesY[$i];
        $txt = new Text((string)$i,$linkX[0], 500 - $linkY[0]);
        $graph->Add($txt); 
        foreach ($connectTo[$i] as $j => $value) {
            if ($j > 0) {
                $linkX[1] = $coordinatesX[$value];
                $linkY[1] = $coordinatesY[$value];
                $plot = new LinePlot($linkY, $linkX);
                $graph->Add($plot);
                if ($lateTime[$value] - $earlyTime[$value] == 0
                        && $lateTime[$i] - $earlyTime[$i] == 0) {
                    $plot->SetColor('#FF0000');    
                    $plot->SetWeight('4');
                } else {
                    $plot->SetColor('#0000FF');
                }
            }
        }
    }
    $txt = new Text((string)$i,$linkX[1], 500 - $linkY[1]);
    $graph->Add($txt); 
    
//    $table = array();
    $table[0] = array();
    $table[1] = array();
    $table[2] = array();
    for ($i = 1; $i <= counter1(); $i++) {
        $table[0][$i] = $earlyTime[$i];
        $table[1][$i] = $lateTime[$i];
        $table[2][$i] = $lateTime[$i] - $earlyTime[$i];
    }
    // Display the graph
     $graph->Stroke('res/'.project().'myStructGraph.png');
     return $table;
}

function dateFinish($workingDays, $dateStart) {
    $dateParsed = explode('-', $dateStart);
    $day = $dateParsed[2];
    $month = $dateParsed[1];
    $year = $dateParsed[0];
    $holiday = loadHolidays();
  // Державні свята

  
  // Лічильник днів місяця 
  $day_count = 0; 
  
  while($day_count < $workingDays) {
      $dayofweek = date('w', mktime(0, 0, 0, $month, $day, $year));
      if ($dayofweek != 0 && $holiday[$month][$day] != 1) {
          $day_count++;
      }
      
      $day++;
      if ($day > date('t', mktime(0, 0, 0, $month, $day - 1, $year))) {
          $day = 1;
          $month++;
          if ($month > 12) {
              $month = 1;
              $year++;
          }
      }           
  }
  return $year.'-'.$month.'-'.$day;
}

function drawGraph2($startDate) {
    if (counter2() == 0) {
        return 0;
    }
    ini_set('include_path', '.:/usr/share/php5');
    require_once ('jpgraph/src/jpgraph.php');
    require_once ('jpgraph/src/jpgraph_gantt.php');
    require_once ('jpgraph/src/jpgraph_canvas.php');
    // Some data
    $table = readTable2();

    $connectFrom = array();
    for ($i = 1; $i <= counter2(); $i++) {
        $connectFrom[$i] = array();
    }
    //read number of depended states for each state on
    for ($i = 1; $i <= counter2(); $i++) {
        $depend = explode(",", $table[$i][3]);
        foreach ($depend as $j => $value) {
            $connectFrom[$i][$value] = $value;
        }
    }

    $times = array();
    for ($i = 1; $i <= counter2(); $i++) {
        $times[$i] = array();
        $times[$i][0] = $table[$i][0];
        $times[$i][1] = $table[$i][1];
        $times[$i][2] = $startDate['year'].'-'.$startDate['month'].'-'.$startDate['day'];    
        $latest = mktime(0, 0, 0, $startDate['month'], $startDate['day'], $startDate['year']);
        foreach ($connectFrom[$i] as $j => $depend) {
            if ($depend != 0) {
                $parsedTime = explode("-", $times[$depend][3]);
                $newtime = mktime(0, 0, 0, 
                        $parsedTime[1], $parsedTime[2], $parsedTime[0]);
               if ($newtime > $latest) {
                   $times[$i][2] = $times[$depend][3];
                   $latest = $newtime;
               }
            }
        }
        $times[$i][3] = dateFinish($table[$i][2], $times[$i][2]);
        $times[$i][4] = $table[$i][4];
    }
    // Графік
    $graph = new GanttGraph();
    $graph->title->Set("Календарний план");
    $graph->ShowHeaders(GANTT_HDAY | GANTT_HMONTH| GANTT_HYEAR);
    $graph->scale->actinfo->SetColTitles( array('Name','Start','End'),array(100));
    $n = count($times) + 1;
    for($i = 1; $i < $n; ++$i) {
        $bar = new GanttBar($times[$i][0],
                array($times[$i][1],$times[$i][2],$times[$i][3]),
                $times[$i][2],$times[$i][3],$times[$i][4],0.35);
        $graph->Add($bar);
    }
    $graph->Stroke('res/'.project().'myGanttGraph.png');
    
    
    // list of workers
    $workers = array();
    for ($i = 1; $i <= counter2(); $i++) {
        $wokersInLine = explode(",", $table[$i][4]);
        $usageInLine = explode(",", $table[$i][5]);
        foreach ($wokersInLine as $j => $worker) {
            if ($worker) {
                            $workers[$worker][0][$i] = $i;
                            if ($usageInLine[$j]) {
                                $workers[$worker][1][$i] = $usageInLine[$j];
                            } else {
                                $workers[$worker][1][$i] = '100';
                            }
                        }
        }
    }
    
    $graph = new GanttGraph();
    $graph->title->Set("Зайнятість працівників");
    $graph->ShowHeaders(GANTT_HDAY | GANTT_HMONTH| GANTT_HYEAR);
    $bar = new GanttBar('Project',
                array('Project',$times[1][2],$times[$n - 1][3]),
                $times[1][2],$times[$n - 1][3],'', 0.35);
    $graph->Add($bar);
    $workerNumber = 0;
    foreach ($workers as $i => $worker) {
        $workerNumber++;
        $usage = array();
        foreach ($worker[0] as $j => $task) {
            $usage[$times[$task][2]][0] = 0;
            $usage[$times[$task][2]][1] += $worker[1][$task];
            $usage[$times[$task][3]][2] += $worker[1][$task];
            $usage[$times[$task][2]][3][] = $task;
            $usage[$times[$task][3]][4][] = $task;
        }
        $height = 0;
        while (1) {
            $start = findMinDate($usage);
            $height += 0.002 * $usage[$start][1];
            $height -= 0.002 * $usage[$start][2];
            $height = min(max($height, 0), 1); 
            $usage[$start][0] = 1;
            $end = findMinDate($usage);
            if (!$end) {
                break;
            }
            
            if ($height > 0.001) {
//                echo $start." ".$end." ".$usage[$start][1]." ".$usage[$start][2]." ".$height."<br>"; 
                $bar = new GanttBar($workerNumber,
                    array((string)$i),
                    $start, $end, (string)($height / 0.2), $height);
                $bar->caption->SetFont(FF_FONT0, FS_BOLD);
                $bar->caption->SetAlign('left','bottom');
                if ($height - 0.2 > 0.001) {
                    $bar->SetFillColor("red");
                } else if ($height - 0.2 < -0.001) {
                    $bar->SetFillColor("blue");
                } else {
                    $bar->SetFillColor("green");
                }
                $graph->Add($bar);
            }      
        }  
        $workers[$worker][2] = $usage; 
//        print_r($usage);
//        echo '<br>';
//        echo '<br>';
//        echo '<br>';
    }
    $graph->Stroke('res/'.project().'myUsageGanttGraph.png');
    return $workers;
}

function findMinDate($usage) {
    $earliest = PHP_INT_MAX;
    $targetDate = FALSE;
    foreach ($usage as $date => $value) {
        if ($value[0] == 0) {
            $parsedTime = explode('-', $date);
            $newtime = mktime(0, 0, 0, 
                    $parsedTime[1], $parsedTime[2], $parsedTime[0]);
            if($newtime < $earliest) {
                $earliest = $newtime;
                $targetDate = $date;
            }
        }
    }
    return $targetDate;
}

?>

