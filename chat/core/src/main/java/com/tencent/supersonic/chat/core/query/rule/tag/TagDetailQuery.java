package com.tencent.supersonic.chat.core.query.rule.tag;

import static com.tencent.supersonic.chat.core.query.rule.QueryMatchOption.OptionType.REQUIRED;
import static com.tencent.supersonic.chat.core.query.rule.QueryMatchOption.RequireNumberType.AT_LEAST;
import static com.tencent.supersonic.headless.api.pojo.SchemaElementType.ID;
import static com.tencent.supersonic.headless.api.pojo.SchemaElementType.TAG;

import org.springframework.stereotype.Component;

@Component
public class TagDetailQuery extends TagSemanticQuery {

    public static final String QUERY_MODE = "TAG_DETAIL";

    public TagDetailQuery() {
        super();
        queryMatcher.addOption(TAG, REQUIRED, AT_LEAST, 1)
                .addOption(ID, REQUIRED, AT_LEAST, 1);
    }

    @Override
    public String getQueryMode() {
        return QUERY_MODE;
    }

}
