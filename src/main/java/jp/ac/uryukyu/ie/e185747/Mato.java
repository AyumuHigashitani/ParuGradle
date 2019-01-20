package jp.ac.uryukyu.ie.e185747;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.ImageIcon;

public class Mato {

    private int x,y,hanbun;//hanbu　＝　的の大きさ
    private Image image;
    private Rectangle rectangle;
    private WaveEngine wav;
    private String soundFilenames = "pon.wav";

    public Mato() {
        wav = new WaveEngine();

        x = (int) (Math.random() * 600) + 500;
        y = (int) (Math.random() * 500);
        this.hanbun = 64;

        wav.load(soundFilenames,"wav/"+soundFilenames);
        loadImage("mato.gif");
    }

    public void drow(Graphics g){

        g.drawImage(image,x,y,hanbun,hanbun,null);

    }

    public void loadImage(String filename){

        File file = new File(filename);

        ImageIcon icon = new ImageIcon(getClass().getResource("image/" + filename));
        image = icon.getImage();
    }

    public boolean cross(int paruCount,int mouseX,int mouseY){
        if (paruCount == 3) {
            rectangle = new Rectangle(x, y, hanbun, hanbun);
            return rectangle.contains(mouseX, mouseY);
        }else {
            return false;
        }
    }

    public void soundPlay(){
        wav.play(soundFilenames);
    }

 }

