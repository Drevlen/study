<!DOCTYPE html>
<html>
    <head>
        <title>Комп'ютерний зір. Тест 1.</title>
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
<p>&nbsp;&nbsp;&nbsp;&nbsp;1 Основним завданням комп'ютерного зору є:</p>
<ul class="test">
<li><input type="radio" name="question1" value="a"/>&nbsp;a) Вивчення процесу людського бачення та реалізація цього процесу за допомогою механічних засобів.</li>
<li><input type="radio" name="question1" value="b"/>&nbsp;б) Аналіз зображення з метою отримання висновків відносно об'єктів та сцен реального світу.</li>
<li><input type="radio" name="question1" value="c"/>&nbsp;в) Обробка зображення(збільшення контрастності, видалення шумів) з метою покращення, якості зображення.</li>
<li><input type="radio" name="question1" value="d"/>&nbsp;г) Управління роботами в реальному часі.</li>
</ul>
<p>&nbsp;&nbsp;&nbsp;&nbsp;2 Комп'ютерний зір можна умовно розділити на три області:</p>
<ul class="test">
<li><input type="radio" name="question2" value="a"/>&nbsp;а) Комп'ютерний зір, машинний зір, обробка зображень.</li>
<li><input type="radio" name="question2" value="b"/>&nbsp;б) Обробка зображень, аналіз зображень, комп'ютерний зір.</li>
<li><input type="radio" name="question2" value="c"/>&nbsp;в) Штучний інтелект, нейробіологія, робототехніка.</li>
<li><input type="radio" name="question2" value="d"/>&nbsp;г) Комп'ютерний зір, машинний зір, людський зір.</li>
</ul>
<p>&nbsp;&nbsp;&nbsp;&nbsp;3 Основним завданням машинного зору є:</p>
<ul class="test">
<li><input type="radio" name="question3" value="a"/>&nbsp;а) Відслідковування  руху об'єкту.</li>
<li><input type="radio" name="question3" value="b"/>&nbsp;б) Керування роботом в реальному часі.</li>
<li><input type="radio" name="question3" value="c"/>&nbsp;в) Видалення шумів з зображення.</li>
<li><input type="radio" name="question3" value="d"/>&nbsp;г) Обробка тривимірних сцен.</li>
</ul>
<p>&nbsp;&nbsp;&nbsp;&nbsp;4 Вирішенням, якої задачі комп'ютерного зору займається рентгенографія:</p>
<ul class="test">
<li><input type="radio" name="question4" value="a"/>&nbsp;а) Отримання рентгенівських знімків.</li>
<li><input type="radio" name="question4" value="b"/>&nbsp;б) Аналіз відеоданих медичного призначення.</li>
<li><input type="radio" name="question4" value="c"/>&nbsp;в) Захист від опромінення рентгенівськими променями.</li>
<li><input type="radio" name="question4" value="d"/>&nbsp;г) Аналіз рентгенівських змінних.</li>
</ul>
<p><input type="submit" value="Завершити" style=" padding: 2px;"/></p>
    </form>
                <?php
                    $score = 0;
                    if (isset($_POST['question1']) && $_POST['question1'] == "c")
                        $score += 3;
                    if (isset($_POST['question2']) && $_POST['question2'] == "a")
                        $score += 3;
                    if (isset($_POST['question3']) && $_POST['question3'] == "b")
                        $score += 3;
                    if (isset($_POST['question4']) && $_POST['question4'] == "b")
                        $score += 3;
                    echo ("<p>Ваш результат ".$score." із 12.</p>");
                    
                ?>
            </div>
            <div id="footer">
                <p>Копірайт © 2012-2013 Вербовий Андрій</p>
            </div>
        </div>
    </body>
</html>
