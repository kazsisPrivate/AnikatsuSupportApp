<?php
// HTTP�w�b�_��ݒ�
$channelToken = '';
$headers = [
	'Authorization: Bearer ' . $channelToken,
	'Content-Type: application/json',
	'charset=UTF-8',
];

var_dump($headers);

// line��groupid���擾����
$fh = fopen("line_groupid.txt", "r");
$groupid = fgets($fh);

// sendmsg.txt�t�@�C���̒��g��ǂݍ���
$contents = file_get_contents("sendmsg.txt");

//POST�f�[�^��ݒ肵��JSON�ɃG���R�[�h
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




// HTTP���N�G�X�g��ݒ�
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

// ���s
$result = curl_exec($ch);

// �G���[�`�F�b�N
$errno = curl_errno($ch);
if ($errno) {
	echo "Some problems occured in this request.";

	return;
}

// HTTP�X�e�[�^�X���擾
$info = curl_getinfo($ch);
$httpStatus = $info['http_code'];

$responseHeaderSize = $info['header_size'];
$body = substr($result, $responseHeaderSize);

// 200 �������� OK
echo $httpStatus . ' ' . $body;