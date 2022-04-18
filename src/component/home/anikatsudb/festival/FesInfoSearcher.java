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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Calendar;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.regex.Pattern;

import function.BasicFunction;
import function.button.ButtonResponse;
import function.confirmation.Confirmer;
import function.db.DBOperator;


public class FesInfoSearcher extends DBOperator {

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

    private final int LB_FONT_SIZE = 30; // 表の上のラベルの文字の大きさ
    private final Font LABEL_FONT = new Font("HGPゴシックM", Font.PLAIN, LB_FONT_SIZE); // 表の上のラベルのフォント

    private final int FD_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/3; // 入力のフィールドの文字の大きさ
    private final Font FIELD_FONT = new Font("游明朝", Font.PLAIN, FD_FONT_SIZE); // 入力のフィールドのフォント

    private final int RDBT_FONT_SIZE = 30; // 入力の枠の隣のラジオボタンの文字の大きさ
    private final Font RDBT_FONT = new Font("HGPゴシックM", Font.PLAIN, RDBT_FONT_SIZE); // 入力の枠の横のラベルのフォント

    private final int SLBT_FONT_SIZE = RDBT_FONT_SIZE - RDBT_FONT_SIZE/6; // (複数の選択肢から)選択ボタンの文字の大きさ
    private final Font SLBT_FONT = new Font("HGPゴシックM", Font.PLAIN, SLBT_FONT_SIZE); // 入力のフィールドのフォント

    private final int ROW_HEIGHT = 25; // 表の一行の高さ
    private final Font TABLE_FONT = new Font("游明朝", Font.PLAIN, ROW_HEIGHT-5); // 表のフォント

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

    private JLabel mbr_table_label; // 表の上に置く"在部している部員"というラベル
    private final int MBRTBLB_WIDTH = LB_FONT_SIZE * 11; // "在部している部員"というラベルの幅
    private final int MBRTBLB_HEIGHT = LB_FONT_SIZE; // "在部している部員"というラベルの高さ
    private final int MBRTBLB_X = FRAME_WIDTH/4 - MBRTBLB_WIDTH/2; // "在部している部員"というラベルの左上端のx座標
    private final int MBRTBLB_Y = 140; // "在部している部員"というラベルの左上端のy座標

    private DefaultTableModel mbr_table_model;
    private JTable mbr_table; // 在部している部員一覧表
    private JScrollPane mbr_table_spanel; // 上の表ににスクロールバーを付けたパネル
    private final int MBR_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int MBR_AREA_Y = 180; // スクロールパネルの左上端のy座標
    private final int MBRAREA_WIDTH = 800; // スクロールパネルの幅
    private final int MBRAREA_HEIGHT = 200; // スクロールパネルの高さ

    private JRadioButton mbr_num_rdbutton; // 入力フィールドの左に置く"会計の部員番号"というボタン
    private final int MBRNUMRDBT_WIDTH = RDBT_FONT_SIZE * 8; // "会計の部員番号"というボタンの幅
    private final int MBRNUMRDBT_HEIGHT = RDBT_FONT_SIZE; // "会計の部員番号"というボタンの高さ
    private final int MBRNUMRDBT_X = 100; // "会計の部員番号"というボタンの左上端のx座標
    private final int MBRNUMRDBT_Y = 400; // "会計の部員番号"というボタンの左上端のy座標
    private JTextField mbr_num_field; // 部員番号を表示するテキストフィールド
    private final int MBRNUMFD_WIDTH = RDBT_FONT_SIZE * 3; // 部員番号を表示するテキストフィールドの幅
    private final int MBRNUMFD_HEIGHT = RDBT_FONT_SIZE; // 部員番号を表示するテキストフィールドの高さ
    private final int MBRNUMFD_X = MBRNUMRDBT_X + MBRNUMRDBT_WIDTH + RDBT_FONT_SIZE/3; // 部員番号を表示するテキストフィールドの左上端のx座標
    private final int MBRNUMFD_Y = MBRNUMRDBT_Y + RDBT_FONT_SIZE/10; // 部員番号を表示するテキストフィールドの左上端のy座標

