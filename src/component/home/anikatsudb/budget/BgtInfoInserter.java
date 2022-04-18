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
import java.util.Date;
import java.util.HashMap;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.regex.Pattern;

import function.db.DBOperator;
import function.BasicFunction;
import function.button.ButtonResponse;
import function.confirmation.Confirmer;


public class BgtInfoInserter extends DBOperator {

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

    private final int LB_FONT_SIZE = 30; // 表の上のラベルの文字の大きさ
    private final Font LABEL_FONT = new Font("HGPゴシックM", Font.PLAIN, LB_FONT_SIZE); // 表の上のラベルのフォント

    private final int ROW_HEIGHT = 25; // 表の一行の高さ
    private final Font TABLE_FONT = new Font("游明朝", Font.PLAIN, ROW_HEIGHT-5); // 表のフォント

    private final int AREA_WIDTH = 800; // スクロールパネルの幅
    private final int AREA_HEIGHT = 230; // スクロールパネルの高さ

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
    
    private HashMap<String, String[]> mbr_info_map; // 各部員の部員番号をキーとし、氏名とハンドルネームを格納しているマップ

    private final int FD_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/3; // 入力のフィールドの文字の大きさ
    private final Font FIELD_FONT = new Font("游明朝", Font.PLAIN, FD_FONT_SIZE); // 入力のフィールドのフォント

    private final int BT_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/6; // ボタンの文字の大きさ
    private final Font BUTTON_FONT = new Font("HGPゴシックM", Font.PLAIN, BT_FONT_SIZE); // 入力のフィールドのフォント

    private JLabel mbr_num_label; // 表示フィールドの左に置く"部員番号"というラベル
    private final int MBRNUMLB_WIDTH = LB_FONT_SIZE * 4; // "部員番号"というラベルの幅
    private final int MBRNUMLB_HEIGHT = LB_FONT_SIZE; // "部員番号"というラベルの高さ
    private final int MBRNUMLB_X = 100; // "部員番号"というラベルの左上端のx座標
    private final int MBRNUMLB_Y = 390; // "部員番号"というラベルの左上端のy座標
    private JTextField mbr_num_field; // 部員番号を表示するテキストフィールド
    private final int MBRNUMFD_WIDTH = LB_FONT_SIZE * 3; // 部員番号を表示するテキストフィールドの幅
    private final int MBRNUMFD_HEIGHT = LB_FONT_SIZE; // 部員番号を表示するテキストフィールドの高さ
    private final int MBRNUMFD_X = MBRNUMLB_X + MBRNUMLB_WIDTH + LB_FONT_SIZE/3; // 部員番号を表示するテキストフィールドの左上端のx座標
    private final int MBRNUMFD_Y = MBRNUMLB_Y + LB_FONT_SIZE/10; // 部員番号を表示するテキストフィールドの左上端のy座標

    private JLabel h_name_label; // 表示フィールドの左に置く"HN"というラベル
    private final int HNMLB_WIDTH = LB_FONT_SIZE * 2 - (int)(LB_FONT_SIZE*0.5); // "HN"というラベルの幅
    private final int HNMLB_HEIGHT = LB_FONT_SIZE; // "HN"というラベルの高さ
    private final int HNMLB_X = 380; // "HN"というラベルの左上端のx座標
    private final int HNMLB_Y = 390; // "HN"というラベルの左上端のy座標
    private JTextField h_name_field; // ハンドルネームを表示するテキストフィールド
    private final int HNMFD_WIDTH = LB_FONT_SIZE * 12; // ハンドルネームを表示するテキストフィールドの幅
    private final int HNMFD_HEIGHT = LB_FONT_SIZE; // ハンドルネームを表示するテキストフィールドの高さ
    private final int HNMFD_X = HNMLB_X + HNMLB_WIDTH + LB_FONT_SIZE/3; // ハンドルネームを表示するテキストフィールドの左上端のx座標
    private final int HNMFD_Y = HNMLB_Y + LB_FONT_SIZE/10; // ハンドルネームを表示するテキストフィールドの左上端のy座標

