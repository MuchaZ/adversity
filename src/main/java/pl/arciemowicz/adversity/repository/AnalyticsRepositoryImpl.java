package pl.arciemowicz.adversity.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import pl.arciemowicz.adversity.controller.AnalyticsCriteria;
import pl.arciemowicz.adversity.domain.AnalyticsData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AnalyticsRepositoryImpl implements AnalyticsRepositoryCustom {

    @Value("${database.collection-name}")
    private String databaseCollectionName;

    @Value("${analytics-data.available-metrics}")
    private List<String> allMetrics;

    private final String DATE_COLUMN_NAME = "date";

    private final MongoTemplate mongoTemplate;

    @Autowired
    public AnalyticsRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<AnalyticsData> findAllByCriteria(AnalyticsCriteria analyticsCriteria) {
        List<AggregationOperation> aggregationOperations = processGroupsFiltersAndMatchesFor(analyticsCriteria, Optional.empty(), Optional.empty());

        return getResultsFor(aggregationOperations);
    }

    @Override
    public List<AnalyticsData> findAllBetweenDatesByCriteria(LocalDate dateFrom, LocalDate dateTo, AnalyticsCriteria analyticsCriteria) {
        List<AggregationOperation> aggregationOperations = processGroupsFiltersAndMatchesFor(analyticsCriteria, Optional.of(dateFrom), Optional.of(dateTo));

        return getResultsFor(aggregationOperations);
    }

    private List<AggregationOperation> processGroupsFiltersAndMatchesFor(AnalyticsCriteria analyticsCriteria, Optional<LocalDate> dateFrom, Optional<LocalDate> dateTo) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        List<String> groupOn = new ArrayList<>(analyticsCriteria.getGroupByDimensions());
        List<String> projectedFields = new ArrayList<>();
        projectedFields.addAll(groupOn);

        if (!groupOn.isEmpty()) {
            List<String> metricsToBeAggregatedOn = analyticsCriteria.getMetricsToBeAggregatedOn();
            if (metricsToBeAggregatedOn.isEmpty()) {
                metricsToBeAggregatedOn = allMetrics;
            }
            projectedFields.addAll(metricsToBeAggregatedOn);

            GroupOperation groupOperation = Aggregation.group(groupOn.toArray(new String[0]));
            for (String metricToBeAggregatedOn : metricsToBeAggregatedOn) {
                groupOperation = groupOperation.sum(metricToBeAggregatedOn).as(metricToBeAggregatedOn);
            }
            aggregationOperations.add(groupOperation);
        }

        List<MatchOperation> matchOperations = new ArrayList<>();
        Map<String, String> dimensionsToFilterOn = analyticsCriteria.getFilterOnDimensions();
        for (Map.Entry<String, String> dimensionToFilterOn : dimensionsToFilterOn.entrySet()) {
            matchOperations.add(Aggregation.match(Criteria.where(dimensionToFilterOn.getKey()).regex(dimensionToFilterOn.getValue())));
        }

        if (!projectedFields.isEmpty()) {
            ProjectionOperation projectionOperation;
            if (dateFrom.isPresent() && dateTo.isPresent()) {
                projectedFields.add(DATE_COLUMN_NAME);
                projectionOperation = Aggregation.project(projectedFields.toArray(new String[0]));
                projectionOperation
                        .and(ComparisonOperators.Gte.valueOf(DateOperators.DateFromString.fromStringOf(DATE_COLUMN_NAME).withFormat("%m/%d/%Y"))).gte(dateFrom).as("match_date_from")
                        .and(ComparisonOperators.Lte.valueOf(DateOperators.DateFromString.fromStringOf(DATE_COLUMN_NAME).withFormat("%m/%d/%Y"))).lte(dateTo).as("match_date_to");
                matchOperations.add(Aggregation.match(Criteria.where("match_date_from").is(true)));
                matchOperations.add(Aggregation.match(Criteria.where("match_date_to").is(true)));
            } else {
                projectionOperation = Aggregation.project(projectedFields.toArray(new String[0]));
            }
            aggregationOperations.add(projectionOperation);
        }
        aggregationOperations.addAll(matchOperations);

        return aggregationOperations;
    }

    private List<AnalyticsData> getResultsFor(List<AggregationOperation> aggregationOperations) {
        AggregationResults<AnalyticsData> groupResults = mongoTemplate.aggregate(Aggregation.newAggregation(aggregationOperations), databaseCollectionName, AnalyticsData.class);

        return groupResults.getMappedResults();
    }
}
