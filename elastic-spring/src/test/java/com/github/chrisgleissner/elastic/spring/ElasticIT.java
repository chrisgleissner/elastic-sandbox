package com.github.chrisgleissner.elastic.spring;

import com.github.chrisgleissner.elastic.spring.fixture.AbstractElasticIT;
import lombok.SneakyThrows;
import org.elasticsearch.client.license.GetLicenseRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElasticIT extends AbstractElasticIT {

    @Test
    void healthy() {
        getFixture().assertHealthy();
    }

    @SneakyThrows
    @Test
    void basicLicense() {
        String licenseDefinition = getFixture().getClient().license()
                .getLicense(new GetLicenseRequest(), getFixture().getRequestOptions()).getLicenseDefinition();
        assertThat(licenseDefinition).contains("\"type\" : \"basic\"");
        assertThat(licenseDefinition).contains("\"status\" : \"active\"");
    }
}
