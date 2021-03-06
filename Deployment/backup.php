<?php

/////////////////////////////////////////
// DATABE CONFIGURATIONS ////////////////
/////////////////////////////////////////
$mysql_host = '127.0.0.1';
$mysql_username = 'root';
$mysql_password = '';
/////////////////////////////////////////
/////////////////////////////////////////
/////////////////////////////////////////

$mysql_database = 'erp';

backup_tables($mysql_host, $mysql_username, $mysql_password, $mysql_database);

function backup_tables($host,$user,$pass,$name,$tables = '*')
{   
    mysql_connect($host, $user, $pass) or die('Error connecting to MySQL server: ' . mysql_error());
    mysql_set_charset("utf8");

    mysql_select_db($name) or die('Error selecting MySQL database: ' . mysql_error());
    
    //get all of the tables
    if($tables == '*')
    {
        $tables = array();
        $result = mysql_query('SHOW TABLES');
        while($row = mysql_fetch_row($result))
        {
            $tables[] = $row[0];
        }
    }
    else
    {
        $tables = is_array($tables) ? $tables : explode(',',$tables);
    }
    
    $return = "";

    //cycle through
    foreach($tables as $table)
    {
        $result = mysql_query('SELECT * FROM '.$table);
        $num_fields = mysql_num_fields($result);
        
        $return .= 'DROP TABLE '.$table.';';
        $row2 = mysql_fetch_row(mysql_query('SHOW CREATE TABLE '.$table));
        $return .= "\n\n".$row2[1].";\n\n";
        
        for ($i = 0; $i < $num_fields; $i++) 
        {
            while($row = mysql_fetch_row($result))
            {
                $return.= 'INSERT INTO '.$table.' VALUES(';
                for($j=0; $j < $num_fields; $j++) 
                {
                    $row[$j] = addslashes($row[$j]);
                    $row[$j] = ereg_replace("\n","\\n",$row[$j]);
                    if (isset($row[$j])) { $return.= '"'.$row[$j].'"' ; } else { $return.= '""'; }
                    if ($j < ($num_fields-1)) { $return.= ','; }
                }
                $return.= ");\n";
            }
        }
        $return .= "\n\n\n";
    }
    
    //save file
    // date("Y/m/d")
    $filename = 'erp-backup-'.date('Y-m-d-H-i-s-a').'.sql';
    $handle = fopen($filename,'w+');
    fwrite($handle, $return);
    fclose($handle);
    echo "Database backup completed successfully. Filename = ".$filename;
}

?>