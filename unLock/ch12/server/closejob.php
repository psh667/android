<?
require('db.php');
require('utils.php');
$data = file_get_contents('php://input');
$jobid = $_GET['jobid'];
$f = fopen("pathtowebsite/sigs/".$jobid.".jpg","w");
fwrite($f,$data);
fclose($f);
print(closeJob($_GET['jobid']));
?>