    private JLabel date_label; // 表示フィールドの左に置く"支払日"というラベル
    private final int DATELB_WIDTH = LB_FONT_SIZE * 3; // "支払日"というラベルの幅
    private final int DATELB_HEIGHT = LB_FONT_SIZE; // "支払日"というラベルの高さ
    private final int DATELB_X = 100; // "支払日"というラベルの左上端のx座標
    private final int DATELB_Y = 460; // "支払日"というラベルの左上端のy座標
    JSpinner.DateEditor date_editor;
    private JSpinner date_spinner; // 日付を入力するテキストフィールド
    private final int DATEFD_WIDTH = LB_FONT_SIZE * 5 - 10; // 日付を入力するテキストフィールドの幅
    private final int DATEFD_HEIGHT = LB_FONT_SIZE; // 日付を入力するテキストフィールドの高さ
    private final int DATEFD_X = DATELB_X + DATELB_WIDTH + LB_FONT_SIZE/3; // 日付を入力するテキストフィールドの左上端のx座標
    private final int DATEFD_Y = DATELB_Y + LB_FONT_SIZE/10; // 日付を入力するテキストフィールドの左上端のy座標

    private JLabel money_label; // 入力フィールドの左に置く"支払金額"というラベル
    private final int MNYLB_WIDTH = LB_FONT_SIZE * 4; // "支払金額"というラベルの幅
    private final int MNYLB_HEIGHT = LB_FONT_SIZE; // "入学年度"というラベルの高さ
    private final int MNYLB_X = 400; // "支払金額"というラベルの左上端のx座標
    private final int MNYLB_Y = 460; // "支払金額"というラベルの左上端のy座標
    private JTextField money_field; // 支払金額を表示するテキストフィールド
    private final int MNYFD_WIDTH = LB_FONT_SIZE * 3; // 支払金額を表示するテキストフィールドの幅
    private final int MNYFD_HEIGHT = LB_FONT_SIZE; // 支払金額を表示するテキストフィールドの高さ
    private final int MNYFD_X = MNYLB_X + MNYLB_WIDTH + LB_FONT_SIZE/3; // 支払金額を表示するテキストフィールドの左上端のx座標
    private final int MNYFD_Y = MNYLB_Y + LB_FONT_SIZE/10; // 支払金額を表示するテキストフィールドの左上端のy座標

    private JButton pre_add_button; // 登録予定表に追加ボタン
    private final int PREADDBT_WIDTH = 290; // 登録予定表に追加ボタンの幅
    private final int PREADDBT_HEIGHT = 60; // 登録予定表に追加ボタンの高さ
    private final int PREADDBT_X = FRAME_WIDTH/4 - PREADDBT_WIDTH/2; // 登録予定表に追加ボタンの左上端のx座標
    private final int PREADDBT_Y = 520; // 登録予定表に追加ボタンの左上端のy座標

    private DefaultTableModel bgt_table_model;
    private JTable bgt_table; // 追加予定の部費の表表示する表
    private JScrollPane bgt_table_spanel; // 上の表にスクロールバーを付けたパネル
    private final int BGT_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int BGT_AREA_Y = 610; // スクロールパネルの左上端のy座標

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

    private ButtonResponse bt_resp; // コンストラクタで受け取ったBgtDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる


