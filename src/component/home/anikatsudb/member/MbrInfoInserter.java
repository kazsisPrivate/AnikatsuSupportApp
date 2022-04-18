package component.home.anikatsudb.member;

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
import java.util.Calendar;
import javax.swing.table.*;
import java.util.Comparator;
import java.util.regex.Pattern;

import function.BasicFunction;
import function.confirmation.Confirmer;
import function.button.ButtonResponse;
import function.db.DBOperator;


public class MbrInfoInserter extends DBOperator {

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

    private final int FD_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/3; // 入力のフィールドの文字の大きさ
    private final Font FIELD_FONT = new Font("游明朝", Font.PLAIN, FD_FONT_SIZE); // 入力のフィールドのフォント

    private final int BT_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/6; // ボタンの文字の大きさ
    private final Font BUTTON_FONT = new Font("HGPゴシックM", Font.PLAIN, BT_FONT_SIZE); // 入力のフィールドのフォント

    private JLabel mbr_num_label; // 入力フィールドの左に置く"部員番号"というラベル
    private final int MBRNUMLB_WIDTH = LB_FONT_SIZE * 4; // "部員番号"というラベルの幅
    private final int MBRNUMLB_HEIGHT = LB_FONT_SIZE; // "部員番号"というラベルの高さ
    private final int MBRNUMLB_X = 280; // "部員番号"というラベルの左上端のx座標
    private final int MBRNUMLB_Y = 80; // "部員番号"というラベルの左上端のy座標
    private JTextField mbr_num_field; // 部員番号を表示するテキストフィールド
    private final int MBRNUMFD_WIDTH = LB_FONT_SIZE * 3; // 部員番号を表示するテキストフィールドの幅
    private final int MBRNUMFD_HEIGHT = LB_FONT_SIZE; // 部員番号を表示するテキストフィールドの高さ
    private final int MBRNUMFD_X = MBRNUMLB_X + MBRNUMLB_WIDTH + LB_FONT_SIZE/3; // 部員番号を表示するテキストフィールドの左上端のx座標
    private final int MBRNUMFD_Y = MBRNUMLB_Y + LB_FONT_SIZE/10; // 部員番号を表示するテキストフィールドの左上端のy座標

    private JLabel et_year_label; // 入力フィールドの左に置く"入学年度"というラベル
    private final int ETYRLB_WIDTH = LB_FONT_SIZE * 4; // "入学年度"というラベルの幅
    private final int ETYRLB_HEIGHT = LB_FONT_SIZE; // "入学年度"というラベルの高さ
    private final int ETYRLB_X = 560; // "入学年度"というラベルの左上端のx座標
    private final int ETYRLB_Y = 80; // "入学年度"というラベルの左上端のy座標
    private JTextField et_year_field; // 入学年度を表示するテキストフィールド
    private final int ETYRFD_WIDTH = LB_FONT_SIZE * 3; // 入学年度を表示するテキストフィールドの幅
    private final int ETYRFD_HEIGHT = LB_FONT_SIZE; // 入学年度を表示するテキストフィールドの高さ
    private final int ETYRFD_X = ETYRLB_X + ETYRLB_WIDTH + LB_FONT_SIZE/3; // 入学年度を表示するテキストフィールドの左上端のx座標
    private final int ETYRFD_Y = ETYRLB_Y + LB_FONT_SIZE/10; // 入学年度を表示するテキストフィールドの左上端のy座標

