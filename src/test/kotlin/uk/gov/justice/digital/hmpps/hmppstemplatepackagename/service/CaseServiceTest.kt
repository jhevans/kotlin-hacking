package uk.gov.justice.digital.hmpps.hmppstemplatepackagename.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CaseServiceTest {
    private val offenderService = OffenderService()
    private val caseService = CaseService(offenderService)

    @Test
    fun `Get a list of cases`() {
        val caseList = caseService.getCaseList()
        assertThat(caseList).hasSize(4)

        val firstCase = caseList[0]
        assertThat(firstCase.defendant.pnc).isEqualTo("12345/AB")
        assertThat(firstCase.defendant.offender).isNull()
    }

    @Test
    fun `enrich some cases`() {
        val enrichedCases = caseService.getOffenderEnrichedCases()
        assertThat(enrichedCases).hasSize(4)

        assertThat(enrichedCases[0].defendant.pnc).isEqualTo("12345/AB")
        assertThat(enrichedCases[0].defendant.offender).isNotNull()

        assertThat(enrichedCases[1].defendant.pnc).isEqualTo("12345/AB")
        assertThat(enrichedCases[1].defendant.offender).isNotNull()

        assertThat(enrichedCases[2].defendant.pnc).isEqualTo("23456/BC")
        assertThat(enrichedCases[2].defendant.offender).isNotNull()

        assertThat(enrichedCases[3].defendant.pnc).isNull()
        assertThat(enrichedCases[3].defendant.offender).isNull()
    }
}
