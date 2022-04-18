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


public class MbrInfoDeleter extends DBOperator {

    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅

    private ImageIcon frame; // フレームの画像
    private JLabel frame_label; // フレームの画像のラベル
    private final int FM_WIDTH = 860; // フレームの画像の幅
    private final int FM_HEIGHT = 980; // フレームの画像の高さ
    private final int FM_X = 50; // フレームの左上端のx座標
    private final int FM_Y = 30; // フレームの左上端のy座標

    private JLabel dlt_icon_label; // Deleteのアイコンの画像のラベル
    private ImageIcon dlt_icon; // Deleteのアイコンの画像
    private final int ICON_WIDTH = 270; // アイコン画像の幅
    private final int ICON_HEIGHT = 130; // アイコン画像の高さ
    private final int ICON_X = 5;//650; // アイコンの左上端のx座標
    private final int ICON_Y = 5; // アイコンの左上端のy座標

    private final int LB_FONT_SIZE = 30; // 表の上のラベルの文字の大きさ
    private final Font LABEL_FONT = new Font("HGPゴシックM", Font.PLAIN, LB_FONT_SIZE); // 表の上のラベルのフォント

    private final int ROW_HEIGHT = 25; // 表の一行の高さ
    private final Font TABLE_FONT = new Font("游明朝", Font.PLAIN, ROW_HEIGHT-5); // 表のフォント

    private final int AREA_WIDTH = 800; // スクロールパネルの幅
    private final int AREA_HEIGHT = 300; // スクロールパネルの高さ

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

    private JLabel dlt_table_label; // 表の上に置く"退部する部員"というラベル
    private final int DLTTBLB_WIDTH = LB_FONT_SIZE * 9; // "退部する部員"というラベルの幅
    private final int DLTTBLB_HEIGHT = LB_FONT_SIZE; // "退部する部員"というラベルの高さ
    private final int DLTTBLB_X = FRAME_WIDTH/4 - DLTTBLB_WIDTH/2; // "退部する部員"というラベルの左上端のx座標
    private final int DLTTBLB_Y = 510; // "退部する部員"というラベルの左上端のy座標
    
    private DefaultTableModel dlt_table_model;
    private JTable dlt_table; // 削除予定の部員一覧表
    private JScrollPane dlt_table_spanel; // 上の表ににスクロールバーを付けたパネル
    private final int DLT_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int DLT_AREA_Y = 550; // スクロールパネルの左上端のy座標

    private JButton dlt_button; // 退部済に変更ボタン
    private final int DLTTBT_WIDTH = 230; // 退部済に変更ボタンの幅
    private final int DLTTBT_HEIGHT = 60; // 退部済に変更ボタンの高さ
    private final int DLTTBT_X = FRAME_WIDTH/4 - DLTTBT_WIDTH/2; // 退部済に変更ボタンの左上端のx座標
    private final int DLTTBT_Y = 870; // 退部済に変更ボタンの左上端のy座標

    private Confirmer dlt_confirmer; // データベースの情報を変更するかの最終確認の画面
    private final Font CONF_FONT = new Font("MS Pゴシック", Font.BOLD, 50); // 確認の文のフォント

    private JButton bk_button; // 戻るボタン
    private final int BKBT_WIDTH = 90; // 戻るボタンの幅
    private final int BKBT_HEIGHT = 50; // 戻るボタンの高さ
    private final int BKBT_X = 780; // 戻るボタンの左上端のx座標
    private final int BKBT_Y = 910; // 戻るボタンの左上端のy座標

    private ButtonResponse bt_resp; // コンストラクタで受け取ったMbrDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる


    public static void main(String args[]) {
        JFrame jf = new JFrame("inserter");
        // ウインドウの作成
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(1920, 1080);

        // ウインドウを画面の中央に表示するようにする
        jf.setLocationRelativeTo(null);
        // ウインドウのサイズを変更できないようにする
        jf.setResizable(false);
        // レイアウトの設定
        jf.setLayout(null);

        jf.setVisible(true);
    }

