Feature: Daytime Running Lights

  With an activated daytime running light, the low beam headlights are engaged when the engine is started

Background:
  Given ignition is on

Rule: Daytime running light remains active as long as ignition key is in ignition lock

@requirement(ELS-17)
Scenario: Daytime running light engaged
Given the driver turned daytime running light on
Then the low beam headlights will be activated

@requirement(ELS-17)
Scenario: Daytime running light engaged with ignition off
Given the ignition is off
And the driver turned daytime running light on
Then the low beam headlight will be deactivated
