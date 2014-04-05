<html>
	<head>
		<title>Table</title>
	</head>
	<body>
		<?php
			require_once("functions.php");
			require_once("db_login.php");
			$db_connection = mysql_connect($db_host, $db_username, $db_password);
			if (!$db_connection) die("Died connecting");
			$db_select = mysql_select_db($db_database);

			if (isset($_COOKIE['username'])) $username=$_COOKIE['username'];

		$query = "SELECT idCustomer FROM Customer WHERE CustomerName = '$username'";				$res = mysql_query($query);
			if ($res_row = mysql_fetch_row($res))
			{
				$userid=$res_row[0];
			}
			if ($_COOKIE["username"] == "admin")
				$userid=-1;

			if (isset($_GET["table"]))
				$table = $_GET["table"];
			else
				$table = NULL;
			echo_header();
			if ($table !== NULL)
			{
				echo "<p>Таблиця <b>$table</b>:</p>";
				if ($table =="order") $table="`order`";
				echo_table($table, $userid);
			}
			else
				echo "<p>Оберіть таблицю.</p>";

			mysql_close($db_connection);
		?>
	</body>
</html>
