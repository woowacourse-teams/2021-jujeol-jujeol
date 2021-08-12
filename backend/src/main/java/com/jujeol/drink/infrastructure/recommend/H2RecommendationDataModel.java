package com.jujeol.drink.infrastructure.recommend;

import com.jujeol.member.domain.Preference;
import javax.sql.DataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.SQL92JDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"local","test"})
@Component
public class H2RecommendationDataModel implements RecommendationDataModel {

    private final DataModel dataModel;

    public H2RecommendationDataModel(DataSource dataSource) {
        dataModel = new SQL92JDBCDataModel(dataSource, Preference.class.getSimpleName().toLowerCase(), "member_id", "drink_id", "rate", null);
    }

    @Override
    public DataModel dataModel() {
        return dataModel;
    }
}