    private JLabel h_name_label; // 表示フィールドの左に置く"HN"というラベル
    private final int HNMLB_WIDTH = LB_FONT_SIZE * 2 - (int)(LB_FONT_SIZE*0.5); // "HN"というラベルの幅
    private final int HNMLB_HEIGHT = LB_FONT_SIZE; // "HN"というラベルの高さ
    private final int HNMLB_X = 470; // "HN"というラベルの左上端のx座標
    private final int HNMLB_Y = 400; // "HN"というラベルの左上端のy座標
    private JTextField h_name_field; // ハンドルネームを表示するテキストフィールド
    private final int HNMFD_WIDTH = LB_FONT_SIZE * 10; // ハンドルネームを表示するテキストフィールドの幅
    private final int HNMFD_HEIGHT = LB_FONT_SIZE; // ハンドルネームを表示するテキストフィールドの高さ
    private final int HNMFD_X = HNMLB_X + HNMLB_WIDTH + LB_FONT_SIZE/3; // ハンドルネームを表示するテキストフィールドの左上端のx座標
    private final int HNMFD_Y = HNMLB_Y + LB_FONT_SIZE/10; // ハンドルネームを表示するテキストフィールドの左上端のy座標

    private JRadioButton year_rdbutton; // 入力フィールドの左に置く"年度"というボタン
    private final int YRRDBT_WIDTH = RDBT_FONT_SIZE * 3; // "年度"というボタンの幅
    private final int YRRDBT_HEIGHT = RDBT_FONT_SIZE; // "年度"というボタンの高さ
    private final int YRRDBT_X = 100; // "年度"というボタンの左上端のx座標
    private final int YRRDBT_Y = 470; // "年度"というボタンの左上端のy座標
    private JTextField year_field; // 年度を入力するテキストフィールド
    private final int YRFD_WIDTH = RDBT_FONT_SIZE * 3; // 年度を入力するテキストフィールドの幅
    private final int YRFD_HEIGHT = RDBT_FONT_SIZE; // 年度を入力するテキストフィールドの高さ
    private final int YRFD_X = YRRDBT_X + YRRDBT_WIDTH + RDBT_FONT_SIZE/3; // 年度を表示するテキストフィールドの左上端のx座標
    private final int YRFD_Y = YRRDBT_Y + RDBT_FONT_SIZE/10; // 年度を入力するテキストフィールドの左上端のy座標

    private JRadioButton food_rdbutton; // 入力フィールドの左に置く"食品名"というボタン
    private final int FOODRDBT_WIDTH = RDBT_FONT_SIZE * 4; // "食品名"というボタンの幅
    private final int FOODRDBT_HEIGHT = RDBT_FONT_SIZE; // "食品名"というボタンの高さ
    private final int FOODRDBT_X = 320; // "食品名"というボタンの左上端のx座標
    private final int FOODRDBT_Y = 470; // "食品名"というボタンの左上端のy座標
    private JTextField food_field; // 食品名を入力するテキストフィールド
    private final int FOODFD_WIDTH = RDBT_FONT_SIZE * 9; // 食品名を入力するテキストフィールドの幅
    private final int FOODFD_HEIGHT = RDBT_FONT_SIZE; // 食品名を入力するテキストフィールドの高さ
    private final int FOODFD_X = FOODRDBT_X + FOODRDBT_WIDTH + RDBT_FONT_SIZE/3; // 食品名を表示するテキストフィールドの左上端のx座標
    private final int FOODFD_Y = FOODRDBT_Y + RDBT_FONT_SIZE/10; // 食品名を入力するテキストフィールドの左上端のy座標

    private JButton sch_button; // 検索ボタン
    private final int SCHBT_WIDTH = 170; // 検索ボタンの幅
    private final int SCHBT_HEIGHT = 60; // 検索ボタンの高さ
    private final int SCHBT_X = FRAME_WIDTH/4 - SCHBT_WIDTH/2; // 検索ボタンの左上端のx座標
    private final int SCHBT_Y = 540; // 検索ボタンの左上端のy座標

