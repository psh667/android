<?
require('db.php');
require('utils.php');
$theid = $_GET['identifier'];
print (getJobsXML($theid));
?>
