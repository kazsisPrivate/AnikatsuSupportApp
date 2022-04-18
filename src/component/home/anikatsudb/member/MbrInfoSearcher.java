package component.home.anikatsudb.member;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.table.TableRowSorter;
import java.util.Comparator;

import function.BasicFunction;
import function.button.ButtonResponse;
import function.db.DBOperator;


public class MbrInfoSearcher extends DBOperator {
    /* 入力情報をもとにして部員の情報を検索するクラス
     */
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
    
    private final int RDBT_FONT_SIZE = 30; // 入力の枠の隣のラジオボタンの文字の大きさ
    private final Font RDBT_FONT = new Font("HGPゴシックM", Font.PLAIN, RDBT_FONT_SIZE); // 入力の枠の横のラベルのフォント

    private final int FD_FONT_SIZE = RDBT_FONT_SIZE - RDBT_FONT_SIZE/3; // 入力のフィールドの文字の大きさ
    private final Font FIELD_FONT = new Font("游明朝", Font.PLAIN, FD_FONT_SIZE); // 入力のフィールドのフォント

    private final int SLBT_FONT_SIZE = RDBT_FONT_SIZE - RDBT_FONT_SIZE/6; // (複数の選択肢から)選択ボタンの文字の大きさ
    private final Font SLBT_FONT = new Font("HGPゴシックM", Font.PLAIN, SLBT_FONT_SIZE); // 入力のフィールドのフォント

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
    
    private JRadioButton mbr_num_rdbutton; // 入力フィールドの左に置く"部員番号"というボタン
    private final int MBRNUMRDBT_WIDTH = RDBT_FONT_SIZE * 5; // "部員番号"というボタンの幅
    private final int MBRNUMRDBT_HEIGHT = RDBT_FONT_SIZE; // "部員番号"というボタンの高さ
    private final int MBRNUMRDBT_X = 100; // "部員番号"というボタンの左上端のx座標
    private final int MBRNUMRDBT_Y = 150; // "部員番号"というボタンの左上端のy座標
    private JTextField mbr_num_field; // 部員番号を入力するテキストフィールド
    private final int MBRNUMFD_WIDTH = RDBT_FONT_SIZE * 3; // 部員番号を入力するテキストフィールドの幅
    private final int MBRNUMFD_HEIGHT = RDBT_FONT_SIZE; // 部員番号を入力するテキストフィールドの高さ
    private final int MBRNUMFD_X = MBRNUMRDBT_X + MBRNUMRDBT_WIDTH + RDBT_FONT_SIZE/3; // 部員番号を入力するテキストフィールドの左上端のx座標
    private final int MBRNUMFD_Y = MBRNUMRDBT_Y + RDBT_FONT_SIZE/10; // 部員番号を入力するテキストフィールドの左上端のy座標

    private JRadioButton et_year_rdbutton; // 入力フィールドの左に置く"入学年度"というボタン
    private final int ETYRRDBT_WIDTH = RDBT_FONT_SIZE * 5; // "入学年度"というボタンの幅
    private final int ETYRRDBT_HEIGHT = RDBT_FONT_SIZE; // "入学年度"というボタンの高さ
    private final int ETYRRDBT_X = 380; // "入学年度"というボタンの左上端のx座標
    private final int ETYRRDBT_Y = 150; // "入学年度"というボタンの左上端のy座標
    private JTextField et_year_field; // 入学年度を入力するテキストフィールド
    private final int ETYRFD_WIDTH = RDBT_FONT_SIZE * 3; // 入学年度を入力するテキストフィールドの幅
    private final int ETYRFD_HEIGHT = RDBT_FONT_SIZE; // 入学年度を入力するテキストフィールドの高さ
    private final int ETYRFD_X = ETYRRDBT_X + ETYRRDBT_WIDTH + RDBT_FONT_SIZE/3; // 入学年度を表示するテキストフィールドの左上端のx座標
    private final int ETYRFD_Y = ETYRRDBT_Y + RDBT_FONT_SIZE/10; // 入学年度を入力するテキストフィールドの左上端のy座標

    private JRadioButton ft_name_rdbutton; // 入力フィールドの左に置く"氏名(姓)"というラベル
    private final int FTNMRDBT_WIDTH = RDBT_FONT_SIZE * 5 - 10; // "氏名(姓)"というラベルの幅
    private final int FTNMRDBT_HEIGHT = RDBT_FONT_SIZE; // "氏名(姓)"というラベルの高さ
    private final int FTNMRDBT_X = 100; // "氏名(姓)"というラベルの左上端のx座標
    private final int FTNMRDBT_Y = 220; // "氏名(姓)"というラベルの左上端のy座標
    private JTextField ft_name_field; // 名前を入力するテキストフィールド
    private final int FTNMFD_WIDTH = RDBT_FONT_SIZE * 5; // 名前を入力するテキストフィールドの幅
    private final int FTNMFD_HEIGHT = RDBT_FONT_SIZE; // 名前を入力するテキストフィールドの高さ
    private final int FTNMFD_X = FTNMRDBT_X + FTNMRDBT_WIDTH + RDBT_FONT_SIZE/3; // 名前を入力するテキストフィールドの左上端のx座標
    private final int FTNMFD_Y = FTNMRDBT_Y + RDBT_FONT_SIZE/10; // 名前を入力するテキストフィールドの左上端のy座標

