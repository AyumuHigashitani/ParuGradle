package jp.ac.uryukyu.ie.e185747;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;

public class Paru {
    //位置
    private int x;
    private int y;

    //spriteシートの番号保存系
    private int count; //staticなのは，MainPanelで
    private int maxCount;

    //それぞれのファイル名
    private String[] filenames = {"paru_normal.png","paru_attack.png","efect.png"};

    //モーションの枚数（何コマで書かれているか）
    private int[] NO = {1,3,0};

    //キャラの大きさ
    private final int width = 230*2;
    private final int height = 120*2;

    //イメージ保存用変数
    private Image[] images;

    //状態の引数
    public static final int NORMAL = 0;
    public static final int ATTACK = 1;

    //現在の状態は？
    private int dir;

    //一つ前の状態は？
    private int beforeDir;

    //threadの保存
    AnimationThread thread;

    //sound系
    private WaveEngine wav;
    private String[] soundFilenames = {"ban.wav", "cartridge.wav"};

    /*
    コンストラクタ
     */
    public Paru(int x, int y){

        wav = new WaveEngine();

        //初期値設定
        this.x = x;
        this.y = y;
        this.dir = 0;
        this.count = 0;
        this.maxCount = NO[0];

        //wav.load(soundFilenames[0],"wav/" + soundFilenames[0]);
        //wav.load(soundFilenames[1],"wav/" + soundFilenames[1]);
        loadImage(filenames);


        //アニメーション用スレッド開始
        thread = new AnimationThread();
        thread.start();
    }

    /*
    描画メソッド
    */
    public void drow(Graphics g){

        if (dir != beforeDir){
            count = 0;
            maxCount = NO[dir];
        }

        beforeDir = dir;

        g.drawImage(images[dir],x,y, //どのモーションか＋描き始め座標
                x + width,y + height, //描き終わり座標
                count * width,0, //spriteシートのどこを取ってくるか
                count * width + width, height,null); //spriteシートの終わりはどこ

        if (count == 3){ //カウント3の時にエフェクト追加　ここに音を入れるのは良くないはず。。。︎後日改良
            wav.play(soundFilenames[0]);
            wav.play(soundFilenames[1]);
            g.drawImage(images[2],280,480,100,100,null);
        }
    }

    /*
    イメージのロード
    */
    public void loadImage(String[] filenames){

        images = new Image[filenames.length];

        for (int i= 0; i < filenames.length;i++) {

            //ImageIcon icon = new ImageIcon(new File(filenames[i]).getAbsolutePath());
            ImageIcon icon = new ImageIcon(getClass().getResource("image/" + filenames[i]));
            images[i] = icon.getImage();
        }
    }

    /*
    状態が変わった時のsleep時間を正しく行わせるため
     */
    public void stop(){
            //割り込み判定で強制的にThread.sleepを終了させる
            thread.interrupt();
            setCount(0);
    }

    public void setDir(int dir){
        this.dir = dir;
    }

    public int getCount(){
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }

    private class AnimationThread extends Thread{

        @Override
        public void run() {
            while (true){
                    if (dir == ATTACK) {
                        if (count == maxCount) {
                            dir = NORMAL;
                            maxCount = NO[NORMAL];
                            count = 0;
                        }
                    }

                    if (count == maxCount) {
                            count = 0;
                        } else {
                            count++;
                        }
                //何ミリ秒ごとに画像を切り替えるか
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    //割り込み時の処理を書く
                    continue;
                }
            }
        }
    }
}