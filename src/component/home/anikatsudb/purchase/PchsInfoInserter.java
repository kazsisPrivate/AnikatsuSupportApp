package component.home.anikatsudb.purchase;

import javax.swing.*;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.*;
import javax.swing.table.*;
import java.util.regex.Pattern;
import java.util.Calendar;
import java.util.Date;

import function.BasicFunction;
import function.button.ButtonResponse;
import function.confirmation.Confirmer;
import function.db.DBOperator;


public class PchsInfoInserter extends DBOperator {

    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅

    private ImageIcon frame; // フレームの画像
    private JLabel frame_label; // フレームの画像のラベル
    private final int FM_WIDTH = 860; // フレームの画像の幅
    private final int FM_HEIGHT = 980; // フレームの画像の高さ
    private final int FM_X = 50; // フレームの左上端のx座標
    private final int FM_Y = 30; // フレームの左上端のy座標

    private JLabel isrt_icon_label; // Insertのアイコンの画像のラベル
    private ImageIcon isrt_icon; // Insertのアイコンの画像
    private final int ICON_WIDTH = 270; // アイコン画像の幅
    private final int ICON_HEIGHT = 130; // アイコン画像の高さ
    private final int ICON_X = 5;//650; // アイコンの左上端のx座標
    private final int ICON_Y = 5; // アイコンの左上端のy座標

    private final int LB_FONT_SIZE = 30; // 入力の枠の隣のラベルの文字の大きさ
    private final Font LABEL_FONT = new Font("HGPゴシックM", Font.PLAIN, LB_FONT_SIZE); // 入力の枠の横のラベルのフォント

    private final int BT_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/6; // ボタンの文字の大きさ
    private final Font BUTTON_FONT = new Font("HGPゴシックM", Font.PLAIN, BT_FONT_SIZE); // 入力のフィールドのフォント

    private final int FD_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/3; // 入力のフィールドの文字の大きさ
    private final Font FIELD_FONT = new Font("游明朝", Font.PLAIN, FD_FONT_SIZE); // 入力のフィールドのフォント

    private final int ROW_HEIGHT = 25; // 一行の高さ
    private final Font TABLE_FONT = new Font("游明朝", Font.PLAIN, ROW_HEIGHT-5); // 確認の文のフォントt

    private JLabel pchs_num_label; // 入力フィールドの左に置く"購入品番号"というラベル
    private final int PCHSNUMLB_WIDTH = LB_FONT_SIZE * 5; // "購入品番号"というラベルの幅
    private final int PCHSNUMLB_HEIGHT = LB_FONT_SIZE; // "購入品番号"というラベルの高さ
    private final int PCHSNUMLB_X = 280; // "購入品番号"というラベルの左上端のx座標
    private final int PCHSNUMLB_Y = 80; // "購入品番号"というラベルの左上端のy座標
    private JTextField pchs_num_field; // 購入品番号を表示するテキストフィールド
    private final int PCHSNUMFD_WIDTH = LB_FONT_SIZE * 3; // 購入品番号を表示するテキストフィールドの幅
    private final int PCHSNUMFD_HEIGHT = LB_FONT_SIZE; // 購入品番号を表示するテキストフィールドの高さ
    private final int PCHSNUMFD_X = PCHSNUMLB_X + PCHSNUMLB_WIDTH + LB_FONT_SIZE/3; // 購入品番号を表示するテキストフィールドの左上端のx座標
    private final int PCHSNUMFD_Y = PCHSNUMLB_Y + LB_FONT_SIZE/10; // 購入品番号を表示するテキストフィールドの左上端のy座標

    private DefaultTableModel mbr_table_model;
    private JTable mbr_table; // 在部している部員一覧表
    private JScrollPane mbr_table_spanel; // 上の表ににスクロールバーを付けたパネル
    private final int MBR_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int MBR_AREA_Y = 140; // スクロールパネルの左上端のy座標
    private final int MBR_AREA_WIDTH = 800; // スクロールパネルの幅
    private final int MBR_AREA_HEIGHT = 150; // スクロールパネルの高さ
    
    private JLabel mbr_num_label; // 入力フィールドの左に置く"購入者の部員番号"というラベル
    private final int MBRNUMLB_WIDTH = LB_FONT_SIZE * 8; // "購入者の部員番号"というラベルの幅
    private final int MBRNUMLB_HEIGHT = LB_FONT_SIZE; // "購入者の部員番号"というラベルの高さ
    private final int MBRNUMLB_X = 100; // "購入者の部員番号"というラベルの左上端のx座標
    private final int MBRNUMLB_Y = 310; // "購入者の部員番号"というラベルの左上端のy座標
    private JTextField mbr_num_field; // 購入者の部員番号を表示するテキストフィールド
    private final int MBRNUMFD_WIDTH = LB_FONT_SIZE * 3; // 購入者の部員番号を表示するテキストフィールドの幅
    private final int MBRNUMFD_HEIGHT = LB_FONT_SIZE; // 購入者の部員番号を表示するテキストフィールドの高さ
    private final int MBRNUMFD_X = MBRNUMLB_X + MBRNUMLB_WIDTH + LB_FONT_SIZE/3; // 購入者の部員番号を表示するテキストフィールドの左上端のx座標
    private final int MBRNUMFD_Y = MBRNUMLB_Y + LB_FONT_SIZE/10; // 購入者の部員番号を表示するテキストフィールドの左上端のy座標
    
