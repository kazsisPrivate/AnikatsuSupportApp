package function.db;

import javax.swing.JPanel;

abstract public class DBOprMgr extends JPanel {
    protected DBOperator db_opr; // そのとき画面に表示しているフォーマットのインスタンス
    protected final int SEARCH_FORMAT = 0; // セットするフォーマットを指定するのに使う静的変数
    protected final int ADD_FORMAT = 1;
    protected final int UPDATE_FORMAT = 2;
    protected final int DELETE_FORMAT = 3;
    abstract protected void setDBOperator(final int format); // 指定したフォーマットを表示
}
