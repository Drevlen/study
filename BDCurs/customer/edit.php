<html>
	<head>
		<title>Edit</title>
	</head>
	<body>
		<?php
			function invalid_url()
			{
				echo "Неприпустима адреса.<br />";
				exit;
			}
			if (!isset($_COOKIE["username"]))
			{
				echo "Необхідно залоґінитися.<br />";
				exit;
			}
			if (!isset($_GET["action"]) || !isset($_GET["table"]))
				invalid_url();
			if (($_GET["action"] != "insert") && !isset($_GET["pk"]))
				invalid_url();
			require_once("functions.php");
			require_once("db_login.php");
			$db_connection = mysql_connect($db_host, $db_username, $db_password);
			if (!$db_connection) die("Died connecting");
			$db_select = mysql_select_db($db_database);

			$table = mysql_real_escape_string($_GET["table"]);
			mysql_query("SET @username_log='".$_COOKIE["username"]."'");
			
			if ($_GET["action"] == "delete")
			{
				$pk = mysql_real_escape_string($_GET["pk"]);
				if ($res = mysql_query("DELETE FROM $table WHERE ".primary_key($table)."=$pk"))
					echo "Виделення пройшло вдало.<br />";
				else
					echo "Провести видалення не вдалося.<br />";
				echo "<p><a href='table.php?table=$table'>Повернутися до таблиці '$table'.</a></p>";
			}
			else if ($_GET["action"] == "update")
			{
				$pk = mysql_real_escape_string($_GET["pk"]);
				$columns = column_names($table);

				if (isset($_POST[primary_key($table)]))
				{
					$elementnumber = 0;
					$query="UPDATE $table SET";
					foreach ($columns as $key => $cn)
					{
						if ($elementnumber!=0)
							$query .= ",";
						$query .= " ".$cn."=";
						$value = htmlentities($_POST[$cn]);
						if ($value == NULL)
							$query .= "NULL";
						else
							$query .= "'".$value."'";
						$elementnumber=$elementnumber+1;
					}
					//$query = substr_replace($query ,"",-1);
					$query .= " WHERE ".primary_key($table)."=$pk";
					if($res = mysql_query($query))
						echo "Оновлення пройшло вдало.<br />";
					else
						echo "Провести оновлення не вдалося.<br />";
				}
				else
				{
					echo "<form action='".$_SERVER["REQUEST_URI"]."' method='post'><table metod><tr>";
					foreach ($columns as $key => $cn)
						echo "<td>$cn:</td>";
					echo "</tr><tr>";
					$res = mysql_query("SELECT * FROM $table WHERE ".primary_key($table)."=$pk");
					$res_row = mysql_fetch_assoc($res);
					foreach ($columns as $key => $cn)
						echo "<td><input type='text' name='$cn' value='".$res_row[$cn]."' /></td>";
					echo "<input type='button' value='Змінити' />";
					echo "</tr></form></table>";
				}
					echo "<p><a href='table.php?table=$table'>Повернутися до таблиці '$table'.</a></p>";
			}			
			else if ($_GET["action"] == "insert")
			{
				$columns = column_names($table);
				if (isset($_POST[primary_key($table)]))
				{
					$elementnumber = 0;
					$query="INSERT INTO $table VALUES (";
					foreach ($columns as $key => $cn)
					{
						if ($elementnumber!=0)
							$query .= ",";
						$value = htmlentities($_POST[$cn]);
						if ($value == NULL)
							$query .= "NULL";
						else
							$query .= $value;
						$elementnumber=$elementnumber+1;
					}
					$query .= ")";
					echo $query."<br />";
					if($res = mysql_query($query))
						echo "Вставка пройшла вдало.<br />";
					else
						echo "Провести вставку не вдалося.<br />";
				}
				else
				{
					echo "<form action='".$_SERVER["REQUEST_URI"]."' method='post'><table metod><tr>";
					foreach ($columns as $key => $cn)
						echo "<td>$cn:</td>";
					echo "</tr><tr>";
					foreach ($columns as $key => $cn)
						echo "<td><input type='text' name='$cn' /></td>";
					echo "<input type='submit' value='Додати' />";
					echo "</tr></form></table>";
				}
					echo "<p><a href='table.php?table=$table'>Повернутися до таблиці '$table'.</a></p>";
			}
			else
				invalid_url();
			mysql_close($db_connection);
		?>
	</body>
</html>
