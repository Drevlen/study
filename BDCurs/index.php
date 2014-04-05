<html>
	<head>
		<title>Index</title>
	</head>
	<body>
		<?php
			$db_host = 'localhost';
			$db_database = 'mydb';
			$db_username = 'root';
			$db_password = '';
			if (isset($_GET["action"]))
			{
				if  ($_GET["action"] == "logout")
				{
					setcookie("username", "", time()-7200);
					header("Location: ".$_SERVER["PHP_SELF"]);
				}
			}
			else
			{
				if (isset($_COOKIE["username"]))
					header("Location: table.php");
				session_start();
				if (!isset($_POST["host"]) || !isset($_POST["database"]) || !isset($_POST["username"]))
				{
					if (isset($_SESSION["wrong_login"]))
					echo "<p>Неможливо з'єднатися. Спробуйте ще раз.</p>";
					echo "<form action='".$_SERVER["PHP_SELF"]."' method='post'><p>";
										echo 	 "<label>Розташування: <input type='text' name='host' /></label>".
						 "<label>Назва БД: <input type='text' name='database' /></label>".
						 "<label>Ім'я: <input type='text' name=' username' /></label>".
						 "<label>Пароль: <input type='password' name='password' /></label>".
						 "<input type='submit' value='Увійти' /></p>";
					echo "</form>";
				}
				else
				{
echo"1";
				$host = mysql_real_escape_string($_POST["host"]);
				$database = mysql_real_escape_string($_POST["database"]);
				$username = mysql_real_escape_string($_POST["username"]);
				$password = mysql_real_escape_string($_POST["password"]);
			//$db_host = $host;
			//$db_database = $database;
			//$db_username = $username;
			//$db_password = $password;
					$db_connection = mysql_connect($db_host, $db_username, $db_password);
					if (!$db_connection) 	die("Died connecting");
					$db_select = mysql_select_db($db_database);
					mysql_close($db_connection);
					if ($db_select)
					{
						setcookie("username", $username);
						header("Location: table.php?host=$db_host&database=$db_database&password=$db_password");
						unset($_SESSION["wrong_login"]);
					}
					else
					{
						$_SESSION["wrong_login"] = true;
						header("Location: ".$_SERVER["PHP_SELF"]);
					}
				}
			}
		?>
	</body>
</html>
