<html>
	<head>
		<title>Table</title>
	</head>
	<body>
		<?php
			require_once("functions.php");
			if (isset($_COOKIE['username']))
				{
					$username=$_COOKIE['username'];
					$db_username=$username;
/*12*/				}
			if (isset($_GET["host"])) $db_host = $_GET["host"];
			if (isset($_GET["database"])) $db_database = $_GET["database"];				if (isset($_GET["password"])) $db_password = $_GET["password"];
/*16*/			else $db_password = ''; 
			require_once("db_login.php");
			$db_connection = mysql_connect($db_host, $db_username, $db_password);
			if (!$db_connection) die("Died connecting");
			$db_select = mysql_select_db($db_database);
			
					if (isset($_GET["tab1"]))
				{
					$table1 = $_GET["tab1"];
					if ($table1 =="order") $table1="`order`";
					echo_reader2($table1);

				}
			else 
				{
					if (!isset($_GET["table1"])) $table1=NULL;
					$table2=NULL;
					echo_reader1();
				}
				if (isset($_GET["tab2"]))
					{
						$table2 = $_GET["tab2"];
						if ($table2 =="order") $table2="`order`";
					}
				else 
					{
						$table2=NULL;

					}

			echo "<p>“аблиц€ 1 <b>$table1</b></p>";
			echo "<p>“аблиц€ 2 <b>$table2</b></p>";
			if ($table1 !== NULL && $table2 !== NULL)
				echo_table($table1, $table2);
			else
				echo "<p>ќбер≥ть таблиц≥.</p>";

			mysql_close($db_connection);
		?>
	</body>
</html>
