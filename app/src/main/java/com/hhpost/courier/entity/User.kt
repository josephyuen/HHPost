package com.hhpost.courier.entity

/**
 * 创建者: (WangXu --- The Great God)
 * 功能描述: 实体类，这个需要和 [com.hhpost.courier.UserCenter] 配合使用啦
 */
class User {
    var uid: Long = 0

    constructor() {}

    constructor(uid: Long) {
        this.uid = uid
    }

}
