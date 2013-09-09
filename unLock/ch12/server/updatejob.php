<?
require('header.php');
require('db.php');
require('utils.php');
$result = updateJob($_POST['jobid'],$_POST['customer'],$_POST['address'],$_POST['city'],$_POST['state'],$_POST['zip'],$_POST['product'],$_POST['producturl'],$_POST['comments'],$_POST['status']);
print("result is $result<br><br>");
if ($result == "SUCCESS")
{
	print("Job Updated.");
	print("<br><a href=showjobs.php?id=".$_POST['identifier'].">View Jobs</a>");
}
else
{
	print("Failed to Update Job.");
}
require('footer.php');
?>