    private JLabel ft_name_label; // 入力フィールドの左に置く"氏名(姓)"というラベル
    private final int FTNMLB_WIDTH = LB_FONT_SIZE * 4 - 10; // "氏名(姓)"というラベルの幅
    private final int FTNMLB_HEIGHT = LB_FONT_SIZE; // "氏名(姓)"というラベルの高さ
    private final int FTNMLB_X = 100; // "氏名(姓)"というラベルの左上端のx座標
    private final int FTNMLB_Y = 150; // "氏名(姓)"というラベルの左上端のy座標
    private JTextField ft_name_field; // 名前を入力するテキストフィールド
    private final int FTNMFD_WIDTH = LB_FONT_SIZE * 5; // 名前を入力するテキストフィールドの幅
    private final int FTNMFD_HEIGHT = LB_FONT_SIZE; // 名前を入力するテキストフィールドの高さ
    private final int FTNMFD_X = FTNMLB_X + FTNMLB_WIDTH + LB_FONT_SIZE/3; // 名前を入力するテキストフィールドの左上端のx座標
    private final int FTNMFD_Y = FTNMLB_Y + LB_FONT_SIZE/10; // 名前を入力するテキストフィールドの左上端のy座標

    private JLabel lt_name_label; // 入力フィールドの左に置く"氏名(名)"というラベル
    private final int LTNMLB_WIDTH = LB_FONT_SIZE * 4 - 10; // "氏名(名)"というラベルの幅
    private final int LTNMLB_HEIGHT = LB_FONT_SIZE; // "氏名(名)"というラベルの高さ
    private final int LTNMLB_X = 400; // "氏名(名)"というラベルの左上端のx座標
    private final int LTNMLB_Y = 150; // "氏名(名)"というラベルの左上端のy座標
    private JTextField lt_name_field; // 名前を入力するテキストフィールド
    private final int LTNMFD_WIDTH = LB_FONT_SIZE * 5; // 名前を入力するテキストフィールドの幅
    private final int LTNMFD_HEIGHT = LB_FONT_SIZE; // 名前を入力するテキストフィールドの高さ
    private final int LTNMFD_X = LTNMLB_X + LTNMLB_WIDTH + LB_FONT_SIZE/3; // 名前を入力するテキストフィールドの左上端のx座標
    private final int LTNMFD_Y = LTNMLB_Y + LB_FONT_SIZE/10; // 名前を入力するテキストフィールドの左上端のy座標

    private JLabel h_name_label; // 入力フィールドの左に置く"ハンドルネーム"というラベル
    private final int HNMLB_WIDTH = LB_FONT_SIZE * 7; // "ハンドルネーム"というラベルの幅
    private final int HNMLB_HEIGHT = LB_FONT_SIZE; // "ハンドルネーム"というラベルの高さ
    private final int HNMLB_X = 100; // "ハンドルネーム"というラベルの左上端のx座標
    private final int HNMLB_Y = 220; // "ハンドルネーム"というラベルの左上端のy座標
    private JTextField h_name_field; // ハンドルネームを入力するテキストフィールド
    private final int HNMFD_WIDTH = LB_FONT_SIZE * 15; // ハンドルネームを入力するテキストフィールドの幅
    private final int HNMFD_HEIGHT = LB_FONT_SIZE; // ハンドルネームを入力するテキストフィールドの高さ
    private final int HNMFD_X = HNMLB_X + HNMLB_WIDTH + LB_FONT_SIZE/3; // ハンドルネームを入力するテキストフィールドの左上端のx座標
    private final int HNMFD_Y = HNMLB_Y + LB_FONT_SIZE/10; // ハンドルネームを入力するテキストフィールドの左上端のy座標

    private JLabel isiniz_label; // 入力フィールドの左に置く"飯塚住"というラベル
    private final int ISINIZLB_WIDTH = LB_FONT_SIZE * 3; // "飯塚住"というラベルの幅
    private final int ISINIZLB_HEIGHT = LB_FONT_SIZE; // "飯塚住"というラベルの高さ
    private final int ISINIZLB_X = 100; // "飯塚住"というラベルの左上端のx座標
    private final int ISINIZLB_Y = 290; // "飯塚住"というラベルの左上端のy座標
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