    private DefaultTableModel res_table_model;
    private JTable res_table; // 検索結果を表示する表
    private JScrollPane res_table_spanel; // 上の表にスクロールバーを付けたパネル
    private final int RES_AREA_WIDTH = 800; // スクロールパネルの幅
    private final int RES_AREA_HEIGHT = 250; // スクロールパネルの高さ
    private final int RES_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int RES_AREA_Y = 640; // スクロールパネルの左上端のy座標

    private JLabel prft_year_label; // 表示フィールドの左に置く"年度"というラベル
    private final int PRFTYRLB_WIDTH = LB_FONT_SIZE * 2; // "年度"というラベルの幅
    private final int PRFTYRLB_HEIGHT = LB_FONT_SIZE; // "年度"というラベルの高さ
    private final int PRFTYRLB_X = 100; // "年度"というラベルの左上端のx座標
    private final int PRFTYRLB_Y = 910; // "年度"というラベルの左上端のy座標
    private JComboBox prft_year_box; // 純利益の年度を入力するコンボボックス
    private final int PRFTYRBX_WIDTH = LB_FONT_SIZE * 3 - 20; // 純利益の年度を入力するコンボボックスの幅
    private final int PRFTYRBX_HEIGHT = LB_FONT_SIZE; // 純利益の年度を入力するコンボボックスの高さ
    private final int PRFTYRBX_X = PRFTYRLB_X + PRFTYRLB_WIDTH + LB_FONT_SIZE/3; // 純利益の年度を入力するコンボボックスの左上端のx座標
    private final int PRFTYRBX_Y = PRFTYRLB_Y + LB_FONT_SIZE/10; // 純利益の年度を入力するコンボボックスの左上端のy座標

    private JButton prft_button; // 今年度の純利益ボタン
    private final int PRFTBT_WIDTH = 260; // 今年度の純利益ボタンの幅
    private final int PRFTBT_HEIGHT = 60; // 今年度の純利益ボタンの高さ
    private final int PRFTBT_X = 250; // 今年度の純利益ボタンの左上端のx座標
    private final int PRFTBT_Y = 900; // 今年度の純利益ボタンの左上端のy座標

    private JButton bk_button; // 戻るボタン
    private final int BKBT_WIDTH = 90; // 戻るボタンの幅
    private final int BKBT_HEIGHT = 50; // 戻るボタンの高さ
    private final int BKBT_X = 780; // 戻るボタンの左上端のx座標
    private final int BKBT_Y = 910; // 戻るボタンの左上端のy座標

    private ButtonResponse bt_resp; // コンストラクタで受け取ったFesDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる


