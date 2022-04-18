package component.home.anikatsudb.budget;

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
import java.util.HashMap;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.regex.Pattern;

import function.db.DBOperator;
import function.BasicFunction;
import function.button.ButtonResponse;



public class BgtInfoSearcher extends DBOperator {

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

    private final int ROW_HEIGHT = 25; // 表の一行の高さ
    private final Font TABLE_FONT = new Font("游明朝", Font.PLAIN, ROW_HEIGHT-5); // 表のフォント

    private JLabel mbr_table_label; // 表の上に置く"在部している部員"というラベル
    private final int MBRTBLB_WIDTH = LB_FONT_SIZE * 11; // "在部している部員"というラベルの幅
    private final int MBRTBLB_HEIGHT = LB_FONT_SIZE; // "在部している部員"というラベルの高さ
    private final int MBRTBLB_X = FRAME_WIDTH/4 - MBRTBLB_WIDTH/2; // "在部している部員"というラベルの左上端のx座標
    private final int MBRTBLB_Y = 100; // "在部している部員"というラベルの左上端のy座標

    private DefaultTableModel mbr_table_model;
    private JTable mbr_table; // 在部している部員一覧表
    private JScrollPane mbr_table_spanel; // 上の表ににスクロールバーを付けたパネル
    private final int MBR_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int MBR_AREA_Y = 140; // スクロールパネルの左上端のy座標
    private final int MBR_AREA_WIDTH = 800; // スクロールパネルの幅
    private final int MBR_AREA_HEIGHT = 230; // スクロールパネルの高さ

    private HashMap<String, String[]> mbr_info_map; // 各部員の部員番号をキーとし、氏名とハンドルネームを格納しているマップ

    private final int FD_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/3; // 入力のフィールドの文字の大きさ
    private final Font FIELD_FONT = new Font("游明朝", Font.PLAIN, FD_FONT_SIZE); // 入力のフィールドのフォント

    private JLabel mbr_info_label; // "検索条件"というラベル
    private final int MBRIFLB_WIDTH = LB_FONT_SIZE * 7; // "検索条件"というラベルの幅
    private final int MBRIFLB_HEIGHT = LB_FONT_SIZE; // "検索条件"というラベルの高さ
    private final int MBRIFLB_X = FRAME_WIDTH/4 - MBRIFLB_WIDTH/2; // "検索条件"というラベルの左上端のx座標
    private final int MBRIFLB_Y = 390; // "検索条件"というラベルの左上端のy座標

    private JLabel mbr_num_label; // 表示フィールドの左に置く"部員番号"というラベル
    private final int MBRNUMLB_WIDTH = LB_FONT_SIZE * 4; // "部員番号"というラベルの幅
    private final int MBRNUMLB_HEIGHT = LB_FONT_SIZE; // "部員番号"というラベルの高さ
    private final int MBRNUMLB_X = 100; // "部員番号"というラベルの左上端のx座標
    private final int MBRNUMLB_Y = 440; // "部員番号"というラベルの左上端のy座標
    private JTextField mbr_num_field; // 部員番号を表示するテキストフィールド
    private final int MBRNUMFD_WIDTH = LB_FONT_SIZE * 3; // 部員番号を表示するテキストフィールドの幅
    private final int MBRNUMFD_HEIGHT = LB_FONT_SIZE; // 部員番号を表示するテキストフィールドの高さ
    private final int MBRNUMFD_X = MBRNUMLB_X + MBRNUMLB_WIDTH + LB_FONT_SIZE/3; // 部員番号を表示するテキストフィールドの左上端のx座標
    private final int MBRNUMFD_Y = MBRNUMLB_Y + LB_FONT_SIZE/10; // 部員番号を表示するテキストフィールドの左上端のy座標

    private JLabel h_name_label; // 表示フィールドの左に置く"HN"というラベル
    private final int HNMLB_WIDTH = LB_FONT_SIZE * 2 - (int)(LB_FONT_SIZE*0.5); // "HN"というラベルの幅
    private final int HNMLB_HEIGHT = LB_FONT_SIZE; // "HN"というラベルの高さ
    private final int HNMLB_X = 380; // "HN"というラベルの左上端のx座標
    private final int HNMLB_Y = 440; // "HN"というラベルの左上端のy座標
    private JTextField h_name_field; // ハンドルネームを表示するテキストフィールド
    private final int HNMFD_WIDTH = LB_FONT_SIZE * 12; // ハンドルネームを表示するテキストフィールドの幅
    private final int HNMFD_HEIGHT = LB_FONT_SIZE; // ハンドルネームを表示するテキストフィールドの高さ
    private final int HNMFD_X = HNMLB_X + HNMLB_WIDTH + LB_FONT_SIZE/3; // ハンドルネームを表示するテキストフィールドの左上端のx座標
    private final int HNMFD_Y = HNMLB_Y + LB_FONT_SIZE/10; // ハンドルネームを表示するテキストフィールドの左上端のy座標

