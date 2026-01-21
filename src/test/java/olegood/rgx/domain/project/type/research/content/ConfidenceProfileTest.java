package olegood.rgx.domain.project.type.research.content;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ConfidenceProfileTest {

    @Test
    void shouldCalculateOverallConfidenceWhenAllFactorsArePresent() {
        // given
        var profile = new ConfidenceProfile();
        profile.setBaseConfidence(new BigDecimal("0.80"));
        profile.setMethodMaturityFactor(new BigDecimal("0.90"));
        profile.setTeamExpertiseFactor(new BigDecimal("0.85"));
        profile.setDataAvailabilityFactor(new BigDecimal("0.95"));

        // when
        var result = profile.calculateOverallConfidence();

        // then
        assertThat(result).isEqualByComparingTo(new BigDecimal("0.5814")); // 0.80 * 0.90 * 0.85 * 0.95
    }

    @Test
    void shouldCalculateOverallConfidenceWhenSomeFactorsAreMissing() {
        // given
        var profile = new ConfidenceProfile();
        profile.setBaseConfidence(new BigDecimal("0.70"));
        profile.setMethodMaturityFactor(new BigDecimal("0.90"));
        profile.setTeamExpertiseFactor(null);
        profile.setDataAvailabilityFactor(new BigDecimal("0.80"));

        // when
        var result = profile.calculateOverallConfidence();

        // then
        assertThat(result).isEqualByComparingTo(new BigDecimal("0.5040")); // 0.70 * 0.90 * 0.80
    }

    @Test
    void shouldReturnZeroWhenBaseConfidenceIsMissing() {
        // given
        var profile = new ConfidenceProfile();
        profile.setBaseConfidence(null);
        profile.setMethodMaturityFactor(new BigDecimal("0.80"));
        profile.setTeamExpertiseFactor(new BigDecimal("0.70"));
        profile.setDataAvailabilityFactor(new BigDecimal("0.90"));

        // when
        var result = profile.calculateOverallConfidence();

        // then
        assertThat(result).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldClampToOneWhenFactorsExceedOne() {
        // given
        var profile = new ConfidenceProfile();
        profile.setBaseConfidence(new BigDecimal("0.90"));
        profile.setMethodMaturityFactor(new BigDecimal("1.50"));
        profile.setTeamExpertiseFactor(new BigDecimal("1.20"));
        profile.setDataAvailabilityFactor(new BigDecimal("1.10"));

        // when
        var result = profile.calculateOverallConfidence();

        // then
        assertThat(result).isEqualByComparingTo(BigDecimal.ONE);
    }

    @Test
    void shouldReturnZeroWhenAllFactorsAreZero() {
        // given
        var profile = new ConfidenceProfile();
        profile.setBaseConfidence(new BigDecimal("0.50"));
        profile.setMethodMaturityFactor(BigDecimal.ZERO);
        profile.setTeamExpertiseFactor(BigDecimal.ZERO);
        profile.setDataAvailabilityFactor(BigDecimal.ZERO);

        // when
        var result = profile.calculateOverallConfidence();

        // then
        assertThat(result).isEqualByComparingTo(BigDecimal.ZERO); // Base confidence (0.50) * 0 * 0 * 0 = 0
    }

    @Test
    void shouldClampToZeroWhenNegativeFactorsArePresent() {
        // given
        var profile = new ConfidenceProfile();
        profile.setBaseConfidence(new BigDecimal("0.80"));
        profile.setMethodMaturityFactor(new BigDecimal("-0.50"));
        profile.setTeamExpertiseFactor(new BigDecimal("0.70"));
        profile.setDataAvailabilityFactor(new BigDecimal("0.90"));

        // when
        var result = profile.calculateOverallConfidence();

        // then
        assertThat(result).isEqualByComparingTo(BigDecimal.ZERO); // Result clamped to 0
    }
}
