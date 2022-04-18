package component.home.anikatsudb.member;

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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import function.BasicFunction;
import function.button.ButtonResponse;
import function.confirmation.Confirmer;
import function.db.DBOperator;


public class MbrInfoUpdater extends DBOperator {

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

    private final int AREA_WIDTH = 800; // スクロールパネルの幅
    private final int AREA_HEIGHT = 380; // スクロールパネルの高さ

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

    private JLabel mbr_info_label; // "変更後の情報"というラベル
    private final int MBRIFLB_WIDTH = LB_FONT_SIZE * 9; // "変更後の情報"というラベルの幅
    private final int MBRIFLB_HEIGHT = LB_FONT_SIZE; // "変更後の情報"というラベルの高さ
    private final int MBRIFLB_X = FRAME_WIDTH/4 - MBRIFLB_WIDTH/2; // "変更後の情報"というラベルの左上端のx座標
    private final int MBRIFLB_Y = 540; // "変更後の情報"というラベルの左上端のy座標

    private final int FD_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/3; // 入力のフィールドの文字の大きさ
    private final Font FIELD_FONT = new Font("游明朝", Font.PLAIN, FD_FONT_SIZE); // 入力のフィールドのフォント

    private final int BT_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/6; // ボタンの文字の大きさ
    private final Font BUTTON_FONT = new Font("HGPゴシックM", Font.PLAIN, BT_FONT_SIZE); // 入力のフィールドのフォント

    private JLabel mbr_num_label; // 入力フィールドの左に置く"部員番号"というラベル
    private final int MBRNUMLB_WIDTH = LB_FONT_SIZE * 4; // "部員番号"というラベルの幅
    private final int MBRNUMLB_HEIGHT = LB_FONT_SIZE; // "部員番号"というラベルの高さ
    private final int MBRNUMLB_X = 100; // "部員番号"というラベルの左上端のx座標
    private final int MBRNUMLB_Y = 590; // "部員番号"というラベルの左上端のy座標
    private JTextField mbr_num_field; // 部員番号を表示するテキストフィールド
    private final int MBRNUMFD_WIDTH = LB_FONT_SIZE * 3; // 部員番号を表示するテキストフィールドの幅
    private final int MBRNUMFD_HEIGHT = LB_FONT_SIZE; // 部員番号を表示するテキストフィールドの高さ
    private final int MBRNUMFD_X = MBRNUMLB_X + MBRNUMLB_WIDTH + LB_FONT_SIZE/3; // 部員番号を表示するテキストフィールドの左上端のx座標
    private final int MBRNUMFD_Y = MBRNUMLB_Y + LB_FONT_SIZE/10; // 部員番号を表示するテキストフィールドの左上端のy座標

    private JLabel ft_name_label; // 入力フィールドの左に置く"氏名(姓)"というラベル
    private final int FTNMLB_WIDTH = LB_FONT_SIZE * 4 - 10; // "氏名(姓)"というラベルの幅
    private final int FTNMLB_HEIGHT = LB_FONT_SIZE; // "氏名(姓)"というラベルの高さ
    private final int FTNMLB_X = 100; // "氏名(姓)"というラベルの左上端のx座標
    private final int FTNMLB_Y = 660; // "氏名(姓)"というラベルの左上端のy座標
    private JTextField ft_name_field; // 名前を入力するテキストフィールド
    private final int FTNMFD_WIDTH = LB_FONT_SIZE * 5; // 名前を入力するテキストフィールドの幅
    private final int FTNMFD_HEIGHT = LB_FONT_SIZE; // 名前を入力するテキストフィールドの高さ
    private final int FTNMFD_X = FTNMLB_X + FTNMLB_WIDTH + LB_FONT_SIZE/3; // 名前を入力するテキストフィールドの左上端のx座標
    private final int FTNMFD_Y = FTNMLB_Y + LB_FONT_SIZE/10; // 名前を入力するテキストフィールドの左上端のy座標

