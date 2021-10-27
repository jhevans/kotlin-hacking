package uk.gov.justice.digital.hmpps.hmppstemplatepackagename.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.gov.justice.digital.hmpps.hmppstemplatepackagename.model.ProbationStatus

internal class OffenderServiceTest {
    private val offenderService = OffenderService()

    @Test
    fun `Get an Offender by PNC`() {
        val offender = offenderService.getOffender("12345/AB")
        assertThat(offender!!.probationStatus).isEqualTo(ProbationStatus.CURRENT)

        val offender2 = offenderService.getOffender("23456/BC")
        assertThat(offender2!!.probationStatus).isEqualTo(ProbationStatus.PREVIOUSLY_KNOWN)

        val nullOffender = offenderService.getOffender("Nope")
        assertThat(nullOffender).isNull()
    }
}
