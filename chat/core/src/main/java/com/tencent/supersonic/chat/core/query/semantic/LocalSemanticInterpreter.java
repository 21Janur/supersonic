package com.tencent.supersonic.chat.core.query.semantic;

import com.github.pagehelper.PageInfo;
import com.tencent.supersonic.auth.api.authentication.pojo.User;
import com.tencent.supersonic.common.util.ContextUtils;
import com.tencent.supersonic.common.util.JsonUtil;
import com.tencent.supersonic.headless.api.pojo.request.DataSetFilterReq;
import com.tencent.supersonic.headless.api.pojo.request.ExplainSqlReq;
import com.tencent.supersonic.headless.api.pojo.request.PageDimensionReq;
import com.tencent.supersonic.headless.api.pojo.request.PageMetricReq;
import com.tencent.supersonic.headless.api.pojo.request.QueryMultiStructReq;
import com.tencent.supersonic.headless.api.pojo.request.QuerySqlReq;
import com.tencent.supersonic.headless.api.pojo.request.QueryStructReq;
import com.tencent.supersonic.headless.api.pojo.response.DataSetResp;
import com.tencent.supersonic.headless.api.pojo.response.DataSetSchemaResp;
import com.tencent.supersonic.headless.api.pojo.response.DimensionResp;
import com.tencent.supersonic.headless.api.pojo.response.DomainResp;
import com.tencent.supersonic.headless.api.pojo.response.ExplainResp;
import com.tencent.supersonic.headless.api.pojo.response.ItemResp;
import com.tencent.supersonic.headless.api.pojo.response.MetricResp;
import com.tencent.supersonic.headless.api.pojo.response.SemanticQueryResp;
import com.tencent.supersonic.headless.server.service.DimensionService;
import com.tencent.supersonic.headless.server.service.MetricService;
import com.tencent.supersonic.headless.server.service.QueryService;
import com.tencent.supersonic.headless.server.service.SchemaService;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalSemanticInterpreter extends BaseSemanticInterpreter {

    private SchemaService schemaService;
    private DimensionService dimensionService;
    private MetricService metricService;
    private QueryService queryService;

    @SneakyThrows
    @Override
    public SemanticQueryResp queryByStruct(QueryStructReq queryStructReq, User user) {
        queryService = ContextUtils.getBean(QueryService.class);
        if (queryStructReq.isConvertToSql()) {
            return queryService.queryByReq(queryStructReq.convert(), user);
        }
        return queryService.queryByReq(queryStructReq, user);
    }

    @Override
    @SneakyThrows
    public SemanticQueryResp queryByMultiStruct(QueryMultiStructReq queryMultiStructReq, User user) {
        queryService = ContextUtils.getBean(QueryService.class);
        return queryService.queryByReq(queryMultiStructReq, user);
    }

    @Override
    @SneakyThrows
    public SemanticQueryResp queryByS2SQL(QuerySqlReq querySqlReq, User user) {
        queryService = ContextUtils.getBean(QueryService.class);
        SemanticQueryResp object = queryService.queryByReq(querySqlReq, user);
        return JsonUtil.toObject(JsonUtil.toString(object), SemanticQueryResp.class);
    }

    @Override
    public List<DataSetSchemaResp> doFetchDataSetSchema(List<Long> ids) {
        DataSetFilterReq filter = new DataSetFilterReq();
        filter.setDataSetIds(ids);
        schemaService = ContextUtils.getBean(SchemaService.class);
        return schemaService.fetchDataSetSchema(filter);
    }

    @Override
    public List<DomainResp> getDomainList(User user) {
        schemaService = ContextUtils.getBean(SchemaService.class);
        return schemaService.getDomainList(user);
    }

    @Override
    public List<DataSetResp> getDataSetList(Long domainId) {
        schemaService = ContextUtils.getBean(SchemaService.class);
        return schemaService.getDataSetList(domainId);
    }

    @Override
    public <T> ExplainResp explain(ExplainSqlReq<T> explainSqlReq, User user) throws Exception {
        queryService = ContextUtils.getBean(QueryService.class);
        return queryService.explain(explainSqlReq, user);
    }

    @Override
    public PageInfo<DimensionResp> getDimensionPage(PageDimensionReq pageDimensionCmd) {
        dimensionService = ContextUtils.getBean(DimensionService.class);
        return dimensionService.queryDimension(pageDimensionCmd);
    }

    @Override
    public PageInfo<MetricResp> getMetricPage(PageMetricReq pageMetricReq, User user) {
        metricService = ContextUtils.getBean(MetricService.class);
        return metricService.queryMetric(pageMetricReq, user);
    }

    @Override
    public List<ItemResp> getDomainDataSetTree() {
        return schemaService.getDomainDataSetTree();
    }

}
