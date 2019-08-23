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

}
