package component.home.anikatsudb.budget;

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

import function.Loading;
import function.db.DBOperator;
import function.BasicFunction;
import function.button.ButtonResponse;
import function.confirmation.Confirmer;


public class BgtInfoDeleter extends DBOperator {

    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅

    private ImageIcon frame; // フレームの画像
    private JLabel frame_label; // フレームの画像のラベル
    private final int FM_WIDTH = 860; // フレームの画像の幅
    private final int FM_HEIGHT = 980; // フレームの画像の高さ
    private final int FM_X = 50; // フレームの左上端のx座標
    private final int FM_Y = 30; // フレームの左上端のy座標

    private JLabel dlt_icon_label; // Updateのアイコンの画像のラベル
    private ImageIcon dlt_icon; // Updateのアイコンの画像
    private final int ICON_WIDTH = 270; // アイコン画像の幅
    private final int ICON_HEIGHT = 130; // アイコン画像の高さ
    private final int ICON_X = 5;//650; // アイコンの左上端のx座標
    private final int ICON_Y = 5; // アイコンの左上端のy座標

    private final int LB_FONT_SIZE = 30; // 表の上のラベルの文字の大きさ
    private final Font LABEL_FONT = new Font("HGPゴシックM", Font.PLAIN, LB_FONT_SIZE); // 表の上のラベルのフォント

    private final int ROW_HEIGHT = 25; // 表の一行の高さ
    private final Font TABLE_FONT = new Font("游明朝", Font.PLAIN, ROW_HEIGHT-5); // 表のフォント

    private HashMap<String, String[]> bgt_info_map; // 各部員の部員番号をキーとし、氏名とハンドルネームを格納しているマップ

    private final int FD_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/3; // 入力のフィールドの文字の大きさ
    private final Font FIELD_FONT = new Font("游明朝", Font.PLAIN, FD_FONT_SIZE); // 入力のフィールドのフォント

    private JTextField year_field; // 年を表示するテキストフィールド
    private final int YEARFD_WIDTH = LB_FONT_SIZE * 3; // 年を表示するテキストフィールドの幅
    private final int YEARFD_HEIGHT = LB_FONT_SIZE; // 年を表示するテキストフィールドの高さ
    private final int YEARFD_X = 280; // 年を表示するテキストフィールドの左上端のx座標
    private final int YEARFD_Y = 90; // 年を表示するテキストフィールドの左上端のy座標
    private JLabel year_label; // 入力フィールドの右に置く"年"というラベル
    private final int YEARLB_WIDTH = LB_FONT_SIZE * 3; // "年"というラベルの幅
    private final int YEARLB_HEIGHT = LB_FONT_SIZE; // "年"というラベルの高さ
    private final int YEARLB_X = YEARFD_X + YEARFD_WIDTH + LB_FONT_SIZE/3; // "年"というラベルの左上端のx座標
    private final int YEARLB_Y = YEARFD_Y - LB_FONT_SIZE/10; // "年"というラベルの左上端のy座標

    private JTextField month_field; // 月を表示するテキストフィールド
    private final int MONTHFD_WIDTH = LB_FONT_SIZE * 2; // 月を表示するテキストフィールドの幅
    private final int MONTHFD_HEIGHT = LB_FONT_SIZE; // 月を表示するテキストフィールドの高さ
    private final int MONTHFD_X = 420; // 月を表示するテキストフィールドの左上端のx座標
    private final int MONTHFD_Y = 90; // 月を表示するテキストフィールドの左上端のy座標
    private JLabel month_label; // 入力フィールドの右に置く"月の部費情報ラベル
    private final int MONTHLB_WIDTH = LB_FONT_SIZE * 6; // "月の部費情報"というラベルの幅
    private final int MONTHLB_HEIGHT = LB_FONT_SIZE; // "月の部費情報"というラベルの高さ
    private final int MONTHLB_X = MONTHFD_X + MONTHFD_WIDTH + LB_FONT_SIZE/3; // "月の部費情報"というラベルの左上端のx座標
    private final int MONTHLB_Y = MONTHFD_Y - LB_FONT_SIZE/10; // "月の部費情報"というラベルの左上端のy座標

