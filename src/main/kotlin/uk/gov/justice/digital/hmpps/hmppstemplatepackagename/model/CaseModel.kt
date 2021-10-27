package uk.gov.justice.digital.hmpps.hmppstemplatepackagename.model

data class Case (val defendant: Defendant)

data class Defendant (
    // A defendant sometimes has an associated offender record if they're known to probation
    val offender: Offender? = null,
    val pnc: String? = null,
    val anticipatedPlea: Plea = Plea.NOT_GUILTY,
) {

    // So we can't get the probationStatus of this Offender because it could be null. This is a NullPointerException
    // waiting to happen! Or is it?
    fun getProbationStatus(): ProbationStatus {
        return this.offender.getProbationStatus()
    }

    // Fortunately - within this context we can define an extension function on Offender with a null receiver which
    // captures this relationship.
    private fun Offender?.getProbationStatus(): ProbationStatus {
        return this?.probationStatus ?: ProbationStatus.NO_RECORD
    }
}

data class Offender (
    val probationStatus: ProbationStatus
) {
    init {
        require(probationStatus != ProbationStatus.NO_RECORD) {
            "An Offender cannot have ProbationStatus NO_RECORD"
        }
    }
}

enum class ProbationStatus {
    CURRENT,
    PREVIOUSLY_KNOWN,
    NO_RECORD
}

enum class Plea {
    GUILTY,
    NOT_GUILTY
}
