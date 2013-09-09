<?
//print("utils loading");
function putJob($id,$customer,$address,$city,$state,$zip,$product,$producturl,$comments)
{
global $mysql_link;

$sql = "insert tbl_jobs (identifier,customer,address,city,state,zip,product,producturl,comments) values ('$id','$customer','$address','$city','$state','$zip','$product','$producturl','$comments')";
//print($sql);
$result = mysql_query($sql,$mysql_link);
if ($result == 1)
{
	return "SUCCESS";
}
else
{
	return "FAILED";
}

}

function updateJob($id,$customer,$address,$city,$state,$zip,$product,$producturl,$comments,$status)
{
global $mysql_link;

$sql = "update tbl_jobs set status='".$status."',customer = '".$customer."',address='".$address."',city='".$city."',state='".$state."',zip='".$zip."',product='".$product."',producturl='".$producturl."',comments='".$comments."' where jobid = ".$id;
//print($sql);
$result = mysql_query($sql,$mysql_link);
if ($result == 1)
{
	return "SUCCESS";
}
else
{
	return "FAILED";
}

}

function showJob($id)
{
global $mysql_link;


$COL_JOBID = 0;
$COL_STATUS = 1;
$COL_IDENTIFIER = 2;
$COL_ADDRESS = 3;
$COL_CITY = 4;
$COL_STATE = 5;
$COL_ZIP = 6;
$COL_CUSTOMER = 7;
$COL_PRODUCT = 8;
$COL_PRODUCTURL = 9;
$COL_COMMENTS = 10;
$sql ="select * from tbl_jobs where jobid = ".$id;
$result = mysql_query($sql,$mysql_link);
  if(mysql_num_rows($result))
  {
	print("<form method=POST action=updatejob.php>");
        print("<table>");
      $row = mysql_fetch_row($result);
	print("<tr><td>Identifier (User)</td><td><input type=text readonly name=identifier value='".$row[$COL_IDENTIFIER]."'><td></tr>");
	print("<tr><td>Job Id</td><td><input type=text readonly name=jobid value=".$row[$COL_JOBID]."></td></tr>");
	print("<tr><td>Status</td><td><input maxlength=10 type=text name=status value='".$row[$COL_STATUS]."'></td></tr>");
	print("<tr><td>Customer</td><td><input maxlength=50 type=text name=customer value='".$row[$COL_CUSTOMER]."'></td></tr>");
	print("<tr><td>Address</td><td><input maxlength=50 type=text name=address value='".$row[$COL_ADDRESS]."'></td></tr>");
	print("<tr><td>City</td><td><input maxlength=30 type=text name=city value='".$row[$COL_CITY]."'></td></tr>");
	print("<tr><td>State</td><td><input maxlength=2 type=text name=state value='".$row[$COL_STATE]."'></td></tr>");
	print("<tr><td>Zip</td><td><input maxlength=10 type=text name=zip value='".$row[$COL_ZIP]."'></td></tr>");
	print("<tr><td>Product</td><td><input maxlength=50 type=text name=product value='".$row[$COL_PRODUCT]."'></td></tr>");
	print("<tr><td>Product Url</td><td><input maxlength=100 type=text name=producturl value='".$row[$COL_PRODUCTURL]."'></td></tr>");
	print("<tr><td>Comments</td><td><input maxlength=100 type=text name=comments value='".$row[$COL_COMMENTS]."'></td></tr>");
	print("</table>");
  	print("<input type=submit value='Update Job Record'></form>");
        print("<br><br><a href='manage.php?action=removeone&id=".$row[$COL_IDENTIFIER]."&jobid=".$row[$COL_JOBID]."'>Delete Job</a>");
 
   }
}






function closeJob($id)
{
global $mysql_link;

$sql = "update tbl_jobs set status = 'CLOSED' where jobid = ".$id;
//print ($sql);
if (mysql_query($sql,$mysql_link))
{
	return "SUCCESS";
}
else
{
	return "FAILED";
}
}


