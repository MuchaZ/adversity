Adversity Interview Task
=============
### Used programming languages and frameworks
- Java
- Spring-Boot
- Mongo DB

Tools and libraries used in the project are: Jackson CSV, Lombok.

### Run

First, **run Mongo server instance** on default port (27017).

Then run the app using Maven:
```bash
./mvnw
```
The BE will be available at http://localhost:8080.

#### Sample usage


**Endpoints**:
/api/analytics, /api/analytics/totalClicks?dateFrom={ISO_DATE}&dateTo{ISO_DATE}, /api/analytics/ctr

All endpoints can be run with full analytics criteria.

This makes the solution very flexible in terms of what values group on, filter on and aggregate on.

Eg. If we want to get CTR per Datasource and Campaign:
```
http://localhost:8080/api/analytics/ctr?groupByDimensions=dataSource,campaign
```


Analytics criteria that could be added to query:

##### metricsToBeAggregatedOn
Possible values:
```
clicks,
impressions
```
Sample: 
```
http://localhost:8080/api/analytics?metricsToBeAggregatedOn=clicks,impressions
```

##### groupByDimensions
Possible values:
```
dataSource,
campaign
```
Sample: 
```
http://localhost:8080/api/analytics?groupByDimensions=dataSource
```

##### filterOnDimensions
Possible values:
```
[dataSource]={string}
[campaign]={string}
```
Sample: 
```
http://localhost:8080/api/analytics?filterOnDimensions[campaign]=GDN_Retargeting
```

#### Sample complex queries:
```
http://localhost:8080/api/analytics?groupByDimensions=dataSource&filterOnDimensions[dataSource]=Google Ads
```
```
http://localhost:8080/api/analytics/totalClicks/?dateFrom=2019-11-10&dateTo=2019-12-30&groupByDimensions=dataSource&filterOnDimensions[dataSource]=Google Ads
```

#### Choices and future development
I've tried to keep things simple but open to future extensions. What I didn't care much about is:
- security: e.g. parameters validation, open CORS, no SSL communication, no safe production configuration and so on.
- testing: Because of missing time I skipped writing most of the required tests, but in the real app for sure it would need work here regarding more unit tests, some integration ones, BDD tests...
- exceptions handling
- performance: Mongo might be not the best choice, especially when requiring custom repository implementation, but taking into consideration limited time, it was an optimum solution

The code is for sure not ready for production, but I believe it's a quite nice seed.

The critical part for the future development I've focused on consists of 2 things:
- proper controller-service-repository flow with the inversion of control so that e.g. replacing the single-file-based repository would be easy.
- future-wise REST endpoint and models.