    private JButton sch_button; // 検索ボタン
    private final int SCHBT_WIDTH = 90; // 検索ボタンの幅
    private final int SCHBT_HEIGHT = 50; // 検索ボタンの高さ
    private final int SCHBT_X = 700; // 検索ボタンの左上端のx座標
    private final int SCHBT_Y = 80; // 検索ボタンの左上端のy座標

    private DefaultTableModel bgt_table_model;
    private JTable bgt_table; // 部費情報一覧表
    private JScrollPane bgt_table_spanel; // 上の表ににスクロールバーを付けたパネル
    private final int BGT_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int BGT_AREA_Y = 140; // スクロールパネルの左上端のy座標
    private final int BGT_AREA_WIDTH = 800; // スクロールパネルの幅
    private final int BGT_AREA_HEIGHT = 300; // スクロールパネルの高さ

    private JLabel dlt_table_label; // 表の上に置く"削除する部費情報"というラベル
    private final int DLTTBLB_WIDTH = LB_FONT_SIZE * 11; // "削除する部費情報"というラベルの幅
    private final int DLTTBLB_HEIGHT = LB_FONT_SIZE; // "削除する部費情報"というラベルの高さ
    private final int DLTTBLB_X = FRAME_WIDTH/4 - DLTTBLB_WIDTH/2; // "削除する部費情報"というラベルの左上端のx座標
    private final int DLTTBLB_Y = 510; // "削除する部費情報"というラベルの左上端のy座標

    private DefaultTableModel dlt_table_model;
    private JTable dlt_table; // 削除予定の部員一覧表
    private JScrollPane dlt_table_spanel; // 上の表ににスクロールバーを付けたパネル
    private final int DLT_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int DLT_AREA_Y = 550; // スクロールパネルの左上端のy座標
    private final int DLT_AREA_WIDTH = 800; // スクロールパネルの幅
    private final int DLT_AREA_HEIGHT = 300; // スクロールパネルの高さ

    private JButton dlt_button; // データベースから削除ボタン
    private final int DLTTBT_WIDTH = 350; // データベースから削除ボタンの幅
    private final int DLTTBT_HEIGHT = 60; // データベースから削除ボタンの高さ
    private final int DLTTBT_X = FRAME_WIDTH/4 - DLTTBT_WIDTH/2; // データベースから削除ボタンの左上端のx座標
    private final int DLTTBT_Y = 870; // データベースから削除ボタンの左上端のy座標

    private Confirmer dlt_confirmer; // データベースの情報を変更するかの最終確認の画面
    private final Font CONF_FONT = new Font("MS Pゴシック", Font.BOLD, 50); // 確認の文のフォント

    private JButton bk_button; // 戻るボタン
    private final int BKBT_WIDTH = 90; // 戻るボタンの幅
    private final int BKBT_HEIGHT = 50; // 戻るボタンの高さ
    private final int BKBT_X = 780; // 戻るボタンの左上端のx座標
    private final int BKBT_Y = 910; // 戻るボタンの左上端のy座標

    private ButtonResponse bt_resp; // コンストラクタで受け取ったBgtDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる


