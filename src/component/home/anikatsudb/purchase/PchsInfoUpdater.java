package component.home.anikatsudb.purchase;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import function.BasicFunction;
import function.button.ButtonResponse;
import function.confirmation.Confirmer;
import function.db.DBOperator;


public class PchsInfoUpdater extends DBOperator {

    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅

    private ImageIcon frame; // フレームの画像
    private JLabel frame_label; // フレームの画像のラベル
    private final int FM_WIDTH = 860; // フレームの画像の幅
    private final int FM_HEIGHT = 980; // フレームの画像の高さ
    private final int FM_X = 50; // フレームの左上端のx座標
    private final int FM_Y = 30; // フレームの左上端のy座標

    private JLabel upd_icon_label; // Updateのアイコンの画像のラベル
    private ImageIcon upd_icon; // Updateのアイコンの画像
    private final int ICON_WIDTH = 270; // アイコン画像の幅
    private final int ICON_HEIGHT = 130; // アイコン画像の高さ
    private final int ICON_X = 5;//650; // アイコンの左上端のx座標
    private final int ICON_Y = 5; // アイコンの左上端のy座標

    private final int LB_FONT_SIZE = 30; // 表の上のラベルの文字の大きさ
    private final Font LABEL_FONT = new Font("HGPゴシックM", Font.PLAIN, LB_FONT_SIZE); // 表の上のラベルのフォント

    private final int ROW_HEIGHT = 25; // 表の一行の高さ
    private final Font TABLE_FONT = new Font("游明朝", Font.PLAIN, ROW_HEIGHT-5); // 表のフォント

    private final int FD_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/3; // 入力のフィールドの文字の大きさ
    private final Font FIELD_FONT = new Font("游明朝", Font.PLAIN, FD_FONT_SIZE); // 入力のフィールドのフォント

    private final int BT_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/6; // ボタンの文字の大きさ
    private final Font BUTTON_FONT = new Font("HGPゴシックM", Font.PLAIN, BT_FONT_SIZE); // 入力のフィールドのフォント

    private JTextField year_field; // 年を表示するテキストフィールド
    private final int YEARFD_WIDTH = LB_FONT_SIZE * 3; // 年を表示するテキストフィールドの幅
    private final int YEARFD_HEIGHT = LB_FONT_SIZE; // 年を表示するテキストフィールドの高さ
    private final int YEARFD_X = 280; // 年を表示するテキストフィールドの左上端のx座標
    private final int YEARFD_Y = 90; // 年を表示するテキストフィールドの左上端のy座標
    private JLabel year_label; // 入力フィールドの右に置く"年"というラベル
    private final int YEARLB_WIDTH = LB_FONT_SIZE * 1; // "年"というラベルの幅
    private final int YEARLB_HEIGHT = LB_FONT_SIZE; // "年"というラベルの高さ
    private final int YEARLB_X = YEARFD_X + YEARFD_WIDTH + LB_FONT_SIZE/3; // "年"というラベルの左上端のx座標
    private final int YEARLB_Y = YEARFD_Y - LB_FONT_SIZE/10; // "年"というラベルの左上端のy座標

    private JTextField month_field; // 月を表示するテキストフィールド
    private final int MONTHFD_WIDTH = LB_FONT_SIZE * 2; // 月を表示するテキストフィールドの幅
    private final int MONTHFD_HEIGHT = LB_FONT_SIZE; // 月を表示するテキストフィールドの高さ
    private final int MONTHFD_X = 420; // 月を表示するテキストフィールドの左上端のx座標
    private final int MONTHFD_Y = 90; // 月を表示するテキストフィールドの左上端のy座標
    private JLabel month_label; // 入力フィールドの右に置く"月の購入品情報"ラベル
    private final int MONTHLB_WIDTH = LB_FONT_SIZE * 7; // "月の購入品情報"というラベルの幅
    private final int MONTHLB_HEIGHT = LB_FONT_SIZE; // "月の購入品情報"というラベルの高さ
    private final int MONTHLB_X = MONTHFD_X + MONTHFD_WIDTH + LB_FONT_SIZE/3; // "月の購入品情報"というラベルの左上端のx座標
    private final int MONTHLB_Y = MONTHFD_Y - LB_FONT_SIZE/10; // "月の購入品情報"というラベルの左上端のy座標

    private JButton sch_button; // 検索ボタン
    private final int SCHBT_WIDTH = 90; // 検索ボタンの幅
    private final int SCHBT_HEIGHT = 50; // 検索ボタンの高さ
    private final int SCHBT_X = 730; // 検索ボタンの左上端のx座標
    private final int SCHBT_Y = 80; // 検索ボタンの左上端のy座標

    private DefaultTableModel pchs_table_model;
    private JTable pchs_table; // 購入品情報一覧表
    private JScrollPane pchs_table_spanel; // 上の表ににスクロールバーを付けたパネル
    private final int PCHS_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int PCHS_AREA_Y = 140; // スクロールパネルの左上端のy座標
    private final int PCHS_AREA_WIDTH = 800; // スクロールパネルの幅
    private final int PCHS_AREA_HEIGHT = 300; // スクロールパネルの高さ

    private JLabel pchs_info_label; // "変更後の情報"というラベル
    private final int PCHSIFLB_WIDTH = LB_FONT_SIZE * 9; // "変更後の情報"というラベルの幅
    private final int PCHSIFLB_HEIGHT = LB_FONT_SIZE; // "変更後の情報"というラベルの高さ
    private final int PCHSIFLB_X = FRAME_WIDTH/4 - PCHSIFLB_WIDTH/2; // "変更後の情報"というラベルの左上端のx座標
    private final int PCHSIFLB_Y = 480; // "変更後の情報"というラベルの左上端のy座標

    private JLabel pchs_num_label; // 入力フィールドの左に置く"購入品番号"というラベル
    private final int PCHSNUMLB_WIDTH = LB_FONT_SIZE * 5; // "購入品番号"というラベルの幅
    private final int PCHSNUMLB_HEIGHT = LB_FONT_SIZE; // "購入品番号"というラベルの高さ
    private final int PCHSNUMLB_X = 100; // "購入品番号"というラベルの左上端のx座標
    private final int PCHSNUMLB_Y = 530; // "購入品番号"というラベルの左上端のy座標
    private JTextField pchs_num_field; // 購入品番号を表示するテキストフィールド
    private final int PCHSNUMFD_WIDTH = LB_FONT_SIZE * 3; // 購入品番号を表示するテキストフィールドの幅
    private final int PCHSNUMFD_HEIGHT = LB_FONT_SIZE; // 購入品番号を表示するテキストフィールドの高さ
    private final int PCHSNUMFD_X = PCHSNUMLB_X + PCHSNUMLB_WIDTH + LB_FONT_SIZE/3; // 購入品番号を表示するテキストフィールドの左上端のx座標
    private final int PCHSNUMFD_Y = PCHSNUMLB_Y + LB_FONT_SIZE/10; // 購入品番号を表示するテキストフィールドの左上端のy座標

