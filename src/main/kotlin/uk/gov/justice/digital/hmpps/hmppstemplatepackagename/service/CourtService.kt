package uk.gov.justice.digital.hmpps.hmppstemplatepackagename.service

import uk.gov.justice.digital.hmpps.hmppstemplatepackagename.model.Case
import uk.gov.justice.digital.hmpps.hmppstemplatepackagename.model.Plea

class CourtService {
    private val caseService = CaseService(OffenderService())

    fun assignCasesToCourtRooms(): Map<String, List<Case>> {
        val allCases = caseService.getOffenderEnrichedCases().toSet()
        val gapCases = allCases.filter { it.defendant.anticipatedPlea == Plea.GUILTY}

        return mapOf(
            "GAP Court Room" to gapCases,
            "Main Court Room" to (allCases subtract gapCases.toSet()).toList()
        )
    }
}
