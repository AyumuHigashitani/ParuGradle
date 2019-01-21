package jp.ac.uryukyu.ie.e185747;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParuTest {

    @Test
    void getImages() {

        //パルさんの初期位置はユニットテストには関係ない
        Paru paru = new Paru(0,0);

        try {
            assertNotNull(paru.getImages());
        }catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("ソースファイルの置く位置が間違っています\nファイルの位置を確認して，もう一度試してください");
        }


    }
}