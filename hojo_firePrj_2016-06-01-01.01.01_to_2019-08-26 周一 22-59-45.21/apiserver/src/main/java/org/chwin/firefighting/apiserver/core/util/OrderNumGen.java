package org.chwin.firefighting.apiserver.core.util;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 订单生成工具类:
 * <p>正常情况下:业务编码(2位)+年(2位)+月(2位)+日(2位)+秒(5位)+循环自增值(3位)+随机码(2位)共18位</p>
 * <p>非正常情况下:业务编码(2位)+年(2位)+月(2位)+日(2位)+秒(5位)+随机码(5位)共18位</p>
 * 业务编码(2位)：10停车订单、20支付订单待定
 *
 * @author wangjunma
 * @version $Id: OrderNumGen,v 0.1 2017/12/18 9:42 Exp $$
 */
public class OrderNumGen {

    /**
     * 循环自增上限,超出则从初始值开始
     */
    private static int maxLoop = 999;
    private static int initNum = 100;
    private volatile static AtomicInteger atomicInteger;

    /**
     * 获取单例atomicInteger
     *
     * @return
     */
    private static AtomicInteger getAtomicInteger() {
        if (atomicInteger == null) {
            synchronized (OrderNumGen.class) {
                if (atomicInteger == null)
                    //初始值为100
                    atomicInteger = new AtomicInteger(initNum);
            }
        }
        return atomicInteger;
    }

    /**
     * 订单号生成
     *
     * @param bsCode 业务编码
     * @return
     */
    public static String next(String bsCode) {
        //获取单例
        AtomicInteger atomicInteger = getAtomicInteger();
        //原子性自增并返回自增前的值
        int nextValue = atomicInteger.getAndIncrement();
        //若循环次数已超过上限
        if (nextValue > maxLoop) {
            //CAS更新值成功
            if (atomicInteger.compareAndSet(nextValue + 1, initNum)) {
                atomicInteger.getAndIncrement();
                return getNextNormally(bsCode, initNum);
            } else {//CAS更新值失败
                //重新获取
                nextValue = atomicInteger.getAndIncrement();
                if (nextValue <= maxLoop) {
                    return getNextNormally(bsCode, nextValue);
                } else {//非正常情况下
                    return getNextUnNormally(bsCode);
                }
            }
        }
        return getNextNormally(bsCode, nextValue);
    }

    public static String getNextNormally(String bsCode, int nextValue) {
        StringBuilder stringBuilder = new StringBuilder();
        //2位随机码
        int randomNum = (int) (Math.random() * 90) + 10;
        stringBuilder.append(bsCode).append(getDateNum()).append(nextValue).append(randomNum);
        return stringBuilder.toString();
    }

    public static String getNextUnNormally(String bsCode) {
        StringBuilder stringBuilder = new StringBuilder();
        //5位随机码
        int randomNum = (int) (Math.random() * 90000) + 10000;
        stringBuilder.append(bsCode).append(getDateNum()).append(randomNum);
        return stringBuilder.toString();
    }

    /**
     * 获取订单号中的时间部分
     *
     * @return
     */
    public static String getDateNum() {
        String sTime = DateUtil.format(new Date(), DateUtil.pattern5);
        Integer nSecond = Integer.valueOf(sTime.substring(6, 8)) * 60 * 60 + Integer.valueOf(sTime.substring(8, 10)) * 60
                + Integer.valueOf(sTime.substring(10, 12));
        String sSecond = String.valueOf(nSecond);
        if (sSecond.length() < 5) {
            for (int i = 5, j = sSecond.length(); i > j; i--) {
                sSecond = "0" + sSecond;
            }
        }
        return sTime.substring(0, 6) + sSecond;
    }


    //==============================Test=============================================

    /**
     * 测试
     */
    public static void main(String[] args) throws InterruptedException {
        //生成停车订单
        String bsCode = "10";
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            String id = next(bsCode);
            System.out.println(id);
        }
        System.out.println("时间:" + (System.currentTimeMillis() - startTime));
    }
}