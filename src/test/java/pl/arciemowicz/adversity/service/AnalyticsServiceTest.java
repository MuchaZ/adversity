package pl.arciemowicz.adversity.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.arciemowicz.adversity.controller.AnalyticsCriteria;
import pl.arciemowicz.adversity.domain.AnalyticsData;
import pl.arciemowicz.adversity.domain.dto.AnalyticsDataDto;
import pl.arciemowicz.adversity.repository.AnalyticsRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnalyticsServiceTest {

    private final List<AnalyticsData> SAMPLE_DATA = new ArrayList<>();
    private final AnalyticsData DATASOURCE1_CAMPAIGN1_DATE10 = AnalyticsData.builder().dataSource("DATA_SOURCE_1").campaign("CAMPAIGN_1").date("10/10/10").clicks(BigDecimal.ONE).impressions(BigDecimal.TEN).build();
    private final AnalyticsData DATASOURCE1_CAMPAIGN2_DATE11 = AnalyticsData.builder().dataSource("DATA_SOURCE_1").campaign("CAMPAIGN_2").date("10/11/10").clicks(BigDecimal.ONE).impressions(BigDecimal.TEN).build();
    private final AnalyticsData DATASOURCE2_CAMPAIGN1_DATE12 = AnalyticsData.builder().dataSource("DATA_SOURCE_2").campaign("CAMPAIGN_1").date("10/12/10").clicks(BigDecimal.ONE).impressions(BigDecimal.TEN).build();
    private final AnalyticsData DATASOURCE2_CAMPAIGN2_DATE13 = AnalyticsData.builder().dataSource("DATA_SOURCE_2").campaign("CAMPAIGN_2").date("10/13/10").clicks(BigDecimal.ONE).impressions(BigDecimal.TEN).build();

    {
        SAMPLE_DATA.add(DATASOURCE1_CAMPAIGN1_DATE10);
        SAMPLE_DATA.add(DATASOURCE1_CAMPAIGN2_DATE11);
        SAMPLE_DATA.add(DATASOURCE2_CAMPAIGN1_DATE12);
        SAMPLE_DATA.add(DATASOURCE2_CAMPAIGN2_DATE13);
    }

    @MockBean
    private AnalyticsRepository analyticsRepository;

    @Autowired
    private AnalyticsService analyticsService;

    @Test
    public void returnsAllDataWhenCriteriaAreNotSpecified() {
        //given
        Mockito.when(analyticsRepository.findAll()).thenReturn(SAMPLE_DATA);
        AnalyticsCriteria analyticsCriteria = new AnalyticsCriteria();

        //when
        List<AnalyticsDataDto> analyticsDataDtos = analyticsService.getData(analyticsCriteria);

        //then
        SAMPLE_DATA.stream().map(AnalyticsDataDto::buildFrom).collect(Collectors.toList()).containsAll(analyticsDataDtos);
    }

    @Test
    public void returnsDataFromASpecificCampaign() {
        //given
        Map<String, String> dimensionsToFilterOn = new HashMap<>();
        dimensionsToFilterOn.put("campaign", "CAMPAIGN_1");

        AnalyticsCriteria analyticsCriteria = new AnalyticsCriteria();
        analyticsCriteria.setFilterOnDimensions(dimensionsToFilterOn);

        List<AnalyticsData> expectedData = new ArrayList<>();
        expectedData.add(DATASOURCE1_CAMPAIGN1_DATE10);
        expectedData.add(DATASOURCE2_CAMPAIGN1_DATE12);
        Mockito.when(analyticsRepository.findAllByCriteria(analyticsCriteria)).thenReturn(expectedData);

        //when
        List<AnalyticsDataDto> analyticsDataDtos = analyticsService.getData(analyticsCriteria);

        //then
        expectedData.stream().map(AnalyticsDataDto::buildFrom).collect(Collectors.toList()).containsAll(analyticsDataDtos);
    }

    @Test
    public void returnsDataFromASpecificDataSource() {
        //given
        Map<String, String> dimensionsToFilterOn = new HashMap<>();
        dimensionsToFilterOn.put("dataSource", "DATA_SOURCE_1");

        AnalyticsCriteria analyticsCriteria = new AnalyticsCriteria();
        analyticsCriteria.setFilterOnDimensions(dimensionsToFilterOn);

        List<AnalyticsData> expectedData = new ArrayList<>();
        expectedData.add(DATASOURCE1_CAMPAIGN1_DATE10);
        expectedData.add(DATASOURCE1_CAMPAIGN2_DATE11);
        Mockito.when(analyticsRepository.findAllByCriteria(analyticsCriteria)).thenReturn(expectedData);

        //when
        List<AnalyticsDataDto> analyticsDataDtos = analyticsService.getData(analyticsCriteria);

        //then
        expectedData.stream().map(AnalyticsDataDto::buildFrom).collect(Collectors.toList()).containsAll(analyticsDataDtos);
    }

    @Test
    public void returnsDataFromATotalClicksWithDateRange() {
        //given
        LocalDate dateFrom = LocalDate.of(2010, 10, 10);
        LocalDate dateTo = LocalDate.of(2010, 10, 12);

        AnalyticsCriteria analyticsCriteria = new AnalyticsCriteria();

        List<AnalyticsData> expectedData = new ArrayList<>();
        expectedData.add(DATASOURCE1_CAMPAIGN1_DATE10);
        expectedData.add(DATASOURCE1_CAMPAIGN2_DATE11);
        expectedData.add(DATASOURCE2_CAMPAIGN1_DATE12);
        Mockito.when(analyticsRepository.findAllBetweenDatesByCriteria(dateFrom, dateTo, analyticsCriteria)).thenReturn(expectedData);

        //when
        List<AnalyticsDataDto> analyticsDataDtos = analyticsService.getTotalClicks(dateFrom, dateTo, analyticsCriteria);

        //then
        expectedData.stream().map(AnalyticsDataDto::buildFrom).collect(Collectors.toList()).containsAll(analyticsDataDtos);
    }

}
