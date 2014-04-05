<?php
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
	function echo_reader1()
	{		
		if (!isset($_COOKIE["username"]))	
			header("Location: /");
		echo "<p>Ви увійшли як <b>".$_COOKIE["username"].
			"</b> <a href='/?action=logout'>(вийти)</a>.</p> ";
		echo "<p>|&nbsp";
		$res = mysql_query("SHOW TABLES");
			while ($res_row = mysql_fetch_row($res))
			{
				$t = $res_row[0];
				if (is_log($t)) continue;
			echo "<a href='table.php?tab1=$t'>$t</a>&nbsp| ";
			}
		echo "</p>";
	}
	function echo_reader2($table)
	{		
		if (!isset($_COOKIE["username"]))	
			header("Location: /");
		echo "<p>Ви увійшли як <b>".$_COOKIE["username"].
			"</b> <a href='/?action=logout'>(вийти)</a>.</p> ";
		echo "<p>|&nbsp";
		$res = mysql_query("SHOW TABLES");
			while ($res_row = mysql_fetch_row($res))
			{
				$t = $res_row[0];
				if (is_log($t)) continue;
			echo "<a href='table.php?tab1=$table&tab2=$t'>$t</a>&nbsp| ";
			}
		echo "</p>";
	}
/*62*/	function echo_table($table1, $table2)
	{
		echo "<table border='1'><tr>";
			$columns1 = column_names($table1);
			$columns2 = column_names($table2);
			if(compare($columns1,$columns2)&&compare($columns2,$columns1))
				{
					echo "<p>Відмінності таблиці <b>$table1</b> :</p>";
					foreach ($columns1 as $key => $cn1)
						echo "<td>$cn1</td>";
					echo "</tr>";
				}
			else 
			{
			echo "<p>Таблиці мають різну структуру!</p>";
			
			echo "<p>Структура таблиці <b>$table1</b> :</p>";
			foreach ($columns1 as $key1 => $cn1)
						echo "<td>$cn1</td>";
			echo "</tr></table>";
			echo "<table border='1'><tr>";
			echo "<p>Структура таблиці <b>$table2</b> :</p>";
			foreach ($columns2 as $key2 => $cn2)
						echo "<td>$cn2</td>";
			echo "</tr></table>";
			return null;
			}
		$big=true;
		$small=false;
		$res1 = mysql_query("SELECT * FROM $table1");
		$res2 = mysql_query("SELECT * FROM $table2");
		while ($row1 = mysql_fetch_assoc($res1))
		{
			$small=false;
			while ($row2 = mysql_fetch_assoc($res2))
			{
				if(compare($row1,$row2)&&compare($row2,$row1))
					$small=true;
			}
/*84*/			if (!$small)
			{
				echo "<tr>";
				foreach ($columns1 as $key => $cn1)
					echo "<td>".$row1[$cn1]."</td>";
				echo "</tr>";
				$big=false;
			}
			$res2 = mysql_query("SELECT * FROM $table2");
		}
		echo "</tr></table>";
		echo "<p>Відмінності таблиці <b>$table2</b> :</p>";
		echo "<table border='1'><tr>";
		
		foreach ($columns2 as $key => $cn2)
				echo "<td>$cn2</td>";
		$res1 = mysql_query("SELECT * FROM $table1");
		$res2 = mysql_query("SELECT * FROM $table2");
		echo "</tr>";
		while ($row2 = mysql_fetch_assoc($res2))
		{
			$small=false;
			while ($row1 = mysql_fetch_assoc($res1))
			{
				if(compare($row1,$row2)&&compare($row2,$row1))
					$small=true;
			}
			if (!$small)
			{
				echo "<tr>";
				foreach ($columns2 as $key => $cn2)
					echo "<td>".$row2[$cn2]."</td>";
				echo "</tr>";
				$big=false;
			}
			$res1 = mysql_query("SELECT * FROM $table1");
		}
		echo "</tr>";
		echo "</table>";

		if ($big) echo "<p><b>Таблиці однакові!</b></p>";
		
	}
	function compare($col1,$col2)
	{
	$big=true;
	foreach ($col1 as $key1 => $c1)
		{
		$small=false;
			foreach ($col2 as $key2 => $c2)
				if($c1==$c2) $small = true;
		if (!$small) $big=false;	
		}
	return $big;
	}
?>