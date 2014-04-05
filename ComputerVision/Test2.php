<!DOCTYPE html>
<html>
    <head>
        <title>Комп'ютерний зір. Тест 2.</title>
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
<p>&nbsp;&nbsp;&nbsp;&nbsp;1 Задача розпізнавання полягає в:</p>
<ul class="test">
<li><input type="radio" name="question1" value="a"/>&nbsp;a) Знаходженні всіх зображень серед великого набору зображень, які мають певний вміст.</li>
<li><input type="radio" name="question1" value="b"/>&nbsp;б) Визначенні положення чи орієнтації визначеного об'єкта відносно камери.</li>
<li><input type="radio" name="question1" value="c"/>&nbsp;в) Визначенні того, чи містять відеодані деякий характерний об'єкт, особливість чи активність.</li>
<li><input type="radio" name="question1" value="d"/>&nbsp;г) Розпізнаванні індивідуального екземпляру об'єкта.</li>
</ul>
<p>&nbsp;&nbsp;&nbsp;&nbsp;2 Задачу розпізнавання можна вважати вирішеною для:</p>
<ul class="test">
<li><input type="radio" name="question2" value="a"/>&nbsp;а) Випадкових об'єктів у випадкових умовах.</li>
<li><input type="radio" name="question2" value="b"/>&nbsp;б) Випадкових об'єктів у визначених умовах.</li>
<li><input type="radio" name="question2" value="c"/>&nbsp;в) Окремих об'єктів у випадкових умовах.</li>
<li><input type="radio" name="question2" value="d"/>&nbsp;г) Окремих об'єктів у визначених умовах.</li>
</ul>
<p>&nbsp;&nbsp;&nbsp;&nbsp;3 Проблема ідентифікації полягає у:</p>
<ul class="test">
<li><input type="radio" name="question3" value="a"/>&nbsp;а) Автоматичній ідентифікації та верифікації персони на зображенні.</li>
<li><input type="radio" name="question3" value="b"/>&nbsp;б) Розпізнаванні визначеної персони на зображенні.</li>
<li><input type="radio" name="question3" value="c"/>&nbsp;в) Визначення  окремих об'єктів, таких як прості геометричні об'єкти, людські обличчя, друковані чи рукописні символи, автомобілі.</li>
<li><input type="radio" name="question3" value="d"/>&nbsp;г) Розпізнаванні індивідуального екземпляру об'єкта.</li>
</ul>
<p>&nbsp;&nbsp;&nbsp;&nbsp;4 Які переваги підходу до розпізнавання основаному на використанні зовнішніх ознак:</p>
<ul class="test">
<li><input type="radio" name="question4" value="a"/>&nbsp;а) Використання єдиного зразка здатне забезпечити надійне розпізнавання.</li>
<li><input type="radio" name="question4" value="b"/>&nbsp;б) Врахування , що об'єкт виглядає по різному в різних умовах.</li>
<li><input type="radio" name="question4" value="c"/>&nbsp;в) Потребує попередньо підготовлених зразків.</li>
<li><input type="radio" name="question4" value="d"/>&nbsp;г) Можна перелічити усі умови представлення об'єкту.</li>
</ul>
<p><input type="submit" value="Завершити" style=" padding: 2px;"/></p>
    </form>
                <?php
                    $score = 0;
                    if (isset($_POST['question1']) && $_POST['question1'] == "c")
                        $score += 3;
                    if (isset($_POST['question2']) && $_POST['question2'] == "d")
                        $score += 3;
                    if (isset($_POST['question3']) && $_POST['question3'] == "d")
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
