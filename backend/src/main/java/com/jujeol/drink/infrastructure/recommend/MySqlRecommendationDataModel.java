package com.jujeol.drink.infrastructure.recommend;

import com.jujeol.member.domain.Preference;
import javax.sql.DataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"dev","prod"})
@Component
public class MySqlRecommendationDataModel implements RecommendationDataModel {

    private final DataModel dataModel;

    public MySqlRecommendationDataModel(DataSource dataSource) {
        this.dataModel = new MySQLJDBCDataModel(dataSource, Preference.class.getSimpleName(),
                "member_id", "drink_id", "rate", null);
    }

    @Override
    public DataModel dataModel() {
        return dataModel;
    }
}
