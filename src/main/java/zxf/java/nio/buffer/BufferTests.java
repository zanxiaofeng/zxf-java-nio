package zxf.java.nio.buffer;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.InvalidMarkException;

public class BufferTests {
    public static void main(String[] args) {
        testBufferCapacity();
        testBufferLimit();
        testBufferPosition();
        testBufferMarkAndReset();
        testBufferInvalidMark();
        testBufferRead();
        testBufferWrite();
    }

    private static void testBufferCapacity() {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(-1);
        } catch (IllegalArgumentException ex) {
            System.out.println("ByteBuffer容量capacity大小不能为负数.");
        }
    }

    private static void testBufferLimit() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{1, 2, 3});
        try {
            byteBuffer.limit(-1);
        } catch (IllegalArgumentException ex) {
            System.out.printf("ByteBuffer限制limit大小不能为负数. capacity=%d, limit=%d, position=%d, readonly=%b, direct=%b\n",
                    byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.isReadOnly(), byteBuffer.isDirect());
        }

        try {
            byteBuffer.limit(100);
        } catch (IllegalArgumentException ex) {
            System.out.printf("ByteBuffer限制limit大小不能大于其capacity容量. capacity=%d, limit=%d, position=%d, readonly=%b, direct=%b\n",
                    byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.isReadOnly(), byteBuffer.isDirect());
        }
    }

    private static void testBufferPosition() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{1, 2, 3});
        byteBuffer.limit(2);
        try {
            byteBuffer.position(3);
        } catch (IllegalArgumentException ex) {
            System.out.printf("ByteBuffer的position位置不能大于其limit限制. capacity=%d, limit=%d, position=%d, readonly=%b, direct=%b\n",
                    byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.isReadOnly(), byteBuffer.isDirect());
        }

        try {
            byteBuffer.position(2);
            byteBuffer.put((byte) 4);
        } catch (BufferOverflowException ex) {
            System.out.printf("ByteBuffer overflow. capacity=%d, limit=%d, position=%d, readonly=%b, direct=%b\n",
                    byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.isReadOnly(), byteBuffer.isDirect());
        }
    }

    private static void testBufferMarkAndReset() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{1, 2, 3});
        byteBuffer.position(1);

        byteBuffer.mark();
        System.out.printf("ByteBuffer mark to. capacity=%d, limit=%d, position=%d, readonly=%b, direct=%b\n",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.isReadOnly(), byteBuffer.isDirect());

        byteBuffer.position(3);
        System.out.printf("ByteBuffer position to. capacity=%d, limit=%d, position=%d, readonly=%b, direct=%b\n",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.isReadOnly(), byteBuffer.isDirect());

        byteBuffer.reset();
        System.out.printf("ByteBuffer reset to. capacity=%d, limit=%d, position=%d, readonly=%b, direct=%b\n",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.isReadOnly(), byteBuffer.isDirect());
    }

    private static void testBufferInvalidMark() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{1, 2, 3});

        try {
            byteBuffer.reset();
        } catch (InvalidMarkException ex) {
            System.out.printf("ByteBuffer的mark标记不存在, 不能reset. capacity=%d, limit=%d, position=%d, readonly=%b, direct=%b\n",
                    byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.isReadOnly(), byteBuffer.isDirect());
        }

        byteBuffer.position(2);
        byteBuffer.mark();
        System.out.printf("ByteBuffer mark to. capacity=%d, limit=%d, position=%d, readonly=%b, direct=%b\n",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.isReadOnly(), byteBuffer.isDirect());

        try {
            byteBuffer.position(1);
            byteBuffer.reset();
        } catch (InvalidMarkException ex) {
            System.out.printf("ByteBuffer的mark标记失效-by position, 不能reset. capacity=%d, limit=%d, position=%d, readonly=%b, direct=%b\n",
                    byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.isReadOnly(), byteBuffer.isDirect());
        }

        try {
            byteBuffer.limit(1);
            byteBuffer.reset();
        } catch (InvalidMarkException ex) {
            System.out.printf("ByteBuffer的mark标记失效-by limit,不能reset. capacity=%d, limit=%d, position=%d, readonly=%b, direct=%b\n",
                    byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.isReadOnly(), byteBuffer.isDirect());
        }
    }

    private static void testBufferRead() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{1, 2, 3});
        System.out.printf("ByteBuffer.read after init. capacity=%d, limit=%d, position=%d, readonly=%b, direct=%b\n",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.isReadOnly(), byteBuffer.isDirect());

        System.out.println("Content: " + byteBuffer.get());

        System.out.printf("ByteBuffer.read after read 1. capacity=%d, limit=%d, position=%d, readonly=%b, direct=%b\n",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.isReadOnly(), byteBuffer.isDirect());
    }

    private static void testBufferWrite() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        System.out.printf("ByteBuffer.write after init. capacity=%d, limit=%d, position=%d, readonly=%b, direct=%b\n",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.isReadOnly(), byteBuffer.isDirect());

        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);

        System.out.printf("ByteBuffer.write after write 2. capacity=%d, limit=%d, position=%d, readonly=%b, direct=%b\n",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.isReadOnly(), byteBuffer.isDirect());
    }
}
