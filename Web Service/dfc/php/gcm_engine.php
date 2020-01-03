<?php
include 'config.php';
$message = $_POST['message'];

$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE); //Kết nối database.
mysqli_set_charset($con,'utf8');
$query = "SELECT RegID FROM `accounts`";
$result = mysqli_query($con,$query);
$regarr = array();
while($row = mysqli_fetch_assoc($result))
{
	array_push($regarr, $row["RegID"]);
}
mysqli_close($con);
// Set POST variables
$url = 'https://android.googleapis.com/gcm/send';

$fields = array(
                'registration_ids'  => $regarr,
                'data'              => array( "message" => $message ),
                );

$headers = array(
                    'Authorization: key=' . GOOGLE_API_KEY,
                    'Content-Type: application/json'
                );

// Open connection
$ch = curl_init();

// Set the url, number of POST vars, POST data
curl_setopt( $ch, CURLOPT_URL, $url );
curl_setopt( $ch, CURLOPT_POST, true );
curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers);
curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
curl_setopt( $ch, CURLOPT_POSTFIELDS, json_encode( $fields ) );

// Execute post
$result = curl_exec($ch);

curl_close($ch);
echo $result;
?>