    public BgtInfoInserter(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // キャラクターに最初の説明を話させる
        bc_func.charaSpeak("部費を支払った部員を選択して、部費情報を入力した後、下の表に追加し", "命令1",
                "その後に、登録ボタンを押し", "命令1");

        // 画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/opr_frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // Deleteのアイコンのラベルのインスタンスを取得、設定
        isrt_icon = new ImageIcon("image/component/home/anikatsudb/budget/inserter/icon/icon.png");
        isrt_icon_label = new JLabel(isrt_icon);
        isrt_icon_label.setBounds(ICON_X, ICON_Y, ICON_WIDTH, ICON_HEIGHT);

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
        mbr_table_spanel.setPreferredSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));
        // パネルの配置を設定
        mbr_table_spanel.setBounds(MBR_AREA_X, MBR_AREA_Y, AREA_WIDTH, AREA_HEIGHT);

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

        // "日付"のラベルを作る
        date_label = new JLabel("支払日");
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

        // "支払金額"のラベルを作る
        money_label = new JLabel("支払金額");
        // フォントを設定
        money_label.setFont(LABEL_FONT);
        // 位置設定
        money_label.setBounds(MNYLB_X, MNYLB_Y, MNYLB_WIDTH, MNYLB_HEIGHT);
        // 支払金額を入力するテキストフィールドを作成する
        money_field = new JTextField();
        // サイズを設定する
        money_field.setPreferredSize(new Dimension(MNYFD_WIDTH, MNYFD_HEIGHT));
        // フォントを設定
        money_field.setFont(FIELD_FONT);
        // 位置設定
        money_field.setBounds(MNYFD_X, MNYFD_Y, MNYFD_WIDTH, MNYFD_HEIGHT);
        // 右寄せで表示するようにする
        money_field.setHorizontalAlignment(JTextField.RIGHT);
        // 毎月の部費の額(500円)をデフォルトでセットする
        money_field.setText(String.valueOf(500));

        // 登録予定表に追加ボタンの生成
        pre_add_button = new JButton();
        // サイズの設定
        pre_add_button.setPreferredSize(new Dimension(PREADDBT_WIDTH, PREADDBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon preaddb_icon = new ImageIcon("image/component/home/anikatsudb/member/inserter/button/pre_add_button.png");
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
                String data[] = new String[bgt_table_model.getColumnCount()];
                // 入力情報をもとにして登録予定表にデータを追加する
                if (getInputData(data)) { // 入力し忘れている情報がないとき
                    // 登録予定表に新しいデータを追加
                    bgt_table_model.addRow(data);
                    // 表のサイズを調整
                    resizeColumnsWidth(bgt_table);
                    // テキストフィールドの更新
                    removeBgtInfo();
                    // テキストフィールドをいじれない状態に戻す
                    setBgtInfoEditable(false);
                    // データベースに登録ボタンを押せるようにする
                    add_button.setEnabled(true);
                }
            }
        });

        // 登録予定表のモデルを作成する
        bgt_table_model = new DefaultTableModel() {
            // ボタンの行だけ反応するようにする
            @Override
            public boolean isCellEditable(int row, int column) {
                switch (column) {
                    case 4:
                        return true;
                    default:
                        return false;
                }
            }
        };
        // 各列の列名をセットする
        String bgt_column_names[] = {"部員番号", "ハンドルネーム", "支払日", "支払金額", "削除ボタン"};
        bgt_table_model.setColumnIdentifiers(bgt_column_names);
        
        // 上のモデルを使用した登録予定表を作成する
        bgt_table = new JTable(bgt_table_model);
        // 表のサイズをスクロールを使用する形にする
        bgt_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // 列名のフォントの設定
        bgt_table.getTableHeader().setFont(new Font("HGPゴシックM", Font.BOLD, 25));
        // 列名の色を設定
        bgt_table.getTableHeader().setForeground(new Color(255, 255, 255));
        bgt_table.getTableHeader().setBackground(new Color(255, 150, 180));
        // 表の文字のフォントを設定
        bgt_table.setFont(TABLE_FONT);
        // 行の高さを設定
        bgt_table.setRowHeight(ROW_HEIGHT);
        // 列を動かせないようする
        bgt_table.getTableHeader().setReorderingAllowed(false);
        // 削除ボタンの設定
        bgt_table.getColumn("削除ボタン").setCellRenderer(new ButtonRenderer());
        bgt_table.getColumn("削除ボタン").setCellEditor(new BgtButtonEditor(new JTextField("x")));
        // 自動でソートできるようにする
        bgt_table.setAutoCreateRowSorter(true);
        // 数字（文字列）のソートの設定だけ行う
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(bgt_table_model);
        sorter.setComparator(0, new Comparator<String>() { // 部員番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        sorter.setComparator(3, new Comparator<String>() { // 支払金額の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        bgt_table.setRowSorter(sorter);
        // 列のヘッダーのサイズに列の幅を合わせる
        setFirstColumnsWidth(bgt_table, bgt_column_names);

        // 追加予定の情報の表を表示するスクロールパネルのインスタンスを取得、設定
        bgt_table_spanel = new JScrollPane(bgt_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        bgt_table_spanel.setPreferredSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));
        // パネルの配置を設定
        bgt_table_spanel.setBounds(BGT_AREA_X, BGT_AREA_Y, AREA_WIDTH, AREA_HEIGHT);

        // それぞれの表の部員番号の列と日付、支払金額の列を右寄せ表示にする
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        TableColumnModel mbr_column_model = mbr_table.getColumnModel();
        TableColumnModel bgt_column_model = bgt_table.getColumnModel();
        mbr_column_model.getColumn(0).setCellRenderer(rightCellRenderer);
        bgt_column_model.getColumn(0).setCellRenderer(rightCellRenderer);
        bgt_column_model.getColumn(2).setCellRenderer(rightCellRenderer);
        bgt_column_model.getColumn(3).setCellRenderer(rightCellRenderer);

        // データベースに登録ボタンの生成
        add_button = new JButton();
        // サイズの設定
        add_button.setPreferredSize(new Dimension(ADDBT_WIDTH, ADDBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon addb_icon = new ImageIcon("image/component/home/anikatsudb/member/inserter/button/add_button.png");
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
        String confirmation = "本当に、登録予定表に追加した部費情報をデータベースに登録しますか？";
        isrt_confirmer = new Confirmer(new IsrtResponse(), confirmation, CONF_FONT, Confirmer.YES_NO);
        // 位置設定
        isrt_confirmer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        
        // 最初は入力情報の欄を編集できないようにする
        setBgtInfoEditable(false);
        add_button.setEnabled(false);

        // 戻るボタンの生成
        bk_button = new JButton();
        // サイズの設定
        bk_button.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsudb/member/updater/button/bk_button.png");
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
        add(mbr_num_label);
        add(mbr_num_field);
        add(h_name_label);
        add(h_name_field);
        add(date_label);
        add(date_spinner);
        add(money_label);
        add(money_field);
        add(pre_add_button);
        add(bgt_table_spanel);
        add(add_button);
        add(bk_button);
        add(isrt_icon_label);
        add(frame_label);
    }


    private class IsrtResponse implements ButtonResponse {
        @Override
        public void pushYes() {
            try {
                // データベースを更新
                updateDB();
                // ログの追加
                bc_func.addLog(bgt_table.getRowCount() + "件の部費情報を、データベースに登録しました。");
                // 登録を終えたデータを登録予定表から削除
                bgt_table_model.setRowCount(0);

                // 在部している部員一覧を再作成
                for (String key : mbr_info_map.keySet()) {
                    int row_num = getMbrRowNum(key);

                    if (row_num == -1) { // 部員一覧表に対称のデータがないとき
                        mbr_table_model.addRow(mbr_info_map.get(key));
                    }
                }

                // キャラクターに報告させる
                bc_func.charaSpeak("部費情報の登録に成功し", "報告");
            } catch (SQLException e) {
                // キャラクターに報告させる
                bc_func.charaSpeak("部費情報の登録に失敗し", "報告",
                        "すでに、同じ支払日で同じ部員のデータを登録し", "疑惑");
                //System.out.println("データベースへの部費情報登録に失敗しました。");
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


    private void updateDB() throws SQLException {
        /* 登録予定表に登録した部費情報をデータベースに登録する
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();

        for (int row = 0; row < bgt_table.getRowCount(); row++) {
            int mbr_num = Integer.parseInt(bgt_table.getValueAt(row, 0).toString());
            String date = bgt_table.getValueAt(row, 2).toString();
            int money = Integer.parseInt(bgt_table.getValueAt(row, 3).toString());

            stmt.execute("INSERT INTO budgets (paid_date, member_id, amount) values ('" +
                    date + "', " + mbr_num + ", " + money + ");");
        }

        // リソースを解放する
        stmt.close();
        // SQL操作をデータベースに反映する
        con.commit();
        // データベースを閉じる
        closeDB();
    }


    private void makeComponentsDisenabled() {
        /* 部品をいじれない状態にする
         */
        mbr_table.setEnabled(false);
        mbr_table_spanel.setEnabled(false);
        date_spinner.setEnabled(false);
        money_field.setEnabled(false);
        pre_add_button.setEnabled(false);
        bgt_table.setEnabled(false);
        bgt_table_spanel.setEnabled(false);
        add_button.setEnabled(false);
        bk_button.setEnabled(false);
    }


    private void makeComponentsEnabled() {
        /* 部品をいじれる状態にする
         */
        if (!mbr_num_field.getText().equals("")) { // 何か登録中の部費情報があったとき
            date_spinner.setEnabled(true);
            pre_add_button.setEnabled(true);
        }
        if (bgt_table.getRowCount() > 0) { // 確認画面でNoを押したとき
            add_button.setEnabled(true);
        }
        mbr_table.setEnabled(true);
        mbr_table_spanel.setEnabled(true);
        money_field.setEnabled(true);
        bgt_table.setEnabled(true);
        bgt_table_spanel.setEnabled(true);
        bk_button.setEnabled(true);
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
                    // 部員情報の欄を編集できるようにする
                    setBgtInfoEditable(true);

                    // 部員一覧表から指定された行を削除
                    int converted_row = mbr_table.convertRowIndexToModel(row);
                    mbr_table_model.removeRow(converted_row);

                    String mbr_num = mbr_num_field.getText();
                    if (!mbr_num.equals("")) { // 他の部員の部費情報を編集中だったら
                        // 編集中の部員の部員情報を表に戻す
                        mbr_table_model.addRow(mbr_info_map.get(mbr_num));
                    }

                    // テキストフィールドに部員情報を移す
                    setTextField(data);
                }
            });
            return button;
        }
    }


    private class BgtButtonEditor extends DefaultCellEditor {

        public BgtButtonEditor(JTextField txt_field) {
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

                    // 削除した部員の部員情報を部員一覧に戻す
                    String mbr_num = bgt_table.getValueAt(row, 0).toString();
                    mbr_table_model.addRow(mbr_info_map.get(mbr_num));

                    // 登録予定表から指定された行を削除
                    int converted_row = bgt_table.convertRowIndexToModel(row);
                    bgt_table_model.removeRow(converted_row);
                    
                    if (bgt_table.getRowCount() == 0) { // 登録予定表に何も登録されていなかったら
                        // データベースに登録するボタンを使えない状態にする
                        add_button.setEnabled(false);
                    }
                }
            });
            return button;
        }
    }


    private boolean getInputData(final String data[]) {
        /* 新しく登録する予定の入力データを引数で受け取った配列に格納する
        @param data[]: 登録する部費情報を格納する配列
         */
        String money = money_field.getText().trim();
        if (money.isEmpty()) { // 入力し忘れている情報があるとき
            // キャラクターに注意させる
            bc_func.charaSpeak("入力していない枠を入力し", "命令2");
            return false;
        }
        else if (!Pattern.compile("^-?[0-9]+$").matcher(money).find()) { // 数字出ないものが支払金額の欄に含まれるとき。正規表現を使って入学年度が数字であるかを確認している
            // キャラクターに注意させる
            bc_func.charaSpeak("支払金額には、数字を入力し", "命令2");
            //System.out.println("お金は数字");
            return false;
        }
        else if (money.length() > 5) { // 支払額が6桁以上の時
            // キャラクターに注意させる
            bc_func.charaSpeak("支払金額には、5桁以内の数字を入力し", "命令2");
            //System.out.println("お金5桁以内で");
            return false;
        }
        else { // 入力し忘れている情報がないとき
            data[0] = mbr_num_field.getText();
            data[1] = h_name_field.getText();
            data[2] = date_editor.getTextField().getText();
            data[3] = money;
            data[4] = "x";

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


    private void setBgtInfoEditable(final boolean f) {
        /* 部員番号以外の部費情報の欄の状態を変更する
        @param f: 部員番号以外の部費情報の欄の状態
         */
        date_spinner.setEnabled(f);
        money_field.setEditable(f);
        pre_add_button.setEnabled(f);
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
        ResultSet rs = stmt.executeQuery("SELECT id, name, h_name FROM members WHERE is_in_club = true" +
                " && id NOT IN (SELECT member_id FROM budgets WHERE paid_date = CURDATE());");


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


    private void setFirstColumnsWidth(final JTable bgt_table, final String[] col_names) {
        TableColumnModel column_model = bgt_table.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(bgt_table.getTableHeader().getFont());

        for (int i = 0; i < col_names.length; i++) {
            int width = fm.stringWidth(col_names[i]) + 20;
            column_model.getColumn(i).setPreferredWidth(width);
            column_model.getColumn(i).setWidth(width);
        }
    }


    private void removeBgtInfo() {
        /* 登録予定表に新しいデータを追加した際に、入力欄を空にする
         */
        // 部員番号のテキストフィールドを空にする
        mbr_num_field.setText("");
        // ハンドルネームのテキストフィールドを空にする
        h_name_field.setText("");
        // 支払金額の欄をデフォルトの500円に戻す
        money_field.setText(String.valueOf(500));

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
}