    private JLabel mbr_num_label; // 入力フィールドの左に置く"購入者の部員番号"というラベル
    private final int MBRNUMLB_WIDTH = LB_FONT_SIZE * 8; // "購入者の部員番号"というラベルの幅
    private final int MBRNUMLB_HEIGHT = LB_FONT_SIZE; // "購入者の部員番号"というラベルの高さ
    private final int MBRNUMLB_X = 100; // "購入者の部員番号"というラベルの左上端のx座標
    private final int MBRNUMLB_Y = 600; // "購入者の部員番号"というラベルの左上端のy座標
    private JTextField mbr_num_field; // 購入者の部員番号を表示するテキストフィールド
    private final int MBRNUMFD_WIDTH = LB_FONT_SIZE * 3; // 購入者の部員番号を表示するテキストフィールドの幅
    private final int MBRNUMFD_HEIGHT = LB_FONT_SIZE; // 購入者の部員番号を表示するテキストフィールドの高さ
    private final int MBRNUMFD_X = MBRNUMLB_X + MBRNUMLB_WIDTH + LB_FONT_SIZE/3; // 購入者の部員番号を表示するテキストフィールドの左上端のx座標
    private final int MBRNUMFD_Y = MBRNUMLB_Y + LB_FONT_SIZE/10; // 購入者の部員番号を表示するテキストフィールドの左上端のy座標

    private JLabel h_name_label; // 表示フィールドの左に置く"HN"というラベル
    private final int HNMLB_WIDTH = LB_FONT_SIZE * 2 - (int)(LB_FONT_SIZE*0.5); // "HN"というラベルの幅
    private final int HNMLB_HEIGHT = LB_FONT_SIZE; // "HN"というラベルの高さ
    private final int HNMLB_X = 470; // "HN"というラベルの左上端のx座標
    private final int HNMLB_Y = 600; // "HN"というラベルの左上端のy座標
    private JTextField h_name_field; // ハンドルネームを表示するテキストフィールド
    private final int HNMFD_WIDTH = LB_FONT_SIZE * 10; // ハンドルネームを表示するテキストフィールドの幅
    private final int HNMFD_HEIGHT = LB_FONT_SIZE; // ハンドルネームを表示するテキストフィールドの高さ
    private final int HNMFD_X = HNMLB_X + HNMLB_WIDTH + LB_FONT_SIZE/3; // ハンドルネームを表示するテキストフィールドの左上端のx座標
    private final int HNMFD_Y = HNMLB_Y + LB_FONT_SIZE/10; // ハンドルネームを表示するテキストフィールドの左上端のy座標

    private JLabel date_label; // 表示フィールドの左に置く"購入日"というラベル
    private final int DATELB_WIDTH = LB_FONT_SIZE * 3; // "購入日"というラベルの幅
    private final int DATELB_HEIGHT = LB_FONT_SIZE; // "購入日"というラベルの高さ
    private final int DATELB_X = 100; // "購入日"というラベルの左上端のx座標
    private final int DATELB_Y = 670; // "購入日"というラベルの左上端のy座標
    private JTextField date_field; // 支払日を入力するテキストフィールド
    private final int DATEFD_WIDTH = LB_FONT_SIZE * 5 - 10; // 購入日を入力するテキストフィールドの幅
    private final int DATEFD_HEIGHT = LB_FONT_SIZE; // 購入日を入力するテキストフィールドの高さ
    private final int DATEFD_X = DATELB_X + DATELB_WIDTH + LB_FONT_SIZE/3; // 購入日を入力するテキストフィールドの左上端のx座標
    private final int DATEFD_Y = DATELB_Y + LB_FONT_SIZE/10; // 購入日を入力するテキストフィールドの左上端のy座標

    private JLabel pchs_name_label; // 入力フィールドの左に置く"購入品名"というラベル
    private final int PCHSNMLB_WIDTH = LB_FONT_SIZE * 4; // "購入品名"というラベルの幅
    private final int PCHSNMLB_HEIGHT = LB_FONT_SIZE; // "購入品名"というラベルの高さ
    private final int PCHSNMLB_X = 400; // "購入品名"というラベルの左上端のx座標
    private final int PCHSNMLB_Y = 670; // "購入品名"というラベルの左上端のy座標
    private JTextField pchs_name_field; // 購入品名を入力するテキストフィールド
    private final int PCHSNMFD_WIDTH = LB_FONT_SIZE * 8; // 購入品名を入力するテキストフィールドの幅
    private final int PCHSNMFD_HEIGHT = LB_FONT_SIZE; // 購入品名を入力するテキストフィールドの高さ
    private final int PCHSNMFD_X = PCHSNMLB_X + PCHSNMLB_WIDTH + LB_FONT_SIZE/3; // 購入品名を入力するテキストフィールドの左上端のx座標
    private final int PCHSNMFD_Y = PCHSNMLB_Y + LB_FONT_SIZE/10; // 購入品名を入力するテキストフィールドの左上端のy座標

    private JLabel pchs_type_label; // 入力フィールドの左に置く"種類"というラベル
    private final int PCHSTPLB_WIDTH = LB_FONT_SIZE * 2; // "種類"というラベルの幅
    private final int PCHSTPLB_HEIGHT = LB_FONT_SIZE; // "種類"というラベルの高さ
    private final int PCHSTPLB_X = 100; // "種類"というラベルの左上端のx座標
    private final int PCHSTPLB_Y = 740; // "種類"というラベルの左上端のy座標
    private JTextField pchs_type_field; // 購入品の種類を入力するテキストフィールド
    private final int PCHSTPFD_WIDTH = LB_FONT_SIZE * 5; // 購入品の種類を入力するテキストフィールドの幅
    private final int PCHSTPFD_HEIGHT = LB_FONT_SIZE; // 購入品の種類を入力するテキストフィールドの高さ
    private final int PCHSTPFD_X = PCHSTPLB_X + PCHSTPLB_WIDTH + LB_FONT_SIZE/3; // 購入品の種類を入力するテキストフィールドの左上端のx座標
    private final int PCHSTPFD_Y = PCHSTPLB_Y + LB_FONT_SIZE/10; // 購入品の種類を入力するテキストフィールドの左上端のy座標

