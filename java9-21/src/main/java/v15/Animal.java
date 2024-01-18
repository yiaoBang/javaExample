package v15;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/30 9:50
 *
 * 限制此类只能由Cat 和Dog继承
 * sealed 表示是一个密封类
 * permits 标识可以由哪些类继承
 */
public sealed class Animal permits Cat,Dog {

}