    private JLabel h_name_label; // 表示フィールドの左に置く"HN"というラベル
    private final int HNMLB_WIDTH = LB_FONT_SIZE * 2 - (int)(LB_FONT_SIZE*0.5); // "HN"というラベルの幅
    private final int HNMLB_HEIGHT = LB_FONT_SIZE; // "HN"というラベルの高さ
    private final int HNMLB_X = 470; // "HN"というラベルの左上端のx座標
    private final int HNMLB_Y = 310; // "HN"というラベルの左上端のy座標
    private JTextField h_name_field; // ハンドルネームを表示するテキストフィールド
    private final int HNMFD_WIDTH = LB_FONT_SIZE * 10; // ハンドルネームを表示するテキストフィールドの幅
    private final int HNMFD_HEIGHT = LB_FONT_SIZE; // ハンドルネームを表示するテキストフィールドの高さ
    private final int HNMFD_X = HNMLB_X + HNMLB_WIDTH + LB_FONT_SIZE/3; // ハンドルネームを表示するテキストフィールドの左上端のx座標
    private final int HNMFD_Y = HNMLB_Y + LB_FONT_SIZE/10; // ハンドルネームを表示するテキストフィールドの左上端のy座標

    private JLabel date_label; // 表示フィールドの左に置く"購入日"というラベル
    private final int DATELB_WIDTH = LB_FONT_SIZE * 3; // "購入日"というラベルの幅
    private final int DATELB_HEIGHT = LB_FONT_SIZE; // "購入日"というラベルの高さ
    private final int DATELB_X = 100; // "購入日"というラベルの左上端のx座標
    private final int DATELB_Y = 380; // "購入日"というラベルの左上端のy座標
    JSpinner.DateEditor date_editor;
    private JSpinner date_spinner; // 日付を入力するテキストフィールド
    private final int DATEFD_WIDTH = LB_FONT_SIZE * 5 - 10; // 日付を入力するテキストフィールドの幅
    private final int DATEFD_HEIGHT = LB_FONT_SIZE; // 日付を入力するテキストフィールドの高さ
    private final int DATEFD_X = DATELB_X + DATELB_WIDTH + LB_FONT_SIZE/3; // 日付を入力するテキストフィールドの左上端のx座標
    private final int DATEFD_Y = DATELB_Y + LB_FONT_SIZE/10; // 日付を入力するテキストフィールドの左上端のy座標

    private JLabel pchs_name_label; // 入力フィールドの左に置く"購入品名"というラベル
    private final int PCHSNMLB_WIDTH = LB_FONT_SIZE * 4; // "購入品名"というラベルの幅
    private final int PCHSNMLB_HEIGHT = LB_FONT_SIZE; // "購入品名"というラベルの高さ
    private final int PCHSNMLB_X = 400; // "購入品名"というラベルの左上端のx座標
    private final int PCHSNMLB_Y = 380; // "購入品名"というラベルの左上端のy座標
    private JTextField pchs_name_field; // 購入品名を入力するテキストフィールド
    private final int PCHSNMFD_WIDTH = LB_FONT_SIZE * 8; // 購入品名を入力するテキストフィールドの幅
    private final int PCHSNMFD_HEIGHT = LB_FONT_SIZE; // 購入品名を入力するテキストフィールドの高さ
    private final int PCHSNMFD_X = PCHSNMLB_X + PCHSNMLB_WIDTH + LB_FONT_SIZE/3; // 購入品名を入力するテキストフィールドの左上端のx座標
    private final int PCHSNMFD_Y = PCHSNMLB_Y + LB_FONT_SIZE/10; // 購入品名を入力するテキストフィールドの左上端のy座標

    private JLabel pchs_type_label; // 入力フィールドの左に置く"種類"というラベル
    private final int PCHSTPLB_WIDTH = LB_FONT_SIZE * 2; // "種類"というラベルの幅
    private final int PCHSTPLB_HEIGHT = LB_FONT_SIZE; // "種類"というラベルの高さ
    private final int PCHSTPLB_X = 100; // "種類"というラベルの左上端のx座標
    private final int PCHSTPLB_Y = 450; // "種類"というラベルの左上端のy座標
    private JComboBox pchs_type_box; // 購入品の種類を入力するテキストフィールド
    private final int PCHSTPBX_WIDTH = LB_FONT_SIZE * 5; // 購入品の種類を入力するテキストフィールドの幅
    private final int PCHSTPBX_HEIGHT = LB_FONT_SIZE; // 購入品の種類を入力するテキストフィールドの高さ
    private final int PCHSTPBX_X = PCHSTPLB_X + PCHSTPLB_WIDTH + LB_FONT_SIZE/3; // 購入品の種類を入力するテキストフィールドの左上端のx座標
    private final int PCHSTPBX_Y = PCHSTPLB_Y + LB_FONT_SIZE/10; // 購入品の種類を入力するテキストフィールドの左上端のy座標

