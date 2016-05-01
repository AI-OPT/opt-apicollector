package com.ai.runner.apicollector.vo;

public enum ElasticType {
    /* RUNNER_API的基础类索引 */RUNNER_API_BASE,
    /* MVNE_API的基础类索引 */MVNE_API_BASE,
    /* API归属分类 */API_OWNER,
    /* API版本历史，含有所有版本记录 */API_VERSION_HISTORY,
    /* 最新发布的API记录只包含一个最后发布的版本 */API_VERSION_NEW,
    /* 参数类信息 */CLASS_DETAIL
}
