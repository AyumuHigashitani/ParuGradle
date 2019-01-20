package jp.ac.uryukyu.ie.e185747;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Image;

public abstract class Panel  extends JPanel implements Runnable{

    // パネルサイズ
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;

    //読み込みたいファイル名
    protected String[] filenames;

    //イメージの保存
    protected Image[] images;

    //ループ用のthread
    protected Thread thread;

    //不透明度
    protected float F;

    /*
    Runnableを使う場合は，run()の中身を必ず書かなければいけない
     */
    @Override
    public abstract void run();

    /*
    イメージのロード用メソッド
     */
    public void loadImage(String[] filenames) {

        images = new Image[filenames.length];

        //なんとか頑張ってみたかったが，ほぼコピペ。。。
        //getClass　＝　クラスをとる　　getRsource　＝　ソースファイルの位置はどこか
        for (int i = 0; i < filenames.length; i++) {

            ImageIcon icon = new ImageIcon(getClass().getResource("image/" + filenames[i]));
            images[i] = icon.getImage();
        }
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getWIDTH() {
        return WIDTH;
    }
}
