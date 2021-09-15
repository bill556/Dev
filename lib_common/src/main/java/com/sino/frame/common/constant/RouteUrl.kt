package com.sino.frame.common.constant

/**
 * 路由地址
 *
 * @author
 * @since 2021/7/28 9:48 下午
 */
object RouteUrl {
    /**
     * Main模块
     */
    object Main {
        /**
         * 首页
         */
        const val MainActivity = "/module_main/MainActivity"
    }

    /**
     * 首页/健康模块
     */
    object Home {

        /**
         * 首页Fragment
         */
        const val HomeFragment = "/module_home/HomeFragment"
    }

    /**
     * 服务模块
     */
    object Service {

        /**
         * 服务Fragment
         */
        const val ServiceFragment = "/module_service/ServiceFragment"
    }

    /**
     * 首页模块
     */
    object Find {

        /**
         * 发现Fragment
         */
        const val FindFragment = "/module_find/FindFragment"
    }

    /**
     * 个人中心和更多模块
     */
    object Me {

        /**
         * 个人中心Fragment
         */
        const val MeFragment = "/module_me/MeFragment"
    }
}