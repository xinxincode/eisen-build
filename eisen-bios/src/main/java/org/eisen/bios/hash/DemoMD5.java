package org.eisen.bios.hash;

/**
 * @Author Eisen
 * @Date 2019/1/3 22:27
 * @Description:
 **/
public class DemoMD5 {
    public static void main(String[] args) {

    }

    private static final long A = 0x67452301L;
    private static final long B = 0xefcdab89L;
    private static final long C = 0x98badcfeL;
    private static final long D = 0x10325476L;

    private static long F(long x, long y, long z) {
        return (x & y) | ((~x) & z);
    }

    private static long G(long x, long y, long z) {
        return (x & z) | (y & (~z));
    }

    private static long H(long x, long y, long z) {
        return x ^ y ^ z;
    }

    private static long I(long x, long y, long z) {
        return y ^ (x | (~z));
    }

}
