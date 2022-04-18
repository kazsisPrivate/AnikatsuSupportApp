package component.home.anikatsudb;

import javax.swing.*;

import function.BasicFunction;
import function.button.ButtonResponse;
import component.home.anikatsudb.member.MbrDBOprMgr;
import component.home.anikatsudb.budget.BgtDBOprMgr;
import component.home.anikatsudb.festival.FesDBOprMgr;
import component.home.anikatsudb.presentation.PstDBOprMgr;
import component.home.anikatsudb.purchase.PchsDBOprMgr;
import function.db.DBOprMgr;


public class AnikatsuDB extends JPanel {

    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅

    public static final int MEMBER_MGR = 0;
    public static final int BUDGET_MGR = 1;
    public static final int PURCHASE_MGR = 2;
    public static final int PRESENTATION_MGR = 3;
    public static final int FESTIVAL_MGR = 4;

    private DBOprMgr dbopr_mgr; // その時操作しているDBMgrのオブジェクトを入れておく

    private ButtonResponse bt_resp; // コンストラクタで受け取ったHomeでの処理を入れる
    private BasicFunction bc_func;


    public AnikatsuDB(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;
    }


    public void setDBManager(final int mgr) {
        /* 指定された種類のオブジェクトをセット、表示する
        @param mgr: 操作するデータの種類
         */
        // 指定された種類のオブジェクトをセットする
        if (mgr == MEMBER_MGR) { // 部員の情報を操作するとき
            dbopr_mgr = new MbrDBOprMgr(bt_resp, bc_func);
        }
        else if (mgr == BUDGET_MGR) { // 部費の情報を操作するとき
            dbopr_mgr = new BgtDBOprMgr(bt_resp, bc_func);
        }
        else if (mgr == PURCHASE_MGR) { // 購入品の情報を操作する
            dbopr_mgr = new PchsDBOprMgr(bt_resp, bc_func);
        }
        else if (mgr == PRESENTATION_MGR) { // 研究発表の情報を操作する
            dbopr_mgr = new PstDBOprMgr(bt_resp, bc_func);
        }
        else { // 工大祭の情報を操作する
            dbopr_mgr = new FesDBOprMgr(bt_resp, bc_func);
        }

        // 位置設定
        dbopr_mgr.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        // 指定された種類の選択画面を表示する
        add(dbopr_mgr, 0);
        repaint();
    }


    private class MgrResponse implements ButtonResponse {
        @Override
        public void pushYes() { }

        @Override
        public void pushNo() { }

        @Override
        public void pushClose() {
            // 画面に表示している選択画面を取り除く
            remove(dbopr_mgr);
            repaint();
        }
    }
}
