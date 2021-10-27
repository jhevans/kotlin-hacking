package uk.gov.justice.digital.hmpps.hmppstemplatepackagename.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class OffenderModelTest {

    @Test
    fun `An Offender cannot have ProbationStatus of NO_RECORD`() {
        val exception =
            org.junit.jupiter.api.assertThrows<IllegalArgumentException> { Offender(ProbationStatus.NO_RECORD) }

        assertThat(exception.message).isEqualTo("An Offender cannot have ProbationStatus NO_RECORD")
    }


    @Test
    fun `Probation Status of a defendant known to probation is CURRENT or PREVIOUSLY_KNOWN`() {
        val currentDefendant = Defendant(Offender(ProbationStatus.CURRENT), "12345/AB")
        val previousDefendant = Defendant(Offender(ProbationStatus.PREVIOUSLY_KNOWN), "12345/AB")

        assertThat(currentDefendant.getProbationStatus()).isEqualTo(ProbationStatus.CURRENT)
        assertThat(previousDefendant.getProbationStatus()).isEqualTo(ProbationStatus.PREVIOUSLY_KNOWN)
    }

    @Test
    fun `Probation Status of a defendant with no offender is NO_RECORD`() {
        val unknownDefendant = Defendant(null, "12345/AB")

        assertThat(unknownDefendant.getProbationStatus()).isEqualTo(ProbationStatus.NO_RECORD)
    }}
