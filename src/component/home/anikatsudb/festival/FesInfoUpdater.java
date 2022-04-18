package component.home.anikatsudb.festival;

import javax.swing.*;
import javax.swing.table.*;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.regex.Pattern;

import function.BasicFunction;
import function.button.ButtonResponse;
import function.confirmation.Confirmer;
import function.db.DBOperator;


public class FesInfoUpdater extends DBOperator {

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

    private JLabel fes_table_label; // 表の上に置く"過去の模擬店情報"というラベル
    private final int MBRTBLB_WIDTH = LB_FONT_SIZE * 11; // "過去の模擬店情報"というラベルの幅
    private final int MBRTBLB_HEIGHT = LB_FONT_SIZE; // "過去の模擬店情報"というラベルの高さ
    private final int MBRTBLB_X = FRAME_WIDTH/4 - MBRTBLB_WIDTH/2; // "過去の模擬店情報"というラベルの左上端のx座標
    private final int MBRTBLB_Y = 100; // "過去の模擬店情報"というラベルの左上端のy座標

    private DefaultTableModel fes_table_model;
    private JTable fes_table; // 過去の工大祭の模擬店情報一覧表
    private JScrollPane fes_table_spanel; // 上の表ににスクロールバーを付けたパネル
    private final int FES_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int FES_AREA_Y = 140; // スクロールパネルの左上端のy座標
    private final int FES_AREA_WIDTH = 800; // スクロールパネルの幅
    private final int FES_AREA_HEIGHT = 200; // スクロールパネルの高さ
    private HashMap<String, String> mbr_h_name_map; // 会計の部員番号をキーとして、ハンドルネームを格納するマップ
    
    private JLabel fes_info_label; // "変更後の情報"というラベル
    private final int FESIFLB_WIDTH = LB_FONT_SIZE * 9; // "変更後の情報"というラベルの幅
    private final int FESIFLB_HEIGHT = LB_FONT_SIZE; // "変更後の情報"というラベルの高さ
    private final int FESIFLB_X = FRAME_WIDTH/4 - FESIFLB_WIDTH/2; // "変更後の情報"というラベルの左上端のx座標
    private final int FESIFLB_Y = 390; // "変更後の情報"というラベルの左上端のy座標

    private DefaultTableModel mbr_table_model;
    private JTable mbr_table; // 在部している部員一覧表
    private JScrollPane mbr_table_spanel; // 上の表ににスクロールバーを付けたパネル
    private final int MBR_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int MBR_AREA_Y = 430; // スクロールパネルの左上端のy座標
    private final int MBR_AREA_WIDTH = 800; // スクロールパネルの幅
    private final int MBR_AREA_HEIGHT = 210; // スクロールパネルの高さ

    private final int FD_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/3; // 入力のフィールドの文字の大きさ
    private final Font FIELD_FONT = new Font("游明朝", Font.PLAIN, FD_FONT_SIZE); // 入力のフィールドのフォント

    private final int BT_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/6; // ボタンの文字の大きさ
    private final Font BUTTON_FONT = new Font("HGPゴシックM", Font.PLAIN, BT_FONT_SIZE); // 入力のフィールドのフォント

    private JLabel mbr_num_label; // 入力フィールドの左に置く"会計の部員番号"というラベル
    private final int MBRNUMLB_WIDTH = LB_FONT_SIZE * 7; // "会計の部員番号"というラベルの幅
    private final int MBRNUMLB_HEIGHT = LB_FONT_SIZE; // "会計の部員番号"というラベルの高さ
    private final int MBRNUMLB_X = 100; // "会計の部員番号"というラベルの左上端のx座標
    private final int MBRNUMLB_Y = 660; // "会計の部員番号"というラベルの左上端のy座標
    private JTextField mbr_num_field; // 会計の部員番号を表示するテキストフィールド
    private final int MBRNUMFD_WIDTH = LB_FONT_SIZE * 3; // 会計の部員番号を表示するテキストフィールドの幅
    private final int MBRNUMFD_HEIGHT = LB_FONT_SIZE; // 会計の部員番号を表示するテキストフィールドの高さ
    private final int MBRNUMFD_X = MBRNUMLB_X + MBRNUMLB_WIDTH + LB_FONT_SIZE/3; // 会計の部員番号を表示するテキストフィールドの左上端のx座標
    private final int MBRNUMFD_Y = MBRNUMLB_Y + LB_FONT_SIZE/10; // 会計の部員番号を表示するテキストフィールドの左上端のy座標

