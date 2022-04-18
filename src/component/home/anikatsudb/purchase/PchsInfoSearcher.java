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
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.table.*;
import java.util.regex.Pattern;
import java.util.Calendar;

import function.db.DBOperator;
import function.BasicFunction;
import function.button.ButtonResponse;


public class PchsInfoSearcher extends DBOperator {

    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅

    private ImageIcon frame; // フレームの画像
    private JLabel frame_label; // フレームの画像のラベル
    private final int FM_WIDTH = 860; // フレームの画像の幅
    private final int FM_HEIGHT = 980; // フレームの画像の高さ
    private final int FM_X = 50; // フレームの左上端のx座標
    private final int FM_Y = 30; // フレームの左上端のy座標

    private JLabel sch_icon_label; // Searchのアイコンの画像のラベル
    private ImageIcon sch_icon; // Searchのアイコンの画像
    private final int ICON_WIDTH = 270; // アイコン画像の幅
    private final int ICON_HEIGHT = 130; // アイコン画像の高さ
    private final int ICON_X = 5;//650; // アイコンの左上端のx座標
    private final int ICON_Y = 5; // アイコンの左上端のy座標

    private final int LB_FONT_SIZE = 30; // 入力の枠の隣のラベルの文字の大きさ
    private final Font LABEL_FONT = new Font("HGPゴシックM", Font.PLAIN, LB_FONT_SIZE); // 入力の枠の横のラベルのフォント

    private final int FD_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/3; // 入力のフィールドの文字の大きさ
    private final Font FIELD_FONT = new Font("游明朝", Font.PLAIN, FD_FONT_SIZE); // 入力のフィールドのフォント

    private final int RDBT_FONT_SIZE = 30; // 入力の枠の隣のラジオボタンの文字の大きさ
    private final Font RDBT_FONT = new Font("HGPゴシックM", Font.PLAIN, RDBT_FONT_SIZE); // 入力の枠の横のラベルのフォント

    private final int SLBT_FONT_SIZE = RDBT_FONT_SIZE - RDBT_FONT_SIZE/6; // (複数の選択肢から)選択ボタンの文字の大きさ
    private final Font SLBT_FONT = new Font("HGPゴシックM", Font.PLAIN, SLBT_FONT_SIZE); // 入力のフィールドのフォント

    private final int ROW_HEIGHT = 25; // 一行の高さ
    private final Font TABLE_FONT = new Font("游明朝", Font.PLAIN, ROW_HEIGHT-5); // 確認の文のフォントt

    private JLabel schmtd_label; // 入力フィールドの左に置く"検索方式"というラベル
    private final int SCHMTDLB_WIDTH = LB_FONT_SIZE * 4; // "検索方式"というラベルの幅
    private final int SCHMTDLB_HEIGHT = LB_FONT_SIZE; // "検索方式"というラベルの高さ
    private final int SCHMTDLB_X = 280; // "検索方式"というラベルの左上端のx座標
    private final int SCHMTDLB_Y = 80; // "検索方式"というラベルの左上端のy座標
    private JRadioButton pftmth_button; // "完全一致検索"というボタン
    private final int PFTMTHBT_WIDTH = SLBT_FONT_SIZE * 5; // "完全一致"ボタンの幅
    private final int PFTMTHBT_HEIGHT = SLBT_FONT_SIZE; // "完全一致"ボタンの高さ
    private final int PFTMTHBT_X = SCHMTDLB_X + SCHMTDLB_WIDTH + 20; // "完全一致"ボタンの左上端のx座標
    private final int PFTMTHBT_Y = SCHMTDLB_Y + LB_FONT_SIZE/10; // "完全一致"ボタンの左上端のy座標
    private JRadioButton abgsmth_button; // "あいまい"というボタン
    private final int ABGSMTHBT_WIDTH = SLBT_FONT_SIZE * 5; // "あいまい"ボタンの幅
    private final int ABGSMTHBT_HEIGHT = SLBT_FONT_SIZE; // "あいまい"ボタンの高さ
    private final int ABGSMTHBT_X = PFTMTHBT_X + PFTMTHBT_WIDTH + 10; // "あいまい"ボタンの左上端のx座標
    private final int ABGSMTHBT_Y = PFTMTHBT_Y; // "あいまい"ボタンの左上端のy座標

    private DefaultTableModel mbr_table_model;
    private JTable mbr_table; // 在部している部員一覧表
    private JScrollPane mbr_table_spanel; // 上の表ににスクロールバーを付けたパネル
    private final int MBR_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int MBR_AREA_Y = 140; // スクロールパネルの左上端のy座標
    private final int MBR_AREA_WIDTH = 800; // スクロールパネルの幅
    private final int MBR_AREA_HEIGHT = 150; // スクロールパネルの高さ

