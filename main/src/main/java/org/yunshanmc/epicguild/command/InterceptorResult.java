package org.yunshanmc.epicguild.command;

/**
 * 命令拦截器的处理结果
 * <p>
 * 作者：YunShan <br>
 * 创建日期：2016年8月7日
 */
public enum InterceptorResult {
    /** 继续执行其余的拦截器，不改变拦截状态 */
    COUTINUE,
    /** 继续执行其余的拦截器，并将拦截状态改为：允许命令执行 */
    ALLOW,
    /** 继续执行其余的拦截器，并将拦截状态改为：禁止命令执行 */
    DENY,
    /** 直接结束命令处理流程，不会执行其余拦截器，也不会执行命令 */
    STOP
}