    private JLabel lt_name_label; // 入力フィールドの左に置く"氏名(名)"というラベル
    private final int LTNMLB_WIDTH = LB_FONT_SIZE * 4 - 10; // "氏名(名)"というラベルの幅
    private final int LTNMLB_HEIGHT = LB_FONT_SIZE; // "氏名(名)"というラベルの高さ
    private final int LTNMLB_X = 400; // "氏名(名)"というラベルの左上端のx座標
    private final int LTNMLB_Y = 660; // "氏名(名)"というラベルの左上端のy座標
    private JTextField lt_name_field; // 名前を入力するテキストフィールド
    private final int LTNMFD_WIDTH = LB_FONT_SIZE * 5; // 名前を入力するテキストフィールドの幅
    private final int LTNMFD_HEIGHT = LB_FONT_SIZE; // 名前を入力するテキストフィールドの高さ
    private final int LTNMFD_X = LTNMLB_X + LTNMLB_WIDTH + LB_FONT_SIZE/3; // 名前を入力するテキストフィールドの左上端のx座標
    private final int LTNMFD_Y = LTNMLB_Y + LB_FONT_SIZE/10; // 名前を入力するテキストフィールドの左上端のy座標

    private JLabel h_name_label; // 入力フィールドの左に置く"ハンドルネーム"というラベル
    private final int HNMLB_WIDTH = LB_FONT_SIZE * 7; // "ハンドルネーム"というラベルの幅
    private final int HNMLB_HEIGHT = LB_FONT_SIZE; // "ハンドルネーム"というラベルの高さ
    private final int HNMLB_X = 100; // "ハンドルネーム"というラベルの左上端のx座標
    private final int HNMLB_Y = 730; // "ハンドルネーム"というラベルの左上端のy座標
    private JTextField h_name_field; // ハンドルネームを入力するテキストフィールド
    private final int HNMFD_WIDTH = LB_FONT_SIZE * 15; // ハンドルネームを入力するテキストフィールドの幅
    private final int HNMFD_HEIGHT = LB_FONT_SIZE; // ハンドルネームを入力するテキストフィールドの高さ
    private final int HNMFD_X = HNMLB_X + HNMLB_WIDTH + LB_FONT_SIZE/3; // ハンドルネームを入力するテキストフィールドの左上端のx座標
    private final int HNMFD_Y = HNMLB_Y + LB_FONT_SIZE/10; // ハンドルネームを入力するテキストフィールドの左上端のy座標

    private JLabel isiniz_label; // 入力フィールドの左に置く"飯塚住"というラベル
    private final int ISINIZLB_WIDTH = LB_FONT_SIZE * 3; // "飯塚住"というラベルの幅
    private final int ISINIZLB_HEIGHT = LB_FONT_SIZE; // "飯塚住"というラベルの高さ
    private final int ISINIZLB_X = 100; // "飯塚住"というラベルの左上端のx座標
    private final int ISINIZLB_Y = 800; // "飯塚住"というラベルの左上端のy座標
    private JRadioButton yes_is_in_iizuka_button;
    private final int Y_ISINIZBT_WIDTH = BT_FONT_SIZE * 3; // 飯塚住みかどうかのはいボタンの幅
    private final int Y_ISINIZBT_HEIGHT = BT_FONT_SIZE; // 飯塚住みかどうかのはいボタンの高さ
    private final int Y_ISINIZBT_X = ISINIZLB_X + ISINIZLB_WIDTH + 30; // 飯塚住みかどうかのはいボタンの左上端のx座標
    private final int Y_ISINIZBT_Y = ISINIZLB_Y + LB_FONT_SIZE/10; // 飯塚住みかどうかのはいボタンの左上端のy座標
    private JRadioButton no_is_in_iizuka_button;
    private final int N_ISINIZBT_WIDTH = BT_FONT_SIZE * 4; // 飯塚住みかどうかのいいえボタンの幅
    private final int N_ISINIZBT_HEIGHT = BT_FONT_SIZE; // 飯塚住みかどうかのいいえボタンの高さ
    private final int N_ISINIZBT_X = Y_ISINIZBT_X + Y_ISINIZBT_WIDTH + 20; // 飯塚住みかどうかのいいえボタンの左上端のx座標
    private final int N_ISINIZBT_Y = Y_ISINIZBT_Y; // 飯塚住みかどうかのいいえボタンの左上端のy座標