    private JLabel year_label; // 入力フィールドの左に置く"対象年"というラベル
    private final int YEARLB_WIDTH = LB_FONT_SIZE * 3; // "対象年"というラベルの幅
    private final int YEARLB_HEIGHT = LB_FONT_SIZE; // "対象年"というラベルの高さ
    private final int YEARLB_X = 100; // "対象年"というラベルの左上端のx座標
    private final int YEARLB_Y = 510; // "対象年"というラベルの左上端のy座標
    private JTextField year_field; // 対象年を表示するテキストフィールド
    private final int YEARFD_WIDTH = LB_FONT_SIZE * 3; // 対象年を表示するテキストフィールドの幅
    private final int YEARFD_HEIGHT = LB_FONT_SIZE; // 対象年を表示するテキストフィールドの高さ
    private final int YEARFD_X = YEARLB_X + YEARLB_WIDTH + LB_FONT_SIZE/3; // 対象年を表示するテキストフィールドの左上端のx座標
    private final int YEARFD_Y = YEARLB_Y + LB_FONT_SIZE/10; // 対象年を表示するテキストフィールドの左上端のy座標

    private JButton blc_button; // 部費の残高ボタン
    private final int BLCBT_WIDTH = 260; // 部費の残高ボタンの幅
    private final int BLCBT_HEIGHT = 60; // 部費の残高ボタンの高さ
    private final int BLCBT_X = 640; // 部費の残高ボタンの左上端のx座標
    private final int BLCBT_Y = 70; // 部費の残高ボタンの左上端のy座標

    private JButton sch_button; // 検索ボタン
    private final int SCHBT_WIDTH = 170; // 検索ボタンの幅
    private final int SCHBT_HEIGHT = 60; // 検索ボタンの高さ
    private final int SCHBT_X = FRAME_WIDTH/4 - SCHBT_WIDTH/2; // 検索ボタンの左上端のx座標
    private final int SCHBT_Y = 560; // 検索ボタンの左上端のy座標

    private HashMap<String, String> money_info_map; // 各月をキーとし、その月に支払った金額を格納しているマップ

    private JLabel res_table_label; // 表の上に置く"《 　　の　　　　の部費情報 》"というラベル
    private final int RESTBLB_WIDTH = LB_FONT_SIZE * 22; // "《 　　の　　　　の部費情報 》"というラベルの幅
    private final int RESTBLB_HEIGHT = LB_FONT_SIZE; // "《 　　の　　　　の部費情報 》"というラベルの高さ
    private final int RESTBLB_X = FRAME_WIDTH/4 - RESTBLB_WIDTH/2; // "《 　　の　　　　の部費情報 》"というラベルの左上端のx座標
    private final int RESTBLB_Y = 670; // "《 　　の　　　　の部費情報 》"というラベルの左上端のy座標
    private JTextField res_h_name_field; // 検索対象のハンドルネームを表示するテキストフィールド
    private final int RESHNMFD_WIDTH = LB_FONT_SIZE * 8; // 検索対象のハンドルネームを表示するテキストフィールドの幅
    private final int RESHNMFD_HEIGHT = LB_FONT_SIZE; // 検索対象のハンドルネームを表示するテキストフィールドの高さ
    private final int RESHNMFD_X = 190; // 検索対象のハンドルネームを表示するテキストフィールドの左上端のx座標
    private final int RESHNMFD_Y = RESTBLB_Y + LB_FONT_SIZE/10; // 検索対象のハンドルネームを表示するテキストフィールドの左上端のy座標
    private JTextField res_year_field; // 対象年を表示するテキストフィールド
    private final int RESYEARFD_WIDTH = LB_FONT_SIZE * 3; // 対象年を表示するテキストフィールドの幅
    private final int RESYEARFD_HEIGHT = LB_FONT_SIZE; // 対象年を表示するテキストフィールドの高さ
    private final int RESYEARFD_X = 470; // 対象年を表示するテキストフィールドの左上端のx座標
    private final int RESYEARFD_Y = RESTBLB_Y + LB_FONT_SIZE/10; // 対象年を表示するテキストフィールドの左上端のy座標

