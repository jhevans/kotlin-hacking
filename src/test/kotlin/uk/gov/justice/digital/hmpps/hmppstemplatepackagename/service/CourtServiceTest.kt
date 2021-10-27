package uk.gov.justice.digital.hmpps.hmppstemplatepackagename.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.gov.justice.digital.hmpps.hmppstemplatepackagename.model.Plea

internal class CourtServiceTest {
    private val courtService = CourtService()
    @Test
    fun `Get cases for each CourtRoom`() {
        val courtLists = courtService.assignCasesToCourtRooms()

        assertThat(courtLists["GAP Court Room"]).hasSize(1)
        assertThat(courtLists["GAP Court Room"]!![0].defendant.anticipatedPlea).isEqualTo(Plea.GUILTY)
        assertThat(courtLists["Main Court Room"]).hasSize(3)
    }
}
