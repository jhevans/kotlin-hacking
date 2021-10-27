package uk.gov.justice.digital.hmpps.hmppstemplatepackagename.service

import uk.gov.justice.digital.hmpps.hmppstemplatepackagename.model.Offender
import uk.gov.justice.digital.hmpps.hmppstemplatepackagename.model.ProbationStatus

class OffenderService {
    private val offenders = mapOf(
        "12345/AB" to Offender(ProbationStatus.CURRENT),
        "23456/BC" to Offender(ProbationStatus.PREVIOUSLY_KNOWN),
    )

    /**
     * This call is expensive so we should use it efficiently
     */
    fun getOffender(pnc: String): Offender? {
        return offenders[pnc]
    }
}