    private JLabel price_label; // 入力フィールドの左に置く"金額"というラベル
    private final int PRCLB_WIDTH = LB_FONT_SIZE * 2; // "金額"というラベルの幅
    private final int PRCLB_HEIGHT = LB_FONT_SIZE; // "金額"というラベルの高さ
    private final int PRCLB_X = 380; // "金額"というラベルの左上端のx座標
    private final int PRCLB_Y = 450; // "金額"というラベルの左上端のy座標
    private JTextField price_field; // 金額を入力するテキストフィールド
    private final int PRCFD_WIDTH = LB_FONT_SIZE * 3; // 金額を入力するテキストフィールドの幅
    private final int PRCFD_HEIGHT = LB_FONT_SIZE; // 金額を入力するテキストフィールドの高さ
    private final int PRCFD_X = PRCLB_X + PRCLB_WIDTH + LB_FONT_SIZE/3; // 金額を入力するテキストフィールドの左上端のx座標
    private final int PRCFD_Y = PRCLB_Y + LB_FONT_SIZE/10; // 金額を入力するテキストフィールドの左上端のy座標

    private JLabel isforfes_label; // 入力フィールドの左に置く"工大祭の模擬店のための購入品"というラベル
    private final int ISFORFESB_WIDTH = LB_FONT_SIZE * 14; // "工大祭の模擬店のための購入品"というラベルの幅
    private final int ISFORFESB_HEIGHT = LB_FONT_SIZE; // "工大祭の模擬店のための購入品"というラベルの高さ
    private final int ISFORFESB_X = 100; // "工大祭の模擬店のための購入品"というラベルの左上端のx座標
    private final int ISFORFESB_Y = 520; // "工大祭の模擬店のための購入品"というラベルの左上端のy座標
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

    private JButton pre_add_button; // 登録予定表に追加ボタン
    private final int PREADDBT_WIDTH = 290; // 登録予定表に追加ボタンの幅
    private final int PREADDBT_HEIGHT = 60; // 登録予定表に追加ボタンの高さ
    private final int PREADDBT_X = FRAME_WIDTH/4 - PREADDBT_WIDTH/2; // 登録予定表に追加ボタンの左上端のx座標
    private final int PREADDBT_Y = 580; // 登録予定表に追加ボタンの左上端のy座標

    private DefaultTableModel pchs_table_model;
    private JTable pchs_table; // 追加予定の部員の表表示する表
    private JScrollPane pchs_table_spanel; // 上の表にスクロールバーを付けたパネル
    private final int PCHS_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int PCHS_AREA_Y = 670; // スクロールパネルの左上端のy座標
    private final int PCHS_AREA_WIDTH = 800; // スクロールパネルの幅
    private final int PCHS_AREA_HEIGHT = 180; // スクロールパネルの高さ

    private JButton add_button; // データベースに登録ボタン
    private final int ADDBT_WIDTH = 320; // データベースに登録ボタンの幅
    private final int ADDBT_HEIGHT = 60; // データベースに登録ボタンの高さ
    private final int ADDBT_X = FRAME_WIDTH/4 - ADDBT_WIDTH/2; // データベースに登録ボタンの左上端のx座標
    private final int ADDBT_Y = 870; // データベースに登録ボタンの左上端のy座標

    private Confirmer isrt_confirmer; // データベースに登録するかの最終確認の画面
    private final Font CONF_FONT = new Font("MS Pゴシック", Font.BOLD, 50); // 確認の文のフォント

    private JButton bk_button; // 戻るボタン
    private final int BKBT_WIDTH = 90; // 戻るボタンの幅
    private final int BKBT_HEIGHT = 50; // 戻るボタンの高さ
    private final int BKBT_X = 780; // 戻るボタンの左上端のx座標
    private final int BKBT_Y = 910; // 戻るボタンの左上端のy座標

    private ButtonResponse bt_resp; // コンストラクタで受け取ったPchsDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる


