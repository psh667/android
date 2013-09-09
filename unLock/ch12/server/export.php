<?php
header("Content-Type: application/vns.ms-excel");
header('Cache-Control: maxage=10'); 
header('Pragma: public');
header('Content-Disposition: attachment; filename="'.$_GET['id'].'_jobs.csv"');
require('db.php');
require('utils.php');
$out = fopen('php://output', 'w');
getJobsToCSV($out,$_GET['id']);
fclose($out);
?>