    private JButton pre_add_button; // 登録予定表に追加ボタン
    private final int PREADDBT_WIDTH = 290; // 登録予定表に追加ボタンの幅
    private final int PREADDBT_HEIGHT = 60; // 登録予定表に追加ボタンの高さ
    private final int PREADDBT_X = FRAME_WIDTH/4 - PREADDBT_WIDTH/2; // 登録予定表に追加ボタンの左上端のx座標
    private final int PREADDBT_Y = 340; // 登録予定表に追加ボタンの左上端のy座標

    private DefaultTableModel table_model;
    private JTable mbr_table; // 追加予定の部員の表表示する表
    private final int ROW_HEIGHT = 25; // 一行の高さ
    private final Font TABLE_FONT = new Font("游明朝", Font.PLAIN, ROW_HEIGHT-5); // 確認の文のフォントt
    private JScrollPane jspanel; // 上の表にスクロールバーを付けたパネル
    private final int AREA_WIDTH = 800; // スクロールパネルの幅
    private final int AREA_HEIGHT = 410; // スクロールパネルの高さ
    private final int AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int AREA_Y = 440; // スクロールパネルの左上端のy座標

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

    private ButtonResponse bt_resp; // コンストラクタで受け取ったMbrDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる


    public MbrInfoInserter(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // キャラクターに最初の説明を話させる
        bc_func.charaSpeak("新しく入部した部員の部員情報を入力して、下の表に追加し", "命令1",
                "その後に、登録ボタンを押", "命令1");

        // menu画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/opr_frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // Insertのアイコンのラベルのインスタンスを取得、設定
        isrt_icon = new ImageIcon("image/component/home/anikatsudb/member/inserter/icon/icon.png");
        isrt_icon_label = new JLabel(isrt_icon);
        isrt_icon_label.setBounds(ICON_X, ICON_Y, ICON_WIDTH, ICON_HEIGHT);

        // "部員番号"のラベルを作る
        mbr_num_label = new JLabel("部員番号");
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
        // 登録する部員の部員番号を取得し、設定する
        try {
            int max_id = getMaxId();
            mbr_num_field.setText(String.valueOf(max_id+1));
        } catch (SQLException e) {
            System.out.println("部員番号をデータベースから取得できませんでした。");
        }

        // "入学年度"のラベルを作る
        et_year_label = new JLabel("入学年度");
        // フォントを設定
        et_year_label.setFont(LABEL_FONT);
        // 位置設定
        et_year_label.setBounds(ETYRLB_X, ETYRLB_Y, ETYRLB_WIDTH, ETYRLB_HEIGHT);
        // 入学年度を入力するテキストフィールドを作成する
        et_year_field = new JTextField();
        // サイズを設定する
        et_year_field.setPreferredSize(new Dimension(ETYRFD_WIDTH, ETYRFD_HEIGHT));
        // フォントを設定
        et_year_field.setFont(FIELD_FONT);
        // 位置設定
        et_year_field.setBounds(ETYRFD_X, ETYRFD_Y, ETYRFD_WIDTH, ETYRFD_HEIGHT);
        // 右寄せで表示するようにする
        et_year_field.setHorizontalAlignment(JTextField.RIGHT);
        // 現在の年度をデフォルトでセットする
        int academic_year = getThisAcademicYear();
        et_year_field.setText(String.valueOf(academic_year));

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

        // "氏名(名)"のラベルを作る
        lt_name_label = new JLabel("氏名(名)");
        // フォントを設定
        lt_name_label.setFont(LABEL_FONT);
        // 位置設定
        lt_name_label.setBounds(LTNMLB_X, LTNMLB_Y, LTNMLB_WIDTH, LTNMLB_HEIGHT);
        // 氏名(名)を入力するテキストフィールドを作成する
        lt_name_field = new JTextField();
        // サイズを設定する
        lt_name_field.setPreferredSize(new Dimension(LTNMFD_WIDTH, LTNMFD_HEIGHT));
        // フォントを設定
        lt_name_field.setFont(FIELD_FONT);
        // 位置設定
        lt_name_field.setBounds(LTNMFD_X, LTNMFD_Y, LTNMFD_WIDTH, LTNMFD_HEIGHT);

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
                String data[] = new String[table_model.getColumnCount()];
                // 入力情報をもとにして登録予定表にデータを追加する
                if (getInputData(data)) { // 入力し忘れている情報がないとき
                    // 登録予定表に新しいデータを追加
                    table_model.addRow(data);
                    // 表のサイズを調整
                    resizeColumnsWidth();
                    // テキストフィールドの更新
                    updateOnNextTextFields();
                }
            }
        });

        // 確認の画面のインスタンスを生成
        String confirmation = "本当に、登録予定表に追加した部員情報をデータベースに登録しますか？";
        isrt_confirmer = new Confirmer(new IsrtResponse(), confirmation, CONF_FONT, Confirmer.YES_NO);
        // 位置設定
        isrt_confirmer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // 登録予定表のモデルを作成する
        table_model = new DefaultTableModel() {
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
        String column_names[] = {"部員番号", "入学年度", "氏名", "ハンドルネーム", "飯塚住", "削除ボタン"};
        table_model.setColumnIdentifiers(column_names);

        // 上のモデルを使用した登録予定表を作成する
        mbr_table = new JTable(table_model);
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
        mbr_table.getColumn("削除ボタン").setCellRenderer(new ButtonRenderer());
        mbr_table.getColumn("削除ボタン").setCellEditor(new ButtonEditor(new JTextField("x")));
        // 自動でソートできるようにする
        mbr_table.setAutoCreateRowSorter(true);
        // 数字（文字列）のソートの設定だけ行う
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(table_model);
        sorter.setComparator(0, new Comparator<String>() { // 部員番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        sorter.setComparator(1, new Comparator<String>() { // 入学年度の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        mbr_table.setRowSorter(sorter);
        // 列のヘッダーのサイズに列の幅を合わせる
        setFirstColumnsWidth(column_names);
        // 部員番号の列と入学年度の列を右寄せ表示にする
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        TableColumnModel column_model = mbr_table.getColumnModel();
        for (int column = 0; column < 2; column++) {
            column_model.getColumn(column).setCellRenderer(rightCellRenderer);
        }

        // 追加予定の情報の表を表示するスクロールパネルのインスタンスを取得、設定
//        jtarea = new JTextArea();
        jspanel = new JScrollPane(mbr_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        jspanel.setPreferredSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));
        // パネルの配置を設定
        jspanel.setBounds(AREA_X, AREA_Y, AREA_WIDTH, AREA_HEIGHT);

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

        // 戻るボタンの生成
        bk_button = new JButton();
        // サイズの設定
        bk_button.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsudb/member/inserter/button/bk_button.png");
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

        add(mbr_num_label);
        add(mbr_num_field);
        add(et_year_label);
        add(et_year_field);
        add(ft_name_label);
        add(ft_name_field);
        add(lt_name_label);
        add(lt_name_field);
        add(h_name_label);
        add(h_name_field);
        add(isiniz_label);
        add(pre_add_button);
        add(yes_is_in_iizuka_button);
        add(no_is_in_iizuka_button);
        add(jspanel);
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

        for (int row = 0; row < mbr_table.getRowCount(); row++) {
            int mbr_num = Integer.parseInt(mbr_table.getValueAt(row, 0).toString());
            int et_year = Integer.parseInt(mbr_table.getValueAt(row, 1).toString());
            String name = mbr_table.getValueAt(row, 2).toString();
            String h_name = mbr_table.getValueAt(row, 3).toString();
            boolean is_in_iizuka;
            if (mbr_table.getValueAt(row, 4).toString().equals("はい")) { // "はい"が格納されているとき
                is_in_iizuka = true;
            }
            else { // "いいえ"が格納されているとき
                is_in_iizuka = false;
            }

            if (h_name.equals("")) { // ハンドルネームが何も入力されていないとき
                stmt.execute("INSERT INTO members (id, entry_year, name, is_in_iizuka) values (" +
                        mbr_num + ", " + et_year + ", '" + name + "', " + is_in_iizuka + ");");
            }
            else { // ハンドルネームが入力されているとき
                stmt.execute("INSERT INTO members (id, entry_year, name, h_name, is_in_iizuka) values (" +
                        mbr_num + ", " + et_year + ", '" + name + "', '" + h_name + "', " + is_in_iizuka + ");");
            }
        }

        // リソースを解放する
        stmt.close();
        // SQL操作をデータベースに反映する
        con.commit();
        // データベースを閉じる
        closeDB();
    }


    private int getMaxId() throws SQLException {
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();
        // 現在一番最大の部員番号を参照
        ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM members;");

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
                bc_func.addLog(mbr_table.getRowCount() + "件の部員情報を、データベースに登録しました。");
                // 登録を終えたデータを登録予定表から削除
                table_model.setRowCount(0);
                // キャラクターに報告させる
                bc_func.charaSpeak("データベースへの新入部員の情報登録に成功し", "報告");
            } catch (SQLException e) {
                // キャラクターに報告させる
                bc_func.charaSpeak("データベースへの新入部員の情報登録に失敗し", "報告");
                //System.out.println("データベースへの新入部員の情報登録に失敗しました。");
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


    private void makeComponentsDisenabled() {
        /* 部品をいじれない状態にする
         */
        mbr_table.setEnabled(false);
        jspanel.setEnabled(false);
        et_year_field.setEnabled(false);
        ft_name_field.setEnabled(false);
        lt_name_field.setEnabled(false);
        h_name_field.setEnabled(false);
        yes_is_in_iizuka_button.setEnabled(false);
        no_is_in_iizuka_button.setEnabled(false);
        pre_add_button.setEnabled(false);
        add_button.setEnabled(false);
        bk_button.setEnabled(false);
    }


    private void makeComponentsEnabled() {
        /* 部品をいじれる状態にする
         */
        mbr_table.setEnabled(true);
        jspanel.setEnabled(true);
        et_year_field.setEnabled(true);
        ft_name_field.setEnabled(true);
        lt_name_field.setEnabled(true);
        h_name_field.setEnabled(true);
        yes_is_in_iizuka_button.setEnabled(true);
        no_is_in_iizuka_button.setEnabled(true);
        pre_add_button.setEnabled(true);
        add_button.setEnabled(true);
        bk_button.setEnabled(true);
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


    private class ButtonRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JButton jbutton = new JButton();
            jbutton.setText((value == null) ? "" : value.toString());
            
            return jbutton;
        }
    }


    private class ButtonEditor extends DefaultCellEditor {

        public ButtonEditor(JTextField txt_field) {
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
                    int converted_row = mbr_table.convertRowIndexToModel(row);
                    table_model.removeRow(converted_row);
                    // 登録予定表を更新する
                    updateTable(converted_row);
                    // テキストフィールドを更新する
                    updateOnPrevTextFields();
                }
            });
            return button;
        }
    }


    private void setFirstColumnsWidth(final String[] col_names) {
        TableColumnModel column_model = mbr_table.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(mbr_table.getTableHeader().getFont());

        for (int i = 0; i < col_names.length; i++) {
            int width = fm.stringWidth(col_names[i]) + 20;
            column_model.getColumn(i).setPreferredWidth(width);
            column_model.getColumn(i).setWidth(width);
        }
    }


    private boolean getInputData(final String data[]) {
        /* 新しく登録する予定の入力データを引数で受け取った配列に格納する
        ハンドルネームは入力しなくてもいいが、それ以外の情報で入力していない情報がないようにする必要がある
        入力していなかったらfalseを返す
        @param data[]: 登録予定表に追加する予定のデータを格納する配列
         */

        String et_year = et_year_field.getText().trim();
        String ft_name = ft_name_field.getText().replaceAll(" ", "").replaceAll("　", "");
        String lt_name = lt_name_field.getText().replaceAll(" ", "").replaceAll("　", "");
        String h_name = h_name_field.getText().trim();
        if (et_year.isEmpty() || ft_name.isEmpty() || lt_name.isEmpty()) { // 入力し忘れている情報があるとき
            // キャラクターに注意させる
            bc_func.charaSpeak("入力していない枠を入力し", "命令2");
            return false;
        }
        else if (!Pattern.compile("^-?[0-9]+$").matcher(et_year).find()) { // 数字出ないものが入学年度の欄に含まれるとき。正規表現を使って入学年度が数字であるかを確認している
            // キャラクターに注意させる
            bc_func.charaSpeak("入学年度には、数字を入力し", "命令2");
            return false;
        }
        else if (et_year.length() != 4) { // 年度の桁数が4桁ではないとき
            // キャラクターに注意させる
            bc_func.charaSpeak("入学年度には、4桁の数字を入力し", "命令2");
            //System.out.println("年度間違い");
            return false;
        }
        else if (ft_name.length() > 5) { // 苗字が6文字以上のとき
            // キャラクターに注意させる
            bc_func.charaSpeak("氏名(姓)は、5文字以内で入力し", "命令2");
            //System.out.println("苗字は5文字以内");
            return false;
        }
        else if (lt_name.length() > 6) { // 名前が7文字以上のとき
            // キャラクターに注意させる
            bc_func.charaSpeak("氏名(名)は、6文字以内で入力し", "命令2");
            //System.out.println("名前は6文字以内");
            return false;
        }
        else if (h_name.length() > 24) { // ハンドルネームが24文字以上のとき
            // キャラクターに注意させる
            bc_func.charaSpeak("ハンドルネームは、24文字内で入力し", "命令2");
            //System.out.println("ハンドルネームは24文字以内");
            return false;
        }
        else { // 入力し忘れている情報がないとき
            data[0] = mbr_num_field.getText();
            data[1] = et_year;
            data[2] = ft_name + " " + lt_name;
            data[3] = h_name;
            if (yes_is_in_iizuka_button.isSelected()) { // はいボタンを選択しているとき
                data[4] = "はい";
            }
            else { // いいえボタンを選択しているとき
                data[4] = "いいえ";
            }
            data[5] = "x";

            return true;
        }
    }


    private void updateOnNextTextFields() {
        /* 登録予定表に新しいデータを追加した際に、部員番号を更新し、
        氏名、ハンドルネームのテキストフィールドを空にし、
        飯塚住のはいボタンを選択している状態にする
         */
        // 部員番号を次の番号にする
        int next_mbr_num = Integer.parseInt(mbr_num_field.getText()) + 1; // 次の部員番号
        mbr_num_field.setText(String.valueOf(next_mbr_num));

        // 氏名、ハンドルネームのテキストフィールドを空にする
        ft_name_field.setText("");
        lt_name_field.setText("");
        h_name_field.setText("");

        // 飯塚住のはいボタンを選択している状態にする
        yes_is_in_iizuka_button.setSelected(true);
    }


    private void updateOnPrevTextFields() {
        /* 登録予定表に登録していた情報を削除する際に、部員番号をを一つ戻す
         */
        // 部員番号を一つ戻す
        int prev_mbr_num = Integer.parseInt(mbr_num_field.getText()) - 1; // 前の部員番号
        mbr_num_field.setText(String.valueOf(prev_mbr_num));
    }

    private void updateTable(final int dlt_row) {
        /* 登録予定表の部員を削除された際に、登録予定表の部員の部員番号の調整を行う
        @param dlt_row: 登録予定表から削除された行番号
         */
        for (int row = dlt_row; row < mbr_table.getRowCount(); row++) {
            // 対象とする行の部員番号を取得
            int mbr_num = Integer.parseInt(mbr_table.getValueAt(row, 0).toString());
            // 削除した部員の分、部員番号を1減らす
            mbr_table.setValueAt(String.valueOf(mbr_num-1), row, 0);
        }
    }


    private void resizeColumnsWidth() {
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
            }
        }
    }
}