    private JRadioButton lt_name_rdbutton; // 入力フィールドの左に置く"氏名(名)"というボタン
    private final int LTNMRDBT_WIDTH = RDBT_FONT_SIZE * 5 - 10; // "氏名(名)"というボタンの幅
    private final int LTNMRDBT_HEIGHT = RDBT_FONT_SIZE; // "氏名(名)"というボタンの高さ
    private final int LTNMRDBT_X = 430; // "氏名(名)"というボタンの左上端のx座標
    private final int LTNMRDBT_Y = 220; // "氏名(名)"というボタンの左上端のy座標
    private JTextField lt_name_field; // 名前を入力するテキストフィールド
    private final int LTNMFD_WIDTH = RDBT_FONT_SIZE * 5; // 名前を入力するテキストフィールドの幅
    private final int LTNMFD_HEIGHT = RDBT_FONT_SIZE; // 名前を入力するテキストフィールドの高さ
    private final int LTNMFD_X = LTNMRDBT_X + LTNMRDBT_WIDTH + RDBT_FONT_SIZE/3; // 名前を入力するテキストフィールドの左上端のx座標
    private final int LTNMFD_Y = LTNMRDBT_Y + RDBT_FONT_SIZE/10; // 名前を入力するテキストフィールドの左上端のy座標

    private JRadioButton h_name_rdbutton; // 入力フィールドの左に置く"ハンドルネーム"というボタン
    private final int HNMRDBT_WIDTH = RDBT_FONT_SIZE * 8; // "ハンドルネーム"というボタンの幅
    private final int HNMRDBT_HEIGHT = RDBT_FONT_SIZE; // "ハンドルネーム"というボタンの高さ
    private final int HNMRDBT_X = 100; // "ハンドルネーム"というボタンの左上端のx座標
    private final int HNMRDBT_Y = 290; // "ハンドルネーム"というボタンの左上端のy座標
    private JTextField h_name_field; // ハンドルネームを入力するテキストフィールド
    private final int HNMFD_WIDTH = RDBT_FONT_SIZE * 15; // ハンドルネームを入力するテキストフィールド幅
    private final int HNMFD_HEIGHT = RDBT_FONT_SIZE; // ハンドルネームを入力するテキストフィールドの高さ
    private final int HNMFD_X = HNMRDBT_X + HNMRDBT_WIDTH + RDBT_FONT_SIZE/3; // ハンドルネームを入力するテキストフィールドの左上端のx座標
    private final int HNMFD_Y = HNMRDBT_Y + RDBT_FONT_SIZE/10; // ハンドルネームを入力するテキストフィールドの左上端のy座標

    private JRadioButton isiniz_rdbutton; // 入力フィールドの左に置く"飯塚住"というボタン
    private final int ISINIZRDBT_WIDTH = RDBT_FONT_SIZE * 4; // "飯塚住"というボタンの幅
    private final int ISINIZRDBT_HEIGHT = RDBT_FONT_SIZE; // "飯塚住"というボタンの高さ
    private final int ISINIZRDBT_X = 100; // "飯塚住"というボタンの左上端のx座標
    private final int ISINIZRDBT_Y = 360; // "飯塚住"というボタンの左上端のy座標
    private JRadioButton yes_is_in_iizuka_button;
    private final int Y_ISINIZBT_WIDTH = SLBT_FONT_SIZE * 3; // 飯塚住みかどうかのはいボタンの幅
    private final int Y_ISINIZBT_HEIGHT = SLBT_FONT_SIZE; // 飯塚住みかどうかのはいボタンの高さ
    private final int Y_ISINIZBT_X = ISINIZRDBT_X + ISINIZRDBT_WIDTH + 20; // 飯塚住みかどうかのはいボタンの左上端のx座標
    private final int Y_ISINIZBT_Y = ISINIZRDBT_Y + RDBT_FONT_SIZE/10; // 飯塚住みかどうかのはいボタンの左上端のy座標
    private JRadioButton no_is_in_iizuka_button;
    private final int N_ISINIZBT_WIDTH = SLBT_FONT_SIZE * 4; // 飯塚住みかどうかのいいえボタンの幅
    private final int N_ISINIZBT_HEIGHT = SLBT_FONT_SIZE; // 飯塚住みかどうかのいいえボタンの高さ
    private final int N_ISINIZBT_X = Y_ISINIZBT_X + Y_ISINIZBT_WIDTH + 10; // 飯塚住みかどうかのいいえボタンの左上端のx座標
    private final int N_ISINIZBT_Y = Y_ISINIZBT_Y; // 飯塚住みかどうかのいいえボタンの左上端のy座標

