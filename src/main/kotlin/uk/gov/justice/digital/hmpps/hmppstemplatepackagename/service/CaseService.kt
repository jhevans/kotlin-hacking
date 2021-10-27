package uk.gov.justice.digital.hmpps.hmppstemplatepackagename.service

import uk.gov.justice.digital.hmpps.hmppstemplatepackagename.model.Case
import uk.gov.justice.digital.hmpps.hmppstemplatepackagename.model.Defendant
import uk.gov.justice.digital.hmpps.hmppstemplatepackagename.model.Plea

class CaseService(private val offenderService: OffenderService) {
    fun getCaseList(): List<Case> {
        return listOf(
            Case(Defendant(pnc = "12345/AB")),
            Case(Defendant(pnc = "12345/AB", anticipatedPlea = Plea.GUILTY)),
            Case(Defendant(pnc = "23456/BC")),
            Case(Defendant()),
        )
    }

    fun getOffenderEnrichedCases(): List<Case> {
        val caseList = getCaseList()
        // Create a map of each pnc to corresponding Offender
        val offenderMap = caseList
            .mapNotNull { it.defendant.pnc }
            .toSet()
            .associateWith { offenderService.getOffender(it) }

        return caseList
            .map {
                it.copy(
                    defendant = it.defendant.copy(
                        offender = offenderMap[it.defendant.pnc]
                    )
                )
            }
    }
}