    private JRadioButton mbr_num_rdbutton; // 入力フィールドの左に置く"購入者の部員番号"というボタン
    private final int MBRNUMRDBT_WIDTH = LB_FONT_SIZE * 9; // "購入者の部員番号"というラベルの幅
    private final int MBRNUMRDBT_HEIGHT = LB_FONT_SIZE; // "購入者の部員番号"というラベルの高さ
    private final int MBRNUMRDBT_X = 100; // "購入者の部員番号"というラベルの左上端のx座標
    private final int MBRNUMRDBT_Y = 310; // "購入者の部員番号"というラベルの左上端のy座標
    private JTextField mbr_num_field; // 購入者の部員番号を表示するテキストフィールド
    private final int MBRNUMFD_WIDTH = RDBT_FONT_SIZE * 3; // 購入者の部員番号を表示するテキストフィールドの幅
    private final int MBRNUMFD_HEIGHT = RDBT_FONT_SIZE; // 購入者の部員番号を表示するテキストフィールドの高さ
    private final int MBRNUMFD_X = MBRNUMRDBT_X + MBRNUMRDBT_WIDTH + RDBT_FONT_SIZE/3; // 購入者の部員番号を表示するテキストフィールドの左上端のx座標
    private final int MBRNUMFD_Y = MBRNUMRDBT_Y + RDBT_FONT_SIZE/10; // 購入者の部員番号を表示するテキストフィールドの左上端のy座標

    private JLabel h_name_label; // 表示フィールドの左に置く"HN"というラベル
    private final int HNMLB_WIDTH = LB_FONT_SIZE * 2 - (int)(LB_FONT_SIZE*0.5); // "HN"というラベルの幅
    private final int HNMLB_HEIGHT = LB_FONT_SIZE; // "HN"というラベルの高さ
    private final int HNMLB_X = 500; // "HN"というラベルの左上端のx座標
    private final int HNMLB_Y = 310; // "HN"というラベルの左上端のy座標
    private JTextField h_name_field; // ハンドルネームを表示するテキストフィールド
    private final int HNMFD_WIDTH = LB_FONT_SIZE * 10; // ハンドルネームを表示するテキストフィールドの幅
    private final int HNMFD_HEIGHT = LB_FONT_SIZE; // ハンドルネームを表示するテキストフィールドの高さ
    private final int HNMFD_X = HNMLB_X + HNMLB_WIDTH + LB_FONT_SIZE/3; // ハンドルネームを表示するテキストフィールドの左上端のx座標
    private final int HNMFD_Y = HNMLB_Y + LB_FONT_SIZE/10; // ハンドルネームを表示するテキストフィールドの左上端のy座標

    private JRadioButton date_rdbutton; // 入力フィールドの左に置く"購入期間"というボタン
    private final int DTRDBT_WIDTH = RDBT_FONT_SIZE * 5; // "購入期間"というボタンの幅
    private final int DTRDBT_HEIGHT = RDBT_FONT_SIZE; // "購入期間"というボタンの高さ
    private final int DTRDBT_X = 100; // "購入期間"というボタンの左上端のx座標
    private final int DTRDBT_Y = 380; // "購入期間"というボタンの左上端のy座標
    private JTextField year_field; // 年を表示するテキストフィールド
    private final int YEARFD_WIDTH = LB_FONT_SIZE * 3; // 年を表示するテキストフィールドの幅
    private final int YEARFD_HEIGHT = LB_FONT_SIZE; // 年を表示するテキストフィールドの高さ
    private final int YEARFD_X = DTRDBT_X + DTRDBT_WIDTH + RDBT_FONT_SIZE/3; // 年を表示するテキストフィールドの左上端のx座標
    private final int YEARFD_Y = DTRDBT_Y + RDBT_FONT_SIZE/10; // 年を表示するテキストフィールドの左上端のy座標
    private JLabel year_label; // 入力フィールドの右に置く"年"というラベル
    private final int YEARLB_WIDTH = LB_FONT_SIZE * 1; // "年"というラベルの幅
    private final int YEARLB_HEIGHT = LB_FONT_SIZE; // "年"というラベルの高さ
    private final int YEARLB_X = YEARFD_X + YEARFD_WIDTH + LB_FONT_SIZE/3; // "年"というラベルの左上端のx座標
    private final int YEARLB_Y = YEARFD_Y - LB_FONT_SIZE/10; // "年"というラベルの左上端のy座標
    private JTextField month_field; // 月を表示するテキストフィールド
    private final int MONTHFD_WIDTH = LB_FONT_SIZE * 2; // 月を表示するテキストフィールドの幅
    private final int MONTHFD_HEIGHT = LB_FONT_SIZE; // 月を表示するテキストフィールドの高さ
    private final int MONTHFD_X = 400; // 月を表示するテキストフィールドの左上端のx座標
    private final int MONTHFD_Y = DTRDBT_Y; // 月を表示するテキストフィールドの左上端のy座標
    private JLabel month_label; // 入力フィールドの右に置く"月の部費情報ラベル
    private final int MONTHLB_WIDTH = LB_FONT_SIZE * 6; // "月の部費情報"というラベルの幅
    private final int MONTHLB_HEIGHT = LB_FONT_SIZE; // "月の部費情報"というラベルの高さ
    private final int MONTHLB_X = MONTHFD_X + MONTHFD_WIDTH + LB_FONT_SIZE/3; // "月の部費情報"というラベルの左上端のx座標
    private final int MONTHLB_Y = MONTHFD_Y - LB_FONT_SIZE/10; // "月の部費情報"というラベルの左上端のy座標