    private DefaultTableModel res_table_model;
    private JTable res_table; // 検索結果を表示する表
    private JScrollPane res_table_spanel; // 上の表にスクロールバーを付けたパネル
    private final int RES_AREA_WIDTH = 285; // スクロールパネルの幅
    private final int RES_AREA_HEIGHT = 230; // スクロールパネルの高さ
    private final int RES_AREA_X = FRAME_WIDTH/4 - RES_AREA_WIDTH/2; // スクロールパネルの左上端のx座標
    private final int RES_AREA_Y = 720; // スクロールパネルの左上端のy座標

    private JButton bk_button; // 戻るボタン
    private final int BKBT_WIDTH = 90; // 戻るボタンの幅
    private final int BKBT_HEIGHT = 50; // 戻るボタンの高さ
    private final int BKBT_X = 780; // 戻るボタンの左上端のx座標
    private final int BKBT_Y = 910; // 戻るボタンの左上端のy座標

    private ButtonResponse bt_resp; // コンストラクタで受け取ったBgtDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる


    public BgtInfoSearcher(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // キャラクターに最初の説明を話させる
        bc_func.charaSpeak("部費の提出状況を確認したい部員を選んでから、対象とする年を入力し", "命令1");

        // 画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/opr_frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // Deleteのアイコンのラベルのインスタンスを取得、設定
        sch_icon = new ImageIcon("image/component/home/anikatsudb/budget/searcher/icon/icon.png");
        sch_icon_label = new JLabel(sch_icon);
        sch_icon_label.setBounds(ICON_X, ICON_Y, ICON_WIDTH, ICON_HEIGHT);

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

        // 検索結果の表を表示するスクロールパネルのインスタンスを取得、設定
        mbr_table_spanel = new JScrollPane(mbr_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        mbr_table_spanel.setPreferredSize(new Dimension(MBR_AREA_WIDTH, MBR_AREA_HEIGHT));
        // パネルの配置を設定
        mbr_table_spanel.setBounds(MBR_AREA_X, MBR_AREA_Y, MBR_AREA_WIDTH, MBR_AREA_HEIGHT);

        // 在部している部員一覧を作成
        try {
            // 在部している部員の情報をデータベースから取得
            List<String[]> data_list = getMbrInfo();
            // 在部している部員一覧表を作成
            setMbrTable(data_list);
            // 部員番号をキーとして、部員情報を格納したマップを作成
            mbr_info_map = createMbrInfoMap(data_list);
        } catch (SQLException se) {
            // キャラクターに報告させる
            bc_func.charaSpeak("在部している部員の情報を取得するのに失敗し", "報告");
            //System.out.println("在部している部員の情報を取得するのに失敗しました。");
            se.printStackTrace();
        }

        // "変更後の情報"のラベルを作る
        mbr_info_label = new JLabel("《 検索条件 》");
        // フォントを設定
        mbr_info_label.setFont(LABEL_FONT);
        // 位置設定
        mbr_info_label.setBounds(MBRIFLB_X, MBRIFLB_Y, MBRIFLB_WIDTH, MBRIFLB_HEIGHT);

        // "部員番号"のラベルを作る
        mbr_num_label = new JLabel("部員番号");
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

        // "対象年"のラベルを作る
        year_label = new JLabel("対象年");
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
        // 現在の年をデフォルトでセットする
        Calendar calendar = Calendar.getInstance();
        year_field.setText(String.valueOf(calendar.get(Calendar.YEAR)));

        // 検索ボタンの生成
        sch_button = new JButton();
        // サイズの設定
        sch_button.setPreferredSize(new Dimension(SCHBT_WIDTH, SCHBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon schb_icon = new ImageIcon("image/component/home/anikatsudb/budget/searcher/button/sch_button.png");
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
                String data[] = new String[3];
                // 入力情報をもとにして検索をし、検索結果表にデータを追加する
                if (getInputData(data)) { // 入力した情報に不備がないとき
                    // 入力情報をもとにして検索する
                    List res_list; // 検索結果を格納するリスト
                    try {
                        res_list = searchMoneyInfo(data);
                        // 検索結果をもとに、money_info_mapを生成
                        money_info_map = createMoneyInfoMap(res_list);
                        // 結果を表に追加
                        updateResultTable(money_info_map);
                        // 検索結果表の上のラベルの中のテキストフィールドに対象者の情報を入れる
                        res_h_name_field.setText(data[1]);
                        res_year_field.setText(data[2]);
                        // キャラクターに報告させる
                        bc_func.charaSpeak("部費情報の検索に成功し", "報告");
                    } catch (SQLException se) {
                        // キャラクターに報告させる
                        bc_func.charaSpeak("部費情報の検索に失敗し", "報告");
                        //System.out.println("検索に失敗しました。");
                        se.printStackTrace();
                    }
                    // テキストフィールドの更新
                    //updateOnNextTextFields();
                }
            }
        });
        // 最初は押せないようにしておく
        sch_button.setEnabled(false);

        // "退部する部員"のラベルを作る
        res_table_label = new JLabel("《  　　　　　　　　の　 　　年の部費情報 》");
        // フォントを設定
        res_table_label.setFont(LABEL_FONT);
        // 位置設定
        res_table_label.setBounds(RESTBLB_X, RESTBLB_Y, RESTBLB_WIDTH, RESTBLB_HEIGHT);
        // ハンドルネームを入力するテキストフィールドを作成する
        res_h_name_field = new JTextField();
        // サイズを設定する
        res_h_name_field.setPreferredSize(new Dimension(RESHNMFD_WIDTH, RESHNMFD_HEIGHT));
        // フォントを設定
        res_h_name_field.setFont(FIELD_FONT);
        // 位置設定
        res_h_name_field.setBounds(RESHNMFD_X, RESHNMFD_Y, RESHNMFD_WIDTH, RESHNMFD_HEIGHT);
        // 編集ができないようにする
        res_h_name_field.setEditable(false);
        // 対象年を入力するテキストフィールドを作成する
        res_year_field = new JTextField();
        // サイズを設定する
        res_year_field.setPreferredSize(new Dimension(RESYEARFD_WIDTH, RESYEARFD_HEIGHT));
        // フォントを設定
        res_year_field.setFont(FIELD_FONT);
        // 位置設定
        res_year_field.setBounds(RESYEARFD_X, RESYEARFD_Y, RESYEARFD_WIDTH, RESYEARFD_HEIGHT);
        // 編集ができないようにする
        res_year_field.setEditable(false);
        // 右寄せで表示するようにする
        res_year_field.setHorizontalAlignment(JTextField.RIGHT);


        // 部費情報の表のモデルを作成する
        res_table_model = new DefaultTableModel() {
            // ボタンの行だけ反応するようにする
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // 各列の列名をセットする
        String res_column_names[] = {"月", "提出状況", "支払額"};
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
        res_sorter.setComparator(0, new Comparator<String>() { // 月の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        res_sorter.setComparator(2, new Comparator<String>() { // 支払金額の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        res_table.setRowSorter(res_sorter);
        // 列のヘッダーのサイズに列の幅を合わせる
        setFirstColumnsWidth(res_table, res_column_names);

        // 検索結果の表を表示するスクロールパネルのインスタンスを取得、設定
        res_table_spanel = new JScrollPane(res_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // サイズの設定
        res_table_spanel.setPreferredSize(new Dimension(RES_AREA_WIDTH, RES_AREA_HEIGHT));
        // パネルの配置を設定
        res_table_spanel.setBounds(RES_AREA_X, RES_AREA_Y, RES_AREA_WIDTH, RES_AREA_HEIGHT);

        // それぞれの表の部員番号の列、月と支払額の列を右寄せ表示にする
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        TableColumnModel mbr_column_model = mbr_table.getColumnModel();
        TableColumnModel res_column_model = res_table.getColumnModel();
        mbr_column_model.getColumn(0).setCellRenderer(rightCellRenderer);
        res_column_model.getColumn(0).setCellRenderer(rightCellRenderer);
        res_column_model.getColumn(2).setCellRenderer(rightCellRenderer);
        // 提出状況の列を中央寄せにする
        DefaultTableCellRenderer centerCellRenderer = new DefaultTableCellRenderer();
        centerCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        res_column_model.getColumn(1).setCellRenderer(centerCellRenderer);

        // 残高ボタンの生成
        blc_button = new JButton();
        // サイズの設定
        blc_button.setPreferredSize(new Dimension(BLCBT_WIDTH, BLCBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon blcb_icon = new ImageIcon("image/component/home/anikatsudb/budget/searcher/button/blc_button.png");
        blc_button.setIcon(blcb_icon);
        blc_button.setContentAreaFilled(false);
        blc_button.setBorderPainted(false);
        blc_button.setFocusPainted(false);
        // ボタンの位置設定
        blc_button.setBounds(BLCBT_X, BLCBT_Y, BLCBT_WIDTH, BLCBT_HEIGHT);
        // ボタンを押した際の処理
        blc_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("blc_button pressed");
                try {
                    // 残高を取得
                    int balance = searchBalance();
                    // キャラクターに報告させる
                    bc_func.charaSpeak("部費の残高は " + balance + " 円", "誇張");
                } catch (SQLException se) {
                    // キャラクターに報告させる
                    bc_func.charaSpeak("部費の残高の取得に失敗し", "報告");
                    System.out.println("部費の残高の取得に失敗しました");
                    se.printStackTrace();
                }
            }
        });

        // 戻るボタンの生成
        bk_button = new JButton();
        // サイズの設定
        bk_button.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsudb/budget/searcher/button/bk_button.png");
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

        add(mbr_table_label);
        add(mbr_table_spanel);
        add(mbr_info_label);
        add(mbr_num_label);
        add(mbr_num_field);
        add(h_name_label);
        add(h_name_field);
        add(year_label);
        add(year_field);
        add(sch_button);
        add(res_table_label);
        add(res_h_name_field);
        add(res_year_field);
        add(res_table_spanel);
        add(blc_button);
        add(bk_button);
        add(sch_icon_label);
        add(frame_label);
    }

    private List<String[]> searchMoneyInfo(final String data[]) throws SQLException {
        /* パラメータで受け取ったデータをもとにして部員情報を検索し、検索結果を格納したリストを返す
        @param data[]: テキストフィールドに入力した検索条件
        @return res_list: 検索結果を格納したリスト
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();

        String start_date = data[2] + "-01-01";
        String end_date = data[2] + "-12-31";

        String sql = "SELECT MONTH(paid_date), SUM(amount) FROM budgets WHERE member_id = " + data[0]
                + " AND paid_date BETWEEN '" + start_date + "' AND '" + end_date
                + "' GROUP BY MONTH(paid_date) ORDER BY MONTH(paid_date) ASC;";

        // 条件に沿って検索する
        ResultSet rs = stmt.executeQuery(sql);

        List<String[]> res_list = new ArrayList<>(); // 検索結果を格納するためのリスト
        // 検索結果を取得し、格納する
        while (rs.next()) {
            // 検索結果として追加する情報の配列を生成
            String res_array[] = {
                    String.valueOf(rs.getInt("MONTH(paid_date)")), // 月
                    String.valueOf(rs.getInt("SUM(amount)")), // 支払額
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


    private void updateResultTable(final HashMap<String, String> map) {
        /* パラメータで受け取ったデータをもとにして検索結果の表を更新する
        @param data_list: 検索結果を格納しているリスト
         */
        // そのとき検索結果の表にセットされている行をすべて削除する
        res_table_model.setRowCount(0);

        // 各月のデータを表に加える
        for (int i = 1; i <= 12; i++) {
            // 表に加えるデータを作成する
            String data[] = new String[3];

            // その月を格納
            String month = String.valueOf(i);
            data[0] = month;
            // 提出状況を格納
            if (map.get(month).equals("\\0")) { // 支払っていないとき
                data[1] = "x";
            }
            else { // 1円でも支払っているとき
                data[1] = "〇";
            }
            // 支払額を格納
            data[2] = map.get(month);

            // 検索結果のうちの1行を追加する
            res_table_model.addRow(data);
            // 表のサイズを調整
            resizeColumnsWidth(res_table);
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
                    // 指定された行の情報を取得する
                    String data[] = getRowInfo(row);
                    // テキストフィールドに部員情報を移す
                    setTextField(data);
                    // ボタンを押した後にボタンが"false"という表示に代わるの防ぐ
                    mbr_table.setValueAt("〇", row, 3);

                    // 検索ボタンを押せるようにする
                    sch_button.setEnabled(true);
                }
            });
            return button;
        }
    }


