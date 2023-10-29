package com.y;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/23 11:02
 */
public class Main {
    public static void main(String[] args) {

        PulseWidth.xlsx(
                "D:\\Code\\javaExample\\Utils\\xlsx\\src\\main\\resources\\com\\y\\sigma.xlsx", "sigma",
                "D:\\Code\\javaExample\\Utils\\xlsx\\src\\main\\resources\\com\\y\\sigma(计算结果).xlsx", true);

        PulseWidth.xlsx(
                "D:\\Code\\javaExample\\Utils\\xlsx\\src\\main\\resources\\com\\y\\thorlabs.xlsx", "thorlabs",
                "D:\\Code\\javaExample\\Utils\\xlsx\\src\\main\\resources\\com\\y\\thorlabs(计算结果).xlsx", true);
    }
}