    public MbrInfoDeleter(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // キャラクターに最初の説明を話させる
        bc_func.charaSpeak("退部する部員を上の表から選択して、下の「退部済に変更」ボタンを押し", "命令1");

        // 画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/opr_frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // Deleteのアイコンのラベルのインスタンスを取得、設定
        dlt_icon = new ImageIcon("image/component/home/anikatsudb/member/deleter/icon/icon.png");
        dlt_icon_label = new JLabel(dlt_icon);
        dlt_icon_label.setBounds(ICON_X, ICON_Y, ICON_WIDTH, ICON_HEIGHT);

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
        String mbr_column_names[] = {"部員番号", "入学年度", "氏名", "ハンドルネーム", "退部ボタン"};
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
        mbr_table.getColumn("退部ボタン").setCellRenderer(new ButtonRenderer());
        mbr_table.getColumn("退部ボタン").setCellEditor(new MbrButtonEditor(new JTextField("x")));
        // 自動でソートできるようにする
        mbr_table.setAutoCreateRowSorter(true);
        // 数字（文字列）のソートの設定だけ行う
        TableRowSorter<DefaultTableModel> mbr_sorter = new TableRowSorter<DefaultTableModel>(mbr_table_model);
        mbr_sorter.setComparator(0, new Comparator<String>() { // 部員番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        mbr_sorter.setComparator(1, new Comparator<String>() { // 入学年度の列
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
            List<String[]> data_list= getMemberInfo();
            // 在部している部員一覧表を作成
            setMemTable(data_list);
        } catch (SQLException se) {
            // キャラクターに報告させる
            bc_func.charaSpeak("在部している部員の情報を取得するのに失敗し", "報告");
            //System.out.println("在部している部員の情報を取得するのに失敗しました。");
            se.printStackTrace();
        }
        
        // "退部する部員"のラベルを作る
        dlt_table_label = new JLabel("《 退部する部員 》");
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
        String dlt_column_names[] = {"部員番号", "入学年度", "氏名", "ハンドルネーム", "取消ボタン"};
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
        dlt_table_spanel.setPreferredSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));
        // パネルの配置を設定
        dlt_table_spanel.setBounds(DLT_AREA_X, DLT_AREA_Y, AREA_WIDTH, AREA_HEIGHT);

        // 部員番号の列と入学年度の列を右寄せ表示にする
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        TableColumnModel mbr_column_model = mbr_table.getColumnModel();
        TableColumnModel dlt_column_model = dlt_table.getColumnModel();
        for (int column = 0; column < 2; column++) {
            mbr_column_model.getColumn(column).setCellRenderer(rightCellRenderer);
            dlt_column_model.getColumn(column).setCellRenderer(rightCellRenderer);
        }

