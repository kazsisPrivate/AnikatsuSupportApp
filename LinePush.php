<?php
// HTTPヘッダを設定
$channelToken = '';
$headers = [
	'Authorization: Bearer ' . $channelToken,
	'Content-Type: application/json',
	'charset=UTF-8',
];

var_dump($headers);

// lineのgroupidを取得する
$fh = fopen("line_groupid.txt", "r");
$groupid = fgets($fh);

// sendmsg.txtファイルの中身を読み込む
$contents = file_get_contents("sendmsg.txt");

//POSTデータを設定してJSONにエンコード
$post = [
	"to" => $groupid,
	"messages" => [
		[
			"type" => "text",
			"text" => $contents,
		],
	],
];
$post = json_encode($post);




// HTTPリクエストを設定
$ch = curl_init('https://api.line.me/v2/bot/message/push');
$options = [
	CURLOPT_CUSTOMREQUEST => 'POST',
	CURLOPT_HTTPHEADER => $headers,
	CURLOPT_RETURNTRANSFER => true,
	CURLOPT_BINARYTRANSFER => true,
	CURLOPT_HEADER => true,
	CURLOPT_POSTFIELDS => $post,
	CURLOPT_SSL_VERIFYPEER => false,
];
curl_setopt_array($ch, $options);

// 実行
$result = curl_exec($ch);

// エラーチェック
$errno = curl_errno($ch);
if ($errno) {
	echo "Some problems occured in this request.";

	return;
}

// HTTPステータスを取得
$info = curl_getinfo($ch);
$httpStatus = $info['http_code'];

$responseHeaderSize = $info['header_size'];
$body = substr($result, $responseHeaderSize);

// 200 だったら OK
echo $httpStatus . ' ' . $body;