    public FesInfoSearcher(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // キャラクターに最初の説明を話させる
        bc_func.charaSpeak("検索方式を選択して、検索したい工大祭の模擬店情報を入力し", "命令1");

        // 画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/opr_frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // Searchのアイコンのラベルのインスタンスを取得、設定
        sch_icon = new ImageIcon("image/component/home/anikatsudb/festival/searcher/icon/icon.png");
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

        // "在部している部員"のラベルを作る
        mbr_table_label = new JLabel("《 在部している部員 》");
        // フォントを設定
        mbr_table_label.setFont(LABEL_FONT);
        // 位置設定
        mbr_table_label.setBounds(MBRTBLB_X, MBRTBLB_Y, MBRTBLB_WIDTH, MBRTBLB_HEIGHT);

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
        // 部員番号の列を右寄せ表示にする
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        TableColumnModel mbr_column_model = mbr_table.getColumnModel();
        mbr_column_model.getColumn(0).setCellRenderer(rightCellRenderer);

        // 検索結果の表を表示するスクロールパネルのインスタンスを取得、設定
        mbr_table_spanel = new JScrollPane(mbr_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        mbr_table_spanel.setPreferredSize(new Dimension(MBRAREA_WIDTH, MBRAREA_HEIGHT));
        // パネルの配置を設定
        mbr_table_spanel.setBounds(MBR_AREA_X, MBR_AREA_Y, MBRAREA_WIDTH, MBRAREA_HEIGHT);

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

        // "会計の部員番号"のラベルを作る
        mbr_num_rdbutton = new JRadioButton("会計の部員番号");
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

        // "年度"のラベルを作る
        year_rdbutton = new JRadioButton("年度");
        // フォントを設定
        year_rdbutton.setFont(RDBT_FONT);
        // 位置設定
        year_rdbutton.setBounds(YRRDBT_X, YRRDBT_Y, YRRDBT_WIDTH, YRRDBT_HEIGHT);
        // ボタンが選択されている状態にする
        year_rdbutton.setSelected(true);
        // ボタンの表示設定
        year_rdbutton.setContentAreaFilled(false);
        year_rdbutton.setBorderPainted(false);
        year_rdbutton.setFocusPainted(false);
        // ボタンを押した際の処理
        year_rdbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");
                if (year_rdbutton.isSelected()) { // 選択されているとき
                    // 入力フィールドに書き込みを行える状態にする
                    year_field.setEditable(true);
                }
                else { // 選択されていないとき
                    // 入力フィールドに書き込みを行えない状態にする
                    year_field.setEditable(false);
                }
            }
        });
        // 年度を入力するテキストフィールドを作成する
        year_field = new JTextField();
        // サイズを設定する
        year_field.setPreferredSize(new Dimension(YRFD_WIDTH, YRFD_HEIGHT));
        // フォントを設定
        year_field.setFont(FIELD_FONT);
        // 位置設定
        year_field.setBounds(YRFD_X, YRFD_Y, YRFD_WIDTH, YRFD_HEIGHT);
        // 右寄せで表示するようにする
        year_field.setHorizontalAlignment(JTextField.RIGHT);
        // 現在の年度をデフォルトでセットする
        int academic_year = getThisAcademicYear();
        year_field.setText(String.valueOf(academic_year));

        // "食品名"のラベルを作る
        food_rdbutton = new JRadioButton("食品名");
        // フォントを設定
        food_rdbutton.setFont(RDBT_FONT);
        // 位置設定
        food_rdbutton.setBounds(FOODRDBT_X, FOODRDBT_Y, FOODRDBT_WIDTH, FOODRDBT_HEIGHT);
        // ボタンが選択されている状態にする
        food_rdbutton.setSelected(true);
        // ボタンの表示設定
        food_rdbutton.setContentAreaFilled(false);
        food_rdbutton.setBorderPainted(false);
        food_rdbutton.setFocusPainted(false);
        // ボタンを押した際の処理
        food_rdbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");
                if (food_rdbutton.isSelected()) { // 選択されているとき
                    // 入力フィールドに書き込みを行える状態にする
                    food_field.setEditable(true);
                }
                else { // 選択されていないとき
                    // 入力フィールドに書き込みを行えない状態にする
                    food_field.setEditable(false);
                }
            }
        });
        // 食品名を入力するテキストフィールドを作成する
        food_field = new JTextField();
        // サイズを設定する
        food_field.setPreferredSize(new Dimension(FOODFD_WIDTH, FOODFD_HEIGHT));
        // フォントを設定
        food_field.setFont(FIELD_FONT);
        // 位置設定
        food_field.setBounds(FOODFD_X, FOODFD_Y, FOODFD_WIDTH, FOODFD_HEIGHT);

        // 登録予定表に追加ボタンの生成
        sch_button = new JButton();
        // サイズの設定
        sch_button.setPreferredSize(new Dimension(SCHBT_WIDTH, SCHBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon preaddb_icon = new ImageIcon("image/component/home/anikatsudb/festival/searcher/button/sch_button.png");
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
                String data[] = new String[3];
                // 入力情報をもとにして検索をし、検索結果表にデータを追加する
                if (getInputData(data)) { // 入力した情報に不備がないとき
                    // 入力情報をもとにして検索する
                    List res_list; // 検索結果を格納するリスト
                    try {
                        res_list = searchFesInfo(data);
                        // 検索結果を表に追加
                        updateResultTable(res_list);
                        // キャラクターに報告させる
                        bc_func.charaSpeak("工大祭の模擬店情報の検索に成功し", "報告");
                    } catch (SQLException se) {
                        // キャラクターに報告させる
                        bc_func.charaSpeak("工大祭の模擬店情報の検索に失敗し", "報告");
                        //System.out.println("検索に失敗しました。");
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
        String res_column_names[] = {"年度", "会計の部員番号", "商品名", "売上金額(売上高)"};
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
        res_sorter.setComparator(0, new Comparator<String>() { // 年度の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        res_sorter.setComparator(1, new Comparator<String>() { // 部員番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        res_sorter.setComparator(3, new Comparator<String>() { // 売上金額の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        res_table.setRowSorter(res_sorter);
        // 列のヘッダーのサイズに列の幅を合わせる
        setFirstColumnsWidth(res_table, res_column_names);
        // 年度と部員番号、売上金額の列を右寄せ表示にする
        TableColumnModel res_column_model = res_table.getColumnModel();
        res_column_model.getColumn(0).setCellRenderer(rightCellRenderer);
        res_column_model.getColumn(1).setCellRenderer(rightCellRenderer);
        res_column_model.getColumn(3).setCellRenderer(rightCellRenderer);

        // 検索結果の表を表示するスクロールパネルのインスタンスを取得、設定
        res_table_spanel = new JScrollPane(res_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        res_table_spanel.setPreferredSize(new Dimension(RES_AREA_WIDTH, RES_AREA_HEIGHT));
        // パネルの配置を設定
        res_table_spanel.setBounds(RES_AREA_X, RES_AREA_Y, RES_AREA_WIDTH, RES_AREA_HEIGHT);

        // "年度"のラベルを作る
        prft_year_label = new JLabel("年度");
        // フォントを設定
        prft_year_label.setFont(LABEL_FONT);
        // 位置設定
        prft_year_label.setBounds(PRFTYRLB_X, PRFTYRLB_Y, PRFTYRLB_WIDTH, PRFTYRLB_HEIGHT);
        // 工大祭の模擬店の年度を取得する
        List<String> fes_year_data = new ArrayList<>();
        try {
            fes_year_data = searchFesYear();
        } catch (SQLException e) {
            // キャラクターに報告させる
            bc_func.charaSpeak("工大祭の模擬店の年度取得に失敗し", "報告");
            System.out.println("工大祭の模擬店の年度取得に失敗しました");
            e.printStackTrace();
        }
        // 年度を選択するコンボボックスを作成する
        if (fes_year_data.size() != 0) { // 模擬店情報が1つでもデータベースに登録されているとき
            prft_year_box = new JComboBox(fes_year_data.toArray(new String[fes_year_data.size()]));
        }
        else { // 模擬店情報が1つもデータベースに登録されないていとき
            prft_year_box = new JComboBox();
            prft_year_box.setEnabled(false);
        }
        // サイズを設定する
        prft_year_box.setPreferredSize(new Dimension(PRFTYRBX_WIDTH, PRFTYRBX_HEIGHT));
        // フォントを設定
        prft_year_box.setFont(FIELD_FONT);
        // 位置設定
        prft_year_box.setBounds(PRFTYRBX_X, PRFTYRBX_Y, PRFTYRBX_WIDTH, PRFTYRBX_HEIGHT);
        // 背景色を白色にする
        prft_year_box.setBackground(Color.WHITE);

        // 今年度の純利益ボタンの生成
        prft_button = new JButton();
        // サイズの設定
        prft_button.setPreferredSize(new Dimension(PRFTBT_WIDTH, PRFTBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon prftb_icon = new ImageIcon("image/component/home/anikatsudb/festival/searcher/button/prft_button.png");
        prft_button.setIcon(prftb_icon);
        prft_button.setContentAreaFilled(false);
        prft_button.setBorderPainted(false);
        prft_button.setFocusPainted(false);
        // ボタンの位置設定
        prft_button.setBounds(PRFTBT_X, PRFTBT_Y, PRFTBT_WIDTH, PRFTBT_HEIGHT);
        // ボタンを押した際の処理
        prft_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("prft_button pressed");
                String prft_year[] = new String[1];
                if (getProfitYear(prft_year)) {
                    try {
                        int profit = searchFesProfit(prft_year[0]);
                        // キャラクターに報告させる
                        bc_func.charaSpeak(prft_year[0] + "年度の工大祭の模擬店の純利益は "
                                + profit + " 円", "誇張");
                        System.out.println(prft_year[0] + "年度の工大祭の模擬店の純利益は" + profit + "円");
                    } catch (SQLException se) {
                        // キャラクターに報告させる
                        bc_func.charaSpeak(prft_year[0] + "年度の純利益の取得に失敗しし", "報告");
                        System.out.println("純利益の検索に失敗しました");
                        se.printStackTrace();
                    }

                }
            }
        });

        // 戻るボタンの生成
        bk_button = new JButton();
        // サイズの設定
        bk_button.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsudb/festival/searcher/button/bk_button.png");
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
        add(mbr_table_label);
        add(mbr_table_spanel);
        add(mbr_num_rdbutton);
        add(mbr_num_field);
        add(h_name_label);
        add(h_name_field);
        add(year_rdbutton);
        add(year_field);
        add(food_rdbutton);
        add(food_field);
        add(sch_button);
        add(res_table_spanel);
        add(prft_year_label);
        add(prft_year_box);
        add(prft_button);
        add(bk_button);
        add(sch_icon_label);
        add(frame_label);
    }


    private boolean getInputData(final String data[]) {
        /* 新しく登録する予定の入力データを引数で受け取った配列に格納する
        @param data[]: 追加する工大祭の模擬店のデータを格納する配列
         */
        String mbr_num = mbr_num_field.getText();
        String year = year_field.getText().trim();
        String food = food_field.getText().trim();
        if (pftmth_button.isSelected()) { // 完全一致検索のとき
            if (mbr_num_rdbutton.isSelected() && mbr_num.isEmpty()) {
                // キャラクターに注意させる
                bc_func.charaSpeak("対象とする部員を選択し", "命令2");
                //System.out.println("no_year");
                return false;
            }
            if (year_rdbutton.isSelected() && year.isEmpty()) {
                // キャラクターに注意させる
                bc_func.charaSpeak("対象とする年を入力し", "命令2");
                //System.out.println("no_year");
                return false;
            }
            else if (food_rdbutton.isSelected() && food.isEmpty()) {
                // キャラクターに注意させる
                bc_func.charaSpeak("食品名を入力し", "命令2");
                //System.out.println("no_food");
                return false;
            }
        }

        if (year_rdbutton.isSelected()) { // 年度が検索条件の一つのとき
            if (!Pattern.compile("^-?[0-9]+$").matcher(year).find()) { // 数字出ないものが年度の欄に含まれるとき。正規表現を使って年度が数字であるかを確認している
                // キャラクターに注意させる
                bc_func.charaSpeak("年度には、数字を入力し", "命令2");
                //System.out.println("年度じゃねえ");
                return false;
            }
            else if (year.length() != 4) { // 年度の桁数が4桁ではないとき
                // キャラクターに注意させる
                bc_func.charaSpeak("年度には、4桁の数字を入力し", "命令2");
                //System.out.println("年度間違い");
                return false;
            }
        }

        if (food_rdbutton.isSelected() && food.length() > 10) { // 食品名が11文字以上のとき
            // キャラクターに注意させる
            bc_func.charaSpeak("食品名は、10文字以内で入力し", "命令2");
            //System.out.println("食品名は10文字以内");
            return false;
        }
        else { // 入力した情報に不備がないとき
            if (mbr_num_rdbutton.isSelected()) { // 部員番号を検索対象とするとき
                data[0] = mbr_num;
            }
            else { // 部員番号を検索対象としないとき
                data[0] = "";
            }

            if (year_rdbutton.isSelected()) { // 年度を検索対象とするとき
                data[1] = year;
            }
            else { // 年度を検索対象としないとき
                data[1] = "";
            }

            if (food_rdbutton.isSelected()) { // 食品名を検索対象とするとき
                data[2] = food;
            }
            else { // 食品名を検索対象としないとき
                data[2] = "";
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
            // 検索結果として追加する情報の配列を生成
            String res_array[] = {
                    String.valueOf(rs.getInt("fes_year")), // 入学年度
                    String.valueOf(rs.getInt("member_id")), // 部員番号
                    rs.getString("h_name"), // ハンドルネーム
                    rs.getString("food_name"), // 食品名
                    String.valueOf(rs.getInt("revenue")), // 売上金額(売上高)
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
        if (data[0].isEmpty() && data[1].isEmpty() && data[2].isEmpty()) { // 検索条件がないとき
            rs = stmt.executeQuery("SELECT festivals.*, h_name FROM festivals JOIN members ON id = member_id;");
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
        String data_stmt[] = new String[3]; // それぞれの検索条件におけるSQLの条件部分

        // 部員番号における検索条件
        if (data[0].isEmpty()) {
            data_stmt[0] = "";
        }
        else {
            data_stmt[0] = " member_id = " + data[0];
        }

        // 年度における検索条件
        if (data[1].isEmpty()) {
            data_stmt[1] = "";
        }
        else {
            data_stmt[1] = " fes_year = " + data[1];
        }

        // 食品名における検索条件
        if (data[2].isEmpty()) {
            data_stmt[2] = "";
        }
        else {
            data_stmt[2] = " food_name = '" + data[2] + "'";
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
        ResultSet rs = stmt.executeQuery("SELECT festivals.*, h_name FROM festivals JOIN members ON id = member_id WHERE"
                + String.join("", data_stmt) + ";");

        return rs;
    }


    private ResultSet executeAbgsSearch(final Statement stmt, final String data[]) throws SQLException {
        /* パラメータで受け取ったデータをもとにした検索のSQLを実行し、結果を返す(あいまい検索)
        @param stmt: SQLを実行するのに使うインスタンス
        @param data[]: 検索条件
        @return rs: 検索結果
         */
        String data_stmt[] = new String[3]; // それぞれの検索条件におけるSQLの条件部分

        // 部員番号における検索条件
        if (data[0].isEmpty()) {
            data_stmt[0] = "";
        }
        else {
            data_stmt[0] = " member_id = " + data[0];
        }

        // 年度における検索条件
        if (data[1].isEmpty()) {
            data_stmt[1] = "";
        }
        else {
            data_stmt[1] = " fes_year = " + data[1];
        }

        // 食品名における検索条件
        if (data[2].isEmpty()) {
            data_stmt[2] = "";
        }
        else {
            data_stmt[2] = " food_name LIKE '%" + data[2] + "%'";
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
        ResultSet rs = stmt.executeQuery("SELECT festivals.*, h_name FROM festivals JOIN members ON id = member_id WHERE"
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
                    // 指定された行の情報をテキストフィールドに反映させる
                    String data[] = getRowInfo(row);
                    setTextField(data);
                    // ボタンを押した後にボタンが"false"という表示に代わるの防ぐ
                    mbr_table.setValueAt("〇", row, 3);
                }
            });
            return button;
        }
    }


    private void resizeColumnsWidth(final JTable jtable) {
        /* 各列の幅を各列の要素の最大の幅に合わせる
         */
        TableColumnModel column_model = jtable.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(jtable.getFont());

        int row = jtable.getRowCount() - 1; // 追加された行の行番号

        for (int column = 0; column < jtable.getColumnCount(); column++) {
            int col_width = column_model.getColumn(column).getWidth(); // 現在の列の幅
            int value_width = fm.stringWidth(jtable.getValueAt(row, column).toString()) + 20; // 新しく追加された文字列の横幅(+10は余白の分)

            if (col_width < value_width) { // 新しい文字列の幅の方が列の幅より大きいとき
                // 新しい文字列の幅に列の幅を合わせる
                column_model.getColumn(column).setPreferredWidth(value_width);
                column_model.getColumn(column).setWidth(value_width);
            }
        }
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


    private String[] getRowInfo(final int row) {
        /* 指定された表の指定された行の情報を取得し、配列で返す
        @param row: 情報を取り出す対象の行
        @return res_array: 指定された行の情報を格納した配列
         */
        int range = mbr_table.getColumnCount() - 1;
        String res_array[] = new String[range]; // 返す配列
        for (int column = 0; column < range; column++) { // ボタンの行以外の情報を格納する
            res_array[column] = mbr_table.getValueAt(row, column).toString();
        }

        return res_array;
    }


    private void setTextField(final String data[]) {
        /* 入力欄を編集する部員の現在の情報で埋める
        @param data[]: 対象とする部員の情報
         */
        // 受け取ったデータをテキストフィールドにセット
        mbr_num_field.setText(data[0]); // 部員番号
        h_name_field.setText(data[2]); // ハンドルネーム
    }


    private int getThisAcademicYear() {
        Calendar calendar = Calendar.getInstance();

        int acyear; // 年度を格納するための変数
        int month = calendar.get(Calendar.MONTH); // 現在の月
        if (month < 4) { // 3月以前
            acyear = calendar.get(Calendar.YEAR) - 1;
        }
        else { // 4月以降
            acyear = calendar.get(Calendar.YEAR);
        }

        return acyear;
    }


    private boolean getProfitYear(final String prft_year[]) {
        /* 検索の際の入力データを引数で受け取った配列に格納する
        @param data[]: 登録する部費情報を格納する配列
         */
        String year = (String)prft_year_box.getSelectedItem();
        if (year == null) { // コンボボックスをいじれないとき
            System.out.println("工大祭情報が一つも登録されていない");
            return false;
        }
        else { // 入力し忘れている情報がないとき
            prft_year[0] = year;

            return true;
        }
    }


    private List<String> searchFesYear() throws SQLException {
        /* 工大祭の模擬店情報が登録されている年度をリストに格納して返す
        @return res_list: 工大祭の模擬店情報が登録されている年度を格納したリスト
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();
        // 現在一番最大の部員番号を参照
        ResultSet rs = stmt.executeQuery("SELECT fes_year FROM festivals;");

        List<String> res_list = new ArrayList<>(); // 検索結果を格納するためのリスト
        // 検索結果を取得し、格納する
        while (rs.next()) {
            // リストに追加する
            res_list.add(rs.getString("fes_year"));
        }

        // リソースを解放する
        stmt.close();
        // データベースを閉じる
        closeDB();

        return res_list;
    }


    private int searchFesProfit(final String prft_year) throws SQLException {
        /* 指定した年度の工大祭の模擬店の純利益を検索し、指定した年度の純利益をを返す
        @param prft_year: 検索対象とする年度
        @return profit: 今年度の工大祭の純利益
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();

        // 今年度の工大祭の模擬店の純利益を取得
        ResultSet rs = stmt.executeQuery("SELECT revenue FROM festivals WHERE fes_year = '" + prft_year + "';");
        int revenue = 0; // 工大祭の模擬店での売上高
        while (rs.next()) {
            revenue = rs.getInt("revenue");
        }

        // 今年度の工大祭の模擬店の費用を取得
        String start_date = prft_year + "-04-01";
        String next_year = String.valueOf(Integer.parseInt(prft_year)+1);
        String end_date = next_year + "-03-31";
        rs = stmt.executeQuery("SELECT SUM(price) FROM purchases WHERE is_for_festival = true AND" +
                " purchase_date BETWEEN '" + start_date + "' AND '" + end_date + "';");
        int expenses = 0; // 工大祭の模擬店での売上高
        while (rs.next()) {
            expenses = rs.getInt("SUM(price)");
        }

        // 今年度に学校からもらった給付金を取得
        rs = stmt.executeQuery("SELECT SUM(amount) FROM budgets WHERE member_id = 1 AND" +
                " paid_date BETWEEN '" + start_date + "' AND '" + end_date + "';");
        int benefit = 0; // 学校からもらった給付金
        while (rs.next()) {
            benefit = rs.getInt("SUM(amount)");
        }

        // リソースを解放する
        stmt.close();
        // データベースを閉じる
        closeDB();

        // 純利益を計算する
        int profit = revenue - expenses + benefit;

        return profit;
    }
}
