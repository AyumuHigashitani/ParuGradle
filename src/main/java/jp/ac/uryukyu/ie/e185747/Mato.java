package jp.ac.uryukyu.ie.e185747;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Mato {

    private int x,y,hanbun;//hanbu　＝　的の大きさ
    private Image image;
    private Rectangle rectangle;
    private WaveEngine wav;
    private String soundFilenames = "pon.wav";

    /*
    コンストラクタ
     */
    public Mato() {
        wav = new WaveEngine();

        x = (int) (Math.random() * 600) + 500;
        y = (int) (Math.random() * 500);
        this.hanbun = 64;

        wav.load(soundFilenames,"wav/"+soundFilenames);
        loadImage("mato.gif");
    }

    /*
    的を描画
     */
    public void drow(Graphics g){
        g.drawImage(image,x,y,hanbun,hanbun,null);
    }

    /*
    イメージをロード
     */
    public void loadImage(String filename){
        ImageIcon icon = new ImageIcon(getClass().getResource("image/" + filename));
        image = icon.getImage();
    }

    /*
    クリック地点と重なったかどうか
    円で判定すべきだが，思いつかなかったので四角で判定
     */
    public boolean cross(int paruCount,int mouseX,int mouseY){
        if (paruCount == 3) { //打つモーションの時に判定させたいので，countが3コマ目の時
            rectangle = new Rectangle(x, y, hanbun, hanbun);
            return rectangle.contains(mouseX, mouseY);
        }else {
            return false;
        }
    }

    /*
    音を鳴らす
     */
    public void soundPlay(){
        wav.play(soundFilenames);
    }

 }