function getJobsXML($id) 
{
global $mysql_link;
$COL_JOBID = 0;
$COL_STATUS = 1;
$COL_IDENTIFIER = 2;
$COL_ADDRESS = 3;
$COL_CITY = 4;
$COL_STATE = 5;
$COL_ZIP = 6;
$COL_CUSTOMER = 7;
$COL_PRODUCT = 8;
$COL_PRODUCTURL = 9;
$COL_COMMENTS = 10;
//$result = mysql_query("SELECT * from tbl_jobs where identifier = '$id' and status = 'OPEN' order by jobid", $mysql_link);
$result = mysql_query("SELECT * from tbl_jobs where identifier = '$id' order by jobid", $mysql_link);
  if(mysql_num_rows($result))
  {
      print("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
      print("<joblist>\n");
      while($row = mysql_fetch_row($result))
      {
	  print("<job><id>$row[$COL_JOBID]</id><status>$row[$COL_STATUS]</status><customer>$row[$COL_CUSTOMER]</customer><address>$row[$COL_ADDRESS]</address><city>$row[$COL_CITY]</city><state>$row[$COL_STATE]</state><zip>$row[$COL_ZIP]</zip><product>$row[$COL_PRODUCT]</product><producturl>$row[$COL_PRODUCTURL]</producturl><comments>$row[$COL_COMMENTS]</comments></job>\n");
      }
      print("</joblist>\n");
  }
}



function getJobs($id) 
{
global $mysql_link;
$COL_JOBID = 0;
$COL_STATUS = 1;
$COL_IDENTIFIER = 2;
$COL_ADDRESS = 3;
$COL_CITY = 4;
$COL_STATE = 5;
$COL_ZIP = 6;
$COL_CUSTOMER = 7;
$COL_PRODUCT = 8;
$COL_PRODUCTURL = 9;
$COL_COMMENTS = 10;
$result = mysql_query("SELECT * from tbl_jobs where identifier = '$id' order by jobid", $mysql_link);
  if(mysql_num_rows($result))
  {
      print("<table border=1><tr><td>Job Id#</td><td>Customer</td><td>Address</td><td>City</td><td>State</td><td>Zip</td><td>Product</td><td>Product URL</td><td>Comments</td><td>Status</td></tr>");
      while($row = mysql_fetch_row($result))
      {
          print("<tr><td><a href='showjob.php?jobid=$row[$COL_JOBID]'>$row[$COL_JOBID]</a></td><td>$row[$COL_CUSTOMER]</td><td>$row[$COL_ADDRESS]</td><td>$row[$COL_CITY]</td><td>$row[$COL_STATE]</td><td>$row[$COL_ZIP]</td><td>$row[$COL_PRODUCT]</td><td><a target='_blank' href='$row[$COL_PRODUCTURL]'>$row[$COL_PRODUCTURL]</a></td><td>$row[$COL_COMMENTS]</td><td>$row[$COL_STATUS]</td></tr>");
      }
      print("</table>");
      print("<a target='_blank' href='export.php?id=$id'>Export Your Job List</a>");
      print("<br>");
      //print("<a href='manage.php?id=$id&action=remove'>Delete Your Jobs</a>");
      print("<br>");
  }
  else
  {
	print("There are no Jobs available.");
  }
	print("<br>Add a <a href='addjob.php?id=$id'>Job</a><br>");
}

function getJobsToCSV($out,$id) 
{
global $mysql_link;
$result = mysql_query("SELECT * from tbl_jobs where identifier = '$id' order by jobid", $mysql_link);
  if(mysql_num_rows($result))
  {
      fputcsv($out,array("Job Id #","Status","Identifier","Customer","Product","Product URL","Comments"));
      while($row = mysql_fetch_array($result,MYSQL_NUM))
      {
	fputcsv($out,$row);
      }
  }
}



function killJobs($id)
{
global $mysql_link;

$sql = "delete from tbl_jobs where identifier ='$id'";
//print($sql);
$result = mysql_query($sql,$mysql_link);
print($result);

}



function killOneJob($jobid)
{
global $mysql_link;

$sql = "delete from tbl_jobs where jobid ='$jobid'";
//print($sql);
$result = mysql_query($sql,$mysql_link);
print($result);

}

?>