    private JRadioButton isinclb_rdbutton; // 入力フィールドの左に置く"在部状況"というボタン
    private final int ISINCLBRDBT_WIDTH = RDBT_FONT_SIZE * 5; // "在部状況"というボタンの幅
    private final int ISINCLBRDBT_HEIGHT = RDBT_FONT_SIZE; // "在部状況"というボタンの高さ
    private final int ISINCLBRDBT_X = 470; // "在部状況"というボタンの左上端のx座標
    private final int ISINCLBRDBT_Y = 360; // "在部状況"というボタンの左上端のy座標
    private JRadioButton yes_is_in_club_button;
    private final int Y_ISINCLBBT_WIDTH = SLBT_FONT_SIZE * 4; // 在部しているかどうかのはいボタンの幅
    private final int Y_ISINCLBBT_HEIGHT = SLBT_FONT_SIZE; // 在部しているかどうかのはいボタンの高さ
    private final int Y_ISINCLBBT_X = ISINCLBRDBT_X + ISINCLBRDBT_WIDTH + 20; // 在部しているかどうかのはいボタンの左上端のx座標
    private final int Y_ISINCLBBT_Y = ISINCLBRDBT_Y + RDBT_FONT_SIZE/10; // 在部しているかどうかのはいボタンの左上端のy座標
    private JRadioButton no_is_in_club_button;
    private final int N_ISINCLBBT_WIDTH = SLBT_FONT_SIZE * 4; // 在部しているかどうかのいいえボタンの幅
    private final int N_ISINCLBBT_HEIGHT = SLBT_FONT_SIZE; // 在部しているかどうかのいいえボタンの高さ
    private final int N_ISINCLBBT_X = Y_ISINCLBBT_X + Y_ISINCLBBT_WIDTH + 10; // 在部しているかどうかのいいえボタンの左上端のx座標
    private final int N_ISINCLBBT_Y = Y_ISINCLBBT_Y; // 在部しているかどうかのいいえボタンの左上端のy座標

    private JButton sch_button; // 検索ボタン
    private final int SCHBT_WIDTH = 170; // 検索ボタンの幅
    private final int SCHBT_HEIGHT = 60; // 検索ボタンの高さ
    private final int SCHBT_X = FRAME_WIDTH/4 - SCHBT_WIDTH/2; // 検索ボタンの左上端のx座標
    private final int SCHBT_Y = 420; // 検索ボタンの左上端のy座標

    private DefaultTableModel table_model;
    private JTable jtable; // 検索結果の表
    private final int ROW_HEIGHT = 25; // 一行の高さ
    private final Font TABLE_FONT = new Font("游明朝", Font.PLAIN, ROW_HEIGHT-5); // 表のフォント
    private JScrollPane jspanel; // 上の表にスクロールバーを付けたパネル
    private final int AREA_WIDTH = 800; // JTextAreaの幅
    private final int AREA_HEIGHT = 370; // JTextAreaの高さ
    private final int AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int AREA_Y = 520; // スクロールパネルの左上端のy座標

    private JButton bk_buttton; // 戻るボタン
    private final int BKBT_WIDTH = 90; // 戻るボタンの幅
    private final int BKBT_HEIGHT = 50; // 戻るボタンの高さ
    private final int BKBT_X = 780; // 戻るボタンの左上端のx座標
    private final int BKBT_Y = 910; // 戻るボタンの左上端のy座標

    private ButtonResponse bt_resp; // コンストラクタで受け取ったMbrDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる


