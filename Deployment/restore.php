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

/////////////////////////////////////////
// BACKUP FILE NAME /////////////////////
/////////////////////////////////////////
$filename = 'backp-file-name.sql';
/////////////////////////////////////////
/////////////////////////////////////////
/////////////////////////////////////////


$mysql_database = 'erp';

mysql_connect($mysql_host, $mysql_username, $mysql_password) or die('Error connecting to MySQL server: ' . mysql_error());
mysql_select_db($mysql_database) or die('Error selecting MySQL database: ' . mysql_error());


mysql_query('SET foreign_key_checks = 0');
mysql_query("SELECT Concat('TRUNCATE TABLE ',table_schema,'.',TABLE_NAME, ';') FROM erp.TABLES where  table_schema in ('erp');");

$templine = '';
$lines = file($filename);
foreach ($lines as $line)
{
if (substr($line, 0, 2) == '--' || $line == '')
    continue;

$templine .= $line;
if (substr(trim($line), -1, 1) == ';')
{
    mysql_query($templine) or print('Error performing query \'<strong>' . $templine . '\': ' . mysql_error() . '<br /><br />');
    $templine = '';
}
}
	mysql_query('SET foreign_key_checks = 1');
 echo "Resoratoring the backup file contents completed successfully.";
?>