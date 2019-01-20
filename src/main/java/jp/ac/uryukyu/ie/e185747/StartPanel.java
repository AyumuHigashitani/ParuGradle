package jp.ac.uryukyu.ie.e185747;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;

public class StartPanel extends Panel{

    static MainPanel mainPanel;

    //private String[] filenames = {"black.gif"};

    public StartPanel(){
        mainPanel = new MainPanel();
        //パネルの大きさなどを設定
        setOpaque(false) ;
        setPreferredSize(new Dimension(getWIDTH(), getHEIGHT()));

        //初期値設定
        this.F = 0.0f;

        //イメージのロード
        loadImage(filenames);

        //ゲーム用のスレッド
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(10);
            } catch (Exception e) {

            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);

        g2.setComposite(composite);
        g.drawImage(images[0],0,0,null);
    }

}