    private JLabel h_name_label; // 表示フィールドの左に置く"HN"というラベル
    private final int HNMLB_WIDTH = LB_FONT_SIZE * 2 - (int)(LB_FONT_SIZE*0.5); // "HN"というラベルの幅
    private final int HNMLB_HEIGHT = LB_FONT_SIZE; // "HN"というラベルの高さ
    private final int HNMLB_X = 470; // "HN"というラベルの左上端のx座標
    private final int HNMLB_Y = 660; // "HN"というラベルの左上端のy座標
    private JTextField h_name_field; // ハンドルネームを表示するテキストフィールド
    private final int HNMFD_WIDTH = LB_FONT_SIZE * 10; // ハンドルネームを表示するテキストフィールドの幅
    private final int HNMFD_HEIGHT = LB_FONT_SIZE; // ハンドルネームを表示するテキストフィールドの高さ
    private final int HNMFD_X = HNMLB_X + HNMLB_WIDTH + LB_FONT_SIZE/3; // ハンドルネームを表示するテキストフィールドの左上端のx座標
    private final int HNMFD_Y = HNMLB_Y + LB_FONT_SIZE/10; // ハンドルネームを表示するテキストフィールドの左上端のy座標

    private JLabel year_label; // 入力フィールドの左に置く"年度"というラベル
    private final int YEARLB_WIDTH = LB_FONT_SIZE * 2; // "年度"というラベルの幅
    private final int YEARLB_HEIGHT = LB_FONT_SIZE; // "年度"というラベルの高さ
    private final int YEARLB_X = 100; // "年度"というラベルの左上端のx座標
    private final int YEARLB_Y = 730; // "年度"というラベルの左上端のy座標
    private JTextField year_field; // 年度を入力するテキストフィールド
    private final int YEARFD_WIDTH = LB_FONT_SIZE * 3; // 年度を入力するテキストフィールドの幅
    private final int YEARFD_HEIGHT = LB_FONT_SIZE; // 年度を入力するテキストフィールドの高さ
    private final int YEARFD_X = YEARLB_X + YEARLB_WIDTH + LB_FONT_SIZE/3; // 年度を入力するテキストフィールドの左上端のx座標
    private final int YEARFD_Y = YEARLB_Y + LB_FONT_SIZE/10; // 年度を入力するテキストフィールドの左上端のy座標

    private JLabel food_label; // 入力フィールドの左に置く"食品名"というラベル
    private final int FOODLB_WIDTH = LB_FONT_SIZE * 3; // "食品名"というラベルの幅
    private final int FOODLB_HEIGHT = LB_FONT_SIZE; // "食品名"というラベルの高さ
    private final int FOODLB_X = 320; // "食品名"というラベルの左上端のx座標
    private final int FOODLB_Y = 730; // "食品名"というラベルの左上端のy座標
    private JTextField food_field; // 食品名を入力するテキストフィールド
    private final int FOODFD_WIDTH = LB_FONT_SIZE * 9; // 食品名を入力するテキストフィールドの幅
    private final int FOODFD_HEIGHT = LB_FONT_SIZE; // 食品名を入力するテキストフィールドの高さ
    private final int FOODFD_X = FOODLB_X + FOODLB_WIDTH + LB_FONT_SIZE/3; // 食品名を入力するテキストフィールドの左上端のx座標
    private final int FOODFD_Y = FOODLB_Y + LB_FONT_SIZE/10; // 食品名を入力するテキストフィールドの左上端のy座標

