package function;

public interface BasicFunction {
    /* 他のクラスに渡して、行ってほしい機能を詰め込んだインターフェース
     */
    void addLog(final String msg); // ログに指定した文を追加させるメソッド
    void charaSpeak(final String line, final String end_key); // キャラクターに指定したことを話させるようにするメソッド
    void charaSpeak(final String line1, final String end_key1, final String line2, final String end_key2); // キャラクターに指定したことを話させるようにするメソッド
    void changeChrModeToAutoMode(); // キャラクターのモードをAUTO_MODEに変えるときの処理
    void setDBMgr(final int mgr); // 指定したDBMgrをセットするようにするためのメソッド
}