    public PchsInfoInserter(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // キャラクターに最初の説明を話させる
        bc_func.charaSpeak("購入者の部員番号を選択して、登録する購入品情報を入力し", "命令1");

        // menu画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/opr_frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // Insertのアイコンのラベルのインスタンスを取得、設定
        isrt_icon = new ImageIcon("image/component/home/anikatsudb/purchase/inserter/icon/icon.png");
        isrt_icon_label = new JLabel(isrt_icon);
        isrt_icon_label.setBounds(ICON_X, ICON_Y, ICON_WIDTH, ICON_HEIGHT);

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
        // 登録する部員の部員番号を取得し、設定する
        try {
            int max_id = getPchsMaxId();
            pchs_num_field.setText(String.valueOf(max_id+1));
        } catch (SQLException e) {
            System.out.println("購入品番号をデータベースから取得できませんでした。");
        }

        // 検索結果表のモデルを作成する
        mbr_table_model = new DefaultTableModel() {
            // ボタンの行だけ反応するようにする
            @Override
            public boolean isCellEditable(int row, int column) {
                switch (column) {
                    case 3:
                        return true;
                    default:
                        return false;
                }
            }
        };
        // 各列の列名をセットする
        String mbr_column_names[] = {"部員番号", "氏名", "ハンドルネーム", "編集ボタン"};
        mbr_table_model.setColumnIdentifiers(mbr_column_names);

        // 上のモデルを使用した登録予定表を作成する
        mbr_table = new JTable(mbr_table_model);
        // 表のサイズをスクロールを使用する形にする
        mbr_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // 列名のフォントの設定
        mbr_table.getTableHeader().setFont(new Font("HGPゴシックM", Font.BOLD, 25));
        // 列名の色を設定
        mbr_table.getTableHeader().setForeground(new Color(255, 255, 255));
        mbr_table.getTableHeader().setBackground(new Color(255, 150, 180));
        // 表の文字のフォントを設定
        mbr_table.setFont(TABLE_FONT);
        // 行の高さを設定
        mbr_table.setRowHeight(ROW_HEIGHT);
        // 列を動かせないようする
        mbr_table.getTableHeader().setReorderingAllowed(false);
        // 削除ボタンの設定
        mbr_table.getColumn("編集ボタン").setCellRenderer(new ButtonRenderer());
        mbr_table.getColumn("編集ボタン").setCellEditor(new MbrButtonEditor(new JTextField("〇")));
        // 自動でソートできるようにする
        mbr_table.setAutoCreateRowSorter(true);
        // 数字（文字列）のソートの設定だけ行う
        TableRowSorter<DefaultTableModel> mbr_sorter = new TableRowSorter<DefaultTableModel>(mbr_table_model);
        mbr_sorter.setComparator(0, new Comparator<String>() { // 部員番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        mbr_table.setRowSorter(mbr_sorter);
        // 列のヘッダーのサイズに列の幅を合わせる
        setFirstColumnsWidth(mbr_table, mbr_column_names);

        // 検索結果の表を表示するスクロールパネルのインスタンスを取得、設定
        mbr_table_spanel = new JScrollPane(mbr_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        mbr_table_spanel.setPreferredSize(new Dimension(MBR_AREA_WIDTH, MBR_AREA_HEIGHT));
        // パネルの配置を設定
        mbr_table_spanel.setBounds(MBR_AREA_X, MBR_AREA_Y, MBR_AREA_WIDTH, MBR_AREA_HEIGHT);

        // 在部している部員一覧を作成
        try {
            // 在部している部員の情報をデータベースから取得
            List<String[]> data_list= getMbrInfo();
            // 在部している部員一覧表を作成
            setMbrTable(data_list);
        } catch (SQLException se) {
            // キャラクターに報告させる
            bc_func.charaSpeak("在部している部員の情報を取得するのに失敗し", "報告");
            System.out.println("在部している部員の情報を取得するのに失敗しました。");
            se.printStackTrace();
        }

        // 部員番号の列を右寄せ表示にする
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        TableColumnModel mbr_column_model = mbr_table.getColumnModel();
        mbr_column_model.getColumn(0).setCellRenderer(rightCellRenderer);

        // "部員番号"のラベルを作る
        mbr_num_label = new JLabel("購入者の部員番号");
        // フォントを設定
        mbr_num_label.setFont(LABEL_FONT);
        // 位置設定
        mbr_num_label.setBounds(MBRNUMLB_X, MBRNUMLB_Y, MBRNUMLB_WIDTH, MBRNUMLB_HEIGHT);
        // 部員番号を入力するテキストフィールドを作成する
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

        // "HN"のラベルを作る
        h_name_label = new JLabel("HN");
        // フォントを設定
        h_name_label.setFont(LABEL_FONT);
        // 位置設定
        h_name_label.setBounds(HNMLB_X, HNMLB_Y, HNMLB_WIDTH, HNMLB_HEIGHT);
        // ハンドルネームを入力するテキストフィールドを作成する
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

        Calendar calendar = Calendar.getInstance();
        Date initDate = calendar.getTime(); // 今日の日付を初期値にする
        calendar.add(Calendar.YEAR, -1);
        Date startDate = calendar.getTime(); // 1年前を最低値にする

        SpinnerModel model =
                new SpinnerDateModel(initDate, startDate, null, Calendar.DAY_OF_MONTH);
        date_spinner = new JSpinner(model);
        date_editor = new JSpinner.DateEditor(date_spinner, "yyyy/MM/dd");

        date_spinner.setEditor(date_editor);
        // サイズを設定する
        date_spinner.setPreferredSize(new Dimension(HNMFD_WIDTH, HNMFD_HEIGHT));
        // フォントを設定
        date_spinner.setFont(FIELD_FONT);
        // 位置設定
        date_spinner.setBounds(DATEFD_X, DATEFD_Y, DATEFD_WIDTH, DATEFD_HEIGHT);
        // 直接入力できないようにする
        date_editor.getTextField().setEditable(false);
        // 中央寄せにする
        date_editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        // テキストフィールドの背景を白色にする
        date_editor.getTextField().setBackground(Color.WHITE);

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

        // "種類"のラベルを作る
        pchs_type_label = new JLabel("種類");
        // フォントを設定
        pchs_type_label.setFont(LABEL_FONT);
        // 位置設定
        pchs_type_label.setBounds(PCHSTPLB_X, PCHSTPLB_Y, PCHSTPLB_WIDTH, PCHSTPLB_HEIGHT);
        // 種類を選択するコンボボックスを作成する
        String type_data[] = {"本(雑誌等)", "文房具", "日用品", "家具", "電化製品", "レンタル商品", "その他"};
        pchs_type_box = new JComboBox(type_data);
        // サイズを設定する
        pchs_type_box.setPreferredSize(new Dimension(PCHSTPBX_WIDTH, PCHSTPBX_HEIGHT));
        // フォントを設定
        pchs_type_box.setFont(FIELD_FONT);
        // 位置設定
        pchs_type_box.setBounds(PCHSTPBX_X, PCHSTPBX_Y, PCHSTPBX_WIDTH, PCHSTPBX_HEIGHT);
        // 背景色を白色にする
        pchs_type_box.setBackground(Color.WHITE);

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
        // いいえボタンを最初に押している状態にする
        no_is_for_fes_button.setSelected(true);
        
        // 登録予定表に追加ボタンの生成
        pre_add_button = new JButton();
        // サイズの設定
        pre_add_button.setPreferredSize(new Dimension(PREADDBT_WIDTH, PREADDBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon preaddb_icon = new ImageIcon("image/component/home/anikatsudb/purchase/inserter/button/pre_add_button.png");
        pre_add_button.setIcon(preaddb_icon);
        pre_add_button.setContentAreaFilled(false);
        pre_add_button.setBorderPainted(false);
        pre_add_button.setFocusPainted(false);
        // ボタンの位置設定
        pre_add_button.setBounds(PREADDBT_X, PREADDBT_Y, PREADDBT_WIDTH, PREADDBT_HEIGHT);
        // ボタンを押した際の処理
        pre_add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pre_add_button pressed");
                // 登録予定表に追加する予定のデータを格納する配列を生成する
                String data[] = new String[pchs_table_model.getColumnCount()];
                // 入力情報をもとにして登録予定表にデータを追加する
                if (getInputData(data)) { // 入力し忘れている情報がないとき
                    // 登録予定表に新しいデータを追加
                    pchs_table_model.addRow(data);
                    // 表のサイズを調整
                    resizeColumnsWidth(pchs_table);
                    // テキストフィールドの更新
                    updateOnNextTextFields();
                    // テキストフィールドをいじれない状態に戻す
                    setPchsInfoEditable(false);
                    // データベースに登録ボタンを押せるようにする
                    add_button.setEnabled(true);
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
        String pchs_column_names[] = {"購入品番号", "購入日", "購入品名", "種類", "部員番号", "ハンドルネーム", "金額", "模擬店", "削除ボタン"};
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
        pchs_table.getColumn("削除ボタン").setCellRenderer(new ButtonRenderer());
        pchs_table.getColumn("削除ボタン").setCellEditor(new PchsButtonEditor(new JTextField("x")));
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
        DefaultTableCellRenderer centerCellRenderer = new DefaultTableCellRenderer();
        centerCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel column_model = pchs_table.getColumnModel();
        column_model.getColumn(0).setCellRenderer(rightCellRenderer);
        column_model.getColumn(1).setCellRenderer(centerCellRenderer);
        column_model.getColumn(4).setCellRenderer(rightCellRenderer);
        column_model.getColumn(6).setCellRenderer(rightCellRenderer);

        // 追加予定の情報の表を表示するスクロールパネルのインスタンスを取得、設定
        pchs_table_spanel = new JScrollPane(pchs_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        pchs_table_spanel.setPreferredSize(new Dimension(PCHS_AREA_WIDTH, PCHS_AREA_HEIGHT));
        // パネルの配置を設定
        pchs_table_spanel.setBounds(PCHS_AREA_X, PCHS_AREA_Y, PCHS_AREA_WIDTH, PCHS_AREA_HEIGHT);

        // データベースに登録ボタンの生成
        add_button = new JButton();
        // サイズの設定
        add_button.setPreferredSize(new Dimension(ADDBT_WIDTH, ADDBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon addb_icon = new ImageIcon("image/component/home/anikatsudb/purchase/inserter/button/add_button.png");
        add_button.setIcon(addb_icon);
        add_button.setContentAreaFilled(false);
        add_button.setBorderPainted(false);
        add_button.setFocusPainted(false);
        // ボタンの位置設定
        add_button.setBounds(ADDBT_X, ADDBT_Y, ADDBT_WIDTH, ADDBT_HEIGHT);
        // ボタンを押した際の処理
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("add_button pressed");
                // 部品を一時的にいじれない状態にする
                makeComponentsDisenabled();
                // 確認画面を表示
                add(isrt_confirmer, 0);
                repaint();
            }
        });

        // 確認の画面のインスタンスを生成
        String confirmation = "本当に、登録予定表に追加した購入品情報をデータベースに登録しますか？";
        isrt_confirmer = new Confirmer(new IsrtResponse(), confirmation, CONF_FONT, Confirmer.YES_NO);
        // 位置設定
        isrt_confirmer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // 最初は入力情報の欄を編集できないようにする
        setPchsInfoEditable(false);
        add_button.setEnabled(false);
        
        // 戻るボタンの生成
        bk_button = new JButton();
        // サイズの設定
        bk_button.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsudb/purchase/inserter/button/bk_button.png");
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

        add(pchs_num_label);
        add(pchs_num_field);
        add(mbr_table_spanel);
        add(pchs_name_label);
        add(pchs_name_field);
        add(mbr_num_label);
        add(mbr_num_field);
        add(h_name_label);
        add(h_name_field);
        add(date_label);
        add(date_spinner);
        add(pchs_name_label);
        add(pchs_name_field);
        add(pchs_type_label);
        add(pchs_type_box);
        add(price_label);
        add(price_field);
        add(isforfes_label);
        add(yes_is_for_fes_button);
        add(no_is_for_fes_button);
        add(pre_add_button);
        add(pchs_table_spanel);
        add(add_button);
        add(bk_button);
        add(isrt_icon_label);
        add(frame_label);
    }


    public void updateDB() throws SQLException {
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();

        for (int row = 0; row < pchs_table.getRowCount(); row++) {
            String pchs_num = pchs_table.getValueAt(row, 0).toString();
            String date = pchs_table.getValueAt(row, 1).toString();
            String pchs_name = pchs_table.getValueAt(row, 2).toString();
            String pchs_type = pchs_table.getValueAt(row, 3).toString();
            String mbr_num = pchs_table.getValueAt(row, 4).toString();
            String price = pchs_table.getValueAt(row, 6).toString();
            boolean is_for_fes;
            if (pchs_table.getValueAt(row, 7).toString().equals("はい")) { // "はい"が格納されているとき
                is_for_fes = true;
            }
            else { // "いいえ"が格納されているとき
                is_for_fes = false;
            }

            stmt.execute("INSERT INTO purchases (id, purchase_date, name, type, member_id, price, is_for_festival)" +
                    " values (" + pchs_num + ", '" + date + "', '" + pchs_name + "', '" + pchs_type + "', "
                    + mbr_num + ", " + price + ", " + is_for_fes + ");");
        }

        // リソースを解放する
        stmt.close();
        // SQL操作をデータベースに反映する
        con.commit();
        // データベースを閉じる
        closeDB();
    }


    private int getPchsMaxId() throws SQLException {
        /* データベースに登録されている購入品番号の最大値を取得する
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();
        // 現在一番最大の部員番号を参照
        ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM purchases;");

        int max_id = -1; // 部員番号の最大値を格納するための変数
        // 最大値を取得し、格納する
        if (rs.next()) {
            max_id = rs.getInt("MAX(id)");
        }

        // データベースを閉じる
        closeDB();

        return max_id;
    }


    private class IsrtResponse implements ButtonResponse {
        @Override
        public void pushYes() {
            try {
                // データベースに登録予定表のデータを登録
                updateDB();
                // ログの追加
                bc_func.addLog(pchs_table.getRowCount() + "件の購入品情報を、データベースに登録しました。");
                // 登録を終えたデータを登録予定表から削除
                pchs_table_model.setRowCount(0);
                // キャラクターに報告させる
                bc_func.charaSpeak("データベースへの購入品情報の登録に成功し", "報告");
            } catch (SQLException e) {
                // キャラクターに報告させる
                bc_func.charaSpeak("データベースへの購入品情報の登録に失敗し", "報告");
                System.out.println("データベースへの新入部員の情報登録に失敗しました。");
                e.printStackTrace();
            }

            // 部品を操作できるようにする
            makeComponentsEnabled();
            // 確認画面を削除
            remove(isrt_confirmer);
            repaint();
        }

        @Override
        public void pushNo() {
            // 部品を操作できるようにする
            makeComponentsEnabled();
            // 確認画面を削除
            remove(isrt_confirmer);
            repaint();
        }

        @Override
        public void pushClose() { }
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


    private class MbrButtonEditor extends DefaultCellEditor {

        public MbrButtonEditor(JTextField txt_field) {
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
                    // 部員情報の欄を編集できるようにする
                    setPchsInfoEditable(true);
                    // 指定された行の情報を部員番号、HNの欄にセットする
                    String data[] = getRowInfo(mbr_table, row);
                    setMbrTextField(data);
                    // ボタンを押した後にボタンが"false"という表示に代わるの防ぐ
                    mbr_table.setValueAt("〇", row, 3);
                }
            });
            return button;
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
                    // 登録予定表から指定された行を削除
                    int converted_row = pchs_table.convertRowIndexToModel(row);
                    pchs_table_model.removeRow(converted_row);
                    // 登録予定表を更新する
                    updatePchsTable(converted_row);
                    // テキストフィールドを更新する
                    updateOnPrevTextFields();
                    
                    if (pchs_table.getRowCount() == 0) { // 登録予定表に何も登録されていなかったら
                        // データベースに登録するボタンを使えない状態にする
                        add_button.setEnabled(false);
                    }
                }
            });
            return button;
        }
    }


    private void setFirstColumnsWidth(final JTable jtable, final String[] col_names) {
        TableColumnModel column_model = jtable.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(jtable.getTableHeader().getFont());

        for (int i = 0; i < col_names.length; i++) {
            int width = fm.stringWidth(col_names[i]) + 20;
            column_model.getColumn(i).setPreferredWidth(width);
            column_model.getColumn(i).setWidth(width);
        }
    }


    private boolean getInputData(final String data[]) {
        /* 新しく登録する予定の入力データを引数で受け取った配列に格納する
        @param data[]: 登録予定表に追加する予定のデータを格納する配列
         */
        String pchs_name = pchs_name_field.getText().trim();
        String price = price_field.getText().trim();

        if (pchs_name.isEmpty() || price.isEmpty()) { // 入力し忘れている情報があるとき
            // キャラクターに注意させる
            bc_func.charaSpeak("入力していない枠を入力し", "命令2");
            return false;
        }
        else if (pchs_name.length() > 15) { // 購入品名が16文字以上のとき
            // キャラクターに注意させる
            bc_func.charaSpeak("購入品名は、15文字以内で入力し", "命令2");
            System.out.println("購入品名は15文字以内");
            return false;
        }
        else if (!Pattern.compile("^-?[0-9]+$").matcher(price).find()) { // 数字出ないものが金額の欄に含まれるとき。正規表現を使って金額が数字であるかを確認している
            // キャラクターに注意させる
            bc_func.charaSpeak("金額には、数字を入力し", "命令2");
            System.out.println("金額は数字");
            return false;
        }
        else { // 入力し忘れている情報がないとき
            data[0] = pchs_num_field.getText();
            data[1] = date_editor.getTextField().getText();
            data[2] = pchs_name;
            data[3] = (String)pchs_type_box.getSelectedItem();
            data[4] = mbr_num_field.getText();
            data[5] = h_name_field.getText();
            data[6] = price;
            if (yes_is_for_fes_button.isSelected()) { // はいボタンを選択しているとき
                data[7] = "はい";
            }
            else { // いいえボタンを選択しているとき
                data[7] = "いいえ";
            }
            data[8] = "x";

            return true;
        }
    }


    private void updateOnNextTextFields() {
        /* 登録予定表に新しいデータを追加した際に、次の入力情報のためにテキストフィールドを更新する
         */
        // 購入品番号を次の番号にする
        int next_pchs_num = Integer.parseInt(pchs_num_field.getText()) + 1; // 次の購入品番号
        pchs_num_field.setText(String.valueOf(next_pchs_num));

        // 氏名、ハンドルネーム、購入品名のテキストフィールドを空にする
        mbr_num_field.setText("");
        h_name_field.setText("");
        pchs_name_field.setText("");
        price_field.setText("");

        // 購入品の種類のボックスをデフォルトの「本(雑誌等)」にセットする
        pchs_type_box.setSelectedItem("本(雑誌等)");

        // 模擬店のための模擬店のためのものであるかのかのいいえボタンを選択している状態にする
        no_is_for_fes_button.setSelected(true);
    }


    private void updateOnPrevTextFields() {
        /* 登録予定表に登録していた情報を削除する際に、購入品番号をを一つ戻す
         */
        // 購入品番号を一つ戻す
        int prev_pchs_num = Integer.parseInt(pchs_num_field.getText()) - 1; // 前の購入品番号
        pchs_num_field.setText(String.valueOf(prev_pchs_num));
    }


    private void updatePchsTable(final int dlt_row) {
        /* 登録予定表の購入品情報を購入品された際に、登録予定表の購入品情報の購入品番号の調整を行う
        @param dlt_row: 登録予定表から削除された行番号
         */
        for (int row = dlt_row; row < pchs_table.getRowCount(); row++) {
            // 対象とする行の購入品番号を取得
            int pchs_num = Integer.parseInt(pchs_table.getValueAt(row, 0).toString());
            // 削除した購入品の分、購入品番号を1減らす
            pchs_table.setValueAt(String.valueOf(pchs_num-1), row, 0);
        }
    }


    private void resizeColumnsWidth(final JTable jtable) {
        /* 各列の幅を各列の要素の最大の幅に合わせる
        @param jtable: 幅調整をする表
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


    private List<String[]> getMbrInfo() throws SQLException {
        /* 現在、部に所属しているメンバーの情報を取り出し、情報(削除ボタン付き)を格納したリストを返す
        @return res_list: 在部している部員情報を格納した
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();
        // 現在、在部している部員の部員情報を取得
        ResultSet rs = stmt.executeQuery("SELECT id, name, h_name FROM members WHERE is_in_club = true");


        List<String[]> res_list = new ArrayList<>(); // 取得した部員情報を格納するためのリスト
        // 取得した部員情報をリストに格納する
        while (rs.next()) {
            String h_name = rs.getString("h_name"); // ハンドルネームを格納する変数
            if (h_name == null) { // ハンドルネームが未登録だったとき
                h_name = "";
            }

            // 検索結果として追加する情報の配列を生成
            String res_array[] = {
                    String.valueOf(rs.getInt("id")), // 部員番号
                    rs.getString("name"), // 氏名
                    h_name, // ハンドルネーム
                    "〇" // 更新ボタン
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


    private void setMbrTable(final List<String[]> data_list) {
        /* パラメータで受け取ったデータをもとにして在部している部員一覧を作成する
        @param data_list: 在部している部員の部員情報を格納しているリスト
         */
        if (data_list.isEmpty()) { // 在部している部員が見つからないとき

        }
        else { // 在部している部員がいるとき
            // それぞれの部員のデータを表に追加する
            for (String data[]: data_list) {
                // 検索結果のうちの1行を追加する
                mbr_table_model.addRow(data);
                // 表のサイズを調整
                resizeColumnsWidth(mbr_table);
            }
        }
    }


    private String[] getRowInfo(final JTable jtable, final int row) {
        /* 指定された表の指定された行の情報を取得し、配列で返す
        @param row: 情報を取り出す対象の行
        @return res_array: 指定された行の情報を格納した配列
         */
        int range = jtable.getColumnCount() - 1;
        String res_array[] = new String[range]; // 返す配列
        for (int column = 0; column < range; column++) { // ボタンの行以外の情報を格納する
            res_array[column] = jtable.getValueAt(row, column).toString();
        }

        return res_array;
    }


    private void setMbrTextField(final String data[]) {
        /* 入力欄を編集する部員の現在の情報で埋める
        @param data[]: 対象とする部員の情報
         */
        // 受け取ったデータをテキストフィールドにセット
        mbr_num_field.setText(data[0]); // 部員番号
        h_name_field.setText(data[2]); // ハンドルネーム
    }


    private void setPchsInfoEditable(final boolean f) {
        /* 部員番号以外の購入品情報の欄の状態を変更する
        @param f: 部員番号以外の購入品情報の欄の状態
         */
        date_spinner.setEnabled(f);
        pchs_name_field.setEditable(f);
        pchs_type_box.setEnabled(f);
        price_field.setEditable(f);
        yes_is_for_fes_button.setEnabled(f);
        no_is_for_fes_button.setEnabled(f);
        pre_add_button.setEnabled(f);
    }


    private void makeComponentsDisenabled() {
        /* 部品をいじれない状態にする
         */
        mbr_table.setEnabled(false);
        mbr_table_spanel.setEnabled(false);
        date_spinner.setEnabled(false);
        pchs_name_field.setEnabled(false);
        pchs_type_box.setEnabled(false);
        price_field.setEnabled(false);
        yes_is_for_fes_button.setEnabled(false);
        no_is_for_fes_button.setEnabled(false);
        pre_add_button.setEnabled(false);
        pchs_table.setEnabled(false);
        pchs_table_spanel.setEnabled(false);
        add_button.setEnabled(false);
        bk_button.setEnabled(false);
    }


    private void makeComponentsEnabled() {
        /* 部品をいじれる状態にする
         */
        if (!mbr_num_field.getText().equals("")) { // 何か登録中の購入品情報があったとき
            date_spinner.setEnabled(true);
            pchs_type_box.setEnabled(true);
            yes_is_for_fes_button.setEnabled(true);
            no_is_for_fes_button.setEnabled(true);
            pre_add_button.setEnabled(true);
        }
        if (pchs_table.getRowCount() > 0) { // 確認画面でNoを押したとき
            add_button.setEnabled(true);
        }
        mbr_table.setEnabled(true);
        mbr_table_spanel.setEnabled(true);
        pchs_name_field.setEnabled(true);
        price_field.setEnabled(true);
        pchs_table.setEnabled(true);
        pchs_table_spanel.setEnabled(true);
        bk_button.setEnabled(true);
    }
}
