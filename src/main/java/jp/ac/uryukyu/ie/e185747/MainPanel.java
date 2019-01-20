package jp.ac.uryukyu.ie.e185747;

import javax.sound.sampled.LineEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

//import javafx.scene.media.AudioClip;

public class MainPanel extends Panel implements MouseMotionListener, MouseListener {

    //読み込みたいファイル名
    private String[] filenames = {"pointaNomal.gif", "pointa.gif", "haikei.jpg"};

    //ゲームが終わったかどうかの変数
    private boolean isFinish;

    //的を何回壊したら終了するか
    private final int endCount = 15;

    //ポインターの押した座標
    private int xPressed;
    private int yPressed;

    //ポインターの座標
    private int x;
    private int y;

    //ポインターの状態
    private boolean isNomal;

    //タイム計測
    private int no;

    //scoreは何点か
    static int score;

    //的関係の時間変数
    private int num; //的を何回壊したか
    private int times1; //的1が消えるまでの時間
    private int times2; //的2が消えるまでの時間

    //的1，的2
    private Mato mato;
    private Mato mato2;

    //パルさん
    private Paru paru;

    //scorepanel
    static ScorePanel scorePanel;

    //Threadのsleep秒
    private int sleepTime;

    //スタートできるかどうか？
    private boolean canStart;

    //Fontについて
    private Font font;

    //sound系
    private WaveEngine wav;
    private String soundFilename = "sunadokeiseiun.wav";

    /*
    コンストラクタ
     */
    public MainPanel() {

        scorePanel = new ScorePanel();
        //javafxが使えないので，急遽wavファイルで。。。
        wav = new WaveEngine();
        wav.load(soundFilename,"wav/" + soundFilename);
        wav.play(soundFilename);

        font = new Font("SansSerif",Font.BOLD,22);

        // パネルの推奨サイズを設定、pack()するときに必要
        setPreferredSize(new Dimension(getWIDTH(), getHEIGHT()));

        //初期値設定
        this.isFinish = false;
        this.canStart = false;
        this.F = 0.0f;
        this.sleepTime = 10;
        score = 0;

        //ロード関係
        loadImage(filenames);

        //AudioClip bgm = new AudioClip(getClass().getResource("wav/" + soundFilename).toString());
        //無限ループ
        //bgm.setCycleCount(AudioClip.INDEFINITE);
        //bgm.play();

        //的の移動までの時間初期値
        times1 = 200;
        times2 = 200;

        //パルさん読み込み+位置指定
        paru = new Paru(10, 350);

        //的の初期
        mato = new Mato();
        mato2 = new Mato();

        //マウスモーションを扱えるように
        addMouseMotionListener(this);
        addMouseListener(this);

        //ゲーム用のスレッド
        thread = new Thread(this);
        thread.start();
    }

    /*
    マウスのカーソルを動かしたとき
     */
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        repaint();
        isNomal = true;
    }

    /*
    マウスをドラッグしたまま移動させたとき
     */
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        repaint();
        isNomal = true;
    }

    @Override
    /*
    マウスが押されたとき
     */
    public void mousePressed(MouseEvent e) {
        canStart = true;
        xPressed = e.getX(); // マウスのX座標
        yPressed = e.getY(); // ラケットを移動
        paru.setDir(paru.ATTACK);
        paru.stop();
        repaint();
    }

    @Override
    /*
    マウスがクリックされたとき
     */
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        isNomal = false;
        repaint();
    }

    @Override
    /*
    マウスが離れたとき
     */
    public void mouseReleased(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        repaint();
    }

    @Override
    /*
    マウスがアプレット上に乗った
     */
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    /*
    マウスがアプレット上から離れたとき
     */
    public void mouseExited(MouseEvent e) {

    }

    /*
    終了した場合の処理
     */
    public void finish() {
        scorePanel.Visible();
    }

    /*
    ゲームとして動く部分
     */
    public void run() {
        while (true) {
            if (canStart) {
                no += 1;
                if (num < endCount) { //何回的を壊せば終了するか
                    //ココは纏められそう。。。
                    if (times1 + 200 == no) { //的1つ目，手動で壊せなかった場合ココ
                        times1 = no;
                        num++;
                        mato = new Mato();
                    } else if (mato.cross(paru.getCount(),xPressed, yPressed)) { //手動で壊した場合はココ
                        mato.soundPlay();
                        mato = new Mato();
                        score ++;
                        times1 = no;
                        num++;
                    }
                    if (num > 5) { //的2つ目は5個以上壊れたら出現
                        if (times2 + 200 == no) {
                            times2 = no;
                            mato2 = new Mato();
                            num++;
                        } else if (mato2.cross(paru.getCount(),xPressed, yPressed)) {
                            mato2.soundPlay();
                            mato2 = new Mato();
                            score ++;
                            times2 = no;
                            num++;
                        }
                    }
                } else {
                    if (!isFinish){ //一度のみ最終scoreを計算する
                        score = Math.round(score * 1000000 / no); //計算式は適当なので，後々適切なものにする
                    }
                    isFinish = true;
                    sleepTime = 200;//フェードアウト時のみ描画を遅くしたいため
                    if (F < 0.6f) {
                        F += 0.05;
                    } else {
                        finish();
                    }
                }
                try {
                    Thread.sleep(sleepTime);
                } catch (Exception e) {

                }
            }
            repaint();

        }
    }

    /**
     * gameLoop.start　→　run, repaint()　→　paintComponent　とくっついているので，ここで書けば，runで呼び出される
     * @param g　グラフィック
     */
    public void paintComponent(Graphics g) {

        super.paintComponent(g); //いる？？

        g.drawImage(images[2], 0, 0, null); //背景
        paru.drow(g);//パルさん描画

        if (canStart) {
            if (!isFinish) {
                mato.drow(g);
                if (num > 5) {
                    mato2.drow(g);
                }
            } else { //ゲームが終了したら，灰色の半透明で塗りつぶす

                Graphics2D g2 = (Graphics2D) g;

                // アルファ値
                AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, F);

                g2.setComposite(composite);// アルファ値をセット（以後の描画は半透明になる
                g.setColor(Color.gray);
                g.fillRect(0, 0, getWIDTH(), getHEIGHT());
            }

        }else { //スタート画面を上手く作れていないので，仮仕様。。。
            g.setFont(font);
            g.drawString("Please Click and Start",getWIDTH()/2,getHEIGHT()/2);
        }

        //ポインタの描画
        if (isNomal) {
            g.drawImage(images[0], x - 8, y - 8, null);
        } else {
            g.drawImage(images[1], x - 8, y - 8, null);
        }
    }
}