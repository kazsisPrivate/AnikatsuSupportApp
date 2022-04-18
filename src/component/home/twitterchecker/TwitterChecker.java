package component.home.twitterchecker;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

import function.BasicFunction;
import function.sns.*;
import function.button.ButtonResponse;
import function.confirmation.Confirmer;


public class TwitterChecker extends JPanel {

    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅

    private LineUser ln_user; // LineUserクラスのインスタンス
    private TwitterUser tw_user; // TwitterUserクラスのインスタンス

    private HashMap<String, String> twckr_qr; // 対象とするツイートのクエリの検索結果を持つマップ

    private Confirmer tc_confirmer; // TwitterCheckerボタンが押された際に表示される確認のインスタンス

    private ButtonResponse bt_resp; // コンストラクタで受け取ったPchsDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる

    private final Font ST_FONT = new Font("MS Pゴシック", Font.BOLD, 50); // 質問文のフォント


    public TwitterChecker(final ButtonResponse br, final BasicFunction bf, final TwitterUser tu, final LineUser lu) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // SNS関連のクラスのオブジェクトの取得
        ln_user = lu;
        tw_user = tu;

        // Twitterから取り出したツイートの情報を核のするマップを作成
        twckr_qr = new HashMap<>();
    }

    private class ConfResponse implements ButtonResponse {
        @Override
        public void pushYes() {
            try {
                // Lineのグループに対象とするツイートを投稿する
                ln_user.post(twckr_qr.get("日時") + "\n\n" + twckr_qr.get("本文"));
                // ログの追加
                bc_func.addLog("ツイート日時: " + twckr_qr.get("日時")
                        + " のTwitterでのツイートを、Lineに投稿しました。");
            } catch (IOException e) {
                // ログの追加
                bc_func.addLog("ツイート日時: " + twckr_qr.get("日時")
                        + " のTwitterでのツイートを、Lineに投稿するのに失敗しました。");
            }

            // 質問のパネルを取り除く
            remove(tc_confirmer);

            // homeでの処理をする
            bt_resp.pushClose();
        }

        @Override
        public void pushNo() {
            // 質問のパネルを取り除く
            remove(tc_confirmer);

            // homeでの処理をする
            bt_resp.pushClose();
        }

        @Override
        public void pushClose() {
            // 質問のパネルを取り除く
            remove(tc_confirmer);

            // homeでの処理をする
            bt_resp.pushClose();
        }
    }

    public void addConfirmer() {
        // Twitterから対象とするツイートを取り出す
        if (tw_user.QueryAnikatsuTweet(twckr_qr)) { // 対象とするツイートが見つかったとき
            tc_confirmer = new Confirmer(new ConfResponse(), twckr_qr.get("検索結果"), ST_FONT, Confirmer.YES_NO);
        }
        else { // 対象とするツイートが見つからなかったとき
            tc_confirmer = new Confirmer(new ConfResponse(), twckr_qr.get("検索結果"), ST_FONT, Confirmer.CLOSE);
        }
        // パネルの位置の設定
        tc_confirmer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        add(tc_confirmer, 0);
    }

}