    private JRadioButton pchs_name_rdbutton; // 入力フィールドの左に置く"購入品名"というボタン
    private final int PCHSNMRDBT_WIDTH = RDBT_FONT_SIZE * 5; // "購入品名"というボタンの幅
    private final int PCHSNMRDBT_HEIGHT = RDBT_FONT_SIZE; // "購入品名"というボタンの高さ
    private final int PCHSNMRDBT_X = 100; // "購入品名"というボタンの左上端のx座標
    private final int PCHSNMRDBT_Y = 450; // "購入品名"というボタンの左上端のy座標
    private JTextField pchs_name_field; // 購入品名を入力するテキストフィールド
    private final int PCHSNMFD_WIDTH = RDBT_FONT_SIZE * 8; // 購入品名を入力するテキストフィールドの幅
    private final int PCHSNMFD_HEIGHT = RDBT_FONT_SIZE; // 購入品名を入力するテキストフィールドの高さ
    private final int PCHSNMFD_X = PCHSNMRDBT_X + PCHSNMRDBT_WIDTH + RDBT_FONT_SIZE/3; // 購入品名を入力するテキストフィールドの左上端のx座標
    private final int PCHSNMFD_Y = PCHSNMRDBT_Y + RDBT_FONT_SIZE/10; // 購入品名を入力するテキストフィールドの左上端のy座標

    private JRadioButton pchs_type_rdbutton; // 入力フィールドの左に置く"種類"というボタン
    private final int PCHSTPRDBT_WIDTH = RDBT_FONT_SIZE * 3; // "種類"というボタンの幅
    private final int PCHSTPRDBT_HEIGHT = RDBT_FONT_SIZE; // "種類"というボタンの高さ
    private final int PCHSTPRDBT_X = 530; // "種類"というボタンの左上端のx座標
    private final int PCHSTPRDBT_Y = 450; // "種類"というボタンの左上端のy座標
    private JComboBox pchs_type_box; // 購入品の種類を入力するテキストフィールド
    private final int PCHSTPBX_WIDTH = RDBT_FONT_SIZE * 5; // 購入品の種類を入力するテキストフィールドの幅
    private final int PCHSTPBX_HEIGHT = RDBT_FONT_SIZE; // 購入品の種類を入力するテキストフィールドの高さ
    private final int PCHSTPBX_X = PCHSTPRDBT_X + PCHSTPRDBT_WIDTH + RDBT_FONT_SIZE/3; // 購入品の種類を入力するテキストフィールドの左上端のx座標
    private final int PCHSTPBX_Y = PCHSTPRDBT_Y + RDBT_FONT_SIZE/10; // 購入品の種類を入力するテキストフィールドの左上端のy座標

    private JButton sch_button; // 検索ボタン
    private final int SCHBT_WIDTH = 170; // 検索ボタンの幅
    private final int SCHBT_HEIGHT = 60; // 検索ボタンの高さ
    private final int SCHBT_X = FRAME_WIDTH/4 - SCHBT_WIDTH/2; // 検索ボタンの左上端のx座標
    private final int SCHBT_Y = 520; // 検索ボタンの左上端のy座標

    private DefaultTableModel res_table_model;
    private JTable res_table; // 検索結果を表示する表
    private JScrollPane res_table_spanel; // 上の表にスクロールバーを付けたパネル
    private final int RES_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int RES_AREA_Y = 620; // スクロールパネルの左上端のy座標
    private final int RES_AREA_WIDTH = 800; // スクロールパネルの幅
    private final int RES_AREA_HEIGHT = 270; // スクロールパネルの高さ