    private JLabel price_label; // 入力フィールドの左に置く"金額"というラベル
    private final int PRCLB_WIDTH = LB_FONT_SIZE * 3; // "金額"というラベルの幅
    private final int PRCLB_HEIGHT = LB_FONT_SIZE; // "金額"というラベルの高さ
    private final int PRCLB_X = 380; // "金額"というラベルの左上端のx座標
    private final int PRCLB_Y = 740; // "金額"というラベルの左上端のy座標
    private JTextField price_field; // 金額を入力するテキストフィールド
    private final int PRCFD_WIDTH = LB_FONT_SIZE * 2; // 金額を入力するテキストフィールドの幅
    private final int PRCFD_HEIGHT = LB_FONT_SIZE; // 金額を入力するテキストフィールドの高さ
    private final int PRCFD_X = PRCLB_X + PRCLB_WIDTH + LB_FONT_SIZE/3; // 金額を入力するテキストフィールドの左上端のx座標
    private final int PRCFD_Y = PRCLB_Y + LB_FONT_SIZE/10; // 金額を入力するテキストフィールドの左上端のy座標

    private JLabel isforfes_label; // 入力フィールドの左に置く"工大祭の模擬店のための購入品"というラベル
    private final int ISFORFESB_WIDTH = LB_FONT_SIZE * 14; // "工大祭の模擬店のための購入品"というラベルの幅
    private final int ISFORFESB_HEIGHT = LB_FONT_SIZE; // "工大祭の模擬店のための購入品"というラベルの高さ
    private final int ISFORFESB_X = 100; // "工大祭の模擬店のための購入品"というラベルの左上端のx座標
    private final int ISFORFESB_Y = 810; // "工大祭の模擬店のための購入品"というラベルの左上端のy座標
    private JRadioButton yes_is_for_fes_button;
    private final int Y_ISFORFESBT_WIDTH = BT_FONT_SIZE * 3; // 模擬店のためのものであるかどうかのはいボタンの幅
    private final int Y_ISFORFESBT_HEIGHT = BT_FONT_SIZE; // 模擬店のためのものであるかどうかのはいボタンの高さ
    private final int Y_ISFORFESBT_X = ISFORFESB_X + ISFORFESB_WIDTH + 30; // 模擬店のためのものであるかどうかのはいボタンの左上端のx座標
    private final int Y_ISFORFESBT_Y = ISFORFESB_Y + LB_FONT_SIZE/10; // 模擬店のためのものであるかどうかのはいボタンの左上端のy座標
    private JRadioButton no_is_for_fes_button;
    private final int N_ISFORFESBT_WIDTH = BT_FONT_SIZE * 4; // 模擬店のためのものであるかどうかのいいえボタンの幅
    private final int N_ISFORFESBT_HEIGHT = BT_FONT_SIZE; // 模擬店のためのものであるかどうかのいいえボタンの高さ
    private final int N_ISFORFESBT_X = Y_ISFORFESBT_X + Y_ISFORFESBT_WIDTH + 20; // 模擬店のためのものであるかどうかのいいえボタンの左上端のx座標
    private final int N_ISFORFESBT_Y = Y_ISFORFESBT_Y; // 模擬店のためのものであるかどうかのいいえボタンの左上端のy座標

    private Confirmer upd_confirmer; // データベースの情報を更新するかの最終確認の画面
    private final Font CONF_FONT = new Font("MS Pゴシック", Font.BOLD, 50); // 確認の文のフォント

    private JButton upd_button; // データベースの購入品情報を更新ボタン
    private final int UPDBT_WIDTH = 500; // データベースの購入品情報を更新ボタンの幅
    private final int UPDBT_HEIGHT = 60; // データベースの購入品情報を更新ボタンの高さ
    private final int UPDBT_X = FRAME_WIDTH/4 - UPDBT_WIDTH/2; // データベースの購入品情報を更新ボタンの左上端のx座標
    private final int UPDBT_Y = 870; // データベースの購入品情報を更新ボタンの左上端のy座標

    private JButton bk_button; // 戻るボタン
    private final int BKBT_WIDTH = 90; // 戻るボタンの幅
    private final int BKBT_HEIGHT = 50; // 戻るボタンの高さ
    private final int BKBT_X = 780; // 戻るボタンの左上端のx座標
    private final int BKBT_Y = 910; // 戻るボタンの左上端のy座標

    private ButtonResponse bt_resp; // コンストラクタで受け取ったPchsDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる


