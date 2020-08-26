package pl.arciemowicz.adversity.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pl.arciemowicz.adversity.controller.AnalyticsCriteria;
import pl.arciemowicz.adversity.domain.AnalyticsData;
import pl.arciemowicz.adversity.domain.dto.Dimensions;

import java.lang.reflect.Field;
import java.util.*;

@Repository
public class AnalyticsRepositoryImpl implements AnalyticsRepositoryCustom {

    @Value("${database.collection-name}")
    private String databaseCollectionName;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public AnalyticsRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<AnalyticsData> findAllByCriteria(AnalyticsCriteria analyticsCriteria) {

        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        List<String> groupOn = new ArrayList<>(analyticsCriteria.getGroupByDimensions());
        List<String> projectedFields = new ArrayList<>();
        projectedFields.addAll(groupOn);
        projectedFields.addAll(analyticsCriteria.getMetricsToBeAggregatedOn());

        String EMPTY_GROUP = "_";
        groupOn.add(EMPTY_GROUP);
        GroupOperation groupOperation = Aggregation.group(groupOn.toArray(new String[0]));

        for (String metricToBeAggregatedOn : analyticsCriteria.getMetricsToBeAggregatedOn()) {
            groupOperation = groupOperation.sum(metricToBeAggregatedOn).as(metricToBeAggregatedOn);
        }
        aggregationOperations.add(groupOperation);

        List<MatchOperation> matchOperations = new ArrayList<>();
        Map<String, String> dimensionsToFilterOn = analyticsCriteria.getFilterOnDimensions();
        for (Map.Entry<String, String> dimensionToFilterOn : dimensionsToFilterOn.entrySet()) {
            matchOperations.add(Aggregation.match(Criteria.where(dimensionToFilterOn.getKey()).regex(dimensionToFilterOn.getValue())));
        }

        if (!projectedFields.isEmpty()) {
            aggregationOperations.add(Aggregation.project(projectedFields.toArray(new String[0])));
        }

        aggregationOperations.addAll(matchOperations);
        AggregationResults<AnalyticsData> groupResults = mongoTemplate.aggregate(Aggregation.newAggregation(aggregationOperations), databaseCollectionName, AnalyticsData.class);

        return groupResults.getMappedResults();
    }
}
