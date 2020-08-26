package pl.arciemowicz.adversity.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.arciemowicz.adversity.domain.AnalyticsData;

public interface AnalyticsRepository extends MongoRepository<AnalyticsData, String>, AnalyticsRepositoryCustom {
}
