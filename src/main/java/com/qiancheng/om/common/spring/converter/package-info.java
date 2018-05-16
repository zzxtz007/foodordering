/**
 * 新增 & 覆盖 Spring 转换器
 * <p>
 * 原有的转换器在遇到错误时会抛出异常，这些异常对于该系统的设计是多余的。覆盖的转换器在遇到转换错误时直接返回 null，便于 Service 统一处理
 *
 * @author Brendan Lee
 */
package com.qiancheng.om.common.spring.converter;