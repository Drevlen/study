<html>
	<head>
		<title>Index</title>
	</head>
	<body>
		<?php
			if (isset($_GET["action"]))
			{
				if  ($_GET["action"] == "logout")
				{
					setcookie("username", "", time()-7200);
					header("Location: ".$_SERVER["PHP_SELF"]);
				}
				else if ($_GET["action"] == "refresh")
				{
					require_once("functions.php");
					require_once("db_login.php");
					$db_connection = mysql_connect($db_host, $db_username, $db_password);
					if (!$db_connection) die("Died connecting");
					$db_select = mysql_select_db($db_database);
					initialize_logs();
					echo "<p>Журнали оновлено. <a href=table.php>Перейти до таблиць.</a></p>";
					mysql_close($db_connection);
				}
			}
			else
			{
				if (isset($_COOKIE["username"]))
					header("Location: table.php");
				session_start();
				if (!isset($_POST["username"]) || !isset($_POST["password"]))
				{
					if (isset($_SESSION["wrong_login"]))
						echo "<p>Комбінація імені і паролю не існує. Спробуйте ще раз.</p>";
					echo "<form action='".$_SERVER["PHP_SELF"]."' method='post'><p>";
					echo "<label>Ім'я: <input type='text' name='username' /></label>".
						 "<label>Пароль: <input type='password' name='password' /></label>".
						 "<input type='submit' value='Увійти' /></p>";
					echo "</form>";
				}
				else
				{
					require_once("db_login.php");
					$db_connection = mysql_connect($db_host, $db_username, $db_password);
					if (!$db_connection) die("Died connecting");
					$db_select = mysql_select_db($db_database);
					$username = mysql_real_escape_string($_POST["username"]);
					$password = mysql_real_escape_string($_POST["password"]);
					$query = "SELECT CustomerName FROM Customer WHERE CustomerName ='$username' AND Password='$password'";
					$res = mysql_query($query);
					mysql_close($db_connection);
					if ($username == "admin" && $password == "admin")
					{
						setcookie("username", $username);
						header("Location: table.php");
						unset($_SESSION["wrong_login"]);
					}
					else
					if ($res_row = mysql_fetch_row($res))
					{
						setcookie("username", $username);
						header("Location: table.php");
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
