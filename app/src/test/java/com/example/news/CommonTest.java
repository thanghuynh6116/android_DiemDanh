package com.example.news;

import com.example.news.untils.Common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommonTest {
    @Test
    public void testIsvalidEmail1() {
        assertEquals(Common.isValidEmailId("abc"), false);
    }

    @Test
    public void testIsvalidEmail2() {
        assertEquals(Common.isValidEmailId("abc"), false);
    }

    @Test
    public void testIsvalidEmail3() {
        assertEquals(Common.isValidEmailId("abc@gmail.com"), true);
    }

    @Test
    public void testIsvalidQRcode1() {
        assertEquals(Common.isValidQRCODE("abc"), false);
    }

    @Test
    public void testIsvalidQRcode2() {
        assertEquals(Common.isValidQRCODE("HỌ VÀ TÊN: PHAN ĐĂNG KHOA\n" +
                "    NGÀNH: CÔNG NGHỆ THÔNG TIN\n" +
                "    KHÓA HỌC: 2018-2022"), false);
    }

    @Test
    public void testIsvalidQRcode3() {
        assertEquals(Common.isValidQRCODE("HỌ VÀ TÊN: PHAN ĐĂNG KHOA\n" +
                "    NGÀNH: CÔNG NGHỆ THÔNG TIN\n" +
                "    KHÓA HỌC: 2018-2022\n" +
                "    MÃ SINH VIÊN: 187IT20877\n" +
                "    ĐỊNH DANH: 187IT20877.VLU@is-tech.vn"), true);
    }



}