    public PchsInfoUpdater(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // キャラクターに最初の説明を話させる
        bc_func.charaSpeak("変更する購入品情報を上の表から選択して、下の枠に変更後の情報を入力し", "命令1");


        // 画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/opr_frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // Updateのアイコンのラベルのインスタンスを取得、設定
        upd_icon = new ImageIcon("image/component/home/anikatsudb/purchase/updater/icon/icon.png");
        upd_icon_label = new JLabel(upd_icon);
        upd_icon_label.setBounds(ICON_X, ICON_Y, ICON_WIDTH, ICON_HEIGHT);

        // "年"のラベルを作る
        year_label = new JLabel("年");
        // フォントを設定
        year_label.setFont(LABEL_FONT);
        // 位置設定
        year_label.setBounds(YEARLB_X, YEARLB_Y, YEARLB_WIDTH, YEARLB_HEIGHT);
        // 入学年度を入力するテキストフィールドを作成する
        year_field = new JTextField();
        // サイズを設定する
        year_field.setPreferredSize(new Dimension(YEARFD_WIDTH, YEARFD_HEIGHT));
        // フォントを設定
        year_field.setFont(FIELD_FONT);
        // 位置設定
        year_field.setBounds(YEARFD_X, YEARFD_Y, YEARFD_WIDTH, YEARFD_HEIGHT);
        // 右寄せで表示するようにする
        year_field.setHorizontalAlignment(JTextField.RIGHT);
        // 現在の年度をデフォルトでセットする
        Calendar calendar = Calendar.getInstance();
        year_field.setText(String.valueOf(calendar.get(Calendar.YEAR)));

        // "月"のラベルを作る
        month_label = new JLabel("月の購入品情報");
        // フォントを設定
        month_label.setFont(LABEL_FONT);
        // 位置設定
        month_label.setBounds(MONTHLB_X, MONTHLB_Y, MONTHLB_WIDTH, MONTHLB_HEIGHT);
        // 月を入力するテキストフィールドを作成する
        month_field = new JTextField();
        // サイズを設定する
        month_field.setPreferredSize(new Dimension(MONTHFD_WIDTH, MONTHFD_HEIGHT));
        // フォントを設定
        month_field.setFont(FIELD_FONT);
        // 位置設定
        month_field.setBounds(MONTHFD_X, MONTHFD_Y, MONTHFD_WIDTH, MONTHFD_HEIGHT);
        // 右寄せで表示するようにする
        month_field.setHorizontalAlignment(JTextField.RIGHT);
        // 現在の月をデフォルトでセットする
        month_field.setText(String.valueOf(calendar.get(Calendar.MONTH)+1));

        // 検索ボタンの生成
        sch_button = new JButton();
        // サイズの設定
        sch_button.setPreferredSize(new Dimension(SCHBT_WIDTH, SCHBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon schb_icon = new ImageIcon("image/component/home/anikatsudb/purchase/updater/button/sch_button.png");
        sch_button.setIcon(schb_icon);
        sch_button.setContentAreaFilled(false);
        sch_button.setBorderPainted(false);
        sch_button.setFocusPainted(false);
        // ボタンの位置設定
        sch_button.setBounds(SCHBT_X, SCHBT_Y, SCHBT_WIDTH, SCHBT_HEIGHT);
        // ボタンを押した際の処理
        sch_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("sch_button pressed");
                // 検索条件のデータを格納する配列を生成する
                String data[] = new String[2];
                // 入力情報をもとにして検索をし、検索結果表にデータを追加する
                if (getInputSchData(data)) { // 入力した情報に不備がないとき
                    // 入力情報をもとにして検索する
                    try {
                        // 検索結果のリストを生成
                        List<String[]> res_list = searchPchsInfo(data);
                        // 検索結果を表に追加
                        setPchsTable(res_list);
                    } catch (SQLException se) {
                        // キャラクターに報告させる
                        bc_func.charaSpeak("購入品情報を取得するのに失敗し", "報告");
                        System.out.println("検索に失敗しました。");
                        se.printStackTrace();
                    }
                    // テキストフィールドの更新
                    //updateOnNextTextFields();
                }
            }
        });

        // 登録予定表のモデルを作成する
        pchs_table_model = new DefaultTableModel() {
            // ボタンの行だけ反応するようにする
            @Override
            public boolean isCellEditable(int row, int column) {
                switch (column) {
                    case 8:
                        return true;
                    default:
                        return false;
                }
            }
        };
        // 各列の列名をセットする
        String pchs_column_names[] = {"購入品番号", "購入日", "購入品名", "種類", "部員番号", "ハンドルネーム", "金額", "模擬店", "編集ボタン"};
        pchs_table_model.setColumnIdentifiers(pchs_column_names);