    private Confirmer upd_confirmer; // データベースの情報を更新するかの最終確認の画面
    private final Font CONF_FONT = new Font("MS Pゴシック", Font.BOLD, 50); // 確認の文のフォント

    private JButton upd_button; // データベースの部員情報を更新ボタン
    private final int UPDBT_WIDTH = 470; // データベースの部員情報を更新ボタンの幅
    private final int UPDBT_HEIGHT = 60; // データベースの部員情報を更新ボタンの高さ
    private final int UPDBT_X = FRAME_WIDTH/4 - UPDBT_WIDTH/2; // データベースの部員情報を更新ボタンの左上端のx座標
    private final int UPDBT_Y = 870; // データベースの部員情報を更新ボタンの左上端のy座標

    private JButton bk_button; // 戻るボタン
    private final int BKBT_WIDTH = 90; // 戻るボタンの幅
    private final int BKBT_HEIGHT = 50; // 戻るボタンの高さ
    private final int BKBT_X = 780; // 戻るボタンの左上端のx座標
    private final int BKBT_Y = 910; // 戻るボタンの左上端のy座標

    private ButtonResponse bt_resp; // コンストラクタで受け取ったMbrDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる

    
    public MbrInfoUpdater(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // キャラクターに最初の説明を話させる
        bc_func.charaSpeak("部員情報を変更する部員を上の表から選択して、下の枠に変更後の情報を入力し", "命令1");


        // 画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/opr_frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // Deleteのアイコンのラベルのインスタンスを取得、設定
        upd_icon = new ImageIcon("image/component/home/anikatsudb/member/updater/icon/icon.png");
        upd_icon_label = new JLabel(upd_icon);
        upd_icon_label.setBounds(ICON_X, ICON_Y, ICON_WIDTH, ICON_HEIGHT);

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
                    case 4:
                        return true;
                    default:
                        return false;
                }
            }
        };
        // 各列の列名をセットする
        String mbr_column_names[] = {"部員番号", "氏名", "ハンドルネーム", "飯塚住", "編集ボタン"};
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
            List<String[]> data_list= getMbrberInfo();
            // 在部している部員一覧表を作成
            setMbrTable(data_list);
        } catch (SQLException se) {
            // キャラクターに報告させる
            bc_func.charaSpeak("在部している部員の情報を取得するのに失敗し", "報告");
            se.printStackTrace();
        }

        // 部員番号の列を右寄せ表示にする
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        TableColumnModel mbr_column_model = mbr_table.getColumnModel();
        mbr_column_model.getColumn(0).setCellRenderer(rightCellRenderer);

        // "変更後の情報"のラベルを作る
        mbr_info_label = new JLabel("《 変更後の情報 》");
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

        // "氏名(姓)"のラベルを作る
        ft_name_label = new JLabel("氏名(姓)");
        // フォントを設定
        ft_name_label.setFont(LABEL_FONT);
        // 位置設定
        ft_name_label.setBounds(FTNMLB_X, FTNMLB_Y, FTNMLB_WIDTH, FTNMLB_HEIGHT);
        // 氏名(姓)を入力するテキストフィールドを作成する
        ft_name_field = new JTextField();
        // サイズを設定する
        ft_name_field.setPreferredSize(new Dimension(FTNMFD_WIDTH, FTNMFD_HEIGHT));
        // フォントを設定
        ft_name_field.setFont(FIELD_FONT);
        // 位置設定
        ft_name_field.setBounds(FTNMFD_X, FTNMFD_Y, FTNMFD_WIDTH, FTNMFD_HEIGHT);
        // 編集ができないようにする
        ft_name_field.setEditable(false);

        // "氏名(名)"のラベルを作る
        lt_name_label = new JLabel("氏名(名)");
        // フォントを設定
        lt_name_label.setFont(LABEL_FONT);
        // 位置設定
        lt_name_label.setBounds(LTNMLB_X, LTNMLB_Y, LTNMLB_WIDTH, LTNMLB_HEIGHT);
        // 氏名(姓)を入力するテキストフィールドを作成する
        lt_name_field = new JTextField();
        // サイズを設定する
        lt_name_field.setPreferredSize(new Dimension(LTNMFD_WIDTH, LTNMFD_HEIGHT));
        // フォントを設定
        lt_name_field.setFont(FIELD_FONT);
        // 位置設定
        lt_name_field.setBounds(LTNMFD_X, LTNMFD_Y, LTNMFD_WIDTH, LTNMFD_HEIGHT);
        // 編集ができないようにする
        lt_name_field.setEditable(false);

        // "ハンドルネーム"のラベルを作る
        h_name_label = new JLabel("ハンドルネーム");
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

        // "飯塚住"のラベルを作る
        isiniz_label = new JLabel("飯塚住");
        // フォントを設定
        isiniz_label.setFont(LABEL_FONT);
        // 位置設定
        isiniz_label.setBounds(ISINIZLB_X, ISINIZLB_Y, ISINIZLB_WIDTH, ISINIZLB_HEIGHT);

        // 飯塚に住んでいるかどうかのはいボタンの生成
        yes_is_in_iizuka_button = new JRadioButton("はい");
        // サイズの設定
        //pl_button.setPreferredSize(new Dimension(PLBT_WIDTH, PLBT_HEIGHT));
        // ボタンの表示設定
        yes_is_in_iizuka_button.setContentAreaFilled(false);
        yes_is_in_iizuka_button.setBorderPainted(false);
        yes_is_in_iizuka_button.setFocusPainted(false);
        // フォントの設定
        yes_is_in_iizuka_button.setFont(BUTTON_FONT);
        // ボタンの位置設定
        yes_is_in_iizuka_button.setBounds(Y_ISINIZBT_X, Y_ISINIZBT_Y, Y_ISINIZBT_WIDTH, Y_ISINIZBT_HEIGHT);
        // ボタンをデフォルトで選択している状態にする
        yes_is_in_iizuka_button.setSelected(true);
        // ボタンを押した際の処理
        yes_is_in_iizuka_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("yes_button pressed");

            }
        });

        // 飯塚に住んでいるかどうかのいいえボタンの生成
        no_is_in_iizuka_button = new JRadioButton("いいえ");
        // ボタンの表示設定
        no_is_in_iizuka_button.setContentAreaFilled(false);
        no_is_in_iizuka_button.setBorderPainted(false);
        no_is_in_iizuka_button.setFocusPainted(false);
        // フォントの設定
        no_is_in_iizuka_button.setFont(BUTTON_FONT);
        // ボタンの位置設定
        no_is_in_iizuka_button.setBounds(N_ISINIZBT_X, N_ISINIZBT_Y, N_ISINIZBT_WIDTH, N_ISINIZBT_HEIGHT);
        // ボタンを押した際の処理
        no_is_in_iizuka_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");

            }
        });

        // 飯塚に住んでいるかどうかのボタンをグループ化する
        ButtonGroup isinizbt_grp = new ButtonGroup();
        isinizbt_grp.add(yes_is_in_iizuka_button);
        isinizbt_grp.add(no_is_in_iizuka_button);

        // 確認の画面のインスタンスを生成
        String confirmation = "本当に、指定した部員の部員情報を更新しますか？";
        upd_confirmer = new Confirmer(new UpdResponse(), confirmation, CONF_FONT, Confirmer.YES_NO);
        // 位置設定
        upd_confirmer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // 登録予定表に追加ボタンの生成
        upd_button = new JButton();
        // サイズの設定
        upd_button.setPreferredSize(new Dimension(UPDBT_WIDTH, UPDBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon updb_icon = new ImageIcon("image/component/home/anikatsudb/member/updater/button/upd_button.png");
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
                String data[] = new String[3]; // 更新後の部員情報を格納するための配列
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
        setMbrInfoEditable(false);

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
        add(mbr_info_label);
        add(mbr_num_label);
        add(mbr_num_field);
        add(ft_name_label);
        add(ft_name_field);
        add(lt_name_label);
        add(lt_name_field);
        add(h_name_label);
        add(h_name_field);
        add(isiniz_label);
        add(yes_is_in_iizuka_button);
        add(no_is_in_iizuka_button);
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

        // 飯塚住かどうかをセットする
        String isiniz;
        if (data[2].equals("はい")) {
            isiniz = "true";
        }
        else {
            isiniz = "false";
        }

        // データベースの部員情報を変更する
        stmt.execute("UPDATE members SET h_name = '" + data[1] +
                "', is_in_iizuka = " + isiniz + " WHERE id = " + data[0] + ";");

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
        mbr_table_spanel.setEnabled(false);
        mbr_table.setEnabled(false);
        ft_name_field.setEnabled(false);
        lt_name_field.setEnabled(false);
        h_name_field.setEnabled(false);
        yes_is_in_iizuka_button.setEnabled(false);
        no_is_in_iizuka_button.setEnabled(false);
        upd_button.setEnabled(false);
        bk_button.setEnabled(false);
    }


    private void makeComponentsEnabled() {
        /* 部品をいじれる状態にする
         */
        mbr_table_spanel.setEnabled(true);
        mbr_table.setEnabled(true);
        ft_name_field.setEnabled(true);
        lt_name_field.setEnabled(true);
        h_name_field.setEnabled(true);
        bk_button.setEnabled(true);
        if (!mbr_num_field.getText().isEmpty()) {
            yes_is_in_iizuka_button.setEnabled(true);
            no_is_in_iizuka_button.setEnabled(true);
            upd_button.setEnabled(true);
        }
    }


    private class UpdResponse implements ButtonResponse {
        @Override
        public void pushYes() {
            try {
                String data[] = new String[4]; // 更新後の部員情報を格納するための配列
                // 更新後の部員情報を取得
                getInputData(data);
                // データベースの部員情報を更新する
                updateDB(data);
                // ログの追加
                bc_func.addLog("部員番号: " + data[0] + " の部員情報を更新しました。");
                // 指定した部員の表における行番号を取得
                int row = getMbrRowNum(data[0]);
                // 表を更新する
                updateMbrTable(data, row);
                resizeColumnsWidth(row);
                // キャラクターに報告させる
                bc_func.charaSpeak("指定した部員の部員情報の更新に成功し", "報告");
                // テキストフィールドを空にする
                removeMbrInfo();
                // 更新できないようにする
                setMbrInfoEditable(false);
            } catch (SQLException e) {
                // キャラクターに報告させる
                bc_func.charaSpeak("指定した部員の部員情報の更新に失敗し", "報告");
                //System.out.println("指定した部員の部員情報の更新に失敗しました。");
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


    private boolean getInputData(final String data[]) {
        /* 新しく登録する予定の入力データを引数で受け取った配列に格納する
        ハンドルネームは入力しなくてもいいが、それ以外の情報で入力していない情報がないようにする必要がある
        入力していなかったらfalseを返す
        @param data[]: 更新後の部員のデータを格納する配列
         */
        String h_name = h_name_field.getText().trim();
        if (h_name.length() > 24) { // ハンドルネームが24文字以上のとき
            // キャラクターに注意させる
            bc_func.charaSpeak("ハンドルネームは、24文字以内で入力し", "命令2");
            //System.out.println("ハンドルネームは24文字以内");
            return false;
        }
        else { // 入力し忘れている情報がないとき
            data[0] = mbr_num_field.getText();
            data[1] = h_name;
            if (yes_is_in_iizuka_button.isSelected()) { // 肺ボタンを選択しているとき
                data[2] = "はい";
            }
            else { // いいえボタンを選択しているとき
                data[2] = "いいえ";
            }

            return true;
        }
    }


    private List<String[]> getMbrberInfo() throws SQLException {
        /* 現在、部に所属しているメンバーの情報を取り出し、情報(削除ボタン付き)を格納したリストを返す
        @return res_list: 在部している部員情報を格納した
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();
        // 現在、在部している部員の部員情報を取得
        ResultSet rs = stmt.executeQuery("SELECT * FROM members WHERE is_in_club = true");


        List<String[]> res_list = new ArrayList<>(); // 取得した部員情報を格納するためのリスト
        // 取得した部員情報をリストに格納する
        while (rs.next()) {
            String h_name = rs.getString("h_name"); // ハンドルネームを格納する変数
            if (h_name == null) { // ハンドルネームが未登録だったとき
                h_name = "";
            }

            String isiniizuka; // 在部しているかどうかのデータを格納するための変数
            if (rs.getBoolean("is_in_iizuka")) { // 在部しているとき
                isiniizuka = "はい";
            }
            else { // 在部していないとき
                isiniizuka = "いいえ";
            }

            // 検索結果として追加する情報の配列を生成
            String res_array[] = {
                    String.valueOf(rs.getInt("id")), // 部員番号
                    rs.getString("name"), // 氏名
                    h_name, // ハンドルネーム
                    isiniizuka, // 飯塚住
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
                    // 部員情報の欄を編集できるようにする
                    setMbrInfoEditable(true);
                    // ボタンを押した後にボタンが"false"という表示に代わるの防ぐ
                    mbr_table.setValueAt("〇", row, 4);
                }
            });
            return button;
        }
    }


    private void resizeColumnsWidth() {
        /* 各列の幅を各列の要素の最大の幅に合わせる
         */
        TableColumnModel column_model = mbr_table.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(mbr_table.getFont());

        int row = mbr_table.getRowCount() - 1; // 追加された行の行番号

        for (int column = 0; column < mbr_table.getColumnCount()-1; column++) { // -1しているのは5行目のボタンのところはリサイズの必要がないから
            int col_width = column_model.getColumn(column).getWidth(); // 現在の列の幅
            int value_width = fm.stringWidth(mbr_table.getValueAt(row, column).toString()) + 20; // 新しく追加された文字列の横幅(+10は余白の分)

            if (col_width < value_width) { // 新しい文字列の幅の方が列の幅より大きいとき
                // 新しい文字列の幅に列の幅を合わせる
                column_model.getColumn(column).setPreferredWidth(value_width);
                column_model.getColumn(column).setWidth(value_width);
            }
        }
    }


    private void resizeColumnsWidth(final int row) {
        /* 各列の幅を各列の要素の最大の幅に合わせる
        @param row: 変更した行
         */
        TableColumnModel column_model = mbr_table.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(mbr_table.getFont());

        for (int column = 0; column < mbr_table.getColumnCount()-1; column++) { // -1しているのは5行目のボタンのところはリサイズの必要がないから
            int col_width = column_model.getColumn(column).getWidth(); // 現在の列の幅
            int value_width = fm.stringWidth(mbr_table.getValueAt(row, column).toString()) + 20; // 新しく追加された文字列の横幅(+10は余白の分)

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
                resizeColumnsWidth();
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
        // 氏名を姓と名にわける
        String name[] = data[1].split(" ");
        ft_name_field.setText(name[0]); // 氏名(姓)
        lt_name_field.setText(name[1]); // 氏名(名)
        h_name_field.setText(data[2]); // ハンドルネーム
        if (data[3].equals("はい")) { //飯塚住
            yes_is_in_iizuka_button.setSelected(true);
        }
        else {
            no_is_in_iizuka_button.setSelected(true);
        }
    }


    private void setMbrInfoEditable(final boolean f) {
        /* 部員番号以外の部員情報の欄の状態を変更する
        @param f: 部員番号以外の部員情報の欄の状態
         */
        h_name_field.setEditable(f);
        yes_is_in_iizuka_button.setEnabled(f);
        no_is_in_iizuka_button.setEnabled(f);
        upd_button.setEnabled(f);
    }


    private void updateMbrTable(final String data[], final int row) {
        /* 変更した部員の表のデータを、変更後のデータに更新する
        @param data[]: 変更後の部員情報
        @param row: 更新する部員の表における行番号
         */
        mbr_table.setValueAt(data[1], row, 1); // 氏名
        mbr_table.setValueAt(data[2], row, 2); // ハンドルネーム
        mbr_table.setValueAt(data[3], row, 3); // 飯塚住
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


    private void removeMbrInfo() {
        mbr_num_field.setText("");
        ft_name_field.setText("");
        lt_name_field.setText("");
        h_name_field.setText("");
    }
}
