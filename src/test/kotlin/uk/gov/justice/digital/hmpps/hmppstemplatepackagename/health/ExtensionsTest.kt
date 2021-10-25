package uk.gov.justice.digital.hmpps.hmppstemplatepackagename.health

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


// A person can have one of the following ProbationStatuses
enum class ProbationStatus {
  CURRENT,
  PREVIOUSLY_KNOWN,
  NO_RECORD
}

// ... a known Offender by definition can't be 'NO_RECORD'
data class Offender (
  val probationStatus: ProbationStatus
) {
  init {
    require(probationStatus != ProbationStatus.NO_RECORD) {
      "An Offender cannot have ProbationStatus NO_RECORD"
    }
  }
}

// A defendant sometimes has an associated offender record if they're known to probation
data class Defendant (
  private val offender: Offender?
) {

  // So we can't get the probationStatus of this Offender because it could be null. This is a NullPointerException
  // waiting to happen! Or is it?
  fun probationStatus(): ProbationStatus {
    return this.offender.probationStatus()
  }

  // Fortunately - within this context we can define an extension function on Offender with a null receiver which
  // captures this relationship. We're completely null safe
  private fun Offender?.probationStatus(): ProbationStatus {
    // Elvis can simplify this further
    return if (this != null) this.probationStatus else ProbationStatus.NO_RECORD
  }
}

class ExtensionsTest {

  @Test
  fun `An Offender cannot have ProbationStatus of NO_RECORD`() {
    val exception = assertThrows<IllegalArgumentException> { Offender(ProbationStatus.NO_RECORD) }

    assertThat(exception.message).isEqualTo("An Offender cannot have ProbationStatus NO_RECORD")
  }


  @Test
  fun `Probation Status of a defendant known to probation is CURRENT or PREVIOUSLY_KNOWN`() {
    val currentDefendant = Defendant(Offender(ProbationStatus.CURRENT))
    val previousDefendant = Defendant(Offender(ProbationStatus.PREVIOUSLY_KNOWN))

    assertThat(currentDefendant.probationStatus()).isEqualTo(ProbationStatus.CURRENT)
    assertThat(previousDefendant.probationStatus()).isEqualTo(ProbationStatus.PREVIOUSLY_KNOWN)
  }

  @Test
  fun `Probation Status of a defendant with no offender is NO_RECORD`() {
    val unknownDefendant = Defendant(null)

    assertThat(unknownDefendant.probationStatus()).isEqualTo(ProbationStatus.NO_RECORD)
  }
}
