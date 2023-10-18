package com.cwc.springbootInit;

import cn.hutool.crypto.digest.MD5;
import org.junit.jupiter.api.Test;


public class md5Test {
    @Test
    public void md5(){
        String s = MD5.create().digestHex("123456");
        System.out.println(s);
    }
}
