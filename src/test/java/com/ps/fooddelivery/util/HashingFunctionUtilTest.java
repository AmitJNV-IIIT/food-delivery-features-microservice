//package com.ps.fooddelivery.util;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class HashingFunctionUtilTest {
//
//    @Test
//    public void testHashPassword() {
//        String rawPassword = "password123";
//        String hashedPassword = HashingFunctionUtil.hashPassword(rawPassword);
//
//        assertNotNull(hashedPassword);
//        assertNotEquals(rawPassword, hashedPassword);
//    }
//
//    @Test
//    public void testValidatePassword() {
//        String rawPassword = "password123";
//        String hashedPassword = HashingFunctionUtil.hashPassword(rawPassword);
//
//        assertTrue(HashingFunctionUtil.validatePassword(rawPassword, hashedPassword));
//        assertFalse(HashingFunctionUtil.validatePassword("wrongpassword", hashedPassword));
//    }
//
//    @Test
//    public void testUtilityClassConstructor() {
//        // Use reflection to access the private constructor
//        try {
//            var constructor = HashingFunctionUtil.class.getDeclaredConstructor();
//            constructor.setAccessible(true);
//            constructor.newInstance();
//            fail("Expected AssertionError to be thrown");
//        } catch (Exception e) {
//           // assertTrue(instanceof AssertionError);
//            assertEquals("Cannot instantiate utility class", e.getMessage());
//        }
//    }
//}
//
