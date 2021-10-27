# Goal
Produce something that looks like real code which does real probation stuff and write it in idiomatic Kotlin.


## Requirements

- Create a mock service that returns info on Cases (data classes) ✅
- Create a batch job that will associate some known Defendants from another source with these (associateWith) ✅ 
- Assign each defendant to a CourtRoom based on their anticipated plea ✅

# *BONUS* Requirements
- Assign each offender to a probation officer depending on the complexity of their case (use sequences/iterators?)
- Publish the following events for relevant defendants (set arithmetic)
  - New court appearance (all known offenders)
  - Pre-sentence report request (defendants with guilty anticipated plea) (if this is actually a thing that makes sense - check)
