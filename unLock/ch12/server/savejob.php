<?
require('header.php');
require('db.php');
require('utils.php');
$result = putJob($_POST['id'],$_POST['customer'],$_POST['address'],$_POST['city'],$_POST['state'],$_POST['zip'],$_POST['product'],$_POST['producturl'],$_POST['comments']);
print("result is $result<br><br>");
if ($result == "SUCCESS")
{
	print("Job Added.");
	print("<br><a href=showjobs.php?id=".$_POST['id'].">View Jobs</a>");
}
else
{
	print("Failed to Add Job.");
}
require('footer.php');
?>