    private boolean getInputData(final String data[]) {
        /* 新しく登録する予定の入力データを引数で受け取った配列に格納する
        @param data[]: 登録する部費情報を格納する配列
         */
        String year = year_field.getText().trim();//money_field.getText().trim();
        if (year.isEmpty()) { // 入力し忘れている情報があるとき
            // キャラクターに注意させる
            bc_func.charaSpeak("入力していない枠を入力し", "命令2");
            return false;
        }
        else if (!Pattern.compile("^-?[0-9]+$").matcher(year).find()) { // 数字出ないものが対象年の欄に含まれるとき。正規表現を使って入学年度が数字であるかを確認している
            // キャラクターに注意させる
            bc_func.charaSpeak("対象とする年には、数字を入力し", "命令2");
            //System.out.println("数字じゃなくね");
            return false;
        }
        else if (year.length() != 4) { // 年の桁数が4桁ではないとき
            // キャラクターに注意させる
            bc_func.charaSpeak("対象とする年には、4桁の数字を入力し", "命令2");
            //System.out.println("年度間違い");
            return false;
        }
        else { // 入力し忘れている情報がないとき
            data[0] = mbr_num_field.getText();
            data[1] = h_name_field.getText();
            data[2] = year;

            return true;
        }
    }


    private void setTextField(final String data[]) {
        /* 入力欄を編集する部員の現在の情報で埋める
        @param data[]: 対象とする部員の情報
         */
        // 受け取ったデータをテキストフィールドにセット
        mbr_num_field.setText(data[0]); // 部員番号
        h_name_field.setText(data[2]); // ハンドルネーム

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


    private List<String[]> getMbrInfo() throws SQLException {
        /* 現在、部に所属しているメンバーの情報を取り出し、情報(削除ボタン付き)を格納したリストを返す
        @return res_list: 在部している部員情報を格納した
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();
        // 現在、在部している部員の部員情報を取得
        ResultSet rs = stmt.executeQuery("SELECT id, name, h_name FROM members WHERE is_in_club = true;");

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


    private void setFirstColumnsWidth(final JTable res_table, final String[] col_names) {
        TableColumnModel column_model = res_table.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(res_table.getTableHeader().getFont());

        for (int i = 0; i < col_names.length; i++) {
            int width = fm.stringWidth(col_names[i]) + 20;
            column_model.getColumn(i).setPreferredWidth(width);
            column_model.getColumn(i).setWidth(width);
        }
    }


    private HashMap<String, String[]> createMbrInfoMap(final List<String[]> list) {
        /* パラメータで受け取った各部員の情報を入れたリストをもとにしてmbr_info_mapを作成する
        @param list: 各部員の情報を入れたリスト
        @return map: 部員番号をキーとして、氏名とハンドルネームを格納したマップ
         */
        HashMap<String, String[]> map = new HashMap<>();
        // 各部員の情報をマップに登録
        for (String data[]: list) {
            String mbr_num = data[0];
            String mbr_info[] = {data[0], data[1], data[2], "〇"};
            map.put(mbr_num, mbr_info);
        }

        return map;
    }


    private HashMap<String, String> createMoneyInfoMap(final List<String[]> list) {
        /* パラメータで受け取った検索結果のリストをもとにして、money_info_mapを作成する
        @param list: searchMoneyInfoメソッドから受け取ったリスト
        @return map: 月をキーとして、その月に支払った額を格納したマップ
         */
        HashMap<String, String> map = new HashMap<>();
        // 検索結果をもとにしてマップを作成する
        for (String data[] : list) {
            map.put(data[0], "\\"+data[1]);
        }
        // 1～12月の間で支払っていない月は、支払額を0円としてマップに加える
        for (int month = 1; month <= 12; month++) {
            String month_str = String.valueOf(month);
            if (!map.containsKey(month_str)) { // 対象月がマップにキーとして登録されていないとき
                map.put(month_str, "\\0");
            }
        }

        return map;
    }


    private int searchBalance() throws SQLException {
        /* 部費の残高を求めるメソッド
        @return balance: 部費の残高
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();

        // 工大祭の模擬店による収入額を取得
        ResultSet rs = stmt.executeQuery("SELECT SUM(revenue) FROM festivals;");
        int revenue = 0; // 工大祭の模擬店による収入額
        while (rs.next()) {
            revenue = rs.getInt("SUM(revenue)");
        }

        // 部費等による収入額を取得
        rs = stmt.executeQuery("SELECT SUM(amount) FROM budgets;");
        int income = 0; // 部費等による収入額
        while (rs.next()) {
            income = rs.getInt("SUM(amount)");
        }

        // 支出額を取得
        rs = stmt.executeQuery("SELECT SUM(price) FROM purchases;");
        int expenses = 0; // 支出額
        while (rs.next()) {
            expenses = rs.getInt("SUM(price)");
        }

        // リソースを解放する
        stmt.close();
        // データベースを閉じる
        closeDB();

        // 純利益を計算する
        int balance = revenue + income - expenses;

        return balance;
    }
}