        // 上のモデルを使用した登録予定表を作成する
        pchs_table = new JTable(pchs_table_model);
        // 表のサイズをスクロールを使用する形にする
        pchs_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // 列名のフォントの設定
        pchs_table.getTableHeader().setFont(new Font("HGPゴシックM", Font.BOLD, 25));
        // 列名の色を設定
        pchs_table.getTableHeader().setForeground(new Color(255, 255, 255));
        pchs_table.getTableHeader().setBackground(new Color(255, 150, 180));
        // 表の文字のフォントを設定
        pchs_table.setFont(TABLE_FONT);
        // 行の高さを設定
        pchs_table.setRowHeight(ROW_HEIGHT);
        // 列を動かせないようする
        pchs_table.getTableHeader().setReorderingAllowed(false);
        // 削除ボタンの設定
        pchs_table.getColumn("編集ボタン").setCellRenderer(new ButtonRenderer());
        pchs_table.getColumn("編集ボタン").setCellEditor(new PchsButtonEditor(new JTextField("〇")));
        // 自動でソートできるようにする
        pchs_table.setAutoCreateRowSorter(true);
        // 数字（文字列）のソートの設定だけ行う
        TableRowSorter<DefaultTableModel> pchs_sorter = new TableRowSorter<DefaultTableModel>(pchs_table_model);
        pchs_sorter.setComparator(0, new Comparator<String>() { // 購入品番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        pchs_sorter.setComparator(4, new Comparator<String>() { // 部員番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        pchs_sorter.setComparator(6, new Comparator<String>() { // 金額の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        pchs_table.setRowSorter(pchs_sorter);
        // 列のヘッダーのサイズに列の幅を合わせる
        setFirstColumnsWidth(pchs_table, pchs_column_names);
        // 購入日の列を中央寄せにし、購入品番号と部員番号、金額の列を右寄せ表示にする
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        DefaultTableCellRenderer centerCellRenderer = new DefaultTableCellRenderer();
        centerCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel column_model = pchs_table.getColumnModel();
        column_model.getColumn(0).setCellRenderer(rightCellRenderer);
        column_model.getColumn(1).setCellRenderer(centerCellRenderer);
        column_model.getColumn(4).setCellRenderer(rightCellRenderer);
        column_model.getColumn(6).setCellRenderer(rightCellRenderer);

        // 検索結果の表を表示するスクロールパネルのインスタンスを取得、設定
        pchs_table_spanel = new JScrollPane(pchs_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        pchs_table_spanel.setPreferredSize(new Dimension(PCHS_AREA_WIDTH, PCHS_AREA_HEIGHT));
        // パネルの配置を設定
        pchs_table_spanel.setBounds(PCHS_AREA_X, PCHS_AREA_Y, PCHS_AREA_WIDTH, PCHS_AREA_HEIGHT);

        // "変更後の情報"のラベルを作る
        pchs_info_label = new JLabel("《 変更後の情報 》");
        // フォントを設定
        pchs_info_label.setFont(LABEL_FONT);
        // 位置設定
        pchs_info_label.setBounds(PCHSIFLB_X, PCHSIFLB_Y, PCHSIFLB_WIDTH, PCHSIFLB_HEIGHT);

        // "購入品番号"のラベルを作る
        pchs_num_label = new JLabel("購入品番号");
        // フォントを設定
        pchs_num_label.setFont(LABEL_FONT);
        // 位置設定
        pchs_num_label.setBounds(PCHSNUMLB_X, PCHSNUMLB_Y, PCHSNUMLB_WIDTH, PCHSNUMLB_HEIGHT);
        // 購入品番号を入力するテキストフィールドを作成する
        pchs_num_field = new JTextField();
        // サイズを設定する
        pchs_num_field.setPreferredSize(new Dimension(PCHSNUMFD_WIDTH, PCHSNUMFD_HEIGHT));
        // フォントを設定
        pchs_num_field.setFont(FIELD_FONT);
        // 位置設定
        pchs_num_field.setBounds(PCHSNUMFD_X, PCHSNUMFD_Y, PCHSNUMFD_WIDTH, PCHSNUMFD_HEIGHT);
        // 編集ができないようにする
        pchs_num_field.setEditable(false);
        // 右寄せで表示するようにする
        pchs_num_field.setHorizontalAlignment(JTextField.RIGHT);
        
        // "購入者の部員番号"のラベルを作る
        mbr_num_label = new JLabel("購入者の部員番号");
        // フォントを設定
        mbr_num_label.setFont(LABEL_FONT);
        // 位置設定
        mbr_num_label.setBounds(MBRNUMLB_X, MBRNUMLB_Y, MBRNUMLB_WIDTH, MBRNUMLB_HEIGHT);
        // 氏名(姓)を入力するテキストフィールドを作成する
        mbr_num_field = new JTextField();
        // サイズを設定する
        mbr_num_field.setPreferredSize(new Dimension(MBRNUMFD_WIDTH, MBRNUMFD_HEIGHT));
        // フォントを設定
        mbr_num_field.setFont(FIELD_FONT);
        // 位置設定
        mbr_num_field.setBounds(MBRNUMFD_X, MBRNUMFD_Y, MBRNUMFD_WIDTH, MBRNUMFD_HEIGHT);
        // 編集ができないようにする
        mbr_num_field.setEditable(false);
        // 右寄せで表示するようにする
        mbr_num_field.setHorizontalAlignment(JTextField.RIGHT);


        // "ハンドルネーム"のラベルを作る
        h_name_label = new JLabel("HN");
        // フォントを設定
        h_name_label.setFont(LABEL_FONT);
        // 位置設定
        h_name_label.setBounds(HNMLB_X, HNMLB_Y, HNMLB_WIDTH, HNMLB_HEIGHT);
        // ハンドルネームを表示するテキストフィールドを作成する
        h_name_field = new JTextField();
        // サイズを設定する
        h_name_field.setPreferredSize(new Dimension(HNMFD_WIDTH, HNMFD_HEIGHT));
        // フォントを設定
        h_name_field.setFont(FIELD_FONT);
        // 位置設定
        h_name_field.setBounds(HNMFD_X, HNMFD_Y, HNMFD_WIDTH, HNMFD_HEIGHT);
        // 編集ができないようにする
        h_name_field.setEditable(false);

        // "購入日"のラベルを作る
        date_label = new JLabel("購入日");
        // フォントを設定
        date_label.setFont(LABEL_FONT);
        // 位置設定
        date_label.setBounds(DATELB_X, DATELB_Y, DATELB_WIDTH, DATELB_HEIGHT);
        // 購入日を表示するテキストフィールドを作成する
        date_field = new JTextField();
        // サイズを設定する
        date_field.setPreferredSize(new Dimension(HNMFD_WIDTH, HNMFD_HEIGHT));
        // フォントを設定
        date_field.setFont(FIELD_FONT);
        // 位置設定
        date_field.setBounds(DATEFD_X, DATEFD_Y, DATEFD_WIDTH, DATEFD_HEIGHT);
        // 直接入力できないようにする
        date_field.setEditable(false);
        // 中央寄せにする
        date_field.setHorizontalAlignment(JTextField.CENTER);

        // "購入品名"のラベルを作る
        pchs_name_label = new JLabel("購入品名");
        // フォントを設定
        pchs_name_label.setFont(LABEL_FONT);
        // 位置設定
        pchs_name_label.setBounds(PCHSNMLB_X, PCHSNMLB_Y, PCHSNMLB_WIDTH, PCHSNMLB_HEIGHT);
        // 購入品名を入力するテキストフィールドを作成する
        pchs_name_field = new JTextField();
        // サイズを設定する
        pchs_name_field.setPreferredSize(new Dimension(PCHSNMFD_WIDTH, PCHSNMFD_HEIGHT));
        // フォントを設定
        pchs_name_field.setFont(FIELD_FONT);
        // 位置設定
        pchs_name_field.setBounds(PCHSNMFD_X, PCHSNMFD_Y, PCHSNMFD_WIDTH, PCHSNMFD_HEIGHT);
        // 編集ができないようにする
        pchs_name_field.setEditable(false);

        // "種類"のラベルを作る
        pchs_type_label = new JLabel("種類");
        // フォントを設定
        pchs_type_label.setFont(LABEL_FONT);
        // 位置設定
        pchs_type_label.setBounds(PCHSTPLB_X, PCHSTPLB_Y, PCHSTPLB_WIDTH, PCHSTPLB_HEIGHT);
        // 購入品の種類を表示するテキストフィールドを作成する
        pchs_type_field = new JTextField();
        // サイズを設定する
        pchs_type_field.setPreferredSize(new Dimension(PCHSTPFD_WIDTH, HNMFD_HEIGHT));
        // フォントを設定
        pchs_type_field.setFont(FIELD_FONT);
        // 位置設定
        pchs_type_field.setBounds(PCHSTPFD_X, PCHSTPFD_Y, PCHSTPFD_WIDTH, PCHSTPFD_HEIGHT);
        // 編集ができないようにする
        pchs_type_field.setEditable(false);

        // "金額"のラベルを作る
        price_label = new JLabel("金額");
        // フォントを設定
        price_label.setFont(LABEL_FONT);
        // 位置設定
        price_label.setBounds(PRCLB_X, PRCLB_Y, PRCLB_WIDTH, PRCLB_HEIGHT);
        // 金額を入力するテキストフィールドを作成する
        price_field = new JTextField();
        // サイズを設定する
        price_field.setPreferredSize(new Dimension(PRCFD_WIDTH, PRCFD_HEIGHT));
        // フォントを設定
        price_field.setFont(FIELD_FONT);
        // 位置設定
        price_field.setBounds(PRCFD_X, PRCFD_Y, PRCFD_WIDTH, PRCFD_HEIGHT);
        // 右寄せで表示するようにする
        price_field.setHorizontalAlignment(JTextField.RIGHT);

        // "工大祭の模擬店のための購入品"のラベルを作る
        isforfes_label = new JLabel("工大祭の模擬店のための購入品");
        // フォントを設定
        isforfes_label.setFont(LABEL_FONT);
        // 位置設定
        isforfes_label.setBounds(ISFORFESB_X, ISFORFESB_Y, ISFORFESB_WIDTH, ISFORFESB_HEIGHT);

        // 工大祭の模擬店のための購入品かどうかのはいボタンの生成
        yes_is_for_fes_button = new JRadioButton("はい");
        // ボタンの表示設定
        yes_is_for_fes_button.setContentAreaFilled(false);
        yes_is_for_fes_button.setBorderPainted(false);
        yes_is_for_fes_button.setFocusPainted(false);
        // フォントの設定
        yes_is_for_fes_button.setFont(BUTTON_FONT);
        // ボタンの位置設定
        yes_is_for_fes_button.setBounds(Y_ISFORFESBT_X, Y_ISFORFESBT_Y, Y_ISFORFESBT_WIDTH, Y_ISFORFESBT_HEIGHT);
        // ボタンをデフォルトで選択している状態にする
        yes_is_for_fes_button.setSelected(true);
        // ボタンを押した際の処理
        yes_is_for_fes_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("yes_button pressed");

            }
        });

