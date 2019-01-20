package jp.ac.uryukyu.ie.e185747;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;

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

    @Override
    public abstract void run();

    public void loadImage(String[] filenames) {

        images = new Image[filenames.length];

        //なんとか頑張ってみる・・・
        //getClass　＝　クラスをとる　　getRsource　＝　ソースファイルの位置はどこか
        for (int i = 0; i < filenames.length; i++) {

            //System.out.println(new File(filenames[i]).getAbsolutePath());
            //ImageIcon icon = new ImageIcon(new File("file:"+filenames[i]).getAbsolutePath());
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
