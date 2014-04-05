<!DOCTYPE html>
<html>
    <head>
        <title>Комп'ютерний зір. Тест 3.</title>
        <meta charset="UTF-8"/>
        <meta name="keywords" content="computer vision, комп'ютерний зір"/>
        <meta name="description" content="Декілька лекцій, що розглядають основні проблеми комп'ютерного зору."/>
        <link rel="icon" href="favicon.ico" />
        <link rel="stylesheet" href="styles/style.css" type="text/css"/>
    </head>
    <body>
        <div id="wrapper"> 
            <div id="header">
                
            </div>
        <ul id="nav">
            <li class="linav"><a href="index.html" class="nav">Головна</a></li>
            <li class="linav"><a href="Lesson1.html" class="nav">Урок 1.</a></li>
            <li class="linav"><a href="Lesson2.html" class="nav">Урок 2.</a></li>
            <li class="linav"><a href="Lesson3.html" class="nav">Урок 3.</a></li>
            <li class="linav"><a href="Test1.php" class="nav">Тест 1.</a></li>
            <li class="linav"><a href="Test2.php" class="nav">Тест 2.</a></li>
            <li class="linav"><a href="Test3.php" class="nav">Тест 3.</a></li>
        </ul>
            <div id="content"> 
    <form id="form1" method="post">
<p>&nbsp;&nbsp;&nbsp;&nbsp;1 Яку функцію не виконує попередня обробка зображень:</p>
<ul class="test">
<li><input type="radio" name="question1" value="a"/>&nbsp;a) Масштабування.</li>
<li><input type="radio" name="question1" value="b"/>&nbsp;б) Видалення шумів та спотворень.</li>
<li><input type="radio" name="question1" value="c"/>&nbsp;в) Покращення контрастності.</li>
<li><input type="radio" name="question1" value="d"/>&nbsp;г) Оцінка характерних параметрів.</li>
</ul>
<p>&nbsp;&nbsp;&nbsp;&nbsp;2 Якій інформації зазвичай відповідають пікселі зображення:</p>
<ul class="test">
<li><input type="radio" name="question2" value="a"/>&nbsp;а) Інтенсивності світла.</li>
<li><input type="radio" name="question2" value="b"/>&nbsp;б) Поглинання звукових хвиль.</li>
<li><input type="radio" name="question2" value="c"/>&nbsp;в) Ядерний магнітний резонанс.</li>
<li><input type="radio" name="question2" value="d"/>&nbsp;г) Глибина електромагнітних хвиль.</li>
</ul>
<p>&nbsp;&nbsp;&nbsp;&nbsp;3 Який етап не є складовою типової системи комп'ютерного зору.:</p>
<ul class="test">
<li><input type="radio" name="question3" value="a"/>&nbsp;а) Високорівнева обробка.</li>
<li><input type="radio" name="question3" value="b"/>&nbsp;б) Попереднє припущення.</li>
<li><input type="radio" name="question3" value="c"/>&nbsp;в) Виокремлення деталей.</li>
<li><input type="radio" name="question3" value="d"/>&nbsp;г) Отримання зображень.</li>
</ul>
<p><input type="submit" value="Завершити" style=" padding: 2px;"/></p>
    </form>
                <?php
                    $score = 0;
                    if (isset($_POST['question1']) && $_POST['question1'] == "d")
                        $score += 4;
                    if (isset($_POST['question2']) && $_POST['question2'] == "a")
                        $score += 4;
                    if (isset($_POST['question3']) && $_POST['question3'] == "b")
                        $score += 4;
                    echo ("<p>Ваш результат ".$score." із 12.</p>");
                    
                ?>
            </div>
            <div id="footer">
                <p>Копірайт © 2012-2013 Вербовий Андрій</p>
            </div>
        </div>
    </body>
</html>