    private JLabel money_label; // 入力フィールドの左に置く"売上金額(売上高)"というラベル
    private final int MNYLB_WIDTH = LB_FONT_SIZE * 8 - 10; // "売上金額(売上高)"というラベルの幅
    private final int MNYLB_HEIGHT = LB_FONT_SIZE; // "売上金額(売上高)"というラベルの高さ
    private final int MNYLB_X = 100; // "売上金額(売上高)"というラベルの左上端のx座標
    private final int MNYLB_Y = 800; // "売上金額(売上高)"というラベルの左上端のy座標
    private JTextField money_field; // 売上金額(売上高)を入力するテキストフィールド
    private final int MNYFD_WIDTH = LB_FONT_SIZE * 4; // 売上金額(売上高)を入力するテキストフィールドの幅
    private final int MNYFD_HEIGHT = LB_FONT_SIZE; // 売上金額(売上高)を入力するテキストフィールドの高さ
    private final int MNYFD_X = MNYLB_X + MNYLB_WIDTH + LB_FONT_SIZE/3; // 売上金額(売上高)を入力するテキストフィールドの左上端のx座標
    private final int MNYFD_Y = MNYLB_Y + LB_FONT_SIZE/10; // 売上金額(売上高)を入力するテキストフィールドの左上端のy座標

    private Confirmer upd_confirmer; // データベースの情報を更新するかの最終確認の画面
    private final Font CONF_FONT = new Font("MS Pゴシック", Font.BOLD, 50); // 確認の文のフォント

    private JButton upd_button; // データベースの模擬店情報を更新ボタン
    private final int UPDBT_WIDTH = 500; // データベースの模擬店情報を更新ボタンの幅
    private final int UPDBT_HEIGHT = 60; // データベースの模擬店情報を更新ボタンの高さ
    private final int UPDBT_X = FRAME_WIDTH/4 - UPDBT_WIDTH/2; // データベースの模擬店情報を更新ボタンの左上端のx座標
    private final int UPDBT_Y = 870; // データベースの模擬店情報を更新ボタンの左上端のy座標

    private JButton bk_button; // 戻るボタン
    private final int BKBT_WIDTH = 90; // 戻るボタンの幅
    private final int BKBT_HEIGHT = 50; // 戻るボタンの高さ
    private final int BKBT_X = 780; // 戻るボタンの左上端のx座標
    private final int BKBT_Y = 910; // 戻るボタンの左上端のy座標

    private ButtonResponse bt_resp; // コンストラクタで受け取ったFesDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる


    public FesInfoUpdater(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // キャラクターに最初の説明を話させる
        bc_func.charaSpeak("変更する工大祭の模擬店情報を上の表から選択して、下の枠に変更後の情報を入力し", "命令1");

        // 画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/opr_frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // Updateのアイコンのラベルのインスタンスを取得、設定
        upd_icon = new ImageIcon("image/component/home/anikatsudb/festival/updater/icon/icon.png");
        upd_icon_label = new JLabel(upd_icon);
        upd_icon_label.setBounds(ICON_X, ICON_Y, ICON_WIDTH, ICON_HEIGHT);

        // "過去の模擬店情報"のラベルを作る
        fes_table_label = new JLabel("《 過去の模擬店情報 》");
        // フォントを設定
        fes_table_label.setFont(LABEL_FONT);
        // 位置設定
        fes_table_label.setBounds(MBRTBLB_X, MBRTBLB_Y, MBRTBLB_WIDTH, MBRTBLB_HEIGHT);

        // 検索結果表のモデルを作成する
        fes_table_model = new DefaultTableModel() {
            // ボタンの行だけ反応するようにする
            @Override
            public boolean isCellEditable(int row, int column) {
                switch (column) {
                    case 5:
                        return true;
                    default:
                        return false;
                }
            }
        };
        // 各列の列名をセットする
        String fes_column_names[] = {"年度", "会計の部員番号", "ハンドルネーム", "食品名", "売上金額(売上高)", "編集ボタン"};
        fes_table_model.setColumnIdentifiers(fes_column_names);

        // 上のモデルを使用した登録予定表を作成する
        fes_table = new JTable(fes_table_model);
        // 表のサイズをスクロールを使用する形にする
        fes_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // 列名のフォントの設定
        fes_table.getTableHeader().setFont(new Font("HGPゴシックM", Font.BOLD, 25));
        // 列名の色を設定
        fes_table.getTableHeader().setForeground(new Color(255, 255, 255));
        fes_table.getTableHeader().setBackground(new Color(255, 150, 180));
        // 表の文字のフォントを設定
        fes_table.setFont(TABLE_FONT);
        // 行の高さを設定
        fes_table.setRowHeight(ROW_HEIGHT);
        // 列を動かせないようする
        fes_table.getTableHeader().setReorderingAllowed(false);
        // 編集ボタンの設定
        fes_table.getColumn("編集ボタン").setCellRenderer(new ButtonRenderer());
        fes_table.getColumn("編集ボタン").setCellEditor(new FesButtonEditor(new JTextField("〇")));
        // 自動でソートできるようにする
        fes_table.setAutoCreateRowSorter(true);
        // 数字（文字列）のソートの設定だけ行う
        TableRowSorter<DefaultTableModel> fes_sorter = new TableRowSorter<DefaultTableModel>(fes_table_model);
        fes_sorter.setComparator(0, new Comparator<String>() { // 年度の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        fes_sorter.setComparator(1, new Comparator<String>() { // 部員番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        fes_sorter.setComparator(4, new Comparator<String>() { // 売上金額の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        fes_table.setRowSorter(fes_sorter);
        // 列のヘッダーのサイズに列の幅を合わせる
        setFirstColumnsWidth(fes_table, fes_column_names);
        // 年度と部員番号、売上金額の列を右寄せ表示にする
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        TableColumnModel fes_column_model = fes_table.getColumnModel();
        fes_column_model.getColumn(0).setCellRenderer(rightCellRenderer);
        fes_column_model.getColumn(1).setCellRenderer(rightCellRenderer);
        fes_column_model.getColumn(4).setCellRenderer(rightCellRenderer);

        // 過去の模擬店情報の表を表示するスクロールパネルのインスタンスを取得、設定
        fes_table_spanel = new JScrollPane(fes_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        fes_table_spanel.setPreferredSize(new Dimension(FES_AREA_WIDTH, FES_AREA_HEIGHT));
        // パネルの配置を設定
        fes_table_spanel.setBounds(FES_AREA_X, FES_AREA_Y, FES_AREA_WIDTH, FES_AREA_HEIGHT);

        // 過去の模擬店一覧を作成
        try {
            // 過去の模擬店情報をデータベースから取得
            List<String[]> data_list= getFesInfo();
            // 過去の模擬店一覧表を作成
            setTable(fes_table, fes_table_model ,data_list);
        } catch (SQLException se) {
            // キャラクターに報告させる
            bc_func.charaSpeak("過去の工大祭の模擬店情報を取得するのに失敗し", "報告");
            //System.out.println("過去の模擬店情報を取得するのに失敗しました。");
            se.printStackTrace();
        }

        // "変更後の情報"のラベルを作る
        fes_info_label = new JLabel("《 変更後の情報 》");
        // フォントを設定
        fes_info_label.setFont(LABEL_FONT);
        // 位置設定
        fes_info_label.setBounds(FESIFLB_X, FESIFLB_Y, FESIFLB_WIDTH, FESIFLB_HEIGHT);

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
            setTable(mbr_table, mbr_table_model, data_list);
        } catch (SQLException se) {
            System.out.println("在部している部員の情報を取得するのに失敗しました。");
            se.printStackTrace();
        }

        // 部員番号の列を右寄せ表示にする
        TableColumnModel mbr_column_model = mbr_table.getColumnModel();
        mbr_column_model.getColumn(0).setCellRenderer(rightCellRenderer);

        // "部員番号"のラベルを作る
        mbr_num_label = new JLabel("会計の部員番号");
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

        // "年度"のラベルを作る
        year_label = new JLabel("年度");
        // フォントを設定
        year_label.setFont(LABEL_FONT);
        // 位置設定
        year_label.setBounds(YEARLB_X, YEARLB_Y, YEARLB_WIDTH, YEARLB_HEIGHT);
        // 年度を入力するテキストフィールドを作成する
        year_field = new JTextField();
        // サイズを設定する
        year_field.setPreferredSize(new Dimension(YEARFD_WIDTH, YEARFD_HEIGHT));
        // フォントを設定
        year_field.setFont(FIELD_FONT);
        // 位置設定
        year_field.setBounds(YEARFD_X, YEARFD_Y, YEARFD_WIDTH, YEARFD_HEIGHT);
        // 右寄せで表示するようにする
        year_field.setHorizontalAlignment(JTextField.RIGHT);
        // 編集ができないようにする
        year_field.setEditable(false);

        // "食品名"のラベルを作る
        food_label = new JLabel("食品名");
        // フォントを設定
        food_label.setFont(LABEL_FONT);
        // 位置設定
        food_label.setBounds(FOODLB_X, FOODLB_Y, FOODLB_WIDTH, FOODLB_HEIGHT);
        // 食品名を入力するテキストフィールドを作成する
        food_field = new JTextField();
        // サイズを設定する
        food_field.setPreferredSize(new Dimension(FOODFD_WIDTH, FOODFD_HEIGHT));
        // フォントを設定
        food_field.setFont(FIELD_FONT);
        // 位置設定
        food_field.setBounds(FOODFD_X, FOODFD_Y, FOODFD_WIDTH, FOODFD_HEIGHT);

        // "売上金額(売上高)"のラベルを作る
        money_label = new JLabel("売上金額(売上高)");
        // フォントを設定
        money_label.setFont(LABEL_FONT);
        // 位置設定
        money_label.setBounds(MNYLB_X, MNYLB_Y, MNYLB_WIDTH, MNYLB_HEIGHT);
        // 売上金額(売上高)を入力するテキストフィールドを作成する
        money_field = new JTextField();
        // サイズを設定する
        money_field.setPreferredSize(new Dimension(MNYFD_WIDTH, MNYFD_HEIGHT));
        // フォントを設定
        money_field.setFont(FIELD_FONT);
        // 位置設定
        money_field.setBounds(MNYFD_X, MNYFD_Y, MNYFD_WIDTH, MNYFD_HEIGHT);
        // 右寄せで表示するようにする
        money_field.setHorizontalAlignment(JTextField.RIGHT);

        // 確認の画面のインスタンスを生成
        String confirmation = "本当に、指定した年度の工大祭の模擬店情報を更新しますか？";
        upd_confirmer = new Confirmer(new UpdResponse(), confirmation, CONF_FONT, Confirmer.YES_NO);
        // 位置設定
        upd_confirmer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // 登録予定表に追加ボタンの生成
        upd_button = new JButton();
        // サイズの設定
        upd_button.setPreferredSize(new Dimension(UPDBT_WIDTH, UPDBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon updb_icon = new ImageIcon("image/component/home/anikatsudb/festival/updater/button/upd_button.png");
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
                String data[] = new String[5]; // 更新後の模擬店情報を格納するための配列
                if (getInputData(data)) { // 情報を取得できたとき
                    // 部品をいじれない状態にする
                    makeComponentsDisenabled();
                    // 確認画面を表示
                    add(upd_confirmer, 0);
                    repaint();
                }
            }
        });

        // 最初は部員情報の欄を編集できないようにする
        setFesEditable(false);

        // 戻るボタンの生成
        bk_button = new JButton();
        // サイズの設定
        bk_button.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsudb/festival/updater/button/bk_button.png");
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

        add(fes_table_label);
        add(fes_table_spanel);
        add(fes_info_label);
        add(mbr_table_spanel);
        add(mbr_num_label);
        add(mbr_num_field);
        add(h_name_label);
        add(h_name_field);
        add(year_label);
        add(year_field);
        add(food_label);
        add(food_field);
        add(money_label);
        add(money_field);
        add(upd_button);
        add(bk_button);
        add(upd_icon_label);
        add(frame_label);
    }


    private void updateDB(final String data[]) throws SQLException {
        /* 受け取ったデータをもとにしてデータベースの部員情報を変更する
        @param data[]: 更新後のデータ
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();

        // データベースの部員情報を変更する
        stmt.execute("UPDATE festivals SET member_id = " + data[0] + ", food_name = '" + data[3] +
                "', revenue = " + data[4] + " WHERE fes_year = " + data[2] + ";");

        // リソースを解放する
        stmt.close();
        // SQL操作をデータベースに反映する
        con.commit();
        // データベースを閉じる
        closeDB();
    }


    private class UpdResponse implements ButtonResponse {
        @Override
        public void pushYes() {
            try {
                String data[] = new String[5]; // 更新後の模擬店情報を格納するための配列
                // 更新後の模擬店情報を取得
                getInputData(data);
                // ログの追加
                bc_func.addLog(data[2] + "年度の工大祭の模擬店情報を更新しました。");
                // 入力したデータの通りに更新する
                updateDB(data);
                // 指定した模擬店情報の表における行番号を取得
                int row = getFesRowNum(data[2]);
                // 表を更新する
                updateFesTable(data, row);
                resizeColumnsWidth(fes_table, row);
                // テキストフィールドを空にする
                removeFesInfo();
                // キャラクターに報告させる
                bc_func.charaSpeak("指定した工大祭の模擬店情報の更新に成功し", "報告");
            } catch (SQLException e) {
                // キャラクターに報告させる
                bc_func.charaSpeak("指定した工大祭の模擬店情報の更新に失敗し", "報告");
                //System.out.println("指定した工大祭の模擬店情報の更新に失敗しました。");
                e.printStackTrace();
            }

            // 部品をいじれる状態に戻す
            makeComponentsEnabled();
            // 更新できないようにする
            setFesEditable(false);
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


    private boolean getInputData(final String data[]) {
        /* 新しく登録する予定の入力データを引数で受け取った配列に格納する
        @param data[]: 更新後の部員のデータを格納する配列
         */
        String food = food_field.getText().trim();
        String money = money_field.getText().trim();

        if (food.isEmpty() || money.isEmpty()) { // 入力し忘れている情報があるとき
            // キャラクターに注意させる
            bc_func.charaSpeak("入力していない枠を入力し", "命令2");
            return false;
        }
        else if (food.length() > 10) { // 食品名が11文字以上のとき
            // キャラクターに注意させる
            bc_func.charaSpeak("食品名は、10文字以内で入力し", "命令2");
            //System.out.println("食品名は10文字以内");
            return false;
        }
        else if (!Pattern.compile("^-?[0-9]+$").matcher(money).find()) { // 数字出ないものが売上金額の欄に含まれるとき。正規表現を使って売上金額が数字であるかを確認している
            // キャラクターに注意させる
            bc_func.charaSpeak("売上金額には、数字を入力し", "命令2");
            //System.out.println("売上金額は数字");
            return false;
        }
        else if (money.length() > 8) { // 売上金額が9桁以上の時
            // キャラクターに注意させる
            bc_func.charaSpeak("売上金額には、8桁以内の数字を入力し", "命令2");
            //System.out.println("お金8桁以内で");
            return false;
        }
        else { // 入力し忘れている情報がないとき
            data[0] = mbr_num_field.getText();
            data[1] = h_name_field.getText();
            data[2] = year_field.getText();
            data[3] = food;
            data[4] = money;

            return true;
        }
    }


    private List<String[]> getFesInfo() throws SQLException {
        /* 現在、過去の模擬店の情報を取り出し、情報(削除ボタン付き)を格納したリストを返す
        @return res_list: 在部している部員情報を格納した
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();
        // 現在、在部している部員の部員情報を取得
//        ResultSet rs = stmt.executeQuery("SELECT * FROM festivals");
        ResultSet rs = stmt.executeQuery("SELECT member_id, h_name, fes_year, food_name, revenue "
                + "FROM members JOIN festivals ON id = member_id ORDER BY fes_year ASC;");


        mbr_h_name_map = new HashMap<>(); // 会計の部員番号をキーとしてハンドルネームを格納するマップ
        List<String[]> res_list = new ArrayList<>(); // 取得した模擬店情報を格納するためのリスト
        // 取得した部員情報をリストに格納する
        while (rs.next()) {
            // 会計の部員番号をキーとしてハンドルネームを格納するマップを生成
            mbr_h_name_map.put(String.valueOf(rs.getInt("member_id")), rs.getString("h_name"));
            // 検索結果として追加する情報の配列を生成
            String res_array[] = {
                    String.valueOf(rs.getInt("fes_year")), // 年度
                    String.valueOf(rs.getInt("member_id")), // 会計の部員番号
                    rs.getString("h_name"), // ハンドルネーム
                    rs.getString("food_name"), // 食品名
                    String.valueOf(rs.getInt("revenue")), // 売上金額
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


    private class ButtonRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JButton jbutton = new JButton();
            jbutton.setText((value == null) ? "" : value.toString());

            return jbutton;
        }
    }


    private class FesButtonEditor extends DefaultCellEditor {

        public FesButtonEditor(JTextField txt_field) {
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
                    // 指定された行の情報を変更情報欄にセットする
                    String data[] = getRowInfo(fes_table, row);
                    setFesTextField(data);
                    // 模擬店情報の欄を編集できるようにする
                    setFesEditable(true);
                    // ボタンを押した後にボタンが"false"という表示に代わるの防ぐ
                    fes_table.setValueAt("〇", row, 5);
                }
            });
            return button;
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


    private void resizeColumnsWidth(final JTable jtable) {
        /* 各列の幅を各列の要素の最大の幅に合わせる
        @param jtable: 幅調整をする表
         */
        TableColumnModel column_model = jtable.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(jtable.getFont());

        int row = jtable.getRowCount() - 1; // 追加された行の行番号

        for (int column = 0; column < jtable.getColumnCount()-1; column++) { // -1しているのはボタンのところはリサイズの必要がないから
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
        @param jtable: 幅調整をする表
        @param row: 変更した行
         */
        TableColumnModel column_model = jtable.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(jtable.getFont());

        for (int column = 0; column < jtable.getColumnCount()-1; column++) { // -1しているのはボタンのところはリサイズの必要がないから
            int col_width = column_model.getColumn(column).getWidth(); // 現在の列の幅
            int value_width = fm.stringWidth(jtable.getValueAt(row, column).toString()) + 20; // 新しく追加された文字列の横幅(+10は余白の分)

            if (col_width < value_width) { // 新しい文字列の幅の方が列の幅より大きいとき
                // 新しい文字列の幅に列の幅を合わせる
                column_model.getColumn(column).setPreferredWidth(value_width);
                column_model.getColumn(column).setWidth(value_width);
            }
        }
    }


    private void setTable(final JTable jtable, final DefaultTableModel table_model, final List<String[]> data_list) {
        /* パラメータで受け取ったデータをもとにして表を作成する
        @param jtable: 作成する表
        @param table_model: 作成する表のモデル
        @param data_list: 表のデータを格納しているリスト
         */
        if (data_list.isEmpty()) { // 表に追加するデータが見つからないとき

        }
        else { // 表に追加するデータがあるとき
            // それぞれのデータを表に追加する
            for (String data[]: data_list) {
                // 検索結果のうちの1行を追加する
                table_model.addRow(data);
                // 表のサイズを調整
                resizeColumnsWidth(jtable);
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


    private void setFesTextField(final String data[]) {
        /* 入力欄を編集する部員の現在の情報で埋める
        @param data[]: 対象とする部員の情報
         */
        // 受け取ったデータをテキストフィールドにセット
        year_field.setText(data[0]); // 年度
        mbr_num_field.setText(data[1]); // 部員番号
        h_name_field.setText(data[2]); // ハンドルネーム
        food_field.setText(data[3]); // 食品名
        money_field.setText(data[4]); // 売上金額
    }


    private void setFesEditable(final boolean f) {
        /* 部員番号以外の部員情報の欄の状態を変更する
        @param f: 部員番号以外の部員情報の欄の状態
         */
        food_field.setEditable(f);
        money_field.setEditable(f);
        upd_button.setEnabled(f);
    }


    private void updateFesTable(final String data[], final int row) {
        /* 変更した部員の表のデータを、変更後のデータに更新する
        @param data[]: 変更後の部員情報
        @param row: 更新する部員の表における行番号
         */
        fes_table.setValueAt(data[0], row, 1); // 会計の部員番号
        fes_table.setValueAt(data[1], row, 2); // ハンドルネーム
        fes_table.setValueAt(data[3], row, 3); // 食品名
        fes_table.setValueAt(data[4], row, 4); // 売上金額
    }


    private int getFesRowNum(final String year) {
        /* パラメータで受け取った年度の模擬店覧表における行番号を返す
        @param year: 対象とする年度
        @return row_num: 対象とする部員番号の部員の表における行番号
         */
        int row_num = -1;
        for (int row = 0; row < fes_table.getRowCount(); row++) {
            String id = fes_table.getValueAt(row, 0).toString();

            if (id.equals(year)) { // 対象とする年度と一致したとき
                row_num = row;

                break;
            }
        }

        return row_num;
    }


    private int getMbrRowNum(final String mbr_num) {
        /* パラメータで受け取った部員番号の部員の表における行番号を返す
        @param mbr_num: 対象とする部員番号
        @return row_num: 対象とする部員番号の部員の表における行番号
         */
        int row_num = -1;
        for (int row = 0; row < mbr_table.getRowCount(); row++) {
            String id = mbr_table.getValueAt(row, 0).toString();

            if (id.equals(mbr_num)) { // 対象とする部員の部員番号と一致したとき
                row_num = row;

                break;
            }
        }

        return row_num;
    }


    private void removeFesInfo() {
        mbr_num_field.setText("");
        h_name_field.setText("");
        year_field.setText("");
        food_field.setText("");
        money_field.setText("");
    }


    private void makeComponentsDisenabled() {
        /* 部品をいじれない状態にする
         */
        mbr_table_spanel.setEnabled(false);
        mbr_table.setEnabled(false);
        fes_table_spanel.setEnabled(false);
        fes_table.setEnabled(false);
        food_field.setEnabled(false);
        money_field.setEnabled(false);
        upd_button.setEnabled(false);
        bk_button.setEnabled(false);
    }


    private void makeComponentsEnabled() {
        /* 部品をいじれる状態にする
         */
        mbr_table_spanel.setEnabled(true);
        mbr_table.setEnabled(true);
        fes_table_spanel.setEnabled(true);
        fes_table.setEnabled(true);
        food_field.setEnabled(true);
        money_field.setEnabled(true);
        bk_button.setEnabled(true);
        if (!mbr_num_field.getText().isEmpty()) {
            upd_button.setEnabled(true);
        }
    }
}