    public BgtInfoDeleter(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // キャラクターに最初の説明を話させる
        bc_func.charaSpeak("削除したい部費情報を上の表から選択して、下の削除ボタンを押し", "命令1");

        // 画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/opr_frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // Deleteのアイコンのラベルのインスタンスを取得、設定
        dlt_icon = new ImageIcon("image/component/home/anikatsudb/budget/deleter/icon/icon.png");
        dlt_icon_label = new JLabel(dlt_icon);
        dlt_icon_label.setBounds(ICON_X, ICON_Y, ICON_WIDTH, ICON_HEIGHT);

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
        month_label = new JLabel("月の部費情報");
        // フォントを設定
        month_label.setFont(LABEL_FONT);
        // 位置設定
        month_label.setBounds(MONTHLB_X, MONTHLB_Y, MONTHLB_WIDTH, MONTHLB_HEIGHT);
        // 入学年度を入力するテキストフィールドを作成する
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
        ImageIcon schb_icon = new ImageIcon("image/component/home/anikatsudb/budget/deleter/button/sch_button.png");
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
                        List<String[]> res_list = searchBgtInfo(data);
                        // 検索結果を表に追加
                        setBgtTable(res_list);
                        // 削除予定部費情報一覧と同じ情報は削除する
                        removeSameInfo();
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

        // 検索結果表のモデルを作成する
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
        String bgt_column_names[] = {"支払日", "部員番号", "ハンドルネーム", "支払額", "削除ボタン"};
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
        TableRowSorter<DefaultTableModel> bgt_sorter = new TableRowSorter<DefaultTableModel>(bgt_table_model);
        bgt_sorter.setComparator(1, new Comparator<String>() { // 部員番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        bgt_sorter.setComparator(3, new Comparator<String>() { // 支払額の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        bgt_table.setRowSorter(bgt_sorter);
        // 列のヘッダーのサイズに列の幅を合わせる
        setFirstColumnsWidth(bgt_table, bgt_column_names);
        // 部員番号の列、支払額の列を右寄せ表示にする
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        TableColumnModel bgt_column_model = bgt_table.getColumnModel();
        bgt_column_model.getColumn(1).setCellRenderer(rightCellRenderer);
        bgt_column_model.getColumn(3).setCellRenderer(rightCellRenderer);
        // 支払日の列を中央寄せにする
        DefaultTableCellRenderer centerCellRenderer = new DefaultTableCellRenderer();
        centerCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        bgt_column_model.getColumn(0).setCellRenderer(centerCellRenderer);

        // 検索結果の表を表示するスクロールパネルのインスタンスを取得、設定
        bgt_table_spanel = new JScrollPane(bgt_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        bgt_table_spanel.setPreferredSize(new Dimension(BGT_AREA_WIDTH, BGT_AREA_HEIGHT));
        // パネルの配置を設定
        bgt_table_spanel.setBounds(BGT_AREA_X, BGT_AREA_Y, BGT_AREA_WIDTH, BGT_AREA_HEIGHT);

        // "退部する部員"のラベルを作る
        dlt_table_label = new JLabel("《 削除する部費情報 》");
        // フォントを設定
        dlt_table_label.setFont(LABEL_FONT);
        // 位置設定
        dlt_table_label.setBounds(DLTTBLB_X, DLTTBLB_Y, DLTTBLB_WIDTH, DLTTBLB_HEIGHT);

        // 登録予定表のモデルを作成する
        dlt_table_model = new DefaultTableModel() {
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
        String dlt_column_names[] = {"支払日", "部員番号", "ハンドルネーム", "支払額", "取消ボタン"};
        dlt_table_model.setColumnIdentifiers(dlt_column_names);

        // 上のモデルを使用した登録予定表を作成する
        dlt_table = new JTable(dlt_table_model);
        // 表のサイズをスクロールを使用する形にする
        dlt_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // 列名のフォントの設定
        dlt_table.getTableHeader().setFont(new Font("HGPゴシックM", Font.BOLD, 25));
        // 列名の色を設定
        dlt_table.getTableHeader().setForeground(new Color(255, 255, 255));
        dlt_table.getTableHeader().setBackground(new Color(255, 150, 180));
        // 表の文字のフォントを設定
        dlt_table.setFont(TABLE_FONT);
        // 行の高さを設定
        dlt_table.setRowHeight(ROW_HEIGHT);
        // 列を動かせないようする
        dlt_table.getTableHeader().setReorderingAllowed(false);
        // 削除ボタンの設定
        dlt_table.getColumn("取消ボタン").setCellRenderer(new ButtonRenderer());
        dlt_table.getColumn("取消ボタン").setCellEditor(new DltButtonEditor(new JTextField("x")));
        // 自動でソートできるようにする
        dlt_table.setAutoCreateRowSorter(true);
        // 数字（文字列）のソートの設定だけ行う
        TableRowSorter<DefaultTableModel> dlt_sorter = new TableRowSorter<DefaultTableModel>(dlt_table_model);
        dlt_sorter.setComparator(0, new Comparator<String>() { // 部員番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        dlt_sorter.setComparator(1, new Comparator<String>() { // 入学年度の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        dlt_table.setRowSorter(dlt_sorter);
        // 列のヘッダーのサイズに列の幅を合わせる
        setFirstColumnsWidth(dlt_table, dlt_column_names);

        dlt_table_spanel = new JScrollPane(dlt_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        dlt_table_spanel.setPreferredSize(new Dimension(DLT_AREA_WIDTH, DLT_AREA_HEIGHT));
        // パネルの配置を設定
        dlt_table_spanel.setBounds(DLT_AREA_X, DLT_AREA_Y, DLT_AREA_WIDTH, DLT_AREA_HEIGHT);

        // 部員番号の列、支払額の列を右寄せ表示にする
        TableColumnModel dlt_column_model = dlt_table.getColumnModel();
        dlt_column_model.getColumn(1).setCellRenderer(rightCellRenderer);
        dlt_column_model.getColumn(3).setCellRenderer(rightCellRenderer);
        // 支払日の列を中央寄せにする
        dlt_column_model.getColumn(0).setCellRenderer(centerCellRenderer);

        // 確認の画面のインスタンスを生成
        String confirmation = "本当に、下の表に追加した部費情報を、データベースから削除しますか？";
        dlt_confirmer = new Confirmer(new DltResponse(), confirmation, CONF_FONT, Confirmer.YES_NO);
        // 位置設定
        dlt_confirmer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // 退部済に変更ボタンの生成
        dlt_button = new JButton();
        // サイズの設定
        dlt_button.setPreferredSize(new Dimension(DLTTBT_WIDTH, DLTTBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon preaddb_icon = new ImageIcon("image/component/home/anikatsudb/budget/deleter/button/dlt_button.png");
        dlt_button.setIcon(preaddb_icon);
        dlt_button.setContentAreaFilled(false);
        dlt_button.setBorderPainted(false);
        dlt_button.setFocusPainted(false);
        // ボタンの位置設定
        dlt_button.setBounds(DLTTBT_X, DLTTBT_Y, DLTTBT_WIDTH, DLTTBT_HEIGHT);
        // ボタンを押した際の処理
        dlt_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("dlt_button pressed");
                // 部品をいじれない状態にする
                makeComponentsDisenabled();
                // 確認の画面を表示する
                add(dlt_confirmer, 0);
                repaint();
            }
        });

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

        add(year_label);
        add(year_field);
        add(month_label);
        add(month_field);
        add(sch_button);
        add(bgt_table_spanel);
        add(dlt_table_label);
        add(dlt_table_spanel);
        add(dlt_button);
        add(bk_button);
        add(dlt_icon_label);
        add(frame_label);
    }


    private void updateDB() throws SQLException {
        /* 受け取ったデータをもとにしてデータベースの部員情報を変更する
        @param data[]: 更新後のデータ
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();

        for (int row = 0; row < dlt_table.getRowCount(); row++) {
            // 対象の情報の支払日と部員番号を取り出す
            String date = dlt_table.getValueAt(row, 0).toString();
            String mbr_num = dlt_table.getValueAt(row, 1).toString();

            // データベースの部員情報を変更する
            stmt.execute("DELETE FROM budgets WHERE paid_date = '" + date
                    + "' AND member_id = " + mbr_num + ";");
        }

        // リソースを解放する
        stmt.close();
        // SQL操作をデータベースに反映する
        con.commit();
        // データベースを閉じる
        closeDB();
    }


    private List<String[]> searchBgtInfo(final String data[]) throws SQLException {
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

        String sql = "SELECT paid_date, member_id, h_name, amount FROM budgets JOIN members"
                + " ON member_id = id WHERE paid_date BETWEEN '" + start_date + "' AND '" + end_date
                + "' ORDER BY paid_date ASC;";

        // 条件に沿って検索する
        ResultSet rs = stmt.executeQuery(sql);

        java.util.List<String[]> res_list = new ArrayList<>(); // 検索結果を格納するためのリスト
        // 検索結果を取得し、格納する
        while (rs.next()) {
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
            String date = sdFormat.format(rs.getDate("paid_date"));

            // 検索結果として追加する情報の配列を生成
            String res_array[] = {
                    date, // 支払日
                    String.valueOf(rs.getInt("member_id")), // 部員番号
                    rs.getString("h_name"), // ハンドルネーム
                    String.valueOf(rs.getInt("amount")), // 支払額
                    "x" // 削除ボタン
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

    private class DltResponse implements ButtonResponse {
        @Override
        public void pushYes() {
            try {
                // 削除部員一覧に登録している部費情報を削除する
                updateDB();
                // ログの追加
                bc_func.addLog(dlt_table.getRowCount() + "件の部費情報を、データベースから削除しました。");
                // 削除部費情報一覧のデータを表から削除
                dlt_table_model.setRowCount(0);
                // キャラクターに報告させる
                bc_func.charaSpeak("部費情報の削除に成功し", "報告");
            } catch (SQLException e) {
                // キャラクターに報告させる
                bc_func.charaSpeak("部費情報の削除に失敗し", "報告");
                //System.out.println("退部した部員の部員情報の変更に失敗しました。");
                e.printStackTrace();
            }

            // 部品をいじれる状態に戻す
            makeComponentsEnabled();
            // 確認画面を削除
            remove(dlt_confirmer);
            repaint();
        }

        @Override
        public void pushNo() {
            // 部品をいじれる状態に戻す
            makeComponentsEnabled();
            // 確認画面を削除
            remove(dlt_confirmer);
            repaint();
        }

        @Override
        public void pushClose() { }
    }


    private void setBgtTable(final List<String[]> list) {
        /* パラメータで受け取ったデータをもとにして検索結果の表を更新する
        @param data_list: 検索結果を格納しているリスト
         */
        // そのとき検索結果の表にセットされている行をすべて削除する
        bgt_table_model.setRowCount(0);

        // 検索結果のデータを表に加える
        for (String data[] : list) {
            // 表にデータを追加する
            bgt_table_model.addRow(data);
            // 表のサイズを調整
            resizeColumnsWidth(bgt_table);
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
                    System.out.println("yeah");
                    // ボタンを押した後にボタンが"false"という表示に代わるの防ぐ
                    bgt_table.setValueAt("x", row, 4);
                    // 指定された行の情報を削除予定部費情報一覧に移す
                    String info[] = getRowInfo(bgt_table, row);
                    dlt_table_model.addRow(info);
                    // 表のサイズを調整
                    resizeColumnsWidth(dlt_table);
                    // 部費情報一覧表から指定された行を削除
                    int converted_row = bgt_table.convertRowIndexToModel(row);
                    bgt_table_model.removeRow(converted_row);
                }
            });
            return button;
        }
    }


    private class DltButtonEditor extends DefaultCellEditor {

        public DltButtonEditor(JTextField txt_field) {
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
                    // ボタンを押した後にボタンが"false"という表示に代わるの防ぐ
                    dlt_table.setValueAt("x", row, 4);
                    // 指定された行の情報(ボタン以外)を部員一覧に戻す
                    String info[] = getRowInfo(dlt_table, row);
                    bgt_table_model.addRow(info);
                    // 削除予定表から指定された行を削除
                    int converted_row = dlt_table.convertRowIndexToModel(row);
                    dlt_table_model.removeRow(converted_row);
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
        else if (!Pattern.compile("^-?[0-9]+$").matcher(year).find()) { // 数字出ないものが年の欄に含まれるとき。正規表現を使って入学年度が数字であるかを確認している
            // キャラクターに注意させる
            bc_func.charaSpeak("対象とする年には、数字を入力し", "命令2");
            //System.out.println("年が数字じゃなくね");
            return false;
        }
        else if (year.length() != 4) { // 年の桁数が4桁ではないとき
            // キャラクターに注意させる
            bc_func.charaSpeak("対象とする年には、4桁の数字を入力し", "命令2");
            //System.out.println("年間違い");
            return false;
        }
        else if (!Pattern.compile("^-?[0-9]+$").matcher(month).find()) { // 数字出ないものが月の欄に含まれるとき。正規表現を使って入学年度が数字であるかを確認している
            // キャラクターに注意させる
            bc_func.charaSpeak("対象とする月には、数字を入力し", "命令2");
            //System.out.println("月が数字じゃなくね");
            return false;
        }
        else if (month.length() > 2) { // 月の桁数が3桁以上のとき
            // キャラクターに注意させる
            bc_func.charaSpeak("対象とする月には、2桁以内の数字を入力し", "命令2");
            //System.out.println("月間違い");
            return false;
        }
        else { // 入力し忘れている情報がないとき
            data[0] = year;
            data[1] = month;

            return true;
        }
    }


    private String[] getRowInfo(final JTable jtable, final int row) {
        /* 指定された表の指定された行の情報を取得し、配列で返す
        @param jtable: 情報を取り出す対象のテーブル
        @param row: 情報を取り出す対象の行
        @return bgtarray: 指定された行の情報を格納した配列
         */
        int range = jtable.getColumnCount();
        String res_array[] = new String[range]; // 返す配列
        for (int column = 0; column < range; column++) { // ボタンの行以外の情報を格納する
            res_array[column] = jtable.getValueAt(row, column).toString();
        }

        return res_array;
    }


    private void makeComponentsDisenabled() {
        /* 部品をいじれない状態にする
         */
        year_field.setEnabled(false);
        month_field.setEnabled(false);
        sch_button.setEnabled(false);
        bgt_table.setEnabled(false);
        bgt_table_spanel.setEnabled(false);
        dlt_table.setEnabled(false);
        dlt_table_spanel.setEnabled(false);
        dlt_button.setEnabled(false);
        bk_button.setEnabled(false);
    }


    private void makeComponentsEnabled() {
        /* 部品をいじれる状態にする
         */
        year_field.setEnabled(true);
        month_field.setEnabled(true);
        sch_button.setEnabled(true);
        bgt_table.setEnabled(true);
        bgt_table_spanel.setEnabled(true);
        dlt_table.setEnabled(true);
        dlt_table_spanel.setEnabled(true);
        dlt_button.setEnabled(true);
        bk_button.setEnabled(true);
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


    private void removeSameInfo() {
        /* 部費情報一覧と削除予定部費情報一覧で被っている情報があったら
        部費情報一覧には表示しないようにする
         */
        for (int dlt_row = 0; dlt_row < dlt_table.getRowCount(); dlt_row++) {
            // 削除予定部費情報一覧から対象とする行の支払日と部員番号を取り出す
            String dlt_date = dlt_table.getValueAt(dlt_row, 0).toString();
            String dlt_mbr_num = dlt_table.getValueAt(dlt_row, 1).toString();
            for (int bgt_row = 0; bgt_row < bgt_table.getRowCount(); bgt_row++) {
                // 指定した年月の部費情報一覧から対象とする行の支払日と部員番号を取り出す
                String bgt_date = bgt_table.getValueAt(bgt_row, 0).toString();
                String bgt_mbr_num = bgt_table.getValueAt(bgt_row, 1).toString();
                if (dlt_date.equals(bgt_date) && dlt_mbr_num.equals(bgt_mbr_num)) { // 削除予定部費情報一覧の情報と一致する行があったとき
                    // 指定した年月の部費情報一覧から指定された行を削除
                    int converted_row = bgt_table.convertRowIndexToModel(bgt_row);
                    bgt_table_model.removeRow(converted_row);
                }
            }
        }
    }
}
