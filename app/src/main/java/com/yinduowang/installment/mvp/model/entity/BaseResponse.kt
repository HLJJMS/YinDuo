/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yinduowang.installment.mvp.model.entity

import java.io.Serializable


/**
 * ================================================
 * 如果你服务器返回的数据格式固定为这种方式(这里只提供思想,服务器返回的数据格式可能不一致,可根据自家服务器返回的格式作更改)
 * 指定范型即可改变 `data` 字段的类型, 达到重用 [BaseResponse], 如果你实在看不懂, 请忽略
 *
 *
 * Created by JessYan on 26/09/2016 15:19
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
data class BaseResponse<T>(var data: T? = null, var code: String = "", var msg: String = "") : Serializable {

    override fun toString(): String {
        return "BaseResponse{" +
                "code='" + code + '\''.toString() +
                ", msg='" + msg + '\''.toString() +
                '}'.toString()
    }
}

data class BasePhpResponse<T>(var data: T? = null, var status: String = "", var msg: String = "") : Serializable {

    override fun toString(): String {
        return "BaseResponse{" +
                "code='" + status + '\''.toString() +
                ", msg='" + msg + '\''.toString() +
                '}'.toString()
    }
}

data class CodeResponse(var code: String = "", var status: String = "", var msg: String = "") : Serializable