    public MbrInfoSearcher(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // キャラクターに最初の説明を話させる
        bc_func.charaSpeak("検索方式を選択して、検索したい部員の部員情報を入力し", "命令1");

        // 画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/opr_frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // Searchのアイコンのラベルのインスタンスを取得、設定
        sch_icon = new ImageIcon("image/component/home/anikatsudb/member/searcher/icon/icon.png");
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
        // サイズの設定
        //pl_button.setPreferredSize(new Dimension(PLBT_WIDTH, PLBT_HEIGHT));
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
        // サイズの設定
        //pl_button.setPreferredSize(new Dimension(PLBT_WIDTH, PLBT_HEIGHT));
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
        
        // "部員番号"のラベルを作る
        mbr_num_rdbutton = new JRadioButton("部員番号");
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
                if (mbr_num_rdbutton.isSelected()) { // 選択されているとき
                    // 入力フィールドに書き込みを行える状態にする
                    mbr_num_field.setEditable(true);
                }
                else { // 選択されていないとき
                    // 入力フィールドに書き込みを行えない状態にする
                    mbr_num_field.setEditable(false);
                }
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
        // 右寄せで表示するようにする
        mbr_num_field.setHorizontalAlignment(JTextField.RIGHT);

        // "入学年度"のラベルを作る
        et_year_rdbutton = new JRadioButton("入学年度");
        // フォントを設定
        et_year_rdbutton.setFont(RDBT_FONT);
        // 位置設定
        et_year_rdbutton.setBounds(ETYRRDBT_X, ETYRRDBT_Y, ETYRRDBT_WIDTH, ETYRRDBT_HEIGHT);
        // ボタンが選択されている状態にする
        et_year_rdbutton.setSelected(true);
        // ボタンの表示設定
        et_year_rdbutton.setContentAreaFilled(false);
        et_year_rdbutton.setBorderPainted(false);
        et_year_rdbutton.setFocusPainted(false);
        // ボタンを押した際の処理
        et_year_rdbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");
                if (et_year_rdbutton.isSelected()) { // 選択されているとき
                    // 入力フィールドに書き込みを行える状態にする
                    et_year_field.setEditable(true);
                }
                else { // 選択されていないとき
                    // 入力フィールドに書き込みを行えない状態にする
                    et_year_field.setEditable(false);
                }
            }
        });
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

        // "氏名(姓)"のラベルを作る
        ft_name_rdbutton = new JRadioButton("氏名(姓)");
        // フォントを設定
        ft_name_rdbutton.setFont(RDBT_FONT);
        // 位置設定
        ft_name_rdbutton.setBounds(FTNMRDBT_X, FTNMRDBT_Y, FTNMRDBT_WIDTH, FTNMRDBT_HEIGHT);
        // ボタンが選択されている状態にする
        ft_name_rdbutton.setSelected(true);
        // ボタンの表示設定
        ft_name_rdbutton.setContentAreaFilled(false);
        ft_name_rdbutton.setBorderPainted(false);
        ft_name_rdbutton.setFocusPainted(false);
        // ボタンを押した際の処理
        ft_name_rdbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");
                if (ft_name_rdbutton.isSelected()) { // 選択されているとき
                    // 入力フィールドに書き込みを行える状態にする
                    ft_name_field.setEditable(true);

                    if (!lt_name_rdbutton.isSelected()
                            && pftmth_button.isSelected()) { // 完全一致検索であり氏名(名)を検索対象としていないとき
                        // 氏名(名)も検索対象としない状態にする
                        lt_name_rdbutton.setSelected(true);
                        lt_name_field.setEditable(true);
                    }
                }
                else { // 選択されていないとき
                    // 入力フィールドに書き込みを行えない状態にする
                    ft_name_field.setEditable(false);

                    if (lt_name_rdbutton.isSelected()
                            && pftmth_button.isSelected()) { // 完全一致検索であり氏名(名)を検索対象としているとき
                        // 氏名(名)も検索対象としない状態にする
                        lt_name_rdbutton.setSelected(false);
                        lt_name_field.setEditable(false);
                    }
                }
            }
        });
        // 氏名(姓)を入力するテキストフィールドを作成する
        ft_name_field = new JTextField();
        // サイズを設定する
        ft_name_field.setPreferredSize(new Dimension(FTNMFD_WIDTH, FTNMFD_HEIGHT));
        // フォントを設定
        ft_name_field.setFont(FIELD_FONT);
        // 位置設定
        ft_name_field.setBounds(FTNMFD_X, FTNMFD_Y, FTNMFD_WIDTH, FTNMFD_HEIGHT);

        // "氏名(名)"のラベルを作る
        lt_name_rdbutton = new JRadioButton("氏名(名)");
        // フォントを設定
        lt_name_rdbutton.setFont(RDBT_FONT);
        // 位置設定
        lt_name_rdbutton.setBounds(LTNMRDBT_X, LTNMRDBT_Y, LTNMRDBT_WIDTH, LTNMRDBT_HEIGHT);
        // ボタンが選択されている状態にする
        lt_name_rdbutton.setSelected(true);
        // ボタンの表示設定
        lt_name_rdbutton.setContentAreaFilled(false);
        lt_name_rdbutton.setBorderPainted(false);
        lt_name_rdbutton.setFocusPainted(false);
        // ボタンを押した際の処理
        lt_name_rdbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");
                if (lt_name_rdbutton.isSelected()) { // 選択されているとき
                    // 入力フィールドに書き込みを行える状態にする
                    lt_name_field.setEditable(true);

                    if (!ft_name_rdbutton.isSelected()
                            && pftmth_button.isSelected()) { // 完全一致検索であり氏名(姓)を検索対象としていないとき
                        // 氏名(姓)も検索対象としない状態にする
                        ft_name_rdbutton.setSelected(true);
                        ft_name_field.setEditable(true);
                    }
                }
                else { // 選択されていないとき
                    // 入力フィールドに書き込みを行えない状態にする
                    lt_name_field.setEditable(false);

                    if (ft_name_rdbutton.isSelected()
                            && pftmth_button.isSelected()) { // 完全一致検索であり氏名(姓)を検索対象としているとき
                        // 氏名(姓)も検索対象としない状態にする
                        ft_name_rdbutton.setSelected(false);
                        ft_name_field.setEditable(false);
                    }
                }
            }
        });
        // 氏名(姓)を入力するテキストフィールドを作成する
        lt_name_field = new JTextField();
        // サイズを設定する
        lt_name_field.setPreferredSize(new Dimension(LTNMFD_WIDTH, LTNMFD_HEIGHT));
        // フォントを設定
        lt_name_field.setFont(FIELD_FONT);
        // 位置設定
        lt_name_field.setBounds(LTNMFD_X, LTNMFD_Y, LTNMFD_WIDTH, LTNMFD_HEIGHT);

        // "ハンドルネーム"のラベルを作る
        h_name_rdbutton = new JRadioButton("ハンドルネーム");
        // フォントを設定
        h_name_rdbutton.setFont(RDBT_FONT);
        // 位置設定
        h_name_rdbutton.setBounds(HNMRDBT_X, HNMRDBT_Y, HNMRDBT_WIDTH, HNMRDBT_HEIGHT);
        // ボタンが選択されている状態にする
        h_name_rdbutton.setSelected(true);
        // ボタンの表示設定
        h_name_rdbutton.setContentAreaFilled(false);
        h_name_rdbutton.setBorderPainted(false);
        h_name_rdbutton.setFocusPainted(false);
        // ボタンを押した際の処理
        h_name_rdbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");
                if (h_name_rdbutton.isSelected()) { // 選択されているとき
                    // 入力フィールドに書き込みを行える状態にする
                    h_name_field.setEditable(true);
                }
                else { // 選択されていないとき
                    // 入力フィールドに書き込みを行えない状態にする
                    h_name_field.setEditable(false);
                }
            }
        });
        // ハンドルネームを入力するテキストフィールドを作成する
        h_name_field = new JTextField();
        // サイズを設定する
        h_name_field.setPreferredSize(new Dimension(HNMFD_WIDTH, HNMFD_HEIGHT));
        // フォントを設定
        h_name_field.setFont(FIELD_FONT);
        // 位置設定
        h_name_field.setBounds(HNMFD_X, HNMFD_Y, HNMFD_WIDTH, HNMFD_HEIGHT);

        // "飯塚住"のラベルを作る
        isiniz_rdbutton = new JRadioButton("飯塚住");
        // フォントを設定
        isiniz_rdbutton.setFont(RDBT_FONT);
        // 位置設定
        isiniz_rdbutton.setBounds(ISINIZRDBT_X, ISINIZRDBT_Y, ISINIZRDBT_WIDTH, ISINIZRDBT_HEIGHT);
        // ボタンが選択されている状態にする
        isiniz_rdbutton.setSelected(true);
        // ボタンの表示設定
        isiniz_rdbutton.setContentAreaFilled(false);
        isiniz_rdbutton.setBorderPainted(false);
        isiniz_rdbutton.setFocusPainted(false);
        // ボタンを押した際の処理
        isiniz_rdbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");
                if (isiniz_rdbutton.isSelected()) { // 選択されているとき
                    // 選択肢のボタンを選択できる状態にする
                    yes_is_in_iizuka_button.setEnabled(true);
                    no_is_in_iizuka_button.setEnabled(true);
                }
                else { // 選択されていないとき
                    // 選択肢のボタンを選択できない状態にする
                    yes_is_in_iizuka_button.setEnabled(false);
                    no_is_in_iizuka_button.setEnabled(false);
                }
            }
        });

        // 飯塚に住んでいるかどうかのはいボタンの生成
        yes_is_in_iizuka_button = new JRadioButton("はい");
        // ボタンの表示設定
        yes_is_in_iizuka_button.setContentAreaFilled(false);
        yes_is_in_iizuka_button.setBorderPainted(false);
        yes_is_in_iizuka_button.setFocusPainted(false);
        // フォントの設定
        yes_is_in_iizuka_button.setFont(SLBT_FONT);
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
        no_is_in_iizuka_button.setFont(SLBT_FONT);
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

        // "在部状況"のラベルを作る
        isinclb_rdbutton = new JRadioButton("在部状況");
        // フォントを設定
        isinclb_rdbutton.setFont(RDBT_FONT);
        // 位置設定
        isinclb_rdbutton.setBounds(ISINCLBRDBT_X, ISINCLBRDBT_Y, ISINCLBRDBT_WIDTH, ISINCLBRDBT_HEIGHT);
        // ボタンが選択されている状態にする
        isinclb_rdbutton.setSelected(true);
        // ボタンの表示設定
        isinclb_rdbutton.setContentAreaFilled(false);
        isinclb_rdbutton.setBorderPainted(false);
        isinclb_rdbutton.setFocusPainted(false);
        // ボタンを押した際の処理
        isinclb_rdbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");
                if (isinclb_rdbutton.isSelected()) { // 選択されているとき
                    // 選択肢のボタンを選択できる状態にする
                    yes_is_in_club_button.setEnabled(true);
                    no_is_in_club_button.setEnabled(true);
                }
                else { // 選択されていないとき
                    // 選択肢のボタンを選択できない状態にする
                    yes_is_in_club_button.setEnabled(false);
                    no_is_in_club_button.setEnabled(false);
                }
            }
        });

        // 在部しているかどうかのはいボタンの生成
        yes_is_in_club_button = new JRadioButton("在部中");
        // ボタンの表示設定
        yes_is_in_club_button.setContentAreaFilled(false);
        yes_is_in_club_button.setBorderPainted(false);
        yes_is_in_club_button.setFocusPainted(false);
        // フォントの設定
        yes_is_in_club_button.setFont(SLBT_FONT);
        // ボタンの位置設定
        yes_is_in_club_button.setBounds(Y_ISINCLBBT_X, Y_ISINCLBBT_Y, Y_ISINCLBBT_WIDTH, Y_ISINCLBBT_HEIGHT);
        // ボタンをデフォルトで選択している状態にする
        yes_is_in_club_button.setSelected(true);
        // ボタンを押した際の処理
        yes_is_in_club_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("yes_button pressed");

            }
        });

        // 飯塚に住んでいるかどうかのいいえボタンの生成
        no_is_in_club_button = new JRadioButton("退部済");
        // ボタンの表示設定
        no_is_in_club_button.setContentAreaFilled(false);
        no_is_in_club_button.setBorderPainted(false);
        no_is_in_club_button.setFocusPainted(false);
        // フォントの設定
        no_is_in_club_button.setFont(SLBT_FONT);
        // ボタンの位置設定
        no_is_in_club_button.setBounds(N_ISINCLBBT_X, N_ISINCLBBT_Y, N_ISINCLBBT_WIDTH, N_ISINCLBBT_HEIGHT);
        // ボタンを押した際の処理
        no_is_in_club_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");

            }
        });

        // 飯塚に住んでいるかどうかのボタンをグループ化する
        ButtonGroup isinclbbt_grp = new ButtonGroup();
        isinclbbt_grp.add(yes_is_in_club_button);
        isinclbbt_grp.add(no_is_in_club_button);

        // 登録予定表に追加ボタンの生成
        sch_button = new JButton();
        // サイズの設定
        sch_button.setPreferredSize(new Dimension(SCHBT_WIDTH, SCHBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon preaddb_icon = new ImageIcon("image/component/home/anikatsudb/member/searcher/button/sch_button.png");
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
                // 検索結果の表に追加する予定のデータを格納する配列を生成する
                String data[] = new String[table_model.getColumnCount()];
                // 入力情報をもとにして検索をし、検索結果表にデータを追加する
                if (getInputData(data)) { // 入力した情報に不備がないとき
                    // 入力情報をもとにして検索する
                    List res_list; // 検索結果を格納するリスト
                    try {
                        res_list = searchMbrInfo(data);
                        // 検索結果を表に追加
                        updateResultTable(res_list);
                        // キャラクターに報告させる
                        bc_func.charaSpeak("部員情報の検索に成功し", "報告");
                    } catch (SQLException se) {
                        // キャラクターに報告させる
                        bc_func.charaSpeak("部員情報の検索に失敗し", "報告");
                        //System.out.println("検索に失敗しました。");
                        se.printStackTrace();
                    }
                }
            }
        });

        // 検索結果表のモデルを作成する
        table_model = new DefaultTableModel() {
            // 全ての行が反応しないようにする
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // 各列の列名をセットする
        String column_names[] = {"部員番号", "入学年度", "氏名", "ハンドルネーム", "飯塚住", "在部状況"};
        table_model.setColumnIdentifiers(column_names);

        // 上のモデルを使用した登録予定表を作成する
        jtable = new JTable(table_model);
        // 表のサイズをスクロールを使用する形にする
        jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // 列名のフォントの設定
        jtable.getTableHeader().setFont(new Font("HGPゴシックM", Font.BOLD, 25));
        // 列名の色を設定
        jtable.getTableHeader().setForeground(new Color(255, 255, 255));
        jtable.getTableHeader().setBackground(new Color(255, 150, 180));
        // 表の文字のフォントを設定
        jtable.setFont(TABLE_FONT);
        // 行の高さを設定
        jtable.setRowHeight(ROW_HEIGHT);
        // 列を動かせないようする
        jtable.getTableHeader().setReorderingAllowed(false);
        // 自動でソートできるようにする
        jtable.setAutoCreateRowSorter(true);
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
        jtable.setRowSorter(sorter);

        // 列のヘッダーのサイズに列の幅を合わせる
        setFirstColumnsWidth(column_names);
        // 部員番号の列と入学年度の列を右寄せ表示にする
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        TableColumnModel column_model = jtable.getColumnModel();
        for (int column = 0; column < 2; column++) {
            column_model.getColumn(column).setCellRenderer(rightCellRenderer);
        }

        // 検索結果の表を表示するスクロールパネルのインスタンスを取得、設定
        jspanel = new JScrollPane(jtable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        jspanel.setPreferredSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));
        // パネルの配置を設定
        jspanel.setBounds(AREA_X, AREA_Y, AREA_WIDTH, AREA_HEIGHT);

        // 戻るボタンの生成
        bk_buttton = new JButton();
        // サイズの設定
        bk_buttton.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsudb/member/searcher/button/bk_button.png");
        bk_buttton.setIcon(bkb_icon);
        bk_buttton.setContentAreaFilled(false);
        bk_buttton.setBorderPainted(false);
        bk_buttton.setFocusPainted(false);
        // ボタンの位置設定
        bk_buttton.setBounds(BKBT_X, BKBT_Y, BKBT_WIDTH, BKBT_HEIGHT);
        // ボタンを押した際の処理
        bk_buttton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("bk_buttton pressed");
                // 選択画面に戻る
                bt_resp.pushClose();
            }
        });

        add(schmtd_label);
        add(pftmth_button);
        add(abgsmth_button);
        add(mbr_num_rdbutton);
        add(mbr_num_field);
        add(et_year_rdbutton);
        add(et_year_field);
        add(ft_name_rdbutton);
        add(ft_name_field);
        add(lt_name_rdbutton);
        add(lt_name_field);
        add(h_name_rdbutton);
        add(h_name_field);
        add(isiniz_rdbutton);
        add(yes_is_in_iizuka_button);
        add(no_is_in_iizuka_button);
        add(isinclb_rdbutton);
        add(yes_is_in_club_button);
        add(no_is_in_club_button);
        add(sch_button);
        add(jspanel);
        add(bk_buttton);
        add(sch_icon_label);
        add(frame_label);
    }


    private boolean getInputData(final String data[]) {
        /* 検索条件として入力したデータを引数で受け取った配列に格納する
        完全一致検索で、検索条件とするのになにも入力していないフィールドがあったらfalseを返す
        @param data[]: 検索条件として入力したデータの配列
         */
        String et_year = et_year_field.getText().trim();
        String ft_name = ft_name_field.getText().trim();
        String lt_name = lt_name_field.getText().trim();
        String h_name = h_name_field.getText().trim();
        if (pftmth_button.isSelected()) { // 完全一致検索のとき
            if (et_year_rdbutton.isSelected() && et_year.isEmpty()) {
                // キャラクターに注意させる
                bc_func.charaSpeak("入学年度を入力し", "命令2");
                //System.out.println("no_et");
                return false;
            }
            else if (ft_name_rdbutton.isSelected() && ft_name.isEmpty()) {
                // キャラクターに注意させる
                bc_func.charaSpeak("氏名(姓)を入力し", "命令2");
                //System.out.println("no_ftnm");
                return false;
            }
            else if (lt_name_rdbutton.isSelected() && lt_name.isEmpty()) {
                // キャラクターに注意させる
                bc_func.charaSpeak("氏名(名)を入力し", "命令2");
                //System.out.println("no_ltnm");
                return false;
            }
            else if (h_name_rdbutton.isSelected() && h_name.isEmpty()) {
                // キャラクターに注意させる
                bc_func.charaSpeak("ハンドルネームを入力し", "命令2");
                //System.out.println("no_hnm");
                return false;
            }
        }

        if (et_year_rdbutton.isSelected()) { // 入学年度が検索条件の一つのとき
            if (!Pattern.compile("^-?[0-9]+$").matcher(et_year).find()) { // 数字出ないものが入学年度の欄に含まれるとき。正規表現を使って入学年度が数字であるかを確認している
                // キャラクターに注意させる
                bc_func.charaSpeak("入学年度には、数字を入力し", "命令2");
                //System.out.println("年度じゃねえ");
                return false;
            }
            else if (et_year.length() != 4) { // 年度の桁数が4桁ではないとき
                // キャラクターに注意させる
                bc_func.charaSpeak("入学年度には、4桁の数字を入力し", "命令2");
                //System.out.println("年度間違い");
                return false;
            }
        }

        if (ft_name_rdbutton.isSelected() && ft_name.length() > 5) { // 苗字が6文字以上のとき
            // キャラクターに注意させる
            bc_func.charaSpeak("氏名(姓)は、5文字以内で入力し", "命令2");
            //System.out.println("苗字は5文字以内");
            return false;
        }
        else if (lt_name_rdbutton.isSelected() && lt_name.length() > 6) { // 名前が7文字以上のとき
            // キャラクターに注意させる
            bc_func.charaSpeak("氏名(名)は、6文字以内で入力し", "命令2");
            //System.out.println("名前は6文字以内");
            return false;
        }
        else if (h_name_rdbutton.isSelected() && h_name.length() > 24) { // ハンドルネームが24文字以上のとき
            // キャラクターに注意させる
            bc_func.charaSpeak("ハンドルネームは、24文字以内で入力し", "命令2");
            //System.out.println("ハンドルネームは24文字以内");
            return false;
        }
        else { // 入力した情報に不備がないとき
            if (mbr_num_rdbutton.isSelected()) { // 部員番号を検索対象とするとき
                data[0] = mbr_num_field.getText();
            }
            else { // 部員番号を検索対象としないとき
                data[0] = "";
            }

            if (et_year_rdbutton.isSelected()) { // 入学年度を検索対象とするとき
                data[1] = et_year;
            }
            else { // 入学年度を検索対象としないとき
                data[1] = "";
            }

            if (ft_name_rdbutton.isSelected()) { // 氏名を検索対象とするとき
                data[2] = ft_name + " " + lt_name;
                data[2] = data[2].trim();
            }
            else { // 氏名を検索対象としないとき
                data[2] = "";
            }

            if (h_name_rdbutton.isSelected()) { // ハンドルネームを検索対象とするとき
                data[3] = h_name;
            }
            else { // ハンドルネームを検索対象としないとき
                data[3] = "";
            }

            if (isiniz_rdbutton.isSelected()) {
                if (yes_is_in_iizuka_button.isSelected()) { // はいボタンを選択しているとき
                    data[4] = "true";
                }
                else { // いいえボタンを選択しているとき
                    data[4] = "false";
                }
            }
            else {
                data[4] = "";
            }


            if (isinclb_rdbutton.isSelected()) { // 在部状況を検索対象とするとき
                if (yes_is_in_club_button.isSelected()) { // 在部中ボタンを選択しているとき
                    data[5] = "true";
                }
                else { // 退部済ボタンを選択しているとき
                    data[5] = "false";
                }
            }
            else {
                data[5] = "";
            }


            return true;
        }
    }


    private List<String[]> searchMbrInfo(final String data[]) throws SQLException {
        /* パラメータで受け取ったデータをもとにして部員情報を検索し、検索結果を格納したリストを返す
        @param data[]: テキストフィールドに入力した検索条件
        @return res_list: 検索結果を格納したリスト
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();
        // 条件に沿って、検索する
        ResultSet rs = executeSearchSQL(stmt, data);

        List<String[]> res_list = new ArrayList<>(); // 検索結果を格納するためのリスト
        // 検索結果を取得し、格納する
        while (rs.next()) {
            String h_name = rs.getString("h_name"); // ハンドルネームを格納する変数
            if (h_name == null) { // ハンドルネームが未登録だったとき
                h_name = "";
            }

            String isiniz; // 飯塚に住んでいるかどうかのデータを格納するための変数
            if (rs.getBoolean("is_in_iizuka")) { // 飯塚に住んでいるとき
                isiniz = "はい";
            }
            else { // 飯塚に住んでいないとき
                isiniz = "いいえ";
            }

            String isinclb; // 在部しているかどうかのデータを格納するための変数
            if (rs.getBoolean("is_in_club")) { // 在部しているとき
                isinclb = "はい";
            }
            else { // 在部していないとき
                isinclb = "いいえ";
            }

            // 検索結果として追加する情報の配列を生成
            String res_array[] = {
                    String.valueOf(rs.getInt("id")), // 部員番号
                    String.valueOf(rs.getInt("entry_year")), // 入学年度
                    rs.getString("name"), // 氏名
                    h_name, // ハンドルネーム
                    isiniz, // 飯塚に住んでいるかどうか
                    isinclb // 在部しているかどうか
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
        if (data[0].isEmpty() && data[1].isEmpty() && data[2].isEmpty() && data[3].isEmpty() && data[4].isEmpty() && data[5].isEmpty()) { // 検索条件がないとき
            rs = stmt.executeQuery("SELECT * FROM members");
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
        String data_stmt[] = new String[6]; // それぞれの検索条件におけるSQLの条件部分

        // 部員番号における検索条件
        if (data[0].isEmpty()) {
            data_stmt[0] = "";
        }
        else {
            data_stmt[0] = " id = " + data[0];
        }

        // 入学年度における検索条件
        if (data[1].isEmpty()) {
            data_stmt[1] = "";
        }
        else {
            data_stmt[1] = " entry_year = " + data[1];
        }

        // 氏名における検索条件
        if (data[2].isEmpty()) {
            data_stmt[2] = "";
        }
        else {
            data_stmt[2] = " name = '" + data[2] + "'";
        }

        // ハンドルネームにおける検索条件
        if (data[3].isEmpty()) {
            data_stmt[3] = "";
        }
        else {
            data_stmt[3] = " h_name = '" + data[3] + "'";
        }

        // 飯塚に住んでいるかどうかにおける検索条件
        if (data[4].isEmpty()) {
            data_stmt[4] = "";
        }
        else {
            data_stmt[4] = " is_in_iizuka = " + data[4];
        }

        // 在部しているかどうかにおける検索条件
        if (data[5].isEmpty()) {
            data_stmt[5] = "";
        }
        else {
            data_stmt[5] = " is_in_club = " + data[5];
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
        ResultSet rs = stmt.executeQuery("SELECT * FROM members WHERE"
                + String.join("", data_stmt) + ";");

        return rs;
    }


    private ResultSet executeAbgsSearch(final Statement stmt, final String data[]) throws SQLException {
        /* パラメータで受け取ったデータをもとにした検索のSQLを実行し、結果を返す(あいまい検索)
        @param stmt: SQLを実行するのに使うインスタンス
        @param data[]: 検索条件
        @return rs: 検索結果
         */
        String data_stmt[] = new String[6]; // それぞれの検索条件におけるSQLの条件部分

        // 部員番号における検索条件
        if (data[0].isEmpty()) {
            data_stmt[0] = "";
        }
        else {
            data_stmt[0] = " id = " + data[0];
        }

        // 入学年度における検索条件
        if (data[1].isEmpty()) {
            data_stmt[1] = "";
        }
        else {
            data_stmt[1] = " entry_year = " + data[1];
        }

        // 氏名における検索条件
        if (data[2].isEmpty()) {
            data_stmt[2] = "";
        }
        else {
            data_stmt[2] = " name LIKE '%" + data[2] + "%'";
        }

        // ハンドルネームにおける検索条件
        if (data[3].isEmpty()) {
            data_stmt[3] = "";
        }
        else {
            data_stmt[3] = " h_name LIKE '%" + data[3] + "%'";
        }

        // 飯塚に住んでいるかどうかにおける検索条件
        if (data[4].isEmpty()) {
            data_stmt[4] = "";
        }
        else {
            data_stmt[4] = " is_in_iizuka = " + data[4];
        }

        // 在部しているかどうかにおける検索条件
        if (data[5].isEmpty()) {
            data_stmt[5] = "";
        }
        else {
            data_stmt[5] = " is_in_club = " + data[5];
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
        ResultSet rs = stmt.executeQuery("SELECT * FROM members WHERE"
                + String.join("", data_stmt) + ";");

        return rs;
    }

    private void setFirstColumnsWidth(final String[] col_names) {
        TableColumnModel column_model = jtable.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(jtable.getTableHeader().getFont());

        for (int i = 0; i < col_names.length; i++) {
            int width = fm.stringWidth(col_names[i]) + 20;
            column_model.getColumn(i).setPreferredWidth(width);
            column_model.getColumn(i).setWidth(width);
        }
    }


    private void resizeColumnsWidth() {
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


    private void updateResultTable(final List<String[]> data_list) {
        /* パラメータで受け取ったデータをもとにして検索結果の表を更新する
        @param data_list: 検索結果を格納しているリスト
         */
        // そのとき検索結果の表にセットされている行をすべて削除する
        table_model.setRowCount(0);

        if (data_list.isEmpty()) { // 検索した結果、該当するものがなかったとき

        }
        else { // 検索した結果、該当するものが見つかったとき
            // 検索結果のデータを表に追加する
            for (String data[]: data_list) {
                // 検索結果のうちの1行を追加する
                table_model.addRow(data);
                // 表のサイズを調整
                resizeColumnsWidth();
            }
        }
    }
}
