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
$filename = 'erp_db.sql';

mysql_connect($mysql_host, $mysql_username, $mysql_password) or die('Error connecting to MySQL server: ' . mysql_error());
mysql_select_db($mysql_database) or die('Error selecting MySQL database: ' . mysql_error());


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
 echo "Tables imported successfully";
?>