        // 確認の画面のインスタンスを生成
        String confirmation = "本当に、下の表に追加した部員において、データベースの在部状況のフィールドを、退部済に変更しますか？";
        dlt_confirmer = new Confirmer(new DltResponse(), confirmation, CONF_FONT, Confirmer.YES_NO);
        // 位置設定
        dlt_confirmer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        
        // 退部済に変更ボタンの生成
        dlt_button = new JButton();
        // サイズの設定
        dlt_button.setPreferredSize(new Dimension(DLTTBT_WIDTH, DLTTBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon preaddb_icon = new ImageIcon("image/component/home/anikatsudb/member/deleter/button/dlt_button.png");
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
                // 部品を一時的にいじれない状態にする
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
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsudb/member/deleter/button/bk_button.png");
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
        add(dlt_table_label);
        add(dlt_table_spanel);
        add(dlt_button);
        add(bk_button);
        add(dlt_icon_label);
        add(frame_label);
    }


    private void updateDB() throws SQLException {
        /* 削除部員一覧に登録している部員を退部済に変更する
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();

        for (int row = 0; row < dlt_table.getRowCount(); row++) {
            // 対象の部員番号を取り出す
            int mbr_num = Integer.parseInt(dlt_table.getValueAt(row, 0).toString());

            // データベースの部員情報を変更する
            stmt.execute("UPDATE members SET is_in_club = false WHERE id = " + mbr_num + ";");
        }

        // リソースを解放する
        stmt.close();
        // SQL操作をデータベースに反映する
        con.commit();
        // データベースを閉じる
        closeDB();
    }


    private class DltResponse implements ButtonResponse {
        @Override
        public void pushYes() {
            try {
                // 削除部員一覧に登録している部員を退部済にする
                updateDB();
                // ログの追加
                bc_func.addLog(dlt_table.getRowCount() + "件の部員情報を、データベースから削除しました。");
                // 削除部員一覧のデータを表から削除
                dlt_table_model.setRowCount(0);
                // キャラクターに報告させる
                bc_func.charaSpeak("退部する部員の部員情報の変更に成功し", "報告");
            } catch (SQLException e) {
                // キャラクターに報告させる
                bc_func.charaSpeak("退部する部員の部員情報の変更に失敗し", "報告");
                System.out.println("退部した部員の部員情報の変更に失敗しました。");
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
            // 確認画面を削除
            remove(dlt_confirmer);
            repaint();
        }

        @Override
        public void pushClose() { }
    }


    private void makeComponentsDisenabled() {
        /* 部品をいじれない状態にする
         */
        mbr_table_spanel.setEnabled(false);
        mbr_table.setEnabled(false);
        dlt_table_spanel.setEnabled(false);
        dlt_table.setEnabled(false);
        dlt_button.setEnabled(false);
        bk_button.setEnabled(false);
    }


    private void makeComponentsEnabled() {
        /* 部品をいじれる状態にする
         */
        mbr_table_spanel.setEnabled(true);
        mbr_table.setEnabled(true);
        dlt_table_spanel.setEnabled(true);
        dlt_table.setEnabled(true);
        dlt_button.setEnabled(true);
        bk_button.setEnabled(true);
    }


    private List<String[]> getMemberInfo() throws SQLException {
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

            // 検索結果として追加する情報の配列を生成
            String res_array[] = {
                    String.valueOf(rs.getInt("id")), // 部員番号
                    String.valueOf(rs.getInt("entry_year")), // 入学年度
                    rs.getString("name"), // 氏名
                    h_name, // ハンドルネーム
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
                    // ボタンを押した後にボタンが"false"という表示に代わるの防ぐ
                    mbr_table.setValueAt("x", row, 4);
                    // 指定された行の情報を削除予定部員一覧に移す
                    String info[] = getRowInfo(mbr_table, row);
                    dlt_table_model.addRow(info);
                    // 表のサイズを調整
                    resizeColumnsWidth(dlt_table);
                    // 部員一覧表から指定された行を削除
                    int converted_row = mbr_table.convertRowIndexToModel(row);
                    mbr_table_model.removeRow(converted_row);
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
                    mbr_table_model.addRow(info);
                    // 削除予定表から指定された行を削除
                    int converted_row = dlt_table.convertRowIndexToModel(row);
                    dlt_table_model.removeRow(converted_row);
                }
            });
            return button;
        }
    }


    private String[] getRowInfo(final JTable jtable, final int row) {
        /* 指定された表の指定された行の情報を取得し、配列で返す
        @param jtable: 情報を取り出す表
        @param row: 情報を取り出す対象の行
        @return res_array: 指定された行の情報を格納した配列
         */
        int range = jtable.getColumnCount();
        String res_array[] = new String[range]; // 返す配列
        for (int column = 0; column < range; column++) { // ボタンの行以外の情報を格納する
            res_array[column] = jtable.getValueAt(row, column).toString();
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


    private void setMemTable(final List<String[]> data_list) {
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
}