        // 工大祭の模擬店のための購入品かどうかのいいえボタンの生成
        no_is_for_fes_button = new JRadioButton("いいえ");
        // ボタンの表示設定
        no_is_for_fes_button.setContentAreaFilled(false);
        no_is_for_fes_button.setBorderPainted(false);
        no_is_for_fes_button.setFocusPainted(false);
        // フォントの設定
        no_is_for_fes_button.setFont(BUTTON_FONT);
        // ボタンの位置設定
        no_is_for_fes_button.setBounds(N_ISFORFESBT_X, N_ISFORFESBT_Y, N_ISFORFESBT_WIDTH, N_ISFORFESBT_HEIGHT);
        // ボタンを押した際の処理
        no_is_for_fes_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");

            }
        });

        // 工大祭の模擬店のための購入品かどうかのボタンをグループ化する
        ButtonGroup isforfesbt_grp = new ButtonGroup();
        isforfesbt_grp.add(yes_is_for_fes_button);
        isforfesbt_grp.add(no_is_for_fes_button);

        // 確認の画面のインスタンスを生成
        String confirmation = "本当に、指定した購入品情報を更新しますか？";
        upd_confirmer = new Confirmer(new UpdResponse(), confirmation, CONF_FONT, Confirmer.YES_NO);
        // 位置設定
        upd_confirmer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // 登録予定表に追加ボタンの生成
        upd_button = new JButton();
        // サイズの設定
        upd_button.setPreferredSize(new Dimension(UPDBT_WIDTH, UPDBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon updb_icon = new ImageIcon("image/component/home/anikatsudb/purchase/updater/button/upd_button.png");
        upd_button.setIcon(updb_icon);
        upd_button.setContentAreaFilled(false);
        upd_button.setBorderPainted(false);
        upd_button.setFocusPainted(false);
        // ボタンの位置設定
        upd_button.setBounds(UPDBT_X, UPDBT_Y, UPDBT_WIDTH, UPDBT_HEIGHT);
        // ボタンを押した際の処理
        upd_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("upd_button pressed");
                String data[] = new String[4]; // 更新後の部員情報を格納するための配列
                if (getInputUpdData(data)) { // 情報を取得できたとき
                    // 部品をいじれない状態にする
                    makeComponentsDisenabled();
                    // 確認画面を表示
                    add(upd_confirmer, 0);
                    repaint();
                }
            }
        });

        // 戻るボタンの生成
        bk_button = new JButton();
        // サイズの設定
        bk_button.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsudb/purchase/updater/button/bk_button.png");
        bk_button.setIcon(bkb_icon);
        bk_button.setContentAreaFilled(false);
        bk_button.setBorderPainted(false);
        bk_button.setFocusPainted(false);
        // ボタンの位置設定
        bk_button.setBounds(BKBT_X, BKBT_Y, BKBT_WIDTH, BKBT_HEIGHT);
        // ボタンを押した際の処理
        bk_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("bk_button pressed");
                // 選択画面に戻る
                bt_resp.pushClose();
            }
        });

        // 最初は変更情報関連の部品をいじれないようにしておく
        setPchsInfoEditable(false);

        add(year_label);
        add(year_field);
        add(month_label);
        add(month_field);
        add(sch_button);
        add(pchs_table_spanel);
        add(pchs_info_label);
        add(pchs_num_label);
        add(pchs_num_field);
        add(mbr_num_label);
        add(mbr_num_field);
        add(h_name_label);
        add(h_name_field);
        add(date_label);
        add(date_field);
        add(pchs_name_label);
        add(pchs_name_field);
        add(pchs_type_label);
        add(pchs_type_field);
        add(price_label);
        add(price_field);
        add(isforfes_label);
        add(yes_is_for_fes_button);
        add(no_is_for_fes_button);
        add(upd_button);
        add(bk_button);
        add(upd_icon_label);
        add(frame_label);
    }


    private void updateDB(final String data[]) throws SQLException {
        /* 受け取ったデータをもとにしてデータベースの購入品情報を変更する
        @param data[]: 更新後のデータ
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();

        // 工大祭の模擬店のための購入品かどうかをセットする
        String is_for_fes;
        if (data[2].equals("はい")) {
            is_for_fes = "true";
        }
        else {
            is_for_fes = "false";
        }

        // データベースの購入品情報を変更する
        stmt.execute("UPDATE purchases SET price = " + data[1] + ", is_for_festival = " + is_for_fes
                + " WHERE id = " + data[0] + ";");

        // リソースを解放する
        stmt.close();
        // SQL操作をデータベースに反映する
        con.commit();
        // データベースを閉じる
        closeDB();
    }


    private List<String[]> searchPchsInfo(final String data[]) throws SQLException {
        /* パラメータで受け取ったデータをもとにして部員情報を検索し、検索結果を格納したリストを返す
        @param data[]: テキストフィールドに入力した検索条件
        @return res_list: 検索結果を格納したリスト
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();

        String start_date = data[0] + "-" + data[1] + "-01";
        String end_date = data[0] + "-" + data[1] + "-31";

        String sql = "SELECT purchases.*, h_name FROM purchases JOIN members"
                + " ON members.id = purchases.member_id"
                + " WHERE purchase_date BETWEEN '" + start_date + "' AND '" + end_date + "';";

        // 条件に沿って検索する
        ResultSet rs = stmt.executeQuery(sql);

        java.util.List<String[]> res_list = new ArrayList<>(); // 検索結果を格納するためのリスト
        // 検索結果を取得し、格納する
        while (rs.next()) {
            // 購入日の表示処理
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
            String date = sdFormat.format(rs.getDate("purchase_date"));

            String is_for_fes; // 工大祭の模擬店のためのものかどうかのデータを格納するための変数
            if (rs.getBoolean("is_for_festival")) { // 模擬店のためのものであるとき
                is_for_fes = "はい";
            }
            else { // 模擬店のためのものでないとき
                is_for_fes = "いいえ";
            }

            // 検索結果として追加する情報の配列を生成
            String res_array[] = {
                    String.valueOf(rs.getInt("id")), // 購入品番号
                    date, // 購入日
                    rs.getString("name"), // 商品名
                    rs.getString("type"), // 購入品の種類
                    String.valueOf(rs.getInt("member_id")), // 部員番号
                    rs.getString("h_name"), // ハンドルネーム
                    rs.getString("price"), // 金額
                    is_for_fes, // 工大祭の模擬店のためのものかどうか
                    "〇" // 編集ボタン
            };
            // リストに追加する
            res_list.add(res_array);
        }

        // リソースを解放する
        stmt.close();
        // データベースを閉じる
        closeDB();

        return res_list;
    }


    private class UpdResponse implements ButtonResponse {
        @Override
        public void pushYes() {
            try {
                String data[] = new String[3]; // 更新後の購入品情報を格納するための配列
                // 更新後の購入品情報を取得
                getInputUpdData(data);
                // データベースの購入品情報を更新する
                updateDB(data);
                // ログの追加
                bc_func.addLog("購入品番号: " + data[0]
                        + " の購入品情報を更新しました。");
                // 指定したデータの購入品の表における行番号を取得
                int row = getPchsRowNum(data[0]);
                // 表を更新する
                updatePchsTable(data, row);
                resizeColumnsWidth(pchs_table, row);
                // テキストフィールドを空にする
                removPchstInfo();
                // 更新できないようにする
                setPchsInfoEditable(false);
                // キャラクターに報告させる
                bc_func.charaSpeak("指定した購入品情報の更新に成功し", "報告");
            } catch (SQLException e) {
                // キャラクターに報告させる
                bc_func.charaSpeak("指定した購入品情報の更新に失敗し", "報告");
                e.printStackTrace();
            }

            // 部品をいじれる状態に戻す
            makeComponentsEnabled();
            // 確認画面を削除
            remove(upd_confirmer);
            repaint();
        }

        @Override
        public void pushNo() {
            // 部品をいじれる状態に戻す
            makeComponentsEnabled();
            // 確認画面を削除
            remove(upd_confirmer);
            repaint();
        }

        @Override
        public void pushClose() { }
    }


    private void setPchsTable(final List<String[]> list) {
        /* パラメータで受け取ったデータをもとにして検索結果の表を更新する
        @param data_list: 検索結果を格納しているリスト
         */
        // そのとき検索結果の表にセットされている行をすべて削除する
        pchs_table_model.setRowCount(0);

        // 検索結果のデータを表に加える
        for (String data[] : list) {
            // 表にデータを追加する
            pchs_table_model.addRow(data);
            // 表のサイズを調整
            resizeColumnsWidth(pchs_table);
        }
    }


    private class ButtonRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JButton jbutton = new JButton();
            jbutton.setText((value == null) ? "" : value.toString());

            return jbutton;
        }
    }


    private class PchsButtonEditor extends DefaultCellEditor {

        public PchsButtonEditor(JTextField txt_field) {
            super(txt_field);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            String label = (value == null) ? "" : value.toString();
            JButton button = new JButton();
            button.setText(label);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    System.out.println("yeah");
                    // 指定された行の情報を取得する
                    String data[] = getRowInfo(row);
                    // テキストフィールドに部員情報を移す
                    setTextField(data);
                    // ボタンを押した後にボタンが"false"という表示に代わるの防ぐ
                    pchs_table.setValueAt("〇", row, 8);

                    // 変更情報関連の部品をいじれるようにする
                    setPchsInfoEditable(true);
                }
            });
            return button;
        }
    }


    private boolean getInputSchData(final String data[]) {
        /* 検索の際の入力データを引数で受け取った配列に格納する
        @param data[]: 登録する部費情報を格納する配列
         */
        String year = year_field.getText().trim();
        String month = month_field.getText().trim();
        if (year.isEmpty() || month.isEmpty()) { // 入力し忘れている情報があるとき
            // キャラクターに注意させる
            bc_func.charaSpeak("入力していない枠を入力し", "命令2");
            return false;
        }
        else if (!Pattern.compile("^-?[0-9]+$").matcher(year).find()) { // 数字出ないものが年の欄に含まれるとき。正規表現を使って年が数字であるかを確認している
            // キャラクターに注意させる
            bc_func.charaSpeak("年には、数字を入力し", "命令2");
            return false;
        }
        else if (year.length() != 4) { // 年の桁数が4桁ではないとき
            // キャラクターに注意させる
            bc_func.charaSpeak("年には、4桁の数字を入力し", "命令2");
            return false;
        }
        else if (!Pattern.compile("^-?[0-9]+$").matcher(month).find()) { // 数字出ないものが月の欄に含まれるとき。正規表現を使って月が数字であるかを確認している
            // キャラクターに注意させる
            bc_func.charaSpeak("月には、数字を入力し", "命令2");
            return false;
        }
        else if (month.length() > 2) { // 年度の桁数が3桁以上のとき
            // キャラクターに注意させる
            bc_func.charaSpeak("月には、2桁以下の数字を入力し", "命令2");
            return false;
        }
        else { // 入力し忘れている情報がないとき
            data[0] = year;
            data[1] = month;

            return true;
        }
    }


    private boolean getInputUpdData(final String data[]) {
        /* 更新予定の入力データを引数で受け取った配列に格納する
        @param data[]: 登録する購入品情報を格納する配列
         */
        String price = price_field.getText().trim();
        if (price.isEmpty()) { // 入力し忘れている情報があるとき
            // キャラクターに注意させる
            bc_func.charaSpeak("入力していない枠を入力し", "命令2");
            return false;
        }
        else if (!Pattern.compile("^-?[0-9]+$").matcher(price).find()) { // 数字出ないものが金額の欄に含まれるとき。正規表現を使って金額が数字であるかを確認している
            // キャラクターに注意させる
            bc_func.charaSpeak("金額には、数字を入力し", "命令2");
            return false;
        }
        else { // 入力し忘れている情報がないとき
            data[0] = pchs_num_field.getText();
            data[1] = price_field.getText();
            if (yes_is_for_fes_button.isSelected()) { // はいボタンを選択しているとき
                data[2] = "はい";
            }
            else { // いいえボタンを選択しているとき
                data[2] = "いいえ";
            }

            return true;
        }
    }


    private void setTextField(final String data[]) {
        /* 入力欄を編集する部員の現在の情報で埋める
        @param data[]: 対象とする部員の情報
         */
        // 受け取ったデータをテキストフィールドにセット
        pchs_num_field.setText(data[0]); // 購入品番号
        date_field.setText(data[1]); // 購入日
        pchs_name_field.setText(data[2]); // 購入品名
        pchs_type_field.setText(data[3]); // 購入品の種類
        mbr_num_field.setText(data[4]); // 部員番号
        h_name_field.setText(data[5]); // ハンドルネーム
        price_field.setText(data[6]); // 金額
        if (data[7].equals("はい")) { // 模擬店
            yes_is_for_fes_button.setSelected(true);
        }
        else {
            no_is_for_fes_button.setSelected(true);
        }
    }


    private String[] getRowInfo(final int row) {
        /* 指定された表の指定された行の情報を取得し、配列で返す
        @param row: 情報を取り出す対象の行
        @return bgtarray: 指定された行の情報を格納した配列
         */
        int range = pchs_table.getColumnCount() - 1;
        String res_array[] = new String[range]; // 返す配列
        for (int column = 0; column < range; column++) { // ボタンの行以外の情報を格納する
            res_array[column] = pchs_table.getValueAt(row, column).toString();
        }

        return res_array;
    }


    private void makeComponentsDisenabled() {
        /* 部品をいじれない状態にする
         */
        year_field.setEnabled(false);
        month_field.setEnabled(false);
        sch_button.setEnabled(false);
        pchs_table.setEnabled(false);
        pchs_table_spanel.setEnabled(false);
        price_field.setEnabled(false);
        yes_is_for_fes_button.setEnabled(false);
        no_is_for_fes_button.setEnabled(false);
        upd_button.setEnabled(false);
        bk_button.setEnabled(false);
    }


    private void makeComponentsEnabled() {
        /* 部品をいじれる状態にする
         */
        year_field.setEnabled(true);
        month_field.setEnabled(true);
        sch_button.setEnabled(true);
        pchs_table.setEnabled(true);
        pchs_table_spanel.setEnabled(true);
        price_field.setEnabled(true);
        bk_button.setEnabled(true);
        if (!mbr_num_field.getText().isEmpty()) {
            yes_is_for_fes_button.setEnabled(true);
            no_is_for_fes_button.setEnabled(true);
            upd_button.setEnabled(true);;
        }
    }


    private void resizeColumnsWidth(final JTable jtable) {
        /* 各列の幅を各列の要素の最大の幅に合わせる
         */
        TableColumnModel column_model = jtable.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(jtable.getFont());

        int row = jtable.getRowCount() - 1; // 追加された行の行番号

        for (int column = 0; column < jtable.getColumnCount()-1; column++) { // -1しているのは5行目のボタンのところはリサイズの必要がないから
            int col_width = column_model.getColumn(column).getWidth(); // 現在の列の幅
            int value_width = fm.stringWidth(jtable.getValueAt(row, column).toString()) + 20; // 新しく追加された文字列の横幅(+10は余白の分)

            if (col_width < value_width) { // 新しい文字列の幅の方が列の幅より大きいとき
                // 新しい文字列の幅に列の幅を合わせる
                column_model.getColumn(column).setPreferredWidth(value_width);
                column_model.getColumn(column).setWidth(value_width);
            }
        }
    }


    private void resizeColumnsWidth(final JTable jtable, final int row) {
        /* 各列の幅を各列の要素の最大の幅に合わせる
        @param row: 変更した行
         */
        TableColumnModel column_model = jtable.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(jtable.getFont());

        for (int column = 0; column < jtable.getColumnCount()-1; column++) { // -1しているのは5行目のボタンのところはリサイズの必要がないから
            int col_width = column_model.getColumn(column).getWidth(); // 現在の列の幅
            int value_width = fm.stringWidth(jtable.getValueAt(row, column).toString()) + 20; // 新しく追加された文字列の横幅(+10は余白の分)

            if (col_width < value_width) { // 新しい文字列の幅の方が列の幅より大きいとき
                // 新しい文字列の幅に列の幅を合わせる
                column_model.getColumn(column).setPreferredWidth(value_width);
                column_model.getColumn(column).setWidth(value_width);
            }
        }
    }


    private void setFirstColumnsWidth(final JTable pchs_table, final String[] col_names) {
        TableColumnModel column_model = pchs_table.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(pchs_table.getTableHeader().getFont());

        for (int i = 0; i < col_names.length; i++) {
            int width = fm.stringWidth(col_names[i]) + 20;
            column_model.getColumn(i).setPreferredWidth(width);
            column_model.getColumn(i).setWidth(width);
        }
    }


    private void setPchsInfoEditable(final boolean f) {
        /* 金額と模擬店の欄の状態を変更する
        @param f: 金額と模擬店の欄の状態
         */
        price_field.setEditable(f);
        yes_is_for_fes_button.setEnabled(f);
        no_is_for_fes_button.setEnabled(f);
        upd_button.setEnabled(f);
    }


    private int getPchsRowNum(final String pchs_num) {
        /* パラメータで受け取った購入品番号に該当する情報の、購入品の表における行番号を返す
        @param pchs_num: 対象とする購入品番号
        @return row_num: 対象とする購入品番号の部員の表における行番号
         */
        int row_num = -1;
        for (int row = 0; row < pchs_table.getRowCount(); row++) {
            String id = pchs_table.getValueAt(row, 0).toString();

            if (id.equals(pchs_num)) { // 対象とする部員の部員番号と一致したとき
                row_num = row;

                break;
            }
        }

        return row_num;
    }

    
    private void updatePchsTable(final String data[], final int row) {
        /* 変更した購入品の表のデータを、変更後のデータに更新する
        @param data[]: 変更後の購入品情報
        @param row: 更新する購入品の表における行番号
         */
        pchs_table.setValueAt(data[1], row, 6); // 金額
        pchs_table.setValueAt(data[2], row, 7); // 模擬店
    }


    private void removPchstInfo() {
        pchs_num_field.setText("");
        mbr_num_field.setText("");
        h_name_field.setText("");
        date_field.setText("");
        pchs_name_field.setText("");
        pchs_type_field.setText("");
        price_field.setText("");
    }
}
