package function.sns;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.ResponseList;
import twitter4j.Paging;
import twitter4j.User;
import twitter4j.api.TimelinesResources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;


public class TwitterUser {

    private Twitter twitter; // Twitterのアカウント(aniaktsu_kyutech)のインスタンス
    // TwitterDeveloverでのアプリ名としてはAnikatsuSystemとして登録

    private String aniken_id; // リツイートする際、対象とするTwitterアカウントのスクリーンID
    
    private final String SEARCH_WORD = "#anikatsu";


    public TwitterUser() {
        openApp();

        // queryid.txtファイルからクエリするときに使用するTwitterのScreenIDを取得
        File log_file = new File("queryid.txt");
        try {
            // queryid.txtを読み込むためのFileReaderクラスとBufferedReaderクラスのオブジェクトを生成
            FileReader fr = new FileReader(log_file);
            BufferedReader br = new BufferedReader(fr);
            // クエリするときに使用するTwitterのスクリーンIDを取得
            aniken_id = br.readLine();
            // ファイルを閉じる
            br.close();
        } catch (IOException e) {
            System.out.println("queryid.txtファイルの読み込みに失敗しました。");
            e.printStackTrace();
        }

    }


    public static void main(String args[]) {
        TwitterUser twuser = new TwitterUser();
        //twuser.post("testです。");

        HashMap<String, String> qr = new HashMap<>();
        if (twuser.QueryAnikatsuTweet(qr)) {
            System.out.println(qr.get("日時"));
            System.out.println(qr.get("本文"));
        }
        else {
            System.out.println("ツイートが見つかりませんでした。");
        }

    }


    protected void openApp() {
        /* Twitterのアカウントのインスタンスを取得
         */
        twitter = new TwitterFactory().getInstance();
    }


    public void post(String msg) throws TwitterException {
        /* 引数で渡されたメッセージをツイートする
        @param msg: ツイートするメッセージの内容
         */
        twitter.updateStatus(msg);
    }

//    public boolean QueryAnikatsuTweet(HashMap<String, String> qr) {
//        /* 最新の部会のツイートを検索し、そのツイートの本文、ツイートの
//        年月日時刻をマップqrに格納する
//        部会のツイートには #meeting というハッシュタグをつけているものとする
//        #meeting のツイートが見つからないときはfalseを返す
//         */
//        return QueryAnikenTweet(qr, "#anikatsu");
//    }

    public boolean QueryAnikatsuTweet(HashMap<String, String> qr) {
        /* アニ研(@kitiarc)のツイートの中で、単語SEARCH_WORDに関連するツイートの
        本文、ツイートの年月日時刻をマップqrに格納する
         */
        TimelinesResources timeline = twitter.timelines();
        // ページングの設定
        Paging paging = new Paging();
        paging.setPage(1);
        paging.count(200);

        // 検索する
        try {
            ResponseList<Status> tweets = timeline.getUserTimeline(aniken_id, paging);

            // 検索結果をマップqrに格納する
            for (Status tweet : tweets) {

                // 投稿された日時
                System.out.println(tweet.getCreatedAt());

                if (tweet.getText().contains(SEARCH_WORD)) {
                    // sch_wordに関連するツイートを取得
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒");
                    qr.put("日時", sdf.format(tweet.getCreatedAt()));
                    qr.put("本文", tweet.getText());

                    break;
                }
            }
        } catch(TwitterException te) {
            System.out.println("タイムラインの取得に失敗しました。");
        }

        if (qr.get("日時") == null) { // sch_wordに関連するツイートが見つからなかったとき
            qr.put("検索結果", "@" + aniken_id + "の最新200件のツイートの中には"
                    + SEARCH_WORD + "に関連するものが見つかりませんでした。");

            return false;
        }
        else { // sch_wordに関連するツイートが見つかったとき
            qr.put("検索結果", "@" + aniken_id + "の" + qr.get("日時")
                    + "のツイートをLineのグループに投稿しますか？");

            return true;
        }
    }
}
