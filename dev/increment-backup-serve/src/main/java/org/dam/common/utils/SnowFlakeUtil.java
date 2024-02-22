package org.dam.common.utils;

import org.springframework.stereotype.Component;

/**
 * @Author dam
 * @create 2024/2/22 16:14
 */
@Component
public class SnowFlakeUtil {
    /**
     * 默认的起始时间，为Thu, 04 Nov 2010 01:42:54 GMT
     */
    private static long DEFAULT_TWEPOCH = 1288834974657L;
    // 时间戳所占位数
    private final static long TIMESTAMP_BITS = 41L;
    // 数据中心标识所占位数（一台机器，设置1就够用了）
    private final static long DATACENTER_BITS = 1L;
    // 机器标识所占位数（一台机器，设置1就够用了）
    private final static long WORKER_ID_BITS = 1L;
    // 每毫秒产生的序列号所占位数
    private final static long SEQUENCE_BITS = 20L;

    // 数据中心标识最大值
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BITS);
    // 机器标识最大值
    private final static long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);
    // 序列号最大值
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BITS);

    // 数据中心标识向左移位数
    private final static long DATA_CENTER_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + TIMESTAMP_BITS;
    // 机器标识向左移位数
    private final static long WORKER_SHIFT = SEQUENCE_BITS + TIMESTAMP_BITS;
    // 时间戳向左移位数
    private final static long TIMESTAMP_SHIFT = SEQUENCE_BITS;

    // 数据中心标识
    private long datacenterId = 1;
    // 机器标识
    private long workerId = 1;
    // 序列号
    private long sequence = 0L;
    // 上次生成ID的时间戳
    private long lastTimestamp = -1L;

    public SnowFlakeUtil() {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter ID can't be greater than " + MAX_DATACENTER_NUM + " or less than 0");
        }
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException("Worker ID can't be greater than " + MAX_WORKER_ID + " or less than 0");
        }
        this.datacenterId = datacenterId;
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        // 如果当前时间小于上次生成ID的时间戳，则说明系统时钟回退过，需要等待时钟追上才能继续生成ID
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("时间回退，拒绝生成ID");
        }
        // 如果当前时间与上次生成ID的时间戳相同，则序列号加一，否则序列号重置为0
        if (lastTimestamp == timestamp) {
            // &运算符。具体的操作是将 (sequence + 1) 值再加 1，然后对MAX_SEQUENCE取模
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 如果序列号达到最大值，则等待下一毫秒重新生成ID
            if (sequence == 0L) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // 生成ID
        return (((timestamp - DEFAULT_TWEPOCH) << TIMESTAMP_SHIFT) |
                (datacenterId << DATA_CENTER_SHIFT) |
                (workerId << WORKER_SHIFT) |
                sequence);
    }

    /**
     * 等到下一秒再生成时间戳
     *
     * @param lastTimestamp
     * @return
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public static void main(String[] args) {
        // -1 : 00000000000000000000000000000000000000000000001
        // -1L << 2 : 0000000000000000000000000000000000000000000100
        // 000000000000000000000000001
        // 111111111111111111111111011
        // 1111
        System.out.println(-1L ^ (-1L << 14));
    }


}

