package function.sns;

import java.io.*;
import java.lang.ProcessBuilder;
import java.lang.Runtime;
import java.util.ArrayList;
import java.util.List;


public class LineUser {


    public LineUser() { }

    public void post(final String msg) throws IOException {
        /* 引数で渡されたメッセージを投稿する
        @param msg: 投稿するメッセージの内容
         */
        // 投稿するメッセージを入れるテキストファイルを作成する
        File fileOut = new File("sendmsg.txt");
        PrintWriter pw = new PrintWriter
                (new BufferedWriter(new OutputStreamWriter
                        (new FileOutputStream(fileOut), "UTF-8")));
        // テキストファイルに書き込む
        pw.write(msg);
        // テキストファイルを閉じる
        pw.close();

        // LinePush.phpを実行することによりLineへmsgを投稿する
        Process p = Runtime.getRuntime().exec(new String[]{"cmd", "/c", "php", "LinePush.php"});

        String res = null; // 送信の出力結果を返す
        InputStream ism = p.getInputStream();
        InputStreamReader reader = new InputStreamReader(ism, "Shift_JIS");
        BufferedReader br = new BufferedReader(reader);
        // 結果文のリスト
        List<String> res_list = new ArrayList<>();
        // 出力をリストに追加
        while ((res = br.readLine()) != null) {
            System.out.println(res);
            res_list.add(res);
        }

        // 送信に成功したかの確認
        String retrun_code = res_list.get(res_list.size() - 1).substring(0, 3);
        if (!retrun_code.equals("200")) {
            throw new IOException("LINE送信失敗したよ。");
        }
    }

}
