package com.netease.nim.uikit.push;

/**
 * Copyright (C) 2020 jmw.com.cn Inc. All rights reserved.
 * <p>
 * Author:Created Jmw by HeJingzhou on 2020-04-24 11:18
 * <p>
 * Company:北京天创时代信息技术有限公司
 * <p>
 * Email:tcoywork@163.com
 * <p>
 * Effect:
 */
public interface PushRegisterIdListener {

    /**
     * 注册结果
     * @param registerId token 或者 registerId
     */
    void registerResult(String registerId);
}
