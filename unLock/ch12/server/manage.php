<?
require('db.php');
require('utils.php');
require('header.php');
?>
<? 

$action=$_GET['action'];

if ($action == 'remove')
{
   print("Removing Jobs.<br>");
   killJobs($_GET['id']); 
}
if ($action == 'removeone')
{
	print("Removing single job.<br>");
	killOneJob($_GET['jobid']);
        getJobs($_GET['id']); 
}
?>
<a href="/">Home</a><br>
<? require('footer.php'); ?>
</body>
</html>


