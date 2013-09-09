<?
require('db.php');
require('utils.php');
require('header.php');
$theid = $_POST['id'];
if ($theid == "")
{
	$theid = $_GET['id']; 
}
?>
<html>
<head>
<title>Android @ MSI Wireless Jobs</title>
</head>
<body>
<h3>Job List for <? print('['.$theid.']'); ?>.</h3>
<hr />
<? getJobs($theid);?>
<br>
<a href="/">Home</a><br>
<? require('footer.php'); ?>
</body>
</html>