    private JButton bk_button; // 戻るボタン
    private final int BKBT_WIDTH = 90; // 戻るボタンの幅
    private final int BKBT_HEIGHT = 50; // 戻るボタンの高さ
    private final int BKBT_X = 780; // 戻るボタンの左上端のx座標
    private final int BKBT_Y = 910; // 戻るボタンの左上端のy座標

    private ButtonResponse bt_resp; // コンストラクタで受け取ったPchsDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる


    public PchsInfoSearcher(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // キャラクターに最初の説明を話させる
        bc_func.charaSpeak("検索方式を選択して、検索したい購入品情報を入力し", "命令1");

        // menu画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/opr_frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // Searcherのアイコンのラベルのインスタンスを取得、設定
        sch_icon = new ImageIcon("image/component/home/anikatsudb/purchase/searcher/icon/icon.png");
        sch_icon_label = new JLabel(sch_icon);
        sch_icon_label.setBounds(ICON_X, ICON_Y, ICON_WIDTH, ICON_HEIGHT);


        // "検索方式"のラベルを作る
        schmtd_label = new JLabel("検索方式");
        // フォントを設定
        schmtd_label.setFont(LABEL_FONT);
        // 位置設定
        schmtd_label.setBounds(SCHMTDLB_X, SCHMTDLB_Y, SCHMTDLB_WIDTH, SCHMTDLB_HEIGHT);

        // "完全一致"ボタンの生成
        pftmth_button = new JRadioButton("完全一致");
        // ボタンの表示設定
        pftmth_button.setContentAreaFilled(false);
        pftmth_button.setBorderPainted(false);
        pftmth_button.setFocusPainted(false);
        // フォントの設定
        pftmth_button.setFont(SLBT_FONT);
        // ボタンの位置設定
        pftmth_button.setBounds(PFTMTHBT_X, PFTMTHBT_Y, PFTMTHBT_WIDTH, PFTMTHBT_HEIGHT);
        // ボタンをデフォルトで選択している状態にする
        pftmth_button.setSelected(true);
        // ボタンを押した際の処理
        pftmth_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("yes_button pressed");

            }
        });

        // "あいまい"ボタンの生成
        abgsmth_button = new JRadioButton("あいまい");
        // ボタンの表示設定
        abgsmth_button.setContentAreaFilled(false);
        abgsmth_button.setBorderPainted(false);
        abgsmth_button.setFocusPainted(false);
        // フォントの設定
        abgsmth_button.setFont(SLBT_FONT);
        // ボタンの位置設定
        abgsmth_button.setBounds(ABGSMTHBT_X, ABGSMTHBT_Y, ABGSMTHBT_WIDTH, ABGSMTHBT_HEIGHT);
        // ボタンを押した際の処理
        abgsmth_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");

            }
        });

        // 飯塚に住んでいるかどうかのボタンをグループ化する
        ButtonGroup schmtd_grp = new ButtonGroup();
        schmtd_grp.add(pftmth_button);
        schmtd_grp.add(abgsmth_button);
        
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
            //System.out.println("在部している部員の情報を取得するのに失敗しました。");
            se.printStackTrace();
        }

        // 部員番号の列を右寄せ表示にする
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        TableColumnModel mbr_column_model = mbr_table.getColumnModel();
        mbr_column_model.getColumn(0).setCellRenderer(rightCellRenderer);

        // "購入者の部員番号"のラベルを作る
        mbr_num_rdbutton = new JRadioButton("購入者の部員番号");
        // フォントを設定
        mbr_num_rdbutton.setFont(RDBT_FONT);
        // 位置設定
        mbr_num_rdbutton.setBounds(MBRNUMRDBT_X, MBRNUMRDBT_Y, MBRNUMRDBT_WIDTH, MBRNUMRDBT_HEIGHT);
        // ボタンが選択されている状態にする
        mbr_num_rdbutton.setSelected(true);
        // ボタンの表示設定
        mbr_num_rdbutton.setContentAreaFilled(false);
        mbr_num_rdbutton.setBorderPainted(false);
        mbr_num_rdbutton.setFocusPainted(false);
        // ボタンを押した際の処理
        mbr_num_rdbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");
            }
        });
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

        // "購入期間"のラベルを作る
        date_rdbutton = new JRadioButton("購入期間");
        // フォントを設定
        date_rdbutton.setFont(RDBT_FONT);
        // 位置設定
        date_rdbutton.setBounds(DTRDBT_X, DTRDBT_Y, DTRDBT_WIDTH, DTRDBT_HEIGHT);
        // ボタンが選択されている状態にする
        date_rdbutton.setSelected(true);
        // ボタンの表示設定
        date_rdbutton.setContentAreaFilled(false);
        date_rdbutton.setBorderPainted(false);
        date_rdbutton.setFocusPainted(false);
        // ボタンを押した際の処理
        date_rdbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");
                if (date_rdbutton.isSelected()) { // 選択されているとき
                    // 入力フィールドに書き込みを行える状態にする
                    year_field.setEditable(true);
                    month_field.setEditable(true);
                }
                else { // 選択されていないとき
                    // 入力フィールドに書き込みを行えない状態にする
                    year_field.setEditable(false);
                    month_field.setEditable(false);
                }
            }
        });
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
        month_label = new JLabel("月");
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

        // "購入品名"のラベルを作る
        pchs_name_rdbutton = new JRadioButton("購入品名");
        // フォントを設定
        pchs_name_rdbutton.setFont(RDBT_FONT);
        // 位置設定
        pchs_name_rdbutton.setBounds(PCHSNMRDBT_X, PCHSNMRDBT_Y, PCHSNMRDBT_WIDTH, PCHSNMRDBT_HEIGHT);
        // ボタンが選択されている状態にする
        pchs_name_rdbutton.setSelected(true);
        // ボタンの表示設定
        pchs_name_rdbutton.setContentAreaFilled(false);
        pchs_name_rdbutton.setBorderPainted(false);
        pchs_name_rdbutton.setFocusPainted(false);
        // ボタンを押した際の処理
        pchs_name_rdbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");
                if (pchs_name_rdbutton.isSelected()) { // 選択されているとき
                    // 入力フィールドに書き込みを行える状態にする
                    pchs_name_field.setEditable(true);
                }
                else { // 選択されていないとき
                    // 入力フィールドに書き込みを行えない状態にする
                    pchs_name_field.setEditable(false);
                }
            }
        });
        // 購入品名を入力するテキストフィールドを作成する
        pchs_name_field = new JTextField();
        // サイズを設定する
        pchs_name_field.setPreferredSize(new Dimension(PCHSNMFD_WIDTH, PCHSNMFD_HEIGHT));
        // フォントを設定
        pchs_name_field.setFont(FIELD_FONT);
        // 位置設定
        pchs_name_field.setBounds(PCHSNMFD_X, PCHSNMFD_Y, PCHSNMFD_WIDTH, PCHSNMFD_HEIGHT);

        // "種類"のラベルを作る
        pchs_type_rdbutton = new JRadioButton("種類");
        // フォントを設定
        pchs_type_rdbutton.setFont(RDBT_FONT);
        // 位置設定
        pchs_type_rdbutton.setBounds(PCHSTPRDBT_X, PCHSTPRDBT_Y, PCHSTPRDBT_WIDTH, PCHSTPRDBT_HEIGHT);
        // ボタンが選択されている状態にする
        pchs_type_rdbutton.setSelected(true);
        // ボタンの表示設定
        pchs_type_rdbutton.setContentAreaFilled(false);
        pchs_type_rdbutton.setBorderPainted(false);
        pchs_type_rdbutton.setFocusPainted(false);
        // ボタンを押した際の処理
        pchs_type_rdbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");
                if (pchs_type_rdbutton.isSelected()) { // 選択されているとき
                    // コンボボックスをいじれない態にする
                    pchs_type_box.setEnabled(true);
                }
                else { // 選択されていないとき
                    // コンボボックスをいじれない状態にする
                    pchs_type_box.setEnabled(false);
                }
            }
        });
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

        // 登録予定表に追加ボタンの生成
        sch_button = new JButton();
        // サイズの設定
        sch_button.setPreferredSize(new Dimension(SCHBT_WIDTH, SCHBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon preaddb_icon = new ImageIcon("image/component/home/anikatsudb/purchase/searcher/button/sch_button.png");
        sch_button.setIcon(preaddb_icon);
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
                // 検索結果表に追加する予定のデータを格納する配列を生成する
                String data[] = new String[4];
                // 入力情報をもとにして検索をし、検索結果表にデータを追加する
                if (getInputData(data)) { // 入力した情報に不備がないとき
                    // 入力情報をもとにして検索する
                    List res_list; // 検索結果を格納するリスト
                    try {
                        res_list = searchFesInfo(data);
                        // 検索結果を表に追加
                        updateResultTable(res_list);
                        // キャラクターに報告させる
                        bc_func.charaSpeak("購入品情報の検索に成功し", "報告");
                    } catch (SQLException se) {
                        // キャラクターに報告させる
                        bc_func.charaSpeak("購入品情報の検索に失敗し", "報告");
                        System.out.println("検索に失敗しました。");
                        se.printStackTrace();
                    }
                }
            }
        });

        // 検索結果の表のモデルを作成する
        res_table_model = new DefaultTableModel() {
            // ボタンの行だけ反応するようにする
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // 各列の列名をセットする
        String res_column_names[] = {"購入品番号", "購入日", "購入品名", "種類", "部員番号", "ハンドルネーム", "金額", "模擬店"};
        res_table_model.setColumnIdentifiers(res_column_names);

        // 上のモデルを使用した部費情報表を作成する
        res_table = new JTable(res_table_model);
        // 表のサイズをスクロールを使用する形にする
        res_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // 列名のフォントの設定
        res_table.getTableHeader().setFont(new Font("HGPゴシックM", Font.BOLD, 25));
        // 列名の色を設定
        res_table.getTableHeader().setForeground(new Color(255, 255, 255));
        res_table.getTableHeader().setBackground(new Color(255, 150, 180));
        // 表の文字のフォントを設定
        res_table.setFont(TABLE_FONT);
        // 行の高さを設定
        res_table.setRowHeight(ROW_HEIGHT);
        // 列を動かせないようする
        res_table.getTableHeader().setReorderingAllowed(false);
        // 自動でソートできるようにする
        res_table.setAutoCreateRowSorter(true);
        // 数字（文字列）のソートの設定だけ行う
        TableRowSorter<DefaultTableModel> res_sorter = new TableRowSorter<DefaultTableModel>(res_table_model);
        res_sorter.setComparator(0, new Comparator<String>() { // 購入品番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        res_sorter.setComparator(4, new Comparator<String>() { // 部員番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        res_sorter.setComparator(6, new Comparator<String>() { // 金額の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        res_table.setRowSorter(res_sorter);
        // 列のヘッダーのサイズに列の幅を合わせる
        setFirstColumnsWidth(res_table, res_column_names);
        // 購入日の列を中央寄せにし、購入品番号と部員番号、金額の列を右寄せ表示にする
        DefaultTableCellRenderer centerCellRenderer = new DefaultTableCellRenderer();
        centerCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel column_model = res_table.getColumnModel();
        column_model.getColumn(0).setCellRenderer(rightCellRenderer);
        column_model.getColumn(1).setCellRenderer(centerCellRenderer);
        column_model.getColumn(4).setCellRenderer(rightCellRenderer);
        column_model.getColumn(6).setCellRenderer(rightCellRenderer);

        // 検索結果の表を表示するスクロールパネルのインスタンスを取得、設定
        res_table_spanel = new JScrollPane(res_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        res_table_spanel.setPreferredSize(new Dimension(RES_AREA_WIDTH, RES_AREA_HEIGHT));
        // パネルの配置を設定
        res_table_spanel.setBounds(RES_AREA_X, RES_AREA_Y, RES_AREA_WIDTH, RES_AREA_HEIGHT);

        // 戻るボタンの生成
        bk_button = new JButton();
        // サイズの設定
        bk_button.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsudb/purchase/searcher/button/bk_button.png");
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

        add(schmtd_label);
        add(pftmth_button);
        add(abgsmth_button);
        add(mbr_table_spanel);
        add(pchs_name_rdbutton);
        add(pchs_name_field);
        add(mbr_num_rdbutton);
        add(mbr_num_field);
        add(h_name_label);
        add(h_name_field);
        add(date_rdbutton);
        add(year_field);
        add(year_label);
        add(month_field);
        add(month_label);
        add(pchs_name_rdbutton);
        add(pchs_name_field);
        add(pchs_type_rdbutton);
        add(pchs_type_box);
        add(sch_button);
        add(res_table_spanel);
        add(bk_button);
        add(sch_icon_label);
        add(frame_label);
    }


    private boolean getInputData(final String data[]) {
        /* 新しく登録する予定の入力データを引数で受け取った配列に格納する
        @param data[]: 登録予定表に追加する予定のデータを格納する配列
         */
        String mbr_num = mbr_num_field.getText();
        String year = year_field.getText().trim();
        String month = month_field.getText().trim();
        String pchs_name = pchs_name_field.getText().trim();

        if (pftmth_button.isSelected()) { // 完全一致検索のとき
            if (mbr_num_rdbutton.isSelected() && mbr_num.isEmpty()) {
                // キャラクターに注意させる
                bc_func.charaSpeak("対象とする部員を選択し", "命令2");
                return false;
            }
            else if (date_rdbutton.isSelected() && year.isEmpty()) {
                // キャラクターに注意させる
                bc_func.charaSpeak("購入年を入力し", "命令2");
                return false;
            }
            else if (date_rdbutton.isSelected() && month.isEmpty()) {
                // キャラクターに注意させる
                bc_func.charaSpeak("購入月を入力し", "命令2");
                return false;
            }
            else if (pchs_name_rdbutton.isSelected() && pchs_name.isEmpty()) {
                // キャラクターに注意させる
                bc_func.charaSpeak("購入品名を入力し", "命令2");
                return false;
            }
        }

        if (date_rdbutton.isSelected()) { // 購入期間が検索条件の一つのとき
            if (!Pattern.compile("^-?[0-9]+$").matcher(year).find()) { // 数字出ないものが年の欄に含まれるとき。正規表現を使って年が数字であるかを確認している
                // キャラクターに注意させる
                bc_func.charaSpeak("購入年には、数字を入力し", "命令2");
                return false;
            }
            else if (year.length() != 4) { // 年の桁数が4桁ではないとき
                // キャラクターに注意させる
                bc_func.charaSpeak("購入年には、4桁の数字を入力し", "命令2");
                return false;
            }
            else if (!Pattern.compile("^-?[0-9]+$").matcher(month).find()) { // 数字出ないものが月の欄に含まれるとき。正規表現を使って月が数字であるかを確認している
                // キャラクターに注意させる
                bc_func.charaSpeak("購入月には、数字を入力し", "命令2");
                return false;
            }
            else if (month.length() > 3) { // 月の桁数が3桁以上のとき
                // キャラクターに注意させる
                bc_func.charaSpeak("購入月には、2桁以内の数字を入力し", "命令2");
                return false;
            }
        }

        if (pchs_name.length() > 15) { // 購入品名が16文字以上のとき
            // キャラクターに注意させる
            bc_func.charaSpeak("購入品名は、15文字以内で入力し", "命令2");
            return false;
        }
        else { // 入力した情報に不備がないとき
            if (mbr_num_rdbutton.isSelected()) { // 部員番号を検索対象とするとき
                data[0] = mbr_num;
            }
            else { // 部員番号を検索対象としないとき
                data[0] = "";
            }

            if (date_rdbutton.isSelected()) { // 発表期間を検索対象とするとき
                data[1] = year + "-" + month;
            }
            else { // 発表期間を検索対象としないとき
                data[1] = "";
            }

            if (pchs_name_rdbutton.isSelected()) { // 購入品名を検索対象とするとき
                data[2] = pchs_name;
            }
            else { // 購入品名を検索対象としないとき
                data[2] = "";
            }

            if (pchs_type_rdbutton.isSelected()) { // 購入品の種類を検索対象とするとき
                data[3] = (String)pchs_type_box.getSelectedItem();
            }
            else { // 購入品の種類を検索対象としないとき
                data[3] = "";
            }

            return true;
        }
    }


    private List<String[]> searchFesInfo(final String data[]) throws SQLException {
        /* パラメータで受け取ったデータをもとにして過去の模擬店情報を検索し、検索結果を格納したリストを返す
        @param data[]: テキストフィールドに入力した検索条件
        @return res_list: 検索結果を格納したリスト
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();
        // 現在一番最大の部員番号を参照
        ResultSet rs = executeSearchSQL(stmt, data);

        List<String[]> res_list = new ArrayList<>(); // 検索結果を格納するためのリスト
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
                    is_for_fes // 工大祭の模擬店のためのものかどうか
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


    private ResultSet executeSearchSQL(final Statement stmt, final String data[]) throws SQLException {
        /* パラメータで受け取ったデータをもとにした検索のSQLを実行し、結果を返す
        @param stmt: SQLを実行するのに使うインスタンス
        @param data[]: 検索条件
        @return rs: 検索結果
         */
        ResultSet rs; // 検索結果を格納するインスタンス
        if (data[0].isEmpty() && data[1].isEmpty() && data[2].isEmpty() && data[3].isEmpty() && data[4].isEmpty()) { // 検索条件がないとき
            rs = stmt.executeQuery("SELECT purchases.*, h_name FROM purchases JOIN members ON members.id = purchases.member_id");
        }
        else { // 検索条件が設定されているとき
            if (pftmth_button.isSelected()) { // 完全一致検索のとき
                rs = executePftSearch(stmt, data);
            }
            else {
                rs = executeAbgsSearch(stmt, data);
            }
        }

        return rs;
    }


    private ResultSet executePftSearch(final Statement stmt, final String data[]) throws SQLException {
        /* パラメータで受け取ったデータをもとにした検索のSQLを実行し、結果を返す(完全一致検索)
        @param stmt: SQLを実行するのに使うインスタンス
        @param data[]: 検索条件
        @return rs: 検索結果
         */
        String data_stmt[] = new String[4]; // それぞれの検索条件におけるSQLの条件部分

        // 部員番号における検索条件
        if (data[0].isEmpty()) {
            data_stmt[0] = "";
        }
        else {
            data_stmt[0] = " member_id = " + data[0];
        }

        // 購入日における検索条件
        if (data[1].isEmpty()) {
            data_stmt[1] = "";
        }
        else {
            String start_date = data[1] + "-01";
            String end_date = data[1] + "-31";
            data_stmt[1] = " purchase_date BETWEEN '" + start_date + "' AND '" + end_date + "'";
        }

        // 購入品名における検索条件
        if (data[2].isEmpty()) {
            data_stmt[2] = "";
        }
        else {
            data_stmt[2] = " purchases.name = '" + data[2] + "'";
        }

        // 購入品の種類における検索条件
        if (data[3].isEmpty()) {
            data_stmt[3] = "";
        }
        else {
            data_stmt[3] = " type = '" + data[3] + "'";
        }

        // それぞれの条件を区切る&&を付ける
        int len = data_stmt.length;
        // 一番最初の条件の配列番号を格納する
        int start_point = len;
        for (int i = 0; i < len; i++) {
            if (!data_stmt[i].isEmpty()) {
                start_point = i;
                break;
            }
        }
        // 区切るための&&を付ける
        for (int i = start_point; i < len-1; i++) {
            for (int j = i+1; j < len; j++) {
                if (!data_stmt[j].isEmpty()) { // 配列data_stmtのj(j > i)番目の要素に何か条件があるとき
                    data_stmt[i] += " &&";
                    i = j-1;
                    break;
                }
            }
        }

        // 検索をし、検索結果を格納する
        ResultSet rs = stmt.executeQuery("SELECT purchases.*, h_name FROM purchases JOIN members ON members.id = purchases.member_id WHERE"
                + String.join("", data_stmt) + ";");

        return rs;
    }


    private ResultSet executeAbgsSearch(final Statement stmt, final String data[]) throws SQLException {
        /* パラメータで受け取ったデータをもとにした検索のSQLを実行し、結果を返す(あいまい検索)
        @param stmt: SQLを実行するのに使うインスタンス
        @param data[]: 検索条件
        @return rs: 検索結果
         */
        String data_stmt[] = new String[4]; // それぞれの検索条件におけるSQLの条件部分

        // 部員番号における検索条件
        if (data[0].isEmpty()) {
            data_stmt[0] = "";
        }
        else {
            data_stmt[0] = " member_id = " + data[0];
        }

        // 購入日における検索条件
        if (data[1].isEmpty()) {
            data_stmt[1] = "";
        }
        else {
            String start_date = data[1] + "-01";
            String end_date = data[1] + "-31";
            data_stmt[1] = " purchase_date BETWEEN '" + start_date + "' AND '" + end_date + "'";
        }

        // 購入品名における検索条件
        if (data[2].isEmpty()) {
            data_stmt[2] = "";
        }
        else {
            data_stmt[2] = " purchases.name LIKE '%" + data[2] + "%'";
        }

        // 購入品の種類における検索条件
        if (data[3].isEmpty()) {
            data_stmt[3] = "";
        }
        else {
            data_stmt[3] = " type = '" + data[3] + "'";
        }

        // それぞれの条件を区切る&&を付ける
        int len = data_stmt.length;
        // 一番最初の条件の配列番号を格納する
        int start_point = len;
        for (int i = 0; i < len; i++) {
            if (!data_stmt[i].isEmpty()) {
                start_point = i;
                break;
            }
        }
        // 区切るための&&を付ける
        for (int i = start_point; i < len-1; i++) {
            for (int j = i+1; j < len; j++) {
                if (!data_stmt[j].isEmpty()) { // 配列data_stmtのj(j > i)番目の要素に何か条件があるとき
                    data_stmt[i] += " &&";
                    i = j-1;
                    break;
                }
            }
        }

        // 検索をし、検索結果を格納する
        ResultSet rs = stmt.executeQuery("SELECT purchases.*, h_name FROM purchases JOIN members ON members.id = purchases.member_id WHERE"
                + String.join("", data_stmt) + ";");

        return rs;
    }


    private void updateResultTable(final List<String[]> data_list) {
        /* パラメータで受け取ったデータをもとにして検索結果の表を更新する
        @param data_list: 検索結果を格納しているリスト
         */
        // そのとき検索結果の表にセットされている行をすべて削除する
        res_table_model.setRowCount(0);

        if (data_list.isEmpty()) { // 検索した結果、該当するものがなかったとき

        }
        else { // 検索した結果、該当するものが見つかったとき
            // 検索結果のデータを表に追加する
            for (String data[]: data_list) {
                // 検索結果のうちの1行を追加する
                res_table_model.addRow(data);
                // 表のサイズを調整
                resizeColumnsWidth(res_table);
            }
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
}
