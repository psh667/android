<?require ('header.php'); ?>
<br />
<br />
<form method="POST" action="savejob.php">
<table>
<tr><td>User/Identifier:</td><td><input type=text readonly name=id value=<? print("'".$_GET['id']."'"); ?>></td></tr>
<tr><td>Customer:</td><td><input name="customer" maxlength="50"></td></tr>
<tr><td>Address:</td><td><input name="address" maxlength="50"></td></tr>
<tr><td>City:</td><td><input name="city" maxlength="50"></td></tr>
<tr><td>State:</td><td><input name="state" maxlength="2"></td></tr>
<tr><td>Zip:</td><td><input name="zip" maxlength="10"></td></tr>
<tr><td>Product:</td><td><input name="product" maxlength="50"></td></tr>
<tr><td>Product URL:</td><td><input name="producturl" maxlength="100"></td></tr>
<tr><td>Comments:</td><td><input name="comments" maxlength="100"></td></tr>
</table>
<input type=submit value="Add Job">
</form><br>
<br>
<? require('footer.php'); ?>
</body>
</html>

