<?php
	function flag($f)
	{
		if ($f==1) $f=0;
		else $f=1;
	}
	function is_log($table)
	{
		return preg_match("/_log$/", $table);
	}
	function column_names($table)
	{
		$res = mysql_query("DESCRIBE $table");
		while ($res_row = mysql_fetch_assoc($res))
			$cn[] = $res_row["Field"];
		return $cn;
	}

	function primary_key($table)
	{
		$res = mysql_query("SHOW INDEX FROM $table WHERE KEY_NAME = 'PRIMARY'");
		if ($res_row = mysql_fetch_assoc($res))
		{
			$prim = $res_row["Column_name"];
			return $prim;
		}
		else
			throw new Exception("No primary key in table '$table' found.");
	}
	function show_tables()
	{
		echo "<br /> All tables: <br />";
		$res = mysql_query("SHOW TABLES");
		while ($res_row = mysql_fetch_row($res))
		{
			$t = $res_row[0];
			if (is_log($t)) continue;
			echo $t;
			echo being_logged($t) ? " - is being LOGGED <br />" : " - is NOT being LOGGED <br />";
		}
	}
	function echo_header()
	{		
		if (!isset($_COOKIE["username"]))	
			header("Location: /");
		echo "<p>Ви увійшли як <b>".$_COOKIE["username"].
			"</b> <a href='/?action=logout'>(вийти)</a>.</p> ";
		echo "<p>|&nbsp";
		if ($_COOKIE["username"] != "admin")
		{
		echo "<a href='table.php?table=Customer'>Особисті дані</a>&nbsp| ";
		echo "<a href='table.php?table=`order`'>Замовлення</a>&nbsp| ";
		echo "<a href='table.php?table=prorder'>Замовлені продукти</a>&nbsp| ";
		}
		else
		{
			$res = mysql_query("SHOW TABLES");
			while ($res_row = mysql_fetch_row($res))
			{
				$t = $res_row[0];
				if (is_log($t)) continue;
			echo "<a href='table.php?table=$t'>$t</a>&nbsp| ";
			}
		}
		echo "</p>";
	}
	function echo_table($table, $userid)
	{
		echo "<table border='1'><tr>";
			$columns = column_names($table);
			foreach ($columns as $key => $cn)
					echo "<td>$cn</td>";
		if($userid!=-1)
		{
		if ($table != "prorder")
			$res = mysql_query("SELECT * FROM $table WHERE idCustomer = $userid");
		if ($table == "prorder")
	$res = mysql_query("SELECT *  FROM $table ,`Order` WHERE `Order`.idCustomer = $userid AND `order`.idOrder = $table.idOrder");

		$prim = primary_key($table);
		while ($res_row = mysql_fetch_assoc($res))
		{
			echo "<tr>";
			foreach ($columns as $key => $cn)
				echo "<td>".$res_row[$cn]."</td>";
			echo "<td><a href='edit.php?action=update&table=$table&pk=".$res_row[$prim]."'>Оновити</a></td>";
		if ($table != "Customer")
echo "<td><a href='edit.php?action=delete&table=$table&pk=".$res_row[$prim]."'>Видалити</a></td>";
			echo "</tr>";
		}
		echo "</tr>";
		echo "</table>";
		if ($table != "Customer")
			echo "<p><a href='edit.php?action=insert&table=$table'>Додати запис</a></p>";
		}
		else
		{
		$prim = primary_key($table);
		$res = mysql_query("SELECT * FROM $table");
		while ($res_row = mysql_fetch_assoc($res))
		{
			echo "<tr>";
			foreach ($columns as $key => $cn)
				echo "<td>".$res_row[$cn]."</td>";
				echo "<td><a href='edit.php?action=update&table=$table&pk=".$res_row[$prim]."'>Оновити</a></td>";
				echo "<td><a href='edit.php?action=delete&table=$table&pk=".$res_row[$prim]."'>Видалити</a></td>";
			echo "</tr>";
		}
		echo "</tr>";
		echo "</table>";
			echo "<p><a href='edit.php?action=insert&table=$table'>Додати запис</a></p>";
		}
	}

?>